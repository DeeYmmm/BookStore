package com.domain;

public class TradeItem {
    private int tradeItemId;
    private Book book;
    private int bookId;
    private int quantity;
    private int tradeId;

    public TradeItem(int bookId, int quantity, int tradeId) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.tradeId = tradeId;
    }

    public TradeItem() {
    }

    public int getTradeItemId() {
        return tradeItemId;
    }

    public void setTradeItemId(int tradeItemId) {
        this.tradeItemId = tradeItemId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    @Override
    public String toString() {
        return "TradeItem{" +
                "tradeItemId=" + tradeItemId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", tradeId=" + tradeId +
                '}';
    }
}
