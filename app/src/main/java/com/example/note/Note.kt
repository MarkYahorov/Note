package com.example.note

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    var text: String?,
    var data: String?
): Parcelable
