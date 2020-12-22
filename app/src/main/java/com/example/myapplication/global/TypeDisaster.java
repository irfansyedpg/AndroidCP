package com.example.myapplication.global;

import java.util.ArrayList;

public class TypeDisaster {

     static  ArrayList<String> listDisaster=new  ArrayList<String>();

    public static ArrayList<String> getDisaster() {


        listDisaster.add("Flood");
        listDisaster.add("Earthquakes");
        listDisaster.add("Extreme Temperatures");
        listDisaster.add("Cyclones");
        listDisaster.add("Drought");
        listDisaster.add("Disease Epidemics");
        listDisaster.add("Pandemics");


        return listDisaster;

    }


}
