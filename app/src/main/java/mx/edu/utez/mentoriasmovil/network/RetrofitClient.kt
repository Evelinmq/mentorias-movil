package mx.edu.utez.mentoriasmovil.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitClient {
    // 10.0.2.2 es la IP para acceder al localhost de tu PC desde el emulador de Android
    private const val BASE_URL = "http://10.0.2.2:8080/"

    var token: String? = null

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            // Si tenemos un token, lo agregamos al encabezado
            token?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }

            chain.proceed(requestBuilder.build())
        }
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
