package me.xiaoying.livegetauthorize.server.factory;

import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import org.jose4j.json.JsonUtil;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;

import java.security.PrivateKey;

/**
 * Factory Jwt
 */
public class JwtFactory {
    private String keyId = FileConfigConstant.SETTING_JWT_KET_ID;
    private String publicKey = FileConfigConstant.SETTING_JWT_PUBLIC_KEY;
    private String privateKey = FileConfigConstant.SETTING_JWT_PRIVATE_KEY;

    private JsonWebSignature jsonWebSignature = new JsonWebSignature();
    private JwtClaims jwtClaims = new JwtClaims();
    private String key = FileConfigConstant.SETTING_PASSWORD_PASSWORD;

    public JwtFactory parameter(String key, String value) {
        this.jwtClaims.setClaim(key, value);
        this.jsonWebSignature.setPayload(this.jwtClaims.toJson());
        return this;
    }

    public JwtFactory setSubject(String subject) {
        this.jwtClaims.setSubject(subject);
        return this;
    }

    public JwtFactory setKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * 设置 JWT 过期时间
     *
     * @param second 秒
     * @return JwtFactory
     */
    public JwtFactory setExpirationTime(long second) {
        NumericDate numericDate = NumericDate.now();
        numericDate.addSeconds(second);
        this.jwtClaims.setExpirationTime(numericDate);
        return this;
    }

    @Override
    public String toString() {
        try {
            this.jwtClaims.setGeneratedJwtId();
            this.jwtClaims.setIssuedAtToNow();
            this.jwtClaims.setAudience();

            // default setting
            // 签名算法 RS256
            this.jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            this.jsonWebSignature.setKeyIdHeaderValue(this.keyId);
            this.jsonWebSignature.setPayload(this.jwtClaims.toJson());

            PrivateKey privateKey = new RsaJsonWebKey(JsonUtil.parseJson(this.privateKey)).getPrivateKey();
            this.jsonWebSignature.setKey(privateKey);
            return this.jsonWebSignature.getCompactSerialization();
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }
    }
}