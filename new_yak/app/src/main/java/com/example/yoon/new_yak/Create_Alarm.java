package com.example.yoon.new_yak;

/**
 * Created by yoon on 2016-05-07.
 */

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;



public class Create_Alarm extends Fragment{

    private ListView content_alarm;
    private ListviewAdapter_content_alarm adapterContetAlarm;

    void setContent(){
        adapterContetAlarm=new ListviewAdapter_content_alarm(getContext(),getActivity());
        content_alarm=(ListView) getActivity().findViewById(R.id.alarm_content_listview);
        content_alarm.setAdapter(adapterContetAlarm);
        adapterContetAlarm.addItem(0);
        adapterContetAlarm.addItem(1);
        adapterContetAlarm.addItem(2);
        adapterContetAlarm.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // getActivity().requestWindowFeature(Window.FEATURE_ACTION_BAR);
        return inflater.inflate(R.layout.alarm_new, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setContent();
        super.onViewCreated(view, savedInstanceState);
    }
}
