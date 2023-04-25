package com.example.maket.ui.add_item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddItemVIewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AddItemVIewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add item fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
