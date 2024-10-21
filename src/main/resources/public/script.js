async function handleLogin(event) {
    event.preventDefault();

    var nome_usuario = document.getElementById('nome_usuario').value;
    var senha = document.getElementById('senha').value;

    try {
        // Faz a requisição para obter todos os usuários
        const response = await fetch('http://localhost:4567/usuarios', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Erro de rede: ' + response.status);
        }

        const data = await response.json(); // Recebe os dados do backend
        const usuario = data.find(usuario => usuario.nome_usuario === nome_usuario && usuario.senha === senha);

        if (usuario) {
            // Armazena o ID do usuário no localStorage
            localStorage.setItem("usuarioId", usuario.id);  // Aqui está o ID
            localStorage.setItem("nome_usuario", usuario.nome_usuario); // Nome de usuário
            localStorage.setItem("tipoUsuario", usuario.tipo); // Tipo do usuário

            // Redireciona com base no tipo de usuário
            if (usuario.tipo === 'admin') {
                window.location.href = './conteudo_staff.html'; // Página de admin
            } else if (usuario.tipo === 'staff') {
                window.location.href = './conteudo_staff.html'; // Página de staff
            } else if (usuario.tipo === 'cliente') {
                window.location.href = './conteudo.html'; // Página de cliente
            } else {
                alert('Tipo de usuário desconhecido');
            }
        } else {
            throw new Error('Usuário não encontrado');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Nome de usuário ou senha inválidos');
    }
}

// Adiciona o evento ao formulário
document.getElementById('loginForm').addEventListener('submit', handleLogin);
