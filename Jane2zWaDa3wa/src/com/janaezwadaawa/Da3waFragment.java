package com.janaezwadaawa;

import java.util.ArrayList;

import android.app.Activity;
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
import android.widget.Toast;

import com.janaezwadaawa.adapters.Da3waAdapter;
import com.janaezwadaawa.adapters.IJana2zListener;
import com.janaezwadaawa.adapters.ISearchListener;
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;

public class Da3waFragment extends Fragment implements IJana2zListener, ISearchListener {

	private Da3waAdapter adapter;
	private ArrayList<Da3wa> da3awi = new ArrayList<Da3wa>();
	private ArrayList<Da3wa> allDa3awi = new ArrayList<Da3wa>();
	
	private ListView listView;
	private TextView txv_emptyList; 
	
	private JDManager jdManager;
	private ProgressDialog loading;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
				allDa3awi.clear();
				da3awi.clear();
				loading = new ProgressDialog(getActivity());
				loading.setCancelable(false);
				loading.setMessage(getString(R.string.please_wait));
				loading.show();
			}
			
			@Override
			protected ArrayList<Da3wa> doInBackground(Void... params) {
				da3awi.addAll(jdManager.getAllDa3awi());
				allDa3awi.addAll(da3awi);
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
		jdManager.setSelectedDa3wa(da3awi.get(position));
		((MainActivity) getActivity()).goToDa3waDetailsFragment();
	}

	@Override
	public void onSearchBykeyword(String keyword) {
		if(!keyword.equals("")){
			ArrayList<Da3wa> filteredList = new ArrayList<Da3wa>();
			for (Da3wa da3wa : da3awi) {
				if(da3wa.getDescription().contains(keyword)
						|| da3wa.getEndTime().contains(keyword)
						|| da3wa.getMosque().contains(keyword)
						|| da3wa.getPlace().contains(keyword)
						|| da3wa.getStartTime().contains(keyword)
						|| da3wa.getTitle().contains(keyword)
						|| da3wa.getTrainer().contains(keyword))
				{
					filteredList.add(da3wa);
				}
			}
			
			if(filteredList.size() > 0){
				da3awi.clear();
				da3awi.addAll(filteredList);
				adapter.notifyDataSetChanged();
			}else
			{
				Toast.makeText(getActivity(), "No item found for \""+keyword+"\"", Toast.LENGTH_LONG).show();
			}
		}else
		{
			da3awi.clear();
			da3awi.addAll(allDa3awi);
			adapter.notifyDataSetChanged();
		}
	}
}
