package com.example.karpj.workerslist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PersonDialog extends DialogFragment {

    private Addable addable;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        addable = (Addable) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dilog,null);


        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("female", R.drawable.sex_female_512));
        genders.add(new Gender("male", R.drawable.sex_male_512));
        GenderAdapter genderAdapter = new GenderAdapter(getContext(), R.layout.gender_item,genders);
        genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(genderAdapter);

        final TextView textView = (TextView)view.findViewById(R.id.date_);
        final Date date = new Date();
        textView.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));

        textView.setOnClickListener(new View.OnClickListener() {
            Calendar dateAndTime=Calendar.getInstance();
            @Override
            public void onClick(View v) {

                new DatePickerDialog(getContext(),d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

            DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, monthOfYear);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    monthOfYear++;
                    textView.setText(dayOfMonth+"/"+monthOfYear +"/"+year);
                }
            };
        });




        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
         builder
                .setTitle("Worker")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText firstName = (EditText)view.findViewById(R.id.firstName);
                        EditText lastName = (EditText)view.findViewById(R.id.lastName);
                        boolean sex = (spinner.getSelectedItemPosition()!=0);
                        try {
                            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(textView.getText().toString());
                            addable.addPerson(new Person(0, "max","karpyak",true, new Date()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }



}
