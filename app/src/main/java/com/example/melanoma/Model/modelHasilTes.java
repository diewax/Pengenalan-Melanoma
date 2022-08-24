package com.example.melanoma.Model;

public class modelHasilTes {


    private String deteksi;
    private String ABCDE;
    private String hasilakhirgood ="Tahi lalat merupakan tahi lalat biasa";
    private String hasilakhirbad ="Segera cek kondisi ke dokter kulit terdekat!!";
    private String hasilakhirgoodbad = "Tahi lalat yang diperiksa dapat berkemungkinan kanker";
    public modelHasilTes(String deteksi,String ABCDE){
        this.deteksi = deteksi;
        this.ABCDE = ABCDE;

    }
    public String getDeteksi() {
        return deteksi;
    }

    public String getABCDE() {
        return ABCDE;
    }

    public String getHasilakhirgood() {
        return hasilakhirgood;
    }

    public String getHasilakhirbad() {
        return hasilakhirbad;
    }

    public String getHasilakhirgoodbad() {
        return hasilakhirgoodbad;
    }
}
