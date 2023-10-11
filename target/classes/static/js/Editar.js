const lessButtons = document.querySelectorAll('.less');
const plusButtons = document.querySelectorAll('.plus');
const quantElements = document.querySelectorAll('.quant');
const precoElements = document.querySelectorAll('.preco');
const idElements = document.querySelectorAll('.id');
const totalElements = document.querySelectorAll('.total');
const removeButtons = document.querySelectorAll('.remover');
const formRemover = document.querySelectorAll('.formRemove')
let valorTotal = document.querySelector('#total');
const formPlus = document.querySelectorAll('.formPlus')
const formLess = document.querySelectorAll('.formLess')
const modalRemover = document.querySelectorAll('.modalRemove')
const buttonFinish = document.querySelector('#finish')

let tempTotal = 0;

for (let i = 0; i < plusButtons.length; i++) {
    const price = parseFloat(precoElements[i].getAttribute('data-price'));
    let quantData = parseInt(quantElements[i].getAttribute('data-quant'));
    quantElements[i].innerHTML = quantData.toString()
    let total = (price * quantData).toFixed(2).toString().replace('.', ',')

    totalElements[i].innerHTML = `Total: R$${total}`;
    formPlus[i].addEventListener('submit', function (event) {

        desabilitarBotoes();
        mostrarModal();

        event.preventDefault();

        fetch('/api/user/pedido/alterarQuant', {
            method: 'POST', body: new FormData(this)
        })
            .then(response => {
                if (response.ok) {
                    let quant = parseInt(quantElements[i].innerHTML);
                    quantData++;
                    quant = quantData
                    quantElements[i].innerHTML = quant.toString();
                    let total = (price * quant).toFixed(2).toString().replace('.', ',');
                    totalElements[i].innerHTML = `Total: R$${total}`;
                    tempTotal += price
                    valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`


                } else {
                    console.log('Ocorreu um erro durante a solicitação.')

                }
            }).finally(() =>
        {
            habilitarBotoes();
            fecharModal();
        }).catch(error => {
                console.error('Erro na solicitação:', error)
            });


    })

    formLess[i].addEventListener('submit', function (event) {

        desabilitarBotoes();
        mostrarModal();
        event.preventDefault();

        let quant = parseInt(quantElements[i].innerHTML);
        if (quant > 1) {
            fetch('/api/user/pedido/alterarQuant', {
                method: 'POST', body: new FormData(this)
            })
                .then(response => {
                    if (response.ok) {

                        quantData--;
                        quant = quantData
                        quantElements[i].innerHTML = quant.toString();
                        let total = (price * quant).toFixed(2).toString().replace('.', ',');
                        totalElements[i].innerHTML = `Total: R$${total}`;

                        tempTotal -= price
                        valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`


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
        } else {
            const modal = new bootstrap.Modal(modalRemover[i])
            modal.show();
        }


    })

    // plusButtons[i].addEventListener('click', function () {
    //     let quant = parseInt(quantElements[i].innerHTML);
    //     quantData++;
    //     quant = quantData
    //     quantElements[i].innerHTML = quant.toString();
    //     let total = (price * quant).toFixed(2).toString().replace('.', ',');
    //     totalElements[i].innerHTML = `Total: R$${total}`;
    //     tempTotal += price
    //     valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`
    //
    //
    // });

    // lessButtons[i].addEventListener('click', function () {
    //     let quant = parseInt(quantElements[i].innerHTML);
    //     if (quant > 0) {
    //         quantData--;
    //         quant = quantData
    //         quantElements[i].innerHTML = quant.toString();
    //         let total = (price * quant).toFixed(2).toString().replace('.', ',');
    //         totalElements[i].innerHTML = `Total: R$${total}`;
    //     }
    //     tempTotal -= price
    //     valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`
    // });

    formRemover[i].addEventListener('submit', function (event) {
        event.preventDefault();

        fetch('/api/user/pedido/removeItem', {
            method: 'POST', body: new FormData(this)
        })
            .then(response => {
                if (response.ok) {
                    const cardProduto = removeButtons[i].parentNode.parentNode.parentNode.parentNode
                    cardProduto.remove();
                    tempTotal -= price * quantData;
                    if (quantElements.length === 0) {
                        tempTotal = 0;
                        valorTotal.innerHTML = `Total: R$0,00`;
                    } else {
                        valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`;
                    }
                } else {
                    console.log('Ocorreu um erro durante a solicitação.')

                }
            })
            .catch(error => {
                console.error('Erro na solicitação:', error)
            });

    });

    valorTotal.innerHTML = `R$${(price * quantData).toFixed(2).replace('.', ',')}`
    tempTotal += price * quantData
}

if (quantElements.length === 0) {
    tempTotal = 0;
    valorTotal.innerHTML = `Total: R$0,00`;
    buttonFinish.disabled = true
} else {
    valorTotal.innerHTML = `Total: R$${tempTotal.toFixed(2).replace('.', ',')}`;
    buttonFinish.disabled = false

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
