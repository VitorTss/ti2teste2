package com.ti2.cc;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "73548993";

    public static void main(String[] args) {
        port(4567); // Define a porta do servidor
        staticFiles.location("/public");

        // Testando a conexão
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Falha ao conectar ao banco de dados.");
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();

        // POST para criar um novo usuário
        post("/usuarios", (request, response) -> {
            String nome = request.queryParams("nome");
            String senha = request.queryParams("senha");
            String nomeUsuario = request.queryParams("nome_usuario"); // Novo campo
            String tipoUsuario = "cliente";

            // Instanciar UserDAO corretamente antes de usar
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                UserDAO userDAO = new UserDAO(connection);
                // Usar o construtor correto da classe User
                userDAO.saveUser(new User(nome, nomeUsuario, senha, tipoUsuario));
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao salvar o usuário!");
            }

            response.status(200);
            return gson.toJson("Usuário criado com sucesso!");
        });

        get("/usuarios", (request, response) -> {
            System.out.println("Requisição para listar todos os usuários recebida.");
            List<User> users = new ArrayList<>();
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                UserDAO userDAO = new UserDAO(connection);
                users = userDAO.getAllUsers();
                System.out.println("Usuários encontrados: " + users.size());
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao buscar os usuários!");
            }
            response.status(200);
            response.type("application/json");
            return gson.toJson(users);
        });
        

        get("/usuarios/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            System.out.println("Requisição para obter o usuário com ID: " + id);
            User user = null;
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                UserDAO userDAO = new UserDAO(connection);
                user = userDAO.getUserById(id);
                System.out.println("Usuário encontrado: " + user);
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao buscar o usuário!");
            }
            if (user == null) {
                response.status(404);
                return gson.toJson("Usuário não encontrado!");
            }
            response.status(200);
            response.type("application/json");
            return gson.toJson(user);
        });
        
        put("/usuarios/:nomeUsuario", (request, response) -> {
            String nomeUsuario = request.params(":nomeUsuario");
            User user = gson.fromJson(request.body(), User.class); // Recebe o objeto com nome e senha
        
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                UserDAO userDAO = new UserDAO(connection);
                boolean isUpdated = userDAO.updateUser(nomeUsuario, user.getNome(), user.getSenha());
                if (isUpdated) {
                    response.status(200);
                    return gson.toJson("Usuário atualizado com sucesso!");
                } else {
                    response.status(404);
                    return gson.toJson("Usuário não encontrado!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao atualizar o usuário!");
            }
        });
        

        // Rota para deletar um usuário
        delete("/usuarios/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                UserDAO userDAO = new UserDAO(connection);
                boolean deleted = userDAO.deleteUser(id);
                if (deleted) {
                    response.status(200);
                    return gson.toJson("Usuário deletado com sucesso!");
                } else {
                    response.status(404);
                    return gson.toJson("Usuário não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao deletar o usuário.");
            }
        });

        // POST para tópicos
        post("/submit", (request, response) -> {
            int id = Integer.parseInt(request.queryParams("id"));
            String titulo = request.queryParams("titulo");
            String conteudo = request.queryParams("conteudo");
            String link = request.queryParams("link");
            String categoria = request.queryParams("categoria");
            String imagem = request.queryParams("imagem");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                TopicDAO topicDAO = new TopicDAO(connection);
                topicDAO.saveTopic(id, titulo, conteudo, link, categoria, imagem);
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao salvar os dados!");
            }

            response.status(200);
            return gson.toJson("Dados recebidos com sucesso!");
        });

        // PUT para editar tópicos
        put("/topicos/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            String titulo = request.queryParams("titulo");
            String conteudo = request.queryParams("conteudo");
            String link = request.queryParams("link");
            String categoria = request.queryParams("categoria");
            String imagem = request.queryParams("imagem");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                TopicDAO topicDAO = new TopicDAO(connection);
                topicDAO.updateTopic(id, titulo, conteudo, link, categoria, imagem);
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao atualizar os dados!");
            }

            response.status(200);
            return gson.toJson("Tópico atualizado com sucesso!");
        });

        // GET para retornar todos os tópicos
        get("/data", (request, response) -> {
            List<Topic> topics = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                TopicDAO topicDAO = new TopicDAO(connection);
                topics = topicDAO.getAllTopics();
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao buscar os tópicos!");
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(topics);
        });

        // GET para retornar um tópico pelo ID
        get("/data/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Topic topic = null;

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                TopicDAO topicDAO = new TopicDAO(connection);
                topic = topicDAO.getTopicById(id);
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao buscar os dados!");
            }

            if (topic == null) {
                response.status(404);
                return gson.toJson("Tópico não encontrado!");
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(topic);
        });

        // Rota para deletar um tópico por ID
        delete("/data/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                TopicDAO topicDAO = new TopicDAO(connection);
                boolean deleted = topicDAO.deleteTopic(id);

                if (deleted) {
                    response.status(200);
                    return gson.toJson("Tópico deletado com sucesso!");
                } else {
                    response.status(404);
                    return gson.toJson("Tópico não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao deletar o tópico.");
            }
        });

        // ROTAS PARA MENSAGENS (CRUD)

        // POST para criar uma nova mensagem
        post("/mensagens", (request, response) -> {
            String titulo = request.queryParams("titulo");
            String conteudo = request.queryParams("conteudo");
            int usuariosId = Integer.parseInt(request.queryParams("usuariosId"));
            String autor = request.queryParams("autor"); // Pegar o autor
        
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                MessageDAO messageDAO = new MessageDAO(connection);
                messageDAO.saveMessage(titulo, conteudo, usuariosId, autor); // Passar o autor
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao salvar a mensagem: " + e.getMessage());
            }
        
            response.status(200);
            return gson.toJson("Mensagem recebida com sucesso!");
        });
        
        // GET para retornar todas as mensagens
        get("/mensagens", (request, response) -> {
            List<Message> messages = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                MessageDAO messageDAO = new MessageDAO(connection);
                messages = messageDAO.getAllMessages();
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao buscar as mensagens!");
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(messages);
        });

        // GET para retornar uma mensagem pelo ID
        get("/mensagens/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Message message = null;

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                MessageDAO messageDAO = new MessageDAO(connection);
                message = messageDAO.getMessageById(id);
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao buscar a mensagem!");
            }

            if (message == null) {
                response.status(404);
                return gson.toJson("Mensagem não encontrada!");
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(message);
        });

        put("/mensagens/:id", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            String titulo = request.queryParams("titulo");
            String conteudo = request.queryParams("conteudo");
            String autor = request.queryParams("autor"); // Recebe o nome do autor
        
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                MessageDAO messageDAO = new MessageDAO(connection);
                messageDAO.updateMessage(id, titulo, conteudo, autor); // Atualiza o autor também
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao editar a mensagem: " + e.getMessage());
            }
        
            response.status(200);
            return gson.toJson("Mensagem editada com sucesso!");
        });
        

        // DELETE para deletar uma mensagem
        delete("/mensagens/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                MessageDAO messageDAO = new MessageDAO(connection);
                boolean deleted = messageDAO.deleteMessage(id);

                if (deleted) {
                    response.status(200);
                    return gson.toJson("Mensagem deletada com sucesso!");
                } else {
                    response.status(404);
                    return gson.toJson("Mensagem não encontrada.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.status(500);
                return gson.toJson("Erro ao deletar a mensagem.");
            }
        });
    }
}
