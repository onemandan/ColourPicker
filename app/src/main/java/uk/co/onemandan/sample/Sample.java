package uk.co.onemandan.sample;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import java.util.List;

import uk.co.onemandan.colourpicker.ColourClickedListener;
import uk.co.onemandan.colourpicker.ColourPicker;

public class Sample extends AppCompatActivity {

    ColourPicker _colourPicker;
    CardView     _selectedColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        _colourPicker   = findViewById(R.id.cp_colours);
        _selectedColour = findViewById(R.id.cv_selected);

        _colourPicker.setColourSelectedListener(new ColourClickedListener() {
            @Override
            public void OnColourClicked(int colour) {
                animChangeColour(colour);
            }
        });
    }

    private void animChangeColour(int colour){
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(_selectedColour.getCardBackgroundColor().getDefaultColor(), colour);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                _selectedColour.setCardBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        anim.start();
    }
}
