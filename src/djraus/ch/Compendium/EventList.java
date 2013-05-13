package djraus.ch.Compendium;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import com.actionbarsherlock.app.SherlockListActivity;
import djraus.ch.Compendium.adapters.EventListAdapter;
import djraus.ch.Compendium.model.Event;

import java.util.ArrayList;

public class EventList extends SherlockListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 0; i < 10; i++) {
            Event e = new Event(i, "Main Event" + i, 10l, "Main Event", "MAIN EVENT STARTING", "Main Event. NAVI");
            events.add(e);
        }
        EventListAdapter eventListAdapter = new EventListAdapter(this, events);
        this.setListAdapter(eventListAdapter);

        Resources r = getResources();
        int px15 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                15, r.getDisplayMetrics());
        int px10 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, r.getDisplayMetrics());

        getListView().setPadding(px15, px10, px15, px10);
        getListView().setDivider(this.getResources().getDrawable(
                android.R.color.transparent));
        getListView().setDividerHeight(px10);
    }
}