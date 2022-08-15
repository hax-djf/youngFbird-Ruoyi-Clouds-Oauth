package com.flybirds.common.util.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author :flybirds
 * @create :2021-05-20 22:28:00
 * @description :
 */
public class AESUtil {

    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static  final Integer keyLength = 128;

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param solt 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String solt) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(solt));

            byte[] result = cipher.doFinal(byteContent);

            return Base64Utils.encodeToString(result);
        } catch (Exception e) {
           e.printStackTrace();
            log.error("加密失败 content{}，solt{}",content,solt);
        }

        return null;
    }

    /**
     * AES 解密操作
     * @param content
     * @param solt
     * @return
     */
    public static String decrypt(String content, String solt) {

        String decrypt = null;
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(solt));
            byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));
            decrypt= new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("解密失败 content{}，solt{}",content,solt);
        }

        return decrypt;
    }

    /**
     * 生成加密秘钥
     * @param solt 密钥
     * @return
     */
    private static SecretKeySpec getSecretKey(String solt) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        SecretKeySpec secretKeySpec = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(solt.getBytes());
            kg.init(keyLength, random);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            secretKeySpec =  new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            log.error("获取密钥失败，solt{}",solt);
        }
        return secretKeySpec;
    }
}
