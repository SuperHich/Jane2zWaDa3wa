package com.janaezwadaawa;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.janaezwadaawa.adapters.AddressesAdapter;
import com.janaezwadaawa.entity.Address;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;
import com.janaezwadaawa.utils.Utils;


public class AboutFragment extends Fragment {

	private JDManager jdManager;
//	private ProgressDialog loading;
	
	private ArrayList<Address> allAddresses = new ArrayList<Address>();
	
	private ListView listView;
	private TextView txv_emptyList; 
	private AddressesAdapter adapter;
	
	private LinearLayout loading;
	
	
	public AboutFragment() {
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
		
		jdManager = JDManager.getInstance(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.about_fragment, container, false);
		
		listView = (ListView) rootView.findViewById(R.id.listView);
		loading = (LinearLayout) rootView.findViewById(R.id.loading);
 		
		txv_emptyList 	= (TextView) rootView.findViewById(R.id.txv_emptyList);
		txv_emptyList	.setTypeface(JDFonts.getBDRFont());
		
		adapter = new AddressesAdapter(getActivity(), allAddresses);
		
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		listView.setAdapter(adapter);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		initData();
		
	}

	private void initData() {

		new AsyncTask<Void, Void, ArrayList<Address>>() {

			@Override
			protected void onPreExecute() {
				allAddresses.clear();
//				loading = new ProgressDialog(getActivity());
//				loading.setMessage(getString(R.string.please_wait));
//				loading.show();
				
				loading.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			protected ArrayList<Address> doInBackground(Void... params) {
				if(getActivity() != null){
					if(!Utils.isOnline(getActivity()))
						return null;

					allAddresses.addAll(jdManager.getAllAddresses());
					return allAddresses;
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(ArrayList<Address> result) {
				if(getActivity() == null)
					return;
				
				loading.setVisibility(View.GONE);
				
				if(result != null){
					adapter.notifyDataSetChanged();
				}else
					Utils.showInfoPopup(getActivity(), null, getString(R.string.error_internet_connexion));
				
				toggleEmptyMessage();
			}
		}.execute();
		
	}
	
	private void toggleEmptyMessage() {
		if(allAddresses.size() == 0)
			txv_emptyList.setVisibility(View.VISIBLE);
		else
			txv_emptyList.setVisibility(View.GONE);
	}

	
}
