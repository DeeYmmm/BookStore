package com.servlet;

import com.Service.BookService;
import com.alibaba.fastjson.JSON;
import com.domain.Book;
import com.xu.BookStoreWebUtils;
import com.xu.CriteriaBook;
import com.xu.Page;
import com.xu.ShoppingCart;

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
}
