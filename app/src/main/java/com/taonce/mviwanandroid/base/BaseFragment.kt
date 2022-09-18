package com.taonce.mviwanandroid.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import pub.devrel.easypermissions.EasyPermissions

/**
 * @description BaseFragment
 * @author      Taonce.
 * @date        2022/8/20/22:04
 */
abstract class BaseFragment<VM : ViewBinding> : Fragment(), EasyPermissions.PermissionCallbacks {
    companion object {
        private const val TAG = "BaseFragment"
    }

    protected val viewBinding: VM by lazy { initViewBinding() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentWork(savedInstanceState)
    }

    abstract fun initViewBinding(): VM

    abstract fun fragmentWork(savedInstanceState: Bundle?)

    /**
     * 请求权限
     */
    fun requestPermission(
        requestCode: Int,
        perms: Array<String>,
        rational: String = "",
        hasPermission: () -> Unit = {}
    ) {
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            hasPermission()
        } else {
            EasyPermissions.requestPermissions(this@BaseFragment, rational, requestCode, *perms)
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