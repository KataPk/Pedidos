function include(file, integrity) {

    let script = document.createElement('script');
    script.src = file;
    script.type = 'text/javascript';
    script.defer = true;
    script.crossOrigin = 'anonymous'
    script.integrity = integrity

    document.getElementsByTagName('head').item(0).appendChild(script);

}

function select() {

    let selectElement = document.getElementById("selectClients");
    let selectedValue = selectElement.value;
    console.log("valor selecionado: " + selectedValue)

    let pathname = window.location.pathname.split('/')
    console.log("path completa: " + pathname)
    let pathApi = pathname[1]
    console.log("path Api: " + pathApi)
    let pathUser = pathname[2]
    console.log("path User: " + pathUser)
    let pathPedido = pathname[3]
    console.log("path Pedido: " + pathPedido)
    let pathRestante = pathname[4]
    console.log("path Restante:" + pathRestante)

    if (selectedValue !== pathPedido) {

        window.location.href = "/api/user/" + selectedValue + "/" + pathRestante
    }


}



