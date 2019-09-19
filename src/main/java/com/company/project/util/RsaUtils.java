package com.company.project.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class RsaUtils {
    private static final String ALGORITHM = "RSA";


    public static void main(String[] args) throws Exception {
        String content = "test";
        //生成key
        KeyPair keyPair = genKeyPair(512);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        //将key字符化
        String keyString = keyToString(publicKey);
        String keyString1 = keyToString(privateKey);
        System.out.println(keyString);
        System.out.println(keyString1);
        //将字符转为key
        Key key1 = stingToKey(keyString, 1);
        Key key2 = stingToKey(keyString1, 2);
        //加解密
        byte[] encrypt = encrypt(content.getBytes(), key2);
        byte[] decrypt = decrypt(encrypt, key1);
        System.out.println(new String(decrypt));
    }



    /**
     * 生成密钥对
     *
     * @param keyLength 密钥长度(最少512位)
     * @return 密钥对 公钥 keyPair.getPublic() 私钥 keyPair.getPrivate()
     * @throws Exception e
     */
    public static KeyPair genKeyPair(final int keyLength) throws Exception {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取公钥和私钥对象
     * @param keyString
     * @param type  钥类型：1 公钥 2 私钥
     * @return
     * @throws Exception
     */
    public static Key stingToKey(String keyString,int type) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            switch (type) {
                case 1:
                    X509EncodedKeySpec specPublic = new X509EncodedKeySpec(Base64.decodeBase64(keyString));
                    return keyFactory.generatePublic(specPublic);
                case 2:
                    PKCS8EncodedKeySpec specPrivate = new PKCS8EncodedKeySpec(Base64.decodeBase64(keyString));
                    return keyFactory.generatePrivate(specPrivate);
                default:return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将钥对象字符串化，以便存储
     *
     * @param key 公钥或私钥
     * @return
     */
    public static String keyToString(Key key) {
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 加密
     *
     * @param content 待加密数据
     * @param key     公钥或私钥
     * @return 加密内容
     * @throws Exception e
     */
    public static byte[] encrypt(final byte[] content, final Key key) throws Exception {
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(content);
    }

    /**
     * 解密
     *
     * @param content 加密数据
     * @param key     公钥或私钥
     * @return 解密内容
     * @throws Exception e
     */
    public static byte[] decrypt(final byte[] content, final Key key) throws Exception {
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(content);
    }


}
