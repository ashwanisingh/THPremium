package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.view.FontCache;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends BaseFragmentTHP {

    private CustomCalendarView calendarView;

    public static CalendarFragment getInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_calendar;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setCustomTypeface(FontCache.getTypeface(getResources().getString(R.string.FONT_FIRA_SANS_REGULAR), getActivity()));

        //Initialize calendar with date
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);

        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                if(mOnCalendarDateClickListener != null) {
                    mOnCalendarDateClickListener.OnEditionOptionClickListener(date);
                }
            }

            @Override
            public void onMonthChanged(Date date) {
//                SimpleDateFormat df = new SimpleDateFormat("MMM-yyyy");
//                Toast.makeText(getActivity(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.calendarParentLayout).setOnTouchListener((v, e)->{
            return true;
        });

    }

    private OnCalendarDateClickListener mOnCalendarDateClickListener;

    public void setOnCalendarDateClickListener(OnCalendarDateClickListener onCalendarDateClickListener) {
        mOnCalendarDateClickListener = onCalendarDateClickListener;
    }


    public interface OnCalendarDateClickListener {
        void OnEditionOptionClickListener(Date date);
    }
}
