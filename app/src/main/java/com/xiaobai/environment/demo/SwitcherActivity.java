package com.xiaobai.environment.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaobai.compile.ui.EnvironmentSwitchActivity;

public class SwitcherActivity extends Activity {

    Button changeButton;
    Button oneKeyChangeButton;
    Button bt_changed_one_key_all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher);
        changeButton = findViewById(R.id.bt_changed);
        oneKeyChangeButton = findViewById(R.id.bt_changed_one_key);
        bt_changed_one_key_all = findViewById(R.id.bt_changed_one_key_all);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnvironmentSwitchActivity.launch(SwitcherActivity.this);
            }
        });

        oneKeyChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnvironmentSwitchActivity.launch(SwitcherActivity.this,EnvironmentSwitchActivity.TYPE_SHOW_MODE_ALL);
            }
        });

        bt_changed_one_key_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnvironmentSwitchActivity.launch(SwitcherActivity.this,EnvironmentSwitchActivity.TYPE_SHOW_MODE_ONLY);
            }
        });
    }
}
