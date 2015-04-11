package com.janaezwadaawa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.entity.SelectGender;
import com.janaezwadaawa.entity.SelectMosque;
import com.janaezwadaawa.entity.SelectSalat;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.Utils;


public class AddJanazaFragment extends Fragment {

	private JDManager mManager;

	private Button btn_add;
	private EditText txv_address, txv_body;
	private Spinner spinner_genre,  spinner_salat, spinner_jamaa , spinner_mantaka;
	private TextView lbl_genre, lbl_wakt_salat, lbl_salat, lbl_jamaa, lbl_mantaka, lbl_body, lbl_address,salat_time, salat_date ;

	private ArrayList<SelectGender> listGenres = new ArrayList<SelectGender>();
	private ArrayList<SelectSalat> listSalawat = new ArrayList<SelectSalat>();
	private ArrayList<SelectMosque> listJawamaa = new ArrayList<SelectMosque>();
	private ArrayList<Place> listManatek = new ArrayList<Place>();


	String date = "", time = "00:00" ;

	private TextView top_header;

	private Button btn_back, btn_preview;

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

		View rootView = inflater.inflate(R.layout.fragment_add_janaza, container, false);

		//		private Button btn_add;
		//		private EditText txv_address, txv_body;
		//		private Spinner spinner_genre, spinner_wakt_salat, spinner_salat, spinner_jamaa , spinner_mantaka;
		//		private TextView lbl_genre, lbl_wakt_salat, lbl_salat, lbl_jamaa, lbl_mantaka, lbl_body, lbl_address ;

		btn_add 	= (Button) rootView.findViewById(R.id.btn_add);
		txv_address 	= (EditText) rootView.findViewById(R.id.txv_address);
		txv_body	= (EditText) rootView.findViewById(R.id.txv_body);

		lbl_genre	= (TextView) rootView.findViewById(R.id.lbl_genre);
		lbl_wakt_salat	= (TextView) rootView.findViewById(R.id.lbl_wakt_salat);
		lbl_salat	= (TextView) rootView.findViewById(R.id.lbl_salat);
		lbl_jamaa	= (TextView) rootView.findViewById(R.id.lbl_jamaa);
		lbl_mantaka	= (TextView) rootView.findViewById(R.id.lbl_mantaka);
		lbl_body	= (TextView) rootView.findViewById(R.id.lbl_body);
		lbl_address	= (TextView) rootView.findViewById(R.id.lbl_address);

		salat_time	= (TextView) rootView.findViewById(R.id.wakt_salat_time);
		salat_date	= (TextView) rootView.findViewById(R.id.wakt_salat_date);

		spinner_genre	= (Spinner) rootView.findViewById(R.id.spinner_genre);
		spinner_salat	= (Spinner) rootView.findViewById(R.id.spinner_salat);
		spinner_jamaa	= (Spinner) rootView.findViewById(R.id.spinner_jamaa);
		spinner_mantaka	= (Spinner) rootView.findViewById(R.id.spinner_mantaka);
		
		top_header	= (TextView) rootView.findViewById(R.id.top_header);
		top_header.setTypeface(JDFonts.getBDRFont());
		
		btn_preview = (Button) rootView.findViewById(R.id.btn_preview);
		btn_preview.setTypeface(JDFonts.getBDRFont());
		
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
		
		btn_back = (Button) rootView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		initiateLists();

		
		lbl_body.setVisibility(View.GONE);
		txv_body.setVisibility(View.GONE);
		
		salat_time.setVisibility(View.INVISIBLE);
//		salat_time.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Calendar mcurrentTime = Calendar.getInstance();
//				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//				int minute = mcurrentTime.get(Calendar.MINUTE);
//
//				TimePickerDialog mTimePicker;
//
//				mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
//					@Override
//					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//
//						Calendar mCalendar = Calendar.getInstance();
//						mCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
//						mCalendar.set(Calendar.MINUTE, selectedMinute);
//						time = new SimpleDateFormat("HH:mm").format(mCalendar.getTime());
//
//						salat_time.setText( time);
//					}
//				}, hour, minute, true);//Yes 24 hour time
//
//				mTimePicker.setTitle("إختيار وقت الصلاة :");
//				mTimePicker.show();
//
//			}
//		});

		salat_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mcurrentTime = Calendar.getInstance();
				int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
				int month = mcurrentTime.get(Calendar.MONTH);
				int year = mcurrentTime.get(Calendar.YEAR);

				DatePickerDialog mDatePicker;

				mDatePicker = new DatePickerDialog(getActivity(), new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub

						Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						date = df.format(cal.getTime());

						salat_date.setText(date);
					}
				}, year, month, day);


				mDatePicker.setTitle("إختيار تاريخ الصلاة :");
				mDatePicker.show();


			}
		});


		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String addressTxt = txv_address.getText().toString();
				final String bodyTxt = txv_body.getText().toString();


				if (addressTxt.equalsIgnoreCase("") || date.equalsIgnoreCase("")) {

					Toast.makeText(getActivity(), getString(R.string.empty_add_janaza_mouhadhra), Toast.LENGTH_SHORT).show();
				}
				else {

					new AsyncTask<Void, Void, Boolean>() {

						ProgressDialog loading ;

						protected void onPreExecute() {

							loading = new ProgressDialog(getActivity());
							loading.setMessage(getString(R.string.please_wait));
							loading.setCancelable(false);
							loading.show();

						};

						@Override
						protected Boolean doInBackground(Void... params) {

							if(!Utils.isOnline(getActivity()))
								return null;

							int place = listManatek.get(spinner_mantaka.getSelectedItemPosition()).getId();
							int mosque = listJawamaa.get(spinner_jamaa.getSelectedItemPosition()).getId();
							int salat = listSalawat.get(spinner_salat.getSelectedItemPosition()).getId();
							int gender = listGenres.get(spinner_genre.getSelectedItemPosition()).getId();

							String salat_time = date + " "+ time ;

							return mManager.addJaneza(mManager.getUid(), addressTxt, bodyTxt, place, mosque, salat, salat_time, gender);

						}

						protected void onPostExecute(Boolean result) {

							loading.dismiss();
							
							if(result != null)
								if (result) {
									Toast.makeText(getActivity(), getString(R.string.success_add), Toast.LENGTH_SHORT).show();
									((IndexActivity)getActivity()).initData();
									getActivity().onBackPressed();
								}else
									Toast.makeText(getActivity(), getString(R.string.error_add), Toast.LENGTH_SHORT).show();
							else
								Utils.showInfoPopup(getActivity(), null, getString(R.string.error_internet_connexion));
						};
					}.execute();


				}
			}});

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


	}

	private void initiateLists() {

		listGenres = mManager.getSelectGenders();

		if (!mManager.getSelectMosques().isEmpty() && !mManager.getSelectSalats().isEmpty() && !mManager.getPlaces().isEmpty()) {

			//			Toast.makeText(getActivity(), getString(R.string.login_empty), Toast.LENGTH_SHORT).show();

			listSalawat = mManager.getSelectSalats();
//			listJawamaa = mManager.getSelectMosques();
			listManatek = mManager.getPlaces();

			initiateSpinners();

		}
		else {

			new AsyncTask<Void, Void, String>() {

				ProgressDialog loading ;

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

					listManatek = mManager.getAllPlaces();
					listSalawat = mManager.getAllSelectSalats();
//					listJawamaa = mManager.getAllSelectMosques();

					return "";
				}

				protected void onPostExecute(String result) {

					loading.dismiss();

					if(result != null){
						if (!listSalawat.isEmpty() && !listManatek.isEmpty()) {

							mManager.setSelectSalats(listSalawat);
							//						mManager.setSelectMosques(listJawamaa);

							initiateSpinners();

						}
					}
					else
						Utils.showInfoPopup(getActivity(), null, getString(R.string.error_internet_connexion));
				};
			}.execute();


		}

	}
	
	private void refreshMosquesByPlace(int placeId){
		new AsyncTask<Integer, Void, String>() {

			ProgressDialog loading ;

			protected void onPreExecute() {

				loading = new ProgressDialog(getActivity());
				loading.setMessage(getString(R.string.please_wait));
				loading.setCancelable(false);
				loading.show();

			};

			@Override
			protected String doInBackground(Integer... params) {

				listJawamaa = mManager.getAllSelectMosques(params[0]);

				return null;
			}

			protected void onPostExecute(String result) {

				loading.dismiss();

				if (!listJawamaa.isEmpty()) {

					initiateJawamaaSpinner();

				}

			};
		}.execute(placeId);

	}
	
	

	private void initiateSpinners() {


		//		initiateLists();

		ArrayList<String> listSpinnerGenres = new ArrayList<String>();
		ArrayList<String> listSpinnerSalawat = new ArrayList<String>();
//		ArrayList<String> listSpinnerJawamaa = new ArrayList<String>();
		ArrayList<String> listSpinnerManatek = new ArrayList<String>();

		for (int i = 0; i < listGenres.size(); i++) {
			listSpinnerGenres.add(listGenres.get(i).getTitle());
		}

		for (int i = 0; i < listSalawat.size(); i++) {
			listSpinnerSalawat.add(listSalawat.get(i).getTitle());
		}

//		for (int i = 0; i < listJawamaa.size(); i++) {
//			listSpinnerJawamaa.add(listJawamaa.get(i).getTitle());
//		}

		for (int i = 0; i < listManatek.size(); i++) {
			listSpinnerManatek.add(listManatek.get(i).getTitle());
		}

		ArrayAdapter<String> spinnerAdapterGenres = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listSpinnerGenres);
		ArrayAdapter<String> spinnerAdapterSalawat = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listSpinnerSalawat);
//		ArrayAdapter<String> spinnerAdapterJawamaa = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listSpinnerJawamaa);
		ArrayAdapter<String> spinnerAdapterManatek = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listSpinnerManatek);

		spinnerAdapterGenres.setDropDownViewResource(R.layout.spinner_item);
		spinnerAdapterSalawat.setDropDownViewResource(R.layout.spinner_item);
//		spinnerAdapterJawamaa.setDropDownViewResource(R.layout.spinner_item);
		spinnerAdapterManatek.setDropDownViewResource(R.layout.spinner_item);

		spinner_genre.setAdapter(spinnerAdapterGenres);
//		spinner_jamaa.setAdapter(spinnerAdapterJawamaa);
		spinner_mantaka.setAdapter(spinnerAdapterManatek);
		spinner_salat.setAdapter(spinnerAdapterSalawat);
		
		spinner_mantaka.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				refreshMosquesByPlace(listManatek.get(position).getId());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

	}	

	private void initiateJawamaaSpinner() {

		ArrayList<String> listSpinnerJawamaa = new ArrayList<String>();

		for (int i = 0; i < listJawamaa.size(); i++) {
			listSpinnerJawamaa.add(listJawamaa.get(i).getTitle());
		}

		ArrayAdapter<String> spinnerAdapterJawamaa = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listSpinnerJawamaa);
		
		spinnerAdapterJawamaa.setDropDownViewResource(R.layout.spinner_item);
		
		spinner_jamaa.setAdapter(spinnerAdapterJawamaa);

	}	


}
