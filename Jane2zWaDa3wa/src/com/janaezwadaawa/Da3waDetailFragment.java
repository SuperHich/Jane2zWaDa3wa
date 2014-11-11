package com.janaezwadaawa;

import java.util.Calendar;
import java.util.Locale;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class Da3waDetailFragment extends Fragment {

//	public static final String ARG_JANA2Z_TYPE = "jana2z_type";
	
	private TextView	txv_day, txv_day_name,
						txv_date, txv_date_name,
						txv_prayer, txv_prayer_name,
						txv_title, txv_trainer, txv_desc, txv_mosque, txv_da3wa_date;
	
	private JDManager jdManager;
	private ProgressDialog loading;
	
	private Da3wa selectedDa3wa;
	
	private int gDay, gMonth, gYear,
	hDay, hMonth, hYear ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		jdManager = JDManager.getInstance(getActivity());
		selectedDa3wa = jdManager.getSelectedDa3wa();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.da3wa_details, container, false);

		txv_day 		= (TextView) rootView.findViewById(R.id.txv_day);
		txv_day_name 	= (TextView) rootView.findViewById(R.id.txv_day_name);
		txv_date 		= (TextView) rootView.findViewById(R.id.txv_date);
		txv_date_name 	= (TextView) rootView.findViewById(R.id.txv_date_name);
		txv_prayer 		= (TextView) rootView.findViewById(R.id.txv_prayer);
		txv_prayer_name = (TextView) rootView.findViewById(R.id.txv_prayer_name);
		
		txv_title		= (TextView) rootView.findViewById(R.id.txv_title);
		txv_trainer		= (TextView) rootView.findViewById(R.id.txv_trainer); 
		txv_desc		= (TextView) rootView.findViewById(R.id.txv_description); 
		txv_mosque		= (TextView) rootView.findViewById(R.id.txv_mosque); 
		txv_da3wa_date	= (TextView) rootView.findViewById(R.id.txv_da3wa_date);
		
		txv_day			.setTypeface(JDFonts.getBDRFont());
		txv_day_name	.setTypeface(JDFonts.getBDRFont());
		txv_date		.setTypeface(JDFonts.getBDRFont());
		txv_date_name	.setTypeface(JDFonts.getBDRFont());
		txv_prayer		.setTypeface(JDFonts.getBDRFont());
		txv_prayer_name	.setTypeface(JDFonts.getBDRFont());
		
		txv_title		.setTypeface(JDFonts.getBDRFont());
		txv_trainer		.setTypeface(JDFonts.getBDRFont());
		txv_desc		.setTypeface(JDFonts.getBDRFont());
		txv_mosque		.setTypeface(JDFonts.getBDRFont());
		txv_da3wa_date	.setTypeface(JDFonts.getBDRFont());
		
		
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		gDay = calendar.get(Calendar.DAY_OF_MONTH);
		gMonth = calendar.get(Calendar.MONTH) + 1;
		gYear = calendar.get(Calendar.YEAR);
		
		
		
		GHTDate gDate = Hijri.GregorianToHijri(gYear, gMonth, gDay);
		Log.i("refreshGDate", gDate.toString());
		
		
//		gDay = gDate.getDayG();
//		gMonth = gDate.getMonthG();
//		gYear = gDate.getYearG();
		
		hDay = gDate.getDayH();
		hMonth = gDate.getMonthH();
		hYear = gDate.getYearH();
		
		txv_day_name.setText( gDate.getDayNameH());
		txv_date_name.setText( hDay + " " + gDate.getMonthNameH() + "  " + hYear + " هـ.");
		
	//	txv_date.setText(gDate.getDayNameH() + " " + hDay + " " + gDate.getMonthNameH() + " " + hYear);
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		initData();
	}
	
	private void initData(){
		
		String non_formatted_date = selectedDa3wa.getStartTime() ;
		String formatted_full_date = (non_formatted_date.split(">")[1]).split("</")[0] ;
		
		String formatted_only_date = formatted_full_date.split(" - ")[0];
		
		Log.e("DATE+++", formatted_only_date);
	
		
		//////// Conversion to HIJRI /////////////////	
		
		String[] date = formatted_only_date.split("/");
		
		
		gYear = Integer.valueOf(date[2]);
		gMonth = Integer.valueOf(date[0]);
		gDay = Integer.valueOf(date[1]);
		
		GHTDate gDate = Hijri.GregorianToHijri(gYear, gMonth, gDay);
		Log.i("refreshGDate", gDate.toString());
		
		
		hDay = gDate.getDayH();
		hMonth = gDate.getMonthH();
		hYear = gDate.getYearH();
		
		String formatted_hijri_date = "يوم " + gDate.getDayNameH()+ " " + hDay + " " + gDate.getMonthNameH() + "  " + hYear + " هـ." ;
		
		
		txv_title.setText(selectedDa3wa.getTitle());
		txv_trainer.setText(selectedDa3wa.getTrainer());
		txv_desc.setText(selectedDa3wa.getDescription());
		txv_da3wa_date.setText(formatted_hijri_date);
		txv_mosque.setText(selectedDa3wa.getMosque());
		
//		new AsyncTask<Void, Void, ArrayList<Janeza>>() {
//
//			@Override
//			protected void onPreExecute() {
//				janaez.clear();
//				loading = new ProgressDialog(getActivity());
//				loading.setMessage(getString(R.string.please_wait));
//				loading.show();
//			}
//			
//			@Override
//			protected ArrayList<Janeza> doInBackground(Void... params) {
////				janaez.addAll(selectedMosque.getJana2z());
//				janaez.addAll(jdManager.getJanaezByMosque(selectedMosque.getId()));
//				return janaez;
//			}
//			
//			@Override
//			protected void onPostExecute(ArrayList<Janeza> result) {
//				loading.dismiss();
//				
//				if(result != null){
//					adapter.notifyDataSetChanged();
//				}
//				toggleEmptyMessage();
//			}
//		}.execute();
		
		

	}
}
