package com.mobilisepakistanirfan.pdma.global;

import java.util.ArrayList;
import java.util.List;

public class GlobalResponseData {


    public   List<String> AppPk;
    public   List<String> FK;
    public   List<String> VarName;
    public   List<String> Response;
    public   List<String> Section;

   public GlobalResponseData()
    {
       AppPk=new ArrayList<>();
        FK=new ArrayList<>();
        VarName=new ArrayList<>();
        Response=new ArrayList<>();
        Section=new ArrayList<>();
    }
}
