package com.flybirds.oauth.adapter.approval;

import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;

import javax.sql.DataSource;

/**
 * 确认授权信息存储
 *
 * @author : flybirds
 */
public class CustomApprovalStore extends JdbcApprovalStore {

    public CustomApprovalStore(DataSource dataSource) {
        super(dataSource);
    }
}
