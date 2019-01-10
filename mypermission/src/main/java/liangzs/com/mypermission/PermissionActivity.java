package liangzs.com.mypermission;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import liangzs.com.mypermission.helper.AbstractPermissionHelper;

/**
 * 检测申请透明activity
 *
 * @author liangzs
 * @Date 2019/1/9
 */
public class PermissionActivity extends AppCompatActivity {
    private static final String KEY_PERMISSION = "KEY_PERMISSION";
    private static AbstractPermissionHelper permissionHelper;

    public static void activityRequestPermission(AbstractPermissionHelper permissionHelper, String[] permissions) {
        PermissionActivity.permissionHelper = permissionHelper;
        Intent intent = new Intent(permissionHelper.getContext(), PermissionActivity.class);
        intent.putExtra(KEY_PERMISSION, permissions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        permissionHelper.getContext().startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = getIntent().getStringArrayExtra(KEY_PERMISSION);
        requestPermissions(permissions, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults, permissionHelper);
        finish();
    }


    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, 0);
    }


}
