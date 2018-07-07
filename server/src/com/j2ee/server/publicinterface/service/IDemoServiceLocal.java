package com.j2ee.server.publicinterface.service;

import com.j2ee.server.orm.TDemoEntity;
import com.j2ee.server.publicinterface.base.IBaseService;

import javax.ejb.Local;

/**
 * Created by lzy on 2017/8/28.
 */
@Local
public interface IDemoServiceLocal extends IBaseService<TDemoEntity> {
}
