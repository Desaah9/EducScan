package fr.isen.perigot.educscan;

import javax.net.ssl.SSLSocketFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ApiClient {

    private static final String BASE_URL = "https://192.168.200.23/api/";
    private static Retrofit retrofit = null;
    private static final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }
    };

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            // Créer un interceptor pour les logs
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Ignorer la vérification du certificat (utile pour les certificats auto-signés)
            SSLSocketFactory sslSocketFactory = getUnsafeOkHttpClient(); // Récupérer directement la SSLSocketFactory
            httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

            // Ignorer la vérification du nom d'hôte
            httpClient.hostnameVerifier((hostname, session) -> true);

            // Ajouter l'interceptor de logs à l'instance d'OkHttpClient
            httpClient.addInterceptor(loggingInterceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    // Méthode pour obtenir une instance SSLSocketFactory personnalisée pour ignorer la vérification du certificat
    private static SSLSocketFactory getUnsafeOkHttpClient() {
        try {
            // Créer un contexte SSL qui ignore la vérification du certificat
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Retourner une instance SSLSocketFactory personnalisée
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
