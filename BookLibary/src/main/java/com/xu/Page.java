package com.xu;

import java.util.List;

public class Page<T> {
    //当前第几页
    private int pageNo;
    //当前页的list
    private List<T> list;
    //当前页显示多少项
    private int pageSize=5;
    //总共有多少项
    private long totalItemNumber;
    //构造器中对pageNo进行初始化
    public Page(int pageNo) {
        this.pageNo = pageNo;
    }
    //需要校验
    public int getPageNo(){
        if (pageNo<=0){
            pageNo=1;
        }else if (pageNo>getTotalPageNumber()){
            pageNo=getTotalPageNumber();
        }
        return pageNo;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize=pageSize;
    }

    public long getTotalItemNumber() {
        return totalItemNumber;
    }

    public void setTotalItemNumber(long totalItemNumber) {
        this.totalItemNumber = totalItemNumber;
    }
    //获取总页数
    public int getTotalPageNumber(){
        int totalPageNumber= (int) (totalItemNumber/pageSize);
        if (totalItemNumber%pageSize!=0){
            totalPageNumber++;
        }
        return totalPageNumber;
    }

    public boolean isHasNext(){
        if (getPageNo()<getTotalPageNumber()){
            return true;
        }
        return false;
    }

    public boolean isHasPrev(){
        if (getPageNo()>1){
            return true;
        }
        return false;
    }

    public int getPrevPage(){
        if (isHasPrev()){
            return getPageNo()-1;
        }
        return getPageNo();
    }

    public int getNextPage(){
        if (isHasNext()){
            return getPageNo()+1;
        }
        return getPageNo();
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", list=" + list +
                ", pageSize=" + pageSize +
                ", totalItemNumber=" + totalItemNumber +
                '}';
    }
}
