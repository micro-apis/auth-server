package apis.micro.auth.server.utils;

import org.springframework.core.io.FileSystemResource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JksUtil {

    private static KeyPair defaultKeyPair;
    private static Map<String, KeyPair> keyPairsMap = new HashMap();

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
}