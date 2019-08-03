package com.cdc.inventorysystem.common.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @Author: laichengfeng
 * @Description:
 * @Date: 2019/05/15 16:00
 */
public class RSAUtils {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String ALGORITHM = "RSA";

    private static final String DEFAULT_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI04v1+nLaTIfAppJWRxA4QnsM0928nLFOVj2Ot/ygJR94Jl5KDlBzeZC/OEeYj5krcQdiTmGEX+fe3bPjj2xqtq7Go2p449W3JSyLK5yodloB9Vl/8YoEp1heteqlGr5d9H1ok/RFwLaOh5MQxsl6AyIEqcc4umZIu+biU3mDGZAgMBAAECgYAorLuSFVi0KXpwlTBL6bEVk0j9FGAFkxSFrWiBC+FnIaykRiy/mrelb9NdZD6NETUMvRUKM6uyHUawB4G4bntx3bNGJVcahuWht1mTxpfGB1Em+w7vsZk+Krb/QGi7yleTVkm5aivDHcc7EE11r9mdNl3UsH/ZklKDuaacv0U4wQJBANTjwJJxoMjSEei5Q9IRBKu3j+qvbWM+8uh/wOF3SGaY9oKdA6S0TRMzrB9Ic4EMbqFShQJKPw+NVSNqa9gvm/0CQQCp0bXylUx40fQDDSqobCS5s9p+vM6tSNMhUxBnSHY2/MLTQr+K+jS0sM2U3rJ0GO5n6cbtQ11ge3Nqiecv9+jNAkBFkhw3f0yk9AmIPSsNtiQJndY5QpUDzumA02cMB3gwlyJP8tHi/E0HzmYcXwfoD1EEBTyUUAElqvE8vqV5o61dAkBkvOefQNu9bGbBDKV6hbR/iOabeitsdh/wy9KWQlEGK4jzQ8xWiDiN27gIkNbxsfxzHiYIh4az/meoEqhXN0wdAkBRARhEG3NZfpXoe+JXVYQER4tW1Oxh0x/9BZ2IddaDawbTpQ6OfHtVjUt+J4TGW4GpWxEn0f61uhQPL/JF2I9A";

    private static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNOL9fpy2kyHwKaSVkcQOEJ7DNPdvJyxTlY9jrf8oCUfeCZeSg5Qc3mQvzhHmI+ZK3EHYk5hhF/n3t2z449sarauxqNqeOPVtyUsiyucqHZaAfVZf/GKBKdYXrXqpRq+XfR9aJP0RcC2joeTEMbJegMiBKnHOLpmSLvm4lN5gxmQIDAQAB";

    private static final String DEFAULT_CHARSET = "utf-8";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA签名
     *
     * @param content      待签名数据
     * @param privateKey   商户私钥
     * @param inputCharset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String inputCharset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(inputCharset));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            logger.error("[op_result: sign error, content:{}, privateKey:{}, inputCharset:{}]", content, privateKey, inputCharset, e);
        }
        return null;
    }

    public static String sign(String content, String inputCharset) {
        return sign(content, DEFAULT_PRIVATE_KEY, inputCharset);
    }

    public static String sign(String content) {
        return sign(content, DEFAULT_CHARSET);
    }

    /**
     * RSA验签名检查
     *
     * @param content      待签名数据
     * @param sign         签名值
     * @param publicKey    公钥
     * @param inputCharset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String publicKey, String inputCharset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(inputCharset));
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            logger.error("[op_result: verify error, content:{}, sign:{}, publicKey:{}, inputCharset:{}]",
                    content, sign, publicKey, inputCharset, e);
        }
        return false;
    }

    public static boolean verify(String content, String sign, String inputCharset) {
        return verify(content, sign, DEFAULT_PUBLIC_KEY, inputCharset);
    }

    public static boolean verify(String content, String sign) {
        return verify(content, sign, DEFAULT_CHARSET);
    }

    private static void doFinal(Cipher cipher, InputStream ins, ByteArrayOutputStream writer, int bits) throws Exception{
        byte[] buf = new byte[bits];
        int bufl;
        while ((bufl = ins.read(buf)) != -1) {
            byte[] block;
            if (buf.length == bufl) {
                block = buf;
            } else {
                block = Arrays.copyOf(buf, bufl);
            }
            writer.write(cipher.doFinal(block));
        }
    }

    /**
     * 私钥加密
     * @param content
     * @param privateKey
     * @param inputCharset
     * @return
     * @throws Exception
     */
    public static String encryptByPriKey(String content, String privateKey, String inputCharset) throws Exception {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, priKey);
            InputStream ins = new ByteArrayInputStream(content.getBytes(inputCharset));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            //rsa加密的字节大小最多是117，将需要加密的内容，按117位拆开加密
            doFinal(cipher, ins, writer, MAX_ENCRYPT_BLOCK);
            return Base64.encode(writer.toByteArray());
        } catch (Exception e) {
            throw e;
        }
    }

    public static String encryptByPriKey(String content, String inputCharset) throws Exception {
        return encryptByPriKey(content, DEFAULT_PRIVATE_KEY, inputCharset);
    }

    public static String encryptByPriKey(String content) throws Exception {
        return encryptByPriKey(content, DEFAULT_CHARSET);
    }

    /**
     * 公钥解密
     *
     * @param content      密文
     * @param publicKey    公钥
     * @param inputCharset 编码格式
     * @return 解密后的字符串
     */
    public static String decryptByPubKey(String content, String publicKey, String inputCharset) throws Exception {
        try {
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decode(publicKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PublicKey publickey = keyf.generatePublic(pubX509);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publickey);
            InputStream ins = new ByteArrayInputStream(Base64.decode(content));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
            doFinal(cipher, ins, writer, MAX_DECRYPT_BLOCK);
            return new String(writer.toByteArray(), inputCharset);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String decryptByPubKey(String content, String inputCharset) throws Exception {
        return decryptByPubKey(content, DEFAULT_PUBLIC_KEY, inputCharset);
    }

    public static String decryptByPubKey(String content) throws Exception {
        return decryptByPubKey(content, DEFAULT_CHARSET);
    }

    /**
     * 公钥加密
     * @param content
     * @param publicKey
     * @param inputCharset
     * @return
     */
    public static String encryptByPubKey(String content, String publicKey, String inputCharset) throws Exception {
        try {
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decode(publicKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PublicKey publickey = keyf.generatePublic(pubX509);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publickey);
            InputStream ins = new ByteArrayInputStream(content.getBytes(inputCharset));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            doFinal(cipher, ins, writer, MAX_ENCRYPT_BLOCK);
            return Base64.encode(writer.toByteArray());
        } catch (Exception e) {
            throw e;
        }
    }

    public static String encryptByPubKey(String content, String inputCharset) throws Exception {
        return encryptByPubKey(content, DEFAULT_PUBLIC_KEY, inputCharset);
    }

    public static String encryptByPubKey(String content) throws Exception {
        return encryptByPubKey(content, DEFAULT_CHARSET);
    }

    /**
     * 私钥解密
     * @param content
     * @param privateKey
     * @param inputCharset
     * @return
     * @throws Exception
     */
    public static String decryptByPriKey(String content, String privateKey, String inputCharset) throws Exception{
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            InputStream ins = new ByteArrayInputStream(Base64.decode(content));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            doFinal(cipher, ins, writer, MAX_DECRYPT_BLOCK);
            return new String(writer.toByteArray(), inputCharset);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String decryptByPriKey(String content, String inputCharset) throws Exception {
        return decryptByPriKey(content, DEFAULT_PRIVATE_KEY, inputCharset);
    }

    public static String decryptByPriKey(String content) throws Exception {
        return decryptByPriKey(content, DEFAULT_CHARSET);
    }

    /**
     * 得到密钥、公钥字符串（经过base64编码）
     *
     * @param key 密钥、公钥
     * @return
     */
    public static String getKeyString(Key key) {
        return Base64.encode(key.getEncoded());
    }

    /**
     * 生成密钥对
     * @throws Exception
     */
    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
        //密钥位数
        keyPairGen.initialize(1024);
        //密钥对
        return keyPairGen.generateKeyPair();
    }

    public static void main(String[] args) throws Exception {
        String testStr = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\r\n" +
                         "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\r\n" +
                         "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc\r\n" +
                         "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd\r\n" +
                         "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee\r\n";
        String encrypt = encryptByPubKey(testStr);
        System.out.println(encrypt);
        System.out.println(decryptByPriKey(encrypt));
    }
}
