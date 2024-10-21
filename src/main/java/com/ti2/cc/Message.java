package com.ti2.cc;

public class Message {
    private int id;
    private String titulo;
    private String conteudo;
    private int usuariosId;

    // Construtor
    public Message(int id, String titulo, String conteudo, int usuariosId) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.usuariosId = usuariosId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(int usuariosId) {
        this.usuariosId = usuariosId;
    }
}
