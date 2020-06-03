package com.oleskiy.taboola.view.api;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.oleskiy.taboola.view.model.Item;
import com.oleskiy.taboola.view.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Item>> modelMutableLiveData = new MutableLiveData<ArrayList<Item>>();

    public ViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Item>> getData(){
        Repository.instance().setMutableLiveData(modelMutableLiveData);
        Repository.instance().getData();
      return modelMutableLiveData;
    }









}
