package com.discover.app.activity;

import android.os.Bundle;
import android.view.Menu;

import com.discover.app.R;
import com.discover.core.BaseActivity;
/**
 * 主界面
 * @author Start
 */
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
