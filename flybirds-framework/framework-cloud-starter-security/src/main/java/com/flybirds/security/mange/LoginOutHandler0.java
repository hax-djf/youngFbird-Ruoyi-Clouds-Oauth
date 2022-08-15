package com.flybirds.security.mange;

import com.flybirds.security.model.OutTask;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @author :flybirds
 */
public interface LoginOutHandler0 {

     Disruptor<OutTask> getDisruptor();

     void userExit(String... tokenValue);

}
