package com.lab.web.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by ytf on 2017/10/23.
 */

public class DrawShowBottomView extends ViewGroup {
    private int childWidth = 0;
    private int mStartX;
    private Scroller mScroller;
    private int mTouchDownX;
    private boolean mFlag;
    public DrawShowBottomView(Context context) {
//        super(context);
        this(context, null);
    }

    public DrawShowBottomView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public DrawShowBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {

        mScroller = new Scroller(getContext());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heighpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMtMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //测量所有子元素
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //处理wrap_content的情况
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(childWidth * getChildCount(), childHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            setMeasuredDimension(childWidth * getChildCount(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        //int height = wm.getDefaultDisplay().getHeight();
        int childCount = getChildCount();

        View child;
        //遍历布局子元素
        for (int i = 0; i < childCount; i++) {
            if (i == 0) {
                child = getChildAt(i);
                //int width = child.getMeasuredWidth();
                childWidth = width / 4; //赋值给子元素宽度变量
                child.layout(-3 * childWidth, 0, -2 * childWidth, child.getMeasuredHeight());
            } else if (i == 1) {
                child = getChildAt(i);
                //int width = child.getMeasuredWidth();

                childWidth = width / 4; //赋值给子元素宽度变量
                child.layout(-2 * childWidth, 0, -childWidth, child.getMeasuredHeight());

            } else if (i == 2) {
                child = getChildAt(i);
                //int width = child.getMeasuredWidth();
                childWidth = width / 4; //赋值给子元素宽度变量
                child.layout(-childWidth, 0, 0, child.getMeasuredHeight());
            } else {
                child = getChildAt(i);
                //int width = child.getMeasuredWidth();
                childWidth = width / 4; //赋值给子元素宽度变量
                child.layout((i - 3) * childWidth, 0, (i - 2) * childWidth, child.getMeasuredHeight());
            }
        }
    }
//事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        boolean scrolling = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = (int) ev.getX();
                scrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mTouchDownX - ev.getX()) >= ViewConfiguration.get(
                        getContext()).getScaledTouchSlop()) {
                    scrolling = true;
                } else {
                    scrolling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                scrolling = false;
                break;

            default:

                break;
        }
        return scrolling;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mOnScrollTouchListener != null) {
                    mOnScrollTouchListener.scrollTouch();
                }
                int endX = (int) event.getX();
                int difX = mTouchDownX - endX;
                //计算是否超出边界
                int desc = getScrollX() + difX;
                if (desc < -3 * childWidth) {
                    scrollTo(-3 * childWidth, 0);
                } else if (desc > 2 * childWidth) {
                    scrollTo(2 * childWidth, 0);
                } else {
                    scrollBy(difX, 0);
                }
                mTouchDownX = endX;
                break;
            case MotionEvent.ACTION_UP:
                int start = getScrollX();
                int dx;

                //计算中心线
                int mid1 = -2 * childWidth - childWidth / 2;
                int mid2 = -childWidth - childWidth / 2;
                int mid3 = -childWidth / 2;
                int mid4 = childWidth / 2;
                int mid5 = childWidth + childWidth / 2;

                if (getScrollX() < mid1) {
                    dx = -3 * childWidth - start;
                    if (mArwShowFlag != null) {
                        mArwShowFlag.pos(-1);
                    }
                    //scrollTo(-2 * childWidth, 0);
                } else if (getScrollX() >= mid1 && getScrollX() < mid2) {
                    dx = -2 * childWidth - start;
                    if (mArwShowFlag != null) {
                        mArwShowFlag.pos(0);
                    }
                    //scrollTo(-childWidth, 0);
                } else if (getScrollX() >= mid2 && getScrollX() < mid3) {
                    dx = -childWidth - start;
                    if (mArwShowFlag != null) {
                        mArwShowFlag.pos(0);
                    }

                    // scrollTo(0, 0);
                } else if (getScrollX() >= mid3 && getScrollX() < mid4) {
                    dx = 0 - start;
                    if (mArwShowFlag != null) {
                        mArwShowFlag.pos(0);
                    }
                    //scrollTo(childWidth, 0);
                } else if (getScrollX() >= mid4 && getScrollX() < mid5) {
                    dx = childWidth - start;
                    if (mArwShowFlag != null) {
                        mArwShowFlag.pos(0);
                    }
                    // scrollTo(2 * childWidth, 0);
                } else {
                    dx = 2 * childWidth - start;
                    if (mArwShowFlag != null) {
                        mArwShowFlag.pos(1);
                    }
                }
                int durration = Math.abs(dx) * 20;
                mScroller.startScroll(start, 0, dx, durration);
                invalidate();
                break;

            default:

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            scrollTo(currX, 0);
            invalidate();
        }
        if (!mScroller.computeScrollOffset() && mFlag) {
            playReAnime();
        }
    }
    private void playReAnime() {
        mFlag = false;
        int start = getScrollX();
        int dx = -2 * childWidth - start;
        int durration = Math.abs(dx) * 160;
        mScroller.startScroll(start, 0, dx, durration);
        invalidate();
    }
    public interface OnScrollTouchListener {
        void scrollTouch();
    }

    public OnScrollTouchListener mOnScrollTouchListener;

    public void setOnScrollTouchListener(OnScrollTouchListener listener) {
        mOnScrollTouchListener = listener;
    }

    public void playAnime() {
        mFlag = true;
        int start = getScrollX();
        int dx = 2 * childWidth - start;
        int durration = Math.abs(dx) * 160;
        mScroller.startScroll(start, 0, dx, durration);
        invalidate();
    }

    public interface ArwShowFlag {
        void pos(int pos);
    }

    public ArwShowFlag mArwShowFlag;

    public void setOnArwShowFlag(ArwShowFlag arwShowFlag) {
        this.mArwShowFlag = arwShowFlag;
    }
}
