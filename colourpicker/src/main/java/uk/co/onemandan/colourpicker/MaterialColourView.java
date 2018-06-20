package uk.co.onemandan.colourpicker;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MaterialColourView extends LinearLayout implements View.OnClickListener{

    private TextView _labelView;
    private CardView _colourView;

    private CharSequence _labelText;
    private int _colourViewColour;

    private OnClickListener _listener;

    public MaterialColourView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View root = inflate(context, R.layout.view_material_colour, this);
        View rootView = root.findViewById(R.id.cv_root);

        _labelView  = root.findViewById(R.id.tv_label);
        _colourView = root.findViewById(R.id.cv_colour);

        rootView.setOnClickListener(this);

        handleAttributes(context, attrs);
    }

    private void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialColourView,
                0, 0);

        setLabelText(ta.getString(R.styleable.MaterialColourView_mcv_labelText) == null ? "" :
                ta.getString(R.styleable.MaterialColourView_mcv_labelText));

        setColour(ta.getColor(R.styleable.MaterialColourView_mcv_initialColour,
                getResources().getColor(android.R.color.transparent)), false);

        ta.recycle();
    }

    public void setLabelText(CharSequence text){
        _labelText = text;

        _labelView.setText(text);
    }

    public void setColour(int colour, boolean animate){
        _colourViewColour = colour;

        if(animate){
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                    _colourView.getCardBackgroundColor().getDefaultColor(), colour);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    _colourView.setCardBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
            colorAnimation.start();
        } else {
            _colourView.setCardBackgroundColor(_colourViewColour);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        _listener = l;
    }

    @SuppressWarnings("unused")
    public CharSequence getLabelText(){
        return _labelText;
    }

    @SuppressWarnings("unused")
    public int getColour(){
        return _colourViewColour;
    }

    @Override
    public void onClick(View v) {
        if(_listener != null) _listener.onClick(v);
    }
}
