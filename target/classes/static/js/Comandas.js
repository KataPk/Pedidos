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
