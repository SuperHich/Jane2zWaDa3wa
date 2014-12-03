package com.janaezwadaawa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;


public class SettingsFragment extends Fragment {


	private static final String PLACES_FRAGMENT = null;

	private Fragment fragment;
	private Button  medina_choice ;
	private Switch notif_switch ;
	private JDManager mManager;
	private TextView title, notif_text;

	public SettingsFragment() {
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

		View rootView = inflater.inflate(R.layout.settings, container, false);

		medina_choice 	= (Button) rootView.findViewById(R.id.medina_choice);
		title 	= (TextView) rootView.findViewById(R.id.title);
		notif_text	= (TextView) rootView.findViewById(R.id.notif_text);
		notif_switch 	= (Switch) rootView.findViewById(R.id.notif_switch);

		title.setTypeface(JDFonts.getBDRFont());
		notif_text.setTypeface(JDFonts.getBDRFont());
		notif_switch.setTypeface(JDFonts.getBDRFont());

		notif_switch.setChecked(mManager.isNotificationEnabled());
		//set the switch to ON 
		//	notif_switch.setChecked(true);

		notif_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if(isChecked){
					//Switch is currently ON
					mManager.updateDeviceToken(mManager.getDeviceToken());
				}else{
					//Switch is currently OFF"
				}
				
				mManager.setNotificationSettings(isChecked);

			}
		});



		medina_choice.setOnTouchListener(new OnTouchListener() {

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

					goToPlacesFragment();

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

	@SuppressLint("NewApi")
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



}
