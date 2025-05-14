document.addEventListener('DOMContentLoaded', () => {
    const botoesExcluir = document.querySelectorAll('.btn-excluir');

    botoesExcluir.forEach(botao => {
        botao.addEventListener('click', () => {
            const exemplarId = botao.getAttribute('data-id');

            if (confirm('Tem certeza que deseja excluir este exemplar?')) {
                fetch(`/exemplar/deletar/${exemplarId}`, {
                    method: 'DELETE'
                })
				.then(response => response.text().then(msg => {
					if (response.redirected) {
						window.location.href = response.url;
					} else if (response.ok) {
						alert(msg);
						location.reload();
					} else {
						alert(msg);
					}
				}))
				.catch(() => {
					alert('Erro na comunicação com o servidor.');
				});
            }
        });
    });
});
