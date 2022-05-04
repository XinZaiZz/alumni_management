
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

    var publishBtn = $('.publishBtn');
    var articleTitle = $('#zhy-editor-title');//文章标题
    var articleContent = $('#my-editormd-html-code');//文章内容
    var noticeBoxTitle = $('.notice-box-title');
    var noticeBoxContent = $('.notice-box-content');
    var noticeBox = $('.notice-box');
    let uploadFile = $("#uploadFile");//上传文件
    let uploadImage = $("#uploadImage");

    publishBtn.click(function () {
        var articleTitleValues =  articleTitle.val();
        var articleContentValues = articleContent.val();
        if(articleTitleValues.length === 0){
            noticeBoxTitle.show();
        } else if (articleContentValues.length === 0){
            noticeBoxContent.show();
        } else{
            $('#my-alert').modal();
            $.ajax({
                type:"GET",
                url:"/findCategoriesName",
                async:false,
                data:{
                },
                dataType:"json",
                success:function (data) {
                    if (data['status'] == 103){
                        return;
                    }
                    var selectCategories = $('#select-categories');
                    selectCategories.empty();
                    selectCategories.append($('<option class="categoriesOption" value="choose">请选择</option>'));
                    for(var i=0;i<data['data'].length;i++){
                        selectCategories.append($('<option class="categoriesOption" value="' + data['data'][i] + '">' + data['data'][i] + '</option>'));
                    }
                    if(aCategory !== "" && aCategory.length > 0){
                        selectCategories.val(aCategory);
                    }
                },
                error:function () {
                }
            });
        }
        // 定时关闭错误提示框
        var closeNoticeBox = setTimeout(function () {
            noticeBox.hide();
        },3000);
    });

    function publishSuccessPutIn(data) {
        $('#removeDiv').html('');
        var sec = $('<div id="all"></div>');
        var success = $('<div class="success"></div>');
        var successBox = $('<div class="success-box"></div>');
        var successArticleTitle = $('<div class="successArticleTitle"><span>' + data.articleTitle + '</span></div>');
        var successWord = $('<div class="success-word"><p><i class="am-success am-icon-check-square-o" style="color: #5eb95e"></i> 发布成功</p></div>');
        var successTimeAndUser = $('<div class="success-time-user">' +
            '<p><i class="am-icon-calendar"></i>&nbsp;' + data.updateDate + '&nbsp;&nbsp;&nbsp;&nbsp;<i class="am-icon-user"></i>&nbsp;' + data.author + '</p>' +
            '</div>');
        var successBtn = $('<div class="successBtn">' +
            '<a href="/editor" class="reWriteBtn am-btn am-btn-danger am-round">写新文章</a>' +
            '<a href="' + data.articleUrl + '" class="lookArticleBtn am-btn am-btn-danger am-round">查看文章</a>' +
            '</div>');

        successBox.append(successArticleTitle);
        successBox.append(successWord);
        successBox.append(successTimeAndUser);
        successBox.append(successBtn);
        success.append(successBox);
        sec.append(success);
        $('#removeDiv').append(sec);
    }

    //验证是否有权限写博客
    /*$.ajax({
        type:"GET",
        url:"/canYouWrite",
        data:{
        },
        dataType:"json",
        success:function (data) {
            if (data['status'] == 103){
                return;
            }
            if(data['status'] != 0){
                var noticeBoxWrite = $('<div class="notice-box-write">' +
                    '<div class="am-alert am-alert-danger">' +
                    '<p>在线写博客功能暂不对外开放，您所写的文章都将发布无效<button type="button" class="canYouWrite am-close">&times;</button></p>' +
                    '</div>' +
                    '</div>');
                $('#app').append(noticeBoxWrite);
            }
            $('.canYouWrite').click(function () {
                $('.notice-box-write').hide();
            })
        },
        error:function () {
        }
    });*/
    //获得草稿文章
    /*$.ajax({
        type:"GET",
        url:"/getDraftArticle",
        async:false,
        data:{
        },
        dataType:"json",
        success:function (data) {
            if (data['status'] == 103){
                return;
            }
            if(data['status'] == 0){
                $('#zhy-editor-title').val(data['data']['articleTitle']);
                $('#my-editormd-markdown-doc').html(data['data']['articleContent']);
                $('#select-type').val(data['data']['articleType']);
                $('#select-grade').val(data['data']['articleGrade']);
                $('#originalAuthor').val(data['data']['originalAuthor']);
                $('#articleUrl').val(data['data']['articleUrl']);
                if(data['data']['articleType'] == "转载"){
                    $('#originalAuthorHide').show();
                    $('.articleUrlHide').show();
                }
                aCategory = data['data']['articleCategories'];
                var tags = data['data']['articleTags'];
                var tag = $('.tag');
                for(var i in tags){
                    tag.append($('<div style="display: inline-block;"><p class="tag-name" contenteditable="true">' + tags[i] + '</p>' +
                        '<i class="am-icon-times removeTag" style="color: #CCCCCC"></i></div>'));
                }
                var articleId = data['data']['id'];
                if(articleId != 0){
                    $('.surePublishBtn ').attr("id",articleId);
                }

            }
        },
        error:function () {
        }
    });*/

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
    var surePublishBtn = $('.surePublishBtn');
    var selectType = $('#select-type');
    // var articleGrade = $('#select-grade');
    // var originalAuthor = $('#originalAuthor');
    // var articleUrl = $('#articleUrl');
    surePublishBtn.click(function () {
        /*var tagNum = $('.tag').find('.tag-name').length;
        var articleTagsValue = [];
        for(var j=0;j<tagNum;j++){
            articleTagsValue[j] = $('.tag-name').eq(j).html();
        }*/
        var articleTypeValue = selectType.val();
        // var articleCategoriesValue = articleCategories.val();
        // var articleGradeValue = articleGrade.val();
        // var originalAuthorValue = originalAuthor.val();
        // var articleUrlValue = articleUrl.val();
        /*if(articleTagsValue.length === 0 || articleTagsValue[tagNum-1] === ""){
            $('.notice-box-tags').show();
        } else if (articleTypeValue === "choose"){
            $('.notice-box-type').show();
        } else if (articleCategoriesValue === "choose"){
            $('.notice-box-categories').show();
        } else if (articleGradeValue === "choose"){
            $('.notice-box-grade').show();
        } else if (articleType.val() == "转载" && originalAuthorValue === ""){
            $('.notice-box-originalAuthor').show();
        } else if (articleType.val() == "转载" && articleUrlValue === ""){
            $('.notice-box-url').show();
        } else {*/
        $.ajax({
            type:"POST",
            url:"/publishArticle/" + articleTypeValue,
            traditional: true,// 传数组
            data:{
                // id : $('.surePublishBtn').attr("id"),
                articleTitle : articleTitle.val(),
                articleContent : articleContent.val(),
                // articleTagsValue : articleTagsValue,
                // selectType : articleTypeValue,
                // articleCategories : articleCategoriesValue,
                // articleGrade : articleGradeValue,
                // originalAuthor : originalAuthorValue,
                // articleUrl : articleUrlValue,
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
                    alert("发布成功！");
                    //成功后返回首页
                    window.location.replace("/toIndex");
                    // });
                }else if (data['status'] === 0) {
                    //发布成功不需要确认是否需要退出该页面
                    // window.removeEventListener('beforeunload',fnClose);
                    alert("发布失败，请检查或通知管理人员吧！");
                    window.location.replace("/toIndex");
                } /*else {
                    $('#my-alert').modal('close');
                    // window.removeEventListener('beforeunload',fnClose);
                    publishSuccessPutIn(data['data']);
                }*/
            },
            error:function () {
                alert("发表异常")
            }
        });

        /*var myxhr = new XMLHttpRequest();
        myxhr.open("GET","/toIndex");
        myxhr.send();
        //事件绑定
        myxhr.onreadystatechange = function () {
            if (myxhr.readyState === 4){
                if (myxhr.status >= 200 && myxhr.status < 300){
                    //处理返回结果
                    // result.innerHTML = myxhr.response;
                }
            }
        };*/
        // }

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
        if (imageFileType === 'img' || imageFileType === 'png') {
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
            alert("图片上传格式只支持img或者png格式哦！")
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
        if (imageFileType === 'img' || imageFileType === 'png' || typeof(uploadImage[0].files[0]) === "undefined") {
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
            alert("图片上传格式只支持img或者png格式哦！")
        }
    });



