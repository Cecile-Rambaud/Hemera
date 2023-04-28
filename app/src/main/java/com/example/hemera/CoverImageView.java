package com.example.hemera;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CoverImageView extends androidx.appcompat.widget.AppCompatImageView {

    public CoverImageView(Context context) {
        super(context);
    }

    public CoverImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            float ratio = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
            if (width / (float) height > ratio) {
                height = (int) (width / ratio);
            } else {
                width = (int) (height * ratio);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float scale = Math.max((float) getWidth() / (float) getDrawable().getIntrinsicWidth(), (float) getHeight() / (float) getDrawable().getIntrinsicHeight());
        canvas.scale(scale, scale);
        super.onDraw(canvas);
    }
}
