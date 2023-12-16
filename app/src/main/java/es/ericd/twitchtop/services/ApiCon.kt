package es.ericd.twitchtop.services

import android.util.Log
import es.ericd.twitchtop.classes.StreamsResponse
import es.ericd.twitchtop.classes.TokenResponse
import es.ericd.twitchtop.interfaces.ApiInterface
import es.ericd.twitchtop.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCon {

    companion object {

        private var retrofitAPI: Retrofit? = null
        private var retrofitID: Retrofit? = null

        private fun getRetrofit(baseUrl: String): Retrofit? {

            when(baseUrl) {
                Constants.BASE_API_URL -> {
                    if (retrofitAPI == null) {
                        retrofitAPI = Retrofit.Builder()
                            .baseUrl(Constants.BASE_API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }

                    return retrofitAPI!!
                }

                Constants.BASE_ID_URL -> {
                    if (retrofitID == null) {
                        retrofitID = Retrofit.Builder()
                            .baseUrl(Constants.BASE_ID_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }

                    return retrofitID!!
                }

                else -> return null

            }

        }

        suspend fun getStreams(authToken: String, cursor: String? = null): StreamsResponse {

            val retrofit = getRetrofit(Constants.BASE_API_URL)

            if (retrofit == null) throw Exception("Something went wrong.")

            val call: Response<StreamsResponse> = retrofit
                .create(ApiInterface::class.java)
                .getData(Constants.CLIENT_ID, "Bearer $authToken", cursor)

            if (!call.isSuccessful) {
                throw Exception("Something went wrong")
            }

            return call.body()!!
        }

        suspend fun getToken(): TokenResponse {

            val retrofit = getRetrofit(Constants.BASE_ID_URL)

            if (retrofit == null) throw Exception("Something went wrong.")

            val call: Response<TokenResponse> = retrofit
                .create(ApiInterface::class.java)
                .getToken(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.GRANT_TYPE)

            if (!call.isSuccessful) {
                throw Exception("Something went wrong")
            }

            return call.body()!!
        }
    }

}