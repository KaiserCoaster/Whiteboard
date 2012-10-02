package com.chopdawgstudios.apps;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.chopdawgstudios.apps.http.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {	
	SparseArray<String> statusCodes = new SparseArray<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		statusCodes.put(200, "Ok.");
		statusCodes.put(401, "Incorrect email and/or password.");
		statusCodes.put(403, "Your account has been disabled.");
		statusCodes.put(405, "Please wait 5 minutes, too many failed attempts.");

		Button login = (Button) findViewById(R.id.loginButton);
		
		login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String user = ((EditText) findViewById(R.id.userField)).getText().toString();
				String password = ((EditText) findViewById(R.id.passwordField)).getText().toString();
								
				if(user.length() > 5 && password.length() > 5) {
					checkLogin(user, password);
				} else {
					errorParse("Invalid Username / Password!");
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	void checkLogin(String user, String password) {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("email", user);
		params.put("password", password);
		params.put("session", "0");
		
		new asyncCheck().execute(params);
	}
	
	void errorParse(String error) {
		TextView errorTextView = (TextView) findViewById(R.id.errorTextView);
		errorTextView.setText(error);
	}
	
	class asyncCheck extends AsyncTask<Map<String, String>, Integer, Integer> {
		@Override
		protected Integer doInBackground(Map<String, String>... params) {	
			try {
				JSONObject status = new JSONObject(new HTTP().post("http://www.chopdawgstudios.com/login/auth.php", params[0]));
				
				return status.getInt("status_code");
			} catch (JSONException e) {
				e.printStackTrace(); 
			}	

			return -1;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			findViewById(R.id.userField).setVisibility(View.GONE);
			findViewById(R.id.passwordField).setVisibility(View.GONE);
			findViewById(R.id.loginButton).setVisibility(View.GONE);
			findViewById(R.id.errorTextView).setVisibility(View.GONE);
						
			setTitle("Checking Login . . .");
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if(result == 200) {
				finish();
				startActivity(new Intent("com.chopdawgstudios.apps.SPLASH"));
			} else {
				if(statusCodes.get(result) != null) errorParse(statusCodes.get(result));
				else errorParse("Server Timed Out! " + result);
				
				findViewById(R.id.userField).setVisibility(View.VISIBLE);
				findViewById(R.id.passwordField).setVisibility(View.VISIBLE);
				findViewById(R.id.loginButton).setVisibility(View.VISIBLE);
				findViewById(R.id.errorTextView).setVisibility(View.VISIBLE);
				
				setTitle("Log In");
			}
		}
	}
}
