package com.j2ee.server.publicinterface.service;

import com.j2ee.server.orm.TDemoEntity;
import com.j2ee.server.publicinterface.base.IBaseService;

import javax.ejb.Remote;

/**
 * Created by lzy on 2017/8/28.
 */
@Remote
public interface IDemoServiceRemote extends IBaseService<TDemoEntity> {
}
