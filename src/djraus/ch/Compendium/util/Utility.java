package djraus.ch.Compendium.util;

import djraus.ch.Compendium.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Utility {

    /**
     * Formats an integer into USD string
     * @param dollars The dollar amount to convert
     * @return The string representation of the dollar amount with included $ and no decimals.
     */
    public static String convertMoneyToString(int dollars) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setCurrency(Currency.getInstance(Locale.US));

        return formatter.format(dollars).replace(".00", "");
    }

    /**
     * Gets the most recent prize pool amount for Dota servers.
     * @return The integer representation of the prize pool.
     */
    public static int getPrizePool() {
        String result = null;
        int prizePool;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httppost = new HttpGet("http://www.dota2.com/jsfeed/intlprizepool");
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            return 1850230;
        }

        try {
            InputStream is = response.getEntity().getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            reader.close();
        } catch (Exception e) {
            return 1850230;
        }

        try {
            JSONObject prizePoolObject = new JSONObject(result);
            prizePool = prizePoolObject.getInt("dollars");
            return prizePool;
        } catch (JSONException e) {
            return 1850230;
        }
    }

    /**
     * Used to show the remaining dollars and compendium purchases for the goal
     * @param goal The target dollar amount
     * @param amountSold The current dollar amount
     * @return A string with formatted response of $ - X Compendiums
     */
    public static String getRemainingCompendiums(int goal, int amountSold){
        int moneyToGo = goal - amountSold;
        int compendiumsToGo = (int) (moneyToGo /2.5);
        return convertMoneyToString(moneyToGo) + " - " + formatNumber(compendiumsToGo) + " Compendiums";
    }

    /**
     * Adds commas to a number
     * @param number The number to format
     * @return A string of the number with commas.
     */
    public static String formatNumber(int number){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        return formatter.format(number);
    }

    public static String getSoldCompendiums(int amountSold){
        return formatNumber((int) ((amountSold - 1600000) / 2.5));
    }
}
