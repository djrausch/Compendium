package djraus.ch.Compendium.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import djraus.ch.Compendium.R;
import djraus.ch.Compendium.model.Event;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {
    private Activity context;
    private ArrayList<Event> events;

    public EventListAdapter(Activity context, ArrayList<Event> events) {
        super(context, R.layout.event_list_row, events);
        this.context = context;
        this.events = events;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            rowView = context.getLayoutInflater().inflate(R.layout.event_list_row, null);
        }
        /*TextView eventTitle = (TextView) rowView.findViewById(R.id.eventTitle);
        TextView eventCountdown = (TextView) rowView.findViewById(R.id.eventCountdown);
        eventTitle.setText(events.get(position).getTitle());*/

        return rowView;
    }
}
