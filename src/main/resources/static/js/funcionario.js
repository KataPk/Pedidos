


    // Máscaras para o add
{
    $(document).ready(function () {
        // Máscara para CEP
        $('.cepUser').mask('00000-000');

        // Máscara para CPF
        $('.cpfUser').mask('000.000.000-00', {reverse: true});

        // Máscara para RG
        $('.rgUser').mask('00.000.000-0', {reverse: true});

        // Máscara para telefone
        $('.telUser').mask('(00) 00000-0000');

        // Máscara para Número de residência
        $('.numResidUser').mask('000')


    });
    // Máscara para UF
    function maskUf(ufInput) {
        // Obtém o valor atual do campo em letras maiúsculas
        let ufValue = ufInput.value.toUpperCase();

        // Remove qualquer caractere que não seja uma letra
        ufValue = ufValue.replace(/[^A-Z]/g, '');

        // Limita o valor a 2 caracteres
        ufValue = ufValue.substring(0, 2);

        // Define o valor do campo com as modificações
        ufInput.value = ufValue;

    }
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

//  Validar Nome
    {
        function validarNome(nomeElement, buttonElement) {
            let nome = nomeElement.value.trim(); // Remove espaços em branco no início e no final

            if (nome === "") {
                exibirErro(nomeElement, "Nome é obrigatório.", buttonElement);
            } else {
                exibirSucesso(nomeElement, buttonElement);
            }
        }

    }

//     Validar email
{
    function validarEmail(inputElement, buttonElement) {
        // Encontra o elemento <p> irmão do <input> dentro do mesmo elemento pai
        let siblingP = inputElement.parentElement.nextElementSibling;

        // Expressão regular para verificar o formato do email
        let regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

        // Testa o email em relação à expressão regular
        if (!regex.test(inputElement.value)) {
            exibirErro(inputElement, "Email inválido. Por favor, insira um email válido.", buttonElement )

        } else {
            exibirSucesso(inputElement, buttonElement)
        }
    }
}

//      Validar RG
    {
        function validarRG(inputElement, buttonElement) {
            let rgValue = inputElement.value;

            // Expressão regular para verificar o formato do RG (00.000.000-0)
            let regex = /^\d{2}\.\d{3}\.\d{3}-\d$/;

            // Testa o RG em relação à expressão regular
            if (!regex.test(rgValue)) {
               exibirErro(inputElement, "RG inválido. Por favor, insira um RG válido.", buttonElement )


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
            let cpf = cpfElement.value.replace(/\D/g, ''); // Remove caracteres não numéricos

            if (cpf.length !== 11 || cpf === "00000000000" || cpf === "11111111111" || cpf === "22222222222" ||
                cpf === "33333333333" || cpf === "44444444444" || cpf === "55555555555" ||
                cpf === "66666666666" || cpf === "77777777777" || cpf === "88888888888" || cpf === "99999999999") {
                // CPF com tamanho inválido ou composto por números repetidos é considerado inválido
                exibirErro(cpfElement, "CPF inválido. Por favor, insira um CPF válido.");
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


                if (TestaCPF(cpf) === false){
                    exibirErro(cpfElement, "CPF inválido. Por favor, insira um CPF válido.");
                } else {
                    exibirSucesso(cpfElement);
                }
            }
        }

        function exibirErro(element, mensagem, botao) {
            let siblingP = element.parentElement.nextElementSibling;
            siblingP.style.display = "block";
            siblingP.textContent = mensagem;
            botao.disabled = true;
        }

        function exibirSucesso(element, botao) {
            let siblingP = element.parentElement.nextElementSibling;
            siblingP.style.display = "none";
            siblingP.textContent = "";
            botao.disabled = false;
        }

        function TestaCPF(strCPF) {
            let Soma;
            let Resto;
            Soma = 0;
            if (strCPF === "00000000000") return false;

            for (let i=1; i<=9; i++) Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (11 - i);
            Resto = (Soma * 10) % 11;

            if ((Resto === 10) || (Resto === 11))  Resto = 0;
            if (Resto !== parseInt(strCPF.substring(9, 10)) ) return false;

            Soma = 0;
            for (let i = 1; i <= 10; i++) Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (12 - i);
            Resto = (Soma * 10) % 11;

            if ((Resto === 10) || (Resto === 11))  Resto = 0;
            return Resto === parseInt(strCPF.substring(10, 11));

        }
    }

    // Validar Idade
    {
        function validarIdade(dataNascElement) {
            let dataNascimento = new Date(dataNascElement.value);
            let hoje = new Date();

            // Calcula a diferença de idade em anos
            let idade = hoje.getFullYear() - dataNascimento.getFullYear();

            // Verifica se o aniversário ainda não ocorreu neste ano
            if (hoje.getMonth() < dataNascimento.getMonth() || (hoje.getMonth() === dataNascimento.getMonth() && hoje.getDate() < dataNascimento.getDate())) {
                idade--;
            }

            if (idade < 18) {
                exibirErro(dataNascElement, "Você deve ter pelo menos 18 anos.");
            } else {
                exibirSucesso(dataNascElement);
            }
        }

    }