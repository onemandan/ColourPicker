package uk.co.onemandan.colourpicker;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private static class ViewHolder {
        CardView    ColourView;
        ImageView   SelectorView;
    }

    private Context _context;

    private List<Integer> _colours;
    private List<Integer> _selected;

    private int _maxSelected;

    private ColourClickedListener _listener;

    GridViewAdapter(Context context, List<Integer> colours, int maxSelected, boolean isSelected,
                    ColourClickedListener listener) {
        _context        = context;
        _colours        = colours;
        _maxSelected    = maxSelected;
        _listener       = listener;

        _selected       = new ArrayList<>();
        if(isSelected)  _selected.add(0);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.view_grid_item,
                    parent, false);

            viewHolder              = new ViewHolder();
            viewHolder.SelectorView = convertView.findViewById(R.id.iv_selector);
            viewHolder.ColourView   = convertView.findViewById(R.id.cv_colour);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(_selected.contains(position)){
            viewHolder.SelectorView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.SelectorView.setVisibility(View.INVISIBLE);
        }

        viewHolder.ColourView.setCardBackgroundColor(_colours.get(position));
        viewHolder.ColourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_selected.contains(position)){
                    _selected.remove(_selected.indexOf(position));
                } else {
                    _selected.add(position);

                    _listener.OnColourClicked(viewHolder.ColourView.getCardBackgroundColor()
                            .getDefaultColor());

                    if(_selected.size() > _maxSelected){
                        _selected.remove(0);
                    }
                }

                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    int getSelectedColour(){
        if(_selected.size() > 0){
            return _colours.get(_selected.get(0));
        }

        return 0;
    }

    List<Integer> getSelectedColours(){
        List<Integer> colours = new ArrayList<>(_selected.size());

        if(_selected.size() > 0){
            for(int i = 0; i < _selected.size(); i++){
                colours.add(_colours.get(_selected.get(i)));
            }
        }

        return colours;
    }

    /*
    private void animScaleUp(View view){
        Animation scaleUp = AnimationUtils.loadAnimation(view.getContext(),  R.anim.scale_up);

        view.startAnimation(scaleUp);
        view.setVisibility(View.VISIBLE);
    }

    private void animScaleDown(final View view){
        Animation scaleDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_down);
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
    */
}
