package com.example.melanoma.View.Home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.melanoma.R;
import com.example.melanoma.View.ABCDE.ABCDE;
import com.example.melanoma.View.Deteksi.DeteksiMelanoma;
import com.example.melanoma.View.Melanoma.WikiMelanoma;

import org.opencv.android.OpenCVLoader;

public class HomeActivity extends AppCompatActivity {




    private CardView wiki,abcde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        wiki = findViewById(R.id.home_melanoma_card);
        abcde = findViewById(R.id.home_tes_card);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(getApplicationContext(), WikiMelanoma.class);

                startActivity(activityChangeIntent);
            }
        });

        abcde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(getApplicationContext(), ABCDE.class);

                startActivity(activityChangeIntent);

            }
        });

    }
}
