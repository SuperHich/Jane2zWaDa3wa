package com.janaezwadaawa;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.janaezwadaawa.adapters.PlacesAdapter;
import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.Utils;


public class PlacesFragment extends ListFragment {

	private PlacesAdapter adapter;
	private ArrayList<Place> places = new ArrayList<Place>();
	private TextView txv_empty, top_header ;
	private Button btn_back;

	private JDManager mManager;
	
	public PlacesFragment() {
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
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_places, container, false);
		
		txv_empty = (TextView) rootView.findViewById(R.id.txv_emptyList);
		top_header =  (TextView) rootView.findViewById(R.id.top_header);
		btn_back	= (Button) rootView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		top_header.setTypeface(JDFonts.getBDRFont());
		
		adapter = new PlacesAdapter(getActivity(), R.layout.place_list_item, places);

		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mManager = JDManager.getInstance(getActivity());
		
		getListView().setAdapter(adapter);
//		getListView().setCacheColorHint(Color.TRANSPARENT);

		initData();
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				((IndexActivity) getActivity()).onPlaceSelected(places.get(position));
			}
		});
		
	}

	private void initData(){
		
		new AsyncTask<Void, Void, ArrayList<Place>>() {

//			private ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				places.clear();
//				loading = new ProgressDialog(getActivity());
//				loading.setCancelable(false);
//				loading.setMessage(getString(R.string.please_wait));
//				loading.show();
			}
			
			@Override
			protected ArrayList<Place> doInBackground(Void... params) {
				if(!Utils.isOnline(getActivity()))
					return null;
				
				if(mManager.getPlaces().isEmpty())
					mManager.getAllPlaces();
				
				return mManager.getPlaces();
			}
			
			@Override
			protected void onPostExecute(ArrayList<Place> result) {
//				loading.dismiss();
				
				if(result != null){
					places.addAll(result);
					adapter.notifyDataSetChanged();
				}else
					Utils.showInfoPopup(getActivity(), null, getString(R.string.error_internet_connexion));
				
				toggleEmptyMessage();
			}
		}.execute();

	}
	
	private void toggleEmptyMessage() {
		if(places.size() == 0)
			txv_empty.setVisibility(View.VISIBLE);
		else
			txv_empty.setVisibility(View.GONE);
	}
}
