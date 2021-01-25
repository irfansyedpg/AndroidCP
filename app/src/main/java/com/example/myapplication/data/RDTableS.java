package com.example.myapplication.data;

/**
 * Created by Umeed-e-Nau on 12/24/2016.
 */
public class RDTableS {
    public static String TABLE_NAME = "RDTable";

    public static String getCreateQuery() {
        String query;
        query = "CREATE TABLE '" + TABLE_NAME + "' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, A1 TEXT, " +
                "A2 TEXT, B1 TEXT, B2 TEXT, C1 TEXT," +
                " C2 TEXT,C3 TEXT,C4 TEXT,D1 TEXT,D2 TEXT,D3 TEXT,D4 TEXT," +
                " D5 TEXT,E1 TEXT,E2 TEXT,E3 TEXT,E4 TEXT,I1 TEXT," +
                " I2 TEXT,I3 TEXT,L1 TEXT,L2 TEXT,L3 TEXT,M1 TEXT," +
                " N1 TEXT,upload_status TEXT)";
        return query;
    }
}
