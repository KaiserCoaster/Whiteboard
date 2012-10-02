package com.chopdawgstudios.apps;

import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.chopdawgstudios.apps.http.HTTP;
import com.chopdawgstudios.apps.widget.custom.ArrayAdapterSpecial;

public class PostFeed extends Activity {
	ListView list;
	LinkedList<String[]> listItems = new LinkedList<String[]>();
	PostFeed postFeed = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        new asyncCheck().execute();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }
    
    void load() {
		finish();
		startActivity(getIntent());
    }
    
    class asyncCheck extends AsyncTask<Integer, Integer, Integer> {
		@Override
		protected Integer doInBackground(Integer... params) {	
			try {
				JSONObject json = new JSONObject(new HTTP().get("http://apps.chopdawgstudios.com/whiteboard/androidGrab.php?mode=post_stream"));
			    
				for(int i = 0; i < json.length(); i++) {
			        try {
			            JSONObject j = json.getJSONObject("data_" + i);
						
			            if(j.getInt("action") == 1) {
			            	String[] element = {j.getString("display_name") + " " + getResources().getString(R.string.feed_comment), j.getString("timestamp"), j.getString("display_name").toLowerCase() + "_sm", j.getInt("id") + ""};
			            	listItems.add(element);
			            } else if(j.getInt("action") == 3) {
			            	String[] element = {j.getString("display_name") + " " + getResources().getString(R.string.feed_love), j.getString("timestamp"), j.getString("display_name").toLowerCase() + "_sm", j.getInt("id") + ""};
			            	listItems.add(element);
			        	} else {
			            	String[] element = {j.getString("display_name") + " posted a " + j.getString("subject") + "." , j.getString("timestamp"), j.getString("display_name").toLowerCase() + "_sm", j.getInt("id") + ""};
			            	listItems.add(element);
			            }
			        } catch (JSONException e) {
			            e.printStackTrace();
			        }
			    }
			} catch (JSONException e) {
		        setContentView(R.layout.activity_menu);
				e.printStackTrace();
			}

			return -1;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			     
	        String refresh[] = {"Refresh", "", "whitespace"};
	        String back[] = {"Back", "", "whitespace"};
	        getResources().getConfiguration();
			if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				refresh[2] = "refresh";
				back[2] = "back";
			}

	        listItems.add(back);
	        listItems.add(refresh);
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			list = (ListView) findViewById(R.id.list);
			
			final ArrayAdapterSpecial<String> adapter = new ArrayAdapterSpecial<String>(postFeed, R.layout.simple_list_custom_2, listItems);
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, final View arg1, int arg2, long arg3) {
					if(listItems.get(arg2)[0] == "Back") {					
						finish();
					}
					else if (listItems.get(arg2)[0] == "Refresh") {
						load();
					}
					else {					
						Intent i = new Intent("com.chopdawgstudios.apps.URLVIEWER");
						i.putExtra("id", (Integer) arg1.getTag());
						startActivity(i);
					}
				}
	        });
	        
	        list.setBackgroundColor(getResources().getColor(android.R.color.black));
	        list.setCacheColorHint(getResources().getColor(android.R.color.black));
	        list.setAdapter(adapter);
		}
    }
}
