package com.github.mysite.web.test;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * description: 数据库密码加解密
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-04-18 12:32
 */
public class TestConfigTool {

    @Test
    public void testPasswordEncryptAndDecrypt() {
        try {
            String encrypt = ConfigTools.encrypt("root");
            //bNVOqb7WKLX5Bjnw+LMv92taj25KOxDimXxILPQjw42wgv+1lHzOH8kr97xDwWdhpY67QuYCS7sWN4W46YbkFA==
            String decrypt = ConfigTools.decrypt(encrypt);
            System.out.println(decrypt);

            String encodeKey = "GJ3vU9W5Vodr+55Lz4i+2+ataqIKAzpwJYBJWkGyZRQ+g1qVCZCNf9y9eMteS2Yz52p7BRG0XkA7QtmE9JGKMw==";
            String defaultPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALxGD+7b5TFusE2q0/qy5YRcVjTWH9GVkM/1n3VM8rynbLSbhYRLgZW9imPgj2dXlQ+chpAc5qUB9QTSPaDZaRECAwEAAQ==";
            String decodeVal = ConfigTools.decrypt(defaultPublicKey, encodeKey);
            System.out.println(decodeVal);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
