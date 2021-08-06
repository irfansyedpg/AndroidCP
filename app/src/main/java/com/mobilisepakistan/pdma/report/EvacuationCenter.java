package com.mobilisepakistan.pdma.report;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilisepakistan.pdma.MapsMarkerActivity;
import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.RecycleviewBinding;
import com.mobilisepakistan.pdma.global.emrConacts;
import com.mobilisepakistan.pdma.global.evacCenters;

import java.util.ArrayList;
import java.util.List;


public class EvacuationCenter extends AppCompatActivity {

    RecycleviewBinding binding ;
    EvacuationCenterCustomAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<String> district;
    ArrayList<String> arrayListall;
    ArrayList<String> tehsil;
    ArrayList<String> centername;
    ArrayList<String> location;
    ArrayList<String> gps;
    String sHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.recycleview);
        //location=(ArrayList<String>) getIntent().getSerializableExtra("mylist");
        location= evacCenters.getlocaiton();
        centername=evacCenters.getname();
        district=evacCenters.getdistrict();
        tehsil=evacCenters.gettehsil();
        gps=evacCenters.getGPS();

        arrayListall=new ArrayList<String>();

     for(int position=0;position<district.size();position++)
     {
         arrayListall.add("DISTRICT: "+district.get(position) + "\n"+ "\n"
                 + "TEHSIL: "+tehsil.get(position) + "\n"+ "\n"
                 + "LOC: "+location.get(position) + "\n"+ "\n"
                 + "CENTER: "+centername.get(position) + "\n"+ "\n"
                 + "GPS"+gps.get(position) );

       //  arrayListall.add(district.get(position) );

     }


        sHeader=getString(R.string.s_ec);
        binding.header.setText(sHeader);

        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) EvacuationCenter.this).finish();
            }
        });


     //   Collections.sort(location);


        mLayoutManager = new LinearLayoutManager(this);
        binding.recycleview.setLayoutManager(mLayoutManager);


        mAdapter = new EvacuationCenterCustomAdapter(this, district,tehsil,location,centername,gps,arrayListall);
        binding.recycleview.setAdapter(mAdapter);



        // RecycleView Text Search View Filter

        ((EditText) findViewById(R.id.ed_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


    }

    // filter method

    private void filter(String text) {
        //new array location that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : arrayListall) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered district
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered district
        mAdapter.filterList(filterdNames);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



}

class  EvacuationCenterCustomAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<String> mDistrict;
    List<String> arylistall;
    List<String> mTehsil;
    List<String> mLocation;
    List<String> mName;
    List<String> mGPS;

    public EvacuationCenterCustomAdapter(Context context, List<String> lstDistrict,List<String> lstTehsil,List<String> listlocation , List<String> listName, List<String> listGPS,List<String> alllist  ){
        mContext = context;

      mDistrict=lstDistrict;
      mTehsil=lstTehsil;
      mLocation=listlocation;
      mName=listName;
      mGPS=listGPS;
        arylistall=alllist;



    }

    // filter

    public void filterList(ArrayList<String> filterdNames) {
     this.arylistall = filterdNames;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleviewdesignevacotion, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    Intent data = new Intent();
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;

//        vh.txtdistic.setText("DISTRICT: "+mDistrict.get(position) + "\n"+ "\n"
//               + " TEHSIL: "+mTehsil.get(position) + "\n"+ "\n"
//               + " LOC: "+mLocation.get(position) + "\n"+ "\n"
//               + " CENTER: "+mName.get(position) + "\n"+ "\n"
//               + "GPS"+mGPS.get(position) );


        try {
            vh.txtdistic.setText(arylistall.get(position));
        }
        catch (IndexOutOfBoundsException e)
        {



            String a="hiii";
        }






//        vh.txttehsil.setText("TEHSIL: "+mTehsil.get(position));
//        vh.txtlocaiton.setText("LOC: "+mLocation.get(position));
//        vh.txtname.setText("CENTER: "+mName.get(position));
//        vh.txtgps.setText(mGPS.get(position));


        vh.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gps = vh.txtdistic.getText().toString();
                String centername=vh.txtdistic.getText().toString();

                String[] ccnamear=centername.split("CENTER: ");
                String[] cc2name=ccnamear[1].split("GPS");
                centername=cc2name[0];


                String[] all =gps.split("GPS");
                String neww=all[1];
                String[]gpslst=neww.split(",");
                String latt=gpslst[0];
                String longg=gpslst[1];


                Intent intt=new Intent(mContext, MapsMarkerActivity.class);
                intt.putExtra("latt",latt);
                intt.putExtra("longg",longg);
                intt.putExtra("title",centername);
                mContext.startActivity(intt);






            }
        });
    }



    @Override
    public int getItemCount() {
        return mDistrict.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtdistic;
//        public TextView txttehsil;
//        public TextView txtlocaiton;
//        public TextView txtname;
//        public TextView txtgps;
        public ImageButton lv;

        public ViewHolder(View v) {
            super(v);
            txtdistic = (TextView) v.findViewById(R.id.txthdist);
//            txttehsil = (TextView) v.findViewById(R.id.txtteshil);
//            txtlocaiton = (TextView) v.findViewById(R.id.txtlocation);
//            txtname = (TextView) v.findViewById(R.id.txtname);
//            txtgps = (TextView) v.findViewById(R.id.txtgps);
            lv=(ImageButton)v.findViewById(R.id.img);

        }
    }








}
