package com.example.footballpackage.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Standings(@SerializedName("table") val table: List<Table>?)