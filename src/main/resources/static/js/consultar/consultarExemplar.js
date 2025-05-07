document.addEventListener('DOMContentLoaded', () => {
    const botoesExcluir = document.querySelectorAll('.btn-excluir');

    botoesExcluir.forEach(botao => {
        botao.addEventListener('click', () => {
            const exemplarId = botao.getAttribute('data-id'); // Obtém o ID do exemplar

            if (confirm('Tem certeza que deseja excluir este exemplar?')) {
                fetch(`/exemplar/deletar/${exemplarId}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        alert('Exemplar excluído com sucesso!');
                        location.reload();  // Recarrega a página após a exclusão
                    } else {
                        alert('Erro ao excluir o exemplar.');
                    }
                })
                .catch(() => {
                    alert('Erro na comunicação com o servidor.');
                });
            }
        });
    });
});
