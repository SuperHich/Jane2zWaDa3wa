package com.janaezwadaawa;

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
	private static final String LOGIN_FRAGMENT = "login";
	private TextView txv_place , txv_date ;
	private Button  about , medina_choice, btn_suggestions, btn_enter;
	private Button dourouss, jana2ez ;

//	settings,share , 
	
	private int gDay, gMonth, gYear,
	hDay, hMonth, hYear ;

	private Fragment fragment;

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
//			case R.id.settings:
//				goToSettingsFragment();
//				break;
			case R.id.about:
				goToAboutFragment();
				break;
//			case R.id.share:
//				shareApp("بادر بتحميل تطبيق جنائز و دعوة http://goo.gl/Hvt1jT");
//				break;
				
				case R.id.btn_enter:

					goToLoginFragment();
					
				break;
				
				case R.id.btn_suggestions:

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

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);

		fragment = new PlacesFragment();

		transaction.replace(R.id.fragment_view, fragment, PLACES_FRAGMENT);
		transaction.addToBackStack(null);

		transaction.commit();


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
	
	public void goToLoginFragment(){

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);


		fragment = new LoginFragment();

		transaction.replace(R.id.fragment_view, fragment, LOGIN_FRAGMENT);
		transaction.addToBackStack(null);

		transaction.commit();

		setEnableState(false);

	}
	
	public void goToAddJanazaFragment(){

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);


		fragment = new AddJanazaFragment();

		transaction.replace(R.id.fragment_view, fragment, LOGIN_FRAGMENT);
		transaction.addToBackStack(null);

		transaction.commit();

		setEnableState(false);

	}
	
	public void goToAddMouhadharaFragment(){

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);


		fragment = new AddMouhadhraFragment();

		transaction.replace(R.id.fragment_view, fragment, LOGIN_FRAGMENT);
		transaction.addToBackStack(null);

		transaction.commit();

		setEnableState(false);

	}
	


//	public void goToSettingsFragment(){
//
//		FragmentManager fragmentManager = getFragmentManager();
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
//		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);
//
//
//		fragment = new SettingsFragment();
//
//		transaction.replace(R.id.fragment_view, fragment, PLACES_FRAGMENT);
//		transaction.addToBackStack(null);
//
//		transaction.commit();
//
//		setEnableState(false);
//
//	}

	public void onPlaceSelected(Place place){

		mManager.setSelectedPlace(place);
		txv_place.setText(place.getTitle());
		onBackPressed();
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
			getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			setEnableState(true);
			fragment = null;
			
//			prepareDrawerItems();
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
	

}
