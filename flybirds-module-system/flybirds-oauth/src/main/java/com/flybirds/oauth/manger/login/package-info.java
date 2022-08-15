/**
 *
 * @author :flybirds
 *
 * 社交登录 2 种情况
 *
 * 1、 第三方账号已经绑定了系统用户信息 , 通过社交绑定表获取绑定用户id，返回用户用户名，进行免密登录
 *
 * 2、第三方账号未进行绑定系统用户账号, 先进行账号密码登录，在进行远程绑定社交账号
 */
package com.flybirds.oauth.manger.login;