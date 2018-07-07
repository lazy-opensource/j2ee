package com.j2ee.admin.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j2ee.admin.common.JsonResult;
import com.j2ee.admin.enums.ResultEnum;
import com.j2ee.server.core.PagingSortQuery;
import com.j2ee.server.enums.RuleEnum;
import com.j2ee.server.utils.ReflectHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laizhiyuan on 2017/8/29.
 */
@SuppressWarnings("all")
public class BaseAction<E> extends ActionSupport implements ModelDriven<E>{

    //实体
    protected E entity;
    //批量删除的ids
    protected String ids;
    //查询条件参数
    protected String conditionParams;

    /**
     * easuUI 查询前台默认的分页排序参数
     */
    protected String rows;
    protected String page;
    protected String sort;
    protected String order;

    /**
     * 返回前台数据结构，任你爱好使用哪种结构
     */
    protected Map<String, Object> responseMap = new HashMap<String, Object>();
    protected JSONObject jsonObject = new JSONObject();
    protected String responseStr;
    protected JsonResult jsonResult = new JsonResult();

    public JsonResult getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(JsonResult jsonResult) {
        this.jsonResult = jsonResult;
    }

    public String getConditionParams() {
        return conditionParams;
    }

    public void setConditionParams(String conditionParams) {
        this.conditionParams = conditionParams;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Map<String, Object> getResponseMap() {
        return responseMap;
    }

    public void setResponseMap(Map<String, Object> responseMap) {
        this.responseMap = responseMap;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private String contextPath = ServletActionContext.getServletContext().getContextPath();

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    /**
     * easyUI 提交表单，返回成功数据时需要使用这种方式
     */
    protected void write(){
        ServletActionContext.getResponse().setContentType("text/html");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        try {
            ServletActionContext.getResponse().getWriter().write(JSON.toJSONString(jsonResult));
            ServletActionContext.getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理查询参数
     * @return
     */
    protected void setConditions(PagingSortQuery<E> pagingSortQueryHelper){
        if (pagingSortQueryHelper == null || conditionParams == null){
            return;
        }
        JSONArray jsonArray = JSON.parseArray(conditionParams);
        List<String> fields = ReflectHelper.fields(entity.getClass());
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (String fieldName : fields){
                /**
                 * 其它属性条件
                 */
                if (jsonObject.get(fieldName) == null || "".equals(jsonObject.get(fieldName))){
                    continue;
                }
                pagingSortQueryHelper.getConditionsMap().put(fieldName, RuleEnum.searchByRule(jsonObject.get("rule").toString()));
                ReflectHelper.setFieldValue(fieldName,jsonObject.get(fieldName), entity);
                break;
            }
        }
    }

    @Override
    public E getModel() {
        if (entity == null){
            try {
                entity = (E) ReflectHelper.getGenericSuperclass(getClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    protected List<Long> id2list(){
        String[] idsArr = ids.split(",");
        List<Long> ids = new ArrayList<Long>();
        for (String id : idsArr){
            ids.add(Long.valueOf(id));
        }
        return ids;
    }

    protected String paramError(){
        jsonResult.setMsg(ResultEnum.PARAM_EMPTY.getDesc());
        jsonResult.setCode(ResultEnum.PARAM_EMPTY.getCode());
        this.write();
        return null;
    }
}
