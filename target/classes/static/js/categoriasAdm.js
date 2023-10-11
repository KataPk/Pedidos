
const formsEdit = document.querySelectorAll('.formEdit')
const formsDelete = document.querySelectorAll('.formDelete')

for(let i = 0; i < formsEdit.length; i++){

    formsEdit[i].addEventListener('submit', function (event) {

        desabilitarBotoes();
        mostrarModal();
        event.preventDefault();
        fetch('/api/admin/editCategoria', {
            method: 'POST', body: new FormData(this)
        })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    console.log('Ocorreu um erro durante a solicitação.')

                }
            }).finally(() =>
        {
            habilitarBotoes();
            fecharModal();
        })
            .catch(error => {
                console.error('Erro na solicitação:', error)
            });


    })
    formsDelete[i].addEventListener('submit', function (event) {

        desabilitarBotoes();
        mostrarModal();
        event.preventDefault();
        fetch('/api/admin/disableCategoria', {
            method: 'POST', body: new FormData(this)
        })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    console.log('Ocorreu um erro durante a solicitação.')

                }
            }).finally(() =>
        {
            habilitarBotoes();
            fecharModal();
        })
            .catch(error => {
                console.error('Erro na solicitação:', error)
            });


    })


}

const formCreate = document.getElementById('formCreate')

formCreate.addEventListener('submit', function (event){

    desabilitarBotoes();
    mostrarModal();
    event.preventDefault();

    fetch('/api/admin/createCategoria', {
        method: 'POST', body: new FormData(this)
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                console.log('Ocorreu um erro durante a solicitação.')

            }
        }).finally(() =>
    {
        habilitarBotoes();
        fecharModal();
    })
        .catch(error => {
            console.error('Erro na solicitação:', error)
        });
})



function desabilitarBotoes() {
    // Selecione os botões que você deseja desabilitar (substitua pelo seletor correto)
    const botoes = document.querySelectorAll('.btn');
    // Desabilite os botões
    botoes.forEach(botao => {
        botao.disabled = true;
    });
}

// Função para habilitar botões
function habilitarBotoes() {
    // Selecione os botões que você deseja habilitar (substitua pelo seletor correto)
    const botoes = document.querySelectorAll('.btn');

    // Habilite os botões
    botoes.forEach(botao => {
        botao.disabled = false;
    });
}

// Função para mostrar o modal de processamento
function mostrarModal() {
    // Selecione o modal de processamento pelo seu ID (substitua pelo ID correto)
    $('#processingModal').modal('show')
}

// Função para fechar o modal de processamento
function fecharModal() {
    // const modal = new bootstrap.Modal(document.getElementById('processingModal'));
    //
    // // Feche o modal (você pode ocultá-lo definindo seu estilo de exibição como 'none')
    // modal._hideModal();

    $('#processingModal').modal('hide')
}