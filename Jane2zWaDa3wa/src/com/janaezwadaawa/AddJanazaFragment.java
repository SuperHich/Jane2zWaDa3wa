package com.janaezwadaawa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.gcm.GcmManager;
import com.janaezwadaawa.gcm.GcmManager.GcmListener;
import com.janaezwadaawa.utils.JDFonts;
import com.pixplicity.easyprefs.library.Prefs;


public class AddJanazaFragment extends Fragment {

	private Fragment fragment;
	
	private JDManager mManager;
	
	private Button btn_add;
	private EditText txv_address, txv_body;
	private Spinner spinner_genre, spinner_wakt_salat, spinner_salat, spinner_jamaa , spinner_mantaka;
	private TextView lbl_genre, lbl_wakt_salat, lbl_salat, lbl_jamaa, lbl_mantaka, lbl_body, lbl_address, title ;


	public AddJanazaFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onDetach() {
		super.onDetach();

	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mManager = JDManager.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_add_janaza, container, false);

//		private Button btn_add;
//		private EditText txv_address, txv_body;
//		private Spinner spinner_genre, spinner_wakt_salat, spinner_salat, spinner_jamaa , spinner_mantaka;
//		private TextView lbl_genre, lbl_wakt_salat, lbl_salat, lbl_jamaa, lbl_mantaka, lbl_body, lbl_address ;
		
		btn_add 	= (Button) rootView.findViewById(R.id.btn_add);
		txv_address 	= (EditText) rootView.findViewById(R.id.txv_address);
		txv_body	= (EditText) rootView.findViewById(R.id.txv_body);

		title	= (TextView) rootView.findViewById(R.id.title);
		lbl_genre	= (TextView) rootView.findViewById(R.id.lbl_genre);
		lbl_wakt_salat	= (TextView) rootView.findViewById(R.id.lbl_wakt_salat);
		lbl_salat	= (TextView) rootView.findViewById(R.id.lbl_salat);
		lbl_jamaa	= (TextView) rootView.findViewById(R.id.lbl_jamaa);
		lbl_mantaka	= (TextView) rootView.findViewById(R.id.lbl_mantaka);
		lbl_body	= (TextView) rootView.findViewById(R.id.lbl_body);
		lbl_address	= (TextView) rootView.findViewById(R.id.lbl_address);
		
		spinner_genre	= (Spinner) rootView.findViewById(R.id.spinner_genre);
		spinner_wakt_salat	= (Spinner) rootView.findViewById(R.id.spinner_wakt_salat);
		spinner_salat	= (Spinner) rootView.findViewById(R.id.spinner_salat);
		spinner_jamaa	= (Spinner) rootView.findViewById(R.id.spinner_jamaa);
		spinner_mantaka	= (Spinner) rootView.findViewById(R.id.spinner_mantaka);
		
		lbl_address.setTypeface(JDFonts.getBDRFont());
		lbl_body.setTypeface(JDFonts.getBDRFont());
		lbl_mantaka.setTypeface(JDFonts.getBDRFont());
		lbl_jamaa.setTypeface(JDFonts.getBDRFont());
		lbl_salat.setTypeface(JDFonts.getBDRFont());
		lbl_wakt_salat.setTypeface(JDFonts.getBDRFont());
		lbl_genre.setTypeface(JDFonts.getBDRFont());
		txv_body.setTypeface(JDFonts.getBDRFont());
		txv_address.setTypeface(JDFonts.getBDRFont());
		btn_add.setTypeface(JDFonts.getBDRFont());
		title.setTypeface(JDFonts.getBDRFont());

		btn_add.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					Button view = (Button) v;
					view.getBackground().setColorFilter(0x5545c2ce, PorterDuff.Mode.SRC_ATOP);
					v.invalidate();
					break;
				}
				case MotionEvent.ACTION_UP: {

					
//					final String loginText = edit_login.getText().toString();
//					final String pwdText = edit_pwd.getText().toString();
					
//					if (loginText.equalsIgnoreCase("") ||pwdText.equalsIgnoreCase("")) {
//						
//						Toast.makeText(getActivity(), getString(R.string.login_empty), Toast.LENGTH_SHORT).show();
//					}
//					else {
//					
//					new AsyncTask<Void, Void, String>() {
//
//						
//						
//						@Override
//						protected String doInBackground(Void... params) {
//
//							String response = mManager.login(loginText, pwdText);
//							
//							return response;
//						}
//						
//						protected void onPostExecute(String result) {
//							
//							if (result != null ) {
//								
//								mManager.setLoggedIn(true);
//								mManager.setUid(result);
//								
//								Prefs.initPrefs(getActivity());
//								Prefs.putString("uid", result);
//								
//								getActivity().onBackPressed();
//								
//							}
//							
//						};
//					}.execute();
//					
//					
//					}

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
		});


		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


	}

//	@SuppressLint("NewApi")
//	public void goToPlacesFragment(){
//
//		FragmentManager fragmentManager = getFragmentManager();
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out);
//		transaction.setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out, R.animator.card_flip_right_in, R.animator.card_flip_right_out);
//
//		fragment = new PlacesFragment();
//
//		transaction.replace(R.id.fragment_view, fragment, PLACES_FRAGMENT);
//		transaction.addToBackStack(null);
//
//		transaction.commit();
//
//
//	}
//	
	



}
