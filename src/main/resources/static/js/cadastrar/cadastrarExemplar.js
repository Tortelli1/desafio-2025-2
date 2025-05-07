function selecionarFilme(button) {
    const filmeId = button.getAttribute("data-id");
    const filmeTitulo = button.getAttribute("data-titulo");

    // Defina os valores nos inputs corretos
    const filmeIdInput = document.getElementById("filmeId");
    const filmeSelecionadoInput = document.getElementById("filmeSelecionado");

    if (filmeIdInput && filmeSelecionadoInput) {
        filmeIdInput.value = filmeId;
        filmeSelecionadoInput.value = filmeTitulo;
    }

    // Fecha o modal
    const modal = bootstrap.Modal.getInstance(document.getElementById('modalFilmes'));
    modal.hide();
}