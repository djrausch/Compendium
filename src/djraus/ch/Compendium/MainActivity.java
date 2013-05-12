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
import djraus.ch.Compendium.util.WebRequest;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends SherlockActivity {
    TextView prizePool;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        prizePool = (TextView) findViewById(R.id.prizePool);
        new SetPrizePool(false).execute();
        Timer t = new Timer();
        t.schedule(new UpdatePrizePoolTask(),30000,30000);
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
        return super.onCreateOptionsMenu(menu);
    }

    private class SetPrizePool extends AsyncTask<Void,Void,Integer>{
        private boolean update;
        public SetPrizePool(boolean update){
            this.update = update;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            return WebRequest.getPrizePool();
        }
        @Override
        protected void onPostExecute(Integer result) {
            prizePool.setText(WebRequest.convertMoneyToString(result));
            if(update){
                Toast.makeText(MainActivity.this,"Prize Pool Updated!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UpdatePrizePoolTask extends TimerTask {
        public void run(){
            new SetPrizePool(true).execute();
        }
    }
}
