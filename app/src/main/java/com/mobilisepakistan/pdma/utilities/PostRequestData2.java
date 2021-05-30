package com.mobilisepakistan.pdma.utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PostRequestData2 {

    public static String getData(HashMap<String, List<String>> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String, List<String>> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");


            List<String> LstValu=entry.getValue();

            for(int i=0;i<LstValu.size();i++){

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(LstValu.get(i), "UTF-8"));
            }
        }   // end of for-loop

        return result.toString();
    }
}


