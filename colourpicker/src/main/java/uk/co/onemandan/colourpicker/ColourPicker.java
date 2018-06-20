package uk.co.onemandan.colourpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ColourPicker extends LinearLayout{

    private List<Integer> _colours = new ArrayList<>();

    private GridView _gridView;

    private ColourClickedListener _listener;

    public ColourPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View root = inflate(context, R.layout.view_grid, this);
        _gridView = root.findViewById(R.id.gv_colours);

        handleAttributes(context, attrs);
    }

    private void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColourPicker,
                0, 0);

        int id = ta.getResourceId(R.styleable.ColourPicker_cp_colours, 0);
        if(id != 0){
            final int[] values = getResources().getIntArray(id);
            for (int i : values) { _colours.add(i); }
        }

        boolean isSelected      = ta.getBoolean(R.styleable.ColourPicker_cp_isSelected, true);
        boolean canDeselectLast = ta.getBoolean(R.styleable.ColourPicker_cp_canDeselectLast, false);
        int maxSelected         = ta.getInteger(R.styleable.ColourPicker_cp_maxSelected, 1);
        int colour              = ta.getColor(R.styleable.ColourPicker_cp_selectorColour,
                getResources().getColor(R.color.TintWhite80));

        _gridView.setNumColumns(ta.getInt(R.styleable.ColourPicker_cp_coloursPerRow, 5));

        ta.recycle();

        _gridView.setAdapter(new GridViewAdapter(context, _colours, maxSelected, colour, isSelected, canDeselectLast,
                new ColourClickedListener() {
                    @Override
                    public void OnColourClicked(int colour) {
                        if(_listener != null) _listener.OnColourClicked(colour);
                    }
                }));
    }

    @SuppressWarnings("unused")
    public List<Integer> getColours(){
        return _colours;
    }

    @SuppressWarnings("unused")
    public List<Integer> getSelectedColours(){
        return ((GridViewAdapter) _gridView.getAdapter()).getSelectedColours();
    }

    @SuppressWarnings("unused")
    public int getSelectedColour(){
        return ((GridViewAdapter) _gridView.getAdapter()).getSelectedColour();
    }

    @SuppressWarnings("unused")
    public void setColourSelectedListener(ColourClickedListener listener){
        _listener = listener;
    }
}
