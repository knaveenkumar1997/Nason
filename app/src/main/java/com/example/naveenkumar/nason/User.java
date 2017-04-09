package com.example.naveenkumar.nason;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by NAVEEN KUMAR on 02-04-2017.
 */

public class User {

    String id;
    String name;
  String phone;
    String lat;
    String lon;
    String request;
    String ack;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String name, String phone, String lat, String lon, String request, String ack) {
        this.id = id;
        this.name = name;
        this.phone=phone;
        this.lat=lat;
        this.lon=lon;
        this.request=request;
        this.ack=ack;
    }

    public String getid()
    {
    return this.id;
    }
    public String getname()
    {
    return this.name;
    }
    public String getphone()
    {
        return this.phone;
    }
    public String getlat()
    {
        return this.lat;
    }
    public String getlon()
    {
        return this.lon;
    }
    public String getrequest()
    {
       return this.request;
    }
    public String getack()
    {
        return this.ack;
    }

    public void setid(String id)
    {
       this.id=id;
    }
    public void setname(String name)
    {
        this.name=name;
    }
    public void setphone(String phone)
    {
        this.phone=phone;
    }
    public void setlat(String lat)
    {
        this.lat=lat;
    }
    public void setlon(String lon)
    {
        this.lon=lon;
    }
    public void setrequest(String request)
    {
        this.request=request;
    }
    public void setack(String ack)
    {
        this.ack=ack;
    }

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("id", id);
//        result.put("name", name);
//        result.put("phone",phone);
//        result.put("lat",lat);
//        result.put("lon", lon);
//        result.put("result", result);
//        result.put("ack", ack);
//        result.put("starCount", starCount);
//        result.put("stars", stars);
//
//        return result;
//    }
}