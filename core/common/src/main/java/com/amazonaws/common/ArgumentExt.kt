package com.amazonaws.common

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayList(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializable(
        key
    ) as? T
}