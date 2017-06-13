package com.example.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view){
    	startActivity(new Intent(this,OtherActivity.class));
    	//从右边进来，从左边出去
    	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    	finish();
    }
}
