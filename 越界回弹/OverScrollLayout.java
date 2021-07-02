package com.aiways.virtualAssistant.presentation.ui.views.listview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OverScrollLayout extends LinearLayout {
    private static final int ANIM_TIME = 400;

    private RecyclerView childView;

    private Rect original = new Rect();

    private boolean isMoved = false;

    private float startXpos;

    private static final float DAMPING_COEFFICIENT = 0.3f;

    private boolean isSuccess = false;

    private ScrollListener mScrollListener;

    public OverScrollLayout(Context context) {
        this(context, null);
    }

    public OverScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        childView = (RecyclerView) getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        original.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
    }

    public void setScrollListener(ScrollListener listener) {
        mScrollListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float touchXpos = ev.getX();
        if (touchXpos >= original.right || touchXpos <= original.left) {
            if (isMoved) {
                recoverLayout();
            }
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startXpos = ev.getX();
            case MotionEvent.ACTION_MOVE:
                int scrollXpos = (int) (ev.getX() - startXpos);
                boolean dragLeft = scrollXpos > 0 && canDragLeft();
                boolean dragRight = scrollXpos < 0 && canDragRight();
                if (dragLeft || dragRight) {
                    cancelChild(ev);
                    int offset = (int) (scrollXpos * DAMPING_COEFFICIENT);
                    childView.layout(original.left+ offset, original.top , original.right+ offset, original.bottom );
                    if (mScrollListener != null) {
                        mScrollListener.onScroll();
                    }
                    isMoved = true;
                    isSuccess = false;
                    return true;
                } else {
                    scrollXpos = (int) ev.getX();
                    isMoved = false;
                    isSuccess = true;
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                if (isMoved) {
                    recoverLayout();
                }
                return !isSuccess || super.dispatchTouchEvent(ev);
            default:
                return true;
        }
    }


    private void cancelChild(MotionEvent ev) {
        ev.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(ev);
    }

    private void recoverLayout() {
        TranslateAnimation anim = new TranslateAnimation(childView.getLeft() - original.left, 0, 0, 0);
        anim.setDuration(ANIM_TIME);
        childView.startAnimation(anim);
        childView.layout(original.left, original.top, original.right, original.bottom);
        isMoved = false;
    }

    private boolean canDragLeft() {
        final int firstVisiblePosition = ((LinearLayoutManager) childView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisiblePosition != 0 && childView.getAdapter().getItemCount() != 0) {
            return false;
        }
        int mostTop = (childView.getChildCount() > 0) ? childView.getChildAt(0).getTop() : 0;
        return mostTop >= 0;
    }

    private boolean canDragRight() {
        final int lastItemPosition = childView.getAdapter().getItemCount() - 1;
        final int lastVisiblePosition = ((LinearLayoutManager) childView.getLayoutManager()).findLastVisibleItemPosition();
        if (lastVisiblePosition >= lastItemPosition) {
            final int childIndex = lastVisiblePosition - ((LinearLayoutManager) childView.getLayoutManager()).findFirstVisibleItemPosition();
            final int childCount = childView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = childView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= childView.getBottom() - childView.getTop();
            }
        }
        return false;
    }


    public interface ScrollListener {
        void onScroll();
    }

}
