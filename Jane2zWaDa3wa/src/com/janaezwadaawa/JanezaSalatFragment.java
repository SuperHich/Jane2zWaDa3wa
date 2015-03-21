package com.janaezwadaawa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.janaezwadaawa.adapters.ISearchListener;
import com.janaezwadaawa.adapters.JanezaGenderAdapter;
import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.JanezaGender;
import com.janaezwadaawa.entity.Mosque2;
import com.janaezwadaawa.entity.Prayer;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class JanezaSalatFragment extends Fragment implements ISearchListener, OnTouchListener {

	protected static final String TAG = JanezaSalatFragment.class.getSimpleName();
	private JanezaGenderAdapter adapter;
	private ArrayList<JanezaGender> items = new ArrayList<JanezaGender>();
	private ArrayList<JanezaGender> allItems = new ArrayList<JanezaGender>();
	private int placeId = -1;
	private Mosque2 mosque;
	private Prayer prayer;
	
	private ExpandableListView listView;
	private TextView txv_emptyList, txv_total, txv_title, txv_salat, txv_douaa, txv_share,
					txv_day, txv_day_name,
					txv_date, txv_date_name;
	private ImageView img_share;
	
	private JDManager jdManager;
	private ProgressDialog loading;
	
	private int gDay, gMonth, gYear,
	hDay, hMonth, hYear ;
	private View footer;
	
	
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
		
		if(jdManager.getSelectedPrayer() != null)
			prayer = jdManager.getSelectedPrayer();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		jdManager.setSearchListener(null);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_janaez_list, container, false);

		listView = (ExpandableListView) rootView.findViewById(android.R.id.list);
 		
		txv_emptyList 	= (TextView) rootView.findViewById(R.id.txv_emptyList);
		txv_day 		= (TextView) rootView.findViewById(R.id.txv_day);
		txv_day_name 	= (TextView) rootView.findViewById(R.id.txv_day_name);
		txv_date 		= (TextView) rootView.findViewById(R.id.txv_date);
		txv_date_name 	= (TextView) rootView.findViewById(R.id.txv_date_name);
		txv_total 		= (TextView) rootView.findViewById(R.id.txv_total);
		txv_title 		= (TextView) rootView.findViewById(R.id.txv_title);
		txv_salat 		= (TextView) rootView.findViewById(R.id.txv_salat);
		
		
		txv_emptyList	.setTypeface(JDFonts.getBDRFont());
		txv_day			.setTypeface(JDFonts.getBDRFont());
		txv_day_name	.setTypeface(JDFonts.getBDRFont());
		txv_date		.setTypeface(JDFonts.getBDRFont());
		txv_date_name	.setTypeface(JDFonts.getBDRFont());
		txv_total		.setTypeface(JDFonts.getBDRFont());
		txv_title		.setTypeface(JDFonts.getBDRFont());
		txv_salat		.setTypeface(JDFonts.getBDRFont());
		
		
		//Add footer to items list
		LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		footer = vi.inflate(R.layout.janeza_footer, null);
		txv_douaa 		= (TextView) footer.findViewById(R.id.txv_douaa);
		txv_share 		= (TextView) footer.findViewById(R.id.txv_share);
		img_share 		= (ImageView) footer.findViewById(R.id.img_share);
		txv_douaa		.setTypeface(JDFonts.getBDRFont());
		txv_share		.setTypeface(JDFonts.getBDRFont());
		txv_douaa.setSelected(true);
		
		footer.setOnTouchListener(this);
		
		if(prayer.getCount() > 0)
			listView.addFooterView(footer, null, true);
		
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
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		txv_total.setText(String.valueOf(mosque.getCount()));
		txv_title.setText(mosque.getTitle());
		txv_salat.setText(prayer.getTitle());
		
		initData();
	}
	
	private void toggleEmptyMessage() {
		if(items.size() == 0)
			txv_emptyList.setVisibility(View.VISIBLE);
		else
			txv_emptyList.setVisibility(View.GONE);
	}

	
	private void initData(){
		
		new AsyncTask<Void, Void, ArrayList<JanezaGender>>() {

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
			protected ArrayList<JanezaGender> doInBackground(Void... params) {
				if(placeId != -1){
					for (int i = 0; i < 4; i++) {
						JanezaGender jg = new JanezaGender();
						jg.setTitle(getGenderName(i));
						ArrayList<String> names = jdManager.getZanaezNames(mosque.getId(), prayer.getId(), i);
						jg.setCount(names.size());
						jg.setNames(names);
						
						items.add(jg);
						allItems.addAll(items);				
					}
				}
				return items;
			}
			
			@Override
			protected void onPostExecute(ArrayList<JanezaGender> result) {
				loading.dismiss();
				
				if(result != null){
					adapter = new JanezaGenderAdapter(getActivity(), items);
					listView.setAdapter(adapter);
				}
				toggleEmptyMessage();
			}
		}.execute();

	}

	private String getGenderName(int id){
		switch (id) {
		case 0:
			return getString(R.string.men);
		case 1:
			return getString(R.string.women);
		case 2:
			return getString(R.string.child_male);
		case 3:
			return getString(R.string.child_female);
		default:
			return null;
		}
	}


	@Override
	public void onSearchBykeyword(String keyword) {
//		if(!keyword.equals("")){
//			ArrayList<Prayer> filteredList = new ArrayList<Prayer>();
//			for (Prayer item : items) {
//				if(item.getTitle().contains(keyword))
//				{
//					filteredList.add(item);
//				}
//			}
//			
//			if(filteredList.size() > 0){
//				items.clear();
//				items.addAll(filteredList);
//				adapter.notifyDataSetChanged();
//			}else
//			{
//				Toast.makeText(getActivity(), "No item found for \""+keyword+"\"", Toast.LENGTH_LONG).show();
//			}
//		}
//		else{
//			items.clear();
//			items.addAll(allItems);
//			adapter.notifyDataSetChanged();
//		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			RelativeLayout view = (RelativeLayout) v;
			view.getBackground().setColorFilter(0x7785dda8, PorterDuff.Mode.SRC_ATOP);
			v.invalidate();
			break;
		}
		case MotionEvent.ACTION_UP: {
			
			StringBuilder sb = new StringBuilder();
			sb.append(mosque.getTitle()+ " - " + prayer.getTitle() + "\n\n");
			
			for(JanezaGender jg : items){
				sb.append(jg.getCount() + " " + jg.getTitle());
				for(String str : jg.getNames()){
					sb.append("\n-" + str);
				}
				sb.append("\n\n");
			}

//			Log.i(TAG, ">>> msg " + sb.toString());
			shareJanaez(sb.toString());

		}
		case MotionEvent.ACTION_CANCEL: {
			RelativeLayout view = (RelativeLayout) v;
			view.getBackground().clearColorFilter();
			view.invalidate();
			break;
		}
		}
		return true;
	}
	
	private void shareJanaez(String text){

		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));

	}
}
