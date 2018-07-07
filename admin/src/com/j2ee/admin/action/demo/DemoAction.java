package com.j2ee.admin.action.demo;

import com.j2ee.admin.base.BaseAction;
import com.j2ee.admin.enums.ResultEnum;
import com.j2ee.admin.utils.EjbHelper;
import com.j2ee.server.core.PagingSortQuery;
import com.j2ee.server.enums.Constant;
import com.j2ee.server.orm.TDemoEntity;
import com.j2ee.server.publicinterface.service.IDemoServiceLocal;
import com.j2ee.server.utils.DateUtil;
import com.j2ee.server.utils.StringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by lzy on 2017/8/28.
 */
public class DemoAction extends BaseAction<TDemoEntity> {

    private static IDemoServiceLocal iDemoServiceLocal =
            (IDemoServiceLocal) EjbHelper.localByJndi(
                    EjbHelper.getJndi(IDemoServiceLocal.class,
                            Constant.DemoServiceImpl)
            );

    public String list(){

        return "list";
    }

    public String query() {
        PagingSortQuery<TDemoEntity> pagingSortHelper =
                new PagingSortQuery<TDemoEntity>();
        if (!StringUtils.isEmpty(getRows())){
            pagingSortHelper.setBeginRow(Integer.valueOf(getPage()));
        }
        if (!StringUtils.isEmpty(getPage())){
            pagingSortHelper.setPageSize(Integer.valueOf(getRows()));
        }
        if (!StringUtils.isEmpty(getSort())){
            pagingSortHelper.setSortField(getSort());
        }
        if (!StringUtils.isEmpty(getOrder())){
            pagingSortHelper.setSortType(getOrder());
        }
        setConditions(pagingSortHelper);
        TDemoEntity entity = getEntity();
        pagingSortHelper = iDemoServiceLocal.findByPaging(entity, pagingSortHelper);

        getResponseMap().put("rows", pagingSortHelper.getRows());
        getResponseMap().put("total", pagingSortHelper.getTotal());

//        PrintWriter pw = ServletActionContext.getResponse().getWriter();
//        pw.print(getJsonObject().toJSONString());
//        pw.close();
        return "query";
    }

    public String addDemo(){

        if (entity == null){
            return paramError();
        }
        try{
            entity.setCreateTime(DateUtil.getCurrentTimestamp(null));
            entity.setUpdateTime(entity.getCreateTime());
            String result = iDemoServiceLocal.insert(entity);
            if (!Constant.Success.equals(result)){
                jsonResult.setMsg(ResultEnum.FAILD.getDesc());
                jsonResult.setCode(ResultEnum.FAILD.getCode());
            }
        }catch (Exception ex){
            ex.printStackTrace();
            jsonResult.setMsg(ResultEnum.SYS_ERROR.getDesc());
            jsonResult.setCode(ResultEnum.SYS_ERROR.getCode());
            this.write();
            return null;
        }
        this.write();
        return null;
    }

    public String toEdit(){
        if (StringHelper.isEmpty(ids)){
            jsonResult.setMsg(ResultEnum.PARAM_EMPTY.getDesc());
            jsonResult.setCode(ResultEnum.PARAM_EMPTY.getCode());
            return SUCCESS;
        }

        TDemoEntity tDemoEntity = iDemoServiceLocal.findById(TDemoEntity.class, Long.valueOf(ids));
        setEntity(tDemoEntity);
        return "edit";
    }

    public String editDemo(){
        if (entity == null){
            return paramError();
        }
        try{
            entity.setUpdateTime(DateUtil.getCurrentTimestamp(null));
            String result = iDemoServiceLocal.update(entity);
            if (!Constant.Success.equals(result)){
                jsonResult.setMsg(ResultEnum.FAILD.getDesc());
                jsonResult.setCode(ResultEnum.FAILD.getCode());
            }
        }catch (Exception ex){
            ex.printStackTrace();
            jsonResult.setMsg(ResultEnum.SYS_ERROR.getDesc());
            jsonResult.setCode(ResultEnum.SYS_ERROR.getCode());
        }
        this.write();
        return null;
    }

    public String del(){
        if (ids == null){
            jsonResult.setMsg(ResultEnum.PARAM_EMPTY.getDesc());
            jsonResult.setCode(ResultEnum.PARAM_EMPTY.getCode());
            return SUCCESS;
        }
        try{

            String result = iDemoServiceLocal.deleteInId(TDemoEntity.class, id2list());
            if (!Constant.Success.equals(result)){
                jsonResult.setMsg(ResultEnum.FAILD.getDesc());
                jsonResult.setCode(ResultEnum.FAILD.getCode());
            }
        }catch (Exception ex){
            ex.printStackTrace();
            jsonResult.setMsg(ResultEnum.SYS_ERROR.getDesc());
            jsonResult.setCode(ResultEnum.SYS_ERROR.getCode());
        }
        return SUCCESS;
    }

}
