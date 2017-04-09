package com.example.naveenkumar.nason;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Request extends AppCompatActivity {
    String name;
    String myphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent m=getIntent();
        name=m.getStringExtra("name");
        myphone=m.getStringExtra("myphone");
        Button button_contact=(Button)findViewById(R.id.button_contact);

        Button button_requests=(Button)findViewById(R.id.button_requests);

        button_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("myphone",myphone);
                startActivity(intent);
            }
        });

        button_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CheckRequests.class);
                startActivity(intent);
            }
        });
    }
}
