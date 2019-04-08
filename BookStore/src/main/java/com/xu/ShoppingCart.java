package com.xu;

import com.domain.Book;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private Map<Integer,ShoppingCartItem> books=new HashMap<>();

    /**
     * 向购物车添加一件商品
     * 1.检查购物车中有没有该商品，若有，则使其数量＋１，若没有，
     * 新创建其对应的ShoppingCartItem，并把其加入到books中
     * @param book
     */
    public void addBook(Book book){
        ShoppingCartItem shoppingCartItem=books.get(book.getId());

        if (shoppingCartItem==null){
            shoppingCartItem=new ShoppingCartItem(book);
            books.put(book.getId(),shoppingCartItem);
        }else {
            shoppingCartItem.increment();
        }

    }

    /**
     * 检验购物车中是否有id指定的商品
     * @param id
     * @return
     */
    public boolean hasBook(Integer id){
        return books.containsKey(id);
    }

    public Map<Integer,ShoppingCartItem> getBooks(){
        return books;
    }

    /**
     * 返回购物车中商品的总数量
     * @return
     */
    public int getBookNumber(){
        int total=0;

        for (ShoppingCartItem shoppingCartItem:getItems()){
            total+=shoppingCartItem.getQuantity();
        }

        return total;
    }

    /**
     * 获取购物车中的所有的图书组成的集合
     * @return
     */
    public Collection<ShoppingCartItem> getItems(){
        return books.values();
    }


    /**
     * 获取购物车中所有的商品的总的价格
     * @return
     */
    public float getTotalMoney(){
        float total=0;

        for (ShoppingCartItem shoppingCartItem:getItems()){
            total+=shoppingCartItem.getItemMoney();
        }

        return total;
    }

    /**
     * 返回购物车是否为空
     * @return
     */
    public boolean isEmpty(){
        return books.isEmpty();
    }

    /**
     * 清空购物车
     */
    public void clear(){
        books.clear();
    }

    /**
     * 移除指定项
     * @param id
     */
    public void remove(int id){
        books.remove(id);
    }

    /**
     * 修改指定书的数量
     * @param id
     * @param quantity
     */
    public void upadteItemQuantity(int id,int quantity){
        ShoppingCartItem shoppingCartItem=books.get(id);
        if (shoppingCartItem!=null){
            shoppingCartItem.setQuantity(quantity);
        }
    }
}
