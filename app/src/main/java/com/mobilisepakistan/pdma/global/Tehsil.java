package com.mobilisepakistan.pdma.global;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;


public class Tehsil {
    private static Multimap<String, String> data = HashMultimap.create();
    public static ArrayList<String> get(String district) {

        data.put("Peshawar","Peshawar");
        data.put("Peshawar","Town-1");
        data.put("Peshawar","Town-2");
        data.put("Peshawar","Town-3");
        data.put("Peshawar","Town-4");
        data.put("Peshawar","Peshawar");
        data.put("Charsadda ","Charsadda");
        data.put("Charsadda ","Shabqadar");
        data.put("Charsadda ","Tangay");
        data.put("Nowshera","Jahangira");
        data.put("Nowshera","Nowshera");
        data.put("Nowshera","Pabbi");
        data.put("Mohmand","Umber Utman Khel");
        data.put("Mohmand","Halim Zai");
        data.put("Mohmand","Pindiali");
        data.put("Mohmand","Pran Ghar");
        data.put("Mohmand","Safi");
        data.put("Mohmand","Upper Mohmand");
        data.put("Mohmand","Ekka Ghund");
        data.put("Khyber","Bara");
        data.put("Khyber","Jamrud");
        data.put("Khyber","Landi Kotal");
        data.put("Khyber","Mula Gori");
        data.put("Mardan","Mardan");
        data.put("Mardan","Takht Bhai");
        data.put("Mardan","Katlang");
        data.put("Mardan","Lund Khwar");
        data.put("Mardan","Rustam");
        data.put("Swabi","Lahor");
        data.put("Swabi","Razar");
        data.put("Swabi","Swabi");
        data.put("Swabi","Topi");
        data.put("Buner ","Daggar");
        data.put("Buner ","Ghara");
        data.put("Buner ","Khudu Khel");
        data.put("Buner ","Mandanr");
        data.put("Buner ","Chagarzai");
        data.put("Buner ","Gadizee");
        data.put("Upper Dir","Dir");
        data.put("Upper Dir","Sharingal");
        data.put("Upper Dir","Wari");
        data.put("Swat","Babuzai");
        data.put("Swat","Barikot");
        data.put("Swat","Behrain");
        data.put("Swat","Charbagh");
        data.put("Swat","Kabal");
        data.put("Swat","Khwaza Khela");
        data.put("Swat","Matta");
        data.put("Shangla","Alpurai");
        data.put("Shangla","Besham");
        data.put("Shangla","Puran");
        data.put("Shangla","Chakesar");
        data.put("Malakand","Sam Rani Zai");
        data.put("Malakand","Swat Rani Zai");
        data.put("Lower Dir","Adenzai");
        data.put("Lower Dir","Lal Qilla");
        data.put("Lower Dir","Samarbagh");
        data.put("Lower Dir","Timergara");
        data.put("Chitral Lower","Chitral");
        data.put("Chitral Upper","Mastuj");
        data.put("Bajaur","Bar Chamer Kand");
        data.put("Bajaur","Barang");
        data.put("Bajaur","Khar");
        data.put("Bajaur","Loe Mamund");
        data.put("Bajaur","Wara Mamund");
        data.put("Bajaur","Nawagai");
        data.put("Bajaur","Salarzai");
        data.put("Bajaur","Utman khel");
        data.put("Hangu","Hangu");
        data.put("Hangu","Tall");
        data.put("Karak","Banda Daud Shah");
        data.put("Karak","Karak");
        data.put("Karak","Takht-e-Nasrati");
        data.put("Kohat","Kohat");
        data.put("Kohat","Lachi");
        data.put("Kurram","Upper Kurram");
        data.put("Kurram","Central Kurram");
        data.put("Kurram","Lower Kurram");
        data.put("Orakzai","Upper Orakzai");
        data.put("Orakzai","Central Orakzai");
        data.put("Orakzai","Lower Orakzai");
        data.put("Orakzai","Ismail Zai");
        data.put("Mansehra","Balakot");
        data.put("Mansehra","Mansehra");
        data.put("Mansehra","Oghi");
        data.put("Mansehra","Baffa Pakhal");
        data.put("Mansehra","Darband");
        data.put("Abbottabad","Abbottabad");
        data.put("Abbottabad","Havelian");
        data.put("Battagram","Allai");
        data.put("Battagram","Battagram");
        data.put("Haripur","Ghazi");
        data.put("Haripur","Haripur");
        data.put("Haripur","Khanpur");
        data.put("Lower Kohistan","Bankad");
        data.put("Lower Kohistan","Pattan");
        data.put("Torghar","Judba");
        data.put("Torghar","Khander");
        data.put("Upper Kohistan","Dassu");
        data.put("Upper Kohistan","Kandia");
        data.put("Kolai Palas","Battera Kolai");
        data.put("Kolai Palas","Palas");
        data.put("Dera Ismail Khan","Dera Ismail Khan");
        data.put("Dera Ismail Khan","Deraban");
        data.put("Dera Ismail Khan","Kulachi");
        data.put("Dera Ismail Khan","Paharpur");
        data.put("Dera Ismail Khan","Paroa");
        data.put("Tank","Tank");
        data.put("South Waziristan","Birmal");
        data.put("South Waziristan","Ladha");
        data.put("South Waziristan","Makin");
        data.put("South Waziristan","Sararogha");
        data.put("South Waziristan","Serwekai");
        data.put("South Waziristan","Tiarza");
        data.put("South Waziristan","Toi khulla");
        data.put("South Waziristan","Wana");
        data.put("North Waziristan","Datta Khel");
        data.put("North Waziristan","Dossali");
        data.put("North Waziristan","Gharyum");
        data.put("North Waziristan","Ghulam Khan");
        data.put("North Waziristan","Mir Ali");
        data.put("North Waziristan","Miran Shah");
        data.put("North Waziristan","Razmak");
        data.put("North Waziristan","Shewa");
        data.put("North Waziristan","Spinwam");
        data.put("North Waziristan","Shawal");
        data.put("Lakki Marwat","Lakki Marwat");
        data.put("Lakki Marwat","Sarai Naurang");
        data.put("Bannu","Bannu");
        data.put("Bannu","Domel");
        data.put("Bannu","Kaki");




        ArrayList<String> list = new ArrayList<>();
        Collection<String> coll = data.get(district);
        for (String item : coll) {
            list.add(item);
        }


        return list;
    }

}
