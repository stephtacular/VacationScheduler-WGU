package com.hahn.vacationscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.hahn.vacationscheduler.entities.Excursion;
import com.hahn.vacationscheduler.entities.Vacation;
import com.hahn.vacationscheduler.R;
import com.hahn.vacationscheduler.database.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String title;
    String housing;
    String startDate;
    String endDate;
    int vacationID;
    EditText editTitle;
    EditText editHousing;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editTitle=findViewById(R.id.titletext);
        title=getIntent().getStringExtra("title");
        editTitle.setText(title);

        editHousing=findViewById(R.id.housingtext);
        housing= getIntent().getStringExtra("housing");
        editHousing.setText(housing);

        editStartDate=findViewById(R.id.startdatetext);
        startDate= getIntent().getStringExtra("startDate");
        editStartDate.setText(startDate);

        editEndDate=findViewById(R.id.enddatetext);
        endDate= getIntent().getStringExtra("endDate");
        editEndDate.setText(endDate);

        vacationID = getIntent().getIntExtra("id", -1);

        //System.out.println(startDate);
        //System.out.println(endDate);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID",vacationID);
                intent.putExtra("startDate", startDate); // Add start date as an extra
                intent.putExtra("endDate", endDate);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView= findViewById(R.id.excursionrecyclerview);
        repository= new Repository(getApplication());
        final ExcursionAdaptor excursionAdaptor = new ExcursionAdaptor(this);
        recyclerView.setAdapter(excursionAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getmAllExcursions()){
            if (e.getVacationID()== vacationID) filteredExcursions.add(e);
        }
        excursionAdaptor.setExcursions(filteredExcursions);

        // Add date picker functionality for start and end dates
        editStartDate.setFocusable(false);
        editEndDate.setFocusable(false);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editStartDate);
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editEndDate);
            }
        });
    }

    private void showDatePickerDialog(final EditText dateField) {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetails.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, selectedMonth, selectedDay);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                dateField.setText(dateFormat.format(selectedDate.getTime()));
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        //1b.a: Allow the user to add as many vacations as desired.
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}
        if (item.getItemId() == R.id.vacationsave) {
            String startDateText = editStartDate.getText().toString();
            String endDateText = editEndDate.getText().toString();

            // Date format validation
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            try {
                Date startDate = dateFormat.parse(startDateText);
                Date endDate = dateFormat.parse(endDateText);

                // Date comparison validation
                if (startDate.before(endDate)) {
                    System.out.println("start date is before end date");
                    Vacation vacation;

                    if (vacationID == -1) {
                        if (repository.getALLVacations().isEmpty()) {
                            vacationID = 1;
                        } else {
                            vacationID = repository.getALLVacations().get(repository.getALLVacations().size() - 1).getVacationID() + 1;
                        }

                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHousing.getText().toString(), startDateText, endDateText);
                        repository.insert(vacation);
                        this.finish();
                    } else {
                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHousing.getText().toString(), startDateText, endDateText);
                        repository.update(vacation);
                        this.finish();
                    }
                } else {
                    System.out.println("End date is before start date");

                    Toast.makeText(VacationDetails.this, "End date must be after the start date", Toast.LENGTH_LONG).show();
                }
            } catch (ParseException e) {
                Toast.makeText(VacationDetails.this, "Invalid date format (MM/dd/yyyy)", Toast.LENGTH_LONG).show();
            }

            return true;
        }
        if (item.getItemId() == R.id.vacationdelete) {

            // Find the current vacation object
            for (Vacation vacation : repository.getALLVacations()) {
                if (vacation.getVacationID() == vacationID) {
                    currentVacation = vacation;
                    break;
                }
            }

            // Get ONLY excursions tied to THIS vacation
            List<Excursion> associated = repository.getAssociatedExcursions(vacationID);

            if (associated == null || associated.isEmpty()) {
                // No excursions → safe to delete vacation
                repository.delete(currentVacation);
                Toast.makeText(this,
                        currentVacation.getVacationTitle() + " was deleted",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                // Excursions exist → block delete
                Toast.makeText(this,
                        "Can't delete a vacation with excursions",
                        Toast.LENGTH_LONG).show();
            }

            return true;
        }

        if(item.getItemId()== R.id.addexcursions){
            if (vacationID == -1)
                Toast.makeText(VacationDetails.this, "Please save vacation before adding excursions", Toast.LENGTH_LONG).show();

            else {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID",vacationID);
                intent.putExtra("startDate", startDate); // Add start date as an extra
                intent.putExtra("endDate", endDate);
                startActivity(intent);
                RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
                final ExcursionAdaptor excursionAdapter = new ExcursionAdaptor(this);
                recyclerView.setAdapter(excursionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Excursion> filteredExcursions = new ArrayList<>();
                for (Excursion e : repository.getmAllExcursions()) {
                    if (e.getVacationID() == vacationID) filteredExcursions.add(e);
                }
                excursionAdapter.setExcursions(filteredExcursions);
                return true;
            }
        }
        if (item.getItemId() == R.id.notifyStart) {
            String startdateFromScreen = editStartDate.getText().toString();
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date startDate = null;
            try {
                startDate = sdf.parse(startdateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (startDate != null) {
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(startDate);

                // Set the alert for the start date
                Long trigger = startCalendar.getTimeInMillis();
                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                intent.putExtra("alert_type", "start"); // Set the alert type as "start"
                intent.putExtra("vacationStarttitle", editTitle.getText().toString());
                intent.putExtra("vacationStartcontent", "Vacation starts today");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                // Add a Toast message to indicate the start alert was set
                Toast.makeText(this, "Start alert scheduled", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (item.getItemId() == R.id.notifyEnd) {
            String enddateFromScreen = editEndDate.getText().toString();
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date endDate = null;
            try {
                endDate = sdf.parse(enddateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (endDate != null) {
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endDate);

                // Set the alert for the end date
                Long trigger = endCalendar.getTimeInMillis();
                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                intent.putExtra("alert_type", "end"); // Set the alert type as "end"
                intent.putExtra("vacationEndtitle", editTitle.getText().toString());
                intent.putExtra("vacationEndcontent", "Vacation ends today");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                // Add a Toast message to indicate the end alert was set
                Toast.makeText(this, "End alert scheduled", Toast.LENGTH_LONG).show();
            }
            return true;
        }






        if (item.getItemId() == R.id.share) {

            // Build the vacation details
            StringBuilder vacationDetails = new StringBuilder();
            vacationDetails.append("Title: ")
                    .append(editTitle.getText().toString())
                    .append("\nHousing: ")
                    .append(editHousing.getText().toString())
                    .append("\nStart Date: ")
                    .append(editStartDate.getText().toString())
                    .append("\nEnd Date: ")
                    .append(editEndDate.getText().toString());

            // Add excursions associated with this vacation
            List<Excursion> excursions = repository.getAssociatedExcursions(vacationID);

            if (excursions != null && !excursions.isEmpty()) {
                vacationDetails.append("\n\nExcursions:\n");
                for (Excursion e : excursions) {
                    vacationDetails.append("• ")
                            .append(e.getExcursionTitle())
                            .append(" – ")
                            .append(e.getDate())
                            .append("\n");
                }
            } else {
                vacationDetails.append("\n\nExcursions:\nNone scheduled.");
            }

            // Share via any app (email, SMS, etc.)
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails.toString());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Vacation Details");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share vacation via");
            startActivity(shareIntent);

            return true;
        }


        // Date format validation
      /*  SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date startDate = dateFormat.parse(editStartDate.getText().toString());
            Date endDate = dateFormat.parse(editEndDate.getText().toString());

            // Date comparison validation
            if (startDate.after(endDate)) {
                System.out.println("End date must be after the start date");
                Toast.makeText(VacationDetails.this, "End date must be after the start date", Toast.LENGTH_LONG).show();
                return true; // Prevent saving if the end date is before the start date
            }

            // Continue processing if date validation is successful
        } catch (ParseException e) {
            Toast.makeText(VacationDetails.this, "Invalid date format", Toast.LENGTH_LONG).show();
            return true; // Prevent saving if the date format is invalid
        }

       */
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdaptor excursionAdapter = new ExcursionAdaptor(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);

    }
}