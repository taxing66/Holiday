package com.joiway.devin.holiday.model.net;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 *
 * 用于fastjson，处理响应数据中data部分
 * @author Jianan 2015年6月29日
 *
 * @param <I>
 * @param <O>
 */
public class DataJson<I, O> {

    private List<I> list;
    private O obj;
    @JSONField(name = "page_sum")
    private int pageSum;
    @JSONField(name = "total_row")
    private int totalRow;

    public List<I> getList() {
        return list;
    }

    public void setList(List<I> list) {
        this.list = list;
    }

    public O getObj() {
        return obj;
    }

    public void setObj(O obj) {
        this.obj = obj;
    }

    public int getPageSum() {
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

}
