package com.example.matthew.bluetoothlock;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
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
public class BluetoothClass implements Runnable {
    private static final int STATE_CONNECTED = 1;
    private static final int STATE_LISTEN =2 ;
    private static final int STATE_CONNECTING = 3;
    private static final int STATE_NONE = 4;
    private BluetoothSocket socket;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
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



    private int BLUETOOTH_CHANNEL = 1;

    public void startListening(String uuid) {
        Method m;
        try {
            sendSocket = adapter.listenUsingInsecureRfcommWithServiceRecord("INSECURE", MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(this);
        thread.start();

        //sendSocket = (BluetoothServerSocket) m.invoke(adapter, BLUETOOTH_CHANNEL);
        //out = sendSocket.accept(250).getOutputStream();

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
            Thread thread = new Thread(this);
            thread.start();
            // execute();
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

    private int mState;

    @Override
    public void run() {
        //if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);
        // setName("AcceptThread");
        BluetoothSocket socket = null;

        // Listen to the server socket if we're not connected
        while (mState != STATE_CONNECTED) {
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                 System.out.println("Waiting to connect************");
                socket = sendSocket.accept();
                //if (D)
                mState = STATE_LISTEN;
                    System.out.println("We have accepted connection and are connected***************");
            } catch (IOException e) {
                //Log.e(TAG, "accept() failed", e);
                break;
            }

            // If a connection was accepted
            if (socket != null) {
                synchronized (BluetoothClass.this) {
                    switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
                            startConnection();
                            //connected(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
                                socket.close();
                            } catch (IOException e) {
                               // Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                    }
                }
            }
        }
       // if (D) Log.i(TAG, "END mAcceptThread");
    }

    private void startConnection() {
        mState = STATE_CONNECTED;
    }
}
