package com.example.calendaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends ArrayAdapter<HourEvent>
{
    // Constructor for the HourAdapter
    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents)
    {
        super(context, 0, hourEvents);
    }

    // Returns the view for each item in the data set
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        // Get the HourEvent object for this position
        HourEvent event = getItem(position);

        // Inflate a new view if no reusable view is available
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);

        // Set the hour and events for this view
        setHour(convertView, event.time);
        setEvents(convertView, event.events);

        return convertView;
    }

    // Sets the hour text in the view
    private void setHour(View convertView, LocalTime time)
    {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.formattedTime(time));
    }

    // Sets the events in the view
    private void setEvents(View convertView, ArrayList<Event> events)
    {
        // Find the TextViews for displaying events
        TextView event1 = convertView.findViewById(R.id.event1);
        TextView event2 = convertView.findViewById(R.id.event2);
        TextView event3 = convertView.findViewById(R.id.event3);

        // Display events based on the number of events
        if(events.size() == 0)
        {
            // No events
            hideEvent(event1);
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 1)
        {
            // One event
            setEvent(event1, events.get(0));
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 2)
        {
            // Two events
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            hideEvent(event3);
        }
        else if(events.size() == 3)
        {
            // Three events
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            setEvent(event3, events.get(2));
        }
        else
        {
            // More than three events
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            event3.setVisibility(View.VISIBLE);
            String eventsNotShown = String.valueOf(events.size() - 2);
            eventsNotShown += " More Events";
            event3.setText(eventsNotShown);
        }
    }

    // Sets the event text and makes the TextView visible
    private void setEvent(TextView textView, Event event)
    {
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
    }

    // Hides the event TextView
    private void hideEvent(TextView tv)
    {
        tv.setVisibility(View.INVISIBLE);
    }
}

