package com.flybirds.system.sysMonitor.controller;

/**
 * redis 缓存监控
 *
 * @author :flybirds
 */

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.annotation.PreAuthorize;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 缓存监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/cache")
public class RedisCachecontroller {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PreAuthorize(hasAnyPermi="monitor:cache:list")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        Properties info = (Properties) stringRedisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) stringRedisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = stringRedisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return AjaxResult.success(result);
    }
}
