const formCriar = document.getElementById('formCreate');
const formsEdit = document.querySelectorAll('.formEdit')
const backMessages = document.querySelectorAll('.backMessage')
{
    formCriar.addEventListener('submit', function (event) {
        event.preventDefault();

        fetch('/api/admin/createMesa', {
            method: 'POST',
            body: new FormData(this)
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    exibirErrorCreate(data.message);
                }
            })
            .catch(error => {
                console.error('Erro na requisição:', error);
            });
    });


    function exibirErrorCreate(mensagem) {
        let siblingP = document.getElementById('backMessage')
        siblingP.style.display = "block";
        siblingP.textContent = mensagem;

    }
}

{
    for (let i = 0; i < formsEdit.length; i++) {
        const formEdit = formsEdit[i]

        formEdit.addEventListener('submit', function (event) {
            event.preventDefault();

            fetch('/api/admin/editMesa', {
                method: 'POST',
                body: new FormData(this)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        location.reload();
                    } else {
                        exibirErrorEdit(data.message);
                    }
                })
                .catch(error => {
                    exibirErrorEdit('Erro na requisição:' + error);
                });
        });


        function exibirErrorEdit(mensagem) {
            let siblingP = backMessages[i]
            siblingP.style.display = "block";
            siblingP.textContent = mensagem;

        }


    }


}















