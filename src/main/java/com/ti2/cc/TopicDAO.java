package com.ti2.cc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
    private Connection connection;

    public TopicDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveTopic(int id, String titulo, String conteudo, String link, String categoria, String imagem) throws SQLException {
        String sql = "INSERT INTO topicos (id ,titulo, conteudo, link, categoria, imagem) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, titulo);
            preparedStatement.setString(3, conteudo);
            preparedStatement.setString(4, link);
            preparedStatement.setString(5, categoria);
            preparedStatement.setString(6, imagem);
            preparedStatement.executeUpdate();
        }
    } 
    
    // Método para atualizar o tópico
    public void updateTopic(int id, String titulo, String conteudo, String link, String categoria, String imagem) throws SQLException {
        String sql = "UPDATE topicos SET titulo = ?, conteudo = ?, link = ?, categoria = ?, imagem = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, titulo);
            statement.setString(2, conteudo);
            statement.setString(3, link);
            statement.setString(4, categoria);
            statement.setString(5, imagem);
            statement.setInt(6, id);
            statement.executeUpdate();
        }
    }

    public Topic getTopicById(int id) throws SQLException {
        Topic topic = null;
        String sql = "SELECT * FROM topicos WHERE id = ?";
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                topic = new Topic(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("conteudo"),
                    rs.getString("link"),
                    rs.getString("categoria"),
                    rs.getString("imagem")
                );
            }
        }
        return topic;
    }    

    public boolean deleteTopic(int id) throws SQLException {
        String sql = "DELETE FROM topicos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    

    public List<Topic> getAllTopics() throws SQLException {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM public.topicos";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id"));
                topic.setTitulo(rs.getString("titulo"));
                topic.setConteudo(rs.getString("conteudo"));
                topic.setLink(rs.getString("link"));
                topic.setCategoria(rs.getString("categoria"));
                topic.setImagem(rs.getString("imagem"));
                //No banco de dados Categoria esta como string, resolver problema depois
                topics.add(topic);
            }
        }
        return topics;
    }
}