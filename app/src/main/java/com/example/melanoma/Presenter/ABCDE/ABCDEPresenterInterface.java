package com.example.melanoma.Presenter.ABCDE;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public interface ABCDEPresenterInterface {
    String generateResult(View view, LinearLayout linearLayout,LinearLayout linearLayout2);
    void getAnswer(RadioButton radioButton);
    int getSelectedID(RadioGroup radioGroup);
    void retryTest(RadioGroup radioGroupA,RadioGroup radioGroupB,RadioGroup radioGroupC,
                   RadioGroup radioGroupD, RadioGroup radioGroupE,LinearLayout linearLayout,
                   LinearLayout linearLayout2, View view);
}


