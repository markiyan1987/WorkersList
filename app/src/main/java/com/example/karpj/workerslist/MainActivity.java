package com.example.karpj.workerslist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
     Person person;
     List<Person> persons = null;

    private ListView listView;
    private PersonAdapter personAdapter;

    private int curItemPosition = -1;
    private View curItem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.personList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(curItemPosition != -1){
                    curItem.setBackgroundColor(Color.WHITE);
                }
                curItemPosition = position;
                curItem = view;
                curItem.setBackgroundColor(Color.GREEN);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        try {
            persons = adapter.getPersons();
            //persons = persons_;
            personAdapter = new PersonAdapter(this, R.layout.list_item, persons);
            listView.setAdapter(personAdapter);
            adapter.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void add(MenuItem item) {
        LayoutInflater li = LayoutInflater.from(context);
        final View dialog = li.inflate(R.layout.dilog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner);
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("female", R.drawable.sex_female_512));
        genders.add(new Gender("male", R.drawable.sex_male_512));
        GenderAdapter genderAdapter = new GenderAdapter(dialog.getContext(), R.layout.gender_item,genders);
        genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(genderAdapter);

        final TextView dateView = (TextView)dialog.findViewById(R.id.date_);
        final Date date = new Date();
        dateView.setText(new SimpleDateFormat("dd-MM-yyyy").format(date));
        dateView.setOnClickListener(new View.OnClickListener() {
            Calendar dateAndTime = Calendar.getInstance();
            @Override
            public void onClick(View v) {

                new DatePickerDialog(dialog.getContext(), d,
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
                    dateView.setText(dateAndTime.get(Calendar.DAY_OF_MONTH) + "-" + (dateAndTime.get(Calendar.MONTH)+1) + "-" + dateAndTime.get(Calendar.YEAR));
                }
            };
        });

        final EditText firstName = (EditText)dialog.findViewById(R.id.firstName_);
        final  EditText lastName = (EditText)dialog.findViewById(R.id.lastName_);




        alertDialogBuilder.setView(dialog);
        alertDialogBuilder
                .setTitle("Add new worker")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                try {
                                    Date date_ = new SimpleDateFormat("dd-MM-yyyy").parse(dateView.getText().toString());
                                    person = new Person(0,firstName.getText().toString(),lastName.getText().toString(),(spinner.getSelectedItemPosition() !=0),date_);
                                    DatabaseAdapter adapter = new DatabaseAdapter(context);
                                    adapter.open();
                                    adapter.insert(person);
                                    adapter.close();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                onResume();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    public void delete(MenuItem item) {
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        adapter.delete(persons.get(curItemPosition).getId());
        adapter.close();
        onResume();
    }

    public void edit(MenuItem item) {
        person = persons.get(curItemPosition);

        LayoutInflater li = LayoutInflater.from(context);
        final View dialog = li.inflate(R.layout.dilog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner);
        List<Gender> genders = new ArrayList<>();
        genders.add(new Gender("female", R.drawable.sex_female_512));
        genders.add(new Gender("male", R.drawable.sex_male_512));
        GenderAdapter genderAdapter = new GenderAdapter(dialog.getContext(), R.layout.gender_item,genders);
        genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(genderAdapter);
        spinner.setSelection(person.isSex()?1:0);

        final TextView dateView = (TextView)dialog.findViewById(R.id.date_);
        final Date date = new Date();
        dateView.setText(new SimpleDateFormat("dd-MM-yyyy").format(person.getDate()));
        dateView.setOnClickListener(new View.OnClickListener() {
            Calendar dateAndTime = Calendar.getInstance();
            @Override
            public void onClick(View v) {

                new DatePickerDialog(dialog.getContext(), d,
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
                    dateView.setText(dateAndTime.get(Calendar.DAY_OF_MONTH) + "-" + (dateAndTime.get(Calendar.MONTH)+1) + "-" + dateAndTime.get(Calendar.YEAR));
                }
            };
        });

        final EditText firstName = (EditText)dialog.findViewById(R.id.firstName_);
        firstName.setText(person.getFirstName());

        final  EditText lastName = (EditText)dialog.findViewById(R.id.lastName_);
        lastName.setText(person.getLastName());



        alertDialogBuilder.setView(dialog);
        alertDialogBuilder
                .setTitle("Edit worker")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                try {
                                    Date date_ = new SimpleDateFormat("dd-MM-yyyy").parse(dateView.getText().toString());
                                    Person person_ = new Person(person.getId(),firstName.getText().toString(),lastName.getText().toString(),(spinner.getSelectedItemPosition() !=0),date_);
                                    DatabaseAdapter adapter = new DatabaseAdapter(context);
                                    adapter.open();
                                    adapter.update(person_);
                                    adapter.close();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                onResume();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
