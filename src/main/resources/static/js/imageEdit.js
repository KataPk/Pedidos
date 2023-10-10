document.addEventListener('DOMContentLoaded', function () {
    // Selecione todos os campos de entrada de imagem com a classe '.fileInput'
    let fileInputs = document.querySelectorAll('.fileInput');

    // Itere sobre todos os campos de entrada de imagem
    for (let i = 0; i < fileInputs.length; i++) {
        let input = fileInputs[i];
        let image = input.nextElementSibling.querySelector('img'); // Encontre a imagem relacionada a este campo
        let modal = new bootstrap.Modal(input.nextElementSibling.nextElementSibling, {}); // Encontre o modal relacionado

        // Adicione um ouvinte de eventos de alteração de arquivo para este campo de entrada de imagem
        input.addEventListener('change', function () {
            // Certifique-se de que um arquivo tenha sido selecionado
            if (input.files && input.files[0]) {
                let reader = new FileReader();

                // Carregue a imagem selecionada no elemento de imagem
                reader.onload = function (e) {
                    image.src = e.target.result;

                    // Ative o Cropper.js para a imagem carregada
                    let cropper = new Cropper(image, {
                        aspectRatio: 1, // Configuração para manter um formato quadrado
                        viewMode: 2,   // Configuração para ajustar a imagem dentro do contêiner
                    });

                    // Adicione um ouvinte de eventos ao botão "Crop" do modal
                    modal.querySelector('#crop').addEventListener('click', function () {
                        // Obtenha os dados cortados da imagem
                        let croppedData = cropper.getCroppedCanvas().toDataURL('image/jpeg');

                        // Substitua o valor do campo de entrada de imagem com os dados cortados
                        input.value = croppedData;

                        // Feche o modal
                        modal.hide();
                    });
                };

                // Leia o arquivo selecionado como uma URL de dados
                reader.readAsDataURL(input.files[0]);

                // Mostre o modal relacionado
                modal.show();
            }
        });
    }
});
