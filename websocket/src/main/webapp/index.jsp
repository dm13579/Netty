<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>客户端</title>
    <style type="text/css">
    </style>
</head>
<body>
<h2>客户端!</h2>
<button onclick="sendMsg()">发送消息</button>
</body>
<script>
    var ws;
    window.onload=function(){
        ws = new WebSocket("ws://localhost:8080/websocket");
        ws.onopen=function(){
        };
    };

//    document.onkeydown=function(event){
//        var e = event || window.event || arguments.callee.caller.arguments[0];
//        if (e && e.keyCode == 13) {
//            sendMsg();
//        }
//    };

    function sendMsg(){
        var txt = "xxx";
        ws.send(txt);

        /**
         * 接收返回的消息
         */
        ws.onmessage = function (evt)
        {
            var received_msg = evt.data;
            console.log(received_msg);
        };

        /**
         * 关闭连接
         */
        ws.onclose = function()
        {

        };

    }
</script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js" type="text/javascript"></script>
<script>
    ws = new WebSocket("ws://localhost:8080/websocket");
    ws.onopen=function(){
    };
    wx.config({
        beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wwe2ce12b37eda3b75', // 必填，企业微信的corpID
        timestamp:1577432405 , // 必填，生成签名的时间戳
        nonceStr: 'afaafafa', // 必填，生成签名的随机串
        signature: 'b88bf6d11307c7985345f1949fd2b2c11b59a612',// 必填，签名，见 附录-JS-SDK使用权限签名算法
        jsApiList: ['selectEnterpriseContact','openUserProfile','openEnterpriseChat'] // 必填，需要使用的JS接口列表，凡是要调用的接口都需要传进来
    });

    wx.ready(function(){
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        wx.checkJsApi({
            jsApiList: ['selectEnterpriseContact','openUserProfile'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function(res) {
                // 以键值对的形式返回，可用的api值true，不可用为false
                // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
                console.log(res);
                ws.send(res);
            }
        });
        wx.invoke('selectExternalContact', {
            "filterType": 0, //0表示展示全部外部联系人列表，1表示仅展示未曾选择过的外部联系人。默认值为0；除了0与1，其他值非法。在企业微信2.4.22及以后版本支持该参数
        }, function(res){
            if(res.err_msg == "selectExternalContact:ok"){
                userIds  = res.userIds ; //返回此次选择的外部联系人userId列表，数组类型
            }else {
                //错误处理
                console.log(res);
                ws.send(res);
            }
        });
        wx.invoke('openUserProfile', {
            "type": 1, //1表示该userid是企业成员，2表示该userid是外部联系人
            "userid": "wmEAlECwAAHrbWYDOK5u3Af13xlYDDNQ" //可以是企业成员，也可以是外部联系人
        }, function(res){
            if(res.err_msg != "openUserProfile:ok"){
                //错误处理
                console.log(res);
                ws.send(res);
            }
            console.log(res);
            ws.send(res);
        });

    });
    wx.error(function(res){
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        console.log(res);
        ws.send(res);
    });
</script>

</html>
