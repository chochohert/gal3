package com.example.yoon.new_yak;

import android.app.Activity;

;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by yoon on 2016-05-08.
 */
public class ListviewAdapter_content_alarm extends BaseAdapter implements View.OnClickListener,Listener_timeDialog.SetTimeSelectLisetener{

    private static final int ITEM_VIEW_TYPE_SET_DATE=1;
    private static final int ITEM_VIEW_TYPE_SET_TIME=0;
    private static final int ITEM_VIEW_TYPE_YAK_CONTENT=2;
    private static final int ITEM_VIEW_TYPE_MAX=3;
    private int cnt,minute,hour;
    private Context context;
    private LinearLayout time_zone;
    private String time;
    private FragmentActivity fragment;
    private Calendar cal=Calendar.getInstance();
    private Listener_timeDialog setTimeListener;
    private TextView first_time,second_time,third_time,freqency;

    public ArrayList<Alarm_ListData> alarm_listDatas=new ArrayList<>();

    public ListviewAdapter_content_alarm(Context context,FragmentActivity fragment){
        this.context=context;
        cnt=0;
        this.fragment=fragment;
    }

    @Override
    public int getCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        return alarm_listDatas.get(position).getType();
    }

    @Override
    public Object getItem(int position) {
        return alarm_listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context=parent.getContext();
        int viewType=getItemViewType(position);

        if(convertView==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Alarm_ListData data=alarm_listDatas.get(position);

            switch (viewType){
                case ITEM_VIEW_TYPE_SET_TIME :
                    convertView=inflater.inflate(R.layout.alarm_set_time,parent,false);

                    first_time=(TextView) convertView.findViewById(R.id.first_time_alarm);
                    second_time=(TextView) convertView.findViewById(R.id.second_time_alarm);
                    third_time=(TextView) convertView.findViewById(R.id.third_time_alarm);
                    first_time.setOnClickListener(this);
                    second_time.setOnClickListener(this);
                    third_time.setOnClickListener(this);
                    freqency=(TextView) convertView.findViewById(R.id.daily_freqency);
                    final TextView set_time_bnt=(TextView) convertView.findViewById(R.id.set_time_bnt);
                    TextView sub_t1=(TextView) convertView.findViewById(R.id.sub_title);
                    set_time_bnt.setOnClickListener(this);

                    time_zone=(LinearLayout) convertView.findViewById(R.id.time_zone);

                    break;
                case ITEM_VIEW_TYPE_SET_DATE :
                    convertView=inflater.inflate(R.layout.alarm_start_end_date,parent,false);

                    TextView start_date_bnt=(TextView) convertView.findViewById(R.id.star_date_bnt);
                    TextView end_date_bnt=(TextView) convertView.findViewById(R.id.end_date_bnt);
                    CheckBox isend=(CheckBox) convertView.findViewById(R.id.isend);

                    break;

                case ITEM_VIEW_TYPE_YAK_CONTENT:
                    convertView=inflater.inflate(R.layout.alarm_yak_refrence,parent,false);
                    TextView title=(TextView) convertView.findViewById(R.id.title_yak_layout_3);
                    EditText Yak_content=(EditText) convertView.findViewById(R.id.yak_content);
                    Yak_content.setBackgroundResource(R.drawable.edittext_bg);
                    break;
            }
        }
        return convertView;
    }

    public void addItem(int type){
        Alarm_ListData data=new Alarm_ListData();
        data.setType(type);
        alarm_listDatas.add(data);
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.set_time_bnt){
          /*  FragmentManager fragmentManager=(fragment).getSupportFragmentManager();
            TimeFragment dialog=new TimeFragment();
            FragmentTransaction ft= fragmentManager.beginTransaction();
            dialog.setListener(this);
            ft.add(dialog,"time_picker").commit();*/

            hour=cal.HOUR_OF_DAY;
            minute=cal.MINUTE;

            TimePickerDialog dlgtime=new TimePickerDialog(context,android.R.style.Theme_Holo_Light,setTimeListener,hour,minute,false);
            dlgtime.setButton(TimePickerDialog.BUTTON_POSITIVE, "확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String str="";

                    if(cnt==0){
                        first_time.setText(time);

                    }else if(cnt==1){
                        second_time.setText(time);

                    }else if(cnt==2){
                        third_time.setText(time);
                    }
                    cnt++;
                }
            });
            dlgtime.show();
        }
    }

    @Override
    public void returnTime(String time) {
        this.time=time;
    }
}

class Alarm_ListData{

    private int type;

     public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}


