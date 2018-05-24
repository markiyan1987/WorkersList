package com.example.karpj.workerslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GenderAdapter extends ArrayAdapter<Gender> {
    private LayoutInflater inflater;
    private int layout;
    private List<Gender> genders;

    public GenderAdapter(Context context, int resource, List<Gender> genders) {
        super(context, resource, genders);
        this.genders = genders;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView sexView = (ImageView) view.findViewById(R.id.sex_);
        TextView genderView = (TextView) view.findViewById(R.id.gender);

        Gender gender = genders.get(position);
        sexView.setImageResource(gender.getSrc());
        genderView.setText(gender.getGender());


        return view;
    }
}
