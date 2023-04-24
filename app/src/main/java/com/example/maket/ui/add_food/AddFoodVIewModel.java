package com.example.maket.ui.add_food;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddFoodVIewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AddFoodVIewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add fodd frament");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
