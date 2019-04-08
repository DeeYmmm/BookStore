package com.filter;

import com.testDao.JDBCUtils;
import com.xu.ConnectionContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "TranactionFilter",urlPatterns = "/*")
public class TranactionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        Connection connection=null;

        try {
            connection=JDBCUtils.getConnection();
            connection.setAutoCommit(false);

            ConnectionContext.getInstance().bind(connection);

            chain.doFilter(req, resp);

            connection.commit();
        } catch (Exception e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            HttpServletRequest request= (HttpServletRequest) req;
            HttpServletResponse response= (HttpServletResponse) resp;
            response.sendRedirect(request.getContextPath()+"/error-1.jsp");
        } finally {
            ConnectionContext.getInstance().remove();
            JDBCUtils.close(null,null,connection);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
