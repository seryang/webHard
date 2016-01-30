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
                        $(data).each(function(){
                            $("#showFile").append(
                                "<tr><td><a class='file-list' value='" + this.filePath  +"' style='cursor:pointer;'>" + this.fileName + "</a> </td><td>" + this.fileSize  + " KB		</td><td>" + this.fileDate + "</td><td>" + this.fileType+"</td></tr>");
                        });
                    }else{
                        alert("파일 업로드에 실패하였습니다.\n다시 시도해 주세요.");
                    }

                    $(".file-list").click(function(){
                        var filePath = $(this).attr("value");
                        var fileName = $(this).html();
                        location.href="downloadFile?filePath="+filePath+"&fileName="+fileName;
                    })
                },
                error : function() {
                    alert("[Error] 다시 시도해 주세요.")
                }
            });
        }
    }
}