package com.janaezwadaawa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.adapters.ISearchListener;
import com.janaezwadaawa.adapters.Mosque2Adapter;
import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.Mosque2;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class JanezaPlacesFragment extends Fragment implements ISearchListener {

	private Mosque2Adapter adapter;
	private ArrayList<Mosque2> mosques = new ArrayList<Mosque2>();
	private ArrayList<Mosque2> allMosques = new ArrayList<Mosque2>();
	private int placeId = -1;
	
	private ListView listView;
	private TextView txv_emptyList, 
					txv_day, txv_day_name,
					txv_date, txv_date_name;
	
	private JDManager jdManager;
	private ProgressDialog loading;
	
	private int gDay, gMonth, gYear,
	hDay, hMonth, hYear ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		jdManager = JDManager.getInstance(getActivity());
		jdManager.setSearchListener(this);
		
		if(jdManager.getSelectedPlace() != null)
			placeId = jdManager.getSelectedPlace().getId();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		jdManager.setSearchListener(null);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mosques_list, container, false);

		listView = (ListView) rootView.findViewById(android.R.id.list);
 		
		txv_emptyList 	= (TextView) rootView.findViewById(R.id.txv_emptyList);
		txv_day 		= (TextView) rootView.findViewById(R.id.txv_day);
		txv_day_name 	= (TextView) rootView.findViewById(R.id.txv_day_name);
		txv_date 		= (TextView) rootView.findViewById(R.id.txv_date);
		txv_date_name 	= (TextView) rootView.findViewById(R.id.txv_date_name);
		
		txv_emptyList	.setTypeface(JDFonts.getBDRFont());
		txv_day			.setTypeface(JDFonts.getBDRFont());
		txv_day_name	.setTypeface(JDFonts.getBDRFont());
		txv_date		.setTypeface(JDFonts.getBDRFont());
		txv_date_name	.setTypeface(JDFonts.getBDRFont());
		
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		gDay = calendar.get(Calendar.DAY_OF_MONTH);
		gMonth = calendar.get(Calendar.MONTH) + 1;
		gYear = calendar.get(Calendar.YEAR);
		
		GHTDate gDate = Hijri.GregorianToHijri(gYear, gMonth, gDay);
//		Log.i("refreshGDate", gDate.toString());
		
		
		gDay = gDate.getDayG();
		gMonth = gDate.getMonthG();
		gYear = gDate.getYearG();
		
		hDay = gDate.getDayH();
		hMonth = gDate.getMonthH();
		hYear = gDate.getYearH();
		
		txv_day_name.setText( gDate.getDayNameH());
		txv_date_name.setText( hDay + " " + gDate.getMonthNameH() + "  " + hYear + " هـ.");
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				jdManager.setSelectedMosque2(mosques.get(position));
				((MainActivity) getActivity()).goToFragment(MainActivity.JANEZA_MOSQUES_FRAGMENT);
				
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		adapter = new Mosque2Adapter(getActivity(), mosques);
		listView.setAdapter(adapter);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		initData();
	}
	
	private void toggleEmptyMessage() {
		if(mosques.size() == 0)
			txv_emptyList.setVisibility(View.VISIBLE);
		else
			txv_emptyList.setVisibility(View.GONE);
	}

	
	private void initData(){
		
		new AsyncTask<Void, Void, ArrayList<Mosque2>>() {

			@Override
			protected void onPreExecute() {
				allMosques.clear();
				mosques.clear();
				loading = new ProgressDialog(getActivity());
				loading.setCancelable(false);
				loading.setMessage(getString(R.string.please_wait));
				loading.show();
			}
			
			@Override
			protected ArrayList<Mosque2> doInBackground(Void... params) {
				if(placeId != -1){
					mosques.addAll(jdManager.getMosques2ByPlace(placeId));
					allMosques.addAll(mosques);
				}
				return mosques;
			}
			
			@Override
			protected void onPostExecute(ArrayList<Mosque2> result) {
				loading.dismiss();
				
				if(result != null){
					adapter.notifyDataSetChanged();
				}
				toggleEmptyMessage();
			}
		}.execute();

	}



	@Override
	public void onSearchBykeyword(String keyword) {
		if(!keyword.equals("")){
			ArrayList<Mosque2> filteredList = new ArrayList<Mosque2>();
			for (Mosque2 mosque : mosques) {
				if(mosque.getTitle().contains(keyword))
				{
					filteredList.add(mosque);
				}
			}
			
			if(filteredList.size() > 0){
				mosques.clear();
				mosques.addAll(filteredList);
				adapter.notifyDataSetChanged();
			}else
			{
				Toast.makeText(getActivity(), "No item found for \""+keyword+"\"", Toast.LENGTH_LONG).show();
			}
		}
		else{
			mosques.clear();
			mosques.addAll(allMosques);
			adapter.notifyDataSetChanged();
		}
	}
}
