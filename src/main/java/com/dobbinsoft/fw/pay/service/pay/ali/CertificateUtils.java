package com.dobbinsoft.fw.pay.service.pay.ali;

import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.support.utils.DigestUtils;
import com.dobbinsoft.fw.support.utils.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CertificateUtils {
    private static final Map<String, String> certSNCache = new ConcurrentHashMap<>();
    private static final Map<String, String> rootCertSNCache = new ConcurrentHashMap<>();

    private static final BouncyCastleProvider provider;

    static {
        // 注册 Bouncy Castle 提供者
        provider = new BouncyCastleProvider();
        Security.addProvider(provider);
    }

    /**
     * 获取证书序列号
     * @param certPath X.509证书文件路径
     * @return 返回证书序列号
     * @throws MatrixPayException
     */
    public static String getCertSN(String certPath) throws MatrixPayException {
        if (certSNCache.containsKey(certPath)) {
            return certSNCache.get(certPath);
        }

        try (FileInputStream bais = new FileInputStream(certPath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(bais);
            return md5((cert.getIssuerX500Principal().getName() + cert.getSerialNumber()).getBytes());
        } catch (CertificateException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            throw new MatrixPayException("Failed to get certificate serial number", e);
        }
    }

    /**
     * 获取根证书序列号
     * @param rootCertPath 根证书路径
     * @return 返回根证书序列号
     * @throws MatrixPayException
     */
    public static String getRootCertSN(String rootCertPath) throws MatrixPayException {
        if (rootCertSNCache.containsKey(rootCertPath)) {
            return rootCertSNCache.get(rootCertPath);
        }

        try (FileInputStream fis = new FileInputStream(rootCertPath);) {

            String cert = StreamUtils.copyToString(fis, StandardCharsets.UTF_8);

            X509Certificate[] certificates = readPemCertChain(cert);

            String rootCertSN = null;
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (X509Certificate c : certificates) {
                if (c.getSigAlgOID().startsWith("1.2.840.113549.1.1")) {
                    md.update((c.getIssuerX500Principal().getName() + c.getSerialNumber()).getBytes());
                    String certSN = new BigInteger(1, md.digest()).toString(16);
                    //BigInteger会把0省略掉，需补全至32位
                    certSN = fillMD5(certSN);
                    if (StringUtils.isEmpty(rootCertSN)) {
                        rootCertSN = certSN;
                    } else {
                        rootCertSN = rootCertSN + "_" + certSN;
                    }
                }

            }
            return rootCertSN;
        } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new MatrixPayException("Failed to get root certificate serial number", e);
        }
    }

    private static X509Certificate[] readPemCertChain(String cert) throws CertificateException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(cert.getBytes());
        CertificateFactory factory = CertificateFactory.getInstance("X.509", provider);
        Collection<? extends Certificate> certificates = factory.generateCertificates(inputStream);
        return certificates.toArray(new X509Certificate[certificates.size()]);
    }

    private static String fillMD5(String md5) {
        return md5.length() == 32 ? md5 : fillMD5("0" + md5);
    }

    /**
     * 支付宝定义的MD5（专用）
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String md5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        String certSN = new BigInteger(1, md.digest()).toString(16);
        //BigInteger会把0省略掉，需补全至32位
        certSN = fillMD5(certSN);
        return certSN;
    }

}
