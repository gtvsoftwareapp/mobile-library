package com.example.modelproject.model;

public class Competencia {

    private String compet;
    private String nivcom;


    public Competencia() {
        this.compet = "";
        this.nivcom = "";
    }

    public Competencia(String compet, String nivcom) {
        this.compet = compet;
        this.nivcom = nivcom;
    }

    public String getCompet() {
        return compet;
    }

    public void setCompet(String compet) {
        this.compet = compet;
    }

    public String getNivcom() {
        return nivcom;
    }

    public void setNivcom(String nivcom) {
        this.nivcom = nivcom;
    }
}
