package com.example.modelproject.model;

public class Colaborador {

    private String codzra;
    private String nome;
    private String codugb;

    public Colaborador() {
        this.codzra = "";
        this.nome = "";
        this.codugb = "";
    }

    public Colaborador(String codzra, String nome, String codugb) {
        this.codzra = codzra;
        this.nome = nome;
        this.codugb = codugb;
    }

    public String getCodzra() {
        return codzra;
    }

    public void setCodzra(String codzra) {
        this.codzra = codzra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodugb() {
        return codugb;
    }

    public void setCodugb(String codugb) {
        this.codugb = codugb;
    }
}