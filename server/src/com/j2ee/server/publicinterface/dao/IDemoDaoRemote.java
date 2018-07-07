package com.j2ee.server.publicinterface.dao;

import com.j2ee.server.orm.TDemoEntity;
import com.j2ee.server.publicinterface.base.IBaseDao;

import javax.ejb.Remote;

/**
 * Created by lzy on 2017/8/28.
 */
@Remote
public interface IDemoDaoRemote extends IBaseDao<TDemoEntity> {
}
