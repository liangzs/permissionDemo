package liangzs.com.mypermission;

import android.app.AlertDialog;
import android.content.DialogInterface;

import liangzs.com.mypermission.model.RationalInterface;
import liangzs.com.mypermission.model.RequestPermission;

/**
 * @author liangzs
 * @Date 2019/1/10
 */
public class RationalDialog implements RationalInterface {

    @Override
    public void callback(final RequestPermission requestPermission) {
        new AlertDialog.Builder(requestPermission.getPermissionHelper().getContext())
                .setCancelable(false)
                .setTitle(R.string.rational_title)
                .setMessage(R.string.rational_message)
                .setPositiveButton(R.string.rational_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission.getPermissionHelper().requesetPermissions(requestPermission.getRequestCode(), requestPermission.getPermissions());
                    }
                })
                .setNegativeButton(R.string.rational_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
