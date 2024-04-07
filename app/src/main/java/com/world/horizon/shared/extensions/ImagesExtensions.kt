package com.world.horizon.shared.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener

fun loadImage(context: Context,
              url: Any,
              imageView: ImageView,
              placeHolder: Int,
              listener: RequestListener<Drawable>? = null) {

    fun setMemoryCategory(context: Context) {
        Glide.get(context).setMemoryCategory(MemoryCategory.NORMAL)
    }

    setMemoryCategory(context)
    Glide.with(context)
        .load(url)
        .centerCrop()
        .dontAnimate()
        .addListener(listener)
        .placeholder(placeHolder)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
}