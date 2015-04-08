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
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.entity.SelectMosque;
import com.janaezwadaawa.entity.SelectTrainer;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.Utils;


public class AddMouhadhraFragment extends Fragment {

	private JDManager mManager;

	private Button btn_add;
	private EditText txv_address, txv_body;
	private Spinner  spinner_trainer, spinner_jamaa , spinner_mantaka;
	private TextView  lbl_wakt_salat_begin,lbl_wakt_salat_final, lbl_trainer, lbl_jamaa, lbl_mantaka, lbl_body, lbl_address, title,salat_time_begin, salat_date_begin ;

	private ArrayList<SelectTrainer> listTrainers = new ArrayList<SelectTrainer>();
	private ArrayList<SelectMosque> listJawamaa = new ArrayList<SelectMosque>();
	private ArrayList<Place> listManatek = new ArrayList<Place>();


	String date_begin = "", time_begin = "",  date_final = "", time_final = ""  ;

	private TextView salat_time_final;

	private TextView salat_date_final;

	private TextView top_header;

	private Button btn_back, btn_preview;

	public AddMouhadhraFragment() {
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

		View rootView = inflater.inflate(R.layout.fragment_add_mouhadhra, container, false);

		btn_add 	= (Button) rootView.findViewById(R.id.btn_add);
		txv_address 	= (EditText) rootView.findViewById(R.id.txv_address);
		txv_body	= (EditText) rootView.findViewById(R.id.txv_body);

		title	= (TextView) rootView.findViewById(R.id.title);
		lbl_wakt_salat_begin	= (TextView) rootView.findViewById(R.id.lbl_wakt_salat_begin);
		lbl_wakt_salat_final	= (TextView) rootView.findViewById(R.id.lbl_wakt_salat_final);
		lbl_trainer	= (TextView) rootView.findViewById(R.id.lbl_trainer);
		lbl_jamaa	= (TextView) rootView.findViewById(R.id.lbl_jamaa);
		lbl_mantaka	= (TextView) rootView.findViewById(R.id.lbl_mantaka);
		lbl_body	= (TextView) rootView.findViewById(R.id.lbl_body);
		lbl_address	= (TextView) rootView.findViewById(R.id.lbl_address);

		salat_time_begin	= (TextView) rootView.findViewById(R.id.wakt_salat_begin_time);
		salat_date_begin	= (TextView) rootView.findViewById(R.id.wakt_salat_begin_date);

		salat_time_final	= (TextView) rootView.findViewById(R.id.wakt_salat_final_time);
		salat_date_final	= (TextView) rootView.findViewById(R.id.wakt_salat_final_date);

		spinner_trainer	= (Spinner) rootView.findViewById(R.id.spinner_trainer);
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
		lbl_trainer.setTypeface(JDFonts.getBDRFont());
		lbl_wakt_salat_begin.setTypeface(JDFonts.getBDRFont());
		lbl_wakt_salat_final.setTypeface(JDFonts.getBDRFont());
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

		salat_time_begin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);

				TimePickerDialog mTimePicker;

				mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

						Calendar mCalendar = Calendar.getInstance();
						mCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
						mCalendar.set(Calendar.MINUTE, selectedMinute);
						time_begin = new SimpleDateFormat("HH:mm").format(mCalendar.getTime());

						salat_time_begin.setText( time_begin);
					}
				}, hour, minute, true);//Yes 24 hour time

				mTimePicker.setTitle("إختيار وقت الصلاة :");
				mTimePicker.show();

			}
		});

		salat_date_begin.setOnClickListener(new OnClickListener() {

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
						date_begin = df.format(cal.getTime());

						salat_date_begin.setText(date_begin);
					}
				}, year, month, day);


				mDatePicker.setTitle("إختيار تاريخ الصلاة :");
				mDatePicker.show();


			}
		});


		salat_time_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);

				TimePickerDialog mTimePicker;

				mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

						Calendar mCalendar = Calendar.getInstance();
						mCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
						mCalendar.set(Calendar.MINUTE, selectedMinute);
						time_final = new SimpleDateFormat("HH:mm").format(mCalendar.getTime());

						salat_time_final.setText( time_final);
					}
				}, hour, minute, true);//Yes 24 hour time

				mTimePicker.setTitle("إختيار وقت الصلاة :");
				mTimePicker.show();

			}
		});

		salat_date_final.setOnClickListener(new OnClickListener() {

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
						date_final = df.format(cal.getTime());

						salat_date_final.setText(date_final);
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


				if (addressTxt.equalsIgnoreCase("") ||bodyTxt.equalsIgnoreCase("") || time_begin.equalsIgnoreCase("")|| date_begin.equalsIgnoreCase("")
						|| time_final.equalsIgnoreCase("")|| date_final.equalsIgnoreCase("")) {

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

							if(Utils.isOnline(getActivity()))
								return null;

							int place = listManatek.get(spinner_mantaka.getSelectedItemPosition()).getId();
							int mosque = listJawamaa.get(spinner_jamaa.getSelectedItemPosition()).getId();
							int trainer = listTrainers.get(spinner_trainer.getSelectedItemPosition()).getId();

							String time_date_begin = date_begin + " "+ time_begin ;
							String time_date_final = date_final + " "+ time_final ;

							return mManager.addDa3wa(mManager.getUid(), addressTxt, bodyTxt, place, mosque, trainer, time_date_begin, time_date_final);

						}

						protected void onPostExecute(Boolean result) {

							loading.dismiss();

							if(result != null)
								if (result ) {
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

//		listManatek = mManager.getPlaces();

		if (!mManager.getSelectTrainers().isEmpty() && !mManager.getPlaces().isEmpty()) {

			listTrainers = mManager.getSelectTrainers();
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
					listTrainers = mManager.getAllSelectTrainers();
//					listJawamaa = mManager.getAllSelectMosques();

					return "";
				}

				protected void onPostExecute(String result) {

					loading.dismiss();

					if(result != null)
					{
						if (!listTrainers.isEmpty() && !listManatek.isEmpty()) {
						
							mManager.setSelectTrainers(listTrainers);
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


		for (int i = 0; i < listTrainers.size(); i++) {
			listSpinnerSalawat.add(listTrainers.get(i).getTitle());
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

//		spinner_jamaa.setAdapter(spinnerAdapterJawamaa);
		spinner_mantaka.setAdapter(spinnerAdapterManatek);
		spinner_trainer.setAdapter(spinnerAdapterSalawat);
		
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
