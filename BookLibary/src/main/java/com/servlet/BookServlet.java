package com.servlet;

import com.Service.AccountService;
import com.Service.BookService;
import com.Service.UserService;
import com.alibaba.fastjson.JSON;
import com.domain.Account;
import com.domain.Book;
import com.domain.User;
import com.xu.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BookServlet extends HttpServlet {
    private BookService bookService=new BookService();
    private UserService userService=new UserService();
    private AccountService accountService=new AccountService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName=request.getParameter("method");

        try {
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }



    protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNoStr=request.getParameter("pageNo");
        String minPriceStr=request.getParameter("minPrice");
        String maxPriceStr=request.getParameter("maxPrice");

        int pageNo=1;
        int minPrice=0;
        int maxPrice=Integer.MAX_VALUE;

        try {
            pageNo=Integer.parseInt(pageNoStr);
        } catch (NumberFormatException e) {
        }
        try {
            minPrice=Integer.parseInt(minPriceStr);
        } catch (NumberFormatException e) {
        }
        try {
            maxPrice=Integer.parseInt(maxPriceStr);
        } catch (NumberFormatException e) {
        }

        CriteriaBook criteriaBook=new CriteriaBook(minPrice,maxPrice,pageNo);
        Page<Book> page=bookService.getPage(criteriaBook);

//        System.out.println(criteriaBook);
//        System.out.println(page);

        request.setAttribute("page",page);

        request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request,response);
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr=request.getParameter("id");
        int id=-1;

        Book book=null;

        try {
            id=Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
        }

        if (id>0)
            book=bookService.getBook(id);

        if (book==null){
            response.sendRedirect("error-1.jsp");
            return;
        }
        request.setAttribute("book",book);
        request.getRequestDispatcher("/WEB-INF/pages/book.jsp").
                forward(request,response);
    }

    protected void addToCart(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String idstr=request.getParameter("id");
        int id=-1;
        boolean flag=false;

        try {
            id=Integer.parseInt(idstr);
        } catch (NumberFormatException e) {
        }

        if (id>0){
            ShoppingCart shoppingCart=
                    BookStoreWebUtils.getShoppingCart(request);
            flag=bookService.addToCart(id,shoppingCart);
        }
        if (flag){
            getBooks(request,response);
            return;
        }

        response.sendRedirect("error-1.jsp");

    }

    protected void toCartPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,response);
    }

    protected void forwardPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String page=request.getParameter("page");
        request.getRequestDispatcher("/WEB-INF/pages/"+page+".jsp").forward(request,response);
    }

    protected void remove(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String idStr=request.getParameter("id");
        int id=-1;

        try {
            id=Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
        }

        ShoppingCart shoppingCart=BookStoreWebUtils.getShoppingCart(request);
        bookService.removeItemFromShoppingCart(shoppingCart,id);

        if (shoppingCart.isEmpty()){
            request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request,response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,response);
    }

    protected void clear(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart=BookStoreWebUtils.getShoppingCart(request);
        bookService.clear(shoppingCart);
        request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request,response);
    }

    protected void updateItemQuantity(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String idStr=request.getParameter("id");
        String quantityStr=request.getParameter("quantity");

        ShoppingCart shoppingCart=BookStoreWebUtils.getShoppingCart(request);

        int id=-1;
        int quantity=-1;

        try {
            id=Integer.parseInt(idStr);
            quantity=Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) { }

        if (id>0&&quantity>0){
            bookService.updateItemQuantity(shoppingCart,id,quantity);
        }

        Map<String,Object> map=new HashMap<>();
        map.put("bookNumber",shoppingCart.getBookNumber());
        map.put("totalMoney",shoppingCart.getTotalMoney());

        String s = JSON.toJSONString(map);

        response.setContentType("application/json");
        response.getWriter().print(s);
    }

    protected void cash(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //1.简单验证：验证表单域的值是否符合基本的规范，不需要查询数据库或调用业务方法
        String username=request.getParameter("username");
        String accountId=request.getParameter("accountId");
        StringBuffer errors = validateFormField(username, accountId);

        //表单验证通过
        if (errors.toString().equals("")){
            errors = validateUser(username, accountId);

            //用户名和帐号验证通过
            if (errors.toString().equals("")){
                errors = validateBookStoreNumber(request);

                //库存验证通过
                if (errors.toString().equals("")){
                    errors = validateBalance(request,accountId);
                }
            }
        }

        if (!errors.toString().equals("")){
            request.setAttribute("errors",errors);
            request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request,response);
        }

    }

    //验证余额是否充足
    private StringBuffer validateBalance(HttpServletRequest request,String accountId){
        StringBuffer errors=new StringBuffer("");
        ShoppingCart cart=BookStoreWebUtils.getShoppingCart(request);
        Account account=accountService.getAccount(Integer.parseInt(accountId));

        if (cart.getTotalMoney()>account.getBalance()){
            errors.append("余额不足！");
        }
        return errors;
    }

    //验证库存是否充足
    private StringBuffer validateBookStoreNumber(HttpServletRequest request){
        StringBuffer errors=new StringBuffer("");
        ShoppingCart shoppingCart=BookStoreWebUtils.getShoppingCart(request);

        for (ShoppingCartItem item:shoppingCart.getItems()){
            int quantity=item.getQuantity();
            int storeNumber=bookService.getBook(item.getBook().getId()).getStoreNumber();

            if (quantity>storeNumber){
                errors.append(item.getBook().getTitle()).append("库存不足<br>");
            }
        }

        return errors;
    }

    //验证用户名和帐号是否匹配
    private StringBuffer validateUser(String username, String accountId1) {
        boolean flag=false;
        User user=userService.getUser(username);
        if (user!=null){
            int accountId2 = user.getAccountId();
            if (accountId1.trim().equals(""+accountId2)){
                flag=true;
            }
        }

        StringBuffer errors2=new StringBuffer("");
        if (!flag){
            errors2.append("用户名和帐号不匹配！");
        }
        return errors2;
    }

    //验证表单域是否符合基本规范
    private StringBuffer validateFormField(String username, String accountId) {
        StringBuffer errors=new StringBuffer("");

        if (username==null||username.equals("")){
            errors.append("用户名不能为空！<br>");
        }

        if (accountId==null||accountId.equals("")){
            errors.append("帐号不能为空！");
        }
        return errors;
    }
}
