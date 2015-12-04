package collpoll.task.bundesligastandings;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import collpoll.task.bundesligastandings.Helper.CallHandler;


public class SecondAct extends ActionBarActivity {

    private ProgressDialog pDialog;


    private static String url = "http://www.football-data.org/alpha/soccerseasons/394/leagueTable";


    private static final String TAG_STANDING = "standing";
    private static final String TAG_POSITION = "position";
    private static final String TAG_TEAMNAME = "teamName";
    private static final String TAG_POINTS = "points";


    JSONArray standings = null;


    ArrayList<HashMap<String, String>> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        teamList = new ArrayList<HashMap<String, String>>();

        new GetStandings().execute();

    }

        private class GetStandings extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(SecondAct.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();

            }

            @Override
            protected Void doInBackground(Void... arg0) {

                CallHandler ch = new CallHandler();

                String jsonString = ch.fetchJson(url, CallHandler.GET);

                Log.d("Response: ", "> " + jsonString);

                if (jsonString != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonString);

                        standings = jsonObj.getJSONArray(TAG_STANDING);

                        for (int i = 0; i < standings.length(); i++) {
                            JSONObject ob = standings.getJSONObject(i);

                            String pos = ob.getString(TAG_POSITION);
                            String t_name = ob.getString(TAG_TEAMNAME);
                            String points = ob.getString(TAG_POINTS);

                           HashMap<String, String> team = new HashMap<String, String>();

                            team.put(TAG_POSITION, pos);
                            team.put(TAG_TEAMNAME, t_name);
                            team.put(TAG_POINTS, points);

                            teamList.add(team);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("CallHandler", "Couldn't get any data from the url");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                if (pDialog.isShowing())
                    pDialog.dismiss();

                ListView lv = (ListView) findViewById(R.id.listv);

                ListAdapter adapter = new SimpleAdapter(
                        SecondAct.this, teamList,
                        R.layout.list_item, new String[] { TAG_TEAMNAME, TAG_POSITION, TAG_POINTS }, new int[] { R.id.name,
                        R.id.position, R.id.points });

               lv.setAdapter(adapter);
            }

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
