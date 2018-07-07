package com.j2ee.server.publicinterface.dao;

import com.j2ee.server.orm.TDemoEntity;
import com.j2ee.server.publicinterface.base.IBaseDao;

import javax.ejb.Local;

/**
 * Created by lzy on 2017/8/28.
 */
@Local
public interface IDemoDaoLocal extends IBaseDao<TDemoEntity>{
}
