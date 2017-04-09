//package com.example.naveenkumar.nason;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
///**
// * Created by NAVEEN KUMAR on 02-04-2017.
// */
//
//public class ReadDataActivity extends AppCompatActivity implements AsyncResponse {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_read_user);
//
//        PostResponseAsyncTask readData = new PostResponseAsyncTask(this);
//        readData.execute("http://yoursite.com/readdata.php");
//    }
//
//    @Override
//    public void processFinish(String output) {
//        //you can get 'output' from here.
//    }
//}