package webservice;


import entries.LeaderBoardEnrty;
import gameConstants.NetworkConstants;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Operations related to Web Service API
 */
public class WebServiceOperations {

    /**
     * AddToLeaderBoard adds the player who has the given nickname with given score.
     */
    public static void addToLeaderBoard(int score, String nickname) {
        String date = now();
        String s = Integer.toString(score);
        String id = getPlayerID(nickname);

        String query_url = NetworkConstants.webServiceURL + "/leaderboard";
        String json = "{ \"date\": \"" + date +"\", \"score\": \""+ s +"\", \"p_id\": \""+ id +"\"}";

        operation(query_url, json, "POST");
    }

    /**
     * Now returns current date. It is used to add the player to the score board.
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    /**
     * GetWeeklyLeaderBoardEntries returns weekly leader board entries.
     */
    public static List<LeaderBoardEnrty> getWeeklyLeaderBoardEntries() {
        String query_url = NetworkConstants.webServiceURL + "/leaderboardweekly";
        String json = "";

        String result = operation(query_url, json, "GET");

        return setLeaderBoard(result);
    }

    /**
     * GetAllTimesLeaderBoardEntries returns all leader board entries.
     */
    public static List<LeaderBoardEnrty> getAllTimesLeaderBoardEntries() {
        String query_url = NetworkConstants.webServiceURL + "/leaderboardalltimes";
        String json = "";

        String result = operation(query_url, json, "GET");

        return setLeaderBoard(result);
    }

    /**
     * SetLeaderBoard parses json object and generates returnObject.
     */
    private static List<LeaderBoardEnrty> setLeaderBoard(String result) {
        List<LeaderBoardEnrty> returnObject = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) {
                LeaderBoardEnrty leaderBoardEnrty = new LeaderBoardEnrty();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                leaderBoardEnrty.setRank(i+1);
                leaderBoardEnrty.setScore(jsonObject.getInt("score"));
                leaderBoardEnrty.setNickname(getPlayerNick(jsonObject.getInt("p_id")));
                returnObject.add(leaderBoardEnrty);
            }
            return returnObject;
        } catch (JSONException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * GetPlayerNick returns nickname of the player who has the given id.
     */
    public static String getPlayerNick(Integer id) {
        //Get Player
        String query_url = NetworkConstants.webServiceURL + "/players/" + id.toString();
        String result = operation(query_url, "" , "GET");

        JSONObject myresponse;
        try {
            myresponse = new JSONObject(result);
            return myresponse.getString("nickname");
        } catch (JSONException e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * GetPlayerID returns id of the player who has the given nickname.
     */
    public static String getPlayerID(String nickname) {
        //Get Player id from nickname
        String query_url = NetworkConstants.webServiceURL + "/getPlayerId/" + nickname;
        String result = operation(query_url, "" , "GET");
        return result;
    }

    /**
     * isNickNameExist controls the database to validate registration.
     */
    public static boolean isNickNameExists(String nickname) {
        String query_url = NetworkConstants.webServiceURL + "/checkplayer";
        String json = nickname;

        String result = operation(query_url, json, "POST");
        return !result.equals("false");

    }

    /**
     * RegisterPlayer adds a player to database.
     */
    public static void registerPlayer(String nickname, String password) {
        String query_url = NetworkConstants.webServiceURL + "/addplayer";
        String json = "{ \"nickname\": \"" + nickname + "\", \"password\": \""+ password +"\" }";

        operation(query_url, json, "POST");

    }

    /**
     * canLogIn controls the given password and nickname to check if they match with database.
     */
    public static boolean canLogIn(String nickname, String password) {
        String query_url = NetworkConstants.webServiceURL + "/canLogIn";
        String json = "{ \"nickname\": \"" + nickname + "\", \"password\": \""+ password +"\" }";

        String result = operation(query_url, json, "POST");
        return result.equals("true");

    }

    /**
     * Operation sends a request to database with given url, type and json object
     * and takes response as a string and returns it.
     */
    private static String operation(String q_url, String json, String type) {
        try {
            URL url = new URL(q_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(type);
            if(type.equals("POST") || type.equals("PUT")){
                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
            //read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            in.close();
            conn.disconnect();
            return result;

        } catch (Exception e) {
            System.out.println(e);
            return "exception";
        }
    }

}
