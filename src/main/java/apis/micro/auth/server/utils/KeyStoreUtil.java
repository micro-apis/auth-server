package apis.micro.auth.server.utils;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyStoreUtil {

    private static JSONObject jsonKeyObject;
    private static KeyPair defaultKeyPair;
    private static Map<String, KeyPair> keyPairsMap = new HashMap();

    private static final Logger logger = LoggerFactory.getLogger(KeyStoreUtil.class);

    public static KeyStoreKeyFactory JwtKeyStoreKeyFactory(String filePath, String password) {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new FileSystemResource(filePath), password.toCharArray());
        return keyStoreKeyFactory;
    }
    public static KeyPair jwtKeyPair(String filePath, String password, String alias){
        KeyPair keyPair = getKeyPair(filePath, password, alias);
        setDefaultKeyPair(keyPair);
        return keyPair;
    }
    public static KeyPair getKeyPair(String filePath, String password, String alias){
        KeyPair keyPair = JwtKeyStoreKeyFactory(filePath, password).getKeyPair(alias);
        return keyPair;
    }
    public static void setDefaultKeyPair(KeyPair kp){
        defaultKeyPair = kp;
        keyPairsMap.put("default-key-id", kp);
    }
    public static void addKeyPair(KeyPair kp, String kid){
        keyPairsMap.put(kid, kp);
    }
    public static KeyPair addKeyPair(String filePath, String password, String alias, String kid){
        KeyPair keyPair = getKeyPair(filePath, password, alias);
        keyPairsMap.put(kid, keyPair);
        return keyPair;
    }
    public static KeyPair getDefaultKeyPair(){
        return defaultKeyPair;
    }
    public static Map<String, KeyPair> getKeyPairsMap() {
        return keyPairsMap;
    }

    public static JSONObject getJwksJsonObject(){
        if(jsonKeyObject == null) {
            List<JWK> keys = new ArrayList<>();
            keyPairsMap.forEach((k,kp)->{
                RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
                logger.info("publicKey algo --> "+publicKey.getAlgorithm());
                logger.info("publicKey pub expo --> "+ Base64URL.encode(publicKey.getPublicExponent()));
                RSAKey key = new RSAKey.Builder(publicKey)
                        //.firstFactorCRTExponent(Base64URL.encode(publicKey.getPublicExponent()))
                        .keyID(k).keyUse(KeyUse.SIGNATURE).build();
                keys.add(key);
            });
            jsonKeyObject = new JWKSet(keys).toJSONObject();
        }
        return jsonKeyObject;
    }
}