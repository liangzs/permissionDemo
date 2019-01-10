package liangzs.com.mypermission.model;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.List;

import liangzs.com.mypermission.helper.AbstractPermissionHelper;
import liangzs.com.mypermission.helper.ActivityPermissionHelper;
import liangzs.com.mypermission.helper.FragmentPermissionHelper;
import liangzs.com.mypermission.helper.SupportFragmentPermissionHelper;

/**
 * @author liangzs
 * @Date 2019/1/9
 */
public class RequestPermission {
    private String[] permissions;
    private int requestCode;
    private AbstractPermissionHelper permissionHelper;
    private RationalInterface rationalInterface;

    public RequestPermission(AbstractPermissionHelper permissionHelper, int requestCode, String[] permissions, RationalInterface rationalInterface) {
        this.permissionHelper = permissionHelper;
        this.permissions = permissions;
        this.requestCode = requestCode;
        this.rationalInterface = rationalInterface;
    }

    public RequestPermission() {

    }


    public AbstractPermissionHelper getPermissionHelper() {
        return permissionHelper;
    }


    public String[] getPermissions() {
        return permissions;
    }


    public int getRequestCode() {
        return requestCode;
    }


    public RationalInterface getRationalInterface() {
        return rationalInterface;
    }


    public final static class Builder {
        private List<String> granted;
        private List<String> denied;
        private String[] permissions;
        private int requestCode;
        private AbstractPermissionHelper permissionHelper;
        private RationalInterface rationalInterface;

        public Builder(Activity holder, int requestCode, String[] permissions) {
            permissionHelper = new ActivityPermissionHelper(holder);
            this.requestCode = requestCode;
            this.permissions = permissions;
        }

        public Builder(android.app.Fragment holder, int requestCode, String[] permissions) {
            permissionHelper = new FragmentPermissionHelper(holder);
            this.requestCode = requestCode;
            this.permissions = permissions;
        }

        public Builder(Fragment holder, int requestCode, String[] permissions) {
            permissionHelper = new SupportFragmentPermissionHelper(holder);
            this.requestCode = requestCode;
            this.permissions = permissions;
        }

        public Builder setGranted(List<String> granted) {
            this.granted = granted;
            return this;
        }

        public Builder setPermissionHelper(AbstractPermissionHelper permissionHelper) {
            this.permissionHelper = permissionHelper;
            return this;
        }

        public Builder setDenied(List<String> denied) {
            this.denied = denied;
            return this;
        }

        public Builder setPermissions(String[] permissions) {
            this.permissions = permissions;
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder setRationalInterface(RationalInterface rationalInterface) {
            this.rationalInterface = rationalInterface;
            return this;
        }

        public RequestPermission builder() {
            return new RequestPermission(permissionHelper, requestCode, permissions, rationalInterface);
        }
    }


}
