package com.example.melanoma.View.ABCDE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.melanoma.Presenter.ABCDE.ABCDEPresenter;
import com.example.melanoma.R;
import com.example.melanoma.View.Deteksi.DeteksiMelanoma;

public class ABCDE extends AppCompatActivity {
    private RadioGroup asymmetrical,border,color,diameter,evolving;
    private RadioButton rbAsymmetrical,rbBorder,rbColor,rbDiameter,rbEvolving;
    private Button generate,deteksi,retry;
    private String hasilABCDE;
    private LinearLayout layoutabcde,resultbefore,resultafter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcde);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Test ABCDE");

        layoutabcde = findViewById(R.id.layout_ABCDE);
        layoutabcde.setBackgroundResource(R.drawable.layout_abcde);

        resultbefore = findViewById(R.id.btm_before_result);
        resultafter = findViewById(R.id.btm_after_result);

        deteksi = findViewById(R.id.m_deteksi);




        asymmetrical = findViewById(R.id.radio_asymmetrical);
        border = findViewById(R.id.radio_border);
        color = findViewById(R.id.radio_color);
        diameter = findViewById(R.id.radio_diameter);
        evolving = findViewById(R.id.radio_evolving);
        generate = findViewById(R.id.m_generate);

        retry = findViewById(R.id.m_retry);

        ABCDEPresenter presenterABCDE = new ABCDEPresenter(getApplicationContext());



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbAsymmetrical = findViewById(presenterABCDE.getSelectedID(asymmetrical));
                rbBorder = findViewById(presenterABCDE.getSelectedID(border));
                rbColor = findViewById(presenterABCDE.getSelectedID(color));
                rbDiameter = findViewById(presenterABCDE.getSelectedID(diameter));
                rbEvolving = findViewById(presenterABCDE.getSelectedID(evolving));
                presenterABCDE.getAnswer(rbAsymmetrical);
                presenterABCDE.getAnswer(rbBorder);
                presenterABCDE.getAnswer(rbColor);
                presenterABCDE.getAnswer(rbDiameter);
                presenterABCDE.getAnswer(rbEvolving);
                hasilABCDE = presenterABCDE.generateResult(findViewById(R.id.content), resultbefore, resultafter);
            }
        });
        deteksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(ABCDE.this, DeteksiMelanoma.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activityChangeIntent.putExtra("Hasil_ABCDE",hasilABCDE);
                startActivity(activityChangeIntent);
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterABCDE.retryTest(asymmetrical,border,color,diameter,evolving,resultbefore,resultafter, findViewById(R.id.content));
            }
        });
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
}
