package com.example.codechallenge8.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataLogin? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class DataLogin(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
