package es.ericd.twitchtop.interfaces

import es.ericd.twitchtop.classes.StreamsResponse
import es.ericd.twitchtop.classes.TokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("oauth2/token")
    suspend fun getToken(
        @Field("client_id") client: String,
        @Field("client_secret") secret: String,
        @Field("grant_type") grantType: String
    ) : Response<TokenResponse>

    @GET("helix/streams")
    suspend fun getData(
        @Header("Client-Id") clientId: String,
        @Header("Authorization") authorization: String,
        @Query("after") after: String? = null,
        @Query("first") first: Int = 10
    ): Response<StreamsResponse>
}