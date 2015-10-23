package liuliu.custom.control.qsprinter.bt;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import liuliu.custom.control.qsprinter.printer.PrintService;
import liuliu.custom.control.qsprinter.printer.PrinterClass;

/**
 * Created by Administrator on 2015/9/25.
 */
public class BtService extends PrintService implements PrinterClass {
    private static final String TAG = "BtService";
    Context context;
    Handler mhandler, handler;
    public static BlueToothService mBTService = null;

    public BtService(Context _context, Handler _mhandler, Handler _handler) {
        context = _context;
        mhandler = _mhandler;
        handler = _handler;
        mBTService = new BlueToothService(context, mhandler);

        mBTService.setOnReceive(new BlueToothService.OnReceiveDataHandleEvent() {
            @Override
            public void OnReceive(final BluetoothDevice device) {
                if (device != null) {
                    Device d = new Device();
                    d.deviceName = device.getName();
                    d.deviceAddress = device.getAddress();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = d;
                    handler.sendMessage(msg);
                    setState(STATE_SCANING);
                } else {
                    Message msg = new Message();
                    msg.what = 8;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    public boolean open(Context context) {
        mBTService.OpenDevice();
        return true;
    }

    @Override
    public boolean close(Context context) {
        mBTService.CloseDevice();
        return false;
    }

    @Override
    public void scan() {
        // TODO Auto-generated method stub
        if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
            mBTService.OpenDevice();
            return;
        }
        if (mBTService.getState() == STATE_SCANING)
            return;

        new Thread() {
            public void run() {
                mBTService.ScanDevice();
            }
        }.start();
    }

    @Override
    public boolean connect(String device) {
        // TODO Auto-generated method stub
        if (mBTService.getState() == STATE_SCANING) {
            stopScan();
        }
        if (mBTService.getState() == STATE_CONNECTING) {
            return false;
        }
        if(mBTService.getState() == STATE_CONNECTED)
        {
            mBTService.DisConnected();
        }
        mBTService.ConnectToDevice(device);// 连接蓝牙


        return true;
    }

    @Override
    public boolean disconnect() {
        // TODO Auto-generated method stub
        mBTService.DisConnected();
        return true;
    }

    @Override
    public int getState() {
        // TODO Auto-generated method stub
        return mBTService.getState();
    }

    @Override
    public boolean write(byte[] buffer) {
        mBTService.write(buffer);
        return true;
    }

    @Override
    public boolean printText(String textStr) {
        return write(getText(textStr));
    }

    @Override
    public boolean printImage(Bitmap bitmap) {
        // TODO Auto-generated method stub
        byte[] bt=getImage(bitmap);
        return write(bt);
    }

    @Override
    public boolean printUnicode(String textStr) {
        return write(getTextUnicode(textStr));
    }

    @Override
    public boolean IsOpen() {
        return mBTService.IsOpen();
    }

    @Override
    public void stopScan() {
        // TODO Auto-generated method stub
        if (getState() == PrinterClass.STATE_SCANING) {
            mBTService.StopScan();
            mBTService.setState(PrinterClass.STATE_SCAN_STOP);
        }
    }

    @Override
    public void setState(int state) {
        // TODO Auto-generated method stub
        mBTService.setState(state);
    }

    @Override
    public List<Device> getDeviceList() {
        List<Device> devList = new ArrayList<Device>();
        // TODO Auto-generated method stub
        Set<BluetoothDevice> devices = mBTService.GetBondedDevice();
        for (BluetoothDevice bluetoothDevice : devices) {
            Device d = new Device();
            d.deviceName = bluetoothDevice.getName();
            d.deviceAddress = bluetoothDevice.getAddress();
            devList.add(d);
        }
        return devList;
    }


}
