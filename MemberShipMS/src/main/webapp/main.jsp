<%@page pageEncoding="utf-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>嗯嗯</title>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.pagination.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <script type="text/javascript">
        $(function () {
            $("#addsale").hide();

            $("#salelist").hide();

            $("#seachnumber").hide();

            $("#addsales").click(function () {
                $("#addsale").show();
                $("#salelist").hide();
                $("#seachnumber").hide();
            });
            $("#salelists").click(function () {
                $("#addsale").hide();
                $("#salelist").show();
                $("#seachnumber").hide();
            });
            $("#seachnumbers").click(function () {
                $("#seachnumber").show();
                $("#addsale").hide();
                $("#salelist").hide();

            });
            load(0);
        });
        function load(pageNum) {
            var userName=$("[name=userName]").val();
            $.ajax({
                url:"/user/allUsers",
                type:"post",
                data:{"userName":userName,"pageNum":pageNum},
                success:function (data) {
                    //清空数据
                    $("#list-content").html('');
                    //追加数据
                    $.each(data.list,function (i,dom) {
                        //一个dom就是一个用户对象
                        $("#list-content").append("<tr><td>"+dom.id+"</td><td>"+dom.name+"</td><td>"+dom.userType.name+"</td><td>"+dom.newStatus+"</td><td>"+dom.dateTime+"</td></tr>");
                    });

                    //渲染分页  总记录数  当前页码  每页数据量
                    $('#pagination').pagination(data.total, {
                        current_page : pageNum,
                        items_per_page : data.pageSize,
                        callback:load,
                        load_first_page : true,
                        prev_text : '上一页',
                        next_text : '下一页'
                    });
                }
            });
        }
    </script>
</head>
<body>
<div align="center">
    <div>
        欢迎您：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
            href="javascript:logout()">退出登陆</a>
    </div>
    <br/>
    <br/>
    <div>
        <a id="addsales">销售</a>
        <br/>
        <br/>
        <a id="salelists">销售信息查询</a>
        <br/>
        <br/>
        <a id="seachnumbers">查看库存</a>
    </div>
    <div style="border: groove;border-color: blue;width: 800px;height: 700px;" align="right">
        <div id="first" align="center">
            <h2>欢迎光临小型进销存系统</h2>
        </div>
        <br/>
        <br/>
        <br/>
        <br/>
        <div id="addsale" align="center">
            <h2>添加销售</h2>
            <form action="/sale/insertSale" method="post" onsubmit="return addsale()">
                商品名称：
                <select name="productId">
                    <option value="0">请选择商品</option>


                    </span>
                </select>
                <br/>
                销售单价：<input type="text" name="price"/>
                <br/>
                销售数量：<input type="text" name="quantitys"/>
                <br/>
                <br/>
                <input type="submit" value="保存"/>
            </form>
        </div>

        <div id="salelist" align="center">

            <h4>销售信息查询：&nbsp;&nbsp;&nbsp;&nbsp;排序方式：
                用户名：<input name="userName" id="userName">
                <input type="button" value="查询" onclick="javascript:load(1)"/>
            </h4>

            <table align="center" border="1px">
                <tr>
                    <th>序号</th>
                    <th>用户名</th>
                    <th>用户类别</th>
                    <th>用户状态</th>
                    <th>最后修改时间</th>
                </tr>
                <tbody id="list-content">

                </tbody>

            </table>
            <div  class="pagination" id="pagination"></div>
        </div>

        <div id="seachnumber" align="center">
            <h4>查看库存&nbsp;&nbsp;&nbsp;商品名称：

                <select name="numberid">
                    <option value="0">请选择商品</option>
                </select>

                <input type="button" value="查询" onclick="seachnumber()"/>
            </h4>
            <h3>该商品的库存数量为：<span name="number"></span></h3>
        </div>
    </div>
</div>
</body>
</html>