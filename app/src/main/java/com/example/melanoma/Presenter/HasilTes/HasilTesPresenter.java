package com.example.melanoma.Presenter.HasilTes;

import android.widget.TextView;

import com.example.melanoma.Model.modelHasilTes;

public class HasilTesPresenter implements HasilTesPresenterInterface{
    private modelHasilTes modelHasilTes;
    public HasilTesPresenter(String deteksi,String ABCDE){
        modelHasilTes = new modelHasilTes(deteksi, ABCDE);
    }

    @Override
    public void hasilTes(TextView viewDeteksi, TextView viewABCDE, TextView viewFinal) {
        viewDeteksi.setText(modelHasilTes.getDeteksi());
        viewABCDE.setText(modelHasilTes.getABCDE());
        if (modelHasilTes.getABCDE().equals("Tahi lalat yang diperiksa berkemungkinan kanker")&&
                modelHasilTes.getDeteksi().equals("Terdapat Kemungkinan Kanker Melanoma")){
            viewFinal.setText(modelHasilTes.getHasilakhirbad());
        }else if (modelHasilTes.getABCDE().equals("Tahi lalat yang diperiksa bukan merupakan kanker")&&
                modelHasilTes.getDeteksi().equals("Tidak Terdapat Kemungkinan Kanker Melanoma")){
            viewFinal.setText(modelHasilTes.getHasilakhirgood());
        }else
            viewFinal.setText(modelHasilTes.getHasilakhirgoodbad());

    }
}
