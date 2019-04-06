<%--
  Created by IntelliJ IDEA.
  User: ym
  Date: 19-2-15
  Time: 下午4:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ include file="/js/hidden.jsp"%>
<html>
<head>
    <title>book</title>
</head>
<body>
    <div style="text-align: center;">
        <br><br>
        Title:${book.title}
        <br><br>
        Author:${book.author}
        <br><br>
        Price:${book.price}
        <br><br>

        <a href="book?method=getBooks&pageNo=${param.pageNo}">继续购物</a>
    </div>
</body>
</html>
