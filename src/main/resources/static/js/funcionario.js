


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