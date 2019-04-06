<%--
  Created by IntelliJ IDEA.
  User: ym
  Date: 19-4-6
  Time: 下午6:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Cash</title>
</head>
<body>
<div style="text-align: center;">
    <br><br>
    您一共购买了 ${sessionScope.ShoppingCart.bookNumber} 本书
    <br>
    应付：￥${sessionScope.ShoppingCart.totalMoney}
    <br><br>

    <c:if test="${requestScope.errors!=null}">
        <span style="color: red; ">${requestScope.errors}</span>
    </c:if>

    <form action="book?method=cash" method="post">
        <table cellpadding="10" align="center">
            <tr>
                <td>信用卡姓名：</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>信用卡帐号：</td>
                <td><input type="text" name="accountId"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="submit"></td>
            </tr>
        </table>
    </form>

</div>
</body>
</html>
