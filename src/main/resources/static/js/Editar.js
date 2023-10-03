const lessButtons = document.querySelectorAll('.less');
const plusButtons = document.querySelectorAll('.plus');
const quantElements = document.querySelectorAll('.quant');
const precoElements = document.querySelectorAll('.preco');
const idElements = document.querySelectorAll('.id');
const totalElements = document.querySelectorAll('.total');
const removeButtons = document.querySelectorAll('.remover');
let valorTotal = document.querySelector('#total');

let arrayValue = document.querySelector('#itemArray')

let tempTotal = 0;

for (let i = 0; i < plusButtons.length; i++) {
    const price = parseFloat(precoElements[i].getAttribute('data-price'));
    let quantData = parseInt(quantElements[i].getAttribute('data-quant'));
    const pedidoId = parseInt(idElements[i].getAttribute('data-id'));

    quantElements[i].innerHTML = quantData.toString()
    let total = (price * quantData).toFixed(2).toString().replace('.', ',')

    totalElements[i].innerHTML = `Total: R$${total}`;

    plusButtons[i].addEventListener('click', function () {
        let quant = parseInt(quantElements[i].innerHTML);
        quantData++;
        quant = quantData
        quantElements[i].innerHTML = quant.toString();
        let total = (price * quant).toFixed(2).toString().replace('.', ',');
        totalElements[i].innerHTML = `Total: R$${total}`;
        tempTotal += price
        valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`

    });

    lessButtons[i].addEventListener('click', function () {
        let quant = parseInt(quantElements[i].innerHTML);
        if (quant > 0) {
            quantData--;
            quant = quantData
            quantElements[i].innerHTML = quant.toString();
            let total = (price * quant).toFixed(2).toString().replace('.', ',');
            totalElements[i].innerHTML = `Total: R$${total}`;
        }
        tempTotal -= price
        valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`
    });

    removeButtons[i].addEventListener('click', function () {
        const cardProduto = this.parentNode.parentNode.parentNode
        cardProduto.remove();
        tempTotal -= price * quantData;
        if (quantElements.length === 0) {
            tempTotal = 0;
            valorTotal.innerHTML = `Total: R$0,00`;
        } else {
            valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`;
        }
    });

    valorTotal.innerHTML = `R$${(price * quantData).toFixed(2).replace('.', ',')}`
    tempTotal += price * quantData
}

if (quantElements.length === 0) {
    tempTotal = 0;
    valorTotal.innerHTML = `Total: R$0,00`;
} else {
    valorTotal.innerHTML = `Total: R$${tempTotal.toFixed(2).replace('.', ',')}`;
}