package liangzs.com.mypermission.helper;

import android.content.Context;

import java.util.List;

/**
 * 因为不同具体对象的权限申请不一致，
 * 有activity，fragment，4v包.fragment
 *
 * @author liangzs
 * @Date 2019/1/9
 */
public abstract class AbstractPermissionHelper<T> {
    /**
     * 是否rationnal
     * @param perm
     * @return
     */
    public abstract boolean shouldShowRequestPermissionRationale(String perm);

    /**
     * 权限请求
     * @param requestCode
     * @param perm
     */
    public abstract void requesetPermissions(int requestCode, String[] perm);

    /**
     * 是否选择不再提示
     * @param perms
     * @return
     */
    public abstract boolean somePermissionAlwayDenied( List<String> perms);


    public abstract T getHolder();

    public abstract Context getContext();

}
