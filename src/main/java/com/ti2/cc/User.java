package com.ti2.cc;

public class User {
    private int id;
    private String nome;
    private String senha;
    private String nome_usuario;
    private String tipo; // Adicione este campo

    // Construtores, getters e setters
    public User() {}

    public User(String nome, String nome_usuario, String senha, String tipo) { // Mude aqui
        this.nome = nome;
        this.nome_usuario = nome_usuario;
        this.senha = senha;
        this.tipo = tipo; // Mude aqui
    }
    
    public User(int id, String nome, String senha, String nome_usuario, String tipo) { // Mude aqui
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.nome_usuario = nome_usuario;
        this.tipo = tipo; // Mude aqui
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNomeUsuario() {
        return nome_usuario;
    }

    public void setNomeUsuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
