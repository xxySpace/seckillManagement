/**
 * 获取登陆信息
 */
function getUserInfo() {
    $.ajax({
        type: "GET",
        url: "/user/getuserinfo",
        xhrFields: {withCredentials: true},
        async: false,
        success: function (data) {
            if (data.status == "success") {
                var g_userVo = data.data;
                $("#userName").text(g_userVo.name);
            } else {
                alert("获取登录信息失败，原因为" + data.data.errMsg);
                if (data.data.errCode == 20003) {
                    window.location.href = "/login";//跳转登录页面
                }
            }
        },
        error: function (data) {
            alert("获取登录信息失败，原因为" + data.responseText);
        }
    });
}

/**
 * 用户个人信息弹框
 */
function myLoginInfo() {
    layer_show("个人信息", "/user/userInfo", "350", "400");
}

/**
 * 切换用户
 */
function changeUser() {
    window.location.href = "/login";
}

/**
 * 退出系统
 */
function closeWebPage(){
    if (navigator.userAgent.indexOf("MSIE") > 0){
        //IE6.0不带提示关闭窗口
        if (navigator.userAgent.indexOf("MSIE 6.0") > 0){
            window.opener = null;
            window.close();
        } else {
            //IE7之后不提示关闭窗口
            window.open('','_top');
            window.top.close();
        }
    } else if (navigator.userAgent.indexOf("Firefox") > 0){
        //Firefox不提示关闭窗口
        window.location.href = 'about:blank';
    } else {
        window.opener = null;
        window.open('','_self','');
        window.close();
    }
}

/**
 * 获取url后带的参数
 * @param name 参数名称
 * @returns {string|null}
 */
function getParam(name) {
    var match = new RegExp(name +
        "=*([^&]+)*", "i").exec(location.search);
    if (match == null)
        match = new RegExp(name + "=(.+)", "i").exec(location.search);
    if (match == null) return null;
    match = match + "";
    var result = match.split(",");
    return decodeURIComponent(result[1]);
}