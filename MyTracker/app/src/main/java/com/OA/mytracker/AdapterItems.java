package com.OA.mytracker;

import java.io.Serializable;



public class AdapterItems implements Serializable
{

    public  String UserName;
    public  String PhoneNumber;
    //for news details
    AdapterItems(  String UserName,String PhoneNumber)
    {
        this. UserName=UserName;
        this. PhoneNumber=PhoneNumber;
    }
}
