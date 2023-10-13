const formsEdit = document.querySelectorAll('.formEdit')
const formsDelete = document.querySelectorAll('.formDelete')

for (let i = 0; i < formsEdit.length; i++) {

    formsEdit[i].addEventListener('submit', function (event) {

        mostrarModal();


    })
    formsDelete[i].addEventListener('submit', function (event) {

        mostrarModal();


    })


}

const formCreate = document.getElementById('formCreate')

formCreate.addEventListener('submit', function (event) {

    mostrarModal();

})


// Função para mostrar o modal de processamento
function mostrarModal() {
    // Selecione o modal de processamento pelo seu ID (substitua pelo ID correto)
    $('#processingModal').modal('show')
}

