$(function () {
    var URL = window.URL || window.webkitURL;
    var $image = $('#EditImg');
    //获取图片截取的位置
    var screenWidth = $(window).width();
    var screenHeight =  $(window).height();
    var $dataX = $('#dataX');
    var $dataY = $('#dataY');
    var $dataHeight = $('#dataHeight');
    var $dataWidth = $('#dataWidth');
    var $dataRotate = $('#dataRotate');
    var $dataScaleX = $('#dataScaleX');
    var $dataScaleY = $('#dataScaleY');
    var options = {
        containerHeight :  screenWidth,
        containerWidth : screenHeight,
        aspectRatio: 1 / 1,
        viewMode:2,
        guides:false,
        background:false,
        scalable:false,
        movable:false,
        crop: function(e) {
            $dataX.val(Math.round(e.x));
            $dataY.val(Math.round(e.y));
            $dataHeight.val(Math.round(e.height));
            $dataWidth.val(Math.round(e.width));
            $dataRotate.val(e.rotate);
            $dataScaleX.val(e.scaleX);
            $dataScaleY.val(e.scaleY);
        }
    }
    $('#EditImg').cropper(options);

    var $inputImage = $('#inputImage');
    URL = window.URL || window.webkitURL;
    var blobURL;
    if (URL) {
        $inputImage.change(function () {
            var files = this.files,
                file;
            if (files && files.length) {
                file = files[0];
                if (/^image\/\w+$/.test(file.type)) {
                    blobURL = URL.createObjectURL(file);
                    $image.one('built.cropper', function () {
                        URL.revokeObjectURL(blobURL);
                    }).cropper('reset', true).cropper('replace', blobURL);
                    $("#showEdit").removeClass('Hide');
                } else {
                    alert('Please choose an image file.');
                }
            }
        });
    } else {
        $inputImage.parent().remove();
    }
    //裁剪图片
    $("#cut").on("click", function () {
        var dataURL = $image.cropper("getCroppedCanvas");
        var imgurl = dataURL.toDataURL("image/*", 0.5);
        $image.cropper('destroy').attr('src', imgurl).cropper(options);
    });
    //提交图片
    $("#submit").on("click", function () {
        var dataURL = $image.cropper("getCroppedCanvas");//拿到剪裁后的数据
        var data = dataURL.toDataURL("image/*", 0.5);//转成base64
        $.ajax({
            url: "uploadImage",
            type: "POST",
            data: {
                imgBase64 : data.toString()
            },
            timeout : 10000, //超时时间设置，单位毫秒
            async: true,
            success: function (result) {
                console.log(result);
                $("#result").html(result);
            },
            error: function (result) {

            }
        });
    });
});