package com.wuliner.unlock_mvp

import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

class UnlockPresenter(val myUnlock: MyUnlock) {

    //记录上一次被点亮的点的视图
    private var lastSelectedDot: ImageView? = null
    //记录密码
    private val passwordBuilder = StringBuilder()
    //模拟密码
    private val password = "123";
    //记录所有点亮的控件
    private val selectedArray = arrayListOf<ImageView>()

    //处理Activity的事件
    fun clicked(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> actionDown(event)
            MotionEvent.ACTION_MOVE -> actionMove(event)
            MotionEvent.ACTION_UP   -> actionUp(event)
        }
    }

    //处理按下事件
    private fun actionDown(event: MotionEvent) {
        //判断触摸点是否在圆点内部
        isInView(event.x, event.y)?.let {
            //点亮圆点
            myUnlock.changeVisiblity(it, true)
            //记录下来
            lastSelectedDot = it
            //记录密码
            passwordBuilder.append(it.tag as String)
            //保存
            selectedArray.add(it)
        }
    }

    //处理松开事件
    private fun actionUp(event: MotionEvent) {
        myUnlock.binding.alertTitle.text = passwordBuilder.toString()
        //判断密码是否正确
        if (passwordBuilder.toString() == password) {
            //密码正确
            myUnlock.changeWord("密码解锁成功")
        } else {
            myUnlock.changeWord("密码解锁失败")
            //切换图片
            selectedArray.forEach { it ->
                //找到这个控件对应的model
                findModel(it)?.let {model ->
                    myUnlock.changeColor(model, true)
                }
            }
        }
        reset()
    }

    //从modelArray查找ImageView对应的model
    private fun findModel(target: ImageView): UnlockModel? {
        for (model in myUnlock.modelsArray) {
            if (model.imageView == target) {
                return  model
            }
        }
        return null
    }

    //对界面和数据重置
    private fun reset() {
        //延迟操作
        Handler().postDelayed({ selectedArray.forEach {
            myUnlock.changeVisiblity(it, false)
            //找到这个控件对应的model
            for (model in myUnlock.modelsArray) {
                if (model.imageView == it) {
                    myUnlock.changeColor(model, false)
                    break
                }
            }
        } }, 500)
        passwordBuilder.clear()
    }

    //处理移动事件
    private fun actionMove(event: MotionEvent) {
        //判断触摸点是否在圆点内部
        val dotView = isInView(event.x, event.y)
        //处理触摸的点内部移动触发move事件
        if (lastSelectedDot != dotView) {
            dotView?.let {
                //判断是不是第一个点
                if (lastSelectedDot == null) {
                    myUnlock.changeVisiblity(dotView, true)
                    addDotData(dotView)
                } else {
                    //有路线时显示路线
                    getLine(dotView)?.let {line ->
                        myUnlock.changeVisiblity(dotView, true)
                        myUnlock.changeVisiblity(line, true)
                        lastSelectedDot = dotView
                        addDotData(dotView)
                        selectedArray.add(line)
                    }
                }
            }
        }
    }

    //获取路线控件
    private fun getLine(currentDot: ImageView): ImageView? {
        //判断路线是否有
        //获取上一个点和当前的tag值 形成的线的tag
        val lastTag = (lastSelectedDot!!.tag as String).toInt()
        val currentTag = (currentDot.tag as String).toInt()
        //形成线的tag small*10 + big
        val lineTag = if (lastTag < currentTag) lastTag*10 + currentTag else currentTag * 10 + lastTag
        //获取lineTag对应的控件
        return myUnlock.binding.container.findViewWithTag("$lineTag")
    }

    //添加点击的点的信息
    fun addDotData(imageView: ImageView) {
        passwordBuilder.append(imageView.tag as String)
        //保存
        selectedArray.add(imageView)
    }

    //判断手指操作的地方是否在点上
    private fun isInView(x: Float, y: Float): ImageView? {
        myUnlock.dotArray.forEach {
            if ((x >= it.left && x <= it.right) && (y >= it.top && y <= it.bottom)) {
                return it
            }
        }
        return null
    }
}