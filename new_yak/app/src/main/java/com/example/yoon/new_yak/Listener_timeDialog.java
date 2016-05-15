package com.example.yoon.new_yak;


import android.app.TimePickerDialog;
import android.widget.TimePicker;

/**
 * Created by yoon on 2016-05-08.
 */
public class Listener_timeDialog implements TimePickerDialog.OnTimeSetListener {

        private int hour,minute;
        private SetTimeSelectLisetener mylistner;
        private String time;

       public void setListener(SetTimeSelectLisetener listener) {
            mylistner = listener;
        }

       public static interface SetTimeSelectLisetener{
            void returnTime(String time);
        }

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

            time=String.valueOf(date+"\t"+hour+":"+this.minute);
            mylistner.returnTime(time);
        }


}
