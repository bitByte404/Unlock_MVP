package com.wuliner.unlock_mvp

import android.widget.ImageView
import android.widget.TextView
import com.wuliner.unlock_mvp.databinding.ActivityMainBinding

interface MyUnlock {
    var dotArray: ArrayList<ImageView>
    var binding: ActivityMainBinding
    var modelsArray: ArrayList<UnlockModel>

    //改变颜色
    fun changeColor(model: UnlockModel, ifRight: Boolean)

    //改变标题的字
    fun changeWord(msg: String)

    //改变控件可见性
    fun changeVisiblity(imageView: ImageView, isVisible: Boolean)
}