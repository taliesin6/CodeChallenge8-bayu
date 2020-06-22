package com.example.codechallenge8.model

import com.google.gson.annotations.SerializedName

data class HistoryBattleResponse(

	@field:SerializedName("data")
	val data: List<DataHistory?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class DataHistory(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("mode")
	val mode: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
