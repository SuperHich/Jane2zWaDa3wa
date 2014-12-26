package com.janaezwadaawa.externals;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.janaezwadaawa.adapters.IFragmentNotifier;
import com.janaezwadaawa.adapters.ISearchListener;
import com.janaezwadaawa.entity.Address;
import com.janaezwadaawa.entity.Da3wa;
import com.janaezwadaawa.entity.Janeza;
import com.janaezwadaawa.entity.Mosque;
import com.janaezwadaawa.entity.Place;
import com.janaezwadaawa.gcm.GcmResponse;

public class JDManager {

	static final String TAG = "JDManager";
	
	private static final String URL_BASE 		= "http://smartlives.ws/projects/exequyApp/api/";
	private static final String URL_JANA2Z 		= URL_BASE + "exequy/";
	private static final String URL_DA3WA 		= URL_BASE + "lectures/";
	private static final String URL_MOSQUES 	= URL_BASE + "mosques";
	private static final String URL_PLACES 		= URL_BASE + "places/";	
	private static final String URL_ADDRESSES 	= URL_BASE + "addresses/";	
	private static final String URL_PUSH_REGISTER	= "http://smartlives.ws/projects/exequyApp/mobile_data/push_notifications";
	
	private static final String ID 				= "id";
	private static final String TITLE 			= "title";
	private static final String MOSQUE 			= "mosque";
	private static final String PLACE 			= "place";
	private static final String DESCRIPTION 	= "descrption";
	private static final String PRAYER_TIME 	= "prayer_time";
	private static final String START_TIME 		= "start_time";
	private static final String END_TIME 		= "end_time";
	private static final String TRAINER			= "trainer";
	private static final String LAT			 	= "lat";
	private static final String LONG		 	= "long";
	private static final String PLACE_ID 		= "place_id";
	private static final String PLACE_NAME 		= "place_name";
	private static final String MOSQUE_ID 		= "mosque_id";
	private static final String COUNT	 		= "count";
	private static final String ADDRESS	 		= "address";
	private static final String PHONES	 		= "phones";
	private static final String PLACE_EN	 	= "place_en";
	private static final String TOKEN	 		= "token";
	private static final String TYPE	 		= "type";
	private static final String GENDER			 	= "gender";
	
	private IFragmentNotifier fragmentNotifier;
	
	private static JDManager mInstance = null;
	private static SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private JSONParser jsonParser;
	private Context mContext;
	
	private Mosque selectedMosque;
	private Da3wa selectedDa3wa;
	
	private ArrayList<Place> places;
	private ISearchListener searchListener;
	
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
					janeza.setGender(jObj.getString(GENDER));

					Log.i(TAG, janeza.toString());

					ArrayList<Janeza> list = hashJana2z.get(janeza.getMosque());
					if(list == null)
						list = new ArrayList<Janeza>();

					list.add(janeza);
					hashJana2z.put(janeza.getMosque(), list);

				} catch (JSONException e) {
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

	public ArrayList<Mosque> getAllJanaez() {
		
		HashMap<String, ArrayList<Janeza>> hashJana2z = new HashMap<String, ArrayList<Janeza>>();
		
		ArrayList<Mosque> mosques = new ArrayList<Mosque>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z);
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Janeza janeza = new Janeza();
				janeza.setId(Integer.valueOf(jObj.getString(ID)));
				janeza.setTitle(jObj.getString(TITLE));
				janeza.setMosque(jObj.getString(MOSQUE));
				janeza.setPlace(jObj.getString(PLACE));
				janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
				janeza.setLatitude(jObj.getString(LAT));
				janeza.setLongitude(jObj.getString(LONG));
				janeza.setGender(jObj.getString(GENDER));
				
				Log.i(TAG, janeza.toString());
				
				ArrayList<Janeza> list = hashJana2z.get(janeza.getMosque());
				if(list == null)
					list = new ArrayList<Janeza>();
				
				list.add(janeza);
				hashJana2z.put(janeza.getMosque(), list);
				
			} catch (JSONException e) {
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
	
	public ArrayList<Janeza> getJanaezByMosque(int mosqueID) {

		ArrayList<Janeza> janaez = new ArrayList<Janeza>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_JANA2Z + "mosques?mosque=" + mosqueID);
		Log.i(TAG, "URL : " + URL_MOSQUES + "?mosque=" + mosqueID);
		if (array != null) 
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject jObj = array.getJSONObject(i);
					Janeza janeza = new Janeza();
					janeza.setId(Integer.valueOf(jObj.getString(ID)));
					janeza.setTitle(jObj.getString(TITLE));
					janeza.setMosque(jObj.getString(MOSQUE));
					janeza.setPlace(jObj.getString(PLACE));
					janeza.setPrayerTime(jObj.getString(PRAYER_TIME));
					janeza.setLatitude(jObj.getString(LAT));
					janeza.setLongitude(jObj.getString(LONG));
					janeza.setGender(jObj.getString(GENDER));

					Log.i(TAG, janeza.toString());


					janaez.add(janeza);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		return janaez;
	}
	
	
	public ArrayList<Mosque> getMosquesByPlace(int placeId) {
		ArrayList<Mosque> mosques = new ArrayList<Mosque>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_MOSQUES.concat("/place/?place=".concat(String.valueOf(placeId))));
		if (array != null) 
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Mosque mosque = new Mosque();
				mosque.setId(Integer.valueOf(jObj.getString(MOSQUE_ID)));
				mosque.setCount(Integer.valueOf(jObj.getString(COUNT)));
				mosque.setTitle(jObj.getString(MOSQUE));
				mosque.setPlace(jObj.getString(PLACE));
				
				Log.i(TAG, mosque.toString());
				
				mosques.add(mosque);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return mosques;
	}
	
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
				place.setPlaceEn(jObj.getString(PLACE_EN));
				
				Log.i(TAG, place.toString());
				
				places.add(place);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(getPlaces() == null){
			setPlaces(places);
		}
		
		return places;
	}
	
	public ArrayList<Address> getAllAddresses() {
		ArrayList<Address> addresses = new ArrayList<Address>();
		JSONArray array = jsonParser.getJSONFromUrl(URL_ADDRESSES);
		if (array != null) 
		
			for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jObj = array.getJSONObject(i);
				Address address = new Address();
				address.setAddress(jObj.getString(ADDRESS));
				address.setPhones(jObj.getString(PHONES));
				
				Log.i(TAG, address.toString());
				
				addresses.add(address);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return addresses;
	}
	
	public boolean registerPush(String regID) {
		boolean isOK = false;
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TOKEN, regID));
		params.add(new BasicNameValuePair(TYPE, "android"));
		
		GcmResponse result = jsonParser.getGcmResponse(URL_PUSH_REGISTER, params);
		if (result != null) 
		{
			Log.i(TAG, "result " + result.isSuccess() + "\n" + result.getMessage());
			isOK = result.isSuccess();

		}
		
		return isOK;
	}

	private String deviceToken;
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public void updateDeviceToken(String token){
		setDeviceToken(token);
		new AsyncTask<String, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(String... params) {
				return registerPush(params[0]);
			}
		}.execute(token);
	}
	
	
	public boolean refreshLocalityIfPossible(String locality){

		Log.v(TAG, ">>> locality " + locality);
		if(getPlaces() != null)
			for(Place place : getPlaces()){
				Log.v(TAG, ">>> Place EN " + place.getPlaceEn());
				if(place.getPlaceEn().equalsIgnoreCase(locality)){
					setSelectedPlace(place);
					return true;
				}
			}
		
		return false;
	}
	
	public void setNotificationSettings(boolean isRegistered){
		editor.putBoolean("notifs", isRegistered);
		editor.commit();
	}
	
	public boolean isNotificationEnabled(){
		return settings.getBoolean("notifs", false);
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
		
		Place selectedPlace = null;
		int placeID = settings.getInt("place_id", -1);
		if(placeID != -1){
			selectedPlace = new Place();
			selectedPlace.setId(placeID);
			selectedPlace.setTitle(settings.getString("place_title", null));
			selectedPlace.setPlaceEn(settings.getString("place_en", null));
		}
		
		return selectedPlace;
	}

	public void setSelectedPlace(Place selectedPlace) {
//		this.selectedPlace = selectedPlace;
		
		editor.putInt("place_id", selectedPlace.getId());
		editor.putString("place_title", selectedPlace.getTitle());
		editor.putString("place_en", selectedPlace.getPlaceEn());
		
		editor.commit();
		
	}

	public Da3wa getSelectedDa3wa() {
		return selectedDa3wa;
	}

	public void setSelectedDa3wa(Da3wa selectedDa3wa) {
		this.selectedDa3wa = selectedDa3wa;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}

	public ISearchListener getSearchListener() {
		return searchListener;
	}

	public void setSearchListener(ISearchListener searchListener) {
		this.searchListener = searchListener;
	}
}