package com.garrymckee.powdermills

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionsListener {

    private val viewModel: MainViewModel by viewModels()
    lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        viewModel.shouldCheckLocationPermissions.observe(this, Observer {
            it.getContentIfNotHandled()?.let { shouldCheckForLocationPermissions ->
                if (shouldCheckForLocationPermissions) {
                    checkLocationPermissions()
                }
            }
        })

        return super.onCreateView(name, context, attrs)

    }

    private fun checkLocationPermissions() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            viewModel.setLocationPermission(true)
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) =
        Toast.makeText(this, R.string.location_permissions_rationale, Toast.LENGTH_LONG).show()

    override fun onPermissionResult(granted: Boolean) = viewModel.setLocationPermission(granted)
}
