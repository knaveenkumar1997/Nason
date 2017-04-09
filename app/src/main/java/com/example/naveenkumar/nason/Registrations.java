package com.example.naveenkumar.nason;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class Registrations extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "w5HiRXmDuANtQK6JS5bESmRVv";
    private static final String TWITTER_SECRET = "TTEKHG6drfQBpYNX2I1kCcvWpNbgXi1k7MpoWnPWGg7QOGk6bp";
    SharedPreferences sharedPreferences;
    //boolean test;
    int test;
    int a,b;
    String name;
    String s_name,s_phone;
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_registrations);
        final DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setVisibility(View.INVISIBLE);
        digitsButton.setText("Register with Phone No");
        //digitsButton.setBackgroundColor(252525);
        final TextView textView=(TextView)findViewById(R.id.textView_auth);
        textView.setVisibility(View.INVISIBLE);
        final EditText editText_name=(EditText)findViewById(R.id.editText_name);
        final TextView textView1=(TextView)findViewById(R.id.textView_name);
        final Button button_name=(Button)findViewById(R.id.button_name);

        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editText_name.getText().toString();
                if(name.equals(""))
                {
                    Toast.makeText(Registrations.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    digitsButton.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.INVISIBLE);
                    editText_name.setVisibility(View.INVISIBLE);
                    button_name.setVisibility(View.INVISIBLE);

                }
            }
        });
        sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);

        a=sharedPreferences.getInt("one",test);
        s_name=sharedPreferences.getString("name",name);
        s_phone=sharedPreferences.getString("myphone",phone);
        if(a==1){
            Intent i=new Intent(getApplicationContext(),Request.class);
            i.putExtra("name",s_name);
            i.putExtra("myphone",s_phone);
            startActivity(i);
        }


        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                phone=phoneNumber;
                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                test=1;
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("one",test);
                editor.putString("name",name);
                editor.putString("myphone",phone);
                editor.commit();
                Intent intent=new Intent(getApplicationContext(),Request.class);
                intent.putExtra("name",name);
                intent.putExtra("myphone",phoneNumber);
                startActivity(intent);
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
                test=0;
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("one",test);
                editor.commit();
            }
        });

    }


}
