package com.amazonaws.common.databindingVer

import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("loadImageFromUrl")
fun ShapeableImageView.loadImageFromUrl(url: String?) {
    this.load(url) {
        crossfade(true)
    }
}