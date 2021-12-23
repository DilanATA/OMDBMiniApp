package com.datarea.omdbapp.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Rating(
    @SerialName("Source")
    val Source: String? = null,
    @SerialName("Value")
    val Value: String? = null
): Parcelable