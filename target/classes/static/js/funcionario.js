


    // Máscaras para o add
{
    $(document).ready(function () {
        // Máscara para CEP
        $('#cep').mask('00000-000');

        // Máscara para CPF
        $('#cpfUsuario').mask('000.000.000-00', {reverse: true});

        // Máscara para RG
        $('#rgUsuario').mask('00.000.000-0', {reverse: true});

        // Máscara para telefone
        $('#telefone1').mask('(00) 0000-0000');
        $('#telefone2').mask('(00) 0000-0000');

        // Máscara para Número de residência
        $('#numResidUsuario').mask('000')

        // Máscara para UF
        let ufInput = $('#uf');

        // Configura um evento de entrada para o campo UF
        ufInput.on('change', function () {
            // Obtém o valor atual do campo em letras maiúsculas
            let ufValue = ufInput.val().toUpperCase();

            // Remove qualquer caractere que não seja uma letra
            ufValue = ufValue.replace(/[^A-Z]/g, '');

            // Limita o valor a 2 caracteres
            ufValue = ufValue.substring(0, 2);

            // Define o valor do campo com as modificações
            ufInput.val(ufValue);
        });
    });
}


    // busca cep para o add
{
    function buscarEndereco() {
        let cep = document.getElementById('cep').value.replace(/\D/g, ''); // Remove caracteres não numéricos
        if (cep.length === 8) {
            let url = 'https://viacep.com.br/ws/' + cep + '/json/';

            fetch(url)
                .then(response => response.json())
                .then(data => {
                    if (!data.erro) {
                        document.getElementById('enderecoUsuario').value = data.logradouro;
                        document.getElementById('BairroUsuario').value = data.bairro;
                        document.getElementById('cidade').value = data.localidade;
                        document.getElementById('uf').value = data.uf;
                    }
                })
                .catch(error => {
                    console.error('Erro ao buscar CEP:', error);
                });
        }
    }
}

//     Validar email
{
    function validarEmail(inputElement) {
        // Encontra o elemento <p> irmão do <input> dentro do mesmo elemento pai
        let siblingP = inputElement.parentElement.nextElementSibling;

        // Expressão regular para verificar o formato do email
        let regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

        // Testa o email em relação à expressão regular
        if (!regex.test(inputElement.value)) {
            // Define o estilo para exibir o elemento <p> abaixo do campo de entrada
            siblingP.style.display = "block";
            siblingP.textContent = "Email inválido. Por favor, insira um email válido.";
            siblingP.class = "text-danger"
            document.getElementById("criarUser").disabled = true
        } else {
            // Limpa o texto e esconde o elemento <p> se o email for válido
            siblingP.style.display = "none";
            siblingP.textContent = "";

            document.getElementById("criarUser").disabled = false

        }
    }
}

//      Validar RG
    {
        function validarRG(inputElement) {
            var rgValue = inputElement.value;
            var siblingP = inputElement.parentElement.nextElementSibling.querySelector(".text-danger");

            // Expressão regular para verificar o formato do RG (00.000.000-0)
            var regex = /^\d{2}\.\d{3}\.\d{3}-\d{1}$/;

            // Testa o RG em relação à expressão regular
            if (!regex.test(rgValue)) {
                // Define o estilo para exibir o elemento <p> abaixo do campo de RG
                siblingP.style.display = "block";
                siblingP.textContent = "RG inválido. Por favor, insira um RG válido.";

                // Desabilita o botão de envio do formulário
                document.getElementById("enviarBotao").disabled = true;
            } else {
                // Limpa o texto e esconde o elemento <p> se o RG for válido
                siblingP.style.display = "none";
                siblingP.textContent = "";

                // Habilita o botão de envio do formulário
                document.getElementById("enviarBotao").disabled = false;
            }
        }

    }

//      Validar CPF
    {
        function validarCPF(cpfElement) {
            let cpf = cpfElement.value.replace(/[^\d]/g, ''); // Remove caracteres não numéricos

            if (cpf.length !== 11 || cpf === "00000000000" || cpf === "11111111111" || cpf === "22222222222" ||
                cpf === "33333333333" || cpf === "44444444444" || cpf === "55555555555" ||
                cpf === "66666666666" || cpf === "77777777777" || cpf === "88888888888" || cpf === "99999999999") {
                // CPF com tamanho inválido ou composto por números repetidos é considerado inválido
                exibirErro(cpfElement, "CPF inválido. Por favor, insira um CPF válido.");
                alert("cpf")
            } else {
                // Calcula o primeiro dígito verificador
                let soma = 0;
                for (let i = 0; i < 9; i++) {
                    soma += parseInt(cpf.charAt(i)) * (10 - i);
                }
                let primeiroDigito = 11 - (soma % 11);

                if (primeiroDigito === 10 || primeiroDigito === 11) {
                    primeiroDigito = 0;
                }

                if (primeiroDigito === parseInt(cpf.charAt(9))) {
                    // Calcula o segundo dígito verificador
                    soma = 0;
                    for (let j = 0; j < 10; j++) {
                        soma += parseInt(cpf.charAt(j)) * (11 - j);
                    }
                    let segundoDigito = 11 - (soma % 11);

                    if (segundoDigito === 10 || segundoDigito === 11) {
                        segundoDigito = 0;
                    }

                    if (segundoDigito === parseInt(cpf.charAt(10))) {
                        // CPF válido
                        exibirSucesso(cpfElement);
                    } else {
                        // Segundo dígito verificador incorreto
                        exibirErro(cpfElement, "CPF inválido. Por favor, insira um CPF válido.");
                    }
                } else {
                    // Primeiro dígito verificador incorreto
                    exibirErro(cpfElement, "CPF inválido. Por favor, insira um CPF válido.");
                }
            }
        }

        function exibirErro(element, mensagem) {
            let siblingP = element.parentElement.nextElementSibling.querySelector(".text-danger");
            siblingP.style.display = "block";
            siblingP.textContent = mensagem;
            document.getElementById("enviarBotao").disabled = true;
        }

        function exibirSucesso(element) {
            let siblingP = element.parentElement.nextElementSibling.querySelector(".text-danger");
            siblingP.style.display = "none";
            siblingP.textContent = "";
            document.getElementById("enviarBotao").disabled = false;
        }
    }