package com.example.modelproject.model;

public class Usuario {

    private String codigo;
    private String usuario;
    private String nome;
    private String senha;
    private String email;
    private String codpro;
    private String codsub;
    private String ativo;

    public Usuario() {
        this.codigo = "";
        this.usuario = "";
        this.nome = "";
        this.senha = "";
        this.email = "";
        this.codpro = "";
        this.codsub = "";
        this.ativo = "";
    }

    public Usuario(String codigo, String usuario, String nome, String senha, String email, String codpro, String codsub, String ativo) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.codpro = codpro;
        this.codsub = codsub;
        this.ativo = ativo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodpro() {
        return codpro;
    }

    public void setCodpro(String codpro) {
        this.codpro = codpro;
    }

    public String getCodsub() {
        return codsub;
    }

    public void setCodsub(String codsub) {
        this.codsub = codsub;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}


