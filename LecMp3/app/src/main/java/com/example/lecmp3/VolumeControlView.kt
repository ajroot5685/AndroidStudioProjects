package com.example.lecmp3

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.PI
import kotlin.math.atan2

class VolumeControlView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    var mx = 0.0f
    var my = 0.0f
    var tx = 0.0f
    var ty = 0.0f
    var angle = 180.0f

    var listener:VolumeListener?=null   //인터페이스 선언

    interface VolumeListener{       // 가져다 쓸 함수 인터페이스 이용
        fun onChanged(angle:Float):Unit
    }

    fun setVolumeListener(listener: VolumeListener){    // 인터페이스 초기화할 수 있는 함수
        this.listener=listener
    }

    fun getAngle(x1:Float, y1:Float):Float {
        mx = x1-(width /2.0f)
        my = (height/2.0f)-y1
        return (atan2(mx, my) *180.0f/ PI).toFloat()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!=null){
            tx = event.getX(0)  // 멀티 터치가 가능하기에 처음으로 터치한 것만 사용
            ty = event.getY(0)
            angle = getAngle(tx, ty)
            invalidate()    // 여러 단계를 거쳐 최종적으로 onDraw() 호출
            if(listener!=null){
                listener?.onChanged(angle)
            }
            return true
        }
        return return false
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.rotate(angle, width / 2.0f, height / 2.0f)
        super.onDraw(canvas)
    }
}