package com.j2ee.server.core;

import com.j2ee.server.enums.RuleEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laizhiyuan on 2017/8/28.
 *
 * <p>
 *     排序分页查询辅助类
 * </p>
 */
public class PagingSortQuery<E> implements Serializable{

    static final long serialVersionUID = 48882L;

    /**
     * 从第几行开始
     */
    private Integer beginRow = 1;

    /**
     * 取多少条数据
     */
    private Integer pageSize = 10;

    /**
     * 结果集
     */
    private List<E> rows = new ArrayList<E>();
    private List<E> data = new ArrayList<E>();

    /**
     * 总页数
     */
    private int total;

    /**
     * 是否查总数，默认是
     */
    private Boolean count = true;

    /**
     * 排序字段，默认是updateTime
     */
    private String sortField = "updateTime";

    /**
     * 默认从大到小
     */
    private String sortType = "desc";

    /**
     * 是否排序，默认是
     */
    private Boolean sort = true;

    /**
     * 单个结果集
     */
    private E singleResult;

    /**
     * 是否分页
     */
    private Boolean paging = true;

    /**
     * 条件map
     */
    private Map<String, RuleEnum> conditionsMap = new HashMap<String, RuleEnum>();

    public Boolean getPaging() {
        return paging;
    }

    public void setPaging(Boolean paging) {
        this.paging = paging;
    }

    public E getSingleResult() {
        return singleResult;
    }

    public void setSingleResult(E singleResult) {
        this.singleResult = singleResult;
    }

    public Integer getBeginRow() {
        if (beginRow <= 1){
            return 0;
        }else {
            return this.beginRow = (beginRow - 1) * getPageSize();
        }
    }

    public void setBeginRow(Integer beginRow) {
        this.beginRow = beginRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Boolean getCount() {
        return count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Boolean getSort() {
        return sort;
    }

    public void setSort(Boolean sort) {
        this.sort = sort;
    }

    public Map<String, RuleEnum> getConditionsMap() {
        return conditionsMap;
    }

    public void setConditionsMap(Map<String, RuleEnum> conditionsMap) {
        this.conditionsMap = conditionsMap;
    }
}
