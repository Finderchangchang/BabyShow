package liuliu.custom.method;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import liuliu.custom.R;
import liuliu.custom.method.xml.Jxml;
import liuliu.custom.control.toast.RadiusToast;

public class Utils {
    public static final String COMPANY_CHANGETIME = "Company_changeTime";
    public static final String PERSON_CHANGETIME = "Person_changeTime";
    public static final String KEY_SESSIONID = "key_sessionid";
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_REMEMBER = "key_remember";
    public static final String KEY_BLUE_READ_CARD_ADDRESS="key_blue_read_card_address";
    public static final String KEY_BLUE_DA_YIN_ADDRESS="key_blue_da_yin_address";
    public static final String KEY_IS_OPEN="key_is_open";

    public static final String KEY_NO_READ_MESSAGE = "key_no_read_message";

    public static final String KEY_HAS_CODES = "key_hascodes";
    public static final String KEY_CODE_NAMES = "key_codenames";

    public static final String KEY_HAS_OPERATORS = "key_hasoperators";
    public static final String KEY_OPERATORS = "key_operators";
    public static final String KEY_PULLTOBACK = "key_pulltoback";//检查是否上传到后台
    public static final String KEY_SERVERNAME = "key_servername";
    public static final String KEY_PORT = "key_port";
    public static final String CHECK_UPLOAD_TIME = "check_upload_time";
    public static final String CHECK_LOAD_TIME = "check_load_time";//下载人员信息时间
    public static final String BACK_SYS_SETTING_TO_SETTING_HTTP = "sys_setting_to_setting_http";//系统设置页面跳转到网络设置页面
    public static final String BACK_Login_TO_SETTING_HTTP = "sys_login_to_setting_http";//登录页面跳转到网络设置页面
    public static final String BACK_RegUser_TO_SETTING_HTTP = "sys_reguser_to_setting_http";//用户注册跳转到网络设置
    public static final String BACK_Login_TO_RegUser = "sys_login_to_reguser";//登录跳转到用户注册
    public static final String KEY_BLUETOOTH_ADDRESS = "key_bluetooth_address";

    public static final String KEY_SIGN_PLACE="key_sign_place";
    public static final String KEY_OPEN_TIME="key_open_time";
    public static final String KEY = "_key";

    public static final int CAMRAM_RESULT = 500;
    public static void setText(TextView textView, String val){
        setText(textView,val,"");
    }
    public static void setText(TextView textView,String val,String def_val){
        if(val!=null){
            textView.setText(val);
        }else{
            if(def_val.equals("")){
                def_val="无";
            }
            textView.setText(def_val);
        }
    }
    private static long exitTime = 0;
    public interface OnExitListener{
        void toast();
        void finish();
    }
    public static void exit(OnExitListener listener) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            listener.toast();
            exitTime = System.currentTimeMillis();
        } else {
            listener.finish();
        }
    }
    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static LinearLayout.LayoutParams setMargin(int width, int height, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                width, height);
        lp.setMargins(left, top, right, bottom);
        return lp;
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*根据String类型的time转化为Date类型*/
    public static Date StringToDate(String time) {
        try {
            return sdf.parse(time);
        } catch (java.text.ParseException e) {
            return null;
        }
    }

    /*将Date转化为String类型*/
    public static String DateToString(Date date) {
        return sdf.format(date);
    }

    /*自定义加载条*/
    public static Dialog ProgressDialog(Context context, Dialog progressDialog, String val) {
        progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(val);
        return progressDialog;
    }

    public static void GetCodeModels(Context context, String CodeName) {
//        XmlManager xml = new XmlManager(context);
//        types = xml.getXml("Code_ExpressType.xml");
//        if (types != null) {
//            for (int i = 0; i < types.size(); i++) {
//                nameList.add(types.get(i).getValue());
//            }
//            if (types.size() > 0) {
//                spinner_expressType.setText(types.get(0).getValue());
//            }
//        }
    }

    public static boolean fillTestData() {
        return false;
    }

    static public String createGUID() {
        return UUID.randomUUID().toString();
    }

    static public double getDouble(TextView view) {
        if (view == null)
            return 0;

        try {
            return Double.parseDouble(view.getText().toString());
        } catch (Exception e) {
        }
        return 0;
    }

    static public int getInt(TextView view) {
        if (view == null)
            return 0;

        try {
            return Integer.parseInt(view.getText().toString());
        } catch (Exception e) {
        }

        return 0;
    }

    static public int getInt(Spinner view) {
        if (view == null)
            return 0;

        try {
            return view.getSelectedItemPosition();
        } catch (Exception e) {
        }

        return -1;
    }

    static public String getString(TextView view) {
        if (view == null)
            return "";

        try {
            return view.getText().toString();
        } catch (Exception e) {
        }

        return "";
    }

    static public String getString(Spinner view) {
        if (view == null)
            return "";

        try {
            return view.toString();
        } catch (Exception e) {
        }

        return "";
    }

    public static String Preferences_name = "BULKGASOLINE";

    public static String ReadString(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, "");
    }

    public static int getScannerWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    public static int getScannerHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

    public static void WriteString(Context context, String key, String value) {
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int ReadInt(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, 0);
    }

    public static void WriteInt(Context context, String key, int value) {
        SharedPreferences sp = getSharedPreferences(context);
        // 锟斤拷锟斤拷锟斤拷锟斤拷
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void WriteBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSharedPreferences(context);
        // 锟斤拷锟斤拷锟斤拷锟斤拷
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Set<String> ReadStringSet(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getStringSet(key, new LinkedHashSet<String>());
    }

    public static void WriteStringSet(Context context, String key,
                                      Set<String> value) {
        SharedPreferences sp = getSharedPreferences(context);
        // 锟斤拷锟斤拷锟斤拷锟斤拷
        Editor editor = sp.edit();
        editor.putStringSet(key, value);

        editor.commit();
    }

    public static boolean hasRememberPassword(Context context) {
        if (Utils.ReadBoolean(context, KEY_REMEMBER)) {
            if (!isEmpty(Utils.ReadString(context, KEY_USERNAME))
                    && !isEmpty(Utils.ReadString(context, KEY_PASSWORD))) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasSessionId(Context context) {
        return !Utils.isEmpty(Utils.ReadString(context, KEY_SESSIONID));
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static void hideIM(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) context).getCurrentFocus();
        if (v == null)
            imm.hideSoftInputFromWindow(null, 0);
        else
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static Uri startCamra(Activity context, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 锟斤拷锟斤拷锟斤拷锟角诧拷锟斤拷一锟斤拷锟斤拷锟捷ｏ拷ContentValues锟斤拷锟斤拷锟斤拷希锟斤拷锟斤拷锟斤拷锟斤拷录锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷息
        // 锟斤拷些锟斤拷锟捷碉拷锟斤拷锟斤拷锟窖撅拷锟斤拷为锟斤拷锟斤拷锟斤拷MediaStore.Images.Media锟斤拷,锟叫的存储锟斤拷MediaStore.MediaColumn锟斤拷锟斤拷
        // ContentValues values = new ContentValues();

        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "testing");
        values.put(MediaStore.Images.Media.DESCRIPTION, "this is description");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        // Uri uri =
        // context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        // values);
        Uri uri = Uri.fromFile(new File(getDataPath() + "/temp/"
                + System.currentTimeMillis() + ".tmp"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 锟斤拷锟斤拷锟酵斤拷锟侥硷拷锟侥存储锟斤拷式锟斤拷uri指锟斤拷锟斤拷锟斤拷Camera应锟斤拷锟斤拷

        // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷要锟斤拷锟斤拷锟斤拷Camera锟襟，匡拷锟皆凤拷锟斤拷Camera锟斤拷取锟斤拷锟斤拷图片锟斤拷
        // 锟斤拷锟皆ｏ拷锟斤拷锟斤拷使锟斤拷startActivityForResult锟斤拷锟斤拷锟斤拷Camera
        context.startActivity(intent);
        return uri;
    }

    public static String getDataPath() {
        File defaultStorageFile = Environment.getExternalStorageDirectory();
        return String.format("%s/Gasoline",
                defaultStorageFile.getAbsolutePath());
    }

    public static String getDataPath(String name) {
        return String.format("%s/%s", getDataPath(), name);
    }

    public static String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static boolean hasCodeNames(Context context) {
        return Utils.ReadBoolean(context, KEY_HAS_CODES);
    }

    public static boolean loadCodeNames(Context context, Set<String> codeNames) {
        if (Utils.ReadBoolean(context, KEY_HAS_CODES)) {
            Set<String> names = Utils.ReadStringSet(context, KEY_CODE_NAMES);
            if (names != null) {
                codeNames.clear();
                codeNames.addAll(names);

                return true;
            }

        }

        return false;
    }

    public static void writeCodeNames(Context context, String xml) {
        Utils.WriteString(context, KEY_CODE_NAMES, xml);
        Utils.WriteBoolean(context, KEY_HAS_CODES, true);
    }

    /**
     * 锟斤拷锟斤拷锟街碉拷锟斤拷息
     */
    public static boolean loadCodes(Context context, String codeName,
                                    ArrayList<String> codeKeys, ArrayList<String> codeValues) {
        String xml = Utils.ReadString(context, codeName);
        if (Utils.isEmpty(xml))
            return false;
        return parseCodeXml(xml, codeKeys, codeValues);
    }

    public static boolean codesEqual(Context context, String codeName,
                                     ArrayList<String> codeKeys, ArrayList<String> codeValues) {
        String xml = Utils.ReadString(context, codeName);
        if (Utils.isEmpty(xml))
            return false;
        return parseCodeXml(xml, codeKeys, codeValues);

    }

    /**
     * 锟斤拷锟截硷拷锟斤拷员锟斤拷息
     */
    public static boolean loadOperators(Context context,
                                        ArrayList<String> operatorIds, ArrayList<String> operators) {
        if (!hasOperators(context)) {
            return false;
        }
        String xml = Utils.ReadString(context, KEY_OPERATORS);
        Log.v("cccccccccccccccc", xml);
        if (Utils.isEmpty(xml)) {
            return false;
        }
        return parseOperatorXml(xml, operatorIds, operators);
    }

    public static boolean hasOperators(Context context) {
        return Utils.ReadBoolean(context, KEY_HAS_OPERATORS);
    }

    public static boolean ReadBoolean(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Preferences_name,
                Context.MODE_PRIVATE);
    }

    public static boolean parseCodeXml(String content,
                                       ArrayList<String> codeKeys, ArrayList<String> codeValues) {
        Jxml xml = new Jxml();
        if (xml.SetDoc(content) && xml.FindElem("InvokeReturn")) {
            xml.IntoElem();
            while (xml.FindElem()) {
                String key, value;
                key = xml.GetTagName();
                if ("Object".compareToIgnoreCase(key) == 0) {
                    codeKeys.clear();
                    codeValues.clear();

                    xml.IntoElem();
                    while (xml.FindElem("CodeModel")) {
                        xml.IntoElem();
                        while (xml.FindElem()) {
                            key = xml.GetTagName();
                            value = xml.GetData();
                            if (key.compareToIgnoreCase("Key") == 0) {
                                codeKeys.add(value);
                            } else if (key.compareToIgnoreCase("Value") == 0) {
                                codeValues.add(value);
                            }
                        }
                        xml.OutOfElem();
                    }
                    xml.OutOfElem();
                } else {
                    value = xml.GetData();
                }
            }
            xml.OutOfElem();

            return true;
        }

        return false;
    }

    public static void writeCodes(Context context, String codeName, String xml) {
        Utils.WriteString(context, codeName, xml);
    }

    public static boolean parseOperatorXml(String content,
                                           ArrayList<String> ids, ArrayList<String> values) {
        Map<String, String> data = new HashMap<String, String>();
        Jxml xml = new Jxml();
        if (xml.SetDoc(content) && xml.FindElem("InvokeReturn")) {
            xml.IntoElem();
            while (xml.FindElem()) {
                String key, value;
                key = xml.GetTagName();
                if ("Object".compareToIgnoreCase(key) == 0) {
                    ids.clear();
                    values.clear();

                    xml.IntoElem();
                    while (xml.FindElem("EmployeeModel")) {
                        xml.IntoElem();
                        while (xml.FindElem()) {
                            key = xml.GetTagName();
                            if (key.compareToIgnoreCase("EmployeeId") == 0) {
                                value = xml.GetData();
                                ids.add(value);
                            } else if (key.compareToIgnoreCase("EmployeeName") == 0) {
                                value = xml.GetData();
                                values.add(value);
                            }
                        }
                        xml.OutOfElem();
                    }
                    xml.OutOfElem();
                } else {
                    value = xml.GetData();
                    data.put(key, value);
                }
            }
            xml.OutOfElem();

            return true;
        }

        return false;
    }

    public static void writeOperators(Context context, String xml) {
        Utils.WriteString(context, KEY_OPERATORS, xml);
        Utils.WriteBoolean(context, KEY_HAS_OPERATORS, true);
    }

    public static String ReadBlueToothAddress(Context context) {
        return Utils.ReadString(context, KEY_BLUETOOTH_ADDRESS);
    }

    public static void WriteBlueToothAddress(Context context, String address) {
        Utils.WriteString(context, KEY_BLUETOOTH_ADDRESS, address);
    }

    public static void Sleep(long mi) {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
        }
    }

    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getCSTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date()).replace(" ", "T");
    }

    public static String encodeImageView(ImageView imageview) {
        String imageString = "";
        try {
            imageview.setDrawingCacheEnabled(true);
            Bitmap bitmap = imageview.getDrawingCache();
            imageString = encodeBitmap(bitmap);
            imageview.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageString;
    }

    public static String encodeBitmap(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
                    .trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String XMlEncode(String strData) {
        if (strData == null)
            return "";

        strData = strData.replace("&", "&amp;");
        strData = strData.replace("<", "&lt;");
        strData = strData.replace(">", "&gt;");
        strData = strData.replace("&apos;", "&apos;");
        strData = strData.replace("\"", "&quot;");

        return strData;
    }

    public static String URLEncoder(String strData) {
        if (strData == null)
            return "";

        return URLEncoder.encode(strData);
    }

    public static String[] LinkedSetToArray(LinkedHashSet<String> datas) {
        String[] array = new String[datas.size()];
        int index = 0;
        for (Iterator<String> iter = datas.iterator(); iter.hasNext(); ) {
            array[index] = (String) iter.next();
            index++;
        }
        return array;
    }

    public static boolean saveAssetsFile(AssetManager assetManager,
                                         String strName, String strTarget) {
        boolean bSucc = false;
        final int BUF_SIZE = 10124;
        try {
            InputStream is = assetManager.open(strName);
            if (is == null)
                return false;
            File fOut = new File(strTarget);
            FileOutputStream ofs = new FileOutputStream(fOut);
            if (is != null && ofs != null) {
                byte[] bBuf = new byte[BUF_SIZE];
                int nRead, length = BUF_SIZE;
                while ((nRead = is.read(bBuf, 0, length)) > 0) {
                    ofs.write(bBuf, 0, nRead);
                }
                ofs.close();
                is.close();
                bSucc = true;
            }
        } catch (Exception e) {
        }
        return bSucc;
    }

    public static String getTempImagePath() {

        String path = Environment.getExternalStorageDirectory() + "/gasoline/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();

        return path + "temp.image";
    }

    public static Bitmap decodeBitmapFromFile(String filePath,
                                              int maxNumOfPixels) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, maxNumOfPixels);
        opts.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private static Bitmap previewBitmap = null;

    public static void setPreviewBitmap(Bitmap bitmap) {
        previewBitmap = bitmap;
    }

    public static Bitmap getPreviewBitmap() {
        return previewBitmap;
    }

    public static boolean isEmptyString(String str) {
        return (str == null || str.length() == 0);
    }

    public static Boolean getJsonBoolean(JSONObject object, String name) {
        try {
            return object.getBoolean(name);
        } catch (JSONException e) {
            return false;
        }
    }

    public static String getJsonString(JSONObject object, String name) {
        try {
            return object.getString(name);
        } catch (JSONException e) {
            return "";
        }
    }

    public static int getJsonInt(JSONObject object, String name) {
        try {
            return object.getInt(name);
        } catch (JSONException e) {
            return 0;
        }
    }

    public static String getJsonDate(JSONObject object, String name) {
        try {
            return getDateString(object.getString(name));
        } catch (JSONException e) {
            return "";
        }
    }

    public static String getDateString(String text) {
        // /Date(1361431509843)/
        try {
            if (!Utils.isEmptyString(text)) {
                text = text.replace("/", "");
                text = text.replace("\\", "");
                text = text.replace("Date", "");
                text = text.replace("(", "");
                text = text.replace(")", "");
                SimpleDateFormat formatter = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                return formatter.format(new Date(Long.valueOf(text)));
            }
        } catch (Exception e) {

        }
        return "";
    }

    public static String getTodayString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }

    public static String getTodayString1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    public interface putListener {
        void put(Intent intent);
    }

    public static void IntentPost(Context context, Class cla, putListener listener) {
        Intent intent = new Intent();
        intent.setClass(context, cla);
        if (listener != null) {
            listener.put(intent);
        }
        context.startActivity(intent);
    }

    public static void IntentPost(Context context, Class cla) {
        IntentPost(context, cla, null);
    }

    //intent=getIntent();name=闇�瑕佹帴鏀剁殑閿�
    public static Object IntentGet(Intent intent, String name) {
        return intent.getStringExtra(name);
    }

    //鑾峰緱鎵嬫満璁惧鐨勭浉鍏充俊鎭�
    //鏉冮檺<uses-permission android:name="android.permission.READ_PHONE_STATE" />
    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
    }

    public static void ToastShort(Context context, String text) {
        RadiusToast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    //闀挎樉绀鸿嚜瀹氫箟Toast寮瑰嚭妗�
    public static void ToastLong(Context context, String text) {
        RadiusToast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 检测Android设备是否支持摄像机
     */
    public static boolean checkCameraDevice(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
}
