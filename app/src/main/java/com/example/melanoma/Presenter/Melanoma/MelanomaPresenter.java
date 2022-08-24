package com.example.melanoma.Presenter.Melanoma;

import android.content.Context;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.melanoma.Model.modelMelanoma;
import com.example.melanoma.Rest.APIClient;
import com.example.melanoma.Rest.APInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.melanoma.View.Melanoma.Adapter.ExpandableListAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MelanomaPresenter implements MelanomaPresenterInterface {
    private Context context;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private List<String> listDataGambar;
    private APInterface client;
    private CompositeDisposable compositeDisposable;
    private fetchDataCallBack fetchDataCallBack;

    public MelanomaPresenter(Context context, CompositeDisposable compositeDisposable, ExpandableListView listView,fetchDataCallBack fetchDataCallBack) {
        this.context = context;
        this.compositeDisposable = compositeDisposable;
        this.listView = listView;
        this.fetchDataCallBack = fetchDataCallBack;
    }

    @Override
    public void fetchData(String data) {
        client = APIClient.getInstace().create(APInterface.class);
        Observable<List<modelMelanoma>> observable = client.getMelanoma(data);
        compositeDisposable.add(
            observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<List<modelMelanoma>>()
               {
                   @Override
                   public void onNext(@NonNull List<modelMelanoma> modelMelanomas) {
                       fetchDataCallBack.onLoading();
                       listDataHeader = new ArrayList<>();
                       listHashMap = new HashMap<>();
                       listDataGambar = new ArrayList<>();
                       int a = 0;
                       for(modelMelanoma element : modelMelanomas){
                           listDataHeader.add(element.getJudul());
                           List<String> deskripsi = new ArrayList<>();
                           String joined= "";
                           if (element.getDesc_tambahan() != null) {
                               joined = String.join("\n► ", element.getDesc_tambahan());
                               joined = "\n\n► "+joined;
                           }
                           if (element.getGambar() != null){
                               listDataGambar.add(element.getGambar());
                           }
                           deskripsi.add(element.getDesc()+ joined);
                           listHashMap.put(listDataHeader.get(a),deskripsi);
                           a++;
                       }
                       listAdapter = new ExpandableListAdapter(context,listDataHeader,listHashMap,listDataGambar);
                       listView.setAdapter(listAdapter);
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {
                       Log.e("", "Something went wrong\n" + e.getMessage());
                       Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                               .show();
                   }

                   @Override
                   public void onComplete() {
                        fetchDataCallBack.onSucces();
                   }
               }
            )
        );

    }
    public interface fetchDataCallBack{
        void onSucces();
        void onLoading();
    }


}
