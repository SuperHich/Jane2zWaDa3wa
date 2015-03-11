package com.janaezwadaawa.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.janaezwadaawa.R;
import com.janaezwadaawa.entity.JanezaGender;
import com.janaezwadaawa.utils.JDFonts;

public class JanezaGenderAdapter extends BaseExpandableListAdapter {

	static final String TAG = JanezaGenderAdapter.class.getSimpleName();
	IMenuListener listener;
	ArrayList<JanezaGender> items = new ArrayList<JanezaGender>();
	LayoutInflater inflater;

	public JanezaGenderAdapter(Context context, ArrayList<JanezaGender> items)
	{
		super();
		this.items.addAll(items);
		inflater= LayoutInflater.from(context);
		listener = (IMenuListener) context;
		
	}

	class ViewGroupHolder
	{
		TextView txv_gender;
		TextView txv_count;
	}
	
	class ViewChildHolder
	{
		TextView txv_name;
		TextView txv_name_value;
		TextView txv_janeza_index;
	}

	@Override
	public int getGroupCount() {
		return items.size();
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return items.get(groupPosition).getNames().size();
	}
	@Override
	public Object getGroup(int groupPosition) {
		return items.get(groupPosition);
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return items.get(groupPosition).getNames().get(childPosition);
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewGroupHolder holder;
		
		if(convertView == null)
		{
			holder = new ViewGroupHolder();
			convertView = inflater.inflate(R.layout.janeza_group_item, null);
			
			holder.txv_gender = (TextView) convertView.findViewById(R.id.txv_gender);
			holder.txv_count = (TextView) convertView.findViewById(R.id.txv_count);
			
			holder.txv_gender.setTypeface(JDFonts.getBDRFont());
			holder.txv_count.setTypeface(JDFonts.getBDRFont());

			convertView.setTag(holder);
		}
		else {
			
			holder = (ViewGroupHolder)convertView.getTag();
		}
		
		JanezaGender item = items.get(groupPosition);
		
		holder.txv_gender.setText(item.getTitle());
		holder.txv_count.setText(item.getCount() + "");
	
		return convertView;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewChildHolder holder;
		if(convertView==null)
		{
			holder = new ViewChildHolder();
			convertView = inflater.inflate(R.layout.janeza_child_item, null);
			
			holder.txv_name 			= (TextView) convertView.findViewById(R.id.txv_name);
			holder.txv_name_value 		= (TextView) convertView.findViewById(R.id.txv_name_value);
			holder.txv_janeza_index 	= (TextView) convertView.findViewById(R.id.txv_janeza_index);
			
			holder.txv_name.setTypeface(JDFonts.getBDRFont());
			holder.txv_name_value.setTypeface(JDFonts.getBDRFont());
			holder.txv_janeza_index.setTypeface(JDFonts.getBDRFont());
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewChildHolder)convertView.getTag();
		}
		
		String item = items.get(groupPosition).getNames().get(childPosition); 
		
		holder.txv_name_value.setText(""+item);
		holder.txv_janeza_index.setText(""+(childPosition+1));
		
		return convertView;
	}
	
	
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}