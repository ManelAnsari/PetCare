package com.example.camilorosales.petcare;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class PetViewModel extends AndroidViewModel {
    private MutableLiveData<Pet> mPets;
    private MqttAndroidClient mClient;
    private final String mServer = "tcp://test.mosquitto.org:1883";
    private String mClientId;
    private MqttConnectOptions mOptions;
    private String mTopic;
    private int mQos;
    private final String TAG = "PetViewModel";

    public PetViewModel(@NonNull Application application) {
        super(application);
        mClientId = MqttClient.generateClientId();
        mClient = new MqttAndroidClient(application.getApplicationContext(), mServer, mClientId);
        mOptions = new MqttConnectOptions();
        mOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        mTopic = "sensors_info";
        mQos = 2;
        mPets = new MutableLiveData<Pet>();
        connect(mClient);

    }

    private void connect(MqttAndroidClient client){
        try{
            IMqttToken token = client.connect(mOptions);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG + "Connect", "Connected succesfully to the mServer");
                    subscribe(client, mTopic, mQos);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d(TAG, "Connection with the mServer couldn't be stablished");
                }
            });
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    private void subscribe(MqttAndroidClient client, String topic, int qos){
        try{
            IMqttToken token = client.subscribe(topic, qos);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG + "Subscribe", "Subscription to the topic completed successfully");
                    listen(client);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d(TAG + "Subscribe", "Couldn't subscribe to the topic");
                }
            });
        }catch(MqttException e){
            e.printStackTrace();
        }
    }

    private void listen(MqttAndroidClient client){
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.d(TAG + "Callbacks", "Connection lost, trying to reconnect");
                connect(client);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d(TAG+"Callbacks", "A message has arrived");
                String jsonString = new String(message.getPayload());

                Pet pet = QueryUtils.parseJson(jsonString);
                if (pet != null){
                    mPets.setValue(pet);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d(TAG + "Callbacks", "Delivery message completed");
            }
        });
    }

    public LiveData<Pet> getPet(){
        if (mPets == null){
            mPets = new MutableLiveData<Pet>();
        }
        return mPets;
    }
}
