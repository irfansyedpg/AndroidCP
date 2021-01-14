package com.example.myapplication.global;

import java.util.List;

public class JsonDailySituationR {


        public List<String> Shift;
        public List<Integer> id;
        public List<String> datee;


    // Getter
    public List<String> getShift() {
        return Shift;
    }

    // Setter
    public void setShift(List<String> shift) {
        this.Shift = shift;
    }

    // Setter
    public void setId(List<Integer> id) {
        this.id = id;
    }


    // Getter
    public List<Integer> getId() {
        return id;
    }

    // Setter
    public void setDatee(List<String> date) {
        this.datee = date;
    }

    // Getter
    public List<String> getDatee() {
        return datee;
    }



}
