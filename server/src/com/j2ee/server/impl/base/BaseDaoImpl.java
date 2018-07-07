package com.j2ee.server.impl.base;

import com.j2ee.server.enums.Constant;
import com.j2ee.server.publicinterface.base.IBaseDao;
import com.j2ee.server.core.CriteriaQuery;
import com.j2ee.server.core.PagingSortQuery;
import com.j2ee.server.utils.ReflectHelper;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lzy on 2017/8/27.
 */
@Stateless(name = Constant.BaseDaoImpl)
@Local(IBaseDao.class)
@SuppressWarnings("all")
public class BaseDaoImpl<E> implements IBaseDao<E>{

    @PersistenceContext(unitName = "global_db")
    protected EntityManager entityManager;

    @Override
    public String insert(E orm) {
        try {
            entityManager.persist(orm);
        }catch (Exception ex){
            ex.printStackTrace();
            return Constant.Faild;
        }
        return Constant.Success;
    }

    @Override
    public String update(E orm) {
        try{
            Class clazz = orm.getClass();
            Field idField = clazz.getDeclaredField("id");
            if (idField == null || ReflectHelper.isIgnoreReflect(idField)){
                throw new RuntimeException("id field not null or is ignore reflect annotation");
            }
            Method idMethod = clazz.getMethod("getId", new Class[]{});
            if (idMethod == null){
                throw new RuntimeException("not found getId method");
            }
            Object idValue = idMethod.invoke(orm, new Object[]{});
            if (idValue == null){
                throw new RuntimeException("id value is null");
            }
            if (!(idValue instanceof Long)){
                throw new RuntimeException("id value type required is Long");
            }
            E oldOrm = findById(orm.getClass(), Long.valueOf(idValue.toString()));

            Field[] fields = clazz.getDeclaredFields();
            String getMethod = null;
            String setMethod = null;
            String methodSubStr = null;
            Method method = null;
            Object obj = null;
            for (Field field : fields){
                /**
                 * 过滤 id 属性
                 */
                if ("id".equals(field.getName()) || ReflectHelper.isIgnoreReflect(field)){
                    continue;
                }
                methodSubStr = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                /**
                 * 拼接get方法String
                 */
                getMethod = "get" + methodSubStr;
                /**
                 * 获得传入实体的当前遍历属性的get方法
                 */
                method = clazz.getMethod(getMethod, new Class[]{});
                if (method == null){
                    continue;
                }
                /**
                 * 过滤值为null的字段，换句话就是不update值为Null的列
                 */
                obj = method.invoke(orm, new Object[]{});
                if (obj == null){
                    continue;
                }
                /**
                 * 拼接set方法String
                 */
                setMethod = "set" + methodSubStr;
                /**
                 * 获得传入实体的当前遍历属性的set方法
                 */
                method = clazz.getMethod(setMethod, field.getType());
                method.invoke(oldOrm, obj);
            }

            entityManager.persist(oldOrm);
        }catch (Exception ex){
            ex.printStackTrace();
            return Constant.Faild;
        }
        return Constant.Success;
    }

    @Override
    public String deleteInId(Class clazz, List<Long> ids) {
        try{
            StringBuilder jqpl = new StringBuilder("delete from ").append(clazz.getSimpleName());
            jqpl.append(" where id in(");
            for (Long id : ids){
                jqpl.append(String.valueOf(id)).append(",");
            }
            jqpl.deleteCharAt(jqpl.toString().length() -1);
            jqpl.append(")");

            Query query = entityManager.createQuery(jqpl.toString());
            query.executeUpdate();
            return Constant.Success;
        }catch (Exception ex){
            ex.printStackTrace();
            return Constant.Faild;
        }
    }

    @Override
    public PagingSortQuery<E> findByPaging(E orm, PagingSortQuery<E> pagingSortQueryHelper) {
        try{
            CriteriaQuery<E> conditionAndSortHelper = new CriteriaQuery<E>(entityManager, orm.getClass(), pagingSortQueryHelper);
            conditionAndSortHelper.executeQueryList(orm);
            return pagingSortQueryHelper;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public  E findById(Class clazz, Long id) {
        try{
            return (E) entityManager.find(clazz, id);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
