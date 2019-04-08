<%--
  Created by IntelliJ IDEA.
  User: ym
  Date: 19-2-14
  Time: 下午2:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ include file="/js/hidden.jsp"%>
<html>
<head>
    <title>books</title>
<script src="/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(function () {

        $("#pageNo").change(function () {
            var val=$(this).val();
            val=$.trim(val);

            var reg=/^\d+$/g;
            var flag=false;
            var pageNo=0;


            if (reg.test(val)){
                pageNo=parseInt(val);
                if (pageNo>=1&&pageNo<=parseInt("${page.totalPageNumber}")) {
                    flag=true;
                }
            }

            if (!flag){
                alert("请输入合法页码．");
                $(this).val("");
                return;
            }

            var href="book?method=getBooks&pageNo="+pageNo+"&"+$(":hidden").serialize();
            window.location.href=href;
        });
    })


</script>

</head>
<body>

    <div style="text-align: center;">
        <c:if test="${param.title!=null}">
            您已经将${param.title}放入购物车中．
            <br><br>
        </c:if>
        <c:if test="${!empty sessionScope.ShoppingCart.books}">
            您的购物车中有${sessionScope.ShoppingCart.bookNumber}本书，<a href="book?method=forwardPage&page=cart&pageNo=${page.pageNo}">查看购物车</a>
        </c:if>

        <br><br>
        <form action="book?method=getBooks" method="post" >
            Price:
            <input type="text" size="1" name="minPrice">-
            <input type="text" size="1" name="maxPrice">
            
            <input type="submit" value="确认">
        </form>

        <br><br>
        <table cellpadding="10" align="center">
            <c:forEach items="${page.list}" var="book">
                <tr>
                    <td>
                        <a href="book?method=getBook&pageNo=${page.pageNo}&id=${book.id}" >${book.title}</a>
                        <br>
                        ${book.author}
                    </td>
                    <td>${book.price}</td>
                    <td><a href="book?method=addToCart&pageNo=${page.pageNo}&id=${book.id}&title=${book.title}">加入购物车</a></td>
                </tr>
            </c:forEach>
        </table>

        <br><br>
        共${page.totalPageNumber} 页
        &nbsp;&nbsp;
        当前是第${page.pageNo} 页
        &nbsp;&nbsp;

        <c:if test="${page.hasPrev}">
            <a href="book?method=getBooks&pageNo=1">首页</a>
            &nbsp;&nbsp;
            <a href="book?method=getBooks&pageNo=${page.prevPage}">上一页</a>
        </c:if>
        &nbsp;&nbsp;
        <c:if test="${page.hasNext}">
            <a href="book?method=getBooks&pageNo=${page.nextPage}">下一页</a>
            &nbsp;&nbsp;
            <a href="book?method=getBooks&pageNo=${page.totalPageNumber}">尾页</a>
        </c:if>
        &nbsp;&nbsp;
        转到<input type="text" size="1" id="pageNo">页
    </div>

</body>
</html>
