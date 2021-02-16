package com.imooc.mgallery.utils;

import java.util.ArrayList;
import java.util.List;

//分页模型对象
public class PageModel {
    private int page;//页号
    private int totalPages;//总页数
    private int rows;//每页记录数
    private int totalRows;//总记录数
    private int pageStartRow;//当前页从第n行开始
    private int pageEndRow;//当前页到n行结束
    private boolean hasNextPage;//是否存在下一页
    private boolean hasPreviousPage;//是否存在上一页
    private List pageData;//当前页面数据

    public PageModel(List data, int page, int rows) {
        this.page = page;
        this.rows = rows;
        totalRows = data.size();
        //总页数计算规则： 总行数/每页记录数，能整除页数取整，不能整除向上取整
        //例如: 18/6=3 | 20/6≈3.33 向上取整=4
        //Math.ceil 浮点数向上取整 Math.floor 浮点数向下取整
        //20/6≈3.33 20/6=3 ,注意事项：Java中两个整数相除只会得到结果整数部分，需转为浮点数运算
        totalPages = new Double(Math.ceil(totalRows/(rows*1f))).intValue();

        pageStartRow = (page-1)*rows; //0
        pageEndRow = page*rows;//6
        if(pageEndRow>totalRows) { //避免下标越界异常
            pageEndRow=totalRows;
        }
        pageData = data.subList(pageStartRow,pageEndRow); //分页数据
        if(page>1) {
            hasPreviousPage = true;
        } else {
            hasPreviousPage = false;
        }

        if(page<totalPages) {
            hasNextPage = true;
        } else {
            hasNextPage = false;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public List getPageData() {
        return pageData;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    public static void main(String[] args) {
        List<Integer> sample = new ArrayList<>();
        for(int i=0; i<=100; i++)
            sample.add(i);
        PageModel pageModel = new PageModel(sample,6,8);
        System.out.println(pageModel.getPageData());
        System.out.println(pageModel.getTotalPages());
        System.out.println(pageModel.getPageStartRow() + ":" + pageModel.getPageEndRow());
    }
}
