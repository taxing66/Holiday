package com.joiway.devin.holiday.controller.tools.net;

import android.content.Context;

import com.joiway.devin.holiday.R;

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
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

/**
 * SSL generate tools
 * @author 陈德华
 * @create 2016-08-09
 * @editer 陈德华
 * @date 2016-08-09
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class SSLFactory {

    /**
     * 测试网需要的SSL
     */
    public static final int VALUE_SSL_FOR_TEST = 1;
    /**
     * 正式发布网需要的SSL
     */
    public static final int VALUE_SSL_FOR_ONLINE = 2;
    /**
     * 预发布网需要的SSL
     */
    public static final int VALUE_SSL_FOR_PRE_ONLINE = 3;
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
    private InputStream mKeyInput;
    private InputStream mTrustStoreInput;
    private String mPwd = "20jw15gz";
    private static SSLFactory mSSLFactory;
    private Context context;

    public static SSLFactory getInstance(Context context) {
        if (mSSLFactory == null) {
            synchronized (SSLFactory.class) {
                if (mSSLFactory == null) {
                    mSSLFactory = new SSLFactory(context);
                }
            }
        }
        return mSSLFactory;
    }

    private SSLFactory(Context context) {
        this.context = context.getApplicationContext();
    }


    public SSLSocketFactory getSSLSockeFactory(int mode) {
        switch (mode) {
            case VALUE_SSL_FOR_TEST:
                setSSLParameters(R.raw.client_62_p12, R.raw.truststore_62_p12, "j13579w", "client_62_p12", "truststore_62_p12");
                break;
            case VALUE_SSL_FOR_PRE_ONLINE:
                setSSLParameters(R.raw.client_9005_p12, R.raw.truststore_9005_p12, "j13579w", "client_9005_p12", "truststore_9005_p12");
                break;
            default:
                setSSLParameters(R.raw.client_p12_test, R.raw.truststore_p12_test, "20jw15gz", "client_p12_test", "truststore_p12_test");
                break;
        }
        return getSSLSocketFactory(mKeyInput, mTrustStoreInput, mPwd);
    }

    /**
     * 设置ssl 所需的参数
     *
     * @param clientId
     * @param trustStoreId
     * @param pwd
     * @param clientName
     * @param trustStoreName
     */
    private void setSSLParameters(int clientId, int trustStoreId, String pwd, String clientName, String trustStoreName) {
        mKeyInput = context.getResources().openRawResource(clientId);
        mTrustStoreInput = context.getResources().openRawResource(trustStoreId);
        mPwd = pwd;
        if (mKeyInput == null || mTrustStoreInput == null) {
            try {
                mKeyInput = context.getAssets().open(clientName);
                mTrustStoreInput = context.getAssets().open(trustStoreName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static SSLSocketFactory getSSLSocketFactory(InputStream keyInput, InputStream truststoreInput, String pwd) {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManager[] keyManagers = createKeyManagers(keyInput, pwd, null);
            TrustManager[] trustManagers = createTrustManagers(truststoreInput, pwd);
            sslContext.init(keyManagers, trustManagers, null);
            return sslContext.getSocketFactory();
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
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
        return keyManagerFactory.getKeyManagers();
    }

    private static TrustManager[] createTrustManagers(InputStream tsInput, String trustStorePassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
        trustStore.load(tsInput, trustStorePassword.toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        return trustManagerFactory.getTrustManagers();
    }
}
