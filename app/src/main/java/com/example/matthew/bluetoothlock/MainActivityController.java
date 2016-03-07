package com.example.matthew.bluetoothlock;

/**
 * Created by Matthew on 23/02/2016.
 */
public class MainActivityController {
    private BluetoothClass bluetooth;

    private <T> T getInstance(T object) {
        try {
            object = (T) object.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    private BluetoothClass getInstance() {
        if (bluetooth == null) {
            bluetooth = new BluetoothClass();
        }
        return bluetooth;
    }

    public MainActivityController() {
        this.bluetooth = getInstance();
    }

    public void startConnect(String mac) {
        bluetooth.startConnection(mac);
    }

    public void startListening(String uuid) {

        bluetooth.startListening(uuid);
    }
}
