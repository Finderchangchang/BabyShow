package liuliu.custom.control.qsprinter.printer;

import android.content.Context;
import android.os.Handler;

import liuliu.custom.control.qsprinter.bt.BtService;

/**
 * Created by Administrator on 2015/9/25.
 */
public class PrinterClassFactory {
    public static PrinterClass create(int type,Context _context,Handler _mhandler,Handler _handler){
        if(type==0){
            return new BtService(_context,_mhandler, _handler);
        }else if(type==1){
            //return new WifiService(_context,_mhandler, _handler);
        }else if(type==2){
            //return new UsbService(_mhandler);
        }
        return null;
    }

}
