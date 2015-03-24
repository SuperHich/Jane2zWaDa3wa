package com.janaezwadaawa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.janaezwadaawa.adapters.ISearchListener;
import com.janaezwadaawa.adapters.PrayersAdapter;
import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.Mosque2;
import com.janaezwadaawa.entity.Prayer;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class JanezaMosquesFragment extends Fragment implements ISearchListener {

	protected static final String TAG = null;
	private PrayersAdapter adapter;
	private ArrayList<Prayer> items = new ArrayList<Prayer>();
	private ArrayList<Prayer> allItems = new ArrayList<Prayer>();
	private int placeId = -1;
	private Mosque2 mosque;
	
	private ExpandableListView listView;
	private TextView txv_emptyList, txv_total, txv_title,
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
		
		if(jdManager.getSelectedMosque2() != null)
			mosque = jdManager.getSelectedMosque2();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		jdManager.setSearchListener(null);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_prayers_list, container, false);

		listView = (ExpandableListView) rootView.findViewById(android.R.id.list);
 		
		txv_emptyList 	= (TextView) rootView.findViewById(R.id.txv_emptyList);
		txv_day 		= (TextView) rootView.findViewById(R.id.txv_day);
		txv_day_name 	= (TextView) rootView.findViewById(R.id.txv_day_name);
		txv_date 		= (TextView) rootView.findViewById(R.id.txv_date);
		txv_date_name 	= (TextView) rootView.findViewById(R.id.txv_date_name);
		txv_total 		= (TextView) rootView.findViewById(R.id.txv_total);
		txv_title 		= (TextView) rootView.findViewById(R.id.txv_title);
		
		txv_emptyList	.setTypeface(JDFonts.getBDRFont());
		txv_day			.setTypeface(JDFonts.getBDRFont());
		txv_day_name	.setTypeface(JDFonts.getBDRFont());
		txv_date		.setTypeface(JDFonts.getBDRFont());
		txv_date_name	.setTypeface(JDFonts.getBDRFont());
		txv_total		.setTypeface(JDFonts.getBDRFont());
		txv_title		.setTypeface(JDFonts.getBDRFont());
		
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
		
		listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				
				jdManager.setSelectedPrayer(items.get(groupPosition));
				((MainActivity) getActivity()).goToFragment(MainActivity.JANEZA_SALAT_FRAGMENT);
				
				return true;
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		txv_total.setText(String.valueOf(mosque.getCount()));
		txv_title.setText(mosque.getTitle());
		
		initData();
	}
	
	private void toggleEmptyMessage() {
		if(items.size() == 0)
			txv_emptyList.setVisibility(View.VISIBLE);
		else
			txv_emptyList.setVisibility(View.GONE);
	}

	
	private void initData(){
		
		new AsyncTask<Void, Void, ArrayList<Prayer>>() {

			@Override
			protected void onPreExecute() {
				allItems.clear();
				items.clear();
				loading = new ProgressDialog(getActivity());
				loading.setCancelable(false);
				loading.setMessage(getString(R.string.please_wait));
				loading.show();
			}
			
			@Override
			protected ArrayList<Prayer> doInBackground(Void... params) {
				if(placeId != -1){
					items.addAll(jdManager.getPrayerByMosque(placeId, mosque.getId()));
					allItems.addAll(items);
				}
				return orderedPrayers(items);
			}
			
			@Override
			protected void onPostExecute(ArrayList<Prayer> result) {
				loading.dismiss();
				
				if(result != null){
					adapter = new PrayersAdapter(getActivity(), items);
					listView.setAdapter(adapter);
				}
				toggleEmptyMessage();
			}
		}.execute();

	}



	@Override
	public void onSearchBykeyword(String keyword) {
		if(!keyword.equals("")){
			ArrayList<Prayer> filteredList = new ArrayList<Prayer>();
			for (Prayer item : items) {
				if(item.getTitle().contains(keyword))
				{
					filteredList.add(item);
				}
			}
			
			if(filteredList.size() > 0){
				items.clear();
				items.addAll(filteredList);
				adapter.notifyDataSetChanged();
			}else
			{
				Toast.makeText(getActivity(), "No item found for \""+keyword+"\"", Toast.LENGTH_LONG).show();
			}
		}
		else{
			items.clear();
			items.addAll(allItems);
			adapter.notifyDataSetChanged();
		}
	}
	
	private ArrayList<Prayer> orderedPrayers(ArrayList<Prayer> prayers){
		ArrayList<Prayer> result = new ArrayList<Prayer>();
		
		Prayer p0 = getPrayerById(prayers, 54);
		if(p0 != null)
			result.add(p0);
		
		Prayer p1 = getPrayerById(prayers, 50);
		if(p1 != null)
			result.add(p1);
		
		Prayer p2 = getPrayerById(prayers, 51);
		if(p2 != null)
			result.add(p2);
		
		Prayer p3 = getPrayerById(prayers, 52);
		if(p3 != null)
			result.add(p3);
		
		Prayer p4 = getPrayerById(prayers, 53);
		if(p4 != null)
			result.add(p4);
		
		return result;
	}
	
	private Prayer getPrayerById(ArrayList<Prayer> prayers, int id){
		for (int i = 0; i < prayers.size(); i++) {
			Prayer p = prayers.get(i);
			if(p.getId() == id)
				return p;
		}
		
		return null;
	}
}
