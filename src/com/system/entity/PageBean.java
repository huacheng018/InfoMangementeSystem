package com.system.entity;

import java.util.List;

/**
 * 分页显示用的 页面page 类对象
 * @Author: 枠成
 * @Data:2019-08-23
 * @Description:com.system.entity
 * @version:1.0
 */
public class PageBean<T> {

    //当前页码
    private int currentPage;
    //总页数
    private int toatalPage;
    //总数据条数
    private int totalData;
    //每页的大小
    private int pageSize;
    //每页的数据
    private List<Student> beanList;

    public PageBean(){}

    public PageBean(int currentPage, int toatalPage, int totalData, int pageSize, List<Student> beanList) {
        this.currentPage = currentPage;
        this.toatalPage = toatalPage;
        this.totalData = totalData;
        this.pageSize = pageSize;
        this.beanList = beanList;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    //总页数不需要指定（由 总数据/每页 大小可得）
    public int getTotalPage() {
        return toatalPage;
    }

    public int getToatalPage() {
        return toatalPage;
    }

    //不能直接写进get方法里直接调用get方法，因为jsp页面用的是jstl而不是jsp语法，页面拿的是真实值
    public void setToatalPage() {
        int totalPage = totalData/pageSize;
        //如果正好，则返回；如果多余，则加一页！
        this.toatalPage= (totalData%pageSize == 0 ? totalPage : totalPage+1);
    }
    //手动输入，以防不测只需
    public void setToatalPage(int toatalPage) {
        this.toatalPage = toatalPage;
    }

    public int getTotalData() {
        return totalData;
    }
    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Student> getBeanList() {
        return beanList;
    }
    public void setBeanList(List<Student> beanList) {
        this.beanList = beanList;
    }
}
