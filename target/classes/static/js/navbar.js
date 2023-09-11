function include(file, integrity) {

    let script = document.createElement('script');
    script.src = file;
    script.type = 'text/javascript';
    script.defer = true;
    script.crossOrigin = 'anonymous'
    script.integrity = integrity

    document.getElementsByTagName('head').item(0).appendChild(script);

}



