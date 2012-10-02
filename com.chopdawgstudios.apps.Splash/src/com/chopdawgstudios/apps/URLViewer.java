package com.chopdawgstudios.apps;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopdawgstudios.apps.http.HTTP;
import com.chopdawgstudios.apps.widget.custom.ResourcesSpecial;

public class URLViewer extends Activity {
	ArrayList<String> listItems = new ArrayList<String>();
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        Integer[] params = {(Integer) extras.get("id")}; //Create an Array of Integers (Only one)
       
        new asyncCheck().execute(params);

	}

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }
    
    class asyncCheck extends AsyncTask<Integer, Integer, JSONObject> {
		@Override
		protected JSONObject doInBackground(Integer... params) {	
			try {			
				String url = "http://apps.chopdawgstudios.com/whiteboard/androidGrab.php?mode=get&id=" + params[0];
	        	JSONObject json = new JSONObject(new HTTP().get(url));
	        	JSONObject j = json.getJSONObject("data_1");
	        	return j;
	        } catch (JSONException e) {
	        	//Should refer to not found error
	            e.printStackTrace();
	        }

			return new JSONObject();
		}
		
		@Override
        protected void onPreExecute() {
                super.onPreExecute();
        setContentView(R.layout.loading); //Loading . . .
        }
		
		@Override
		protected void onPostExecute(JSONObject j) {
			super.onPostExecute(j);
			setContentView(R.layout.url_layout); //Loaded!
            
            TextView text1 = (TextView) findViewById(R.id.URLTextView1);
            TextView text2 = (TextView) findViewById(R.id.URLTextView2);
            ImageView image = (ImageView) findViewById(R.id.URLImageView);
            Button back = (Button) findViewById(R.id.backButton);
			try {
	            image.setBackgroundResource(ResourcesSpecial.getResId(j.getString("display_name").toLowerCase() + "_sm", null, R.drawable.class));
		        text1.setText((CharSequence) j.get("full_name"));
		        text2.setText((CharSequence) j.get("timestamp"));
		        setTitle(j.get("full_name") + " - " + j.get("timestamp")); //Better than URL Viewer
	        } catch (JSONException e) {
	        	//Should refer to not found error
	            e.printStackTrace();
	        }
			back.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					finish();					
				}
	        });
			
		}
		
    }
    
}
