$(document).ready(function () {
    let dlFlg = $('#downloadflg').val();

    if (dlFlg == 1) {
        $("a#downloadLink").removeClass("btn-outline-secondary");

        $("a#downloadLink").addClass("btn-secondary");
        $("a#downloadLink").css('pointer-events', 'none').attr('tabindex', -1);
        $("a#downloadLink").attr("disable", "disabled");
    }
});

function downloadImg() {
    let fileName = $('#fileName').val();
    let link = document.getElementById("downloadLink");
    link.download = fileName;

    document.pictConvFileDelForm.submit();
}

function reselect() {
    document.pictConvReTopForm.submit();
}

function validateFile() {
    const fileInput = document.getElementById('inputGroupFile04');
    const uploadButton = document.getElementById('upliadFile');
        
    if (fileInput.files.length === 0) {
        uploadButton.disabled = true;
        return;
    }

    const fileName = fileInput.files[0].name;
    const validExtensions = ['.jpeg', '.jpg', '.png'];

    const fileExtension = fileName.slice(((fileName.lastIndexOf(".") - 1) >>> 0) + 2);
        
    if (validExtensions.includes('.' + fileExtension.toLowerCase())) {
        uploadButton.disabled = false;
    } else {
        uploadButton.disabled = true;
        alert('アップロードできるファイルは「.jpeg, .jpg」または 「.png」のみです。');
    }
}