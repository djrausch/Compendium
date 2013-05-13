package djraus.ch.Compendium;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockListActivity;
import djraus.ch.Compendium.adapters.EventListAdapter;
import djraus.ch.Compendium.model.Event;

import java.util.ArrayList;

public class EventList extends SherlockListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        ArrayList<Event> events = new ArrayList<Event>();
        for(int i = 0; i<10; i++){
            Event e = new Event(i,"Main Event"+i,10l,"Main Event","MAIN EVENT STARTING","Main Event. NAVI");
            events.add(e);
        }
        EventListAdapter eventListAdapter = new EventListAdapter(this,events);
        this.setListAdapter(eventListAdapter);
    }
}