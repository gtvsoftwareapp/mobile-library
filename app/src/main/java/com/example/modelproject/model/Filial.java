package com.example.modelproject.model;

public class Filial {

    private String codfil;
    private String desfil;


    public Filial() {
        this.codfil = "";
        this.desfil = "";
    }

    public Filial(String codfil, String desfil) {
        this.codfil = codfil;
        this.desfil = desfil;
    }

    public String getFilial() {
        return codfil;
    }

    public void setFilial(String codfil) {
        this.codfil = codfil;
    }

    public String getFazenda() {
        return desfil;
    }

    public void setFazenda(String desfil) {
        this.desfil = desfil;
    }
}
