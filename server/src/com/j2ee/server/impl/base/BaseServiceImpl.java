package com.j2ee.server.impl.base;

import com.j2ee.server.publicinterface.base.IBaseDao;
import com.j2ee.server.publicinterface.base.IBaseService;
import com.j2ee.server.core.PagingSortQuery;

import java.util.List;

/**
 * Created by lzy on 2017/8/28.
 */
public class BaseServiceImpl<E> implements IBaseService<E> {

    private IBaseDao iBaseDao;

    protected void setDao(IBaseDao iBaseDao){
        this.iBaseDao = iBaseDao;
    }

    @Override
    public String insert(E orm) {
        return iBaseDao.insert(orm);
    }

    @Override
    public String update(E orm) {
        return iBaseDao.update(orm);
    }

    @Override
    public String deleteInId(Class clazz, List<Long> ids) {
        return iBaseDao.deleteInId(clazz, ids);
    }

    @Override
    public PagingSortQuery<E> findByPaging(E orm, PagingSortQuery<E> pagingSortQueryHelper) {
        return iBaseDao.findByPaging(orm, pagingSortQueryHelper);
    }

    @Override
    public E findById(Class clazz, Long id) {
        return (E) iBaseDao.findById(clazz, id);
    }
}
