package com.example.r2d.autosmssender;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.telephony.SmsManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.example.r2d.wafflecopter.multicontactpicker.ContactResult;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import javax.security.auth.callback.PasswordCallback;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MySMSservice extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SMS = "com.example.r2d.autosmssender.action.SMS";
    private static final String ACTION_WHATSAPP = "com.example.r2d.autosmssender.action.WHATSAPP";

    // TODO: Rename parameters
    private static final String MESSAGE = "com.example.r2d.autosmssender.extra.PARAM1";
    private static final String COUNT = "com.example.r2d.autosmssender.extra.PARAM2";
    private static final String MOBILE_NUMBER = "com.example.r2d.autosmssender.extra.PARAM3";
    private static final String IS_EACH_WORD = "com.example.r2d.autosmssender.extra.PARAM4";
    private static final String FILEPATH = "com.example.r2d.autosmssender.extra.PARAM54";

    public MySMSservice() {
        super("MySMSservice");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSMS(Context context, String message, String count, List<ContactResult> mobile_numbers) {

        List<String> numbers =new ArrayList<String>();
        for(int i = 0;i<mobile_numbers.size();i++){
            numbers.add(mobile_numbers.get(i).getPhoneNumbers().get(0).getNumber());
        }
        String[] numbersArray = numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySMSservice.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(COUNT, count);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionWHATSAPP(Context context, String message, String count, List<ContactResult> mobile_numbers, Boolean isEachWord,String file_path) {

        List<String> numbers =new ArrayList<String>();
        for(int i = 0;i<mobile_numbers.size();i++){
            numbers.add(mobile_numbers.get(i).getPhoneNumbers().get(0).getNumber());
        }
        String[] numbersArray = numbers.toArray(new String[0]);

        Intent intent = new Intent(context, MySMSservice.class);
        intent.setAction(ACTION_WHATSAPP);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(COUNT, count);
        intent.putExtra(MOBILE_NUMBER,numbersArray);
        intent.putExtra(IS_EACH_WORD,isEachWord);
        intent.putExtra(FILEPATH,file_path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SMS.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String count = intent.getStringExtra(COUNT);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                handleActionSMS(message, count,mobile_number);
            } else if (ACTION_WHATSAPP.equals(action)) {
                final String message = intent.getStringExtra(MESSAGE);
                final String count = intent.getStringExtra(COUNT);
                final String[] mobile_number = intent.getStringArrayExtra(MOBILE_NUMBER);
                final boolean isEachWord = intent.getBooleanExtra(IS_EACH_WORD,false);
                final String filepath = intent.getStringExtra(FILEPATH);
                handleActionWHATSAPP(message, count,mobile_number,isEachWord,filepath);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSMS(String message, String count, String[] mobile_number) {
        // TODO: Handle action Foo

        try {
            if(mobile_number.length!=0) {
                for(int j=0;j<mobile_number.length;j++) {

                    for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mobile_number[j], null, message, null, null);
                        sendBroadcastMessage("Result:"+ (i+1) + " "+ mobile_number[j]);
                    }

                }
            }
        }catch(Exception e){

        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWHATSAPP(String message, String count, String[] mobile_number, boolean isEachWord,String filepath) {

          if(filepath!=null ){
            try {
                PackageManager packageManager = getApplicationContext().getPackageManager();
                if (mobile_number.length != 0) {
                    for (int j = 0; j < mobile_number.length; j++) {

                        for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                            String number = mobile_number[j];
                            Intent sendIntent = new Intent("android.intent.action.MAIN");
                            Uri fileData = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                                    .getApplicationContext().getPackageName() + ".provider", new File(filepath));

                            sendIntent.putExtra(Intent.EXTRA_STREAM, fileData);
                            sendIntent.putExtra("jid", number + "@s.whatsapp.net");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            sendIntent.setPackage("com.whatsapp");
                            sendIntent.setType(getMimeType(fileData));
                            getApplicationContext().startActivity(sendIntent);
                            if (sendIntent.resolveActivity(packageManager) != null) {
                                getApplicationContext().startActivity(sendIntent);
                                //Thread.sleep(dalayN);
                                sendBroadcastMessage("Result: " + number);
                            } else {
                                sendBroadcastMessage("Result: WhatsApp Not installed");
                            }

                        }

                    }
                }
            } catch (Exception e) {
                sendBroadcastMessage("Result: " + e.toString());
            }
        }
        else {
            try {
                PackageManager packageManager = getApplicationContext().getPackageManager();
                if (mobile_number.length != 0) {
                    for (int j = 0; j < mobile_number.length; j++) {

                        for (int i = 0; i < Integer.parseInt(count.toString()); i++) {
                            String number = mobile_number[j];


                            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(message + "   ", "UTF-8");
                            Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
                            whatappIntent.setPackage("com.whatsapp");
                            whatappIntent.setData(Uri.parse(url));
                            whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(whatappIntent);
                            Thread.sleep(10000);
                            sendBroadcastMessage("Result: " + number);
                        /*    if (whatappIntent.resolveActivity(packageManager) != null) {
                                getApplicationContext().startActivity(whatappIntent);
                                Thread.sleep(10000);
                                sendBroadcastMessage("Result: " + number);
                            } else {
                                sendBroadcastMessage("Result: WhatsApp Not installed");
                            }*/
                        }

                    }
                }
            } catch (Exception e) {
                sendBroadcastMessage("Result: " + e.toString());
            }
        }


    }


    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = getApplicationContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
    private void sendBroadcastMessage(String message){
        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("result",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
