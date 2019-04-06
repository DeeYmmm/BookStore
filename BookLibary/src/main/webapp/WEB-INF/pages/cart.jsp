<%--
  Created by IntelliJ IDEA.
  User: ym
  Date: 19-2-18
  Time: 下午4:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>cart</title>
    <script src="/js/jquery-3.3.1.js"></script>
    <script type="text/javascript">

        //hidden.jsp中a的点击事件与delete上的点击事件冲突，无法弹出询问框，
        //暂时不include hidden.jsp，将添加href后缀的操作在每一个方法内部进行
        $(function () {

            $(".delete").click(function () {
                var tr=$(this).parent().parent();
                var title=$.trim(tr.find("td:first").text());
                var flag=confirm("确定要删除"+title+"的信息吗？");

                if (flag){
                    var serializeVal = $(":hidden").serialize();
                    var href = this.href + "&" + serializeVal;
                    window.location.href = href;
                }

                return false;
            });

            $(".tiao").click(function () {
                var serializeVal = $(":hidden").serialize();
                var href = this.href + "&" + serializeVal;
                window.location.href = href;
                return false;
            });

            //ajax修改单个商品的数量
            //1.获取页面所有的text，添加onchange函数
            //2.请求地址:bookservlet
            //3.请求参数为:method:updateItemQuantity,id:name属性值,quantity:val,
            //  time:new Date()
            //4.在updateItemQuantity方法中，获取quantity,id,再获取购物车对象，调用service方法做修改
            //5.传回json,bookNumber:xx,totalMoney:xx
            //6.更新bookNumber和totalMoney

            $(":text").change(function () {
                var quantityVal=$.trim(this.value);
                var tr=$(this).parent().parent();
                var title=$.trim(tr.find("td:first").text());

                var flag=false;
                var quantity=-1;
                var reg=/^\d+$/g;
                if (reg.test(quantityVal)) {
                    quantity = parseInt(quantityVal);
                    if (quantity >= 0) {
                        flag = true;
                    }
                }

                if (flag===false){
                    alert("输入的数量不合法！");
                    $(this).val($(this).attr("class"));
                    return;
                }

                if (quantity===0){
                    flag=confirm("确定要删除"+title+"的信息吗？");
                    if (flag){
                        var href=tr.find("td:last").find("a").attr("href");
                        //alert(href);
                        var serializeVal = $(":hidden").serialize();
                        href = href + "&" + serializeVal;
                        window.location.href = href;

                        return;
                    }
                }

                flag=confirm("确定要修改"+title+"的数量吗？");

                if(flag===false){
                    $(this).val($(this).attr("class"));
                    return;
                }

                var url="book";
                var idval=$.trim(this.name);
                var args={"method":"updateItemQuantity","id":idval,"quantity":quantityVal,"time":new Date()};

                $.post(url,args,function (data) {
                   var bookNumber=data.bookNumber;
                   var totalMoney=data.totalMoney;

                   $("#totalMoney").text("总金额:￥"+totalMoney);
                   $("#bookNumber").text("您的购物车中共有"+bookNumber+"本书");
                });
            });


        })
    </script>
</head>
<body>

<div style="text-align: center;">

    <input type="hidden" name="minPrice" value="${param.minPrice}">
    <input type="hidden" name="maxPrice" value="${param.maxPrice}">

    <br><br>
    <div id="bookNumber">您的购物车中共有${sessionScope.ShoppingCart.bookNumber}本书</div>
    <table cellpadding="10" align="center">
        <tr>
            <td>Title</td>
            <td>Quantity</td>
            <td>Price</td>
            <td>&nbsp;</td>
        </tr>

        <c:forEach items="${sessionScope.ShoppingCart.items}" var="item">
            <tr>
                <td>${item.book.title}</td>
                <td>
                    <input class="${item.quantity}" type="text" size="1" name="${item.book.id}" value="${item.quantity}">
                </td>
                <td>${item.book.price}</td>
                <td><a href="book?method=remove&pageNo=${param.pageNo}&id=${item.book.id}" class="delete">删除</a></td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="4" id="totalMoney">总金额:￥${sessionScope.ShoppingCart.totalMoney}</td>
        </tr>

        <tr>
            <td colspan="4">
                <a href="book?method=getBooks&pageNo=${param.pageNo}" class="tiao">继续购物</a>
                &nbsp;&nbsp;

                <a href="book?method=clear">清空购物车</a>
                &nbsp;&nbsp;

                <a href="book?method=forwardPage&page=cash" class="tiao">结帐</a>
                &nbsp;&nbsp;
            </td>
        </tr>
    </table>

</div>

</body>
</html>
