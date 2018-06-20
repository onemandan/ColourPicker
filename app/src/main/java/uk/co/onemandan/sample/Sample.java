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
import uk.co.onemandan.colourpicker.MaterialColourView;

public class Sample extends AppCompatActivity {

    ColourPicker _colourPicker;
    MaterialColourView _selectedColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        _colourPicker   = findViewById(R.id.cp_colours);
        _selectedColour = findViewById(R.id.mcv_selected);

        _colourPicker.setColourSelectedListener(new ColourClickedListener() {
            @Override
            public void OnColourClicked(int colour) {
                _selectedColour.setColour(colour, true);
            }
        });
    }
}
