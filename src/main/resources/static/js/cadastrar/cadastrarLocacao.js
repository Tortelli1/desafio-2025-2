document.addEventListener("DOMContentLoaded", function () {
    const cpfInput = document.getElementById("cpf");
    const telefoneInput = document.getElementById("telefone");

    cpfInput.addEventListener("input", function () {
        let value = cpfInput.value.replace(/\D/g, "");
        if (value.length > 11) value = value.slice(0, 11);
        cpfInput.value = value
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    });

    telefoneInput.addEventListener("input", function () {
        let value = telefoneInput.value.replace(/\D/g, "");
        if (value.length > 11) value = value.slice(0, 11);
        telefoneInput.value = value
            .replace(/(\d{2})(\d)/, "($1) $2")
            .replace(/(\d{5})(\d{4})$/, "$1-$2");
    });
	
	const selectExemplar = document.getElementById('exemplar');

	    selectExemplar.addEventListener('change', function () {
	        const selectedOptions = Array.from(selectExemplar.selectedOptions);

	        if (selectedOptions.length > 3) {
	            alert("Você pode selecionar no máximo 3 exemplares.");
	            
	            selectedOptions[selectedOptions.length - 1].selected = false;
	        }
	    });
});
