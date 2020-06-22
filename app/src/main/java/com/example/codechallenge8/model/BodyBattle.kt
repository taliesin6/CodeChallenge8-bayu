package com.example.codechallenge8.model

import com.google.gson.annotations.SerializedName

data class BodyBattle(

	@field:SerializedName("mode")
	val mode: String? = null,

	@field:SerializedName("result")
	val result: String? = null
)
