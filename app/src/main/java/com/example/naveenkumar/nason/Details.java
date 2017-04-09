package com.example.naveenkumar.nason;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {
    String myphone,myname;
    ListView listv;
    List<String> r_helpers_names= new ArrayList<>();;
    List<String> r_helpers_phones= new ArrayList<>(),entirelist= new ArrayList<>();;
    int range=50,show_flag=0;
    String details;
    String r_counter,r_name,r_phone="";
    double src_latitude=0,src_longitude=0,tar_latitude=0,tar_longitude=0;
    DatabaseReference mydatabase,rdatabase;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        listv=(ListView)findViewById(R.id.listView_help);

        Intent myintent =getIntent();
        details=myintent.getStringExtra("details");
        myphone=myintent.getStringExtra("myphone");
        myname=myintent.getStringExtra("myname");
        Button button=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);
        String[] parts = details.split("\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
        r_name=parts[0];
        r_phone=parts[1];
        String[] parts1 = r_phone.split(" ");
        r_phone="";
        for( int i=0;i<parts1.length;i++)
        {
            r_phone=r_phone+parts1[i];
        }
        String sp="-\t\t";
        String[] parts2 = r_name.split(sp);
        r_name=parts2[1];










        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference(tel.getDeviceId().toString());
//                mydatabase=FirebaseDatabase.getInstance().getReference();
//                myRef.setValue(tel.getDeviceId().toString());
//                String number=tel.getLine1Number().toString();
//                String lat="123.2131231";
//                String lon="12.23123123";
//                myRef.child("phone").setValue(number);
//                myRef.child("lat").setValue(lat);
//                myRef.child("lon").setValue(lon);
//
//                FirebaseDatabase db = FirebaseDatabase.getInstance();

                //myRef.child(tel.getDeviceId().toString()).setValue(tel.getDeviceId().toString());
//                String id=tel.getDeviceId().toString();
//                String name="Naveen kumar";
//                String phone="+919731720159";
//                String lat="122.2231381";
//                String lon="12.26756753";
//                String request="null";
//                String ack="null";
//                mydatabase= FirebaseDatabase.getInstance().getReference();
//                User user=new User(id,name,phone,lat,lon,request,ack);
//                mydatabase.child("users").child(id).setValue(user);

if(show_flag==0) {show_flag=1;
    final List<String> contacts_helpers = getHelpers();
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.textcolor, contacts_helpers);
    listv.setAdapter(adapter);
    if (contacts_helpers.size() == 0) {

        Toast.makeText(Details.this, "No Helpers available", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(Details.this, "Click on any helper to request for help", Toast.LENGTH_SHORT).show();
    }
    listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SmsManager smsManager = SmsManager.getDefault();
            String send_phone = r_helpers_phones.get(position);
            smsManager.sendTextMessage(send_phone, null, "Hello this is " + myname + "\nCan you help me, If " + r_name + " is around you\nThat would be a great favour", null, null);
            Toast.makeText(Details.this, "Request sent", Toast.LENGTH_SHORT).show();
        }
    });
}

            }
        });


    }

    public List<String> getHelpers()
    {
        int e_size=entirelist.size();
        int help_count=0;
        for(int i=0;i<e_size;i++)
        {

            String e_info=entirelist.get(i);
            String[] parts_info= e_info.split("\n");
           tar_latitude= Double.parseDouble(parts_info[3]);
            tar_longitude= Double.parseDouble(parts_info[4]);
            Log.d("android_values", String.valueOf(src_latitude)+"\t"+String.valueOf(src_longitude)+"\t"+String.valueOf(tar_latitude)+"\t"+String.valueOf(tar_longitude));
            double dist=distance(src_latitude,src_longitude,tar_latitude,tar_longitude);
            int int_dist= (int) dist;
            if(int_dist<=range)
            {
                r_helpers_phones.add(help_count,parts_info[2]);
                r_helpers_names.add(help_count,parts_info[1]);
                help_count++;
           }

        }

        return r_helpers_names;
    }

    @Override
    protected void onStart() {
        super.onStart();
        rdatabase=FirebaseDatabase.getInstance().getReference().child("users");
        ValueEventListener postListener =new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String info="";
                ArrayList<User> list = new ArrayList<User>();
                int reg_flag=0;
                int count=0;
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    String id = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String phone = child.child("phone").getValue().toString();
                    String lat = child.child("lat").getValue().toString();
                    String lon = child.child("lon").getValue().toString();
                    info=id+"\n"+name+"\n"+phone+"\n"+lat+"\n"+lon;

                    //info=info+"\n\n";

                    if(r_phone.equals(phone))
                    {
                        reg_flag=1;
                        src_latitude= Double.parseDouble(lat);
                        src_longitude= Double.parseDouble(lon);
                        //Toast.makeText(Details.this, "Registered in NASON", Toast.LENGTH_SHORT).show();
                        textView.setText(r_name+" is "+ " registered in NASON");
                    }
                    else
                    {
                        entirelist.add(count,info);
                        count=count+1;
                    }
                    //textView.setText(info);

                }
                if(reg_flag==0)
                {
                    //Toast.makeText(Details.this, "Not Registered in NASON", Toast.LENGTH_SHORT).show();
                    textView.setText(r_name+" is not "+ " registered in NASON");
                }
//                User post = dataSnapshot.getValue(User.class);
//                String info="";
//                info=info+post.getid()+"\n"+post.getname()+"\n"+post.getphone()+"\n"+post.getlat()+
//                        "\n"+post.getlon()+"\n"+post.getrequest()+"\n"+post.getack()+"";
//                textView.setText(info);

                //Toast.makeText(getApplicationContext(),info,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        rdatabase.addValueEventListener(postListener);
    }


    double distance(double lat1,double long1,double lat2,double long2){
        double theta=long1-long2;
        double dist= Math.sin(deg2rad(lat1))*Math.sin(deg2rad(lat2))+Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))
                *Math.cos(deg2rad(theta));
        dist=Math.acos(dist);
        dist=rad2deg(dist);
        dist=dist*60*1.1515;
        return (dist*1.852000)*1000;
    }

    double deg2rad(double deg){
        return (deg*Math.PI/180.0);
    }

    double rad2deg(double rad){
        return (rad*180.0/Math.PI);
    }

}
