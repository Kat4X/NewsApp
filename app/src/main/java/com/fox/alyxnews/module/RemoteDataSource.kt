package com.fox.alyxnews.module

import android.os.Build
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class RemoteDataSource {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Suppress("DEPRECATION")
    private val settingsSpec: List<ConnectionSpec> =
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            val spec1 = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .supportsTlsExtensions(true)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                    CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
                )
                .build()
            listOf(spec1, ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)
        } else {
            listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)
        }

    private fun provideX509TrustManager(): X509TrustManager? {
        try {
            val factory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(null as KeyStore?);
            val trustManagers = factory.trustManagers;
            return trustManagers[0] as X509TrustManager
        } catch (exception: NoSuchAlgorithmException) {
            Timber.e("$exception")
        }
        return null
    }


    private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectionSpecs(connectionSpecs = settingsSpec)
            try {
                this.sslSocketFactory(SSLSocketFactoryExtended(), provideX509TrustManager()!!)
            } catch (e: Exception) {
                Timber.e("Error while setting Protocols $e")
            }
//            addInterceptor(AuthorizationInterceptor())
            addInterceptor(
                HttpLoggingInterceptor().apply {
//                    level =
//                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//                        else HttpLoggingInterceptor.Level.NONE
                }
            )
            addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()
                )
            }
            connectTimeout(20, TimeUnit.MINUTES)
            readTimeout(20, TimeUnit.MINUTES)
            writeTimeout(20, TimeUnit.MINUTES)
            retryOnConnectionFailure(true)
            authenticator?.let { this.authenticator(it) }
        }.build()
    }


    fun <Api> buildClientApi(api: Class<Api>): Api {
        return Retrofit.Builder().apply {
//            baseUrl(Constance.BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
//            client(getRetrofitClient(tokenAuth))
        }.build().create(api)
    }

    private fun <Api> buildUnauthorizedClientApi(api: Class<Api>): Api {
        return Retrofit.Builder().apply {
//            baseUrl(Constance.BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(getRetrofitClient())
        }.build().create(api)
    }

}