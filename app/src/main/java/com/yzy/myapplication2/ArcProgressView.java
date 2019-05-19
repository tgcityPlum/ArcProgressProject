package com.yzy.myapplication2;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ArcProgressView extends View {
    //弧的宽度
    private int mStrokeWith = dpToPx(8);
    //弧的背景的颜色
    private int mBackgroundColor = 0xfff2f2f2;
    //弧的前景的颜色
    private int mFontColor = 0xffFF6c4c;
    //画弧的控件
    private Paint mArcPaint;
    //当前进度的文字画笔
    private Paint currentNumberTextPaint;
    //默认信息的文字画笔
    private Paint defaultTextPaint;
    //百分号的文字画笔
    private Paint percentSignTextPaint;
    //科目的画笔
    private Paint subjectTextPaint;
    //当前进度夹角大小
    private float mIncludedAngle = 0;
    //进度的依次递增的数值
    private int mAnimatorValue = 0;
    //进度的最大数值
    private int mMaxValue = 100;
    //进度的当前的数据
    private int mCurrentValue;
    //进度
    private String mPercentSign = "%";
    //中心点的XY坐标
    private float centerX, centerY;
    private float currentY;
    //圆弧背景的开始的夹角
    private float mStartAngle = 139;
    //圆弧背景的开始和结束间的夹角大小
    private float mSweepAngle = 262;
    //默认的文字
    private String defaultText = "点击科目调整组合方案   ";
    //传入的选科
    private String afferentSubject = "大学适合您";

    public ArcProgressView(Context context) {
        super(context);
    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initArcCenter();

        initPaint();

        onDrawArc(canvas);

        onDrawText(canvas);
    }

    /**
     * 绘制圆心
     */
    private void initArcCenter() {
        //设置圆的坐标
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        currentY = getHeight();
    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        initArcPaint();

        initTextPaint();
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        //当前进度的画笔
        currentNumberTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentNumberTextPaint.setTextSize(dpToPx(32));
        currentNumberTextPaint.setFakeBoldText(true);
        currentNumberTextPaint.setColor(getResources().getColor(R.color.color_333333));
        //默认信息的画笔
        defaultTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        defaultTextPaint.setTextSize(dpToPx(10));
        defaultTextPaint.setStrokeWidth(3);
        defaultTextPaint.setTextAlign(Paint.Align.LEFT);
        defaultTextPaint.setColor(getResources().getColor(R.color.color_999999));
        //%的画笔
        percentSignTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentSignTextPaint.setTextSize(dpToPx(10));
        percentSignTextPaint.setStrokeWidth(3);
        percentSignTextPaint.setTextAlign(Paint.Align.LEFT);
        percentSignTextPaint.setColor(getResources().getColor(R.color.color_333333));
        //科目的画笔
        subjectTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subjectTextPaint.setTextSize(dpToPx(15));
        subjectTextPaint.setStrokeWidth(3);
        subjectTextPaint.setTextAlign(Paint.Align.LEFT);
        subjectTextPaint.setColor(getResources().getColor(R.color.color_1F1F1F));
    }

    /**
     * 初始化弧画笔
     */
    private void initArcPaint() {
        //圆弧的paint
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔类型
        mArcPaint.setStyle(Paint.Style.STROKE);
        //设置画笔的画出的形状
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置颜色
        mArcPaint.setColor(mBackgroundColor);
        //设置宽度
        mArcPaint.setStrokeWidth(mStrokeWith);
    }

    /**
     * 绘制圆弧
     */
    private void onDrawArc(Canvas canvas) {
        RectF oval = new RectF(mStrokeWith + dpToPx(5), mStrokeWith + dpToPx(5), getWidth() - mStrokeWith - dpToPx(5), getHeight() - mStrokeWith);

        canvas.drawArc(oval, mStartAngle, mSweepAngle, false, mArcPaint);
        //绘制当前数值对应的圆弧
//        mArcPaint.setColor(Color.parseColor("#FF4A40"));
        mArcPaint.setColor(mFontColor);
        //根据当前数据绘制对应的圆弧
        canvas.drawArc(oval, mStartAngle, mIncludedAngle, false, mArcPaint);
    }

    private void onDrawText(Canvas canvas) {
        //画当前进度的文字  和 %
        Rect currentNumberRect = new Rect();
//        currentNumberTextPaint.setAlpha(transparent);
        String animatorValue = String.valueOf(mAnimatorValue);
        currentNumberTextPaint.getTextBounds(mCurrentValue + "", 0, (mCurrentValue + "").length(), currentNumberRect);
        Rect percentSignRect = new Rect();
        percentSignTextPaint.getTextBounds(mPercentSign, 0, mPercentSign.length(), percentSignRect);
        canvas.drawText(animatorValue, getWidth() / 2 - (currentNumberRect.width() / 2 + percentSignRect.width() / 2), currentY - dpToPx(110), currentNumberTextPaint);
        canvas.drawText(mPercentSign, (getWidth() / 2 + currentNumberRect.width() / 2) - percentSignRect.width() / 2 + dpToPx(8), currentY - dpToPx(110), percentSignTextPaint);
        //绘制科目的文字
        Rect subjectRect = new Rect();
        subjectTextPaint.getTextBounds(afferentSubject, 0, afferentSubject.length(), subjectRect);
        canvas.drawText(afferentSubject, (getWidth() / 2 - subjectRect.width() / 2), currentY - dpToPx(65), subjectTextPaint);
        //绘制 “点击科目调整组合方案” 文字
        Rect defaultTextRect = new Rect();
        defaultTextPaint.getTextBounds(defaultText, 0, defaultText.length(), defaultTextRect);
        canvas.drawText(defaultText, (getWidth() / 2 + dpToPx(4)) - defaultTextRect.width() / 2, currentY - dpToPx(36), defaultTextPaint);
    }

    private int dpToPx(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }

    /**
     * 设置数据
     *
     * @param currentValue 当前绘制的值
     */
    public void setValues(int currentValue, String afferentSubject) {
        this.afferentSubject = afferentSubject;
        mCurrentValue = currentValue;
        if (mCurrentValue > mMaxValue) {
            mCurrentValue = mMaxValue;
        }
        //计算弧度比重
        float scale = (float) mCurrentValue / mMaxValue;
        //计算弧度
        float currentAngle = scale * mSweepAngle;
        //开始执行动画
        setAnimation(0, currentAngle, 2000);
    }

    /**
     * 为绘制弧度及数据设置动画
     *
     * @param startAngle   开始的弧度
     * @param currentAngle 需要绘制的弧度
     * @param time         动画执行的时长
     */
    private void setAnimation(float startAngle, float currentAngle, int time) {
        //绘制当前数据对应的圆弧的动画效果
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(startAngle, currentAngle);
        progressAnimator.setDuration(time);
        progressAnimator.setTarget(mIncludedAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIncludedAngle = (float) animation.getAnimatedValue();
                //重新绘制，不然不会出现效果
                postInvalidate();
            }
        });
        //开始执行动画
        progressAnimator.start();

        //中心数据的动画效果
        mAnimatorValue = 0;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mAnimatorValue, mCurrentValue);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

}
