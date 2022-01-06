$(document).ready(function () {


    $(document).on('click','.origin_tag_delete_btn', function (){
        const tagNo = $(this).attr('id');
        $.ajax({
            url: '/articles/removeTag',
            method: 'POST',
            dataType: 'json',
            data: {
                tagNo: tagNo,
                articleNo: $('#articleNo').val(),
            },
            success: function (data) {
                tagList = data;
                htmlStr = "";
                $('#origin-tag-list').html(""); //view창 비우기
                for (let i = 0; i < tagList.length; i++) {
                    htmlStr += '<div style="display: inline-block">';
                    htmlStr += "<li class='ori-tag-item'>" + tagList[i].name + "</li>";
                    htmlStr += "<span id=" + tagList[i].tagNo + " class='origin_tag_delete_btn'>X</span>";
                    htmlStr += "</div>";
                }
                console.log(htmlStr);
                $('#origin-tag-list').html(htmlStr);
            },
            error: function (e) {
                console.trace();
                reject(e);
            }
        });
    });



    // 게시글 수정 Form 엔터키로 등록 차단
    $('#updateForm').on("keydown", function (event) {
        if((event.keyCode || event.which()) == 13){
            event.preventDefault();
        }
    })

    // 게시글 수정
    $('#update_btn').on('click', function (){
        var children = $('#tag-list').children();
        let tagList = [];
        children.each(function (index,element){
            console.log($(element).text());
            tagList.push($(element).text());
        })
        $('#tags').val(tagList.join());
        $('#updateForm').submit();
    });


    //파일 삭제
    $(document).on('click', '.remove_file_btn', function () {
        const fileNo = $(this).parents('div').attr('id');
        console.log('삭제할 file 번호', fileNo);
        if(confirm("삭제된 파일은 복구되지 않습니다.") == true){
            $.ajax({
                url: '/removeFile',
                method: 'POST',
                dataType: 'json',
                data: {
                    fileNo: fileNo,
                    articleNo: $('#articleNo').val(),
                },
                success: function (data) {
                    fileList = data;
                    htmlStr = "";
                    $('#viewImage').html(""); //view창 비우기
                    for (let i = 0; i < fileList.length; i++) {
                        htmlStr += "<div id=" + fileList[i].no + ">";
                        htmlStr += "<img src='/images/" + fileList[i].systemFileName + "' width='200' height='200'/>";
                        htmlStr += "<input type='button' class='remove_file_btn' value='삭제'>";
                        htmlStr += "</div>";
                    }
                    console.log(htmlStr);
                    $('#viewImage').html(htmlStr);
                },
                error: function (e) {
                    console.trace();
                    reject(e);
                }
            });
        } else{

        }
    });


    // start tag
    var tag = {};
    var counter = 0;

    // 태그를 추가한다.
    function addTag(value) {
        tag[counter] = value; // 태그를 Object 안에 추가
        counter++; // counter 증가 삭제를 위한 del-btn 의 고유 id 가 된다.
    }

    // 최종적으로 서버에 넘길때 tag 안에 있는 값을 array type 으로 만들어서 넘긴다.
    function marginTag() {
        return Object.values(tag)
            .filter(function (word) {
                return word !== "";
            });
    }

    $("#tag")
        .on("keyup", function (e) {
            var self = $(this);
            console.log("keypress");

            // input 에 focus 되있을 때 엔터 및 스페이스바 입력시 구동
            if (e.key === "Enter" || e.keyCode == 32) {

                var tagValue = self.val(); // 값 가져오기

                // 값이 없으면 동작 안합니다.
                if (tagValue !== "") {

                    // 같은 태그가 있는지 검사한다. 있다면 해당값이 array 로 return 된다.
                    var result = Object.values(tag)
                        .filter(function (word) {
                            return word === tagValue;
                        })

                    // 태그 중복 검사
                    if (result.length == 0) {
                        $("#tag-list")
                            .append("<li class='tag-item'>" + tagValue + "<span class='del-btn' idx='" + counter + "'>X</span></li>");
                        addTag(tagValue);
                        self.val("");
                    } else {
                        alert("태그값이 중복됩니다.");
                    }
                }
                e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
            }
        });

    // 삭제 버튼
    // 삭제 버튼은 비동기적 생성이므로 document 최초 생성시가 아닌 검색을 통해 이벤트를 구현시킨다.
    $(document)
        .on("click", ".del-btn", function (e) {
            var index = $(this)
                .attr("idx");
            tag[index] = "";
            $(this)
                .parent()
                .remove();
        });

    // end tag
});