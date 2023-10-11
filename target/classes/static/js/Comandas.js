const removeButtons = document.querySelectorAll('.remove');
const formRemover = document.querySelectorAll('.formRemove')


for (let i = 0; i< formRemover.length; i++){
formRemover[i].addEventListener('submit', function (event) {
    event.preventDefault();

    // desabilitarBotoes();
    // mostrarModal();

    fetch('/api/user/pedido/removePedido', {
        method: 'POST', body: new FormData(this)
    })
        .then(response => {
            if (response.ok) {
                const order = removeButtons[i].closest('.order')
                order.remove();

            } else {
                console.log('Ocorreu um erro durante a solicitação.')

            }
        })
        .catch(error => {
            console.error('Erro na solicitação:', error)
        })
    //     .finally(()=>{
    //     habilitarBotoes();
    //     fecharModal();
    //
    // })
    ;

});
}

// Função para mostrar o modal de processamento
function mostrarModal() {
    // Selecione o modal de processamento pelo seu ID (substitua pelo ID correto)
    $('#processingModal').modal('show')
}

// Função para fechar o modal de processamento
function fecharModal() {

    // Selecione o modal de processamento pelo seu ID (substitua pelo ID correto)
    $('#processingModal').modal('hide')
}
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
