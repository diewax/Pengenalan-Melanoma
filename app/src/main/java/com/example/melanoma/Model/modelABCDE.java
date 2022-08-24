package com.example.melanoma.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class modelABCDE {

    private List<String> jawabanABCDE;

    public modelABCDE() {
        jawabanABCDE = new ArrayList<>();
    }

    public String getJawabanABCDE(int a) {
        return jawabanABCDE.get(a);
    }

    public void setJawabanABCDE(String jawabanABCDE) {
        this.jawabanABCDE.add(jawabanABCDE);
    }
    public void clearJawabanABCDE(){
        jawabanABCDE.clear();
    }

}
