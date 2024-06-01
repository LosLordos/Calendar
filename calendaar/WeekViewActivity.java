package com.example.calendaar;

import static com.example.calendaar.CalendarUtils.daysInWeekArray;
import static com.example.calendaar.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText; // TextView for displaying the current month and year
    private RecyclerView calendarRecyclerView; // RecyclerView for displaying the calendar days in a week
    private ListView eventListView; // ListView for displaying the events of the selected date
    private LocalTime time; // LocalTime object for handling time

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view); // Sets the layout for the activity
        initWidgets(); // Initializes the UI components
        setWeekView(); // Sets up the initial view for the week


    }

    // Initializes the UI components by finding them by their ID in the layout
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    // Sets up the view for the week
    private void setWeekView() {
        // Initialize selectedDate if it's null
        if (CalendarUtils.selectedDate == null) {
            CalendarUtils.selectedDate = LocalDate.now();
        }

        // Sets the text for the month and year
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));

        // Gets the days in the week for the selected date
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        // Sets up the RecyclerView with a GridLayoutManager and CalendarAdapter
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setEventAdpater(); // Sets up the adapter for the event ListView
    }




    // Moves to the previous week and updates the view
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    // Moves to the next week and updates the view
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, String dayText) {

    }

    // Handles item click events in the calendar
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    // Called when the activity becomes visible to the user
    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater(); // Updates the event adapter
    }

    // Sets the adapter for the event ListView
    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate, time);
        EventAdapter eventAdapter = new EventAdapter(dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    // Starts a new activity to create a new event
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}
