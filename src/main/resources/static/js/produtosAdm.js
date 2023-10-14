const inputValues = document.querySelectorAll('.produtoValor');

{
    for (let i = 0; i < inputValues.length; i++) {
        const inputValue = inputValues[i];
        inputValue.addEventListener("input", function () {
            let valor = parseFloat(inputValue.value);

            if (valor === 0) {
                // Se o valor for 0, defina-o automaticamente como 1
                inputValue.value = "1";
            }

            if (valor > 1000) {

                inputValue.value = "1000"
            }

            // Arredonde o valor para duas casas decimais

        });


        inputValue.addEventListener("change", function () {
            let valor = parseFloat(inputValue.value);

            valor = valor.toFixed(2);
            // Defina o valor arredondado de volta no input
            inputValue.value = valor;
        });


    }


}