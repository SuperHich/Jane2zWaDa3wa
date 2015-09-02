package com.janaezwadaawa;

import java.nio.charset.Charset;

import org.apache.http.Header;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.externals.LoopjRestClient;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pixplicity.easyprefs.library.Prefs;


public class LoginFragment extends Fragment {

	private Fragment fragment;
	private Switch notif_switch ;
	private JDManager mManager;
	private Button btn_login;
	private EditText edit_pwd;
	private EditText edit_login;
	private TextView lbl_login, lbl_pwd, top_header ;
	private Button btn_back;


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
	public void onPause() {
		super.onPause();
		
		Utils.hideKeyBoardFromWindow(getActivity(), edit_pwd);
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
		
		lbl_pwd		= (TextView) rootView.findViewById(R.id.lbl_pwd);
		lbl_login	= (TextView) rootView.findViewById(R.id.lbl_login);

		lbl_pwd.setTypeface(JDFonts.getBDRFont());
		lbl_login.setTypeface(JDFonts.getBDRFont());
		
		top_header	= (TextView) rootView.findViewById(R.id.top_header);
		top_header.setTypeface(JDFonts.getBDRFont());
		
		btn_login.setTypeface(JDFonts.getBDRFont());
		edit_login.setTypeface(JDFonts.getBDRFont());
		edit_pwd.setTypeface(JDFonts.getBDRFont());

		btn_back = (Button) rootView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

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

						private ProgressDialog loading;

						protected void onPreExecute() {
							loading = new ProgressDialog(getActivity());
							loading.setMessage(getString(R.string.please_wait));
							loading.setCancelable(false);
							loading.show();
						};
						
						@Override
						protected String doInBackground(Void... params) {
							if(!Utils.isOnline(getActivity()))
								return null;
							
							return mManager.login(loginText, pwdText);
						}
						
						protected void onPostExecute(String result) {
							loading.dismiss();
							
							if (result != null ) {
								
								if(result.equals("false"))
									Utils.showInfoPopup(getActivity(), null, getString(R.string.check_login_password));
								else{
									mManager.setLoggedIn(true);
									mManager.setUid(result);

									Prefs.initPrefs(getActivity());
									Prefs.putString("uid", result);

									//								getActivity().onBackPressed();
									((IndexActivity) getActivity()).goToFragment(IndexActivity.ADMIN_FRAGMENT, false);
								}
								
							}else
								Utils.showInfoPopup(getActivity(), null, getString(R.string.error_internet_connexion));
							
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

}
