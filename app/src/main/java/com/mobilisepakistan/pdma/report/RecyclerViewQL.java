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

import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.RecycleviewBinding;
import com.mobilisepakistan.pdma.global.QuickLinks;
import com.mobilisepakistan.pdma.global.emrConacts;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewQL extends AppCompatActivity {

    RecycleviewBinding binding ;
    RecyclerViewCustomAdapterQL mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    
    ArrayList<String> listlinks;
    ArrayList<String> arrayListheader;
    String sHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.recycleview);
        //list=(ArrayList<String>) getIntent().getSerializableExtra("mylist");
        arrayListheader= QuickLinks.gettext();
        listlinks=QuickLinks.getlinks();

        sHeader="IMPORTANT LINKS";
        binding.header.setText(sHeader);

        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) RecyclerViewQL.this).finish();
            }
        });




     //   Collections.sort(list);


        mLayoutManager = new LinearLayoutManager(this);
        binding.recycleview.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewCustomAdapterQL(this, listlinks,arrayListheader);
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
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : arrayListheader) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mAdapter.filterList(filterdNames);
    }




}

class  RecyclerViewCustomAdapterQL extends RecyclerView.Adapter {

    Context mContext;
    List<String> mListlink;

    List<String> arylistheade;
    public RecyclerViewCustomAdapterQL(Context context, List<String> listlinks,List<String> listheader ){
        mContext = context;
        mListlink = listlinks;

        arylistheade=listheader;
    }

    // filter

    public void filterList(ArrayList<String> filterdNames) {
//        this.arylistheade = filterdNames;
//        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleviewdesignql, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }




    Intent data = new Intent();
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;

        vh.txtheader.setText(arylistheade.get(position));
       vh.txtrcv.setText(mListlink.get(position));

//        vh.txtheader.setText(hList.get(position));

//
//        try {
//            vh.txtrcv.setText(arylistheade.get(position));
//        }
//        catch (IndexOutOfBoundsException e)
//        {
//            String a="hiii";
//        }
        vh.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtrcv = vh.txtrcv.getText().toString();




                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txtrcv));
                mContext.startActivity(browserIntent);

//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"+txtrcv));
//            //    callIntent.setData(Uri.parse(txtrcv));
//                mContext.startActivity(intent);



            }
        });
    }



    @Override
    public int getItemCount() {
        return mListlink.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtrcv;
        public TextView txtheader;

        public ImageButton lv;

        public ViewHolder(View v) {
            super(v);
            txtrcv = (TextView) v.findViewById(R.id.txtlink);

            txtheader = (TextView) v.findViewById(R.id.txtrcv);

            lv = (ImageButton) v.findViewById(R.id.img);
        }
    }








}
