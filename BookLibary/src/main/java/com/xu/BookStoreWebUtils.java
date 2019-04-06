package com.xu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BookStoreWebUtils {

    /**
     * 获取购物车对象：从 session 中获取，若　session　中没有该对象，
     * 则创建一个新的购物车对象，放入到 session中；
     * 若有，直接返回．
     * @param request
     * @return
     */
    public static ShoppingCart getShoppingCart(HttpServletRequest request){
        HttpSession session=request.getSession();
        ShoppingCart shoppingCart= (ShoppingCart) session.getAttribute("ShoppingCart");

        if (shoppingCart==null){
            shoppingCart=new ShoppingCart();
            session.setAttribute("ShoppingCart",shoppingCart);
        }
        return shoppingCart;
    }
}
