package com.example.modelproject.model;

public class Configuracao {

    private int id;
    private String url;

    public Configuracao() {
        this.id = -1;
        this.url = "";
    }

    public Configuracao(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}