package com.example.camilorosales.petcare;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class PetViewModelFactory implements ViewModelProvider.Factory {
    private String mUserEmail;
    private Application mApplication;

    public PetViewModelFactory(Application application, String userEmail) {
        this.mUserEmail = userEmail;
        this.mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PetViewModel(this.mApplication, this.mUserEmail);
    }
}
