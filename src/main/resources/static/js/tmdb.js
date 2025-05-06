document.addEventListener('DOMContentLoaded', function () {
    const buscaTituloInput = document.getElementById('buscaTitulo');
    const sugestoesDiv = document.getElementById('sugestoes');

    buscaTituloInput.addEventListener('input', function () {
        const query = buscaTituloInput.value.trim();

        if (query.length >= 3) {
            fetch(`/filmes/buscar-filmes?titulo=${encodeURIComponent(query)}`)
                .then(response => response.json())
                .then(data => {
                    if (Array.isArray(data) && data.length > 0) {
                        mostrarSugestoes(data);
                    } else {
                        sugestoesDiv.innerHTML = '<p class="list-group-item">Nenhum filme encontrado</p>';
                    }
                })
                .catch(error => {
                    console.error('Erro ao buscar filmes:', error);
                    sugestoesDiv.innerHTML = '<p class="list-group-item text-danger">Erro na busca</p>';
                });
        } else {
            sugestoesDiv.innerHTML = '';
        }
    });

	function mostrarSugestoes(filmes) {
        sugestoesDiv.innerHTML = '';
        filmes.forEach(filme => {
            const sugestaoItem = document.createElement('a');
            sugestaoItem.classList.add('list-group-item', 'list-group-item-action');
            sugestaoItem.textContent = filme.titulo;
            sugestaoItem.addEventListener('click', function () {
                preencherFormulario(filme);
                sugestoesDiv.innerHTML = '';
            });
            sugestoesDiv.appendChild(sugestaoItem);
        });
    }

    function preencherFormulario(filme) {
        document.getElementById('titulo').value = filme.titulo || '';
        document.getElementById('resumo').value = filme.resumo || '';
        document.getElementById('pontuacao').value = filme.pontuacao || '';
		const data = new Date(filme.lancamento);
        const lancamentoFormatado = data.toISOString().split('T')[0];
        document.getElementById('lancamento').value = lancamentoFormatado;
    }

});
