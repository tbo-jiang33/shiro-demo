<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Index</title>
    <style>
        .demo1{
            background-color: #00FF00;
        }
        .demo2{
            background-color: pink;
        }
        .demo3{
            background-color: #00F7DE;
        }
        .demo4{
            background-color: green;
        }
        .demo5{
            background-color: gray;
        }
    </style>

</head>
<body>
<!--从session里取出user对象-->
<h1>This page is admin！${user.username}</h1>


<script type="text/javascript" src="../js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="../layui/layui.js"></script>
<link rel="stylesheet" href="../layui/css/layui.css">

<div class="layui-fluid">
    常规布局（以中型屏幕桌面为例）：
    <div class="layui-row ">
        <div class="layui-col-md6 demo1">
            <div>你的内容 9/12</div>
        </div>
        <div class="layui-col-md6 demo2">
            <div class="demo2">你的内容 3/12
        </div>
    </div>

    移动设备、平板、桌面端的不同表现：
    <div class="layui-row layui-col-space10">
        <div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
            <div class="demo2">移动：6/12 | 平板：6/12 | 桌面：4/12</div>
        </div>
        <div class="layui-col-xs6 layui-col-sm6 layui-col-md4">
            <div class="demo3">移动：6/12 | 平板：6/12 | 桌面：4/12</div>
        </div>
        <div class="layui-col-xs4 layui-col-sm12 layui-col-md4">
            <div class="demo4">移动：4/12 | 平板：12/12 | 桌面：4/12</div>
        </div>
        <div class="layui-col-xs4 layui-col-sm7 layui-col-md8">
            <div class="demo5">移动：4/12 | 平板：7/12 | 桌面：8/12</div>
        </div>
        <div class="layui-col-xs4 layui-col-sm5 layui-col-md4">
            <div class="demo1">移动：4/12 | 平板：5/12 | 桌面：4/12</div>
        </div>
    </div>


</div>

<script>

    layui.use('layer', function () {


    })
</script>

</body>
</html>