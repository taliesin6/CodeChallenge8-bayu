package com.example.codechallenge8.api

import com.example.codechallenge8.model.*
import com.example.codechallenge8.util.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST(REGISTER)
    fun register(@Body bodyRegister: BodyRegister) : Call<RegisterResponse>

    @POST(LOGIN)
    fun login(@Body bodyLogin: BodyLogin) : Call<LoginResponse>

    @Multipart
    @PUT(PUTUSER)
    fun upload(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody
    ) : Call<UsersResponse>

    @POST(BATTLE)
    fun saveBattle(
        @Header("Authorization") token: String,
        @Body bodyBattle: BodyBattle
    ) : Call<BattleResponse>

    @GET(BATTLE)
    fun getBattle(
        @Header("Authorization") token: String
    ) : Call<HistoryBattleResponse>

    @GET(AUTH)
    fun auth(
        @Header("Authorization") token: String
    ) : Call<AuthResponse>
}