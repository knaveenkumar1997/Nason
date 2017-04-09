package com.example.naveenkumar.nason;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

        import android.Manifest;
        import android.content.ContentResolver;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Message;
        import android.provider.Contacts;
        import android.provider.Contacts.People;
        import android.provider.ContactsContract;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

       /* import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.api.GoogleApiClient;
*/
        import org.w3c.dom.Text;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;
        import android.os.Handler;
        import android.Manifest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    int counter=0;
    //Manifest.permission.ACCESS_FINE_LOCATION
    DatabaseReference mydatabase,rdatabase;
    ListView lstNames;
    //MyObserver contactsObserver;
    String locfile="locfile";
    String filename = "myfile";
    String myphone,myname;
    String string = "";
    SharedPreferences sharedPreferences;
    String id;
    int flag=0;
    String lat,lon,request,ack;
    final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
    Location l;
    double curr_lat,curr_long;
    double hyd_lat=13.355030,hyd_long=74.793916,total_dist;
    LocationListener locationListener;
    LocationManager locationManager;
    static final int REQUEST_LOCATION=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final Intent myintent = getIntent();
        myphone = myintent.getStringExtra("myphone");
        myname = myintent.getStringExtra("name");
        id = tel.getDeviceId().toString();
         lat = "13.3516";
        lon = "74.7932";
         request = "null";
       ack = "null";
        //sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);

        //flag=sharedPreferences.getInt("flag",flag);


/*




FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(locfile, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }







 */



       /* if (flag==1) {
            lat = sharedPreferences.getString("lat", String.valueOf(curr_lat));
            lon = sharedPreferences.getString("lon", String.valueOf(curr_long));
            mydatabase = FirebaseDatabase.getInstance().getReference();
            User user1 = new User(id, myname, myphone, lat, lon, request, ack);
            mydatabase.child("users").child(id).setValue(user1);
        }
        else{
*/
            mydatabase = FirebaseDatabase.getInstance().getReference();
            User user = new User(id, myname, myphone, lat, lon, request, ack);
            mydatabase.child("users").child(id).setValue(user);
        //}


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                curr_lat = location.getLatitude();
                curr_long = location.getLongitude();
                Log.d("android_lat", curr_lat + "android_lon" + curr_long);
//                SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.putInt("flag",1);
//                editor.putString("lat",String.valueOf(curr_lat));
//                editor.putString("lon", String.valueOf(curr_long));
//                editor.commit();
                lat= String.valueOf(curr_lat);
                lon=String.valueOf(curr_long);
                mydatabase = FirebaseDatabase.getInstance().getReference();
                User user = new User(id, myname, myphone, lat, lon, request, ack);
                mydatabase.child("users").child(id).setValue(user);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        //LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());




        //final TextView textView = (TextView) findViewById(R.id.textView);
        lstNames = (ListView) findViewById(R.id.listView);
        //Button button_read = (Button) findViewById(R.id.button_read);
        //Button button_show = (Button) findViewById(R.id.button_show);
        //textView.setVisibility(View.INVISIBLE);
        //lstNames.setVisibility(View.INVISIBLE);
        //button.performClick();
        //lstNames.setVisibility(View.INVISIBLE);
        showContacts();

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Contacts Updated", Toast.LENGTH_SHORT).show();
        lstNames.setVisibility(View.VISIBLE);
        final List<String> contacts = getContacts();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.textcolor, contacts);
        lstNames.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "No.of Contacts: " + counter, Toast.LENGTH_SHORT).show();
        lstNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String details = contacts.get(position);
                Intent intent = new Intent(getApplicationContext(), Details.class);
                intent.putExtra("myname",myname);
                intent.putExtra("details", details);
                intent.putExtra("myphone", myphone);
                startActivity(intent);
            }
        });
        //******************NEW*************

    }

    //end of oncreate
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getContactNames();
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            //lstNames.setAdapter(adapter);
        }
    }

    private void getContactNames() {
        List<String> contacts = new ArrayList<>();

        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (contacts.contains(name + "\t" + phone))
                {
                   continue;
                }
                else
                {
                    contacts.add(name + "\t" + phone);
                }
                string = string + name + "\t" + phone + "\n";
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();


    }

    private List<String> getContacts() {
        List<String> contacts = new ArrayList<>();
        String path = getApplicationContext().getFilesDir().toString();
        File file = new File(path, filename);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String part1 = parts[0]; // 004
                String part2 = parts[1];
                counter=counter+1;
                contacts.add(counter+"-\t\t"+part1 + "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + part2);
            }
            br.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }


        return contacts;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void obs() {

    }
    /*

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.naveenkumar.contacts/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.naveenkumar.contacts/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
*/

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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finish();
        //System.exit(0);
        moveTaskToBack(true);
    }
}
