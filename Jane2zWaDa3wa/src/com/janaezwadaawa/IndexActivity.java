package com.janaezwadaawa;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.utils.JDFonts;

public class IndexActivity extends FragmentActivity implements OnTouchListener{

	private static final String PLACES_FRAGMENT = null;
	private TextView txv_place , txv_date ;
	private Button settings, share , about, medina_choice ;
	private Button dourouss, jana2ez ;
	
	private Fragment fragment;

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

		txv_place = (TextView) findViewById(R.id.txv_place);
		txv_date = (TextView) findViewById(R.id.txv_date);

		JDFonts.Init(this);

		txv_place.setTypeface(JDFonts.getBDRFont());
		txv_date.setTypeface(JDFonts.getBDRFont());

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
				goToPlacesFragment();
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
	
	public void goToPlacesFragment(){
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);

		fragment = new PlacesFragment();

		transaction.replace(R.id.fragment_view, fragment, PLACES_FRAGMENT);
		transaction.addToBackStack(PLACES_FRAGMENT);

		transaction.commit();
		
		setEnableState(false);

	}
	
	public void onPlaceSelected(Place place){
		
		txv_place.setText(place.getTitle());
		
	}
	
	private void setEnableState(boolean enabled){
		jana2ez.setEnabled(enabled);
		dourouss.setEnabled(enabled);
		settings.setEnabled(enabled);
		share.setEnabled(enabled);
		about.setEnabled(enabled);
		medina_choice.setEnabled(enabled);
	}
	
	@Override
	public void onBackPressed() {
		
		if(fragment != null){
			setEnableState(true);
			fragment = null;
		}
		super.onBackPressed();
	}
}
