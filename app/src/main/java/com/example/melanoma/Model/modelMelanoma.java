package com.example.melanoma.Model;

import java.util.List;

import androidx.annotation.Nullable;

public class modelMelanoma {
    private String judul;
    private String desc;
    private String gambar;
    private List<String> desc_tambahan;

    public modelMelanoma(String judul, String desc, List<String> desc_tambahan,String gambar) {
        this.judul = judul;
        this.desc = desc;
        this.desc_tambahan = desc_tambahan;
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getDesc_tambahan() {
        return desc_tambahan;
    }

    public void setDesc_tambahan(List<String> desc_tambahan) {
        this.desc_tambahan = desc_tambahan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
