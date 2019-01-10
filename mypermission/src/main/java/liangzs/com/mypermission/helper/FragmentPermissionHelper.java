package liangzs.com.mypermission.helper;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * @author liangzs
 * @Date 2019/1/9
 */
public class FragmentPermissionHelper extends AbstractPermissionHelper<Fragment> {
    private Fragment fragment;

    public FragmentPermissionHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        return fragment.shouldShowRequestPermissionRationale(perm);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        return fragment.getActivity();
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
}
