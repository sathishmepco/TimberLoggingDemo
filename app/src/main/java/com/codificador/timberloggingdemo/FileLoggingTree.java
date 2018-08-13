package com.codificador.timberloggingdemo;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import timber.log.Timber;

public class FileLoggingTree extends Timber.DebugTree {

    private static final String TAG = "TimberLogging";
    private Context context;

    public FileLoggingTree(Context context) {
        this.context = context;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        try {
            File direct = new File(Environment.getExternalStorageDirectory().getPath() + "/MyAppLogs");
            if (!direct.exists()) {
                direct.mkdir();
            }

            String fileNameTimeStamp = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String logTimeStamp = new SimpleDateFormat("E MMM dd yyyy 'at' hh:mm:ss:SSS aaa", Locale.getDefault()).format(new Date());
            String fileName = fileNameTimeStamp + ".log";
            File file = new File(direct, fileName);
            file.createNewFile();

            if(priority == Log.DEBUG)
                message= "DEBUG - " + message;
            else if(priority == Log.ERROR)
                message= "ERROR - " + message;

            if (file.exists()) {
                OutputStream fileOutputStream = new FileOutputStream(file, true);
                if(!TextUtils.isEmpty(message)){
                    fileOutputStream.write((logTimeStamp+" :: "+message+"\n").getBytes());
                }
                if(t != null){
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    t.printStackTrace(printWriter);
                    fileOutputStream.write((printWriter.toString()+"\n").getBytes());
                }
                fileOutputStream.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while logging into file : " + e);
        }
    }
}