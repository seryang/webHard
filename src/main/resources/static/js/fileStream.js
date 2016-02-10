/*
 업로드 할 파일 유무 체크
 */
function valid_fileCheck() {
    var filename = $("#upload-file-input").attr("value").split("\\")[2];
    if(filename != undefined){
        if (confirm(filename + " 파일을 업로드하시겠습니까?")) {
            $.ajax({
                url: "/uploadCheck",
                type: "POST",
                data: new FormData($("#upload-file-form")[0]),
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                success: function (data) {
                    if (data) {
                        if(confirm(filename + " 파일은 이미 존재합니다. 덮어쓰시겠습니까?")) {
                            uploadFromElement();
                            //upload_go();
                        }
                    }else{
                        uploadFromElement();
                        //upload_go();
                    }
                },
                error: function () {
                    alert("[Error] 다시 시도해 주세요.")
                }
            });
        }
    }
}


/*
 * 폴더 생성
 */
function addFolder() {
    var folderName = prompt("생성할 폴더명을 입력해주세요", "");
    if (folderName != "") {
        if (folderName != null) {
            $.ajax({
                url: "/addFolder",
                type: "POST",
                data: "folderName=" + encodeURIComponent(folderName),
                dataType: "text",
                success: function (data) {
                    alert(data);
                    if (data == "폴더가 생성되었습니다.") {
                        //$("#showFile").append("<tr><td class='folder-list' style='cursor:pointer'><img src='img/folder.jpg'/>" + folderName + "</td><td></td><td></td><td>폴더</td></tr>");
                        location.reload();
                    };
                },
                error: function () {
                    alert("[Error]다시 시도해 주세요.")
                }
            });
        }
    } else {
        alert("폴더명을 입력하세요.");
    }
}

function uploadFromElement() {
    var formData = new FormData($("#upload-file-form")[0]);
    upload_go(formData);
}

function uploadFromDragAndDrop(files) {
    var formData = new FormData(),
        length = files.length,
        i = 0;

    for (; i < length; i += 1) {
        formData.append('uploadfile', files[i]);
    }
    upload_go(formData);
}

/*
 * 파일 업로드
 */
function upload_go(formData){

    $.ajax({
        url: "/uploadFile",
        type: "POST",
        data: formData,
        //data: new FormData($("#upload-file-form")[0]),
        //data: new FormData(document.getElementById("upload-file-form")),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
            if (data) {
                alert("파일이 업로드 되었습니다.");
                location.reload();
            } else {
                alert("파일 업로드에 실패하였습니다.\n다시 시도해 주세요.");
            }
        },
        error: function () {
            alert("[Error] 다시 시도해 주세요.")
        }
    });
}

/*
 * 파일 삭제
 */
function deleteFile(){
    $.ajax({
        url: "/deleteFile",
        type: "POST",
        data: new FormData($("#upload-file-form")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
            if (data) {
                alert("파일이 업로드 되었습니다.");
                location.reload();
            } else {
                alert("파일 업로드에 실패하였습니다.\n다시 시도해 주세요.");
            }
        },
        error: function () {
            alert("[Error] 다시 시도해 주세요.")
        }
    });
}