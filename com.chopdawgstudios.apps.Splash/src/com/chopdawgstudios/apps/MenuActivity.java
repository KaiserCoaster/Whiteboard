package com.chopdawgstudios.apps;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends Activity {
	ListView list;
	int[] activities = {R.menu.activity_counter};
	ArrayList<String> listItems = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        list = (ListView) findViewById(R.id.list);
        
        listItems.add("Post Feed");
        listItems.add("Activity Feed");
        listItems.add("Settings");
        listItems.add("Debug");
        
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_custom_1, listItems);
        
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(listItems.get(arg2) == "Debug") {					
					startActivity(new Intent("com.chopdawgstudios.apps.DEBUG"));
        		}
				else if (listItems.get(arg2) == "Activity Feed") {
					Thread delay = new Thread(){
						public void run() {
							try {
								setContentView(R.layout.loading);
								sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								finish();
								startActivity(getIntent());
								startActivity(new Intent("com.chopdawgstudios.apps.FEED"));
							}
						}
					};
					
					delay.run();
				}
				else if (listItems.get(arg2) == "Post Feed") {
					Thread delay = new Thread(){
						public void run() {
							try {
								setContentView(R.layout.loading);
								sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								finish();
								startActivity(getIntent());
								startActivity(new Intent("com.chopdawgstudios.apps.POSTFEED"));
							}
						}
					};
					
					delay.run();
				}
			}
        });
        
        list.setBackgroundColor(getResources().getColor(android.R.color.black));
        list.setCacheColorHint(getResources().getColor(android.R.color.black));
        list.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }
}
