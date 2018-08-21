package com.example.johan.dice;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter{
    Activity activity;
    List<GameRound> rounds;
    LayoutInflater inflater;

    public CustomAdapter(Activity activity, List<GameRound> rounds) {
        this.activity = activity;
        this.rounds = rounds;
        this.inflater = activity.getLayoutInflater();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<GameRound> getrounds() {
        return rounds;
    }

    public void setrounds(List<GameRound> rounds) {
        this.rounds = rounds;
    }

    @Override
    public int getCount() {
        return rounds.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view==null) {
            view = inflater.inflate(R.layout.single_row,viewGroup,false);
            holder = new ViewHolder();
            holder.tvUserName = (TextView)view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (ImageView)view.findViewById(R.id.iv_check_box);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder)view.getTag();
        }

        GameRound model = rounds.get(i);
        holder.tvUserName.setText(model.getName());

        if (model.hasBeenPlayed()) {
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked_green); //If round has been played
            model.setSelected(false);
        }
        else if (model.isSelected()) {
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked); //If round is supposed to be checked

        }
        else {
            holder.ivCheckBox.setBackgroundResource(R.drawable.unchecked); //All other rounds unchecked
        }
        return view;
    }
    public void updateRecords(List<GameRound> rounds) {
        this.rounds = rounds;
        notifyDataSetChanged();
    }
    class ViewHolder {
        TextView tvUserName;
        ImageView ivCheckBox;
    }
}
