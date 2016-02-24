package com.example.matthew.bluetoothlock;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Matthew on 23/02/2016.
 */
public class MainActivityActions implements View.OnClickListener {
    private MainActivity main;
    private MainActivityController controller;

    public MainActivityActions(MainActivity main) {
        this.controller = new MainActivityController();
        this.main = main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.am_btn_connect:
                controller.startConnect(main.getMac());
                break;
            case R.id.am_btn_listen:
                Toast.makeText(main,"Escuchando 3000 milisegundos" ,Toast.LENGTH_SHORT).show();
                TelephonyManager tManager = (TelephonyManager)main.getSystemService(Context.TELEPHONY_SERVICE);
                String uuid = tManager.getDeviceId();
                controller.startListening(uuid);
                break;
        }
    }
}
