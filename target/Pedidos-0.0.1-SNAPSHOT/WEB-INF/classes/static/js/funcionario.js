
const nomeUsers = document.querySelectorAll('.nomeUser');
const rgUsers = document.querySelectorAll('.rgUser');
const cpfUsers = document.querySelectorAll('.cpfUser');
const dataNascUsers = document.querySelectorAll('.dataNascUser');
const cepUsers = document.querySelectorAll('.cepUser');
const enderecoUsers = document.querySelectorAll('.enderecoUser');
const bairroUsers = document.querySelectorAll('.bairroUser');
const cidadeUsers = document.querySelectorAll('.cidadeUser');
const ufUsers = document.querySelectorAll('.ufUser');
const tel1Users = document.querySelectorAll('.tel1User');
const tel2Users = document.querySelectorAll('.tel2User');
const emailUsers = document.querySelectorAll('.emailUser');
const formsEdit = document.querySelectorAll('.formEdit')
const email2Users = document.querySelectorAll('.emailUser2');

const btnSubmits = document.querySelectorAll('.btnSubmit');



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


    // Validações
{
    // Validações gerais
    for (let i = 0; i < nomeUsers.length; i++) {
        console.log(i)
        const button = btnSubmits[i];

        cepUsers[i].addEventListener('change', function () {
            let cep = cepUsers[i].value.replace(/\D/g, ''); // Remove caracteres não numéricos

            if (cep.length === 8) {
                let url = 'https://viacep.com.br/ws/' + cep + '/json/';
                console.log(url)
                fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        if (!data.erro) {
                            enderecoUsers[i].value = data.logradouro;
                            bairroUsers[i].value = data.bairro;
                            cidadeUsers[i].value = data.localidade;
                            ufUsers[i].value = data.uf;


                        }
                    })
                    .catch(error => {
                        console.error('Erro ao buscar CEP:', error);

                    })


            }

        })

        nomeUsers[i].addEventListener('blur', function () {
            const nomeUser = nomeUsers[i];

            let nome = nomeUser.value.trim(); // Remove espaços em branco no início e no final

            if (nome === "") {
                exibirErro(nomeUser, "Nome é obrigatório.");
            } else {
                exibirSucesso(nomeUser, button);
            }


        })

            tel1Users[i].addEventListener('change', function () {
                const telUser = tel1Users[i];
                const regex = /^(\([0-9]{2}\)\s*|[0-9]{2}-)[0-9]{4,5}-[0-9]{4}$/;

                if (!regex.test(telUser.value)) {
                    exibirErro(telUser, "Telefone inválido. Por favor, insira um email válido.");
                } else {
                    exibirSucesso(telUser);
                }
            });


        {
            tel2Users[i].addEventListener('change', function () {
                const telUser = tel2Users[i];
                const regex = /^(\([0-9]{2}\)\s*|[0-9]{2}-)[0-9]{4,5}-[0-9]{4}$/;

                if (!regex.test(telUser.value)) {
                    exibirErro(telUser, "Telefone inválido. Por favor, insira um email válido.");
                } else {
                    exibirSucesso(telUser);
                }
            });

            tel2Users[i].addEventListener('blur', function () {
                const telUser = tel2Users[i];
                if (telUser.value === "") {
                    exibirSucesso(telUser);
                }
            });
        }
        {
            email2Users[i].addEventListener('change', function () {
                const emailUser = email2Users[i];
                const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

                if (!regex.test(emailUser.value)) {
                    exibirErro(emailUser, "Email inválido. Por favor, insira um email válido.");
                } else {
                    exibirSucesso(emailUser);
                }
            });

            email2Users[i].addEventListener('blur', function () {
                const emailUser = email2Users[i];
                if (emailUser.value === "") {
                    exibirSucesso(emailUser);
                }
            });
        }
        emailUsers[i].addEventListener('change', function () {
            const emailUser = emailUsers[i];
            const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

            if (!regex.test(emailUser.value)) {
                exibirErro(emailUser, "Email inválido. Por favor, insira um email válido.");
            } else {
                exibirSucesso(emailUser);
            }
        });

        rgUsers[i].addEventListener('change', function () {

            const rgUser = rgUsers[i];


            // Expressão regular para verificar o formato do RG (00.000.000-0)
            let regex = /^\d{2}\.\d{3}\.\d{3}-\d$/;

            // Testa o RG em relação à expressão regular
            if (!regex.test(rgUser.value)) {
                exibirErro(rgUser, "RG inválido. Por favor, insira um RG válido.")

            } else {
                exibirSucesso(rgUser)

            }
        })

        cpfUsers[i].addEventListener('change', function () {

            const cpfUser = cpfUsers[i];

            let cpf = cpfUser.value.replace(/\D/g, ''); // Remove caracteres não numéricos

            if (cpf.length !== 11 || cpf === "00000000000" || cpf === "11111111111" || cpf === "22222222222" ||
                cpf === "33333333333" || cpf === "44444444444" || cpf === "55555555555" ||
                cpf === "66666666666" || cpf === "77777777777" || cpf === "88888888888" || cpf === "99999999999") {
                // CPF com tamanho inválido ou composto por números repetidos é considerado inválido
                exibirErro(cpfUser, "CPF inválido. Por favor, insira um CPF válido.");
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
                        exibirSucesso(cpfUser);
                    } else {
                        // Segundo dígito verificador incorreto
                        exibirErro(cpfUser, "CPF inválido. Por favor, insira um CPF válido.");
                    }
                } else {
                    // Primeiro dígito verificador incorreto
                    exibirErro(cpfUser, "CPF inválido. Por favor, insira um CPF válido.");
                }


                if (TestaCPF(cpf) === false) {
                    exibirErro(cpfUser, "CPF inválido. Por favor, insira um CPF válido.");
                } else {
                    exibirSucesso(cpfUser);
                }
            }
        })

        dataNascUsers[i].addEventListener('change', function () {

            const dataNascUser = dataNascUsers[i];
            let dataNascimento = new Date(dataNascUser.value);
            let hoje = new Date();

            // Calcula a diferença de idade em anos
            let idade = hoje.getFullYear() - dataNascimento.getFullYear();

            // Verifique se o ano de nascimento é maior que o ano atual
            if (dataNascimento > hoje) {
                // Defina o ano de nascimento como o ano atual
                dataNascimento.setFullYear(hoje.getFullYear());

                // Atualize o valor do campo de entrada com o ano ajustado
                dataNascUser.valueAsDate = dataNascimento;
            }


            // Verifica se o aniversário ainda não ocorreu neste ano
            if (hoje.getMonth() < dataNascimento.getMonth() || (hoje.getMonth() === dataNascimento.getMonth() && hoje.getDate() < dataNascimento.getDate())) {
                idade--;
            }
            console.log(idade)
            if (idade < 18) {
                exibirErro(dataNascUser, "Você deve ter pelo menos 18 anos.");
            } else {
                exibirSucesso(dataNascUser);
            }


        })


        function exibirErro(element, mensagem) {
            let siblingP = element.parentElement.nextElementSibling;
            siblingP.style.display = "block";
            siblingP.textContent = mensagem;
            button.disabled = true;
        }

        function exibirSucesso(element) {
            let siblingP = element.parentElement.nextElementSibling;
            siblingP.style.display = "none";
            siblingP.textContent = "";
            button.disabled = false;
        }


    }

    //  Validação da senha
    {const pass = document.getElementById('senhaUsuario');
        const regex = /^(?=.*[!@#$%^&*()_+])(?=.*[A-Z])(?=.*\d.*\d.*\d)[A-Za-z0-9!@#$%^&*()_+]{8,}$/;

        pass.addEventListener('input', function () {
            if (!regex.test(pass.value) || hasSequentialNumbers(pass.value)) {
                const missingRequirements = [];

                if (pass.value.length < 8) {
                    missingRequirements.push("deve conter pelo menos 8 caracteres");
                }

                if (!/(?=.*[A-Z])/.test(pass.value)) {
                    missingRequirements.push("deve conter pelo menos 1 letra maiúscula");
                }

                if (!/(?=.*[!@#$%^&*()_+])/.test(pass.value)) {
                    missingRequirements.push("deve conter pelo menos 1 caractere especial (@,#,$,%,&,etc)");
                }

                if (!/(?=.*\d.*\d.*\d)/.test(pass.value)) {
                    missingRequirements.push("deve conter pelo menos 3 números");
                }

                if (hasSequentialNumbers(pass.value)) {
                    missingRequirements.push("não deve conter números sequenciais");
                }

                const errorMessage = `Senha inválida. A senha ${missingRequirements.join(", ")}.`;
                exibirErroSenha(pass, errorMessage);
            } else {
                exibirSucessoSenha(pass);
            }
        });

        function hasSequentialNumbers(password) {
            for (let i = 0; i < password.length - 2; i++) {
                if (
                    isDigit(password[i]) &&
                    isDigit(password[i + 1]) &&
                    isDigit(password[i + 2]) &&
                    (parseInt(password[i + 1]) - parseInt(password[i]) === 1) &&
                    (parseInt(password[i + 2]) - parseInt(password[i + 1]) === 1)
                ) {
                    return true; // Senha contém números sequenciais
                }
            }
            return false; // Senha não contém números sequenciais
        }

        function isDigit(char) {
            return /\d/.test(char);
        }

        function exibirErroSenha(element, mensagem) {
            let siblingP = element.parentElement.nextElementSibling;
            let button = document.getElementById('criarUser')
            siblingP.style.display = "block";
            siblingP.textContent = mensagem;
            button.disabled = true;
        }

        function exibirSucessoSenha(element) {
            let siblingP = element.parentElement.nextElementSibling;
            let button = document.getElementById('criarUser')
            siblingP.style.display = "none";
            siblingP.textContent = "";
            button.disabled = false;
        }




    }


    {
        const formCriar = document.getElementById('formCreate');

        formCriar.addEventListener('submit', function (event) {
            event.preventDefault();

            fetch('/api/admin/funcionarios/createFuncionario', {
                method: 'POST',
                body: new FormData(this)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        location.reload();
                    } else {
                        exibirErrorCreate(data.message);
                    }
                })
                .catch(error => {
                    console.error('Erro na requisição:', error);
                });
        });


        function exibirErrorCreate(mensagem) {
            let siblingP = document.getElementById('backMessage')
            siblingP.style.display = "block";
            siblingP.textContent = mensagem;

        }
    }

    {
        for (let i = 0; i < formsEdit.length; i++){
        const formEdit = formsEdit[i]

            formEdit.addEventListener('submit', function (event) {
                event.preventDefault();

                fetch('/api/admin/EditUsuario', {
                    method: 'POST',
                    body: new FormData(this)
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            location.reload();
                        } else {
                            exibirErrorCreate(data.message);
                        }
                    })
                    .catch(error => {
                        exibirErrorCreate('Erro na requisição:' + error);
                    });
            });

        }


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
{
    const togglePassword = document.querySelector("#togglePassword");
    const password = document.querySelector("#senhaUsuario");
    togglePassword.addEventListener("click", function () {
        // toggle the type attribute
        const type = password.getAttribute("type") === "password" ? "text" : "password";
        password.setAttribute("type", type);

        // toggle the icon
        this.classList.toggle("bi-eye");
    });
}