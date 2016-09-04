package linqh.myclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Modifier;

/**
 * Created by dz1 on 2015/12/31.
 */
public class Clock extends View {
    //View默认最小宽度
    private static final int DEFAULT_MIN_WIDTH = 200;
    //秒针长度
    private float secondPointerLength;
    //分针长度
    private float minutePointerLength;
    //时针长度
    private float hourPointerLength;
    //外圆边框宽度
    private static final float DEFAULT_BORDER_WIDTH = 6f;
    //指针反向超过圆点的长度
    private static final float DEFAULT_POINT_BACK_LENGTH = 40f;
    //长刻度线
    private static final float DEFAULT_LONG_DEGREE_LENGTH = 40f;
    //短刻度线
    private static final float DEFAULT_SHORT_DEGREE_LENGTH = 30f;

//    控件的宽高
    private int mViewWidth;
    private int mViewHeight;

    private Thread timeThread = new Thread() {
        @Override
        public void run() {
            try {
                while(true){
                    updateHandler.sendEmptyMessage(0);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };

    public Clock(Context context) {
        super(context, null);
        init();
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //启动线程让指针动起来
    private void init(){
        timeThread.start();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desired = DEFAULT_MIN_WIDTH;
            width = desired;
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(desired, widthSize);
            }
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = DEFAULT_MIN_WIDTH;
            height = desired;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desired,heightSize);
            }
        }
        super.onMeasure(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;
        System.out.println("mViewWidth:" + mViewWidth + ",mViewHeight:" + mViewHeight);
        super.onSizeChanged(w, h, oldw, oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置时钟半径，设置时、分、秒针的长度
        float r = (Math.min(getHeight() / 2, getWidth() / 2) - DEFAULT_BORDER_WIDTH / 2);
        secondPointerLength = r * 0.8f;
        minutePointerLength = r * 0.6f;
        hourPointerLength = r * 0.5f;
        //画外圆
        float borderWidth = DEFAULT_BORDER_WIDTH;   //画笔宽度
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(borderWidth);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r, paintCircle);
        super.onDraw(canvas);
    }
}
