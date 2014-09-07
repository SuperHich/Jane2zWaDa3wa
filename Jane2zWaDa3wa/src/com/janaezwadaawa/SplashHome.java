package com.janaezwadaawa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.janaezwadaawa.utils.Utils;


/**
 * Sa7i7 Al Boukhari
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class SplashHome extends Activity {
	
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 3000;
	
	public static final int MESSAGE_START = 1;
	public static final int MESSAGE_FINISH = 2;
	
	private RelativeLayout principal_layout;
	
	private Handler splashHandler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
				
			case MESSAGE_FINISH :
				
				SplashHome.this.startActivity(new Intent(SplashHome.this, IndexActivity.class));
				Utils.animateSlide(SplashHome.this);
				SplashHome.this.finish();
				
				break;

			}
			

			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashhome);
		
		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
		
		Message msg = Message.obtain();
		msg.what = MESSAGE_FINISH;
	    splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		
		
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			splashHandler.removeMessages(STOPSPLASH);
		}
		return super.onKeyDown(keyCode, event);

	}	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Utils.cleanViews(principal_layout);
	}
	


}