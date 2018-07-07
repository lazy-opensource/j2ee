package com.j2ee.server.core;

import com.j2ee.server.annotation.RangeQuery;
import com.j2ee.server.enums.RuleEnum;
import com.j2ee.server.utils.DateUtil;
import com.j2ee.server.utils.ReflectHelper;
import com.j2ee.server.utils.StringHelper;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by laizhiyuan on 2017/8/28.
 *
 * <p>
 *     条件辅助类
 * </p>
 */
@SuppressWarnings("all")
public class CriteriaQuery<E> {

    private EntityManager entityManager;
    private Class clazz;
    private PagingSortQuery<E> pagingSortQueryHelper;
    private CriteriaBuilder criteriaBuilder;
    private javax.persistence.criteria.CriteriaQuery<E> criteriaQuery;
    private Root<E> root;
    private List<Predicate> predicateList = new ArrayList<Predicate>();
    private Object fieldValue;
    private String fieldName;
    private String fieldType;
    private List<Order> orders = new ArrayList<Order>();
    private Field field = null;
    private Method method = null;
    private String methodName = null;
    private String ruleValue = RuleEnum.EQ.getRule();

    public CriteriaQuery(EntityManager entityManager, Class clazz, PagingSortQuery<E> pagingSortQueryHelper) {
        if (entityManager == null){
            throw new RuntimeException("param entityManager not null");
        }
        if (clazz == null){
            throw new RuntimeException("param clazz not null");
        }
        if (pagingSortQueryHelper == null){
            throw new RuntimeException("param pagingSortHelper not null");
        }
        this.clazz = clazz;
        this.pagingSortQueryHelper = pagingSortQueryHelper;
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(clazz);
    }

    private void executeCount(){
        javax.persistence.criteria.CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        root = cq.from(clazz);
        cq.select(criteriaBuilder.count(root));
        cq.where(list2Arr());
        TypedQuery<Long> typedQuery = entityManager.createQuery(cq);
        Long count = typedQuery.getSingleResult();
        pagingSortQueryHelper.setTotal(Integer.valueOf(count.toString()));

    }


    public PagingSortQuery<E> executeQueryList(E orm) throws Exception {
        root = criteriaQuery.from(clazz);
        for (String fieldName : pagingSortQueryHelper.getConditionsMap().keySet()){
            if (StringHelper.isEmpty(fieldName)){
                continue;
            }
            this.fieldName = fieldName;
            this.method = ReflectHelper.getGetMethodByFieldName(this.fieldName, clazz);
            if (method == null){
                continue;
            }
            this.fieldValue = method.invoke(orm, new Object[]{});
            this.ruleValue = pagingSortQueryHelper.getConditionsMap().get(this.fieldName).getRule();
            field = ReflectHelper.getFieldByName(this.fieldName, clazz);
            if (field == null){
                System.out.println("==========================> not found field name:" + this.fieldName);
                continue;
            }
            //如果是范围查找
            if (ReflectHelper.isRangeQuery(field)){
                RangeQuery annotation = field.getAnnotation(RangeQuery.class);
                if (this.fieldName.endsWith(annotation.suffix())){
                    //去掉suffix，变为数据库映射字段
                    this.fieldName = this.fieldName.substring(0, this.fieldName.length() - annotation.suffix().length());
                    Transient t = field.getAnnotation(Transient.class);
                    if (t == null){
                        System.out.println("=========================> Warning: The Field:" + this.fieldName + " not Annotation @Transient");
                    }
                }else {
                    throw new RuntimeException("The end of this field " + this.fieldName + "  doesn't match " + annotation.suffix());
                }
            }
            fieldType = field.getType().getSimpleName();
            this.addCondition();
        }

        criteriaQuery.where(list2Arr());
        //如果客户端设置排序开关为true
        if (pagingSortQueryHelper.getSort()){
            setSort();
            criteriaQuery.orderBy(orders);
        }
        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery);

        //如果客户端设置分页开关为true
        if (pagingSortQueryHelper.getPaging()){
            typedQuery.setFirstResult(pagingSortQueryHelper.getBeginRow());
            typedQuery.setMaxResults(pagingSortQueryHelper.getPageSize());
        }
        List<E> list = typedQuery.getResultList();
        pagingSortQueryHelper.setRows(list);
        //如果客户端设置查总数开关为true
        if (pagingSortQueryHelper.getCount()){
            executeCount();
        }
        return pagingSortQueryHelper;
    }


    private Predicate[] list2Arr(){
        Predicate[] predicateArr = new Predicate[predicateList.size()];
        for (int i = 0; i < predicateList.size(); i++){
            predicateArr[i] = predicateList.get(i);
        }
        return predicateArr;
    }

    private void setSort(){
        fieldName = pagingSortQueryHelper.getSortField();
        try {
            fieldType = clazz.getDeclaredField(fieldName).getType().getSimpleName();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if ("desc".equalsIgnoreCase(pagingSortQueryHelper.getSortType())){
            orders.add(criteriaBuilder.desc(createPath()));
        }else {
            orders.add(criteriaBuilder.asc(createPath()));
        }
    }

    private void addCondition(){
        if (null == this.fieldType || null == this.fieldName || null == this.ruleValue
                || null == this.fieldValue || "".equals(this.fieldValue) || "".equals(this.fieldType)
                || "".equals(this.fieldName) || "".equals(this.ruleValue)){
            return;
        }
        this.addRule();
        return;
    }

    private void addRule(){
        if (RuleEnum.EQ.getRule().equals(ruleValue)){
            predicateList.add(criteriaBuilder.equal(createPath(), fieldValue));
        }else if (RuleEnum.LT.getRule().equals(ruleValue)){
            if (isTimestampType()){
                predicateList.add(criteriaBuilder.lessThan((Expression<Timestamp>) createPath(), Timestamp.valueOf(fieldValue.toString())));
            }else {
                predicateList.add(criteriaBuilder.lessThan((Expression<? extends Comparable>) createPath(), (Comparable)fieldValue));
            }
        }else if (RuleEnum.GT.getRule().equals(ruleValue)){
            if (isTimestampType()){
                predicateList.add(criteriaBuilder.greaterThan((Expression<Timestamp>) createPath(), Timestamp.valueOf(fieldValue.toString())));
            }else {
                predicateList.add(criteriaBuilder.greaterThan((Expression<? extends Comparable>) createPath(), (Comparable)fieldValue));
            }
        }else if (RuleEnum.LE.getRule().equals(ruleValue)){
            if (isTimestampType()){
                predicateList.add(criteriaBuilder.lessThanOrEqualTo((Expression<Timestamp>) createPath(), Timestamp.valueOf(fieldValue.toString())));
            }else {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo((Expression<? extends Comparable>) createPath(), (Comparable)fieldValue));
            }
        }else if (RuleEnum.GE.getRule().equals(ruleValue)){
            if (isTimestampType()){
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo((Expression<Timestamp>) createPath(), Timestamp.valueOf(fieldValue.toString())));
            }else {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo((Expression<? extends Comparable>) createPath(), (Comparable)fieldValue));
            }
        }else if (RuleEnum.IN.getRule().equals(ruleValue)){
            CriteriaBuilder.In in = criteriaBuilder.in(createPath());
            List<String> inValue = Arrays.asList(fieldValue.toString().split(","));
            for (String str : inValue){
                in.value(str);
            }
            predicateList.add(in);
        }else if (RuleEnum.NOT_NULL.getRule().equals(ruleValue)){
            predicateList.add(criteriaBuilder.isNotNull(createPath()));
        }else if (RuleEnum.IS_NULL.getRule().equals(ruleValue)){
            predicateList.add(criteriaBuilder.isNull(createPath()));
        }else if (RuleEnum.LIKE.getRule().equals(ruleValue)){
            if (fieldValue.toString().indexOf("%") < 0){
                fieldValue = "%" + fieldValue.toString() + "%";
            }
            predicateList.add(criteriaBuilder.like((Expression<String>) createPath(), fieldValue.toString()));
        }else if (RuleEnum.NOT_IN.getRule().equals(ruleValue)){
            CriteriaBuilder.In in = criteriaBuilder.in(createPath());
            List<String> inValue = Arrays.asList(fieldValue.toString().split(","));
            for (String str : inValue){
                in.value(str);
            }
            predicateList.add(criteriaBuilder.not(in));
        }else if (RuleEnum.NOT_EQ.getRule().equals(ruleValue)){
            predicateList.add(criteriaBuilder.notEqual(createPath(), fieldValue));
        }else if (RuleEnum.BETWEEN.getRule().equals(ruleValue)){
            if (fieldValue.toString().indexOf("@") < 0){
                throw new RuntimeException("Using between rule, two interval values must be connected with the @ symbol");
            }
            String[] tempArr = fieldValue.toString().split("@");
            if (tempArr == null || tempArr.length != 2){
                throw new RuntimeException("Two interval values must be connected with the @ symbol and can only have two values");
            }
            Timestamp[] tempDate = new Timestamp[2];
            tempDate[0] = DateUtil.string2Timestamp(tempArr[0], null);
            tempDate[1] = DateUtil.string2Timestamp(tempArr[1], null);
            fieldType = "Timestamp";
            predicateList.add(criteriaBuilder.between((Expression<? extends Timestamp>) createPath(), tempDate[0], tempDate[1]));
        }
    }

    private boolean isTimestampType(){
        if ("Timestamp".equals(fieldType)){
            return true;
        }
        return false;
    }

    private  Path<?> createPath(){
        if ("String".equalsIgnoreCase(fieldType)){
            Path<String> path = root.get(fieldName);
            return path;
        } else if ("BigDecimal".equalsIgnoreCase(fieldType)){
            Path<BigDecimal> path = root.get(fieldName);
            return path;
        }else if ("Integer".equalsIgnoreCase(fieldType)){
            Path<Integer> path = root.get(fieldName);
            return path;
        }else if ("Date".equalsIgnoreCase(fieldType)){
            Path<Date> path = root.get(fieldName);
            return path;
        }else if ("Time".equalsIgnoreCase(fieldType)){
            Path<Time> path = root.get(fieldName);
            return path;
        }else if("Double".equalsIgnoreCase(fieldType)){
            Path<Double> path = root.get(fieldName);
            return path;
        }else if ("Float".equalsIgnoreCase(fieldType)){
            Path<Float> path = root.get(fieldName);
            return path;
        }else if ("Long".equalsIgnoreCase(fieldType)){
            Path<Long> path = root.get(fieldName);
            return path;
        }else if ("Timestamp".equalsIgnoreCase(fieldType)){
            Path<Timestamp> path = root.get(fieldName);
            return path;
        }else {
            Path<Object> path = root.get(fieldName);
            return path;
        }
    }

    private Integer conversionToInteger(String value){
        try {
            return Integer.valueOf(value);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
