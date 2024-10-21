package com.ti2.cc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, senha, nome_usuario, tipo) VALUES (?, ?, ?, ?)"; // Mude aqui
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getSenha());
            stmt.setString(3, user.getNomeUsuario());
            stmt.setString(4, user.getTipo()); // Mude aqui
            stmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setSenha(rs.getString("senha"));
                user.setNomeUsuario(rs.getString("nome_usuario"));
                user.setTipo(rs.getString("tipo")); // Mude aqui
                users.add(user);
            }
        }
        return users;
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNome(rs.getString("nome"));
                    user.setSenha(rs.getString("senha"));
                    user.setNomeUsuario(rs.getString("nome_usuario"));
                    user.setTipo(rs.getString("tipo")); // Mude aqui
                    return user;
                }
            }
        }
        return null;
    }

    public boolean updateUser(String nome_usuario, String nome, String senha) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, senha = ? WHERE nome_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome); // Atualizando o nome
            stmt.setString(2, senha); // Atualizando a senha
            stmt.setString(3, nome_usuario); // Usando o nome_usuario como identificador
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Retorna true se alguma linha foi atualizada
        }
    }
    

    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
