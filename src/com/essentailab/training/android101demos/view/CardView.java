package com.essentailab.training.android101demos.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.essentailab.training.android101demos.R;

public class CardView extends FrameLayout{

	private ImageView mImage;
	private TextView mText;
	private View back;
	private TextView bText;
	
	public OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			flipCard();
		}
	};
	
	public CardView(Context context){
		this(context, null);
	}

	public CardView(Context context, AttributeSet attrs) {
	    this(context, attrs, 0);
	}
	
	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray a = context.obtainStyledAttributes(attrs,
		        R.styleable.CardView, 0, 0);
	    String titleText = a.getString(R.styleable.CardView_text);
	    String backText = a.getString(R.styleable.CardView_backText);
	    int textColor = a.getColor(R.styleable.CardView_textColor, 255);
	    float textSize = a.getDimension(R.styleable.CardView_textSize, 12);
	    Drawable image = a.getDrawable(R.styleable.CardView_image);
	    int bgndColor = a.getColor(R.styleable.CardView_textBgndColor, 0);
	    int backBgndColor = a.getColor(R.styleable.CardView_backBgndColor, 0);
	    a.recycle();

	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.view_card, this, true);

	    mImage = (ImageView) getChildAt(0);
	    mText = (TextView) getChildAt(1);
	    back = getChildAt(2);
	    bText = (TextView) back.findViewById(R.id.back_txt);
	    
	    setText(titleText);
	    setBgndColor(bgndColor);
	    setTextColor(textColor);
	    setTextSize(textSize);
	    setImageDrawable(image);
	    setBackText(backText);
	    setBackBgndColor(backBgndColor);
	    
	    setBackgroundColor(Color.TRANSPARENT);
	}
	
	public void flipCard(){
		View cardFace=mImage;
		View cardBack=back;
	    FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

	    if (cardFace.getVisibility() == View.GONE)
	        flipAnimation.reverse();
	    
	    this.startAnimation(flipAnimation);
	}
	
	public void setText(String text){
		mText.setText(text);
	}
	
	public void setBackText(String text){
		bText.setText(text);
	}
	
	public void setTextSize(float size){
		mText.setTextSize(size);
	}
	
	public void setTextColor(int color){
		mText.setTextColor(color);
	}
	
	public void setBgndColor(int color) {
		mText.setBackgroundColor(color);
	}

	public void setBackBgndColor(int color) {
		back.setBackgroundColor(color);
	}
	
	public void setImageDrawable(Drawable drawable) {
		mImage.setImageDrawable(drawable);
	}
	
	private class FlipAnimation extends Animation{
	    private Camera camera;

	    private View fromView;
	    private View toView;

	    private float centerX;
	    private float centerY;

	    private boolean forward = true;
	    
	    public FlipAnimation(View fromView, View toView){
	        this.fromView = fromView;
	        this.toView = toView;

	        setDuration(700);
	        setFillAfter(false);
	        setInterpolator(new AccelerateDecelerateInterpolator());
	    }

	    public void reverse(){
	        forward = false;
	        View switchView = toView;
	        toView = fromView;
	        fromView = switchView;
	    }

	    @Override
	    public void initialize(int width, int height, int parentWidth, int parentHeight){
	        super.initialize(width, height, parentWidth, parentHeight);
	        centerX = width/2;
	        centerY = height/2;
	        camera = new Camera();
	    }

	    @Override
	    protected void applyTransformation(float interpolatedTime, Transformation t){
	        final double radians = Math.PI * interpolatedTime;
	        float degrees = (float) (180.0 * radians / Math.PI);
	        if (interpolatedTime >= 0.5f){
	            degrees -= 180.f;
	            fromView.setVisibility(View.GONE);
	            toView.setVisibility(View.VISIBLE);
	        }

	        if (forward)
	            degrees = -degrees;

	        final Matrix matrix = t.getMatrix();
	        camera.save();
	        camera.rotateY(degrees);
	        camera.getMatrix(matrix);
	        camera.restore();
	        matrix.preTranslate(-centerX, -centerY);
	        matrix.postTranslate(centerX, centerY);
	    }
	}
}
