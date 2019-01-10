package liangzs.com.mypermission.helper;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * @author liangzs
 * @Date 2019/1/9
 */
public class SupportFragmentPermissionHelper extends AbstractPermissionHelper<Fragment> {
    private Fragment fragment;

    public SupportFragmentPermissionHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        return fragment.shouldShowRequestPermissionRationale(perm);

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
    public void requesetPermissions(int requestCode, String[] perm) {
        fragment.requestPermissions(perm, requestCode);
    }

    @Override
    public Fragment getHolder() {
        return fragment;
    }

    @Override
    public Context getContext() {
        return fragment.getContext();
    }
}
