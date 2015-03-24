package com.janaezwadaawa;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.janaezwadaawa.SearchDialog.EditNameDialogListener;
import com.janaezwadaawa.adapters.ElementsListAdapter;
import com.janaezwadaawa.adapters.IMenuListener;
import com.janaezwadaawa.entity.DrawerItem;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class MainActivity extends FragmentActivity implements IMenuListener, OnTouchListener, EditNameDialogListener{

	public static final String JANEZA_PLACES_FRAGMENT = "janeza_places_fragment";
	public static final String JANEZA_MOSQUES_FRAGMENT = "janeza_mosques_fragment";
	public static final String JANEZA_SALAT_FRAGMENT = "janeza_salat_fragment";
	public static final String MOSQUES_FRAGMENT = "mosques_fragment";
	public static final String JANAEZ_FRAGMENT = "janaez_fragment";
	public static final String DA3AWI_FRAGMENT = "da3awi_fragment";
	public static final String SETTINGS_FRAGMENT = "settings_fragment";
	public static final String DA3WA_DETAILS_FRAGMENT = "da3wa_details_fragment";

	public static final String 	DEFAULT_FRAG_POSITION 	= "default_frag_position";
	public static final String 	SELECTED_PLACE 			= "selected_place";

	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerLinear;
	private ActionBarDrawerToggle mDrawerToggle;
	private RelativeLayout moving_layout;
	ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
	private ListView listView;
	private ElementsListAdapter adapter;


	private Button btn_menu, btn_search;
	private ImageView header, img_header;


	public static final int MESSAGE_START = 1;
	private static final String TAG = null;
	private int lastPosition = 0, selected_placeID = -1;
	private String lastText = "";
	private boolean isFirstStart = true;

	private Fragment fragment, fragment1;
	private String currentFragment;

	private boolean isBackEnabled = false;
	private JDManager mManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mManager = JDManager.getInstance(this);
		JDFonts.Init(this);

		header = (ImageView) findViewById(R.id.header);
		img_header = (ImageView) findViewById(R.id.img_header);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLinear = (LinearLayout) findViewById(R.id.drawer_linear);
		moving_layout = (RelativeLayout) findViewById(R.id.moving_layout);
		listView = (ListView) findViewById(R.id.listView);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 

				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

			public void onDrawerClosed(View view) {
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				moving_layout.setTranslationX(- slideOffset * drawerView.getWidth());
				mDrawerLayout.bringChildToFront(drawerView);
				mDrawerLayout.requestLayout();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		adapter = new ElementsListAdapter(this, drawerItems);
		listView.setAdapter(adapter);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		img_header.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, IndexActivity.class));
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
				finish();
			}
		});

		//		MenuCustomAdapter adapter = new MenuCustomAdapter(this, getResources
		//
		//				().obtainTypedArray(R.array.menu_drawables));
		//
		//
		//		mDrawerList.setAdapter(adapter);
		//		mDrawerList.setDivider(null);
		//
		//		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		//		mainView = (RelativeLayout) findViewById(R.id.content_frame);
		//		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
		//
		//				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
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
		//				mainView.setTranslationX(- slideOffset * drawerView.getWidth());
		//				mDrawerLayout.bringChildToFront(drawerView);
		//				mDrawerLayout.requestLayout();
		//			}
		//		};
		//		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//				if (savedInstanceState == null) {
		//					selectItem(1);
		//				}

		prepareDrawerItems();

		btn_menu = (Button) findViewById(R.id.menu);
//		btn_menu.setOnTouchListener(this);
		btn_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(currentFragment.equals(JANEZA_PLACES_FRAGMENT) || currentFragment.equals(DA3AWI_FRAGMENT) || currentFragment.equals(SETTINGS_FRAGMENT)){
					if(!mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
						mDrawerLayout.openDrawer(Gravity.RIGHT);
					else
						mDrawerLayout.closeDrawer(Gravity.RIGHT);		
				}
				else
					onBackPressed();
			}
		});

		btn_search = (Button) findViewById(R.id.search);
		btn_search.setOnTouchListener(this);


		lastPosition = getIntent().getExtras().getInt(DEFAULT_FRAG_POSITION);
		//		selected_placeID = getIntent().getExtras().getInt(SELECTED_PLACE);
	}


	private void prepareDrawerItems() {

//		if (!mManager.isLoggedIn() )
//		{
			drawerItems.clear(); 

			drawerItems.add(new DrawerItem(getString(R.string.drawer_janaez) ,R.drawable.ic_drawer_janaez));
			drawerItems.add(new DrawerItem(getString(R.string.drawer_mouhadhrat),R.drawable.ic_drawer_mouhadhrat));
			drawerItems.add(new DrawerItem(getString(R.string.drawer_share) ,R.drawable.ic_drawer_share));
			drawerItems.add(new DrawerItem(getString(R.string.drawer_settings) ,R.drawable.ic_drawer_settings));
			
			adapter.notifyDataSetChanged();

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					mDrawerLayout.closeDrawers();
					
					if (position == 2) {
						shareApp("بادر بتحميل تطبيق جنائز و دعوة http://goo.gl/Hvt1jT");
					}else {
						selectItem(position);
					}
					
//					switch (position) {
//					case 0:
//
//						fragment = new JanezaPlacesFragment();
//						btn_search.setVisibility(View.GONE);
//						currentFragment = JANEZA_PLACES_FRAGMENT;
//						header.setBackgroundResource(R.drawable.jana2ez);
//						
//						break;
//					case 1:
//
//						fragment = new Da3waFragment();
//						btn_search.setVisibility(View.VISIBLE);
//						currentFragment = DA3AWI_FRAGMENT;
//						header.setBackgroundResource(R.drawable.dawrat_header);
//
//						break;
//					case 2:
//											
//						shareApp("بادر بتحميل تطبيق جنائز و دعوة http://goo.gl/Hvt1jT");
//						
//						break;
//						
//					case 3:
//						
//						fragment = new SettingsFragment();
//						btn_search.setVisibility(View.VISIBLE);
//						currentFragment = SETTINGS_FRAGMENT;
//						header.setBackgroundResource(R.drawable.settings_header);
//						
//						break;	
//					
//					default:
//						break;
//					}

				}
			});



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
//						//						shareApp("بادر بتحميل تطبيق جنائز و دعوة http://goo.gl/Hvt1jT");
//
//						break;
//					case 2:
//
//						//						goToAddJanazaFragment();
//						//						mDrawerLayout.closeDrawers();
//						break;
//
//					case 3:
//						//						goToAddMouhadharaFragment();
//						//						mDrawerLayout.closeDrawers();
//						break;
//					case 4:
//
//						//						mManager.setLoggedIn(false);
//						//						mManager.setUid(null);
//						//						
//						//						Prefs.initPrefs(IndexActivity.this);
//						//						Prefs.putString("uid", "");
//						//						
//						//						mDrawerLayout.closeDrawers();
//						//						prepareDrawerItems();
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

	}

	@Override
	protected void onResume() {
		super.onResume();

	}


	@Override
	protected void onStart() {
		super.onStart();

		if(isFirstStart){
			selectItem(lastPosition);

			isFirstStart = false;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	//	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	//		@Override
	//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	//		{
	//			selectItem(position);
	//		}
	//	}


	private void selectItem(int position) {

		btn_menu.setBackgroundResource(R.drawable.menu);

		lastPosition = position;
		boolean shouldSwitch = true;

		//		Bundle args = null;
		// update the main content by replacing fragments

		switch (position) {
		case 0:
			fragment = new JanezaPlacesFragment();
			btn_search.setVisibility(View.GONE);
			currentFragment = JANEZA_PLACES_FRAGMENT;
			header.setBackgroundResource(R.drawable.jana2ez);
			break;
		case 1:
			fragment = new Da3waFragment();
			btn_search.setVisibility(View.VISIBLE);
			currentFragment = DA3AWI_FRAGMENT;
			header.setBackgroundResource(R.drawable.dawrat_header);

			break;
			
		case 3:
			fragment = new SettingsFragment();
			btn_search.setVisibility(View.GONE);
			currentFragment = SETTINGS_FRAGMENT;
			header.setBackgroundResource(R.drawable.settings_header);

			break;	
		
		default:
			shouldSwitch = false;
			break;

		}

		//		if(args != null)
		//			fragment.setArguments(args);

		if(shouldSwitch)
			switchTab(fragment, false);

		// update selected item and title, then close the drawer
		//		mDrawerList.setItemChecked(position, true);
		//		mDrawerLayout.closeDrawer(mDrawerList);

	}


	private void switchTab(Fragment tab, boolean withAnimation) {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.content_frame);

		final FragmentTransaction ft = fm.beginTransaction();
		if(withAnimation)
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);

		if (fragment == null) {
			ft.add(R.id.content_frame, tab);

		} else {
			ft.replace(R.id.content_frame, tab);
		}

		ft.commit();

	}

	@Override
	public void onMenuItemClicked(int position) {
		selectItem(position);
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Button view = (Button) v;
			view.getBackground().setColorFilter(0x77000000, 

					PorterDuff.Mode.SRC_ATOP);
			v.invalidate();
			break;
		}
		case MotionEvent.ACTION_UP: {

			Button view = (Button) v;
			view.getBackground().clearColorFilter();
			view.invalidate();

			switch (v.getId()) {
			case R.id.menu:
				//					if(isBackEnabled)
				//					{		
				onBackPressed();
				//					}
				//					else{
				//						if(!mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
				//							mDrawerLayout.openDrawer(Gravity.RIGHT);
				//						else
				//							mDrawerLayout.closeDrawer(Gravity.RIGHT);		
				//					}
				break;
			case R.id.search:
				//show search dialog;
				showSearchDialog();
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

	private void showSearchDialog() {
//		FragmentManager fm = getFragmentManager();
		SearchDialog searchDialog = new SearchDialog(lastText);
		searchDialog.show(getSupportFragmentManager(), "fragment_search_keyword");
	}

	@Override
	public void onFinishEditDialog(String inputText) {

		if(mManager.getSearchListener() != null)
		{
			mManager.getSearchListener().onSearchBykeyword(inputText);
		}

		//			lastText = inputText;
		//
		//			// update the main content by replacing fragments
		//			fragment = new AhadithFragment();
		//			Bundle args = new Bundle();
		//			args.putInt(AhadithFragment.ARG_AHADITH, AhadithFragment.TYPE_AHADITH_KEYWORD_ID);
		//			args.putInt(AhadithFragment.ARG_AHADITH_SEARCH, lastPosition);
		//			args.putString(AhadithFragment.ARG_AHADITH_KEYWORD_TEXT, inputText);
		//			args.putInt(AhadithFragment.ARG_BAB_ID, lastBabId);
		//			fragment.setArguments(args);
		//
		//			FragmentManager fragmentManager = getSupportFragmentManager();
		//			FragmentTransaction ft = fragmentManager.beginTransaction();
		//
		//			ft.replace(R.id.content_frame, fragment);
		//			scaled = false ;
		//			ft.commit();

	}

	private Fragment getFragmentInstance(String tag){
		if(tag.equals(JANEZA_PLACES_FRAGMENT))
			return new JanezaPlacesFragment();
		else if(tag.equals(JANEZA_MOSQUES_FRAGMENT))
			return new JanezaMosquesFragment();
		else if(tag.equals(JANEZA_SALAT_FRAGMENT))
			return new JanezaSalatFragment();
		else if(tag.equals(DA3WA_DETAILS_FRAGMENT))
			return new Da3waDetailFragment();
		else if(tag.equals(DA3AWI_FRAGMENT))
			return new Da3waFragment();
		else 
			return null;
	}

	public void goToFragment(String fragmentTAG){

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		transaction.setCustomAnimations(R.animator.left_in, R.animator.left_out, R.animator.right_in, R.animator.right_out);

//		fragment1 = (Fragment) fragmentManager.findFragmentByTag(fragmentTAG);
//
//		if(fragment1 == null){
			fragment1 = getFragmentInstance(fragmentTAG);

			transaction.replace(R.id.content_frame, fragment1, fragmentTAG);
			transaction.addToBackStack(fragmentTAG);
//		}else{
//			transaction.attach(fragment1);
//		}

		transaction.commit();

//		header.setBackgroundResource(R.drawable.jana2ez);
		btn_menu.setBackgroundResource(R.drawable.back_list);
		currentFragment = fragmentTAG;

		isBackEnabled = true;

	}

	@Override
	public void onBackPressed() {

		if(currentFragment.equals(DA3WA_DETAILS_FRAGMENT))
		{
//			currentFragment = DA3AWI_FRAGMENT;
			
			goToFragment(DA3AWI_FRAGMENT);
			btn_menu.setBackgroundResource(R.drawable.menu);
			isBackEnabled = false;
//			super.onBackPressed();
		}
		else if(currentFragment.equals(JANEZA_MOSQUES_FRAGMENT)){
//			currentFragment = JANEZA_PLACES_FRAGMENT;

			goToFragment(JANEZA_PLACES_FRAGMENT);
			btn_menu.setBackgroundResource(R.drawable.menu);
			isBackEnabled = false;
//			super.onBackPressed();
		}
		else if(currentFragment.equals(JANEZA_SALAT_FRAGMENT)){
//			currentFragment = JANEZA_MOSQUES_FRAGMENT;
			
			goToFragment(JANEZA_MOSQUES_FRAGMENT);
			btn_menu.setBackgroundResource(R.drawable.back_list);
			isBackEnabled = true;
//			super.onBackPressed();
		}else{
			startActivity(new Intent(MainActivity.this, IndexActivity.class));
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			finish();
		}

	}
	
	private void shareApp(String text){

		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));

	}

}