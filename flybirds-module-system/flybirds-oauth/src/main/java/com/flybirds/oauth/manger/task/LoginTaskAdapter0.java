package com.flybirds.oauth.manger.task;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.model.LoginTask;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadFactory;

/**
 * 订阅者 进行登录之后 用户个人信息存储管理
 *
 * @author :flybrids
 */
@Slf4j
public abstract class LoginTaskAdapter0 implements EventHandler<LoginTask>, LoginTaskHandler0{

    private Disruptor<LoginTask> disruptor;

    @Override
    public void onEvent(LoginTask event, long taskid, boolean endOfBatch) throws Exception {

       try {
           AssertUtil.isTrue(StringUtils.isNull(event),obj -> log.error("任务参数异常 任务序号id %s",obj) ,
                   taskid ,String.format("任务参数异常 任务序号id %s",taskid));
           AuthToken authToken =  event.getAuthToken();
           log.info("进入[Disruptor]任务提交  用户 [{}] 状态 [{}]",authToken.getUser_name(), event.getLoginStatus().getLabel());
           submitTask(authToken,event.getLoginChannel());
       }catch (Exception e){
           // TODO 任务日志先不开发
           e.printStackTrace();
           log.error("进入任务提交抛出异常信息 {}",e.getMessage());
       }
    }
    /**
     * 获取登录之后处理Disruptor处理队列
     * @return
     */
    @Override
    public Disruptor<LoginTask> getDisruptor() {
        return disruptor;
    }

    /**
     * 初始化队列对象->线程池提交任务
     */
    @PostConstruct
    protected void initDisuptor() {
        if (disruptor == null) {
            synchronized (this) {
                if (disruptor == null) {
                    int ringBufferSize = 32 * 1024;
                    ThreadFactory threadFactory = (Runnable runnable) -> new Thread(runnable);
                    disruptor = new Disruptor<>(() -> LoginTask.builder().build(), ringBufferSize, threadFactory);
                    //从spring工厂中获取本身的bean.  由于事务问题，无法同步进行，所以顺序进行
                    disruptor.handleEventsWith(SpringUtils.getBean(this.getClass()));
                    //可以使用then进行后续的订阅者的操作

                    //定义异常默认处理方式
                    disruptor.setDefaultExceptionHandler(new ExceptionHandler<LoginTask>() {
                        @Override
                        public void handleEventException(Throwable ex, long sequence, LoginTask event) {
                            log.error(String.format("【%s】用户登录完成数据异步处理异常,异常信息为：%s", event.getAuthToken().getUser_name(), ex));
                        }
                        @Override
                        public void handleOnStartException(final Throwable ex) {
                            log.error("Exception during onStart()", ex);
                        }

                        @Override
                        public void handleOnShutdownException(final Throwable ex) {
                            log.error("Exception during onShutdown()", ex);
                        }
                    });
                    //启动
                    disruptor.start();
                }
            }
        }
    }
}
