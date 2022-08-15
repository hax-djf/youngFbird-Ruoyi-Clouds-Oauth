package com.flybirds.oauth.core;

import com.flybirds.common.constant.SecurityConstant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * 客户端信息持久化，并且存入redis的缓存冲 {@link org.springframework.security.oauth2.client.token.JdbcClientTokenServices}
 *
 * @author: flybirds
 */
@Service(value = "RedisClientDetailsService")
public class RedisClientDetailsService extends JdbcClientDetailsService {

    public RedisClientDetailsService(DataSource dataSource) {
        super(dataSource);
        super.setSelectClientDetailsSql(SecurityConstant.DEFAULT_SELECT_STATEMENT);
        super.setFindClientDetailsSql(SecurityConstant.DEFAULT_FIND_STATEMENT);
    }

    /**
     *   value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
         key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
         condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL
     * @param clientId
     * @return
     */
    @Override
    @Cacheable(value = "oauth:client:details:", key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId)
    {
        return super.loadClientByClientId(clientId);
    }

}
