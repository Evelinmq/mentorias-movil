package mx.edu.utez.mentoriasmovil.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
        private const val BASE_URL = ""//ip con el puerto del servidor

        val apiService: apiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(apiService::class.java)
        }

}