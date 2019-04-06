<%--
  Created by IntelliJ IDEA.
  User: ym
  Date: 19-2-15
  Time: 下午5:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(function () {
        $("a").each(function () {
            this.onclick=function () {
                var serializeVal = $(":hidden").serialize();
                var href = this.href + "&" + serializeVal;
                window.location.href = href;
                return false;
            };
        });
    })
</script>
<input type="hidden" name="minPrice" value="${param.minPrice}">
<input type="hidden" name="maxPrice" value="${param.maxPrice}">