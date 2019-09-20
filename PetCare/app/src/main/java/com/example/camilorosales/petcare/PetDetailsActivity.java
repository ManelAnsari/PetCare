package com.example.camilorosales.petcare;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PetDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Pet>> {

    private LineChart mTemperatureChart;
    private LineChart mHeartRateChart;
    private String mUserEmail;
    private Pet mPet;
    private EditText mStartDate;
    private EditText mEndDate;
    private Button mUpdate;
    private long mStartTimeStamp;
    private long mEndTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.mPet = (Pet) bundle.getSerializable("pet");
        this.mUserEmail = bundle.getString("email");
        getLoaderManager().initLoader(0, null, this);

        this.mStartDate = (EditText) findViewById(R.id.start_date);
        this.mEndDate = (EditText) findViewById(R.id.end_date);
        this.mUpdate = (Button) findViewById(R.id.update);

        this.mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartDate();
            }
        });

        this.mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEndDate();
            }
        });

        this.mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PetDetailsActivity", "Update");
                getLoaderManager().restartLoader(0, null, PetDetailsActivity.this);
            }
        });

        mTemperatureChart = findViewById(R.id.temperature_chart);
        mHeartRateChart = findViewById(R.id.hear_rate_chart);

        // Chart style
        setChartStyle(mTemperatureChart);
        setChartStyle(mHeartRateChart);

        //refresh
        mTemperatureChart.invalidate();
        mHeartRateChart.invalidate();

    }

    private void setChartStyle(LineChart lineChart) {
        // Controlling X axis
        XAxis xAxis = lineChart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);
            @Override
            public String getFormattedValue(float value) {
                long millis = (long) value;
                return mFormat.format(new Date(millis));
            }
        });

        // Controlling right side of y axis
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        // disable description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // force pinch zoom along both axis
        lineChart.setPinchZoom(true);

        lineChart.getXAxis().setDrawGridLines(false);
    }

    private LineData getTemperatureData(Pet pet, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSet = new LineDataSet(lineEntries, getString(R.string.temperature));
        lineDataSet.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        lineDataSet.setValueTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setDrawCircles(true);

        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    private LineData getHeartRateData(Pet pet, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSet = new LineDataSet(lineEntries, getString(R.string.heart_rate));
        lineDataSet.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        lineDataSet.setValueTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setDrawCircles(true);

        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    @Override
    public Loader<ArrayList<Pet>> onCreateLoader(int id, Bundle args) {
        Log.d("PetDetailsActivity", "Creating loader");
        try {
            JSONObject bodyJson = new JSONObject();
            JSONObject ownerJson = new JSONObject();
            Log.d("PetDetailsActivity", "email: " + this.mUserEmail);
            ownerJson.put("email", this.mUserEmail);
            Log.d("PetDetailsActivity", ownerJson.toString());
            bodyJson.put("owner", ownerJson);
            bodyJson.put("pet", this.mPet.toJson());
            if (this.mStartTimeStamp >= 0) bodyJson.put("beginDate", this.mStartTimeStamp);
            if (this.mEndTimeStamp >= 0) bodyJson.put("endDate", this.mEndTimeStamp);
            String bodyString = bodyJson.toString();
            Log.d("PetDetailsActivity", bodyString);
            return new PetLoader(PetDetailsActivity.this, "http://167.86.117.236:3001/api/getPetInfo", bodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Pet>> loader, ArrayList<Pet> pets) {
        ArrayList<Entry> temperatureEntries = new ArrayList<Entry>();
        ArrayList<Entry> heartBeatEntries = new ArrayList<Entry>();
        Log.d("PetDetailsActivity", "Data received");
        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);
        for (Pet pet : pets) {
            Log.v("PetDetailsActivity", "Adding temperature: (" + pet.getTime() + ", " + pet.getTemperature() + ")");
            Log.v("PetDetailsActivity", "Adding heart rate: (" + pet.getTime() + ", " + pet.getHeartRate() + ")");
            Log.v("PetDetailsActivity", "Timestamp: " + pet.getTime() + " -> " + format.format(new Date(pet.getTime())));
            temperatureEntries.add(new Entry(pet.getTime(), pet.getTemperature()));
            heartBeatEntries.add(new Entry(pet.getTime(), pet.getHeartRate()));
        }

        this.mTemperatureChart.setData(this.getTemperatureData(this.mPet, temperatureEntries));
        this.mTemperatureChart.invalidate();
        this.mHeartRateChart.setData(this.getHeartRateData(this.mPet, heartBeatEntries));
        this.mHeartRateChart.invalidate();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Pet>> loader) {
        Log.d("PetDetailsActivity", "Loader reset");
    }

    public void getStartDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int actualMonth = month + 1;
                String formattedDay = (dayOfMonth < 10) ? "0" + dayOfMonth : "" + dayOfMonth;
                String formattedMonth = (actualMonth < 10)? "0" + String.valueOf(actualMonth):String.valueOf(actualMonth);
                String formattedDate = formattedDay + "/" + formattedMonth + "/" + year;
                mStartDate.setText(formattedDate);

                try {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = (Date) format.parse(formattedDate);
                    mStartTimeStamp = date.getTime();
                } catch (ParseException e) {
                    Log.e("PetDetailsActivity", "Error parsing date");
                    e.printStackTrace();
                }
            }
        }, year, month, day);

        dialog.show();
    }

    public void getEndDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int actualMonth = month + 1;
                String formattedDay = (dayOfMonth < 10) ? "0" + dayOfMonth : "" + dayOfMonth;
                String formattedMonth = (actualMonth < 10)? "0" + String.valueOf(actualMonth):String.valueOf(actualMonth);
                String formattedDate = formattedDay + "/" + formattedMonth + "/" + year;
                mEndDate.setText(formattedDate);

                try {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = (Date) format.parse(formattedDate);
                    mEndTimeStamp = date.getTime();
                } catch (ParseException e) {
                    Log.e("PetDetailsActivity", "Error parsing date");
                    e.printStackTrace();
                }
            }
        }, year, month, day);

        dialog.show();
    }
}
