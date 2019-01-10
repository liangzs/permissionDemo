package liangzs.com.mypermission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import liangzs.com.mypermission.helper.AbstractPermissionHelper;
import liangzs.com.mypermission.model.PermissionAlwayDenied;
import liangzs.com.mypermission.model.PermissionFail;
import liangzs.com.mypermission.model.PermissionSuccess;
import liangzs.com.mypermission.model.RationalInterface;
import liangzs.com.mypermission.model.RequestPermission;

/**
 * @author liangzs
 * @Date 2019/1/9
 */
public class PermissionManager {


    public static boolean hasPermission(Context context, String... param) {
        for (String permission : param) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * activity权限请求
     * 1先检查有哪些权限没授权，如有未授权，再检查这个未授权是不是rational，
     * 2再核查是不是用户拒绝并且点击不再提示,只能通过onRequestPermissionsResult去核实
     * 3如果没有未授权的 success
     *
     * @param holder
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Activity holder, RationalInterface rationalInterface, int requestCode, String... permissions) {
        RequestPermission requestPermission = new RequestPermission.Builder(holder, requestCode, permissions)
                .setRationalInterface(rationalInterface).builder();
        if (!hasPermission(holder, permissions)) {
            List rationalList = getNationalPermission(requestPermission);
            //触发rationnal回调
            if (rationalList.size() > 0) {
                requestPermission.getRationalInterface().callback(requestPermission);
            } else {
                //走正常requestPermssion,这里包含了2
                requestPermission.getPermissionHelper().requesetPermissions(requestCode, permissions);
            }
            return;
        }
        //权限通过
        resolverAnnotateSuccess(holder, requestCode, permissions);

    }

    /**
     * activity权限请求
     * 1先检查有哪些权限没授权，如有未授权，再检查这个未授权是不是rational，
     * 2再核查是不是用户拒绝并且点击不再提示,只能通过onRequestPermissionsResult去核实
     * 3如果没有未授权的 success
     *
     * @param holder
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Fragment holder, RationalInterface rationalInterface, int requestCode, String... permissions) {
        RequestPermission requestPermission = new RequestPermission.Builder(holder, requestCode, permissions)
                .setRationalInterface(rationalInterface).builder();
        if (!hasPermission(holder.getActivity(), permissions)) {
            List rationalList = getNationalPermission(requestPermission);
            //触发rationnal回调
            if (rationalList.size() > 0) {
                requestPermission.getRationalInterface().callback(requestPermission);
            } else {
                //走正常requestPermssion,这里包含了2
                requestPermission.getPermissionHelper().requesetPermissions(requestCode, permissions);
            }
            return;
        }
        //权限通过
        resolverAnnotateSuccess(holder, requestCode, permissions);

    }
    /**
     * activity权限请求
     * 1先检查有哪些权限没授权，如有未授权，再检查这个未授权是不是rational，
     * 2再核查是不是用户拒绝并且点击不再提示,只能通过onRequestPermissionsResult去核实
     * 3如果没有未授权的 success
     *
     * @param holder
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(android.app.Fragment holder, RationalInterface rationalInterface, int requestCode, String... permissions) {
        RequestPermission requestPermission = new RequestPermission.Builder(holder, requestCode, permissions)
                .setRationalInterface(rationalInterface).builder();
        if (!hasPermission(holder.getActivity(), permissions)) {
            List rationalList = getNationalPermission(requestPermission);
            //触发rationnal回调
            if (rationalList.size() > 0) {
                requestPermission.getRationalInterface().callback(requestPermission);
            } else {
                //走正常requestPermssion,这里包含了2
                requestPermission.getPermissionHelper().requesetPermissions(requestCode, permissions);
            }
            return;
        }
        //权限通过
        resolverAnnotateSuccess(holder, requestCode, permissions);

    }


    /**
     * 检查rationnal
     *
     * @param requestPermission
     */
    private static List<String> getNationalPermission(RequestPermission requestPermission) {
        List<String> rationalList = new ArrayList<>();
        for (String permission : requestPermission.getPermissions()) {
            if (requestPermission.getPermissionHelper().shouldShowRequestPermissionRationale(permission)) {
                rationalList.add(permission);
            }
        }

        return rationalList;
    }

    /**
     * 运行成功的注解方法
     */
    private static void resolverAnnotateSuccess(Object object, int requestCode, String[] permissions) {
        Class cla = object.getClass();
        if (cla != null) {
            for (Method method : cla.getDeclaredMethods()) {
                PermissionSuccess an = method.getAnnotation(PermissionSuccess.class);
                if (an != null) {
                    try {
                        //防止方法是private
                        if (method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(object, requestCode, permissions);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 运行失败的注解方法
     */
    private static void resolverAnnotateFail(Object object, int requestCode, String[] permissions) {
        Class cla = object.getClass();
        if (cla != null) {
            for (Method method : cla.getDeclaredMethods()) {
                PermissionFail an = method.getAnnotation(PermissionFail.class);
                if (an != null) {
                    try {
                        //防止方法是private
                        if (method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(object, requestCode, permissions);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 运行失败的注解方法
     */
    private static void resolverAnnotateAlwayDenied(Object object, int requestCode, String[] permissions) {
        Class cla = object.getClass();
        if (cla != null) {
            for (Method method : cla.getMethods()) {
                PermissionAlwayDenied an = method.getAnnotation(PermissionAlwayDenied.class);
                if (an != null) {
                    try {
                        //防止方法是private
                        if (method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(object, requestCode, permissions);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 走PermissionActivity的onRequestPermissionsResult方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param permissionHelper
     */
    public static void onRequestPermissionsResult(int requestCode,
                                                  String[] permissions,
                                                  int[] grantResults,
                                                  AbstractPermissionHelper permissionHelper) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        if (!denied.isEmpty()) {
            resolverAnnotateFail(permissionHelper.getHolder(), requestCode, permissions);
            //检查是否选择了不再提醒按钮
            if (permissionHelper.somePermissionAlwayDenied(Arrays.asList(permissions))) {
                resolverAnnotateAlwayDenied(permissionHelper.getHolder(), requestCode, permissions);
            }
        }

        if (!granted.isEmpty() && denied.isEmpty()) {
            resolverAnnotateSuccess(permissionHelper.getHolder(), requestCode, permissions);
        }
    }

}
