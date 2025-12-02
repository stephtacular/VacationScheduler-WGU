package com.hahn.vacationscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hahn.vacationscheduler.R;
import com.hahn.vacationscheduler.database.Repository;
import com.hahn.vacationscheduler.entities.Vacation;
import com.hahn.vacationscheduler.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String title;
    String date;
    int excursionID;
    int vacationID;
    EditText editTitle;
    TextView editDate;
    Repository repository;
    Excursion currentExcursion;


    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.excursionTitle);
        editTitle.setText(title);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", vacationID);
        date = getIntent().getStringExtra("startDate");
        editDate = findViewById(R.id.date);
        editDate.setText(date);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getALLVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationID());
        }

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }

        };

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //get value from other screen,but I'm going to hard code it right now
                String info = editDate.getText().toString();
                if (info.equals("")) info = "10/10/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int vacationID = getIntent().getIntExtra("vacationID", -1);
        String vacationStartDate = getIntent().getStringExtra("startDate");
        String vacationEndDate = getIntent().getStringExtra("endDate");
        String excursionStartDate = editDate.getText().toString();

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursioninsert) {
            // Insert Excursion
            Excursion excursion;
            if (excursionID == -1) {
                if (repository.getmAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;

                // Existing code to create the Excursion object and set its properties

                // Date validation
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                try {
                    Date excursionDate = dateFormat.parse(excursionStartDate);
                    Date startDate = dateFormat.parse(vacationStartDate);
                    Date endDate = dateFormat.parse(vacationEndDate);
                    System.out.println(vacationID);
                    System.out.println(vacationStartDate);
                    System.out.println(vacationEndDate);
                    System.out.println(editDate.getText().toString());

                    if (excursionDate.compareTo(startDate) >= 0 && excursionDate.compareTo(endDate) <= 0) {
                        // Excursion date is within the vacation start and end dates
                        excursion = new Excursion(
                                excursionID,
                                vacationID,
                                editTitle.getText().toString(),
                                editDate.getText().toString()
                        );
                        System.out.println("Success");
                        repository.insert(excursion);
                        Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was inserted", Toast.LENGTH_LONG).show();
                        this.finish();
                    } else {
                        // Excursion date is not within the vacation start and end dates
                        System.out.println("Not a success");
                        Toast.makeText(ExcursionDetails.this, "Excursion date is not during the associated vacation.", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle parsing exception
                    Toast.makeText(ExcursionDetails.this, "Error parsing dates.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " wasn't inserted. Please click on save.", Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId() == R.id.excursionsave) {
            boolean excursionFound = false;

            for (Excursion saveexcursion : repository.getmAllExcursions()) {
                if (saveexcursion.getExcursionID() == excursionID) {
                    excursionFound = true;

                    for (Vacation vacation1 : repository.getALLVacations()) {
                        if (vacation1.getVacationID() == saveexcursion.getVacationID()) {
                            Vacation vacation_excursion = vacation1;
                            System.out.println(vacation_excursion.getStartDate());
                            System.out.println(vacation_excursion.getEndDate());

                            // Date validation
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                            try {
                                Date excursionDate = dateFormat.parse(excursionStartDate);
                                Date startDate = dateFormat.parse(vacation_excursion.getStartDate());
                                Date endDate = dateFormat.parse(vacation_excursion.getEndDate());
                                System.out.println(saveexcursion.getVacationID());
                                System.out.println(vacation_excursion.getStartDate());
                                System.out.println(vacation_excursion.getEndDate());
                                System.out.println(editDate.getText().toString());

                                if (excursionDate.compareTo(startDate) >= 0 && excursionDate.compareTo(endDate) <= 0) {
                                    // Excursion date is within the vacation start and end dates
                                    System.out.println("Success");
                                    saveexcursion = new Excursion(
                                            excursionID,
                                            saveexcursion.getVacationID(),
                                            editTitle.getText().toString(),
                                            editDate.getText().toString()
                                    );
                                    repository.update(saveexcursion);
                                    Toast.makeText(ExcursionDetails.this, saveexcursion.getExcursionTitle() + " was saved", Toast.LENGTH_LONG).show();
                                    this.finish();
                                } else {
                                    // Excursion date is not within the vacation start and end dates
                                    System.out.println("Not a Success");
                                    Toast.makeText(ExcursionDetails.this, "Excursion date is not during the associated vacation.", Toast.LENGTH_LONG).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                // Handle parsing exception
                                Toast.makeText(ExcursionDetails.this, "Error parsing dates.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }

            if (!excursionFound) {
                Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " wasn't inserted. Please click on save.", Toast.LENGTH_LONG).show();
            }
        }





        if (item.getItemId() == R.id.excursiondelete) {
            ////// Delete Excursion code///////////
            for (Excursion excursion1 : repository.getmAllExcursions()) {
                if (excursion1.getExcursionID() == excursionID) {
                    currentExcursion = excursion1;
                    break;
                }
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
            this.finish();
        }


        if (item.getItemId() == R.id.notify) {
            String dateFromScreen = editDate.getText().toString();
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (myDate != null) {
                Calendar excursionCalendar = Calendar.getInstance();
                excursionCalendar.setTime(myDate);

                // Set the alert for the excursion date
                Long trigger = excursionCalendar.getTimeInMillis();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("alert_type", "excursion"); // Set the alert type as "excursion"
                intent.putExtra("excursionStarttitle", editTitle.getText().toString());
                intent.putExtra("excursionStartcontent", "Excursion starts today");
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                // Add a Toast message to indicate the excursion alert was set
                Toast.makeText(this, "Excursion alert scheduled", Toast.LENGTH_LONG).show();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}