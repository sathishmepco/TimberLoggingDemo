package com.codificador.timberloggingdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import com.codificador.timberloggingdemo.databinding.ActivityMainBinding;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setEvent(this);
        permissionCheck();
        Timber.d("OnCreate Called");
    }

    public void onButtonClick(){
        Timber.d("Button Clicked");
        try{
            int result = 10/0;
        }catch (Exception e){
            Timber.e(e,e.getLocalizedMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("OnResume Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("OnPause Called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.d("OnStart Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.d("OnStop Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("OnDestroy Called");
    }

    public void onSubmitClick(){
        Timber.d("Submit Clicked");
        if(TextUtils.isEmpty(binding.editText.getText().toString()))
            Timber.d("EditText is empty");
        else
            Timber.d("EditText contains - "+binding.editText.getText().toString());
    }

    private void permissionCheck(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_DENIED)
                finish();
        }
    }
}