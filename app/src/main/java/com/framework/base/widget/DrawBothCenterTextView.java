package com.framework.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.framework.test.R;

/**
 * drawable和文本居中显示
 * 如果左右都有drawable则不能用该类，需要重新继承TextView，覆写onDraw
 */
public class DrawBothCenterTextView extends AppCompatTextView {

    private Bitmap drawLeft;
    private Bitmap drawRight;
    private String text = "";

    public DrawBothCenterTextView(Context context) {
        super(context);
    }

    public DrawBothCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.DrawBothCenterTextView);
        if (typed != null) {
            int drawLeftId = typed.getResourceId(R.styleable.DrawBothCenterTextView_drawableLeft, 0);
            int drawRightId = typed.getResourceId(R.styleable.DrawBothCenterTextView_drawableRight, 0);
            text = typed.getString(R.styleable.DrawBothCenterTextView_text);

            drawLeft = getBitmap(context, drawLeftId);
            drawRight = getBitmap(context, drawRightId);

            typed.recycle();
        }
    }

    public DrawBothCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float textWidth = getPaint().measureText(text);
        int drawPadding = getCompoundDrawablePadding();
        int padding = getPaddingLeft() + getPaddingRight();
        int bodyWidth = 0;
        int height = getHeight();
        if (drawLeft != null) {
            bodyWidth += drawLeft.getWidth();
            // icon在左侧
            canvas.drawBitmap(drawLeft, 0, (height-drawLeft.getHeight())/2, getPaint());
        }
        if (textWidth > 0) {
            canvas.drawText(text, bodyWidth, getTextSize(), getPaint());
            bodyWidth += textWidth;
        }
        if (drawRight != null) {
            // icon在右侧
            canvas.drawBitmap(drawRight, bodyWidth, (height-getTextSize())/2, getPaint());
            bodyWidth += drawRight.getWidth();
        }
        bodyWidth += drawPadding * 2;
        canvas.translate((getWidth() - bodyWidth - padding)/2, 0);
        super.onDraw(canvas);
    }

    private Bitmap getBitmap(Context context,int vectorDrawableId) {
        Bitmap bitmap=null;
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(getResources(), vectorDrawableId);
        }
        return bitmap;
    }
}
