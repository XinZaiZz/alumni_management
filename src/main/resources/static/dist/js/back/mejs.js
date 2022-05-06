$(function () {

    $('#user_search_btn').click(function (){
        methods.seachName();
    })

    $('#search_btn').click(function () {
        methods.seachTitleName();
    })

    $('#back_btn').click(function () {
        $('#Ktext').val(' ');
        methods.resectList();
    })

})

var addEnter = true,
    noRepeat = true,
    trIndex,
    hasNullMes = false


var methods = {
    seachName: function () {

        var a = $('#show_tbody tr');
        var nameVal = $('#Ktext').val().trim();
        var nameArr = [];

        if (nameVal==='') {
            bootbox.alert({
                title: "来自火星的提示",
                message: "搜索内容不能为空",
                closeButton:false
            })
            return;
        }

        for (var c=0;c<a.length;c++) {

            var txt = $('td:eq(1)', a.eq(c)).html().trim();
            nameArr.push(txt);
        }

        a.hide();
        for (var i=0;i<nameArr.length;i++) {
            if (nameArr[i].indexOf(nameVal)>-1) {
                a.eq(i).show();
            }
        }
    },
    seachTitleName: function () {

        var a = $('#show_tbody tr');
        var nameVal = $('#Ktext').val().trim();
        var nameArr = [];

        if (nameVal==='') {
            bootbox.alert({
                title: "来自火星的提示",
                message: "搜索内容不能为空",
                closeButton:false
            })
            return;
        }

        for (var c=0;c<a.length;c++) {

            var txt = $('td:eq(3)', a.eq(c)).html().trim();
            nameArr.push(txt);
        }

        a.hide();
        for (var i=0;i<nameArr.length;i++) {
            if (nameArr[i].indexOf(nameVal)>-1) {
                a.eq(i).show();
            }
        }
    },
    resectList: function () {
        $('#show_tbody tr').show();
    },
}