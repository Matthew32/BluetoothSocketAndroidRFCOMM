package com.example.matthew.bluetoothlock;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Matthew on 23/02/2016.
 */
public class BluetoothClass extends AsyncTask<String, String, String> {
    private BluetoothSocket socket;
    private BluetoothServerSocket sendSocket;
    private BluetoothAdapter adapter;
    private BluetoothDevice device;
    private OutputStream out;
    private InputStream input;
    private String contenido;
    private boolean paron;

    public BluetoothClass() {
        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected String doInBackground(String... params) {
        int available = 0;
        byte[] bites = null;
        char palabra = ' ';

        return contenido;
    }

    private int BLUETOOTH_CHANNEL = 1;

    public void startListening(String uuid) {
        Method m;
        try {
            sendSocket = adapter.listenUsingInsecureRfcommWithServiceRecord("InSECURE", UUID.fromString(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sendSocket = (BluetoothServerSocket) m.invoke(adapter, BLUETOOTH_CHANNEL);
        //out = sendSocket.accept(250).getOutputStream();
        Thread thread = new Thread() {
            int available = 0;

            byte[] bites = new byte[100];

            public void run() {
                try {
                    socket = sendSocket.accept();
                    input = socket.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        available = input.available();
                        if (available > 0) {
                            input.read(bites);
                            for (int i = 0; i < bites.length; i++) {
                                contenido += (char) bites[i];
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        thread.start();
        //socket = sendSocket.accept();
        //out = socket.getOutputStream();


        //input = sendSocket.accept().getInputStream();

    }

    public void startConnection(String mac) {
        device = adapter.getRemoteDevice(mac);
        Method m = null;
        try {
            m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{int.class});
            socket = (BluetoothSocket) m.invoke(device, 1);
            socket.connect();
            out = socket.getOutputStream();
            input = socket.getInputStream();
            execute();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data) {
        char[] datax = data.toCharArray();
        byte[] bites = new byte[datax.length];
        try {
            out.write(bites);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
