package com.chopdawgstudios.apps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.splash);
		final Intent menu = new Intent("com.chopdawgstudios.apps.LOGIN");
		
		Thread delay = new Thread(){
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
					startActivity(menu);
				}
			}
		};
		
		delay.start();
	}
}
