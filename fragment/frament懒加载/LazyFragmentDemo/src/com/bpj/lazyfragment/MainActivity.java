package com.bpj.lazyfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	public static final int POSITION_ZERO = 0;
	public static final int POSITION_ONE = 1;
	private TextView tv2;
	private RelativeLayout rl;
	private int mPosition = POSITION_ZERO;
	private FragmentAdapter mFragmentAdapter;
	private Fragment[] mFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFragments = new Fragment[]{Fragment1.newInstance(),Fragment2.newInstance()};
		mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),mFragments);

		rl = (RelativeLayout) findViewById(R.id.rl);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setFragment(rl,mPosition);
				
				if(mPosition == POSITION_ONE){
					mPosition = POSITION_ZERO;
				}else{
					mPosition = POSITION_ONE;
				}

			}

		});
	}

	private void setFragment(RelativeLayout rl,int position ){
		Fragment fragment = (Fragment) mFragmentAdapter.instantiateItem(rl, position);
		mFragmentAdapter.setPrimaryItem(rl, 0, fragment);
		mFragmentAdapter.finishUpdate(rl);
	}


}
