

    const uploadFile = document.getElementById("upload_file");
    const uploadBtn = document.getElementById("upload_btn");
    const uploadText = document.getElementById("upload_text");

    uploadBtn.addEventListener("click", function () {
        uploadFile.click();
    });

    uploadFile.addEventListener("change", function() {

        console.log(uploadFile.value)
        console.log(uploadText.value)
        console.log(uploadFile.value)

        if(uploadFile.value) {
            uploadText.innerHTML = uploadFile.value;
        } else {
            uploadText.innerHTML = "Файл не загружен";
        }
    });

    
