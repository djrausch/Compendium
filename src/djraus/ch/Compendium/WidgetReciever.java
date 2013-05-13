package djraus.ch.Compendium;

import djraus.ch.Compendium.R;
import djraus.ch.Compendium.util.WebRequest;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.res.Resources;
import android.widget.RemoteViews;

import java.text.DecimalFormat;

public class WidgetReciever extends AppWidgetProvider{
	
	Context c = null;

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
		String[] results = getPoolResults(context.getResources());
		RemoteViews rmViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		
		rmViews.setTextViewText(R.id.widgetCurrentProgress, results[0]);
		rmViews.setTextViewText(R.id.widgetNextGoal, results[1]);
		rmViews.setTextViewText(R.id.widgetMoneyLeft, results[2]);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
	}
	
	private String[] getPoolResults(Resources res){
		int currPool = WebRequest.getPrizePool();
		int nextGoal = 2600000;
		int amountLeft = nextGoal-currPool;
		DecimalFormat df = new DecimalFormat("#,###,##0");  //Nicely formats decimal numbers
		
		String[] results = {res.getString(R.string.current) + "$" + df.format(currPool), res.getString(R.string.goalWidget) + "$" + df.format(nextGoal), res.getString(R.string.remainder) + "$" + df.format(amountLeft)};  //an array of all elements to go into the widget
		
		return results;
	}
/*	
	private class GetPrizePool extends AsyncTask<Void, Void, Integer>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			return WebRequest.getPrizePool();
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			DecimalFormat df = new DecimalFormat("#,###,##0");
			RemoteViews rmViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			
		}
		
	}*/

}
