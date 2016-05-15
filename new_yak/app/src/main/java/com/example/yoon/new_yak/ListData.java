package com.example.yoon.new_yak;

/**
 * Created by yoon on 2016-05-07.
 */
public class ListData {
    private String title,time;
    private String [] frequency;
    private String start_date;
    private int len;

    public ListData(String title,String time,String []daily_frequency,String start_date){
        this.time=time;
        this.title=title;
        this.len=daily_frequency.length;
        this.frequency=new String[len];
        for(int i=0;i<len;i++){
            this.frequency[i]=daily_frequency[i];
        }
        this.start_date=start_date;
    }

    public ListData(){

    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getDaily_frequency() {
        return frequency;
    }

    public void setDaily_frequency(String[] daily_frequency) {
        this.frequency = daily_frequency;
    }

    public int getsize(){
        return len;
    }

}
