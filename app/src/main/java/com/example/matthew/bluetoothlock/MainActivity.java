package com.example.matthew.bluetoothlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityActions actions;
    private String mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = (Button) findViewById(R.id.am_btn_connect);
        btnListen = (Button) findViewById(R.id.am_btn_listen);
        actions = new MainActivityActions(this);
        btnConnect.setOnClickListener(actions);
        btnListen.setOnClickListener(actions);
        root = (LinearLayout) findViewById(R.id.am_ll_root);
        ets = new ArrayList();
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof EditText) {
                ets.add((EditText) v);
            }

        }

    }

    private Button btnConnect;
    private Button btnListen;
    private List<EditText> ets;
    private LinearLayout root;

    public String getMac() {
        EditText et = null;
        for (int i = 0; i < ets.size(); i++) {
            et = ets.get(i);
            if (i == ets.size() - 1) {
                mac += et.getText().toString();
            } else {
                mac += et.getText().toString() + ":";
            }
        }
        return mac;
    }
}
