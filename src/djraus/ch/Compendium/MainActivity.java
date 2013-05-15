package djraus.ch.Compendium;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import djraus.ch.Compendium.adapters.GoalListAdapter;
import djraus.ch.Compendium.model.Goal;
import djraus.ch.Compendium.util.Utility;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends SherlockListActivity {
    private TextView prizePool;
    private Timer t;
    private TextView totalSold;
    private GoalListAdapter goalListAdapter;
    private int currentPrizePool;
    private ArrayList<Goal> goals = new ArrayList<Goal>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        prizePool = (TextView) findViewById(R.id.prizePool);
        totalSold = (TextView) findViewById(R.id.totalSold);
        goalListAdapter = new GoalListAdapter(MainActivity.this, goals, currentPrizePool);
        this.setListAdapter(goalListAdapter);
        Resources r = getResources();
        int px15 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                15, r.getDisplayMetrics());
        int px10 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, r.getDisplayMetrics());

        getListView().setPadding(px15, px10, px15, px10);
        getListView().setDivider(this.getResources().getDrawable(
                android.R.color.transparent));
        getListView().setDividerHeight(px10);
        getListView().setVerticalScrollBarEnabled(false);
        new SetPrizePool(false).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Buy Compendium").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String url = "http://www.dota2.com/store/itemdetails/15162?appid=570";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("Refresh Prize Pool").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new SetPrizePool(true).execute();
                return true;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
       /* menu.add("Event List").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, EventList.class);
                startActivity(i);
                return true;
            }
        });*/
        return super.onCreateOptionsMenu(menu);
    }

    private class SetPrizePool extends AsyncTask<Void, Void, Integer> {
        private boolean update;

        public SetPrizePool(boolean update) {
            this.update = update;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            goals.add(new Goal("Goal 1", 1700000, "Compendium owners receive a Limited Edition 125% Battle Booster that lasts until The International ends."));
            goals.add(new Goal("Goal 2", 1850000, "Compendium Courier gains additional stages."));
            goals.add(new Goal("Goal 3", 2000000, "Compendium owners will receive a custom HUD skin."));
            goals.add(new Goal("Goal 4", 2200000, "Compendium owners will receive a Taunt item with a brand new animation."));
            goals.add(new Goal("Goal 5", 2400000, "Compendium owners get to vote on participants in an 8 player Solo Championship (1 vs 1) at The International."));
            goals.add(new Goal("Goal 6", 2600000, "Compendium owners will receive a new Immortal item."));
            goals.add(new Goal("Goal 7", 3200000, "Compendium Owners will be able to select the next Hero shipped in Dota 2."));
            currentPrizePool = Utility.getPrizePool();
            return currentPrizePool;
        }

        @Override
        protected void onPostExecute(Integer currentPrizePool) {
            prizePool.setText(Utility.convertMoneyToString(currentPrizePool));
            totalSold.setText(Utility.getSoldCompendiums(currentPrizePool) + " " + getResources().getString(R.string.compendiumsSold));
            goalListAdapter.updateCurrentPrizePool(currentPrizePool);
        }
    }

    private class UpdatePrizePoolTask extends TimerTask {
        public void run() {
            new SetPrizePool(true).execute();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        t.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        t = new Timer();
        t.schedule(new UpdatePrizePoolTask(), 30000, 30000);
    }
}
