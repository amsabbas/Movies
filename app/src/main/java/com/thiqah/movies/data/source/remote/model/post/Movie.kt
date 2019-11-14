package com.thiqah.movies.data.source.remote.model.post

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") var id: Int,
    @ColumnInfo(name = "title")
    @SerializedName("title") var title: String,
    @ColumnInfo(name = "body")
    @SerializedName("body") var body: String
)