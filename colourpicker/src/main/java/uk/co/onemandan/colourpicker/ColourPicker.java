package uk.co.onemandan.colourpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ColourPicker extends LinearLayout{

    public interface ColourSelectedListener {
        void OnLastColourSelected(int colour);
    }

    private static class ViewHolder {
        CardView    ColourView;
        ImageView   SelectorView;
        int         Colour;
        boolean     isSelected;
    }

    private class GridAdapter extends BaseAdapter {

        private Context         _context;
        private List<Integer>   _colours;

        GridAdapter(Context context, List<Integer> colours) {
            _context = context;
            _colours = colours;
        }

        @Override
        public int getCount() {
            return _colours.size();
        }

        @Override
        public Object getItem(int position) {
            return _colours.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(_context).inflate(
                        R.layout.view_grid_item,null,false);

                viewHolder              = new ViewHolder();
                viewHolder.SelectorView = convertView.findViewById(R.id.iv_selector);
                viewHolder.ColourView   = convertView.findViewById(R.id.cv_colour);
                viewHolder.isSelected   = false;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            int colour = _colours.get(position);
            viewHolder.ColourView.setCardBackgroundColor(colour);
            viewHolder.Colour = colour;

            return convertView;
        }
    }

    private List<Integer>       _colours    = new ArrayList<>();
    private List<ViewHolder>    _selected   = new ArrayList<>();

    private GridView _gridView;

    private int _maxSelected;

    private ColourSelectedListener _listener;

    public ColourPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){

        View root = inflate(context, R.layout.view_grid, this);
        _gridView = root.findViewById(R.id.gv_colours);

        _gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                handleOnColourSelected(viewHolder);
            }
        });

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

        setMaxSelected(ta.getInt(R.styleable.ColourPicker_cp_maxSelected, 1));

        ta.recycle();

        _gridView.setAdapter(new GridAdapter(context, _colours));
    }

    private void handleOnColourSelected(ViewHolder viewHolder){
        if(viewHolder.isSelected){
            animScaleDown(viewHolder.SelectorView);

            _selected.remove(viewHolder);
            viewHolder.isSelected = false;
        } else {
            animScaleUp(viewHolder.SelectorView);

            //Deselect previously selected views
            if(_selected.size() >= _maxSelected){
                animScaleDown(_selected.get(0).SelectorView);
                _selected.get(0).isSelected = false;
                _selected.remove(0);
            }

            _selected.add(viewHolder);
            viewHolder.isSelected = true;

            if(_listener != null){
                _listener.OnLastColourSelected(viewHolder.Colour);
            }
        }
    }

    private void animScaleUp(View view){
        Animation scaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);

        view.startAnimation(scaleUp);
        view.setVisibility(View.VISIBLE);
    }

    private void animScaleDown(final View view){
        Animation scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
        scaleDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(scaleDown);
    }

    @SuppressWarnings("unused")
    public int getMaxSelected(){ return _maxSelected; }

    @SuppressWarnings("unused")
    public int getSelectedColour(){
        if(_selected.size() > 0){
            return _selected.get(0).Colour;
        }

        return 0;
    }

    @SuppressWarnings("unused")
    public List<Integer> getSelectedColours(){
        List<Integer> colours = new ArrayList<>();

        if(_selected.size() > 0){
            for(int i = 0; i < _selected.size(); i++){
                colours.add(_selected.get(i).Colour);
            }
        }

        return colours;
    }

    @SuppressWarnings("unused")
    public List<Integer> getColours(){
        return _colours;
    }

    @SuppressWarnings("unused")
    public void setMaxSelected(int maxSelected) { _maxSelected = maxSelected; }

    @SuppressWarnings("unused")
    public void setColourSelectedListener(ColourSelectedListener listener){
        _listener = listener;
    }
}
