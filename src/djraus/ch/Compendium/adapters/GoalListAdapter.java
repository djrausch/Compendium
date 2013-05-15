package djraus.ch.Compendium.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import djraus.ch.Compendium.R;
import djraus.ch.Compendium.model.Event;
import djraus.ch.Compendium.model.Goal;
import djraus.ch.Compendium.util.Utility;

import java.util.ArrayList;

public class GoalListAdapter extends ArrayAdapter<Goal> {
    private Activity context;
    private ArrayList<Goal> goals;
    private int currentPrizePool;

    public GoalListAdapter(Activity context, ArrayList<Goal> goals, int currentPrizePool) {
        super(context, R.layout.goal_list_row, goals);
        this.context = context;
        this.goals = goals;
        this.currentPrizePool = currentPrizePool;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            rowView = context.getLayoutInflater().inflate(R.layout.goal_list_row, null);
        }
        TextView goalTitle = (TextView) rowView.findViewById(R.id.stretchGoalTitle);
        TextView goalPrice = (TextView) rowView.findViewById(R.id.stretchGoalPrice);
        TextView goalStatus = (TextView) rowView.findViewById(R.id.goalStatus);
        TextView goalDescription = (TextView) rowView.findViewById(R.id.stretchGoalDescription);

        Goal g = goals.get(position);

        goalTitle.setText(g.getTitle());
        goalPrice.setText(Utility.convertMoneyToString(g.getPrizePool()));
        goalDescription.setText(g.getDescription());

        if(currentPrizePool >= g.getPrizePool()){
            //Goal is completed
            goalStatus.setText(context.getResources().getString(R.string.completed));
        } else{
            //Goal is not completed
            goalStatus.setText(Utility.getRemainingCompendiums(g.getPrizePool(), currentPrizePool));
        }
        return rowView;
    }

    public void updateCurrentPrizePool(int currentPrizePool){
        this.currentPrizePool = currentPrizePool;
        super.notifyDataSetChanged();
    }
}
