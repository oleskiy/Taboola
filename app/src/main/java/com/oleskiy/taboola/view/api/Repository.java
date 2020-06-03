package com.oleskiy.taboola.view.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.oleskiy.taboola.view.model.Item;
import com.oleskiy.taboola.view.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Repository {
    private static final Repository insance = new Repository();
    private static MutableLiveData<ArrayList<Item>> repositoryModelMutableLiveData = new MutableLiveData<ArrayList<Item>>();
    private ArrayList<Item> widgetModels = new ArrayList<>();
    private Repository() {}

    public static Repository instance() {
        return insance;
    }

    public void setMutableLiveData(MutableLiveData<ArrayList<Item>> modelMutableLiveData){
        repositoryModelMutableLiveData =  modelMutableLiveData;
    }

    public void getData() {
        getRemoteData();
    }

    private void getRemoteData(){
        Retrofit apiService = ApiService.initApiService();
        apiService.create(ApiService.ApiInterface.class).getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Item>>() {
                    @Override
                    public void accept(List<Item> models) throws Exception {
                        for (int i=0;i<models.size();i++ ) {
                           Item item =  models.get(i);
                           item.setId(i+1);
                            DBHelper.getInstance().addOrUpdateData(item);
                        }

                        sendDataToList();
                    }
                });
    }

    public void updateColor(int index, int color){
        DBHelper.getInstance().updateColor(index, color);

    }

    public void sendDataToList(){
        widgetModels.clear();
        ArrayList<Item> items = DBHelper.getInstance().getAllItems();
        widgetModels.addAll(items);
        addTaboolaWidget(2, Item.ViewType.TABOOLA_WIDGET);
        addTaboolaWidget(9, Item.ViewType.TABOOLA_FEED);
        repositoryModelMutableLiveData.postValue(widgetModels);
    }

    void addTaboolaWidget(int index, Item.ViewType type) {

        if (type == Item.ViewType.TABOOLA_FEED) {
            Item taboolaFeedItem = new Item();
            taboolaFeedItem.setViewType(Item.ViewType.TABOOLA_FEED);
            widgetModels.add(index, taboolaFeedItem);
        } else {
            Item taboolaWidgetItem = new Item();
            taboolaWidgetItem.setViewType(Item.ViewType.TABOOLA_WIDGET);
            widgetModels.add(index, taboolaWidgetItem);
        }
    }

    }
