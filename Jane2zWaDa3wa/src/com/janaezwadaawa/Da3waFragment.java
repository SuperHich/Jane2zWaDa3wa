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
import android.widget.ListView;
import android.widget.TextView;

import com.janaezwadaawa.adapters.Da3waAdapter;
import com.janaezwadaawa.adapters.IJana2zListener;
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class Da3waFragment extends Fragment implements IJana2zListener {

	private Da3waAdapter adapter;
	private ArrayList<Da3wa> da3awi = new ArrayList<Da3wa>();
	
	private ListView listView;
	private TextView txv_emptyList; 
	
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
		View rootView = inflater.inflate(R.layout.fragment_da3awi, container, false);
		
		listView = (ListView) rootView.findViewById(R.id.listView);
 		
		txv_emptyList 	= (TextView) rootView.findViewById(R.id.txv_emptyList);
		txv_emptyList	.setTypeface(JDFonts.getBDRFont());
		
		adapter = new Da3waAdapter(getActivity(), da3awi, this);
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
		if(da3awi.size() == 0)
			txv_emptyList.setVisibility(View.VISIBLE);
		else
			txv_emptyList.setVisibility(View.GONE);
	}

	
	private void initData(){
		
		new AsyncTask<Void, Void, ArrayList<Da3wa>>() {

			@Override
			protected void onPreExecute() {
				da3awi.clear();
				loading = new ProgressDialog(getActivity());
				loading.setCancelable(false);
				loading.setMessage(getString(R.string.please_wait));
				loading.show();
			}
			
			@Override
			protected ArrayList<Da3wa> doInBackground(Void... params) {
				da3awi.addAll(jdManager.getAllDa3awi());
				return da3awi;
			}
			
			@Override
			protected void onPostExecute(ArrayList<Da3wa> result) {
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
