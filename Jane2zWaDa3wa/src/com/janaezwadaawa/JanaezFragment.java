package com.janaezwadaawa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.janaezwadaawa.adapters.IJana2zListener;
import com.janaezwadaawa.adapters.JanaezAdapter;
import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.Janeza;
import com.janaezwadaawa.entity.Mosque;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class JanaezFragment extends Fragment implements IJana2zListener {

//	public static final String ARG_JANA2Z_TYPE = "jana2z_type";
//	public static final String ARG_PLACE_ID = "place_id";
//	public static final String ARG_JANA2Z_SEARCH = "jana2z_search_type";
//	public static final String ARG_JANA2Z_KEYWORD_TEXT = "jana2z_keyword";
	
	private JanaezAdapter adapter;
	private ArrayList<Janeza> janaez = new ArrayList<Janeza>();
//	private int jana2zType;
//	private int placeId;
//	private int jana2zSearch;
//	private String jana2zKeyword;
	
	private ListView listView;
	private TextView txv_emptyList, 
					txv_day, txv_day_name,
					txv_date, txv_date_name,
					txv_prayer, txv_prayer_name,
					txv_title, txv_total;
	
	private JDManager jdManager;
	private ProgressDialog loading;
	
	private Mosque selectedMosque;
	
	private int gDay, gMonth, gYear,
	hDay, hMonth, hYear ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		jdManager = JDManager.getInstance(getActivity());
		selectedMosque = jdManager.getSelectedMosque();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_janaez, container, false);
//		if(getArguments() != null){
//			jana2zType = getArguments().getInt(ARG_JANA2Z_TYPE);
//			placeId = getArguments().getInt(ARG_PLACE_ID);
//			jana2zSearch = getArguments().getInt(ARG_JANA2Z_SEARCH);
//			jana2zKeyword = getArguments().getString(ARG_JANA2Z_KEYWORD_TEXT);
//		}

		listView = (ListView) rootView.findViewById(R.id.listView);
 		
		txv_emptyList 	= (TextView) rootView.findViewById(R.id.txv_emptyList);
		txv_day 		= (TextView) rootView.findViewById(R.id.txv_day);
		txv_day_name 	= (TextView) rootView.findViewById(R.id.txv_day_name);
		txv_date 		= (TextView) rootView.findViewById(R.id.txv_date);
		txv_date_name 	= (TextView) rootView.findViewById(R.id.txv_date_name);
		txv_prayer 		= (TextView) rootView.findViewById(R.id.txv_prayer);
		txv_prayer_name = (TextView) rootView.findViewById(R.id.txv_prayer_name);
		txv_title 		= (TextView) rootView.findViewById(R.id.txv_title);
		txv_total 		= (TextView) rootView.findViewById(R.id.txv_total);
		
		txv_emptyList	.setTypeface(JDFonts.getBDRFont());
		txv_day			.setTypeface(JDFonts.getBDRFont());
		txv_day_name	.setTypeface(JDFonts.getBDRFont());
		txv_date		.setTypeface(JDFonts.getBDRFont());
		txv_date_name	.setTypeface(JDFonts.getBDRFont());
		txv_prayer		.setTypeface(JDFonts.getBDRFont());
		txv_prayer_name	.setTypeface(JDFonts.getBDRFont());
		txv_title		.setTypeface(JDFonts.getBDRFont());
		txv_total		.setTypeface(JDFonts.getBDRFont());
		
		
		txv_title.setText(selectedMosque.getTitle());
		txv_total.setText(selectedMosque.getCount()+"");
		
		
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
		
		
		adapter = new JanaezAdapter(getActivity(), janaez, this);
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		listView.setAdapter(adapter);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		initData();
	}
	
	private void toggleEmptyMessage() {
		if(janaez.size() == 0)
			txv_emptyList.setVisibility(View.VISIBLE);
		else
			txv_emptyList.setVisibility(View.GONE);
	}

	
	private void initData(){
		
		new AsyncTask<Void, Void, ArrayList<Janeza>>() {

			@Override
			protected void onPreExecute() {
				janaez.clear();
				loading = new ProgressDialog(getActivity());
				loading.setMessage(getString(R.string.please_wait));
				loading.show();
			}
			
			@Override
			protected ArrayList<Janeza> doInBackground(Void... params) {
//				janaez.addAll(selectedMosque.getJana2z());
				janaez.addAll(jdManager.getJanaezByMosque(selectedMosque.getId()));
				return janaez;
			}
			
			@Override
			protected void onPostExecute(ArrayList<Janeza> result) {
				loading.dismiss();
				
				if(result != null){
					adapter.notifyDataSetChanged();
				}
				toggleEmptyMessage();
			}
		}.execute();

	}


	@Override
	public void onItemDetailsClicked(int position) {
		// TODO Auto-generated method stub
		
	}
}
