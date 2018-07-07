package com.j2ee.server.impl.service;

import com.j2ee.server.enums.Constant;
import com.j2ee.server.impl.base.BaseServiceImpl;
import com.j2ee.server.orm.TDemoEntity;
import com.j2ee.server.publicinterface.base.IBaseDao;
import com.j2ee.server.publicinterface.dao.IDemoDaoLocal;
import com.j2ee.server.publicinterface.service.IDemoServiceLocal;
import com.j2ee.server.publicinterface.service.IDemoServiceRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by lzy on 2017/8/28.
 */
@Stateless(name = Constant.DemoServiceImpl)
public class DemoServiceImpl extends BaseServiceImpl<TDemoEntity> implements IDemoServiceLocal, IDemoServiceRemote {

    /**
     * 注入DAO
     * @param iBaseDao
     */
    @EJB(beanName = Constant.DemoDaoImpl, beanInterface = IDemoDaoLocal.class)
    @Override
    protected void setDao(IBaseDao iBaseDao) {
        super.setDao(iBaseDao);
    }
}
