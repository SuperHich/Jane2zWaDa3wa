package com.janaezwadaawa.externals;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.janaezwadaawa.adapters.IFragmentNotifier;
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.entity.Janeza;
import com.janaezwadaawa.entity.Mosque;
import com.janaezwadaawa.entity.Place;

public class JDManager {

	static final String TAG = "SABManager";
	
	private static final String URL_BASE 		= "http://smartlives.ws/projects/exequyApp/api/";
	private static final String URL_JANA2Z 		= URL_BASE + "exequy/";
	private static final String URL_DA3WA 		= URL_BASE + "lectures/";
	private static final String URL_MOSQUES 	= URL_BASE + "mosques/";
	private static final String URL_PLACES 		= URL_BASE + "places/";	
	
	private static final String ID 				= "id";
	private static final String TITLE 			= "title";
	private static final String MOSQUE 			= "mosque";
	private static final String PLACE 			= "place";
	private static final String DESCRIPTION 	= "description";
	private static final String PRAYER_TIME 	= "prayer_time";
	private static final String START_TIME 		= "start_time";
	private static final String END_TIME 		= "end_time";
	private static final String TRAINER			= "trainer";
	private static final String LAT			 	= "lat";
	private static final String LONG		 	= "long";
	private static final String PLACE_ID 		= "place_id";
	private static final String PLACE_NAME 		= "place_name";

	private IFragmentNotifier fragmentNotifier;
	
	private static JDManager mInstance = null;
	private static SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private JSONParser jsonParser;
	private Context mContext;
	
	private Mosque selectedMosque;
	private Place selectedPlace;
	
	public JDManager(Context context) {
		
		mContext = context;
		jsonParser = new JSONParser();
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
	}

	public synchronized static JDManager getInstance(Context context) {
		if (mInstance == null)
			mInstance = new JDManager(context);

		return mInstance;
	}
	
	public ArrayList<Mosque> getJanaezByPlace(int placeID) {

		HashMap<String, ArrayList<Janeza>> hashJana2z = new HashMap<String, ArrayList<Janeza>>();

		ArrayList<Mosque> mosques = new ArrayList<Mosque>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z + "place/?place=" + placeID);
		if (array != null) 
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject jObj = array.getJSONObject(i);
					Janeza janeza = new Janeza();
					//				janeza.setId(Integer.valueOf(jObj.getString(ID)));
					janeza.setTitle(jObj.getString(TITLE));
					janeza.setMosque(jObj.getString(MOSQUE));
					janeza.setPlace(jObj.getString(PLACE));
					janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
					janeza.setLatitude(jObj.getString(LAT));
					janeza.setLongitude(jObj.getString(LONG));

					Log.i(TAG, janeza.toString());

					ArrayList<Janeza> list = hashJana2z.get(janeza.getMosque());
					if(list == null)
						list = new ArrayList<Janeza>();

					list.add(janeza);
					hashJana2z.put(janeza.getMosque(), list);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		for(ArrayList<Janeza> list : hashJana2z.values()){
			Mosque mosque = new Mosque();
			mosque.setTitle(list.get(0).getMosque());
			mosque.setJana2z(list);
			mosques.add(mosque);
		}

		return mosques;
	}

	public ArrayList<Mosque> getAllMosque() {
		
		HashMap<String, ArrayList<Janeza>> hashJana2z = new HashMap<String, ArrayList<Janeza>>();
		
		ArrayList<Mosque> mosques = new ArrayList<Mosque>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Janeza janeza = new Janeza();
//				janeza.setId(Integer.valueOf(jObj.getString(ID)));
				janeza.setTitle(jObj.getString(TITLE));
				janeza.setMosque(jObj.getString(MOSQUE));
				janeza.setPlace(jObj.getString(PLACE));
				janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
				janeza.setLatitude(jObj.getString(LAT));
				janeza.setLongitude(jObj.getString(LONG));
				
				Log.i(TAG, janeza.toString());
				
				ArrayList<Janeza> list = hashJana2z.get(janeza.getMosque());
				if(list == null)
					list = new ArrayList<Janeza>();
				
				list.add(janeza);
				hashJana2z.put(janeza.getMosque(), list);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		for(ArrayList<Janeza> list : hashJana2z.values()){
			Mosque mosque = new Mosque();
			mosque.setTitle(list.get(0).getMosque());
			mosque.setJana2z(list);
			mosques.add(mosque);
		}
		
		return mosques;
	}
	
	
//	public ArrayList<Janeza> getJana2zByPlace(int placeId) {
//		ArrayList<Janeza> jana2z = new ArrayList<Janeza>();
//		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z.concat("place/?place=".concat(String.valueOf(placeId))));
//		if (array != null) 
//		for (int i = 0; i < array.length(); i++) {
//			try {
//				JSONObject jObj = array.getJSONObject(i);
//				Janeza janeza = new Janeza();
//				janeza.setId(Integer.valueOf(jObj.getString(ID)));
//				janeza.setTitle(jObj.getString(TITLE));
//				janeza.setMosque(jObj.getString(MOSQUE));
//				janeza.setPlace(jObj.getString(PLACE));
//				janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
//				janeza.setLatitude(jObj.getString(LAT));
//				janeza.setLongitude(jObj.getString(LONG));
//				
//				Log.i(TAG, janeza.toString());
//				
//				jana2z.add(janeza);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		
//		return jana2z;
//	}
	
	public ArrayList<Da3wa> getAllDa3awi() {
		ArrayList<Da3wa> da3awi = new ArrayList<Da3wa>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_DA3WA);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Da3wa da3wa = new Da3wa();
				da3wa.setId(Integer.valueOf(jObj.getString(ID)));
				da3wa.setTitle(jObj.getString(TITLE));
				da3wa.setDescription(jObj.getString(DESCRIPTION));
				da3wa.setMosque(jObj.getString(MOSQUE));
				da3wa.setPlace(jObj.getString(PLACE));
				da3wa.setTrainer(jObj.getString(TRAINER));
				da3wa.setStartTime(jObj.getString(START_TIME));
				da3wa.setEndTime(jObj.getString(END_TIME));
				da3wa.setLatitude(jObj.getString(LAT));
				da3wa.setLongitude(jObj.getString(LONG));
				
				Log.i(TAG, da3wa.toString());
				
				da3awi.add(da3wa);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return da3awi;
	}
	
	public ArrayList<Place> getAllPlaces() {
		ArrayList<Place> places = new ArrayList<Place>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_PLACES);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Place place = new Place();
				place.setId(Integer.valueOf(jObj.getString(PLACE_ID)));
				place.setTitle(jObj.getString(PLACE_NAME));
				
				Log.i(TAG, place.toString());
				
				places.add(place);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return places;
	}
	
	public IFragmentNotifier getFragmentNotifier() {
		return fragmentNotifier;
	}

	public void setFragmentNotifier(IFragmentNotifier fragmentNotifier) {
		this.fragmentNotifier = fragmentNotifier;
	}

	public Mosque getSelectedMosque() {
		return selectedMosque;
	}

	public void setSelectedMosque(Mosque selectedMosque) {
		this.selectedMosque = selectedMosque;
	}

	public Place getSelectedPlace() {
		return selectedPlace;
	}

	public void setSelectedPlace(Place selectedPlace) {
		this.selectedPlace = selectedPlace;
	}
}