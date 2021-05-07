package com.example.modelproject.model;

public class Ugb {
    private String filial;
    private String codloc;
    private String codger;
    private String codcoo;
    private String codugb;
    private String desugb;
    private int notasm;
    private int nota5s;
    private int avlmat;


    public Ugb() {
        this.filial = "";
        this.codloc = "";
        this.codger = "";
        this.codcoo = "";
        this.codugb = "";
        this.desugb = "";
        this.notasm = -1;
        this.nota5s = -1;
        this.avlmat = -1;

    }

    public Ugb(String filial, String codloc, String codger, String codcoo, String codugb, String desugb, int notasm, int nota5s, int avlmat) {
        this.filial = filial;
        this.codloc = codloc;
        this.codger = codger;
        this.codcoo = codcoo;
        this.codugb = codugb;
        this.desugb = desugb;
        this.notasm = notasm;
        this.nota5s = nota5s;
        this.avlmat = avlmat;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getCodloc() {
        return codloc;
    }

    public void setCodloc(String codloc) {
        this.codloc = codloc;
    }

    public String getCodger() {
        return codger;
    }

    public void setCodger(String codger) {
        this.codger = codger;
    }

    public String getCodcoo() {
        return codcoo;
    }

    public void setCodcoo(String codcoo) {
        this.codcoo = codcoo;
    }

    public String getCodugb() {
        return codugb;
    }

    public void setCodugb(String codugb) {
        this.codugb = codugb;
    }

    public String getDesugb() {
        return desugb;
    }

    public void setDesugb(String desugb) {
        this.desugb = desugb;
    }

    public int getNotasm() {
        return notasm;
    }

    public void setNotasm(int notasm) {
        this.notasm = notasm;
    }

    public int getNota5s() {
        return nota5s;
    }

    public void setNota5s(int nota5s) {
        this.nota5s = nota5s;
    }

    public int getAvlmat() {
        return avlmat;
    }

    public void setAvlmat(int avlmat) {
        this.avlmat = avlmat;
    }
}