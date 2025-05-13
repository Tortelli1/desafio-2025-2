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

	const selectFiltro = document.getElementById('filtro');
	const inputValor = document.getElementById('valorFiltro');
	const formFiltro = document.getElementById('form-filtro');

	function aplicarMascaraCPF(valor) {
		return valor
			.replace(/\D/g, '')
			.replace(/(\d{3})(\d)/, '$1.$2')
			.replace(/(\d{3})(\d)/, '$1.$2')
			.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
	}

	selectFiltro.addEventListener('change', () => {
		inputValor.disabled = false;
		inputValor.value = '';
		inputValor.placeholder = `Digite o ${selectFiltro.options[selectFiltro.selectedIndex].text.toLowerCase()}...`;
	});

	inputValor.addEventListener('input', () => {
		if (selectFiltro.value === 'cpf') {
			inputValor.value = aplicarMascaraCPF(inputValor.value);
		}
	});

	formFiltro.addEventListener('submit', (e) => {
		e.preventDefault();
		const campo = selectFiltro.value;
		const valor = inputValor.value.trim();

		if (!campo || !valor) {
			alert('Selecione um filtro e informe um valor.');
			return;
		}

		const url = `/locacoes/consultar?${campo}=${encodeURIComponent(valor)}`;
		window.location.href = url;
	});
});
