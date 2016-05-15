package com.example.yoon.new_yak;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;


/**
 * Created by yoon on 2016-05-07.
 */
public class TimeFragment extends DialogFragment {

   /* int hour;
    int myminute;
    Calendar cal=Calendar.getInstance();
    private TimePickerDialogListener timeSetListener;
    private String time;
    TimePickerDialog dialog;
    private SetTimeSelectLisetener mylistner;
    private TextView ok_bnt,cancle_bnt;
    private TimePicker timePicker;

   public void setListener(SetTimeSelectLisetener listener) {
        mylistner = listener;
    }

    static interface SetTimeSelectLisetener{
        void returnTime(String time);
    }



    public Dialog onCreateDialog(Bundle savedInstanceState) {

        hour=cal.HOUR_OF_DAY;
        myminute=cal.MINUTE;


        timePicker=(TimePicker) getView().findViewById(R.id.Yak_timepicker);






        dialog=new TimePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_NoActionBar,timeSetListener,hour,myminute,false);
        return dialog;

    }

    private class TimePickerDialogListener implements TimePickerDialog.OnTimeSetListener{

        private int hour,minute;


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            this.hour=hourOfDay;
            this.minute=minute;

            String date="";

            if(hour<12){
                date="오전";
            }else{
                date="오후";
                hour=hour-12;
            }

           time=String.valueOf(date+"\t"+hour+":"+myminute);
        }
    }*/


}
