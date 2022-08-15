package com.flybirds.common.util.ip;

import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.enums.login.AccountLoginScopeEnum;
import com.flybirds.common.util.str.StringUtils;
import eu.bitwalker.useragentutils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static com.flybirds.common.enums.login.AccountLoginScopeEnum.APP;
import static com.flybirds.common.enums.login.AccountLoginScopeEnum.PC;

/**
 * 操作系统的工具类
 *
 * @author :flybirds
 */
public class UserAgentUtils {

    private static Logger logger = LoggerFactory.getLogger(UserAgentUtils.class);

    /**
     * 根据http获取userAgent信息
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(SecurityConstant.USER_AGENT);
        return userAgent;
    }

    /**
     * 获取用户代理对象
     * @param request
     * @return
     */
    public static UserAgent getUserAgentClass(HttpServletRequest request){
        return UserAgent.parseUserAgentString(getUserAgent(request));
    }


    /**
     * 根据request获取userAgent，然后解析出osVersion
     * @param request
     * @return
     */
    public static String getOsVersion(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getOsVersion(userAgent);
    }

    /**
     * 根据userAgent解析出osVersion
     * @param userAgent
     * @return
     */
    public static String getOsVersion(String userAgent) {
        String osVersion = "";
        if(StringUtils.isBlank(userAgent))
            return osVersion;
        String[] strArr = userAgent.substring(userAgent.indexOf("(")+1,
                userAgent.indexOf(")")).split(";");
        if(null == strArr || strArr.length == 0)
            return osVersion;

        osVersion = strArr[1];
        logger.info("osVersion is:{}", osVersion);
        return osVersion;
    }

    /**
     * 获取操作系统对象
     * @param userAgent
     * @return
     */
    private static OperatingSystem getOperatingSystem(String userAgent) {
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        OperatingSystem operatingSystem =agent != null? agent.getOperatingSystem():null;
        return operatingSystem;
    }

    /**
     * 获取os：Windows/ios/Android
     * @param request
     * @return
     */
    public static String getOs(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getOs(userAgent);
    }

    /**
     * 获取os：Windows/ios/Android
     * @param userAgent
     * @return
     */
    public static String getOs(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);

        String os =String.format("未知系系统，疑似其他请求工具[%s]",userAgent);
        try {
            os = operatingSystem.getGroup().toString();
        }catch (Exception e){
            logger.error("获取os：Windows/ios/Android 失败{}",userAgent);
            e.printStackTrace();
        }
        logger.info("os is:{}", os);
        return os;
    }


    /**
     * 获取deviceType
     * @param request
     * @return
     */
    public static String getDevicetype(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getDevicetype(userAgent);
    }

    /**
     * 获取deviceType
     * @param userAgent
     * @return
     */
    public static String getDevicetype(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);

        String deviceType =String.format("未知系浏览器厂家类型，疑似其他请求工具[%s]",userAgent);
        try {
            deviceType = operatingSystem.getDeviceType().toString();
        }catch (Exception e){
            logger.error("获取device的生产厂家失败 {}",userAgent);
            e.printStackTrace();
        }
        logger.info("deviceType is:{}", deviceType);
        return deviceType;
    }

    /**
     * 获取操作系统的名字
     * @param request
     * @return
     */
    public static String getOsName(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getOsName(userAgent);
    }

    /**
     * 获取操作系统的名字
     * @param userAgent
     * @return
     */
    public static String getOsName(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);

        String osName =String.format("未知系统名字，疑似其他请求工具[%s]",userAgent);
        try {
            osName = operatingSystem.getName().toString();
        }catch (Exception e){
            logger.error("获取device的生产厂家失败 {}",userAgent);
            e.printStackTrace();
        }
        logger.info("osName is:{}", osName);
        return osName;
    }


    /**
     * 获取device的生产厂家
     * @param request
     */
    public static String getDeviceManufacturer(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getDeviceManufacturer(userAgent);
    }

    /**
     * 获取device的生产厂家
     * @param userAgent
     */
    public static String getDeviceManufacturer(String userAgent) {
        OperatingSystem operatingSystem =  getOperatingSystem(userAgent);
        String deviceManufacturer =String.format("未知浏览器生产厂家，疑似其他请求工具[%s]",userAgent);
        try {
            deviceManufacturer = operatingSystem.getManufacturer().toString();
        }catch (Exception e){
            logger.error("获取device的生产厂家失败 {}",userAgent);
            e.printStackTrace();
        }
        logger.info("deviceManufacturer is:{}", deviceManufacturer);
        return deviceManufacturer;
    }

    /**
     * 获取浏览器对象
     * @param agent
     * @return
     */
    public static Browser getBrowser(String agent) {
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        Browser browser = userAgent != null ?userAgent.getBrowser():null;
        return browser;
    }

    /**
     * 获取浏览器对象
     * @param request
     * @return
     */
    public static Browser getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(getUserAgent(request));
        Browser browser = userAgent != null ?userAgent.getBrowser():null;
        return browser;
    }


    /**
     * 获取browser name
     * @param request
     * @return
     */
    public static String getBorderName(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderName(userAgent);
    }

    /**
     * 获取browser name
     * @param userAgent
     * @return
     */
    public static String getBorderName(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String borderName = String.format("未知浏览器名称，疑似其他请求工具[%s]",userAgent);
        try {
             borderName = browser.getName();
        }catch (Exception e){
            logger.error("获取浏览器名称失败 {}",userAgent);
            e.printStackTrace();
        }
        logger.info("borderName is:{}", borderName);
        return borderName;
    }


    /**
     * 获取浏览器的类型
     * @param request
     * @return
     */
    public static String getBorderType(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderType(userAgent);
    }

    /**
     * 获取浏览器的类型
     * @param userAgent
     * @return
     */
    public static String getBorderType(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String borderType =String.format("未知浏览器类型，疑似其他请求工具[%s]",userAgent);

        try {
             borderType = browser.getBrowserType().getName();
        }catch (Exception e){
            logger.error("获取浏览器的类型失败 {}",userAgent);
            e.printStackTrace();
        }
        logger.info("borderType is:{}", borderType);
        return borderType;
    }

    /**
     * 获取浏览器组： CHROME、IE
     * @param request
     * @return
     */
    public static String getBorderGroup(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderGroup(userAgent);
    }

    /**
     * 获取浏览器组： CHROME、IE
     * @param userAgent
     * @return
     */
    public static String getBorderGroup(String userAgent) {
        Browser browser =  getBrowser(userAgent);

        String browerGroup = String.format("未知浏览器组,疑似其他请求工具[%s]",userAgent);
        try {
             browerGroup = browser.getGroup().getName();
        }catch (Exception e){
            logger.error("获取浏览器组失败 {}",userAgent);
            e.printStackTrace();
        }

        logger.info("browerGroup is:{}", browerGroup);
        return browerGroup;
    }

    /**
     * 获取浏览器的生产厂商
     * @param request
     * @return
     */
    public static String getBrowserManufacturer(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBrowserManufacturer(userAgent);
    }


    /**
     *  获取浏览器的生产厂商
     * @param userAgent
     * @return
     */
    public static String getBrowserManufacturer(String userAgent) {
        Browser browser =  getBrowser(userAgent);

        String browserManufacturer =String.format("未知浏览器厂家,疑似其他请求工具[%s]",userAgent);
        try {
             browserManufacturer = browser.getManufacturer().getName();
        }catch (Exception e){
            logger.error("浏览器的生产厂商获取失败 {}",userAgent);
            e.printStackTrace();
        }
        logger.info("browserManufacturer is:{}", browserManufacturer);
        return browserManufacturer;
    }


    /**
     * 获取浏览器使用的渲染引擎
     * @param request
     * @return
     */
    public static String getBorderRenderingEngine(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBorderRenderingEngine(userAgent);
    }

    /**
     * 获取浏览器使用的渲染引擎
     * @param userAgent
     * @return
     */
    public static String getBorderRenderingEngine(String userAgent) {
        Browser browser =  getBrowser(userAgent);

        String renderingEngine = String.format("未知浏览器引擎，疑似其他请求工具[%s]",userAgent);
        try {
            renderingEngine = browser.getRenderingEngine().name().toString();
        }catch (Exception e){
            logger.error("浏览器引擎获取失败 {}",userAgent);
            e.printStackTrace();
        }
        logger.info("renderingEngine is:{}", renderingEngine);
        return renderingEngine;
    }

    /**
     * 获取浏览器版本
     * @param request
     * @return
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getBrowserVersion(userAgent);
    }

    /**
     * 获取浏览器版本
     * @param userAgent
     * @return
     */
    public static String getBrowserVersion(String userAgent) {
        Browser browser =  getBrowser(userAgent);
        String borderVersion ="未知浏览器，疑似其他请求工具";
        try {
            Version version = browser.getVersion(userAgent);
            borderVersion = version == null ? borderVersion: version.getVersion();
        }catch (Exception e){
            logger.error("浏览器版本号获取失败 {}",userAgent);
            e.printStackTrace();
        }
        return borderVersion;
    }

    /**
     * 是否是手机
     * @param request
     * @return
     */
    public static boolean isMobile(HttpServletRequest request){
        return DeviceType.MOBILE.equals(getDeviceType(request));
    }

    /**
     * 是否是平板
     * @param request
     * @return
     */
    public static boolean isTablet(HttpServletRequest request){
        return DeviceType.TABLET.equals(getDeviceType(request));
    }

    /**
     * 是否IE版本是否小于等于IE8
     * @param request
     * @return
     */
    public static boolean isLteIE8(HttpServletRequest request){

        Browser browser = getBrowser(request);
        return Browser.IE5.equals(browser) || Browser.IE6.equals(browser)
                || Browser.IE7.equals(browser) || Browser.IE8.equals(browser);
    }

    /**
     * 是否是手机和平板
     * @param request
     * @return
     */
    public static boolean isMobileOrTablet(HttpServletRequest request){
        DeviceType deviceType = getDeviceType(request);
        return DeviceType.MOBILE.equals(deviceType) || DeviceType.TABLET.equals(deviceType);
    }

    /**
     * 获取设备类型
     * @param request
     * @return
     */
    public static DeviceType getDeviceType(HttpServletRequest request){
        return getUserAgentClass(request).getOperatingSystem().getDeviceType();
    }

    /**
     * 判断终端设备
     * @param requestHeader
     * @return
     */
    public static boolean isMobileDevice(String requestHeader) {
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[] { "android", "mac os", "managerproject",
                "windows phone", "okhttp" };
        if (requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();// 先转小写再判断
        for (int i = 0; i < deviceArray.length; i++) {
            if (requestHeader.indexOf(deviceArray[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static AccountLoginScopeEnum getDevice(String requestHeader) {
        String[] deviceArray = new String[] { "android", "mac os", "managerproject",
                "windows phone", "okhttp" };
        if (requestHeader == null)
            return PC;
        requestHeader = requestHeader.toLowerCase();// 先转小写再判断
        for (int i = 0; i < deviceArray.length; i++) {
            if (requestHeader.indexOf(deviceArray[i]) >= 0) {
                return APP;
            }
        }
        return PC;
    }

    //测试
    public static void main(String[] args) {
        String winUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36";

        System.out.println("浏览器组："+getBorderGroup(winUserAgent));
        System.out.println("浏览器名字："+getBorderName(winUserAgent));
        System.out.println("浏览器类型"+getBorderType(winUserAgent));
        System.out.println("浏览器生产商："+getBrowserManufacturer(winUserAgent));
        System.out.println("浏览器版本："+getBrowserVersion(winUserAgent));
        System.out.println("设备生产厂商:"+getDeviceManufacturer(winUserAgent));
        System.out.println("设备类型:"+getDevicetype(winUserAgent));
        System.out.println("设备操作系统："+getOs(winUserAgent));
        System.out.println("操作系统的名字："+getOsName(winUserAgent));
        System.out.println("操作系统的版本号："+getOsVersion(winUserAgent));
        System.out.println("操作系统浏览器的渲染引擎:"+getBorderRenderingEngine(winUserAgent));
    }

}
