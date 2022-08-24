package com.example.melanoma.Presenter.ABCDE;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.melanoma.R;

import com.example.melanoma.Model.modelABCDE;
import com.google.android.material.snackbar.Snackbar;


import androidx.core.content.ContextCompat;

public class ABCDEPresenter implements ABCDEPresenterInterface {
    private Context context;
    private int jumlahjawaban=0;
    private modelABCDE modelABCDE;

    public ABCDEPresenter(Context context) {
        this.context = context;
        modelABCDE = new modelABCDE();
    }


    @Override
    public String generateResult(View view, LinearLayout before,LinearLayout after) {
        String hasil_tes = "";
        if (jumlahjawaban<5){
            //notif jika terdapat ada jawaban yang kosong
            Snackbar snackbar = Snackbar.make(view,"Silahkan isi seluruh jawaban",Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryContainer));
            TextView textSnackbar = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
            textSnackbar.setTextColor(ContextCompat.getColor(context,R.color.colorOnPrimaryContainer));
            snackbar.show();
            jumlahjawaban=0;
        }else {
            if (modelABCDE.getJawabanABCDE(0).equals("Yes")||modelABCDE.getJawabanABCDE(1).equals("Yes")||
                    modelABCDE.getJawabanABCDE(2).equals("Yes")||modelABCDE.getJawabanABCDE(3).equals("Yes")||
                    modelABCDE.getJawabanABCDE(4).equals("Yes")){
                hasil_tes = "Tahi lalat yang diperiksa berkemungkinan kanker";

            }else{
                hasil_tes = "Tahi lalat yang diperiksa bukan merupakan kanker";
            }
            before.setVisibility(view.GONE);
            after.setVisibility(view.VISIBLE);
        }
        modelABCDE.clearJawabanABCDE();
        return hasil_tes;
    }

    @Override
    public void getAnswer(RadioButton radioButton) {
        if (radioButton != null){
            modelABCDE.setJawabanABCDE((String) radioButton.getText());
            jumlahjawaban++;
        }
    }

    @Override
    public int getSelectedID(RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            int selectedID = radioGroup.getCheckedRadioButtonId();
            return selectedID;
        }
        return 0;
    }

    @Override
    public void retryTest(RadioGroup radioGroupA, RadioGroup radioGroupB,
                          RadioGroup radioGroupC, RadioGroup radioGroupD,
                          RadioGroup radioGroupE, LinearLayout before,
                          LinearLayout after, View view) {
        radioGroupA.clearCheck();
        radioGroupB.clearCheck();
        radioGroupC.clearCheck();
        radioGroupD.clearCheck();
        radioGroupE.clearCheck();
        before.setVisibility(view.VISIBLE);
        after.setVisibility(view.GONE);
        jumlahjawaban=0;

    }
}
