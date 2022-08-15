/**
 * 社交登录实现逻辑
 * 1.账号需要先绑定之后，才能进行登录
 * 2.将用户信息和 {@link com.flybirds.system.sysSocial.entity.SocialUserDO 进行绑定} 用户对->社交是 一对多的关系
 * 3.实现用户和社交绑定表之间的 绑定，取消绑定等动作
 *  难点: 社交登录通过code码，信息获取成功之后，获取到社交用户信息，如何实现自动登录
 *  方案1：实现一个社交登录，绑定oauth2中,通过接口调用来实现
 *  登录的动作在oauth工程
 *  绑定的解绑的动作在system工程
 *
 */
package com.flybirds.system.sysSocial;
