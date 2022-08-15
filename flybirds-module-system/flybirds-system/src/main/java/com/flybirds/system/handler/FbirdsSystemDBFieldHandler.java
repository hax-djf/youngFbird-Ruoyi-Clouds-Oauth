package com.flybirds.system.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.flybirds.common.util.thread.Threads;
import com.flybirds.mybatis.core.basepojo.MpBaseEntity;
import com.flybirds.security.core.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * mybatis_plus的配置
 *
 * @author : flybirds
 */
@Component
public class FbirdsSystemDBFieldHandler implements MetaObjectHandler {

    private final Logger log = LoggerFactory.getLogger(FbirdsSystemDBFieldHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {

        Date current = new Date();

        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof MpBaseEntity) {
            log.info("flybirds-system  start insert fill check ongoing ....线程名称 {} ",Threads.getName());
            MpBaseEntity baseDO = (MpBaseEntity) metaObject.getOriginalObject();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getCreateTime())) {
                this.strictInsertFill(metaObject, "createTime", Date.class ,current);
            }
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
//            if (Objects.isNull(baseDO.getCreateUser())) {
//                this.strictInsertFill(metaObject, "createUser", Long.class, SecurityUtils.getUserId());
//            }
            if (Objects.isNull(baseDO.getCreateUser())) {
                this.strictInsertFill(metaObject, "createName", String.class, SecurityUtils.getUsername());
            }

            if (Objects.isNull(baseDO.getUpdateTime())) {
                this.strictInsertFill(metaObject, "updateTime", Date.class ,current);
            }
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
//            if (Objects.isNull(baseDO.getUpdateUser())) {
//                this.strictInsertFill(metaObject, "updateUser", Long.class, SecurityUtils.getUserId());
//            }
            if (Objects.isNull(baseDO.getUpdateName())) {
                this.strictInsertFill(metaObject, "updateName", String.class, SecurityUtils.getUsername());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
//        Object updateUser = getFieldValByName("updateUser", metaObject);
        Object updateName = getFieldValByName("updateName", metaObject);
        Object updateTime = getFieldValByName("updateTime", metaObject);

//        if (Objects.isNull(updateUser)) {
//            this.strictInsertFill(metaObject, "updateUser", Long.class, SecurityUtils.getUserId());
//        }
        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        if (Objects.isNull(updateName)) {
            this.strictInsertFill(metaObject, "updateName", String.class, SecurityUtils.getUsername());
        }
        // 更新时间
        if (Objects.isNull(updateTime)) {
            this.strictInsertFill(metaObject, "updateTime",Date.class,new Date()); // 起始版本 3.3.3(推荐)
        }
    }
}
