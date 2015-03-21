package com.janaezwadaawa;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.janaezwadaawa.adapters.ElementsListAdapter;
import com.janaezwadaawa.entity.DrawerItem;
import com.janaezwadaawa.externals.JDManager;
import com.janaezwadaawa.utils.JDFonts;
import com.pixplicity.easyprefs.library.Prefs;


public class AdminMenuFragment extends ListFragment {

	private ElementsListAdapter adapter;
	private ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
	private TextView top_header ;
	private Button btn_back;

	private JDManager mManager;
	
	public AdminMenuFragment() {
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
		
		top_header =  (TextView) rootView.findViewById(R.id.top_header);
		
		top_header.setText(R.string.admin_menu);
		top_header.setTypeface(JDFonts.getBDRFont());
		
		btn_back = (Button) rootView.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mManager = JDManager.getInstance(getActivity());
		
		items.add(new DrawerItem(getString(R.string.add_janaza), R.drawable.ic_drawer_janaez));
		items.add(new DrawerItem(getString(R.string.add_mouhadhra), R.drawable.ic_drawer_mouhadhrat));
		items.add(new DrawerItem(getString(R.string.quit), R.drawable.ic_drawer));
		
		adapter = new ElementsListAdapter(getActivity(), items);
		getListView().setAdapter(adapter);
//		getListView().setCacheColorHint(Color.TRANSPARENT);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					((IndexActivity) getActivity()).goToFragment(IndexActivity.ADD_JANEZA_FRAGMENT, false);
					break;
				case 1:
					((IndexActivity) getActivity()).goToFragment(IndexActivity.ADD_MOHADHRA_FRAGMENT, false);
					break;
				case 2:
					mManager.setLoggedIn(false);
					mManager.setUid(null);

					Prefs.initPrefs(getActivity());
					Prefs.putString("uid", "");
					
					((IndexActivity) getActivity()).onBackPressed();
					break;
				default:
					break;
				}
			}
		});
		
	}

}
