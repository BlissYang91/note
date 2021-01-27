```
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView


/**
* Author: yangtianfu
* Date: 2021/1/25 18:08
* Describe:drawableLeft与文本一起居中显示
*/
@SuppressLint("AppCompatCustomView")
class DrawableCenterTextView : TextView {
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle){}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    override fun onDraw(canvas: Canvas) {
        val drawables = compoundDrawables
        if (drawables != null) {
            val drawableLeft = drawables[0]
            if (drawableLeft != null) {
                val textWidth = paint.measureText(text.toString())
                val drawablePadding = compoundDrawablePadding
                var drawableWidth =  drawableLeft.intrinsicWidth
                val bodyWidth = textWidth + drawableWidth + drawablePadding
                canvas.translate((width - bodyWidth) / 2, 0F)
            }
        }
        super.onDraw(canvas)
    }
}

```
> 使用
```
<DrawableCenterTextView
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:gravity="center_vertical"
                android:text="联系"
                android:drawablePadding="8dp"
                android:drawableLeft="@drawable/icon"
                android:textColor="#ff000000"
                android:textSize="14sp"/>
```