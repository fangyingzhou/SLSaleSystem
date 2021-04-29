package com.slsale.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/18
 * @Description:分页工具类
 * @Version:1.0
 */
public class PageSupport {
    private Integer pageNo = 1;
    private Integer pageSize = 1;
    private Integer totalCount; //总数量
    private Integer pageCount;  //总页数
    private Integer num = 3;
    private List items = new ArrayList<>(); //存储当前页中所有对象

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    //计算总页数
    public void setTotalCount(Integer totalCount) {
        if(totalCount>0){
            this.totalCount = totalCount;
            this.pageCount = (totalCount+pageSize -1)/pageSize;
        }
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    //获取前一页
    public Integer getPrev(){
        return pageNo-1;
    }
    //获取后一页
    public Integer getNext(){
        return pageNo+1;
    }
    //获取最后一页
    public Integer getLast(){
        return pageCount;
    }
    //是否存在前一页
    public boolean isExitPrev(){
        if(pageNo-1>0){
            return true;
        }
        return false;
    }
    //是否存在下一页
    public boolean isExistNext(){
        if(pageCount != null && pageNo < pageCount){
            return true;
        }
        return false;
    }
    //当前页的前num页  1 2 3 4 [5] 6 7 8 9
    public List<Integer> getPrevPages(){
        List<Integer> list = new ArrayList<>();
        Integer frontPage = 1;
        if(pageNo-num > 0){
            frontPage = pageNo - num;
        }
        for(int i=frontPage;i<pageNo;i++){
            list.add(i);
        }
        return list;
    }

    //当前页的后num页 1 2 3 4 [5] 6 7 8 9
    public List<Integer> getNextPages(){
        List<Integer> list = new ArrayList<>();
        Integer endNum = pageNo;
        if(pageCount != null){
           if(pageNo<pageCount && (pageNo+num)<pageCount){
               endNum = pageNo + num;
           }else{
               endNum = pageCount;
           }
        }
        for(int i=pageNo+1;i<=endNum;i++){
            list.add(i);
        }
        return list;
    }

}
