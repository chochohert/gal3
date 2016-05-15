package com.example.yoon.new_yak;

/**
 * Created by yoon on 2016-05-07.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yoon on 2016-05-07.
 */
public class ListViewAdapter_main extends BaseAdapter {

    private Context Yak_context=null;
    private ArrayList<ListData> Yak_ListData=new ArrayList<>();

    public ListViewAdapter_main(Context context){
        this.Yak_context=context;
    }


    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title,String time,String start_date,String [] freqency) {

        ListData data=new ListData();
        data.setStart_date(start_date);
        data.setDaily_frequency(freqency);
        data.setTime(time);
        data.setTitle(title);

        Yak_ListData.add(data);
    }
    public void addItem(ListData data) {

        Yak_ListData.add(data);
    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Yak_holder holder;

        if (convertView == null) {

            holder = new Yak_holder();

            LayoutInflater inflater = (LayoutInflater) Yak_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_yak, parent,false);

            holder.first_time = (TextView) convertView.findViewById(R.id.first_time);
            holder.second_time = (TextView) convertView.findViewById(R.id.second_time);
            holder.third_time=(TextView) convertView.findViewById(R.id.third_time);
            holder.start_date=(TextView) convertView.findViewById(R.id.start_date);
            holder.yak_name=(TextView) convertView.findViewById(R.id.yak_name);
            holder.repeat_time=(TextView) convertView.findViewById(R.id.repeat_time);

            convertView.setTag(holder);
        }else{//캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다.

            holder=(Yak_holder) convertView.getTag();
        }


        holder.start_date.setText(Yak_ListData.get(position).getStart_date());
        holder.yak_name.setText(Yak_ListData.get(position).getTitle());
        holder.repeat_time.setText(Yak_ListData.get(position).getTime());

        for(int i=0;i<Yak_ListData.get(position).getsize();i++){
            if(i==0){
                holder.first_time.setText(Yak_ListData.get(position).getDaily_frequency()[i]);
            }else if(i==1){
                holder.second_time.setText(Yak_ListData.get(position).getDaily_frequency()[i]);
            }else{
                holder.third_time.setText(Yak_ListData.get(position).getDaily_frequency()[i]);
            }
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return Yak_ListData.size();
    }

    @Override
    public Object getItem(int position) {
        return Yak_ListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
class Yak_holder{

    public TextView first_time,second_time,third_time;
    public TextView yak_name;
    public TextView start_date;
    public TextView repeat_time;

}
