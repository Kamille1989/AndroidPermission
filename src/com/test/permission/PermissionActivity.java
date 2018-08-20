package com.test.permission;

import com.test.permission.PermissionUtils.PermissionGrant;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class PermissionActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback
{
	private LinearLayout layout;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
 
        boolean flag1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
        Log.d(PermissionUtils.TAG, "FLAG1: " + flag1);
        if (flag1)
        {
        	boolean flag2 = !PermissionUtils.CheckPermissions(this);
            Log.d(PermissionUtils.TAG, "FLAG2: " + flag2);
        	if (flag2)
        	{
        		PermissionUtils.requestMultiPermissions(this, new PermissionGrant() {
					
					@Override
					public void onPermissionGranted(int requestCode) {
						
					}
				});
        	}
            else
            {
                StartMainActivity();
            }
        }
        else
        {
            StartMainActivity();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    	StringBuilder permission = new StringBuilder(); 
    	for (int i = 0; i < grantResults.length; ++i)
    	{
    		if (grantResults[i] == PackageManager.PERMISSION_DENIED)
    		{
    			permission.append(permissions[i] + "\n");
    		}
    	}
    	if (!PermissionUtils.CheckPermissions(this))
    	{
        	PermissionUtils.openSettingActivity(this, "我们需要这些权限以启动应用\n" + permission.toString());
    	}
        else
        {
            StartMainActivity();
        }
    }
 
    private void StartMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();	
    }
}