package com.wuliner.unlock_mvp

import android.widget.ImageView

data class UnlockModel(
    val imageView: ImageView,
    val wrongPicture: Int,
    val rightPicture: Int
)