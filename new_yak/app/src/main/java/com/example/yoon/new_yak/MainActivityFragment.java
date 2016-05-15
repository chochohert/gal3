package com.example.yoon.new_yak;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListViewAdapter_main Yak_adapter;
    private ListView Yak_Listview;

    public MainActivityFragment() {

    }

    void setContent(){
        Yak_Listview=(ListView) getActivity().findViewById(R.id.Yak_listview);
        Yak_adapter=new ListViewAdapter_main(getContext());
        Yak_Listview.setOnItemClickListener(this);
        String time[]={"09:00","12:00","17:00"};
        ListData date=new ListData("아침 약","2",time,"2016/05/07");
        Yak_adapter.addItem(date);
        Yak_adapter.notifyDataSetChanged();


        Yak_Listview.setAdapter(Yak_adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setContent();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListData data= (ListData) Yak_Listview.getAdapter().getItem(position);
        Toast.makeText(getContext(),data.getTitle(),Toast.LENGTH_SHORT).show();
    }
}
