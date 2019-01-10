package liangzs.com.permissiondemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import liangzs.com.mypermission.PermissionManager;
import liangzs.com.mypermission.RationalDialog;
import liangzs.com.mypermission.helper.SettingPageHelper;
import liangzs.com.mypermission.model.PermissionAlwayDenied;
import liangzs.com.mypermission.model.PermissionFail;
import liangzs.com.mypermission.model.PermissionSuccess;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionManager.requestPermission(MainActivity.this, new RationalDialog(), 1, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS);

            }
        });
    }

    private void requesetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.i(TAG, "shouldShowRequestPermissionRationale");
            } else {
                Log.i(TAG, "else...");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @PermissionSuccess
    public void success(int requestCoe, String[] permissions) {
        Log.i(TAG, "PermissionSuccess");
    }

    @PermissionFail
    public void fail(int requestCoe, String[] permissions) {
        Log.i(TAG, "PermissionFail");

    }

    @PermissionAlwayDenied
    public void gotoSetting(int requestCoe, String[] permissions) {
        Log.i(TAG, "PermissionAlwayDenied");
        new AlertDialog.Builder(MainActivity.this).setTitle("to settting").setMessage("need to permission")
                .setPositiveButton("setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SettingPageHelper.GoToSetting(MainActivity.this);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }


}
