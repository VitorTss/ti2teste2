package com.ti2.cc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private Connection connection;

    public MessageDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveMessage(String titulo, String conteudo, int usuariosId, String nome_usuario) throws SQLException {
        String sql = "INSERT INTO mensagens (titulo, conteudo, usuarios_id, autor) VALUES (?, ?, ?, ?)"; // Adiciona o autor
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, conteudo);
            preparedStatement.setInt(3, usuariosId);
            preparedStatement.setString(4, nome_usuario); // Define o autor
            preparedStatement.executeUpdate();
        }
    }
    

    // Método para buscar todas as mensagens
    public List<Message> getAllMessages() throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM mensagens";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("conteudo"),
                        rs.getInt("usuarios_id"));
                messages.add(message);
            }
        }
        return messages;
    }

    // Método para buscar uma mensagem pelo ID
    public Message getMessageById(int id) throws SQLException {
        Message message = null;
        String sql = "SELECT * FROM mensagens WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                message = new Message(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("conteudo"),
                        rs.getInt("usuarios_id"));
            }
        }
        return message;
    }

    public void updateMessage(int id, String titulo, String conteudo, String nome_usuario) throws SQLException {
        String sql = "UPDATE mensagens SET titulo = ?, conteudo = ?, autor = ? WHERE id = ?"; // Inclui o autor
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, conteudo);
            preparedStatement.setString(3, nome_usuario); // Atualiza o autor
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }
    }
    

    // Método para deletar uma mensagem
    public boolean deleteMessage(int id) throws SQLException {
        String sql = "DELETE FROM mensagens WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
