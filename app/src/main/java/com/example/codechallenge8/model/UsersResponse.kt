package com.example.codechallenge8.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("data")
	val data: DataUsers? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class DataUsers(

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
