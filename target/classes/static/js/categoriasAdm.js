const formsEdit = document.querySelectorAll('.formEdit')
const formsDelete = document.querySelectorAll('.formDelete')

for (let i = 0; i < formsEdit.length; i++) {

    formsEdit[i].addEventListener('submit', function () {

        mostrarModal();


    })
    formsDelete[i].addEventListener('submit', function () {
        mostrarModal();

    })


}

const formCreate = document.getElementById('formCreate')

formCreate.addEventListener('submit', function () {

    mostrarModal();

})


// Função para habilitar botões

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