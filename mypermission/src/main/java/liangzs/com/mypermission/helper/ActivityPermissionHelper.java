package liangzs.com.mypermission.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.List;

import liangzs.com.mypermission.PermissionActivity;

/**
 * @author liangzs
 * @Date 2019/1/9
 */
public class ActivityPermissionHelper extends AbstractPermissionHelper<Activity> {

    private Activity activity;

    public ActivityPermissionHelper(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, perm);
    }

    @Override
    public void requesetPermissions(int requestCode, String[] perm) {
        PermissionActivity.activityRequestPermission(this, perm);
    }

    @Override
    public boolean somePermissionAlwayDenied(List<String> perms) {
        for (String deniedPermission : perms) {
            if (!shouldShowRequestPermissionRationale(deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Activity getHolder() {
        return activity;
    }

    @Override
    public Context getContext() {
        return activity;
    }

}
