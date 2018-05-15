package gauvain.seigneur.badgecutout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Badge View with punched Textview
 * Inspired from CutoutTextView Widget of Plaid app of Nick Butcher
 */
public class BadgeCutOutView extends View {
    //View attributes
    private int mBackgroundColor;
    private String mText;
    private float mTextSize;
    private float mCornerRadius;
    private float mBadgeStroke;
    private int mStrokeColor;
    private boolean mIsIncludeBadgePadding;
    private boolean mIsCenterBadgeText;
    //internal
    private TextPaint mTextPaint;
    private Paint mPaint;
    private Paint mStrokePaint;
    private Bitmap mCutout;
    private Rect mRect;
    private RectF mRectF;
    private RectF mStrokeRectF;
    private Canvas mCutoutCanvas;
    private Rect mTextBounds;
    private float mTextHeight;
    private float mTextWidth;
    private float mBadgePadding; //default badge padding is set in addition to android Padding attributes
    float mYTextPosition;
    float mXTextPosition;
    boolean isTextCenteredInBadge;

    public BadgeCutOutView(Context context) {
        super(context);
        init(context, null);
    }

    public BadgeCutOutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BadgeCutOutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BadgeCutOutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet set) {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextBounds = new Rect();
        mRect = new Rect();
        mRectF = new RectF(mRect);
        mStrokeRectF = new RectF(mRect);

        final TypedArray a = getContext().obtainStyledAttributes(set, R.styleable.BadgeCutOutView, 0, 0);
        if (a.hasValue(R.styleable.BadgeCutOutView_android_fontFamily)) {
            try {
                Typeface font = ResourcesCompat.getFont(getContext(),
                        a.getResourceId(R.styleable.BadgeCutOutView_android_fontFamily, 0));
                if (font != null) {
                    mTextPaint.setTypeface(font);
                }
            } catch (Resources.NotFoundException nfe) {
            }
        }
        if (a.hasValue(R.styleable.BadgeCutOutView_backgroundColor)) {
            mBackgroundColor = a.getColor(R.styleable.BadgeCutOutView_backgroundColor,
                    mBackgroundColor);
        } else {
            mBackgroundColor = ContextCompat.getColor(context, R.color.defaultBadgeBackgroundColor);
        }
        if (a.hasValue(R.styleable.BadgeCutOutView_cornerRadius)) {
            mCornerRadius = a.getDimension(R.styleable.BadgeCutOutView_cornerRadius,
                    0);
        }
        if (a.hasValue(R.styleable.BadgeCutOutView_android_text)) {
            mText = a.getString(R.styleable.BadgeCutOutView_android_text);
        }
        if (a.hasValue(R.styleable.BadgeCutOutView_textSize)) {
            mTextSize = a.getDimensionPixelSize(R.styleable.BadgeCutOutView_textSize,0);
        } else {
            mTextSize = context.getResources().getDimension(R.dimen.default_badge_text_size);
        }
        if (a.hasValue(R.styleable.BadgeCutOutView_centerBadgeText)) {
            mIsCenterBadgeText = a.getBoolean(R.styleable.BadgeCutOutView_centerBadgeText,true);
        } else {
            mIsCenterBadgeText=false;
        }
        if (a.hasValue(R.styleable.BadgeCutOutView_includeBadgePadding)) {
            mIsIncludeBadgePadding = a.getBoolean(R.styleable.BadgeCutOutView_includeBadgePadding,true);
            if (!mIsIncludeBadgePadding) {
                mBadgePadding =  0;
            } else {
                mBadgePadding =  context.getResources().getDimension(R.dimen.badge_padding);
            }
        } else {
            mBadgePadding =  context.getResources().getDimension(R.dimen.badge_padding);
        }

        if (a.hasValue(R.styleable.BadgeCutOutView_badgeStroke)) {
            mBadgeStroke = a.getDimension(R.styleable.BadgeCutOutView_badgeStroke,0);
        } else {
            mBadgeStroke = 0;
        }
        if (a.hasValue(R.styleable.BadgeCutOutView_strokeColor)) {
            mStrokeColor = a.getColor(R.styleable.BadgeCutOutView_strokeColor,
                    mBackgroundColor);
        }
        a.recycle();
        //init view first time
        initViews();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mCutout, 0, 0, null);
        Log.d("badgeTextWitdh",""+String.valueOf(mTextWidth)+" "+String.valueOf(mTextBounds.width())+
                " "+String.valueOf(getWidth()));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = (mTextBounds.width()+ getPaddingLeft() + getPaddingRight()) +((int)  getBadgePadding()*2) +((int) getBadgeStroke()*2);
        int desiredHeight = (mTextBounds.height() + getPaddingTop() + getPaddingBottom())+((int)  getBadgePadding()*2)+((int) getBadgeStroke()*2);

        Log.d("onMeasure",""+String.valueOf(desiredWidth)+" "+String.valueOf(desiredHeight)+
                " "+String.valueOf(getWidth()));
        setMeasuredDimension(measureDimension(desiredWidth, widthMeasureSpec), measureDimension(desiredHeight, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawCanvas();
    }

    @Override
    public boolean hasOverlappingRendering() {
        return true;
    }

    /**
     * Init View according to attributes
     * Must be called twice :
     * in init() void
     * in drawCanvas() void
     */
    private void initViews() {
        defineTextView();
        defineStrokeRectView();
        defineRectView();
        if (mIsCenterBadgeText) {
            mYTextPosition = centeredYTextPosition();
            mXTextPosition = centeredXTextPosition();
        } else {
            mYTextPosition = freeYTextPosition();
            mXTextPosition = freeXTextPosition();
        }
        defineTextBounds();
        Log.d("initViews",String.valueOf(mXTextPosition)+" "+String.valueOf(freeXTextPosition()));
    }

    /**
     * Define rect view
     */
    private void defineRectView () {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        mRectF.left = getBadgeStroke();
        mRectF.right = getWidth()-getBadgeStroke();
        mRectF.top = getBadgeStroke();
        mRectF.bottom = getHeight()-getBadgeStroke();
    }

    /**
     * Define stroke rect view according to first RectView and Stroke dimens
     */
    private void defineStrokeRectView () {
        if (getBadgeStroke()>0) {
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setColor(mStrokeColor);
            mStrokePaint.setStrokeWidth(getBadgeStroke());
            mStrokeRectF.left = getBadgeStroke()/2;
            mStrokeRectF.right = getWidth()-getBadgeStroke()/2;
            mStrokeRectF.top = getBadgeStroke()/2;
            mStrokeRectF.bottom = getHeight()-getBadgeStroke()/2;
        }
    }

    /**
     * Define TextSize, and make it transparent
     */
    private void defineTextView() {
        mTextPaint.setTextSize(mTextSize);
        // this is the magic – Clear mode punches out the bitmap
        mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    /**
     * get textView size
     */
    private void defineTextBounds() {
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        mTextHeight = mTextBounds.height();
        mTextWidth = mTextPaint.measureText(mText);


        Log.d("defineTextBounds",String.valueOf(mText.length()));
    }

    /**
     * Draw canvas
     */
    private void drawCanvas() {
        if (mCutout != null && !mCutout.isRecycled()) {
            mCutout.recycle();
            Log.i("BadgeView", "cutout recycled");
        }
        //we initView another time in order to avoid size non-measuring even if the screen is off!
        initViews();
        //draw view
        mCutout = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        mCutout.setHasAlpha(true);
        mCutoutCanvas = new Canvas(mCutout);
        //inner rectf
        mCutoutCanvas.drawRoundRect(mRectF, getCornerRadius(), getCornerRadius(), mPaint);
        //outer rectF (for stroke)
        mCutoutCanvas.drawRoundRect(mStrokeRectF,
                setStrokeCornerRadius(),
                setStrokeCornerRadius(),
                mStrokePaint);
        //text inside the inner rectf
        mCutoutCanvas.drawText(
                mText,
                mXTextPosition,
                mYTextPosition,
                mTextPaint);
    }

    /**
     * Allows to make badge width and height wrap_content if user doesn't define a specific
     * height and width
     * to be implement in setMeasuredDimension in onMeasure
     */
    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        if (result < desiredSize){
            Log.e("BadgeView", "The view is too small, the content might get cut");
        }
        return result;
    }

    /**
     * Manage vertical position of text according to padding inside the view
     * @return vertical position
     */
    public float freeYTextPosition() {
        float paddingBottom=getPaddingBottom()+(getBadgePadding())+(getBadgeStroke());
        float paddingTop=getPaddingTop()+(getBadgePadding())+getBadgeStroke();
        float heightOfView=(mTextBounds.height() + paddingBottom + paddingTop);
        Log.d("freeYTextPosition", " "+String.valueOf(heightOfView));
        return heightOfView-paddingBottom;
    }

    /**
     * Manage horizontal position of text according to padding inside the view
     * @return horizontal position
     */
    public float freeXTextPosition() {
        /*float paddingLeft=getPaddingLeft()+(getBadgePadding())+(getBadgeStroke());
        float paddingRight=getPaddingRight()+(getBadgePadding())+(getBadgeStroke());
        float widthOfView=mTextBounds.width() + paddingLeft + paddingRight;
        return widthOfView-widthOfView-paddingRight;*/

        float paddingLeft=getPaddingLeft()+(getBadgePadding())+(getBadgeStroke());
        float paddingRight=getPaddingRight()+(getBadgePadding());
        float widthOfView=mTextBounds.width() + paddingLeft + paddingRight;
        Log.d("badgeTextXPos", " "+String.valueOf(widthOfView)+ " "+
                String.valueOf(mTextBounds.width()-paddingRight));
        return widthOfView - mTextBounds.width() -paddingRight-mTextBounds.left;



    }

    /**
     * Vertical centered text  -  the text is placed without include padding
     * @return the position of text centered
     */
    public float centeredYTextPosition() {
        return (getHeight() + mTextHeight) / 2;
    }

    /**
     * Horizontal centered text  -  the text is placed without include padding
     * @return the position of text centered
     */
    public float centeredXTextPosition() {
        return getWidth()/2 - (mTextBounds.width() / 2)-mTextBounds.left;
    }

    /**
     * get badgePadding dimens
     * @return badgePadding
     */
    private float getBadgePadding(){
        return mBadgePadding;
    }

    /**
     * get the stroke dimension
     * @return the dimens of stroke
     */
    private float getBadgeStroke(){
        return mBadgeStroke;
    }

    /**
     * get the cornerRadius dimension
     * @return the dimens of radius
     */
    private float getCornerRadius(){
        return mCornerRadius;
    }

    /**
     * Define stroke corner radius according to view corner radius and the badgeStroke width
     * @return value of corner radius for stroke rect
     */
    private float setStrokeCornerRadius() {
        float strokeCornerRadiusScale = 1+(getBadgeStroke()/getCornerRadius())/2;
        return getCornerRadius()*strokeCornerRadiusScale;

    }
}