package com.thiqah.posts.data.source.remote.model.post

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id") var id: Int,
    @ColumnInfo(name = "title")
    @SerializedName("title") var title: String,
    @ColumnInfo(name = "body")
    @SerializedName("body") var body: String
)