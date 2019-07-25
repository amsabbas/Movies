package com.thiqah.posts.base.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName(KEY_STATUS) val status: String?,
    @SerializedName(KEY_CODE) val code: Int?,
    @SerializedName(KEY_MESSAGE) val message: String?,
    @SerializedName(KEY_DATA) val data: String?
) {
    companion object {
        private const val KEY_CODE = "code"
        private const val KEY_STATUS = "status"
        private const val KEY_MESSAGE = "message"
        private const val KEY_DATA = "data"

    }
}