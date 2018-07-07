package com.j2ee.server.impl.dao;

import com.j2ee.server.enums.Constant;
import com.j2ee.server.impl.base.BaseDaoImpl;
import com.j2ee.server.orm.TDemoEntity;
import com.j2ee.server.publicinterface.dao.IDemoDaoLocal;
import com.j2ee.server.publicinterface.dao.IDemoDaoRemote;

import javax.ejb.Stateless;

/**
 * Created by lzy on 2017/8/28.
 */
@Stateless(name = Constant.DemoDaoImpl)
public class DemoDaoImpl extends BaseDaoImpl<TDemoEntity> implements IDemoDaoLocal, IDemoDaoRemote {
}
