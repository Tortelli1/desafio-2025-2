document.addEventListener("DOMContentLoaded", function () {
    const cpfInput = document.getElementById("cpf");
    const telefoneInput = document.getElementById("telefone");

    cpfInput.addEventListener("input", function () {
        let value = cpfInput.value.replace(/\D/g, ""); // Remove tudo que não é número
        if (value.length > 11) value = value.slice(0, 11);
        cpfInput.value = value
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    });

    telefoneInput.addEventListener("input", function () {
        let value = telefoneInput.value.replace(/\D/g, ""); // Remove tudo que não é número
        if (value.length > 11) value = value.slice(0, 11);
        telefoneInput.value = value
            .replace(/(\d{2})(\d)/, "($1) $2")
            .replace(/(\d{5})(\d{4})$/, "$1-$2");
    });
});
