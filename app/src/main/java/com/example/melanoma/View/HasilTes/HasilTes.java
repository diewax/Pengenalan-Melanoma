package com.example.melanoma.View.HasilTes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.melanoma.Presenter.HasilTes.HasilTesPresenter;
import com.example.melanoma.R;
import com.example.melanoma.View.Home.HomeActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HasilTes extends AppCompatActivity {

    private TextView textDeteksi, textABCDE, textFinal;
    private String isiTextDeteksi,isiTextABCDE;
    private LinearLayout layoutabcdetop,layoutabcdebtm,layoutdeteksitop,layoutdeteksibtm,layoutfinaltop,layoutfinalbtm;
    private Button btnFinish;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_tes);

        Bundle extras = getIntent().getExtras();
        isiTextDeteksi = extras.getString("Hasil_Deteksi");
        isiTextABCDE = extras.getString("Hasil_ABCDE");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Hasil Tes Tahi Lalat");

        textDeteksi = findViewById(R.id.hasil_deteksi);
        textABCDE = findViewById(R.id.hasil_ABCDE);
        textFinal = findViewById(R.id.hasil_kesimpulan);

        btnFinish = findViewById(R.id.button_finish);

        layoutabcdetop = findViewById(R.id.hasil_ABCDE_top);
        layoutabcdebtm = findViewById(R.id.hasil_ABCDE_btm);
        layoutdeteksitop = findViewById(R.id.hasil_Deteksi_top);
        layoutdeteksibtm = findViewById(R.id.hasil_Deteksi_btm);
        layoutfinaltop = findViewById(R.id.hasil_Final_top);
        layoutfinalbtm = findViewById(R.id.hasil_Final_btm);

        layoutabcdetop.setBackgroundResource(R.drawable.layout_hasiltestop);
        layoutabcdebtm.setBackgroundResource(R.drawable.layout_hasiltesbottom);
        layoutdeteksitop.setBackgroundResource(R.drawable.layout_hasiltestop);
        layoutdeteksibtm.setBackgroundResource(R.drawable.layout_hasiltesbottom);
        layoutfinaltop.setBackgroundResource(R.drawable.layout_hasiltestop);
        layoutfinalbtm.setBackgroundResource(R.drawable.layout_hasiltesbottom);

        HasilTesPresenter hasilTesPresenter = new HasilTesPresenter(isiTextDeteksi,isiTextABCDE);
        hasilTesPresenter.hasilTes(textDeteksi,textABCDE,textFinal);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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
