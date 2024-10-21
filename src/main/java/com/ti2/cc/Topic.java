package com.ti2.cc;

public class Topic {
    private int id;
    private String titulo;
    private String conteudo;
    private String link;
    private String categoria; // Array de texto para a categoria
    private String imagem; // Imagem em formato de array de bytes

    // Construtor padrão
    public Topic() {
    }

    // Construtor com parâmetros
    public Topic(int id, String titulo, String conteudo, String link, String categoria, String imagem) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.link = link;
        this.categoria = categoria;
        this.imagem = imagem;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria; 
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", link='" + link + '\'' +
                ", categoria=" + categoria + '\'' +
                ", imagem=" + (imagem != null ? "exists" : "null") +
                '}';
    }
}