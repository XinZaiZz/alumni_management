
    var flag = 1;
    var aCategory = "";

    $('#originalAuthorHide').hide();
    $('.articleUrlHide').hide();

    var fnClose = function(e){
        e.returnValue = '确定离开当前页面吗?';
    };
    window.addEventListener('beforeunload',fnClose);

    var testEditor;
    $(function() {
        testEditor = editormd("my-editormd", { //注意1：这里的就是上面的DIV的id属性值
                width: "100%",
                height: 740,
                syncScrolling: true, //设置双向滚动
                path: "../../../lib/", //lib目录的路径
                previewTheme : "dark", //代码块使用dark主题
                codeFold : true,
                emoji:true,
                tocm : true, // Using [TOCM]
                tex : true, // 开启科学公式TeX语言支持，默认关闭
                flowChart : true, // 开启流程图支持，默认关闭
                sequenceDiagram : true, // 开启时序/序列图支持，默认关闭,
                htmlDecode : true, //不过滤标签
                imageUpload : true, //上传图片
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp","JPG","JPEG","GIF","PNG","BMP","WEBP"],
                imageUploadURL : "/uploadImage",
                onload:function () {
                    // console.log('onload', this);
                },
                saveHTMLToTextarea: true, //注意3：这个配置，方便post提交表单
                toolbarIcons : function () {
                    return ["bold","del","italic","quote","|","h1","h2","h3","h4","h5","h6","|","list-ul","list-ol","hr","|","link","image","code","code-block","table","datetime","html-entities","emoji","|","watch","preview","fullscreen","clear","search","|","help","info"]
                }
            });

        });

    var articleTitle = $('#zhy-editor-title');//文章标题
    var articleContent = $('#my-editormd-html-code');//文章内容
    var noticeBoxTitle = $('.notice-box-title');
    var noticeBoxContent = $('.notice-box-content');
    var noticeBox = $('.notice-box');
    let uploadFile = $("#uploadFile");//上传文件
    let uploadImage = $("#uploadImage");


    // 插入标签
    var addTagsBtn = $('.addTagsBtn');
    $(function() {
        var i = 0;
        var $wrapper = $('.tag');
        var appendPanel = function(index) {
            var panel = $('<div style="display: inline-block;"><p class="tag-name" contenteditable="true"></p>' +
                '<i class="glyphicon glyphicon-remove removeTag" style="color: #CCCCCC"></i></div>');
            $wrapper.append(panel);
            $('.tag-name').click(function () {
                $(this).focus();
            });
        };
        addTagsBtn.on('click', function() {
            if(i >= 4){
                addTagsBtn.attr('disabled','disabled');
            }
            var value=$('.tag-name').eq(i-1).html();
           if(value != ""){
               appendPanel(i);
               i++;
               console.log(i)
           }
        });

        $('.tag').on('click','.removeTag',function () {
            $(this).parent().remove();
            i--;
            if(i <= 4){
                addTagsBtn.removeAttr('disabled');
            }
            console.log(i)
        });

    });

    // 显示文章作者
    /*var articleType = $('#select-type');
    $('#select-type').blur(function () {
        if(articleType.val() == "转载"){
            $('#originalAuthorHide').show();
            $('.articleUrlHide').show();
        } else if (articleType.val() == "原创"){
            $('#originalAuthorHide').hide();
            $('.articleUrlHide').hide();
        }
    });*/

    // 发表博客
    let surePublishBtn = $('.surePublishBtn');


    // var articleGrade = $('#select-grade');
    // var originalAuthor = $('#originalAuthor');
    // var articleUrl = $('#articleUrl');
    surePublishBtn.click(function () {
        let uploadImageVal = uploadImage.val();
        let imageFileType = uploadImageVal.substring(uploadImageVal.lastIndexOf('.') + 1).toLowerCase();
        if (imageFileType === 'jpg' || imageFileType === 'png' || typeof(uploadImage[0].files[0]) === "undefined") {
            let formData = new FormData();
            formData.append("articleTitle", articleTitle.val());
            formData.append("articleContent", articleContent.val());
            formData.append("articleHtmlContent", testEditor.getHTML());
            // formData.append("uploadFile", $("#uploadFile")[0].files[0]);
            formData.append("uploadImage", uploadImage[0].files[0]);
            $.ajax({
                processData: false,//这个必须有，不然会报错
                contentType: false,//这个必须有，不然会报错
                type: "POST",
                url: "/publishArticle",
                // traditional: true,// 传数组
                data: formData,
                dataType: "json",
                success: function (data) {
                    //发布成功
                    if (data['status'] === 1) {
                        // window.removeEventListener('beforeunload',fnClose);
                        // $.get("/toLogin",function(data,status,xhr){
                        //发布成功不需要确认是否需要退出该页面
                        window.removeEventListener('beforeunload', fnClose);
                        alert(data['message']);
                        //成功后返回首页
                        window.location.replace("/toDashboard");
                        // });
                    } else if (data['status'] === 0) {
                        //发布成功不需要确认是否需要退出该页面
                        // window.removeEventListener('beforeunload',fnClose);
                        alert(data['message']);
                        window.location.replace("/toDashboard");
                    } /*else {
                        $('#my-alert').modal('close');
                        // window.removeEventListener('beforeunload',fnClose);
                        publishSuccessPutIn(data['data']);
                    }*/
                },
                error: function () {
                    alert("发表异常")
                }
            });
        }else {
            alert("图片上传格式只支持jpg或者png格式哦！")
        }

        // 定时关闭错误提示框
        var closeNoticeBox = setTimeout(function () {
            noticeBox.hide();
        }, 3000);
    });

    // 发表论坛
    var surePublishForumBtn = $('.surePublishForumBtn');

    surePublishForumBtn.click(function () {
        $.ajax({
            type:"POST",
            url:"/publishForum",
            traditional: true,// 传数组
            data:{
                articleTitle : articleTitle.val(),
                articleContent : articleContent.val(),
                articleHtmlContent : testEditor.getHTML()
            },
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            dataType:"json",
            success:function (data) {
                //发布成功
                if(data['status'] === 1){
                    // window.removeEventListener('beforeunload',fnClose);
                    // $.get("/toLogin",function(data,status,xhr){
                    //发布成功不需要确认是否需要退出该页面
                    window.removeEventListener('beforeunload',fnClose);
                    alert(data['message']);
                    //成功后返回首页
                    window.location.replace("/toAlumniForum");
                    // });
                }else if (data['status'] === 0) {
                    //发布成功不需要确认是否需要退出该页面
                    // window.removeEventListener('beforeunload',fnClose);
                    alert(data['message']);
                    window.location.replace("/toAlumniForum");
                }else if (data['status'] === 2) {
                    alert(data['message']);
                    //如果未登录跳转登录界面
                    window.location.replace("/toLogin");
                }
            }
        });

        // 定时关闭错误提示框
        var closeNoticeBox = setTimeout(function () {
            noticeBox.hide();
        }, 3000);
    });


    // 发表活动
    let surePublishActivityBtn = $('.surePublishActivityBtn');

    surePublishActivityBtn.click(function () {
        //普通通过ajax是不能发送file类型的input的，只能通过FormData类发送，并且processData，contentType必须有且为false
        let formData = new FormData();
        formData.append("articleTitle", articleTitle.val());
        formData.append("articleContent", articleContent.val());
        formData.append("articleHtmlContent", testEditor.getHTML());
        formData.append("uploadFile", $("#uploadFile")[0].files[0]);
        $.ajax({
            processData: false,//这个必须有，不然会报错
            contentType: false,//这个必须有，不然会报错
            type:"POST",
            url:"/activity/publishActivity",
            // traditional: true,// 传数组
            data: formData,
            // contentType:"application/x-www-form-urlencoded; charset=utf-8",
            dataType:"json",
            success:function (data) {
                //发布成功
                if(data['status'] === 1){
                    // window.removeEventListener('beforeunload',fnClose);
                    // $.get("/toLogin",function(data,status,xhr){
                    //发布成功不需要确认是否需要退出该页面
                    window.removeEventListener('beforeunload',fnClose);
                    alert(data['message']);
                    //成功后返回首页
                    window.location.replace("/toActivityPage");
                    // });
                }else if (data['status'] === 0) {
                    //发布成功不需要确认是否需要退出该页面
                    // window.removeEventListener('beforeunload',fnClose);
                    alert(data['message']);
                    window.location.replace("/toActivityPage");
                }else if (data['status'] === 2) {
                    alert(data['message']);
                    //如果未登录跳转登录界面
                    window.location.replace("/toLogin");
                }
            }
        });

        // 定时关闭错误提示框
        var closeNoticeBox = setTimeout(function () {
            noticeBox.hide();
        }, 3000);
    });

    // 发表校友帮扶
    let surePublishAlumniHelpBtn = $('.surePublishAlumniHelpBtn');

    surePublishAlumniHelpBtn.click(function () {
        //普通通过ajax是不能发送file类型的input的，只能通过FormData类发送，并且processData，contentType必须有且为false
        let uploadImageVal = uploadImage.val();
        let imageFileType = uploadImageVal.substring(uploadImageVal.lastIndexOf('.') + 1).toLowerCase();
        // alert(imageFileType);
        //判断封面图片只能够为img或者png格式
        if (imageFileType === 'jpg' || imageFileType === 'png') {
            let formData = new FormData();
            formData.append("articleTitle", articleTitle.val());
            formData.append("articleContent", articleContent.val());
            formData.append("articleHtmlContent", testEditor.getHTML());
            formData.append("uploadFile", $("#uploadFile")[0].files[0]);
            formData.append("uploadImage", uploadImage[0].files[0]);
            $.ajax({
                processData: false,//这个必须有，不然会报错
                contentType: false,//这个必须有，不然会报错
                type: "POST",
                url: "/help/publishAlumniHelp",
                // traditional: true,// 传数组
                data: formData,
                // contentType:"application/x-www-form-urlencoded; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    //发布成功
                    if (data['status'] === 1) {
                        // window.removeEventListener('beforeunload',fnClose);
                        // $.get("/toLogin",function(data,status,xhr){
                        //发布成功不需要确认是否需要退出该页面
                        window.removeEventListener('beforeunload', fnClose);
                        alert(data['message']);
                        //成功后返回首页
                        window.location.replace("/help/toAlumniHelp");
                        // });
                    } else if (data['status'] === 0) {
                        //发布成功不需要确认是否需要退出该页面
                        // window.removeEventListener('beforeunload',fnClose);
                        alert(data['message']);
                        window.location.replace("/help/toAlumniHelp");
                    } else if (data['status'] === 2) {
                        alert(data['message']);
                        //如果未登录跳转登录界面
                        window.location.replace("/toLogin");
                    }
                }
            });

            // 定时关闭错误提示框
            var closeNoticeBox = setTimeout(function () {
                noticeBox.hide();
            }, 3000);
        }
        else {
            alert("图片上传格式只支持jpg或者png格式哦！")
        }
    });

    // 发表校友风采展示
    let surePublishAlumniPhotoBtn = $('.surePublishAlumniPhotoBtn');

    surePublishAlumniPhotoBtn.click(function () {
        // alert(uploadImage[0].files[0]);
        //普通通过ajax是不能发送file类型的input的，只能通过FormData类发送，并且processData，contentType必须有且为false
        let uploadImageVal = uploadImage.val();
        let imageFileType = uploadImageVal.substring(uploadImageVal.lastIndexOf('.') + 1).toLowerCase();
        // alert(imageFileType);
        //判断封面图片只能够为img或者png格式
        if (imageFileType === 'jpg' || imageFileType === 'png' || typeof(uploadImage[0].files[0]) === "undefined") {
            let formData = new FormData();
            formData.append("articleTitle", articleTitle.val());
            formData.append("articleContent", articleContent.val());
            formData.append("articleHtmlContent", testEditor.getHTML());
            // formData.append("uploadFile", $("#uploadFile")[0].files[0]);
            formData.append("uploadImage", uploadImage[0].files[0]);
            $.ajax({
                processData: false,//这个必须有，不然会报错
                contentType: false,//这个必须有，不然会报错
                type: "POST",
                url: "/publishAlumniPhoto",
                // traditional: true,// 传数组
                data: formData,
                // contentType:"application/x-www-form-urlencoded; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    //发布成功
                    if (data['status'] === 1) {
                        // window.removeEventListener('beforeunload',fnClose);
                        // $.get("/toLogin",function(data,status,xhr){
                        //发布成功不需要确认是否需要退出该页面
                        window.removeEventListener('beforeunload', fnClose);
                        alert(data['message']);
                        //成功后返回首页
                        window.location.replace("/toPhotoPage");
                        // });
                    } else if (data['status'] === 0) {
                        //发布成功不需要确认是否需要退出该页面
                        // window.removeEventListener('beforeunload',fnClose);
                        alert(data['message']);
                        window.location.replace("/dashboard");
                    } else if (data['status'] === 2) {
                        alert(data['message']);
                        //如果未登录跳转登录界面
                        window.location.replace("/toLogin");
                    }
                }
            });

            // 定时关闭错误提示框
            var closeNoticeBox = setTimeout(function () {
                noticeBox.hide();
            }, 3000);
        }
        else {
            alert("图片上传格式只支持jpg或者png格式哦！")
        }
    });



