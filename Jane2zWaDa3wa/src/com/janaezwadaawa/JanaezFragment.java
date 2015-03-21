package com.janaezwadaawa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.adapters.IJana2zListener;
import com.janaezwadaawa.adapters.ISearchListener;
import com.janaezwadaawa.adapters.JanaezAdapter;
import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.Janeza;
import com.janaezwadaawa.entity.Mosque;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class JanaezFragment extends Fragment implements IJana2zListener, ISearchListener {

	private JanaezAdapter adapter;
	private ArrayList<Janeza> janaez = new ArrayList<Janeza>();
	private ArrayList<Janeza> allJanaez = new ArrayList<Janeza>();
	
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
		
		selectedMosque = jdManager.getSelectedMosque();
		
		txv_title.setText(selectedMosque.getTitle());
		txv_total.setText(selectedMosque.getCount()+"");
		
		adapter = new JanaezAdapter(getActivity(), janaez, this);
		listView.setAdapter(adapter);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		initData();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		jdManager = JDManager.getInstance(getActivity());
		jdManager.setSearchListener(this);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		jdManager.setSearchListener(null);
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
				allJanaez.clear();
				janaez.clear();
				loading = new ProgressDialog(getActivity());
				loading.setMessage(getString(R.string.please_wait));
				loading.show();
			}
			
			@Override
			protected ArrayList<Janeza> doInBackground(Void... params) {
//				janaez.addAll(selectedMosque.getJana2z());
				janaez.addAll(jdManager.getJanaezByMosque(selectedMosque.getId()));
				allJanaez.addAll(janaez);
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


	@Override
	public void onSearchBykeyword(String keyword) {
		
		if(!keyword.equals("")){
			ArrayList<Janeza> filteredList = new ArrayList<Janeza>();
			for (Janeza janeza : janaez) {
				if(janeza.getMosque().contains(keyword)
						|| janeza.getPlace().contains(keyword)
						|| janeza.getTitle().contains(keyword))
				{
					filteredList.add(janeza);
				}
			}
			
			if(filteredList.size() > 0){
				janaez.clear();
				janaez.addAll(filteredList);
				adapter.notifyDataSetChanged();
			}else
			{
				Toast.makeText(getActivity(), "No item found for \""+keyword+"\"", Toast.LENGTH_LONG).show();
			}
		}
		else{
			janaez.clear();
			janaez.addAll(allJanaez);
			adapter.notifyDataSetChanged();
		}
		
	}
}
