package com.wuliner.unlock_mvp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wuliner.unlock_mvp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MyUnlock {
    override lateinit var binding: ActivityMainBinding
    //保存model
    override var modelsArray = arrayListOf<UnlockModel>()
    //保存dot
    override var dotArray = ArrayList<ImageView>()
    //保存竖线
    var verticalLineArray = ArrayList<ImageView>()
    //保存横线
    var landscapeLineArray = ArrayList<ImageView>()
    //保存左斜线
    var leftSlashLineArray = ArrayList<ImageView>()
    //保存右斜线
    var rightSlashLineArray = ArrayList<ImageView>()
    //保存presenter对象
    val presenter = UnlockPresenter(this)
    //提示框
    lateinit var alertTitle: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //初始化数据
        initData()
        //添加触摸事件
        binding.container.setOnTouchListener { v, event ->
            presenter.clicked(event)
            true
        }
    }



    //进行初始化操作
    private fun initData() {

        //将9个点的视图保存到数组中
        dotArray = arrayListOf(
            binding.dot1,
            binding.dot2,
            binding.dot3,
            binding.dot4,
            binding.dot5,
            binding.dot6,
            binding.dot7,
            binding.dot8,
            binding.dot9
        )
        dotArray.forEach {
            modelsArray.add(UnlockModel(it, R.drawable.dot_normal, R.drawable.dot_selected))
        }
        //竖线
        verticalLineArray = arrayListOf(
            binding.line14,
            binding.line25,
            binding.line36,
            binding.line47,
            binding.line58,
            binding.line69
        )
        verticalLineArray.forEach {
            modelsArray.add(UnlockModel(it, R.drawable.line_1_normal, R.drawable.line_1_error))
        }
        //横线
        landscapeLineArray = arrayListOf(
            binding.line12,
            binding.line23,
            binding.line45,
            binding.line56,
            binding.line78,
            binding.line89
        )
        landscapeLineArray.forEach {
            modelsArray.add(UnlockModel(it, R.drawable.line_2_normal, R.drawable.line_2_error))
        }
        //左斜
        leftSlashLineArray = arrayListOf(
            binding.line24,
            binding.line35,
            binding.line57,
            binding.line68
        )
        leftSlashLineArray.forEach {
            modelsArray.add(UnlockModel(it, R.drawable.line_4_normal, R.drawable.line_4_error))
        }
        //右斜
        rightSlashLineArray = arrayListOf(binding.line15, binding.line26, binding.line48, binding.line59)
        rightSlashLineArray.forEach {
            modelsArray.add(UnlockModel(it, R.drawable.line_3_normal, R.drawable.line_3_error))
        }

        alertTitle = binding.alertTitle
    }
    //改变控件的颜色
    override fun changeColor(model: UnlockModel, ifRight: Boolean) {
        model.apply {
            if (ifRight) {
                imageView.setImageResource(rightPicture)
            } else {
                imageView.setImageResource(wrongPicture)
            }
        }
    }
    //改变提示框的颜色
    override fun changeWord(msg: String) {
        alertTitle.text = msg
    }
    //改变控件的可见性
    override fun changeVisiblity(imageView: ImageView, isVisible: Boolean) {
        if (isVisible) {
            imageView.visibility = View.VISIBLE
        } else {
            imageView.visibility = View.INVISIBLE
        }
    }
}