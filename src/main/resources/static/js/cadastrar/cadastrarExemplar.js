function selecionarFilme(button) {
    const filmeId = button.getAttribute("data-id");
    const filmeTitulo = button.getAttribute("data-titulo");
    const filmeIdInput = document.getElementById("filmeId");
    const filmeSelecionadoInput = document.getElementById("filmeSelecionado");

    if (filmeIdInput && filmeSelecionadoInput) {
        filmeIdInput.value = filmeId;
        filmeSelecionadoInput.value = filmeTitulo;
    }

    const modal = bootstrap.Modal.getInstance(document.getElementById('modalFilmes'));
    modal.hide();
}