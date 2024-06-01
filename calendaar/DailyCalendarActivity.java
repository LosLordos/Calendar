package com.example.calendaar;

import static com.example.calendaar.CalendarUtils.selectedDate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity
{
    // UI components
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar); // Sets the layout for the activity
        initWidgets(); // Initializes the UI components



    }

    // Initializes the UI components by finding them by their ID in the layout
    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    // Called when the activity becomes visible to the user
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView(); // Updates the view for the selected day
    }

    // Updates the view for the selected day
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDayView()
    {
        // Sets the text for the month and day
        monthDayText.setText(CalendarUtils.monthYearFromDate(selectedDate));
        // Sets the text for the day of the week
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter(); // Sets the adapter for the hour list view
    }

    // Sets the adapter for the hour list view
    private void setHourAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    // Generates a list of hourly events for the selected date
    private ArrayList<HourEvent> hourEventList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour < 24; hour++)
        {
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDate(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }

        return list;
    }

    // Moves to the previous day and updates the view
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousDayAction(View view)
    {
        selectedDate = selectedDate.minusDays(1);
        setDayView();
    }

    // Moves to the next day and updates the view
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextDayAction(View view)
    {
        selectedDate = selectedDate.plusDays(1);
        setDayView();
    }

    // Starts a new activity to create a new event
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}
