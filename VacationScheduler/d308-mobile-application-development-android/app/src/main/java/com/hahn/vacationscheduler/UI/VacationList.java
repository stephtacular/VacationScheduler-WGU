package com.hahn.vacationscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hahn.vacationscheduler.R;
import com.hahn.vacationscheduler.UI.VacationDetails;
import com.hahn.vacationscheduler.database.Repository;
import com.hahn.vacationscheduler.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    private VacationAdaptor vacationAdaptor;
    private SearchView searchView;
    private List<Vacation> allVacations; // Add a class-level list for all vacations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        allVacations = repository.getALLVacations(); // Load all vacations here

        vacationAdaptor = new VacationAdaptor(this);
        recyclerView.setAdapter(vacationAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdaptor.setVacations(allVacations);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter your list of vacations based on the user's input.
                List<Vacation> filteredVacations = filterVacations(newText);
                vacationAdaptor.setVacations(filteredVacations);
                return true;
            }
        });

        return true;
    }

    private List<Vacation> filterVacations(String query) {
        List<Vacation> filteredVacations = new ArrayList<>();
        for (Vacation vacation : allVacations) {
            if (vacation.getVacationTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredVacations.add(vacation);
            }
        }
        return filteredVacations;
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Vacation> allVacations=repository.getALLVacations();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        vacationAdaptor=new VacationAdaptor(this);
        recyclerView.setAdapter(vacationAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdaptor.setVacations(allVacations);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.addvacation){
            repository=new Repository(getApplication());
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);

            return true;

        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            //Intent intent = new Intent(VacationList.this, VacationDetails.class);
            //startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.generateReport) {
            // User selected "Generate Report," so generate and display the report.
            String report = generateReport(); // Implement the generateReport method

            if (report != null) {
                shareReport(report);
            } else {
                Toast.makeText(this, "Unable to generate the report", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void shareReport(String report) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        // Set the text and title for sharing
        sendIntent.putExtra(Intent.EXTRA_TEXT, report);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Vacation Report");

        // Set the type based on the user's choice
        sendIntent.setType("text/plain");

        // Create a chooser to let the user select the sharing method
        Intent shareIntent = Intent.createChooser(sendIntent, "Share Report via");

        // Check if there are apps available for sharing
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        } else {
            Toast.makeText(this, "No apps available for sharing", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateReport() {
        // Fetch data from your database and format it as a report
        StringBuilder report = new StringBuilder();

        // Example: Fetch vacation details
        List<Vacation> vacations = repository.getALLVacations();
        for (Vacation vacation : vacations) {
            report.append("Title: ").append(vacation.getVacationTitle()).append("\n");
            report.append("Housing: ").append(vacation.getHousingName()).append("\n");
            report.append("Start Date: ").append(vacation.getStartDate()).append("\n");
            report.append("End Date: ").append(vacation.getEndDate()).append("\n\n");
        }

        // Return the generated report as a string
        return report.toString();
    }


}