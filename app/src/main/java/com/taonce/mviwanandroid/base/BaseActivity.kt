package com.taonce.mviwanandroid.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import pub.devrel.easypermissions.EasyPermissions

/**
 * @description BaseActivity
 * @author      Taonce.
 * @date        2022/8/20/21:04
 */
abstract class BaseActivity<VM : ViewBinding> : AppCompatActivity(),
    EasyPermissions.PermissionCallbacks {
    companion object {
        private const val TAG = "BaseActivity"
    }

    protected val viewBinding: VM by lazy { initViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        activityWork(savedInstanceState)
    }

    abstract fun initViewBinding(): VM

    abstract fun activityWork(savedInstanceState: Bundle?)

    /**
     * 请求权限
     */
    fun requestPermission(requestCode: Int, perms: Array<String>, hasPermission: () -> Unit = {}) {
        if (EasyPermissions.hasPermissions(this, *perms)) {
            hasPermission()
        } else {
            EasyPermissions.requestPermissions(this@BaseActivity, "", requestCode, *perms)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted requestCode $requestCode, perms $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied requestCode $requestCode, perms: $perms")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}