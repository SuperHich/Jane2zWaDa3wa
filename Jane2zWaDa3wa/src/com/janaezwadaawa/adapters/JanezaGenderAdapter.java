package com.janaezwadaawa.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.janaezwadaawa.R;
import com.janaezwadaawa.dateconverter.Hijri;
import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.JanezaGender;
import com.janaezwadaawa.entity.JanezaPerson;
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
		TextView txv_date;
		TextView txv_date_value;
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
			holder.txv_date 			= (TextView) convertView.findViewById(R.id.txv_date);
			holder.txv_date_value 		= (TextView) convertView.findViewById(R.id.txv_date_value);
			holder.txv_janeza_index 	= (TextView) convertView.findViewById(R.id.txv_janeza_index);
			
			holder.txv_name.setTypeface(JDFonts.getBDRFont());
			holder.txv_name_value.setTypeface(JDFonts.getBDRFont());
			holder.txv_date.setTypeface(JDFonts.getBDRFont());
			holder.txv_date_value.setTypeface(JDFonts.getBDRFont());
			holder.txv_janeza_index.setTypeface(JDFonts.getBDRFont());
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewChildHolder)convertView.getTag();
		}
		
		JanezaPerson item = items.get(groupPosition).getNames().get(childPosition); 
		
		holder.txv_name_value.setText(""+item.getTitle());
		holder.txv_date_value.setText(getFormattedHijriDate(item.getDate()));
		holder.txv_janeza_index.setText(""+(childPosition+1));
		
		return convertView;
	}
	
	
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	private String getFormattedHijriDate(String date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = sdf.parse(date);
			
			if(d != null){
				Calendar calendar = Calendar.getInstance(Locale.getDefault());
				calendar.setTime(d);
				int gDay = calendar.get(Calendar.DAY_OF_MONTH);
				int gMonth = calendar.get(Calendar.MONTH) + 1;
				int gYear = calendar.get(Calendar.YEAR);
				
				GHTDate gDate = Hijri.GregorianToHijri(gYear, gMonth, gDay);
				
				gDay = gDate.getDayG();
				gMonth = gDate.getMonthG();
				gYear = gDate.getYearG();
				
				int hDay = gDate.getDayH();
				int hMonth = gDate.getMonthH();
				int hYear = gDate.getYearH();
				
				return gDate.getDayNameH() + "  " + hDay + " " + gDate.getMonthNameH() + "  " + hYear + " هـ.";
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
		
	}

}