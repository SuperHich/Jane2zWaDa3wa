package com.janaezwadaawa;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.janaezwadaawa.adapters.IJana2zListener;
import com.janaezwadaawa.adapters.MosqueAdapter;
import com.janaezwadaawa.entity.Mosque;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class MosqueFragment extends Fragment implements IJana2zListener {

	public static final String ARG_JANA2Z_TYPE = "jana2z_type";
	public static final String ARG_PLACE_ID = "place_id";
	public static final String ARG_JANA2Z_SEARCH = "jana2z_search_type";
	public static final String ARG_JANA2Z_KEYWORD_TEXT = "jana2z_keyword";
	
	private MosqueAdapter adapter;
	private ArrayList<Mosque> mosques = new ArrayList<Mosque>();
	private int jana2zType;
	private int placeId;
	private int jana2zSearch;
	private String jana2zKeyword;
	
	private GridView gridView;
	private TextView txv_emptyList, 
					txv_day, txv_day_name,
					txv_date, txv_date_name,
					txv_prayer, txv_prayer_name;
	
	private JDManager jdManager;
	private ProgressDialog loading;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		jdManager = JDManager.getInstance(getActivity());
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mosques, container, false);
		if(getArguments() != null){
			jana2zType = getArguments().getInt(ARG_JANA2Z_TYPE);
			placeId = getArguments().getInt(ARG_PLACE_ID);
			jana2zSearch = getArguments().getInt(ARG_JANA2Z_SEARCH);
			jana2zKeyword = getArguments().getString(ARG_JANA2Z_KEYWORD_TEXT);
		}

		gridView = (GridView) rootView.findViewById(R.id.gridView);
 		
		txv_emptyList 	= (TextView) rootView.findViewById(R.id.txv_emptyList);
		txv_day 		= (TextView) rootView.findViewById(R.id.txv_day);
		txv_day_name 	= (TextView) rootView.findViewById(R.id.txv_day_name);
		txv_date 		= (TextView) rootView.findViewById(R.id.txv_date);
		txv_date_name 	= (TextView) rootView.findViewById(R.id.txv_date_name);
		txv_prayer 		= (TextView) rootView.findViewById(R.id.txv_prayer);
		txv_prayer_name = (TextView) rootView.findViewById(R.id.txv_prayer_name);
		
		txv_emptyList	.setTypeface(JDFonts.getBDRFont());
		txv_day			.setTypeface(JDFonts.getBDRFont());
		txv_day_name	.setTypeface(JDFonts.getBDRFont());
		txv_date		.setTypeface(JDFonts.getBDRFont());
		txv_date_name	.setTypeface(JDFonts.getBDRFont());
		txv_prayer		.setTypeface(JDFonts.getBDRFont());
		txv_prayer_name	.setTypeface(JDFonts.getBDRFont());
		
		adapter = new MosqueAdapter(getActivity(), mosques, this);
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		gridView.setAdapter(adapter);
		gridView.setCacheColorHint(Color.TRANSPARENT);
		
		initData();
	}
	
	private void toggleEmptyMessage() {
		if(mosques.size() == 0)
			txv_emptyList.setVisibility(View.VISIBLE);
		else
			txv_emptyList.setVisibility(View.GONE);
	}

	
	private void initData(){
		
		new AsyncTask<Void, Void, ArrayList<Mosque>>() {

			@Override
			protected void onPreExecute() {
				mosques.clear();
				loading = new ProgressDialog(getActivity());
				loading.setMessage(getString(R.string.please_wait));
				loading.show();
			}
			
			@Override
			protected ArrayList<Mosque> doInBackground(Void... params) {
				mosques.addAll(jdManager.getAllMosque());
				return mosques;
			}
			
			@Override
			protected void onPostExecute(ArrayList<Mosque> result) {
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
		jdManager.setSelectedMosque(mosques.get(position));
		((MainActivity) getActivity()).goToJanaezFragment();
		
	}
}