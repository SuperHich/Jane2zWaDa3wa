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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.gcm.GcmManager;
import com.janaezwadaawa.gcm.GcmManager.GcmListener;
import com.janaezwadaawa.utils.JDFonts;
import com.pixplicity.easyprefs.library.Prefs;


public class LoginFragment extends Fragment {

	private Fragment fragment;
	private Switch notif_switch ;
	private JDManager mManager;
	private Button btn_login;
	private EditText edit_pwd;
	private EditText edit_login;


	public LoginFragment() {
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

		View rootView = inflater.inflate(R.layout.fragment_login, container, false);

		btn_login 	= (Button) rootView.findViewById(R.id.btn_login);
		edit_login 	= (EditText) rootView.findViewById(R.id.edit_login);
		edit_pwd	= (EditText) rootView.findViewById(R.id.edit_pwd);

		btn_login.setTypeface(JDFonts.getBDRFont());
		edit_login.setTypeface(JDFonts.getBDRFont());
		edit_pwd.setTypeface(JDFonts.getBDRFont());


		btn_login.setOnTouchListener(new OnTouchListener() {

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

					
					final String loginText = edit_login.getText().toString();
					final String pwdText = edit_pwd.getText().toString();
					
					if (loginText.equalsIgnoreCase("") ||pwdText.equalsIgnoreCase("")) {
						
						Toast.makeText(getActivity(), getString(R.string.login_empty), Toast.LENGTH_SHORT).show();
					}
					else {
					
					new AsyncTask<Void, Void, String>() {

						
						
						@Override
						protected String doInBackground(Void... params) {

							String response = mManager.login(loginText, pwdText);
							
							return response;
						}
						
						protected void onPostExecute(String result) {
							
							if (result != null ) {
								
								mManager.setLoggedIn(true);
								mManager.setUid(result);
								
								Prefs.initPrefs(getActivity());
								Prefs.putString("uid", result);
								
								getActivity().onBackPressed();
								
							}
							
						};
					}.execute();
					
					
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
