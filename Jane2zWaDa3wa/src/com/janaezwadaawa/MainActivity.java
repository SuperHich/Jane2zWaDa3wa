package com.janaezwadaawa;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.janaezwadaawa.AhadithSearchDialog.EditNameDialogListener;
import com.janaezwadaawa.adapters.IMenuListener;
import com.janaezwadaawa.adapters.MenuCustomAdapter;

public class MainActivity extends FragmentActivity implements IMenuListener, OnTouchListener, EditNameDialogListener{

	public static final String COMMENTS_FRAGMENT = "comments_fragment";
	public static final String ADD_COMMENT_FRAGMENT = "add_comment_fragment";
	public static final String EDIT_COMMENT_FRAGMENT = "edit_comment_fragment";
	public static final String FAVOURITE_FRAGMENT = "favourite_fragment";
	public static final String BOOKS_FRAGMENT = "books_fragment";
	public static final String ABWAB_FRAGMENT = "abwab_fragment";
	public static final String AHADITH_FRAGMENT = "ahadith_fragment";
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private Button btn_menu, btn_search;

	private ActionBarDrawerToggle mDrawerToggle;
	RelativeLayout mainView ;

	public static final int MESSAGE_START = 1;
	private int lastPosition = 0;
	private String lastText = "";
	private boolean isFirstStart = true;
	
	private Fragment fragment;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.right_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		MenuCustomAdapter adapter = new MenuCustomAdapter(this, getResources

				().obtainTypedArray(R.array.menu_drawables));


		mDrawerList.setAdapter(adapter);
		mDrawerList.setDivider(null);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mainView = (RelativeLayout) findViewById(R.id.content_frame);

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
				mainView.setTranslationX(- slideOffset * drawerView.getWidth());
				mDrawerLayout.bringChildToFront(drawerView);
				mDrawerLayout.requestLayout();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		//		if (savedInstanceState == null) {
		//			selectItem(1);
		//		}

		btn_menu = (Button) findViewById(R.id.menu);
		btn_menu.setOnTouchListener(this);
		//		btn_menu.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View arg0) {
		//				if(!mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
		//					mDrawerLayout.openDrawer(Gravity.RIGHT);
		//				else
		//					mDrawerLayout.closeDrawer(Gravity.RIGHT);		
		//			}
		//		});

		btn_search = (Button) findViewById(R.id.search);
		btn_search.setOnTouchListener(this);

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

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			selectItem(position);
		}
	}


	private void selectItem(int position) {
		
		btn_menu.setBackgroundResource(R.drawable.menu);

		lastPosition = position;
		
		
		Bundle args = null;
		// update the main content by replacing fragments
		
		switch (position) {
		case 0:
			fragment = new MosqueFragment();
//			args = new Bundle();
//			args.putInt(AhadithFragment.ARG_AHADITH, position);
			btn_search.setVisibility(View.VISIBLE);
			break;
		case 1:
			fragment = new Da3waFragment();
//			args = new Bundle();
//			args.putInt(AhadithFragment.ARG_AHADITH, position);
			btn_search.setVisibility(View.VISIBLE);
			break;
//		case 2:
//			fragment = new AhadithFragment();
//			args = new Bundle();
//			args.putInt(AhadithFragment.ARG_AHADITH, position);
//			btn_search.setVisibility(View.VISIBLE);
//			break;
//		case 3:
//			fragment = new AbwabFragment();
//			currentFragment = ABWAB_FRAGMENT;
//			btn_search.setVisibility(View.GONE);
//			break;
//		case 4:
//			fragment = new BooksFragment();
//			currentFragment = BOOKS_FRAGMENT;
//			btn_search.setVisibility(View.GONE);
//			break;
//		case 5:
//			fragment = new AboutFragment();
//			btn_search.setVisibility(View.GONE);
//			break;
//		case 6:
//			fragment = new AboutAppFragment();
//			btn_search.setVisibility(View.GONE);
//			break;
		default:
			fragment = new MosqueFragment();
			break;

		}
//		
//		
		if(args != null)
			fragment.setArguments(args);

//		if(position != 0)
			switchTab(fragment, false);

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);

	}


	private void switchTab(Fragment tab, boolean withAnimation) {
		FragmentManager fm = getSupportFragmentManager();
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
					if(!mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
						mDrawerLayout.openDrawer(Gravity.RIGHT);
					else
						mDrawerLayout.closeDrawer(Gravity.RIGHT);		
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
			FragmentManager fm = getSupportFragmentManager();
			AhadithSearchDialog searchDialog = new AhadithSearchDialog(lastText);
			searchDialog.show(fm, "fragment_search_keyword");
		}

		@Override
		public void onFinishEditDialog(String inputText) {

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

		
		public void goToJanaezFragment(){
			
			fragment = new JanaezFragment();

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.setCustomAnimations(R.anim.right_in, R.anim.right_out, R.anim.left_in, R.anim.left_out);

			ft.replace(R.id.content_frame, fragment);
			ft.commit();
		}
		
		@Override
		public void onBackPressed() {
			super.onBackPressed();
		}
		
}