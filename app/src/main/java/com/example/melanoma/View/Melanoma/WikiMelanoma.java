package com.example.melanoma.View.Melanoma;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.melanoma.Presenter.Melanoma.MelanomaPresenter;
import com.example.melanoma.R;

public class WikiMelanoma extends AppCompatActivity implements MelanomaPresenter.fetchDataCallBack {

    private ExpandableListView listView;
    private ProgressBar progressBar;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.lvExp);
        listView.setDivider(null);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        MelanomaPresenter melanomaPresenter = new MelanomaPresenter(
                getApplicationContext(),compositeDisposable,listView,this
                );
        melanomaPresenter.fetchData("data");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onSucces() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoading() {

    }
}
