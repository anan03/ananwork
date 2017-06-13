package com.example.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class OtherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }
    public void click(View view){
    	startActivity(new Intent(this,MainActivity.class));
    	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    	finish();
    }
}
