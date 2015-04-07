package com.janaezwadaawa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
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
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.Mosque2;
import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.gcm.GcmManager;
import com.janaezwadaawa.gcm.IGcmDispatcher;
import com.janaezwadaawa.gcm.GcmManager.GcmListener;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.MyLocationManager;
import com.janaezwadaawa.utils.Utils;

public class IndexActivity extends FragmentActivity implements OnTouchListener, LocationListener, IGcmDispatcher{

	public static final String PLACES_FRAGMENT 			= "places";
	public static final String LOGIN_FRAGMENT 			= "login";
	public static final String ABOUT_FRAGMENT 			= "about";
	public static final String SETTING_FRAGMENT 		= "setting";
	public static final String ADD_JANEZA_FRAGMENT 		= "add_janeza";
	public static final String ADD_MOHADHRA_FRAGMENT 	= "add_mohadhra";
	public static final String ADD_I9TIRAH_FRAGMENT 	= "add_i9tirah";
	public static final String ADMIN_FRAGMENT 			= "admin";
	protected static final String TAG = null;
	
	private TextView txv_place , txv_date, txv_janaez_total, txv_da3awi_total  ;
	private Button  about , medina_choice, btn_suggestions, btn_enter;
	private Button dourouss, jana2ez ;

//	settings,share , 
	
	private int gDay, gMonth, gYear,
	hDay, hMonth, hYear ;

	private Fragment fragment;
	private String currentTAG;

	private JDManager mManager;

	private MyLocationManager mLocationManager;
	private GcmManager gcmManager;
	
	
//	private DrawerLayout mDrawerLayout;
//	private LinearLayout mDrawerLinear;
//	private ActionBarDrawerToggle mDrawerToggle;
//	private Button btn_menu_outside;
//	private ListView listView;
//	private ElementsListAdapter adapter;
//	private RelativeLayout moving_layout;
//	ArrayList<String> drawerItems = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_activity);

		mManager = JDManager.getInstance(this);
		mManager.setGcmDispatcher(this);
		
		mLocationManager = MyLocationManager.getIntance(this);
		mLocationManager.start();
		mLocationManager.register(this);

		try {

			mManager.setBadgeCounter(0);
			ShortcutBadger.setBadge(getApplicationContext(), mManager.getBadgeCounter());

		} catch (ShortcutBadgeException e) {
			e.printStackTrace();
		}

		medina_choice 		= (Button) findViewById(R.id.medina_choice);
		btn_suggestions 	= (Button) findViewById(R.id.btn_suggestions);
		btn_enter 			= (Button) findViewById(R.id.btn_enter);
		
		about 			= (Button) findViewById(R.id.about);
		dourouss 		= (Button) findViewById(R.id.jana2ez);
		jana2ez 		= (Button) findViewById(R.id.dourouss);

		txv_place = (TextView) findViewById(R.id.txv_place);
		txv_date = (TextView) findViewById(R.id.txv_date);
		txv_janaez_total = (TextView) findViewById(R.id.txv_janaez_total);
		txv_da3awi_total = (TextView) findViewById(R.id.txv_da3awi_total);
		
//		btn_menu_outside 	= (Button) findViewById(R.id.btn_menu_outside);
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		mDrawerLinear = (LinearLayout) findViewById(R.id.drawer_linear);
//		moving_layout = (RelativeLayout) findViewById(R.id.moving_layout);
//		listView = (ListView) findViewById(R.id.listView);
		

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

		medina_choice.setOnTouchListener(this);
		btn_enter.setOnTouchListener(this);
		btn_suggestions.setOnTouchListener(this);
		about.setOnTouchListener(this);
		jana2ez.setOnTouchListener(this);
		dourouss.setOnTouchListener(this);

		initGCM();

//		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//
//		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
//
//				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
//
//			public void onDrawerClosed(View view) {
//				supportInvalidateOptionsMenu();
//			}
//
//			public void onDrawerOpened(View drawerView) {
//				supportInvalidateOptionsMenu();
//			}
//
//			@Override
//			public void onDrawerSlide(View drawerView, float slideOffset) {
//				super.onDrawerSlide(drawerView, slideOffset);
//						moving_layout.setTranslationX(- slideOffset * drawerView.getWidth());
//				mDrawerLayout.bringChildToFront(drawerView);
//				mDrawerLayout.requestLayout();
//			}
//		};
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//		btn_menu_outside.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
////				if(!mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
//					mDrawerLayout.openDrawer(Gravity.RIGHT);
//				
//			}
//		});
//		adapter = new ElementsListAdapter(IndexActivity.this, drawerItems);
//		listView.setAdapter(adapter);
//		listView.setCacheColorHint(Color.TRANSPARENT);
//		
//		
//		prepareDrawerItems();
	}

//	private void prepareDrawerItems() {
//
//		if (!mManager.isLoggedIn() )
//		{
//		drawerItems.clear(); 
//		
//		drawerItems.add(getString(R.string.principal));
//		drawerItems.add(getString(R.string.share_app));
//		drawerItems.add(getString(R.string.login));
//		
//		adapter.notifyDataSetChanged();
//		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//				switch (position) {
//				case 0:
//					mDrawerLayout.closeDrawers();
//
//					break;
//				case 1:
//					
//					shareApp("بادر بتحميل تطبيق جنائز و دعوة http://goo.gl/Hvt1jT");
//					
//					break;
//				case 2:
//					mDrawerLayout.closeDrawers();
//					goToLoginFragment();
//					
//					break;
//				default:
//					break;
//				}
//				
//			}
//		});
//		
//		
//		
//		} else
//		{
//			drawerItems.clear(); 
//			
//			drawerItems.add(getString(R.string.principal));
//			drawerItems.add(getString(R.string.share_app));
//			drawerItems.add(getString(R.string.add_janaza));
//			drawerItems.add(getString(R.string.add_mouhadhra));
//			drawerItems.add(getString(R.string.quit));
//			
//			adapter.notifyDataSetChanged();
//			
//			
//			listView.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//
//					switch (position) {
//					case 0:
//						mDrawerLayout.closeDrawers();
//
//						break;
//					case 1:
//						
//						shareApp("بادر بتحميل تطبيق جنائز و دعوة http://goo.gl/Hvt1jT");
//						
//						break;
//					case 2:
//						
//						goToAddJanazaFragment();
//						mDrawerLayout.closeDrawers();
//						break;
//						
//					case 3:
//						goToAddMouhadharaFragment();
//						mDrawerLayout.closeDrawers();
//						break;
//					case 4:
//
//						mManager.setLoggedIn(false);
//						mManager.setUid(null);
//						
//						Prefs.initPrefs(IndexActivity.this);
//						Prefs.putString("uid", "");
//						
//						mDrawerLayout.closeDrawers();
//						prepareDrawerItems();
//						
//						break;	
//					default:
//						break;
//					}
//					
//				}
//			});
//			
//		}
//		
//	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initData();
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
			case R.id.about:
				goToFragment(ABOUT_FRAGMENT, true);
				break;

			case R.id.btn_enter:
				if(mManager.isLoggedIn())
					goToFragment(ADMIN_FRAGMENT, true);
				else
					goToFragment(LOGIN_FRAGMENT, true);

				break;

			case R.id.btn_suggestions:
				goToFragment(ADD_I9TIRAH_FRAGMENT, true);
				break;

			case R.id.medina_choice:
				goToFragment(PLACES_FRAGMENT, true);
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

	private Fragment getFragmentByTag(String tag){
		if(tag.equals(ABOUT_FRAGMENT))
			return new AboutFragment();
		else if(tag.equals(LOGIN_FRAGMENT))
			return new LoginFragment();
		else if(tag.equals(ADD_JANEZA_FRAGMENT))
			return new AddJanazaFragment();
		else if(tag.equals(ADD_MOHADHRA_FRAGMENT))
			return new AddMouhadhraFragment();
		else if(tag.equals(SETTING_FRAGMENT))
			return new SettingsFragment();
		else if(tag.equals(ADMIN_FRAGMENT))
			return new AdminMenuFragment();
		else if(tag.equals(ADD_I9TIRAH_FRAGMENT))
			return new AddMo9tarahatFragment();
		else if(tag.equals(PLACES_FRAGMENT))
			return new PlacesFragment();
		else 
			return null;
			
	}
	
	public void goToFragment(String tag, boolean withCard){

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if(withCard)
			transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);
//		else
//			transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);


		fragment = getFragmentByTag(tag);

		transaction.replace(R.id.fragment_view, fragment, tag);
		transaction.addToBackStack(tag);

		transaction.commit();
		
		currentTAG = tag;

		setEnableState(false);

	}

	public void onPlaceSelected(Place place){

		changePushPlace(place.getId());
		mManager.setSelectedPlace(place);
		txv_place.setText(place.getTitle());
		onBackPressed();
		
		initData();
	}

	private void setEnableState(boolean enabled){
		jana2ez.setEnabled(enabled);
		dourouss.setEnabled(enabled);
		medina_choice.setEnabled(enabled);
		btn_enter.setEnabled(enabled);
		btn_suggestions.setEnabled(enabled);
		about.setEnabled(enabled);
	}

	@Override
	public void onBackPressed() {

		if(fragment != null){
			if(currentTAG.equals(ADD_JANEZA_FRAGMENT) || currentTAG.equals(ADD_MOHADHRA_FRAGMENT))
				goToFragment(ADMIN_FRAGMENT, false);
			else{

				getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				setEnableState(true);
				fragment = null;

//				prepareDrawerItems();
			}
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
				mManager.updateDeviceToken("" + registrationId);
			}

		});

	}

	protected void resetGcm(){

		gcmManager = null ;
		initGCM();

	}
	
	
	public void initData(){
		
		new AsyncTask<Void, Void, Boolean>() {
			ArrayList<Mosque2> mosques = new ArrayList<Mosque2>();
			ArrayList<Da3wa> da3awi = new ArrayList<Da3wa>();
			
			@Override
			protected void onPreExecute() {
				txv_janaez_total.setVisibility(View.GONE);
				txv_da3awi_total.setVisibility(View.GONE);
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				if(mManager.getSelectedPlace() != null)
					if(mManager.getSelectedPlace().getId() != -1){
						mosques.addAll(mManager.getMosques2ByPlace(mManager.getSelectedPlace().getId()));
						da3awi.addAll(mManager.getAllDa3awi());
						return true;
					}
				return false;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				
				if(result){
					
					int janaezNb = 0;
					for(Mosque2 m : mosques){
						janaezNb += m.getCount();
					}
					
					if(janaezNb > 0){
						txv_janaez_total.setVisibility(View.VISIBLE);
						txv_janaez_total.setText("" + janaezNb);
					}
					
					if(da3awi.size() > 0){
						txv_da3awi_total.setVisibility(View.VISIBLE);
						txv_da3awi_total.setText("" + da3awi.size());
					}
				}
			}
		}.execute();

	}

	private void changePushPlace(final int placeId){
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				if(Utils.isOnline(IndexActivity.this))
					return mManager.changePuchPlace(mManager.getDeviceToken(), placeId);
				
				return false;
			}
			
			protected void onPostExecute(Boolean result) {
				if(result)
					Log.i(TAG, ">>>>> Place push changed successfully");
				else
					Log.i(TAG, ">>>>> Place push not changed");
			};
		}.execute();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mManager.setGcmDispatcher(null);
	}

	@Override
	public void onNewNotificationReceived() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				initData();				
			}
		});
	}

}
