package com.example.hi.reminder;

import java.util.*;


public class DateCal {

    int yr,mn,da;
    public DateCal(int yr,int mn,int da)
    {
        this.yr=yr;
        this.mn=mn;
        this.da=da;
    }
    //@TargetApi(Build.VERSION_CODES.N)

    public long calculate() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2= Calendar.getInstance();
        c2.set(Calendar.YEAR,yr);
        c2.set(Calendar.MONTH,mn);
        c2.set(Calendar.DAY_OF_MONTH,da);

        long m1 = c1.getTimeInMillis();
        long m2 = c2.getTimeInMillis();

        long diff = m2 - m1;
        long ans = diff / (24 * 60 * 60 * 1000);

        return  ans;
    }
}
