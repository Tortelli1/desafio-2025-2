document.addEventListener("DOMContentLoaded", function() {
    const buscarExemplarInput = document.getElementById("buscarFilme");
    const listaExemplaresDiv = document.getElementById("lista-filmes");
    const formLocacao = document.getElementById("formLocacao");

    function exibirExemplares(exemplares) {
        listaExemplaresDiv.innerHTML = '';
        exemplares.forEach(exemplar => {
            const div = document.createElement("div");
            div.classList.add("list-group-item");
            div.textContent = `${exemplar.filmeTitulo} - ID: ${exemplar.id}`;
            div.addEventListener("click", () => {
                buscarExemplarInput.value = exemplar.filmeTitulo;
                formLocacao.exemplarId.value = exemplar.id;
                listaExemplaresDiv.innerHTML = '';
            });
            listaExemplaresDiv.appendChild(div);
        });
    }

    function buscarExemplares(query) {
        if (!query) {
            listaExemplaresDiv.innerHTML = ''; 
            return;
        }

        fetch(`/exemplares/buscar?titulo=${query}`)
            .then(response => response.json())
            .then(exemplares => {
                exibirExemplares(exemplares);
            })
            .catch(err => {
                console.error('Erro ao buscar exemplares', err);
            });
    }

    buscarExemplarInput.addEventListener("input", function(event) {
        const query = event.target.value.trim();
        buscarExemplares(query);
    });
});
