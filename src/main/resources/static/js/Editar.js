const lessButtons = document.querySelectorAll('.less');
const plusButtons = document.querySelectorAll('.plus');
const quantElements = document.querySelectorAll('.quant');
const totalElements = document.querySelectorAll('.total');
const removeButtons = document.querySelectorAll('.remover');
let valorTotal = document.querySelector('#total');

let tempTotal = 0;

for (let i = 0; i < plusButtons.length; i++) {
    const price = parseFloat(quantElements[i].getAttribute('data-price'));
    let quantData = parseInt(quantElements[i].getAttribute('data-quant'))

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
        console.log(tempTotal)
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
        console.log(tempTotal)
        valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`
    });

    removeButtons[i].addEventListener('click', function () {
        const cardProduto = this.parentNode.parentNode.parentNode
        cardProduto.remove();
        tempTotal -= price * quantData;
        console.log(tempTotal);
        if (quantElements.length === 0) {
            tempTotal = 0;
            valorTotal.innerHTML = `Total: R$0,00`;
        } else {
            valorTotal.innerHTML = `Total: R$${(tempTotal).toFixed(2).replace('.', ',')}`;
        }
    });

    valorTotal.innerHTML = `R$${(price * quantData).toFixed(2).replace('.', ',')}`
    tempTotal += price * quantData
    console.log(tempTotal + 'temp')
}

if (quantElements.length === 0) {
    tempTotal = 0;
    valorTotal.innerHTML = `Total: R$0,00`;
} else {
    valorTotal.innerHTML = `Total: R$${tempTotal.toFixed(2).replace('.', ',')}`;
}
