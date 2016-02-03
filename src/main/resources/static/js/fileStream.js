// 파일 업로드
function uploadFile() {
    var filename = $("#upload-file-input").attr("value").split("\\")[2];
    if(filename != undefined){
        if (confirm(filename + " 파일을 업로드하시겠습니까?")) {
            $.ajax({
                url : "/uploadFile",
                type : "POST",
                data : new FormData($("#upload-file-form")[0]),
                enctype : 'multipart/form-data',
                processData : false,
                contentType : false,
                cache : false,
                success : function(data) {
                    if(data != null){
                        alert("파일이 업로드 되었습니다.");
                        //$(data).each(function(){
                        //    $("#showFile").append(
                        //        "<tr><td class='file-list' th:value='"+this.fileName+"'style='cursor:pointer;'>" + this.fileName + "</td><td>" + this.fileSize  + " KB		</td><td>" + this.fileDate + "</td><td>" + this.fileType+"</td></tr>");
                        //});
                        location.reload();
                    }else{
                        alert("파일 업로드에 실패하였습니다.\n다시 시도해 주세요.");
                    }
                },
                error : function() {
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