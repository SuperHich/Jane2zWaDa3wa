package com.janaezwadaawa;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.Utils;


public class AddMo9tarahatFragment extends Fragment {

	private JDManager mManager;

	private Button btn_add;
	private EditText txv_address, txv_mobile, txv_body;
	private TextView lbl_body, lbl_address, lbl_mobile;

	private TextView top_header;

	private Button btn_back;

	public AddMo9tarahatFragment() {
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
		Utils.hideKeyBoardFromWindow(getActivity(), txv_body);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mManager = JDManager.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_add_i9tirah, container, false);

		btn_add 	= (Button) rootView.findViewById(R.id.btn_add);
		txv_address	= (EditText) rootView.findViewById(R.id.txv_address);
		txv_body	= (EditText) rootView.findViewById(R.id.txv_body);
		txv_mobile 	= (EditText) rootView.findViewById(R.id.txv_mobile);
		
		lbl_mobile	= (TextView) rootView.findViewById(R.id.lbl_mobile);
		lbl_body	= (TextView) rootView.findViewById(R.id.lbl_body);
		lbl_address	= (TextView) rootView.findViewById(R.id.lbl_address);
		
		top_header	= (TextView) rootView.findViewById(R.id.top_header);
		top_header.setTypeface(JDFonts.getBDRFont());

		lbl_address.setTypeface(JDFonts.getBDRFont());
		lbl_body.setTypeface(JDFonts.getBDRFont());
		lbl_mobile.setTypeface(JDFonts.getBDRFont());
		txv_body.setTypeface(JDFonts.getBDRFont());
		txv_address.setTypeface(JDFonts.getBDRFont());
		btn_add.setTypeface(JDFonts.getBDRFont());
		
		btn_back = (Button) rootView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String addressTxt = txv_address.getText().toString();
				String bodyTxt = txv_body.getText().toString();
				String mobileTxt = txv_body.getText().toString();

				if (addressTxt.equalsIgnoreCase("") ||bodyTxt.equalsIgnoreCase("")) {

					Toast.makeText(getActivity(), getString(R.string.empty_add_janaza_mouhadhra), Toast.LENGTH_SHORT).show();
				}
				else {

					String body = bodyTxt + "\n\n" + mobileTxt;
					Utils.shareWithMail(getActivity(), "info@gheras.org.sa", addressTxt, body, getString(R.string.send));
					
				}
			}});

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


	}

}
