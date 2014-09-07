package com.janaezwadaawa.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class JDFonts {
	
	private static Typeface bdr_gr_2;
	
	public static void Init(Context context){
		bdr_gr_2  				= Typeface.createFromAsset(context.getAssets(), "fonts/bdr_gr_2.ttf");
	}

	public static Typeface getBDRFont() {
		return bdr_gr_2;
	}
}
