package com.janaezwadaawa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.janaezwadaawa.utils.JDFonts;

public class IndexActivity extends Activity implements OnTouchListener{

	private TextView place , date ;
	private Button settings, share , about, medina_choice ;
	private Button dourouss, jana2ez ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_activity);

		settings 		= (Button) findViewById(R.id.settings);
		share 			= (Button) findViewById(R.id.share);
		about 			= (Button) findViewById(R.id.about);
		medina_choice 	= (Button) findViewById(R.id.medina_choice);
		dourouss 		= (Button) findViewById(R.id.jana2ez);
		jana2ez 		= (Button) findViewById(R.id.dourouss);

		place = (TextView) findViewById(R.id.place);
		date = (TextView) findViewById(R.id.date);

		JDFonts.Init(this);

		place.setTypeface(JDFonts.getBDRFont());
		date.setTypeface(JDFonts.getBDRFont());

		settings.setOnTouchListener(this);
		share.setOnTouchListener(this);
		about.setOnTouchListener(this);
		medina_choice.setOnTouchListener(this);
		jana2ez.setOnTouchListener(this);
		dourouss.setOnTouchListener(this);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Button view = (Button) v;
			view.getBackground().setColorFilter(0x7785dda8, PorterDuff.Mode.SRC_ATOP);
			v.invalidate();
			break;
		}
		case MotionEvent.ACTION_UP: {
			
			switch (v.getId()) {
			case R.id.jana2ez:
				Intent intent1 = new Intent(IndexActivity.this, MainActivity.class);
				intent1.putExtra(MainActivity.DEFAULT_FRAG_POSITION, 0);
				startActivity(intent1);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
				finish();
				break;
			case R.id.dourouss:
				Intent intent2 = new Intent(IndexActivity.this, MainActivity.class);
				intent2.putExtra(MainActivity.DEFAULT_FRAG_POSITION, 1);
				startActivity(intent2);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
				finish();
				break;
			case R.id.settings:
				break;
			case R.id.about:
				break;
			case R.id.share:
				break;
			case R.id.medina_choice:
				break;

			default:
				break;
			}
			
		}
		case MotionEvent.ACTION_CANCEL: {
			Button view = (Button) v;
			view.getBackground().clearColorFilter();
			view.invalidate();
			break;
		}
		}
		return true;
	}
}
