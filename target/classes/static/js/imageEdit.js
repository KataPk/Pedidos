const fileInputs = document.querySelectorAll('.fileInput')

import {openDefaultEditor} from './pintura.js'
for (let i = 0; i < fileInputs.length; i++){
    let fileInput = fileInputs[i]

    fileInput.addEventListener('change', () =>{

        if (!fileInput.files.length) return;

        const editor = openDefaultEditor({
            imageCropAspectRatio: 1,
            src: fileInput.files[0]
        });

        editor.on('process', (imageState) =>{

            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(imageState.dest);

            fileInput.files = dataTransfer.files;
        })

    })

}