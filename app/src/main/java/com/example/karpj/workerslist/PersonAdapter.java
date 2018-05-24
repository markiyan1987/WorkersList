package com.example.karpj.workerslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {
    private LayoutInflater inflater;
    private int layout;
    private List<Person> persons;

    public PersonAdapter(Context context, int resource, List<Person> person) {
        super(context, resource, person);
        this.persons = person;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView sexView = (ImageView) view.findViewById(R.id.sex);
        TextView firstNameView = (TextView) view.findViewById(R.id.firstName);
        TextView lastNameView = (TextView) view.findViewById(R.id.lastName);
        TextView dateView = (TextView) view.findViewById(R.id.date);

        Person person = persons.get(position);

        if (person.isSex()) {
            sexView.setImageResource(R.drawable.sex_male_512);
        } else {
            sexView.setImageResource(R.drawable.sex_female_512);
        }

        firstNameView.setText(person.getFirstName());
        lastNameView.setText(person.getLastName());
        String date  = new SimpleDateFormat("dd-MM-yyyy").format(person.getDate());
        dateView.setText(date);

        return view;
    }
}
