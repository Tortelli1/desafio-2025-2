document.addEventListener('DOMContentLoaded', () => {
    const botoesExcluir = document.querySelectorAll('.btn-excluir');

    botoesExcluir.forEach(botao => {
        botao.addEventListener('click', () => {
            const filmeId = botao.getAttribute('data-id');

            if (confirm('Tem certeza que deseja excluir este filme?')) {
                fetch(`/filmes/deletar/${filmeId}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.redirected) {
                        window.location.href = response.url;
                    } else if (response.ok) {
                        location.reload();
                    } else {
                        alert('Erro ao excluir o filme.');
                    }
                })
                .catch(() => {
                    alert('Erro na comunicação com o servidor.');
                });
            }
        });
    });
});
