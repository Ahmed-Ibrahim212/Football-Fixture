package com.example.footballpackage.data.remote.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Competition(
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("name") val name: String?
): Parcelable

data class CompetitionResponse(@SerializedName("count") val count:Int?,
                               @SerializedName("competitions") val competitions: List<Competition>?)
