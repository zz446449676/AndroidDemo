package com.android.testdemo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 支持左滑删除
 */
public class SlideRecyclerView extends RecyclerView {
    public static final String TAG = "zhang_SlideRecyclerView";

    private boolean isSideSlip = false; // 是否为横向滑动；

    private float mLastX;   // 滑动过程中记录上次触碰点X

    private VelocityTracker mVelocityTracker;   // 速度追踪器

    private static final int SNAP_VELOCITY = 300;   // 最小滑动速度

    private boolean isAllowSlide = true; // 当处于多选删除状态下，不允许左滑删除

    private final int mTouchSlop; // 认为是滑动的最小距离（一般由系统提供）

    private float mFirstX, mFirstY; // 首次触碰范围

    private ViewGroup mItemView;   // 当前触碰的子item

    private ViewGroup mPreItemView;   // 已经滑出删除按钮的itemView

    private final Scroller mScroller; // 负责当前itemView的滚动

    private final Scroller mPreScroller; // 负责上一个itemView的滚动

    private int mMessageDeleteWidth;

    public SlideRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SlideRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mPreScroller = new Scroller(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        obtainVelocity(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取当前点击的ItemView
                ViewGroup curView = (ViewGroup) findChildViewUnder(x, y);
                // 处理收回删除按钮的动画逻辑
                if (!mScroller.isFinished()) {
                    if (mItemView != curView) {
                        int dx = mItemView.getScrollX() >= mMessageDeleteWidth / 2 ? mMessageDeleteWidth : 0;
                        mScroller.abortAnimation();
                        mItemView.scrollTo(dx, 0);
                    }
                }

                mFirstX = x;
                mFirstY = y;
                mLastX = x;
                mItemView = curView;
                break;

            case MotionEvent.ACTION_MOVE:
                // 获取用户的滑动速度
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();
                // 如果滑动大于最小滑动距离或者滑动速度大于最小滑动速度进入此逻辑
                if (Math.abs(x - mFirstX) >= mTouchSlop || (Math.abs(y - mFirstY) >= mTouchSlop) ||
                        Math.abs(yVelocity) > SNAP_VELOCITY || Math.abs(xVelocity) > SNAP_VELOCITY) {
                    // 滑动距离大于最小距离并且有滑出删除按钮时，分情况把删除按钮给收回
                    // 1. 如果是竖直方向滑动，直接收回
                    // 2. 前一次滑动的ItemView和当前正在滑动的ItemView不是同一个，则直接收回
                    if (mPreItemView != null && mPreItemView.getScrollX() != 0 &&
                            (Math.abs(x - mFirstX) < Math.abs(y - mFirstY) || mPreItemView != mItemView)) {
                        int dx = mPreItemView.getScrollX() >= mMessageDeleteWidth ? -mMessageDeleteWidth : -mPreItemView.getScrollX();
                        // 执行收回滑动动画
                        mPreScroller.startScroll(mPreItemView.getScrollX(), 0, dx, 0, 200);
                        invalidate();
                    }
                }

                // 判断滑动方向是否为横向滑动
                if ((Math.abs(xVelocity) > SNAP_VELOCITY && Math.abs(xVelocity) > Math.abs(yVelocity))
                        || (Math.abs(x - mFirstX) >= mTouchSlop && Math.abs(x - mFirstX) > Math.abs(y - mFirstY))) {
                    // 标记为横向滑动，用于后面的禁止竖直滑动逻辑
                    isSideSlip = true;
                    // 计算删除按钮的宽度，设置最大可左滑的宽度
                    if (mItemView != null) {
                        mMessageDeleteWidth = mItemView.getChildAt(5).getWidth();
                    }
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                // 进入横向滑动，在横向滑动后直接return true 消耗此事件，禁止竖直滑动
                if (isSideSlip && isAllowSlide) {
                    if (mItemView != null) {
                        // 随手指滑动
                        float dx = mLastX - x;
                        int scrollX = mItemView.getScrollX();
                        if (scrollX + dx > 0) {
                            // 左滑，滑出删除按钮
                            if (scrollX + dx > mMessageDeleteWidth) {
                                dx = mMessageDeleteWidth - scrollX;
                            }
                        } else {
                            // 右滑，关闭删除按钮
                            dx = -scrollX;
                        }
                        mItemView.scrollBy((int)dx, 0);
                        mLastX = x;
                    }
                    return true;
                }
                // 存在偶现的情况，在拦截阶段没能及时收回删除按钮，需要在后续的竖直方向滑动时进行收回
                if (mPreItemView != null && mPreItemView.getScrollX() != 0 &&
                        Math.abs(x - mFirstX) < Math.abs(y - mFirstY) && mPreScroller.isFinished()) {
                    int dx = mPreItemView.getScrollX() >= mMessageDeleteWidth ? -mMessageDeleteWidth : -mPreItemView.getScrollX();
                    // 执行收回滑动动画
                    mPreScroller.startScroll(mPreItemView.getScrollX(), 0, dx, 0, 200);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                isSideSlip = false;
                // 使用局部变量，减少机器指令的赋值操作
                int messageDeleteWidth = mMessageDeleteWidth;
                if (mItemView != null) {
                    int scrollX = mItemView.getScrollX();
                    // 发生了横向滑动
                    if (scrollX > 0) {
                        int dx = 0;
                        // 滑动超过一半时，则完全滑出删除按钮，否则收回删除按钮
                        if (scrollX > messageDeleteWidth / 2 && scrollX <= messageDeleteWidth) {
                            // 处理对上一个ItemView的收回动画
                            // 处理 当已展开的删除按钮正在被收回过程中（滚动正在执行），此时快速执行ACTION_UP，导致mPreItemView重新赋值，导致上一个按钮没有被完全收回的场景
                            if (!mPreScroller.isFinished()) {
                                mPreScroller.abortAnimation();
                                mPreItemView.scrollTo(0,0);
                            }
                            mPreItemView = mItemView;
                            dx = messageDeleteWidth - scrollX;
                        } else {
                            dx = -scrollX;
                        }
                        if (dx != 0) {
                            mScroller.startScroll(scrollX, 0, dx, 0, 200);
                            invalidate();
                        }
                    }
                }
                releaseVelocity();
                break;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mItemView != null) {
                mItemView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                invalidate();
            }
        }
        if (mPreScroller.computeScrollOffset()) {
            if (mPreItemView != null) {
                mPreItemView.scrollTo(mPreScroller.getCurrX(), mPreScroller.getCurrY());
                invalidate();
            }
        }
    }

    // 获取速度计算器，此设计模式使用了享元模式
    private void obtainVelocity(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    // 释放速度计算器
    private void releaseVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 将滑出的删除按钮进行收回
     */
    public void closeMenu() {
        if (mItemView != null && mItemView.getScrollX() != 0) {
            mItemView.scrollTo(0, 0);
        }
        if (mPreItemView != null && mPreItemView.getScrollX() !=0) {
            mPreItemView.scrollTo(0, 0);
        }
    }

    /**
     * 设置是否可以横向滑动
     * 站内信在某些场景下不可以横向滑动展示出删除按钮，例如：处于多选状态时
     */
    public void setIsAllowSlide(boolean allow) {
        this.isAllowSlide = allow;
        if (mItemView != null) {
            mItemView.scrollTo(0, 0);
            mItemView = null;
        }
        if (mPreItemView != null) {
            mPreItemView.scrollTo(0, 0);
            mPreItemView = null;
        }
    }
}
