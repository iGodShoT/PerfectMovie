package com.example.perfectmovie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemOfList (
    var Title: String,
    var Overview: String,
    var Vote_average: String,
    var Poster_path: String,
    var Release_date: String
) : Parcelable