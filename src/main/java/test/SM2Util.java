package test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;

/**
 * need jars:
 * bcpkix-jdk15on-160.jar
 * bcprov-jdk15on-160.jar
 * <p>
 * ref:
 * https://tools.ietf.org/html/draft-shen-sm2-ecdsa-02
 * http://gmssl.org/docs/oid.html
 * http://www.jonllen.com/jonllen/work/164.aspx
 * <p>
 * 用BC的注意点：
 * 这个版本的BC对SM3withSM2的结果为asn1格式的r和s，如果需要直接拼接的r||s需要自己转换。下面rsAsn1ToPlainByteArray、rsPlainByteArrayToAsn1就在干这事。
 * 这个版本的BC对SM2的结果为C1||C2||C3，据说为旧标准，新标准为C1||C3||C2，用新标准的需要自己转换。下面changeC1C2C3ToC1C3C2、changeC1C3C2ToC1C2C3就在干这事。
 */
public class SM2Util {
    private static final Logger log = LoggerFactory.getLogger(SM2Util.class);
    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    private static ECDomainParameters ecDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());
    private static ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static byte[] hexToByte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            char[] arr = hex.toCharArray();
            byte[] b = new byte[hex.length() / 2];
            int i = 0;
            int j = 0;
            for (int l = hex.length(); i < l; ++j) {
                String swap = "" + arr[i++] + arr[i];
                int byteint = Integer.parseInt(swap, 16) & 255;
                b[j] = (new Integer(byteint)).byteValue();
                ++i;
            }
            return b;
        }
    }

    /**
     * @param msg
     * @param userId
     * @param privateKey
     * @return r||s，直接拼接byte数组的rs
     */
    public static byte[] signSm3WithSm2(byte[] msg, byte[] userId, PrivateKey privateKey) {
        return rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(msg, userId, privateKey));
    }

    /**
     * @param msg
     * @param userId
     * @param privateKey
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signSm3WithSm2Asn1Rs(byte[] msg, byte[] userId, PrivateKey privateKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature signer = Signature.getInstance("SM3withSM2", "BC");
            signer.setParameter(parameterSpec);
            signer.initSign(privateKey, new SecureRandom());
            signer.update(msg, 0, msg.length);
            byte[] sig = signer.sign();
            return sig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param msg
     * @param userId
     * @param rs        r||s，直接拼接byte数组的rs
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey) {
        return verifySm3WithSm2Asn1Rs(msg, userId, rsPlainByteArrayToAsn1(rs), publicKey);
    }

    /**
     * @param msg
     * @param userId
     * @param rs        in <b>asn1 format</b>
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature verifier = Signature.getInstance("SM3withSM2", "BC");
            verifier.setParameter(parameterSpec);
            verifier.initVerify(publicKey);
            verifier.update(msg, 0, msg.length);
            return verifier.verify(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * bc加解密使用旧标c1||c2||c3，此方法在加密后调用，将结果转化为c1||c3||c2
     *
     * @param c1c2c3
     * @return
     */
    private static byte[] changeC1C2C3ToC1C3C2(byte[] c1c2c3) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1; //sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
        final int c3Len = 32; //new SM3Digest().getDigestSize();
        byte[] result = new byte[c1c2c3.length];
        System.arraycopy(c1c2c3, 0, result, 0, c1Len); //c1
        System.arraycopy(c1c2c3, c1c2c3.length - c3Len, result, c1Len, c3Len); //c3
        System.arraycopy(c1c2c3, c1Len, result, c1Len + c3Len, c1c2c3.length - c1Len - c3Len); //c2
        return result;
    }


    /**
     * bc加解密使用旧标c1||c3||c2，此方法在解密前调用，将密文转化为c1||c2||c3再去解密
     *
     * @param c1c3c2
     * @return
     */
    private static byte[] changeC1C3C2ToC1C2C3(byte[] c1c3c2) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1; //sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
        final int c3Len = 32; //new SM3Digest().getDigestSize();
        byte[] result = new byte[c1c3c2.length];
        System.arraycopy(c1c3c2, 0, result, 0, c1Len); //c1: 0->65
        System.arraycopy(c1c3c2, c1Len + c3Len, result, c1Len, c1c3c2.length - c1Len - c3Len); //c2
        System.arraycopy(c1c3c2, c1Len, result, c1c3c2.length - c3Len, c3Len); //c3
        return result;
    }

    /**
     * c1||c3||c2
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] sm2Decrypt(byte[] data, PrivateKey key) {
        return sm2DecryptOld(changeC1C3C2ToC1C2C3(data), key);
    }

    /**
     * 文件内容解密
     *
     * @param sm2FileDecrypt
     * @return byte[]
     */
    public static boolean sm2FileDecrypt(String filePath, String key) {
        try {
            FileUtils.writeByteArrayToFile(new File(filePath), sm2Decrypt(Files.readAllBytes(Paths.get(filePath)), getPrivatekey(key)));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 文件内容加密
     *
     * @param sm2FileEncrypt
     * @return boolean
     */
    public static boolean sm2FileEncrypt(String filePath, String key) {
        try {
            FileUtils.writeByteArrayToFile(new File(filePath), SM2Util.sm2Encrypt(Files.readAllBytes(Paths.get(filePath)), getPublickey(key)));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * c1||c3||c2
     *
     * @param data
     * @param key
     * @return
     */

    public static byte[] sm2Encrypt(byte[] data, PublicKey key) {
        return changeC1C2C3ToC1C3C2(sm2EncryptOld(data, key));
    }

    /**
     * c1||c2||c3
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] sm2EncryptOld(byte[] data, PublicKey key) {
        BCECPublicKey localECPublicKey = (BCECPublicKey) key;
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * c1||c2||c3
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] sm2DecryptOld(byte[] data, PrivateKey key) {
        BCECPrivateKey localECPrivateKey = (BCECPrivateKey) key;
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(localECPrivateKey.getD(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(false, ecPrivateKeyParameters);
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sm4Encrypt(byte[] keyBytes, byte[] plain) {
        if (keyBytes.length != 16) throw new RuntimeException("err key length");
        if (plain.length % 16 != 0) throw new RuntimeException("err data length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher out = Cipher.getInstance("SM4/ECB/NoPadding", "BC");
            out.init(Cipher.ENCRYPT_MODE, key);
            return out.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sm4Decrypt(byte[] keyBytes, byte[] cipher) {
        if (keyBytes.length != 16) throw new RuntimeException("err key length");
        if (cipher.length % 16 != 0) throw new RuntimeException("err data length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher in = Cipher.getInstance("SM4/ECB/NoPadding", "BC");
            in.init(Cipher.DECRYPT_MODE, key);
            return in.doFinal(cipher);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] sm3(byte[] bytes) {
        SM3Digest sm3 = new SM3Digest();
        sm3.update(bytes, 0, bytes.length);
        byte[] result = new byte[sm3.getDigestSize()];
        sm3.doFinal(result, 0);
        return result;
    }

    private final static int RS_LEN = 32;

    private static byte[] bigIntToFixexLengthBytes(BigInteger rOrS) {
        // for sm2p256v1, n is 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
        // r and s are the result of mod n, so they should be less than n and have length<=32
        byte[] rs = rOrS.toByteArray();
        if (rs.length == RS_LEN) return rs;
        else if (rs.length == RS_LEN + 1 && rs[0] == 0) return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
        else if (rs.length < RS_LEN) {
            byte[] result = new byte[RS_LEN];
            Arrays.fill(result, (byte) 0);
            System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
            return result;
        } else {
            throw new RuntimeException("err rs: " + Hex.toHexString(rs));
        }
    }

    /**
     * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
     *
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     */
    private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer) {
        ASN1Sequence seq = ASN1Sequence.getInstance(rsDer);
        byte[] r = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(0)).getValue());
        byte[] s = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(1)).getValue());
        byte[] result = new byte[RS_LEN * 2];
        System.arraycopy(r, 0, result, 0, r.length);
        System.arraycopy(s, 0, result, RS_LEN, s.length);
        return result;
    }

    /**
     * BC的SM3withSM2验签需要的rs是asn1格式的，这个方法将直接拼接r||s的字节数组转化成asn1格式
     *
     * @param sign in plain byte array
     * @return rs result in asn1 format
     */
    private static byte[] rsPlainByteArrayToAsn1(byte[] sign) {
        if (sign.length != RS_LEN * 2) throw new RuntimeException("err rs. ");
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN, RS_LEN * 2));
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        try {
            return new DERSequence(v).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("EC", "BC");
            kpGen.initialize(ecParameterSpec, new SecureRandom());
            KeyPair kp = kpGen.generateKeyPair();
            return kp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BCECPrivateKey getPrivatekey(String prik) {
        BigInteger d = new BigInteger(1, hexToByte(prik));
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecParameterSpec);
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    public static BCECPublicKey getPublickey(String pubk2) {
        byte[] publicKey = hexToByte(pubk2);
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().decodePoint(publicKey), ecParameterSpec);
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    public static PublicKey getPublickeyFromX509File(File file) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            FileInputStream in = new FileInputStream(file);
            X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
//	            System.out.println(x509.getSerialNumber());
            return x509.getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHexString(byte[] byteArray) {
        final StringBuilder hexString = new StringBuilder("");
        if (byteArray == null || byteArray.length <= 0)
            return null;
        for (int i = 0; i < byteArray.length; i++) {
            int v = byteArray[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                hexString.append('0');
            }
            hexString.append(hv);
        }
        return hexString.toString().toLowerCase();
    }

    /**
     * SM2私钥签名（不使用行内国密算法库）
     */
    public static byte[] sm3withsm2Signature2(String privateKeyStr, String dataStr) throws Exception {
        byte[] key = Hex.decode(privateKeyStr);
        byte[] data = dataStr.getBytes();

        // 获得一条签名曲线
        ECParameterSpec spec = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        // 构造domain函数
        ECDomainParameters domainParameters = new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN(), spec.getH(), spec.getSeed());
        // 国密要求，ID默认值为1234567812345678
        ECPrivateKeyParameters privateKey = new ECPrivateKeyParameters(new BigInteger(1, key), domainParameters);
        ParametersWithID parameters = new ParametersWithID(privateKey, "1234567812345678".getBytes());

        // 初始化签名实例
        SM2Signer signer = new SM2Signer();
        signer.init(true, parameters);
        signer.update(data, 0, data.length);

        // 计算签名值
        byte[] signature = decodeDERSignature(signer.generateSignature());
        return signature;
    }

    private static byte[] decodeDERSignature(byte[] signature) throws Exception {
        ASN1InputStream stream = new ASN1InputStream(new ByteArrayInputStream(signature));
        try {
            ASN1Sequence primitive = (ASN1Sequence) stream.readObject();
            Enumeration enumeration = primitive.getObjects();
            BigInteger R = ((ASN1Integer) enumeration.nextElement()).getValue();
            BigInteger S = ((ASN1Integer) enumeration.nextElement()).getValue();
            byte[] bytes = new byte[64];
            byte[] r = format(R.toByteArray());
            byte[] s = format(S.toByteArray());
            System.arraycopy(r, 0, bytes, 0, 32);
            System.arraycopy(s, 0, bytes, 32, 32);
            return bytes;
        } catch (Exception var10) {
            throw new Exception();
        }
    }

    private static byte[] format(byte[] value) {
        if (value.length == 32) {
            return value;
        } else {
            byte[] bytes = new byte[32];
            if (value.length > 32) {
                System.arraycopy(value, value.length - 32, bytes, 0, 32);
            } else {
                System.arraycopy(value, 0, bytes, 32 - value.length, value.length);
            }

            return bytes;
        }
    }

    public static boolean verify(PublicKey publicKey, byte[] signResult, String plainText) {
        boolean result = false;
        try {
            Signature signature = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), new BouncyCastleProvider());
            signature.initVerify(publicKey);
            signature.update(plainText.getBytes());
            result = signature.verify(signResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        sm2FileDecrypt("D:\\file\\PAY_20210519.CSV", "bcf05d7642ee50a4d617eca885150bcd412bba1147d882f67bdc736dce5d4ec0");
    }

    public static void main2(String[] args) {
        byte[] data3 = null;
        String prik = "02A2D45F5C5A4A50415CE9F031CCFB20DA8172B35526779FA7A24B65BA54620A";
        String p = "{\"HEAD\":{\"xRtnLvl\":\"\",\"xWkeCod\":\"CISPRQA1\",\"xItvTms\":\"\",\"xCmmTyp\":\"\",\"xMacCod\":\"\",\"xTlrNbr\":\"\",\"xCmmRsv\":\"\",\"xTypCod\":\"\",\"xDocSiz\":\"\",\"xUsrPwd\":\"\",\"xPreIsu\":\"\",\"xRqsNbr\":\"\",\"xIsuCnl\":\"\",\"xHdrLen\":\"203\",\"xRtnCod\":\"SUC0000\",\"xIsuDat\":\"0\",\"xIsuTim\":\"0\",\"xMsgFlg\":\"\",\"xOrgIsu\":\"\",\"xSysCod\":\"\",\"xEntUsr\":\"\",\"xEncCod\":\"\",\"xDskSys\":\"\",\"xKeyVal\":\"\",\"xAppRsv\":\"\",\"xDevNbr\":\"\",\"xDalCod\":\"N\"},\"BODY\":{\"CISPRQA1Z1\":[{\"zRsvFld\":\"\",\"zMerMem\":\"123456789ABCDE\",\"zFleIdx\":\"\",\"zMerCod\":\"Y00006\",\"zSubCod\":\"E4\",\"zOrdNbr\":\"Y00006240913007744\",\"zReqAmt\":\"100.00\",\"zRtnCod\":\"01\",\"zReqTyp\":\"00\"}],\"$REQSEQ$\":[{\"xReqSeq\":\"CIS10HD05QO00400013N\"}]}}";
        String publicKey = "046BEA269EB98E8E8F764AABEAE34091207E297A0F7FCDC9D0DED55AF755C271955B58701EF34B3E8DAA04107AF4C3E8DCA83554DEAE450A6EAFFB9A654577FF72";
        byte[] data = sm2Encrypt(JSONObject.parseObject(p).toJSONString().getBytes(), getPublickey(publicKey));
        String data2 = new String(Base64.getEncoder().encode(data));
        System.out.println("data is:" + new String(Base64.getEncoder().encode(data)));
        System.out.println(Hex.toHexString(data));
        data3 = sm2Decrypt(Base64.getDecoder().decode(data2), getPrivatekey(prik));
        System.out.println(new String(data3));
        boolean is = verify(getPublickey(publicKey), data, new String(data3));
        System.out.println(is);
    }

    public static void main333(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CertPathBuilderException, InvalidKeyException, Exception, CertificateException {

//	        // 随便看看 ---------------------
//	        System.out.println("GMNamedCurves: ");
//        for (Enumeration e = GMNamedCurves.getNames(); e.hasMoreElements(); ) {
//            System.out.println(e.nextElement());
//        }
//	        System.out.println("sm2p256v1 n:"+x9ECParameters.getN());
//	        System.out.println("sm2p256v1 nHex:"+Hex.toHexString(x9ECParameters.getN().toByteArray()));


        // 生成公私钥对 ---------------------
        KeyPair kp = generateKeyPair();

        System.out.println(Hex.toHexString(kp.getPrivate().getEncoded()));
        System.out.println(Hex.toHexString(kp.getPublic().getEncoded()));

        System.out.println(kp.getPrivate().getAlgorithm());
        System.out.println(kp.getPublic().getAlgorithm());

        System.out.println(kp.getPrivate().getFormat());
        System.out.println(kp.getPublic().getFormat());

        System.out.println("private key d: " + ((BCECPrivateKey) kp.getPrivate()).getD());
        System.out.println("public key q:" + ((BCECPublicKey) kp.getPublic()).getQ()); //{x, y, zs...}

        byte[] msg = "message digest".getBytes();
        byte[] userId = "userId".getBytes();
        //  apisign = sm3withsm2Signature2("FFB39CE099F579502817869BD70597C09E7A45B39DE8F8FB7C76D5E65015B470", "appid=APPKEY&secret=APPSecret&sign=eyJwYXJhbWV0ZXJzIjogInBhcmFtMT1hJnBhcmFtMj1iYiIsICJ2YXJpYWJsZXMiOiBbInZhcjEiXX0K&timestamp=1587893708");
        byte[] sig = signSm3WithSm2(msg, userId, kp.getPrivate());
        System.out.println(Hex.toHexString(sig));
        System.out.println(verifySm3WithSm2(msg, userId, sig, kp.getPublic()));


        // 国密规范正式私钥
        //String prik = "4c0e2d8a6a0edfb1bd43aa59b396fd7022240d198555a300a6f71b4f9ed0c520";
//        String prik = "294B5550B6D468EECAE1383CF7B395A2B84452C620EF9234E68B729A4426FCD8";
//        // 国密规范正式公钥
//        //String pubk2 = "04eb38fea502e748faf09e60ab46720474d4ab002b0cbdaac2219496c4d4d279b876d723988b6e27b925e47637ab3ed1fb0ac4930831753d21d5369b6354fa6f7f";
//        String pubk2 = "046046C5A5C77BD2D7349CB2DC023AE96FE499A96D6D3E3B1C58A12CD65F186F3E923077B04FA3D4E1C894FC1F99BC62B8891F78E5F28DCE8602445CA1D88ACAAD";
////	        // 由d生成私钥 ---------------------
//        BCECPrivateKey privateKey = getPrivatekey(prik);
/*	        BigInteger d = new BigInteger("097b5230ef27c7df0fa768289d13ad4e8a96266f0fcb8de40d5942af4293a54a", 16);
        BCECPrivateKey bcecPrivateKey = getPrivatekeyFromD(d);
        System.out.println(bcecPrivateKey.getParameters());
        System.out.println(Hex.toHexString(bcecPrivateKey.getEncoded()));
        System.out.println(bcecPrivateKey.getAlgorithm());
        System.out.println(bcecPrivateKey.getFormat());
        System.out.println(bcecPrivateKey.getD());
        System.out.println(bcecPrivateKey instanceof java.security.interfaces.ECPrivateKey);
        System.out.println(bcecPrivateKey.getParameters());*/


////	        公钥X坐标PublicKeyXHex: 59cf9940ea0809a97b1cbffbb3e9d96d0fe842c1335418280bfc51dd4e08a5d4
////	        公钥Y坐标PublicKeyYHex: 9a7f77c578644050e09a9adc4245d1e6eba97554bc8ffd4fe15a78f37f891ff8
/*        PublicKey publicKey = getPublickeyFromX509File(new File("/Users/xxx/Downloads/xxxxx.cer"));
        System.out.println(publicKey);
        PublicKey publicKey1 = getPublickeyFromXY(new BigInteger("59cf9940ea0809a97b1cbffbb3e9d96d0fe842c1335418280bfc51dd4e08a5d4", 16), new BigInteger("9a7f77c578644050e09a9adc4245d1e6eba97554bc8ffd4fe15a78f37f891ff8", 16));
        System.out.println(publicKey1);
        System.out.println(publicKey.equals(publicKey1));
        System.out.println(publicKey.getEncoded().equals(publicKey1.getEncoded()));*/


//        PublicKey publicKey = getPublickey(pubk2);

//	        // sm2 encrypt and decrypt test ---------------------
/*	        KeyPair kp = generateKeyPair();
        PublicKey publicKey2 = kp.getPublic();
        PrivateKey privateKey2 = kp.getPrivate();*/
//        byte[] data3 = null;
//        byte[] data = sm2Encrypt("{\"HEAD\":{\"xRtnLvl\":\"\",\"xWkeCod\":\"CISPRQA1\",\"xItvTms\":\"\",\"xCmmTyp\":\"\",\"xMacCod\":\"\",\"xTlrNbr\":\"\",\"xCmmRsv\":\"\",\"xTypCod\":\"\",\"xDocSiz\":\"\",\"xUsrPwd\":\"\",\"xPreIsu\":\"\",\"xRqsNbr\":\"\",\"xIsuCnl\":\"\",\"xHdrLen\":\"203\",\"xRtnCod\":\"SUC0000\",\"xIsuDat\":\"0\",\"xIsuTim\":\"0\",\"xMsgFlg\":\"\",\"xOrgIsu\":\"\",\"xSysCod\":\"\",\"xEntUsr\":\"\",\"xEncCod\":\"\",\"xDskSys\":\"\",\"xKeyVal\":\"\",\"xAppRsv\":\"\",\"xDevNbr\":\"\",\"xDalCod\":\"N\"},\"BODY\":{\"CISPRQA1Z1\":[{\"zRsvFld\":\"\",\"zMerMem\":\"123456789ABCDE\",\"zFleIdx\":\"\",\"zMerCod\":\"Y00006\",\"zSubCod\":\"E4\",\"zOrdNbr\":\"Y00006240913007744\",\"zReqAmt\":\"100.00\",\"zRtnCod\":\"01\",\"zReqTyp\":\"00\"}],\"$REQSEQ$\":[{\"xReqSeq\":\"CIS10HD05QO00400013N\"}]}}".getBytes(), publicKey);
//        String data2 = new String(Base64.getEncoder().encode(data));
//        System.out.println("data is:" + new String(Base64.getEncoder().encode(data)));
//        System.out.println(Hex.toHexString(data));
//        data3 = sm2Decrypt(Base64.getDecoder().decode(data2), privateKey);
//        System.out.println(Hex.toHexString(data3));


//	        // sm4 encrypt and decrypt test ---------------------
//	        //0123456789abcdeffedcba9876543210 + 0123456789abcdeffedcba9876543210 -> 681edf34d206965e86b3e94f536e4246
//	        byte[] plain = Hex.decode("0123456789abcdeffedcba98765432100123456789abcdeffedcba98765432100123456789abcdeffedcba9876543210");
//	        byte[] key = Hex.decode("0123456789abcdeffedcba9876543210");
//	        byte[] cipher = Hex.decode("595298c7c6fd271f0402f804c33d3f66");
//	        byte[] bs = sm4Encrypt(key, plain);
//	        System.out.println(Hex.toHexString(bs));;
//	        bs = sm4Decrypt(key, bs);
//	        System.out.println(Hex.toHexString(bs));
    }
}