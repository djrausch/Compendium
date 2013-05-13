package djraus.ch.Compendium;

import djraus.ch.Compendium.R;
import djraus.ch.Compendium.util.Utility;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DecimalFormat;

public class WidgetReciever extends AppWidgetProvider{
	
	Context c = null;

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
//		c = context;
//		Log.d("CompendiumWidget", "onPlace");
//		new GetPrizePool().execute();
//		
//		RemoteViews rmViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//		
//		rmViews.setTextViewText(R.id.widgetCurrentProgress, "0");
//		rmViews.setTextViewText(R.id.widgetNextGoal, "1");
//		rmViews.setTextViewText(R.id.widgetMoneyLeft, "2");
//		
//		String[] results = getPoolResults(context.getResources());
//		RemoteViews rmViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//		
//		rmViews.setTextViewText(R.id.widgetCurrentProgress, results[0]);
//		rmViews.setTextViewText(R.id.widgetNextGoal, results[1]);
//		rmViews.setTextViewText(R.id.widgetMoneyLeft, results[2]);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d("CompendiumWidget", "onUpdate");
		
		c = context;
		
		for(int widgetId:appWidgetIds){
			new GetPrizePool(widgetId, context, appWidgetManager).execute();
		}		
	}
	
	private String[] getPoolResults(int backgroundResults, Resources res){
		int currPool = backgroundResults;
		int nextGoal = 2600000;
		int amountLeft = nextGoal-currPool;
		DecimalFormat df = new DecimalFormat("#,###,##0");  //Nicely formats decimal numbers
		
		String[] results = {res.getString(R.string.current) + "$" + df.format(currPool), res.getString(R.string.goalWidget) + "$" + df.format(nextGoal), res.getString(R.string.remainder) + "$" + df.format(amountLeft)};  //an array of all elements to go into the widget
		
		return results;
	}
	
	private class GetPrizePool extends AsyncTask<Void, Void, Integer>{
		
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
//			DecimalFormat df = new DecimalFormat("#,###,##0");
			RemoteViews rmViews = new RemoteViews(c.getPackageName(), R.layout.widget_layout);
			String[] backgroundResults = getPoolResults(result, c.getResources());
			
			rmViews.setTextViewText(R.id.widgetCurrentProgress, backgroundResults[0]);
			rmViews.setTextViewText(R.id.widgetNextGoal, backgroundResults[1]);
			rmViews.setTextViewText(R.id.widgetMoneyLeft, backgroundResults[2]);
			
			manager.updateAppWidget(id, rmViews);
		}
		
	}

}
