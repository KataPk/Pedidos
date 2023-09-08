const toastLiveExample = document.getElementById('liveToast')
const lessButtons = document.querySelectorAll('.less');
const plusButtons = document.querySelectorAll('.plus');
const quantElements = document.querySelectorAll('.quant');
const totalElements = document.querySelectorAll('.total');
const addButtons = document.querySelectorAll('.liveToastBtn')

function toast() {
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
    toastBootstrap.show()
}

for (let i = 0; i < plusButtons.length; i++) {
    const price = parseFloat(quantElements[i].getAttribute('data-price'));
    addButtons[i].addEventListener('mouseover', function () {
        let quant = parseInt(quantElements[i].innerHTML);

        if (quant <= 0) {
            addButtons[i].setAttribute('disabled', true);
            addButtons[i].removeAttribute('onclick')
            addButtons[i].removeAttribute('data-bs-dismiss')
            addButtons[i].removeAttribute('data-bs-delay')

        }
        if (quant > 0) {
            addButtons[i].removeAttribute('disabled')
            addButtons[i].setAttribute('data-bs-dismiss', 'modal')
            addButtons[i].setAttribute('data-bs-delay', '3000')
            addButtons[i].setAttribute('onclick', 'toast()')
        }

    })

    plusButtons[i].addEventListener('click', function () {
        let quant = parseInt(quantElements[i].innerHTML);
        quant += 1;
        quantElements[i].innerHTML = quant.toString();
        addButtons[i].removeAttribute('disabled')

        let total = (price * quant).toFixed(2).toString().replace('.', ',');
        totalElements[i].innerHTML = `Total: R$${total}`;
    });

    lessButtons[i].addEventListener('click', function () {
        let quant = parseInt(quantElements[i].innerHTML);


        if (quant > 0) {
            quant -= 1;
            quantElements[i].innerHTML = quant.toString();
            let total = (price * quant).toFixed(2).toString().replace('.', ',');
            totalElements[i].innerHTML = `Total: R$${total}`;
        }

    });


}
