package com.janaezwadaawa;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.janaezwadaawa.adapters.PlacesAdapter;
import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.externals.JDManager;


public class PlacesFragment extends ListFragment {

	public static final String ARG_BOOKID = "arg_bookid";
	
	private PlacesAdapter adapter;
	private ArrayList<Place> places = new ArrayList<Place>();
	private TextView txv_empty;
	
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
		
//		if(getArguments() != null)
//			bookId = getArguments().getInt(ARG_BOOKID);
		
		View rootView = inflater.inflate(R.layout.fragment_places, container, false);
		
		txv_empty = (TextView) rootView.findViewById(R.id.txv_emptyList);

		adapter = new PlacesAdapter(getActivity(), R.layout.place_list_item, places);

		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		getListView().setAdapter(adapter);
//		getListView().setCacheColorHint(Color.TRANSPARENT);

		new LoadDataTask().execute();
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				((IndexActivity) getActivity()).onPlaceSelected(places.get(position));
				getActivity().onBackPressed();
			}
		});
		
	}

	private class LoadDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}
			
			places.clear();
			places.addAll(JDManager.getInstance(getActivity()).getAllPlaces());

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// We need notify the adapter that the data have been changed
			adapter.notifyDataSetChanged();
			
			if(places.size() == 0)
				txv_empty.setVisibility(View.VISIBLE);
			else
				txv_empty.setVisibility(View.GONE);

		}

		@Override
		protected void onCancelled() {
		}
	}

}
