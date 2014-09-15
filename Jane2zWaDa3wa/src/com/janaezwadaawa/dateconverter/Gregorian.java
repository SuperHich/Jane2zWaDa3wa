package com.janaezwadaawa.dateconverter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.janaezwadaawa.entity.GHTDate;
import com.janaezwadaawa.entity.HGDate;



//public class GregorianCalendar {
//
//	public final static String[] WEEKDAYS = { "", "Sunday", "Monday", "Thursday", "Wednesday", "Thursday", "Friday", "Saturday" };
//	public final static String[] MONTHS = { "", "January", "February", "March", "",
//		"April", "May", "June", "July", "August", "September", "October", "November", "December"};
//	
////	function HijriToGeorgian(Year, Month, Day: integer): MultiDate;
////	var
////	  y, m, d: integer;
////	  Jd: integer;
////	  F: integer;
////	  n: integer;
////	  i: integer;
////	  j: integer;
////	  k: integer;
////	begin
////
////
////	  d := Day;
////	  m := Month;
////	  y := Year;
////
////	  Jd := intPart((11 * y + 3) / 30) +
////	        354 * y + 30 * m -
////	        intPart((m - 1) / 2) +
////	        d + 1948440 - 385;
////
////	  if (Jd > 2299160) then
////	  begin
////	    F := Jd + 68569;
////	    n := intPart((4 * F) / 146097);
////	    F := F - intPart((146097 * n + 3) / 4);
////	    i := intPart((4000 * (F + 1)) / 1461001);
////	    F := F - intPart((1461 * i) / 4) + 31;
////	    j := intPart((80 * F) / 2447);
////	    d := F - intPart((2447 * j) / 80);
////	    F := intPart(j / 11);
////	    m := j + 2 - 12 * F;
////	    y := 100 * (n - 49) + i + F;
////	  end
////	  else
////	  begin
////	    j := Jd + 1402;
////	    k := intPart((j - 1) / 1461);
////	    F := j - 1461 * k;
////	    n := intPart((F - 1) / 365) - intPart(F / 1461);
////	    i := F - 365 * n + 30;
////	    j := intPart((80 * i) / 2447);
////	    d := i - intPart((2447 * j) / 80);
////	    i := intPart(j / 11);
////	    m := j + 2 - 12 * i;
////	    y := 4 * k + n + i - 4716;
////	  end;
////
////	  result.DayH := Day;
////	  result.MonthH := Month;
////	  result.YearH := Year;
////	  result.MonthNameH := HigriMonthName(result.MonthH);
////
////	  result.DayG := d;
////	  result.MonthG := m;
////	  result.YearG := y;
////
////	  if result.YearG > 0 then
////	    result.Date := EncodeDate(y, m, d);
////
////	  result.MonthNameG := GeorgianMonthName(result.MonthG);
////	  result.DayOfWeek := DayOfTheWeekG(result.Date);
////	  result.DayName := GetDayName(result.DayOfWeek);
////
////	end;
//	
//	public static ConverterEntity HijriToGeorgian(int Year, int Month, int Day){
//		
//		int y, m, d, Jd, F, n, i, j, k;
//		
//		d = Day;
//		m = Month;
//		y = Year;
//		
//		Jd = (int)((11 * y + 3) / 30) +
//		        354 * y + 30 * m -
//		        (int)((m - 1) / 2) +
//		        d + 1948440 - 385;
//
//		if(Jd > 2299160)
//		{
//			F = Jd + 68569;
//			n = (int)((4 * F) / 146097);
//			F = F - (int)((146097 * n + 3) / 4);
//			i = (int)((4000 * (F + 1)) / 1461001);
//			F = F - (int)((1461 * i) / 4) + 31;
//			j = (int)((80 * F) / 2447);
//			d = F - (int)((2447 * j) / 80);
//			F = (int)(j / 11);
//			m = j + 2 - 12 * F;
//			y = 100 * (n - 49) + i + F;
//		}
//		else{
//			j = Jd + 1402;
//			k = (int)((j - 1) / 1461);
//			F = j - 1461 * k;
//			n = (int)((F - 1) / 365) - (int)(F / 1461);
//			i = F - 365 * n + 30;
//			j = (int)((80 * i) / 2447);
//			d = i - (int)((2447 * j) / 80);
//			i = (int)(j / 11);
//			m = j + 2 - 12 * i;
//			y = 4 * k + n + i - 4716;
//		}
//		
//		ConverterEntity result = new ConverterEntity();
//		result.setDayH(Day);
//		result.setMonthH(Month);
//		result.setYearH(Year);
////		result.setDayNameH(HijriCalendar.WEEKDAYS[result.getDayH()]);
//		result.setMonthNameH(HijriCalendar.MONTHS[result.getMonthH()]);
////		result.setMonthNameH(HigriMonthName(result.getMonthH());
//
//		result.setDayG(d);
//		result.setMonthG(m);
//		result.setYearG(y);
//
//		if (result.getYearG() > 0) 
//		{
//			Calendar c = Calendar.getInstance();
//			c.set(y, m, d);
////			result.Date = EncodeDate(y, m, d);
//			result.setDayG(c.get(Calendar.DAY_OF_WEEK));
//			result.setDayNameG(WEEKDAYS[result.getDayG()]);
//			result.setDayNameH(HijriCalendar.WEEKDAYS[result.getDayG()]);
//		}
//		
//		result.setMonthNameG(MONTHS[result.getMonthG()]);
//		
////		result.MonthNameG = GeorgianMonthName(result.MonthG);
////		result.DayOfWeek := DayOfTheWeekG(result.Date);
////		result.DayName := GetDayName(result.DayOfWeek);
//		
//		return result;
//	}
//	
//}

public class Gregorian extends Object {

	public final static String[] WEEKDAYS = { "", "Sunday", "Monday", "Thursday", "Wednesday", "Thursday", "Friday", "Saturday" };
	public final static String[] MONTHS = { "", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	final static double YR[]  =  {0.970224,0.970203,1.0306898201,1.0307121293};
	int gregorian = 0; // g=0, j = 1
	/*#define YR  0.970203 
	Use this to get Julian years; other
	calculations are the same _except_
	that Julian leap years were different: 
	you'll have to modify that part of the code */

	static final double  YADD = 622.540000;  /* The Hijri era began on July 16th 622AD*/

	// void calend();

	/*____________________________________
	This is a stand-alone version;  you can include the functions
	calend and isleap in any other program, passing a Hijri date in
	the usual numerical form dd/mm/yyyy:  represented here as
	Hd, Hm, Hy.   Calend returns (prints out) the corresponding
	Gregorian Gd, Gm, Gy.
	____________________________________*/

	public static GHTDate HijriToGregorian(HGDate hdate)
	{
	String s;
	int hd, hm, hy =0, hg=0;
	     /*   printf("%s", "I will convert a Hijri date to a Gregorian one for you\n\n");
	        printf("%s","Please enter the day: ");
	        hd = atoi(gets(s));
	        printf("\n\n%s","Now enter the month: ");
	        hm = atoi(gets(s));
	        printf("\n\n%s","Now enter the year (four digits, please): ");
	        hy = atoi(gets(s));
	                */
	        return calend(hdate);
	}

	static GHTDate  calend(HGDate Hdmy) {
	        int Hd, Hm, Hy, Hg;         /*These are the starting points*/
	        Double q, r;
	        double  p,  x = 0.0;
	        int Gd, Gm, Gy = 0;     /*These are the end-points*/
	        int i, j; /*j holds the return from function isleap();
	                                it should be either 1 or 0*/

	        int gdoy;       /*This is the ordinal number 
	                                of a day in the Gregorian year*/

	        int hsupp=0;      /*An intermediate number: the number
	                                of hdays in the whole months elapsed
	                                since the hNew Year*/

	        /*int leap;     To store whether the gyear is a leap year or not*/

	        /*Here is a useful array from Kernighan and Ritchie, p104*/
	        int gday[][] = {
	        {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
	        {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}
	        };
			Hd = Hdmy.getDay();
			Hm = Hdmy.getMonth();
	  		Hy = Hdmy.getYear() - 1;
	  		Hg = Hdmy.getGregorian();
	       /*Calculate the number of hdays elapsed in the hyear
	        since the hyear began*/

	        switch (Hm){
	                case 1        : {     hsupp = 0 + Hd;
	                                                break;}
	                case 2        : {     hsupp = 30 + Hd;
	                                                break;}
	                case 3        : {     hsupp = 59 + Hd;
	                                                break;}
	                case 4        : {     hsupp = 89 + Hd;
	                                                break;}
	                case 5        : {     hsupp = 118 + Hd;
	                                                break;}
	                case 6        : {     hsupp = 148 + Hd;
	                                                break;}
	                case 7        : {     hsupp = 177 + Hd;
	                                                break;}
	                case 8        : {     hsupp = 207 + Hd;
	                                                break;}
	                case 9        : {     hsupp = 236 + Hd;
	                                                break;}
	                case 10       : {     hsupp = 266 + Hd;
	                                                break;}
	                case 11       : {     hsupp = 295 + Hd;
	                                                break;}
	                case 12       : {     hsupp = 325 + Hd;
	                                                break;}
	                case 0        : {     hsupp = 0 + Hd;
	                                                break;}
	                }

	/*____________________________________

	Calculate the Gy and the gdoy of the hNew Year

	This procedure will find the day of the
	Gregorian year on which the Islamic New Year's
	Day fell.

	The procedure with paper and pencil is:

	        multiply the Islamic year by 0.970224;

	        add 622.54 (The Islamic era began on day 54 of
	                                                        Gregorian year 622);

	That gives you an answer such as 1995.330.
	The Gregorian year is 1995; you can calculate the 
	Gregorian day the Islamic New Year began by
	multiplying .330 by 365, and taking
	the whole number as the Gregorian ordinal
	day.

	____________________________________*/



	        x = (Hy * YR[Hg]) + YADD;			// YR Gregorian or Julian 
	        Gy = (int) x;                         /*Assigning a float to an int
	                                                truncates the fractional part, 
	                                                and gives the Gregorian year*/
	     //   p =  modf( x, q);  /* q is addr of the integer part of x and
	          p = x - Gy;        //                           gets discarded. CHECK !!!!! mdf change
	                              //                  p  now contains the fractional part of x */
	        gdoy = (int) (p * 365);       /*So multiply that by 365, and assign the
	                                                float to an int, getting the whole number*/

	/*____________________________________
	We now have the Gregorian year (Gy) and
	day-of-the-year (gdoy) on which the Hijri New Year began.
	We add to that, the number of days 
	elapsed in the Hijri year (hsupp -from
	the earlier switch which includes the number of days
	that have elapsed since the current Hijri month
	began (Hd)).  That gives the number of days after
	the New Year.
	To calculate the Gregorian day corresponding
	to the nth day after the Hijri new year's day:

	        If it is a leap year, and gdoy is greater than
	        366, add 1 to Gy, and subtract 366 from gdoy.
	        
	        If it is not a leap year, and gdoy is greater than 365,
	        add 1 to Gy and subtract 365 from gdoy.
	        
	        Otherwise do nothing.
	        
	        And in all cases then go on to work out
	        the day of the month, using the gday array

	______________________________________*/

	        gdoy = gdoy + hsupp;

	        j=(isleap(Gy));
	        if(j == 1)
	        {
	                if(gdoy >= 366)
	                {
	                        Gy = Gy + 1;
	                        gdoy = gdoy -366;
	                }
	        }
	        else if(j == 0)
	        {
	                if(gdoy >= 365)
	                {
	                        Gy = Gy +1;
	                        gdoy = gdoy -365;
	                }
	        }

	        for (i = 1; gdoy > gday[j][i]; i++)
	        {
	        	gdoy -= gday[j][i];
	        }

	                Gm = i;
	                Gd = gdoy;

//	         printf("\n\nThe Gregorian date is day %d of month %d of %d\n", Gd, Gm, Gy);
	              GHTDate ghtDate = new GHTDate();
	              
	              GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
	              c.set(Gy, Gm-1, Gd);
	              int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
	              
	              ghtDate.setDayH(Hd);
	              ghtDate.setMonthH(Hm);
	              ghtDate.setYearH(Hdmy.getYear());
	              
	              ghtDate.setDayNameH(Hijri.WEEKDAYS[dayOfWeek]);
	              ghtDate.setMonthNameH(Hijri.MONTHS[Hm]);
	              
	              ghtDate.setDayG(Gd);
	              ghtDate.setMonthG(Gm);
	              ghtDate.setYearG(Gy);
	              
	              ghtDate.setDayNameG(WEEKDAYS[dayOfWeek]);
	              ghtDate.setMonthNameG(MONTHS[Gm+1]);
	              
//	              GHTDate amazighDate = Tamazight.GregorianToAmazigh(Gy, Gm, Gd);
//	              ghtDate.setDayT(amazighDate.getDayT());
//	              ghtDate.setMonthT(amazighDate.getMonthT());
//	              ghtDate.setYearT(amazighDate.getYearT());
//	              ghtDate.setDayNameT(amazighDate.getDayNameT());
//	              ghtDate.setMonthNameT(amazighDate.getMonthNameT());
	              
	              return ghtDate;
//	              return new HGDate(Gd, Gm, Gy);
	}

	/*Work out whether the year is leap or not,
	and return 1 if it is not, 2 if it is*/

	static int isleap(int year)
	{
	        int leap;

	        if(year%4 == 0 && year%100 != 0 || year%400 == 0) leap = 1;
	        else leap = 0;
	        return(leap);
	}

}
