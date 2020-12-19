package com.example.myapplication.global;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Tehsil {
    private static Multimap<String, String> data = HashMultimap.create();
    public static ArrayList<String> get(String district) {
        data.put("CHITRAL","CHITRAL");
        data.put("CHITRAL","MASTUJ");
        data.put("UPPER DIR","DIR");
        data.put("UPPER DIR","SHARINGAL");
        data.put("UPPER DIR","WARI");
        data.put("LOWER DIR","ADENZAI");
        data.put("LOWER DIR","LAL QILA");
        data.put("LOWER DIR","SAMARBAGH");
        data.put("LOWER DIR","TEMERGARA");
        data.put("SWAT","BABUZAI");
        data.put("SWAT","BARIKOT");
        data.put("SWAT","BEHRAIN");
        data.put("SWAT","CHARBAGH");
        data.put("SWAT","KABAL");
        data.put("SWAT","KHWAZA KHELA");
        data.put("SWAT","MATTA SHAMZAI");
        data.put("SHANGLA","ALPURI");
        data.put("SHANGLA","BESHAM");
        data.put("SHANGLA","PURAN");
        data.put("BUNER","DAGGAR/BUNER");
        data.put("BUNER","GAGRA");
        data.put("BUNER","KHADOKHAIL");
        data.put("BUNER","MANDAR");
        data.put("MALAKAND","SAM RANIZAI");
        data.put("MALAKAND","SWAT RANIZAI");
        data.put("KOHISTAN","PALAS");
        data.put("KOHISTAN","PATTAN");
        data.put("KOHISTAN","DASSU");
        data.put("KOHISTAN","KANDIA");
        data.put("MANSEHRA","BALAKOT");
        data.put("MANSEHRA","MANSEHRA");
        data.put("MANSEHRA","OGHI");
        data.put("TORGHAR","JUDBA");
        data.put("TORGHAR","KHANDER");
        data.put("BATAGRAM","BATAGRAM (BANNA)");
        data.put("BATAGRAM","ALLAI");
        data.put("ABBOTTABAD","ABBOTTABAD");
        data.put("ABBOTTABAD","HAVELIAN");
        data.put("HARIPUR","GHAZI");
        data.put("HARIPUR","HARIPUR");
        data.put("MARDAN","KATLANG");
        data.put("MARDAN","MARDAN");
        data.put("MARDAN","TAKHT BHAI");
        data.put("SWABI","LAHORE");
        data.put("SWABI","RAZAR");
        data.put("SWABI","SWABI");
        data.put("SWABI","TOPI");
        data.put("CHARSADDA","CHARSADDA");
        data.put("CHARSADDA","SHABQADAR");
        data.put("CHARSADDA","TANGI");
        data.put("PESHAWAR","PESHAWAR");
        data.put("NOWSHERA","JAHANGIRA");
        data.put("NOWSHERA","NOWSHERA");
        data.put("NOWSHERA","PABBI");
        data.put("KOHAT","KOHAT");
        data.put("KOHAT","LACHI");
        data.put("HANGU","HANGU");
        data.put("HANGU","TALL");
        data.put("KARAK","BANDA DAUD SHAH");
        data.put("KARAK","KARAK");
        data.put("KARAK","TAKHT-E-NASRATI");
        data.put("BANNU","BANNU");
        data.put("BANNU","DOMEL");
        data.put("LAKKI MARWAT","LAKKI MARWAT");
        data.put("LAKKI MARWAT","NAURANG");
        data.put("DERA ISMAIL KHAN","DERA ISMAIL KHAN");
        data.put("DERA ISMAIL KHAN","DARABAN");
        data.put("DERA ISMAIL KHAN","PAHARPUR");
        data.put("DERA ISMAIL KHAN","KULACHI");
        data.put("DERA ISMAIL KHAN","PAROA");
        data.put("TANK","TANK");


        ArrayList<String> list = new ArrayList<>();
        Collection<String> coll = data.get(district);
        for (String item : coll) {
            list.add(item);
        }


        return list;
    }

}
