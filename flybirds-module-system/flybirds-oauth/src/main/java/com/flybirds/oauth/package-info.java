/**
 * 改版在线用户处理
 * 1.废除强退用户的 token数据存储
 * 处理方案：
 * 在返回用户信息的时候，将用户的token直接进行加密返回即可，使用jti+userName的Md5值做为盐值做为的形式加密，去除oauth秘钥
 * 在用户强制退出的时候，使用jti和userName解密，将token数据解密操作
 * 2.改版 user_agent_entity 的数据 不在存储到token中
 *
 */
package com.flybirds.oauth;
