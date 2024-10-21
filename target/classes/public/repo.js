document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");

    if (id) {
        // Faz a requisição para o endpoint correto do banco de dados
        fetch(`http://localhost:4567/data/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro na resposta do servidor");
                }
                return response.json();
            })
            .then(data => {
                // Preenchendo o título e o conteúdo com os dados do tópico
                document.getElementById("content-title").textContent = data.titulo;
                document.getElementById("content-title").style.fontWeight = "bold";

                let contentBody = '';

                // Verificando se o conteúdo inclui "Passo"
                if (data.conteudo && data.conteudo.includes("Passo")) {
                    const steps = data.conteudo.split('Passo');
                    contentBody += '<ul>';
                    steps.forEach((step, index) => {
                        if (index > 0 && step.trim() !== '') {
                            contentBody += `<li>Passo ${step.trim()}</li>`;
                        } else if (index === 0) {
                            contentBody += `<li>${step.trim()}</li>`;
                        }
                    });
                    contentBody += '</ul>';
                } else if (data.conteudo) {
                    contentBody = `<p>${data.conteudo}</p>`;
                } else {
                    contentBody = `<p>Conteúdo não disponível.</p>`;
                }

                document.getElementById("content-body").innerHTML = contentBody;

                // Carregando vídeos no carousel
                const carouselInner = document.getElementById("carouselInner");
                if (data.link && Array.isArray(data.link) && data.link.length > 0) {
                    data.link.forEach((link, index) => {
                        const slideDiv = document.createElement("div");
                        slideDiv.classList.add("carousel-item");
                        if (index === 0) {
                            slideDiv.classList.add("active");
                        }
                        slideDiv.innerHTML = `
                            <iframe width="100%" height="400px" src="${link}" frameborder="0" allowfullscreen></iframe>
                        `;
                        carouselInner.appendChild(slideDiv);
                    });
                } else {
                    carouselInner.innerHTML = `<p>Vídeos não disponíveis.</p>`;
                }
            })
            .catch(error => {
                console.error("Erro ao carregar conteúdo:", error);
                document.getElementById("content-body").textContent = "Erro ao carregar conteúdo.";
            });
    } else {
        document.getElementById("content-body").textContent = "ID do conteúdo não fornecido.";
    }
});
