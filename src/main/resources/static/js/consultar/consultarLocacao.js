document.addEventListener('DOMContentLoaded', () => {
    const botoesExcluir = document.querySelectorAll('.btn-excluir');

    botoesExcluir.forEach(botao => {
        botao.addEventListener('click', () => {
            const locacaoId = botao.getAttribute('data-id');

            if (confirm('Tem certeza que deseja excluir esta locação?')) {
                fetch(`/locacoes/deletar/${locacaoId}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        alert('Locação excluída com sucesso!');
                        location.reload();
                    } else {
                        alert('Erro ao excluir a locação.');
                    }
                })
                .catch(() => {
                    alert('Erro na comunicação com o servidor.');
                });
            }
        });
    });

    const botoesEditar = document.querySelectorAll('.btn-editar');

    botoesEditar.forEach(botao => {
        botao.addEventListener('click', () => {
            const locacaoId = botao.getAttribute('data-id');
            window.location.href = `/locacoes/editar/${locacaoId}`;
        });
    });
});
