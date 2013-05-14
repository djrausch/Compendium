package djraus.ch.Compendium;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import djraus.ch.Compendium.util.Utility;

import java.text.DecimalFormat;

public class WidgetReciever extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("CompendiumWidget", "onUpdate");

        for (int widgetId : appWidgetIds) {
            new GetPrizePool(widgetId, context, appWidgetManager).execute();
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews rmViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            rmViews.setOnClickPendingIntent(R.id.iconButton, pIntent);
            appWidgetManager.updateAppWidget(widgetId, rmViews);
        }
    }

    private String[] getPoolResults(int backgroundResults, Resources res) {
        int currPool = backgroundResults;
        int nextGoal = 0;
        if (currPool >= 2600000) {
            //Over
        } else if (currPool >= 2000000) {
            nextGoal = 2600000;
        } else if (currPool >= 1850000) {
            nextGoal = 2000000;
        } else if (currPool >= 1700000) {
            nextGoal = 1850000;
        } else {
            nextGoal = 1700000;
        }
        int amountLeft = nextGoal - currPool;
        DecimalFormat df = new DecimalFormat("#,###,##0");  //Nicely formats decimal numbers

        String[] results = {res.getString(R.string.current) + " $" + df.format(currPool), res.getString(R.string.goalWidget) + " $" + df.format(nextGoal), res.getString(R.string.remainder) + " $" + df.format(amountLeft)};  //an array of all elements to go into the widget

        return results;
    }

    private class GetPrizePool extends AsyncTask<Void, Void, Integer> {

        Context c = null;
        int id = 0;
        AppWidgetManager manager = null;

        public GetPrizePool(int widgetId, Context context, AppWidgetManager appWidgetManager) {
            c = context;
            id = widgetId;
            manager = appWidgetManager;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Log.d("ConpendiumWidget", "doInBackground");
            return Utility.getPrizePool();
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            Log.d("ConpendiumWidget", "onPost");
            RemoteViews rmViews = new RemoteViews(c.getPackageName(), R.layout.widget_layout);
            String[] backgroundResults = getPoolResults(result, c.getResources());

            rmViews.setTextViewText(R.id.widgetCurrentProgress, backgroundResults[0]);
            rmViews.setTextViewText(R.id.widgetNextGoal, backgroundResults[1]);
            rmViews.setTextViewText(R.id.widgetMoneyLeft, backgroundResults[2]);

            manager.updateAppWidget(id, rmViews);
        }

    }

}
