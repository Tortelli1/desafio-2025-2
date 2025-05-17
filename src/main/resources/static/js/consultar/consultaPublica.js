document.addEventListener("DOMContentLoaded", () => {
    const cpfInput = document.getElementById("cpf");
    cpfInput.addEventListener("input", function () {
        let value = cpfInput.value.replace(/\D/g, "");
        if (value.length > 11) value = value.slice(0, 11);
        cpfInput.value = value
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    });
});