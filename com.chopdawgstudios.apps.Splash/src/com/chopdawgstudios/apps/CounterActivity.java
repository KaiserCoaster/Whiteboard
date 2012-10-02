package com.chopdawgstudios.apps;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity {
	int total;
	Button add, subtract;
	TextView displayTotal;
	CharSequence totalString;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
               
        total = 0;
        add = (Button) findViewById(R.id.buttonAdd);
        subtract = (Button) findViewById(R.id.buttonSubtract);
        displayTotal = (TextView) findViewById(R.id.signifier);
        totalString = getText(R.string.total);        
        updateTotal();
                
        add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				total++;
				updateTotal();
			}
        });
        
        subtract.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				total--;
				updateTotal();
			}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_counter, menu);
        return true;
    }
    
    
    private void updateTotal() {
		displayTotal.setText(totalString + " " + total);
    }
}
