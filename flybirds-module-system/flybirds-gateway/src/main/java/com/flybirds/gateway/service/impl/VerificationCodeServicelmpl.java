package com.flybirds.gateway.service.impl;

import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.exception.CaptchaException;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.idtool.IdUtils;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.sign.Base64;
import com.flybirds.gateway.service.VerificationCodeService;
import com.flybirds.redis.manger.RedisCacheManger;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码实现处理
 *
 * @author ruoyi
 */
@Service
public class VerificationCodeServicelmpl implements VerificationCodeService {

    @Resource(name = "VerificationProducer")
    private Producer verificationProducer;

    @Resource(name = "VerificationProducerMath")
    private Producer verificationProducerMath;

    @Autowired
    private RedisCacheManger redisCacheManger;

    // 验证码类型
    private String captchaType = "math";

    /**
     * 生成验证码
     */
    @Override
    public AjaxResult createCapcha() throws IOException, CaptchaException
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstantEnum.CAPTCHA_CODE_KEY.buildSuffix(uuid);

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = verificationProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = verificationProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = verificationProducer.createText();
            image = verificationProducer.createImage(capStr);
        }

        redisCacheManger.setCacheObject(verifyKey, code, Constant.Time.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCapcha(String code, String uuid) throws CaptchaException
    {

        AssertUtil.isNotNull(code,new CaptchaException(MsgConstant.Captcha.CAPTCHA_NOT_NULL));

        AssertUtil.isNotNull(code,new CaptchaException(MsgConstant.Captcha.CAPTCHA_LOSS));

        String verifyKey = CacheConstantEnum.CAPTCHA_CODE_KEY.buildSuffix(uuid);

        String captcha = redisCacheManger.getCacheObject(verifyKey);

        redisCacheManger.deleteObject(verifyKey);

        AssertUtil.isTrue(!code.equalsIgnoreCase(captcha),new CaptchaException(MsgConstant.Captcha.CAPTCHA_ERROR));
    }
}
