package jw.cn.com.jwutils.controller.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

/**
 * ssl 生成工具
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class SSLUtils {

    private static final String KEY_STORE_TYPE_P12 = "PKCS12";

    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory (InputStream keyInput, InputStream truststoreInput, String pwd){

        try{
            SSLContext context = SSLContext.getInstance("TLS");
            KeyManager[] keyManagers = createKeyManagers(keyInput, pwd, null);
            TrustManager[] trustManagers = createTrustManagers(truststoreInput, pwd);
            context.init(keyManagers, trustManagers, null);

            javax.net.ssl.SSLSocketFactory socketFactory = context.getSocketFactory();
            return socketFactory;

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static KeyManager[] createKeyManagers(InputStream keyInput, String keyStorePassword, String alias)
            throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
        keyStore.load(keyInput, keyStorePassword.toCharArray());

        KeyManager[] managers;
        if (alias != null) {
            managers =
                    new KeyManager[] {
                            new AliasKeyManager(keyStore, alias, keyStorePassword)};
        } else {
            KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword == null ? null : keyStorePassword.toCharArray());
            managers = keyManagerFactory.getKeyManagers();
        }
        return managers;
    }

    private static TrustManager[] createTrustManagers(InputStream tsInput, String trustStorePassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
        trustStore.load(tsInput, trustStorePassword.toCharArray());
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        return trustManagerFactory.getTrustManagers();
    }

    private static class AliasKeyManager implements X509KeyManager {

        private KeyStore _ks;
        private String _alias;
        private String _password;

        public AliasKeyManager(KeyStore ks, String alias, String password) {
            _ks = ks;
            _alias = alias;
            _password = password;
        }

        public String chooseClientAlias(String[] str, Principal[] principal, Socket socket) {
            return _alias;
        }

        public String chooseServerAlias(String str, Principal[] principal, Socket socket) {
            return _alias;
        }

        public X509Certificate[] getCertificateChain(String alias) {
            try {
                java.security.cert.Certificate[] certificates = this._ks.getCertificateChain(alias);
                if(certificates == null){throw new FileNotFoundException("no certificate found for alias:" + alias);}
                X509Certificate[] x509Certificates = new X509Certificate[certificates.length];
                System.arraycopy(certificates, 0, x509Certificates, 0, certificates.length);
                return x509Certificates;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public String[] getClientAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }

        public PrivateKey getPrivateKey(String alias) {
            try {
                return (PrivateKey) _ks.getKey(alias, _password == null ? null : _password.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public String[] getServerAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }
    }

}
