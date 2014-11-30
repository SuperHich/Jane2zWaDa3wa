package com.janaezwadaawa;

import java.util.Calendar;
import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.gcm.GcmManager;
import com.janaezwadaawa.gcm.GcmManager.GcmListener;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.MyLocationManager;

public class IndexActivity extends FragmentActivity implements OnTouchListener, LocationListener{

	private static final String PLACES_FRAGMENT = null;
	private TextView txv_place , txv_date ;
	private Button settings, share , about ;
	private Button dourouss, jana2ez ;

	private int gDay, gMonth, gYear,
	hDay, hMonth, hYear ;

	private Fragment fragment;

	private JDManager mManager;

	private MyLocationManager mLocationManager;
	private GcmManager gcmManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_activity);

		mManager = JDManager.getInstance(this);
		mLocationManager = MyLocationManager.getIntance(this);
		mLocationManager.start();
		mLocationManager.register(this);

		settings 		= (Button) findViewById(R.id.settings);
		share 			= (Button) findViewById(R.id.share);
		about 			= (Button) findViewById(R.id.about);
		dourouss 		= (Button) findViewById(R.id.jana2ez);
		jana2ez 		= (Button) findViewById(R.id.dourouss);

		txv_place = (TextView) findViewById(R.id.txv_place);
		txv_date = (TextView) findViewById(R.id.txv_date);

		JDFonts.Init(this);

		txv_place.setTypeface(JDFonts.getBDRFont());
		txv_date.setTypeface(JDFonts.getBDRFont());

		if(mManager.getSelectedPlace() != null)
			txv_place.setText(mManager.getSelectedPlace().getTitle());

		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		gDay = calendar.get(Calendar.DAY_OF_MONTH);
		gMonth = calendar.get(Calendar.MONTH) + 1;
		gYear = calendar.get(Calendar.YEAR);

		GHTDate gDate = Hijri.GregorianToHijri(gYear, gMonth, gDay);
		//		Log.i("refreshGDate", gDate.toString());


		gDay = gDate.getDayG();
		gMonth = gDate.getMonthG();
		gYear = gDate.getYearG();

		hDay = gDate.getDayH();
		hMonth = gDate.getMonthH();
		hYear = gDate.getYearH();

		txv_date.setText( hDay + " " + gDate.getMonthNameH() + "  " + hYear + " هـ.");

		//	txv_date.setText(gDate.getDayNameH() + " " + hDay + " " + gDate.getMonthNameH() + " " + hYear);

		settings.setOnTouchListener(this);
		share.setOnTouchListener(this);
		about.setOnTouchListener(this);
		jana2ez.setOnTouchListener(this);
		dourouss.setOnTouchListener(this);
		
		initGCM();

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

				if(mManager.getSelectedPlace() == null){
					Toast.makeText(IndexActivity.this, R.string.select_place, Toast.LENGTH_LONG).show();
				}else{
					Intent intent1 = new Intent(IndexActivity.this, MainActivity.class);
					intent1.putExtra(MainActivity.DEFAULT_FRAG_POSITION, 0);
					startActivity(intent1);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
					finish();
				}
				break;
			case R.id.dourouss:
				//				if(jdManager.getSelectedPlace() == null){
				//					Toast.makeText(IndexActivity.this, R.string.select_place, Toast.LENGTH_LONG).show();
				//				}else{
				Intent intent2 = new Intent(IndexActivity.this, MainActivity.class);
				intent2.putExtra(MainActivity.DEFAULT_FRAG_POSITION, 1);
				startActivity(intent2);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
				finish();
				//				}
				break;
			case R.id.settings:
				goToSettingsFragment();
				break;
			case R.id.about:
				goToAboutFragment();
				break;
			case R.id.share:
				shareApp("بادر بتحميل تطبيق جنائز و دعوة http://goo.gl/Hvt1jT");
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

	

	public void goToAboutFragment(){

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);


		fragment = new AboutFragment();

		transaction.replace(R.id.fragment_view, fragment, PLACES_FRAGMENT);
		transaction.addToBackStack(null);

		transaction.commit();

		setEnableState(false);

	}
	
	
	public void goToSettingsFragment(){

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);


		fragment = new SettingsFragment();

		transaction.replace(R.id.fragment_view, fragment, PLACES_FRAGMENT);
		transaction.addToBackStack(null);

		transaction.commit();

		setEnableState(false);

	}

	public void onPlaceSelected(Place place){

		mManager.setSelectedPlace(place);
		txv_place.setText(place.getTitle());
		onBackPressed();
	}

	private void setEnableState(boolean enabled){
		jana2ez.setEnabled(enabled);
		dourouss.setEnabled(enabled);
		settings.setEnabled(enabled);
		share.setEnabled(enabled);
		about.setEnabled(enabled);
	}

	@Override
	public void onBackPressed() {

		if(fragment != null){
			getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			setEnableState(true);
			fragment = null;
		}else
			super.onBackPressed();
	}

	private void shareApp(String text){

		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String locality = mLocationManager.getLocality();
		if(locality != null){
			if(mManager.refreshLocalityIfPossible(locality))
				txv_place.setText(mManager.getSelectedPlace().getTitle());
		}

		mLocationManager.remove(this);
		mLocationManager.stop();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
	
	private void initGCM() {

		gcmManager = new GcmManager(this);
		gcmManager.setOnGcmListener(new GcmListener() {
			@Override
			public void onRegistrationComplete(String registrationId) {
				Log.e("XX", "" + registrationId) ;
//				updateDeviceToken("" + registrationId);
			}

		});

	}


	protected void resetGcm(){

		gcmManager = null ;
		initGCM();

	}
}
