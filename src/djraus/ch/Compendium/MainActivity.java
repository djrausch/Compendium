package djraus.ch.Compendium;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import djraus.ch.Compendium.util.Utility;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends SherlockActivity {
    private TextView prizePool;
    private Timer t;
    private TextView totalSold;
    private TextView goal1Status;
    private TextView goal2Status;
    private TextView goal3Status;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        prizePool = (TextView) findViewById(R.id.prizePool);
        totalSold = (TextView) findViewById(R.id.totalSold);
        goal1Status = (TextView) findViewById(R.id.statusGoal1);
        goal2Status = (TextView) findViewById(R.id.statusGoal2);
        goal3Status = (TextView) findViewById(R.id.statusGoal3);
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
        menu.add("Event List").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,EventList.class);
                startActivity(i);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private class SetPrizePool extends AsyncTask<Void,Void,Integer>{
        private boolean update;
        public SetPrizePool(boolean update){
            this.update = update;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            return Utility.getPrizePool();
        }
        @Override
        protected void onPostExecute(Integer result) {
            prizePool.setText(Utility.convertMoneyToString(result));
            if(result>=2600000){
                goal1Status.setText(R.string.completed);
                goal2Status.setText(R.string.completed);
                goal3Status.setText(R.string.completed);
            } else if(result>=1850000){
                goal1Status.setText(R.string.completed);
                goal2Status.setText(R.string.completed);
                goal3Status.setText(Utility.getRemainingCompendiums(2600000, result));
            } else if(result>=1700000){
                goal1Status.setText(R.string.completed);
                goal2Status.setText(Utility.getRemainingCompendiums(1850000, result));
                goal3Status.setText(Utility.getRemainingCompendiums(2600000, result));
            } else{
                goal1Status.setText(Utility.getRemainingCompendiums(1700000, result));
                goal2Status.setText(Utility.getRemainingCompendiums(1850000, result));
                goal3Status.setText(Utility.getRemainingCompendiums(2600000, result));
            }
            totalSold.setText(Utility.getSoldCompendiums(result) + " " + getResources().getString(R.string.compendiumsSold));
            if(update){
                //Toast.makeText(MainActivity.this,"Prize Pool Updated!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UpdatePrizePoolTask extends TimerTask {
        public void run(){
            new SetPrizePool(true).execute();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        t.cancel();
    }
    @Override
    public void onResume(){
        super.onResume();
        t = new Timer();
        t.schedule(new UpdatePrizePoolTask(),30000,30000);
    }
}
