const forgotToast = document.getElementById('toastForgot');
const forgotTrigger = document.getElementById('forgotBtn');
const btnSend = document.getElementById('forgotBtn');
const email = document.getElementById('EmailForgot');
const btnForm = document.getElementById('SendForm');
const form = document.getElementById('login-form');

forgotTrigger.addEventListener('click', function () {
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(forgotToast);
    toastBootstrap.show();
});

btnForm.addEventListener('click', validateForm);

function validateForm(event) {
    event.preventDefault();
    const email = document.getElementById('emailLogin').value;
    const senha = document.getElementById('password').value;
    const data = {
        email: email,
        senha: senha
    };

    fetch('/api/v1/acessar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(function (response) {
            if (response.ok) {
                // Acesso autorizado, faça alguma ação
                window.location.href = "/mesas"; // Redirecionar para a página de mesas
            } else {
                // Acesso não autorizado, exiba uma mensagem de erro
                const errorMsg = document.getElementById('error-msg');
                errorMsg.innerHTML = "E-mail ou senha incorretos.";
            }
        })
        .catch(function (error) {
            // Ocorreu um erro na solicitação
            console.error('Erro na solicitação', error);
        });
}
