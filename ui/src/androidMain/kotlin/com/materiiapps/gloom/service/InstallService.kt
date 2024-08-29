package com.materiiapps.gloom.service

import android.app.Service
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Build
import android.os.IBinder
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.domain.manager.ToastManager
import com.materiiapps.gloom.ui.util.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InstallService : Service(), KoinComponent {

    private val toastManager: ToastManager by inject()
    private val messages = mapOf(
        PackageInstaller.STATUS_FAILURE to Res.strings.install_fail_generic,
        PackageInstaller.STATUS_FAILURE_BLOCKED to Res.strings.install_fail_blocked,
        PackageInstaller.STATUS_FAILURE_INVALID to Res.strings.install_fail_invalid,
        PackageInstaller.STATUS_FAILURE_CONFLICT to Res.strings.install_fail_conflict,
        PackageInstaller.STATUS_FAILURE_STORAGE to Res.strings.install_fail_storage,
        PackageInstaller.STATUS_FAILURE_INCOMPATIBLE to Res.strings.install_fail_incompatible,
        8 /* STATUS_FAILURE_TIMEOUT (Added in Android 14) */ to Res.strings.install_fail_incompatible,
    )

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (val statusCode = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -999)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {

                @Suppress("DEPRECATION")
                val confirmationIntent = when {
                    Build.VERSION.SDK_INT < 34 -> intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)!!
                    else -> intent.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java)!!
                }.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(confirmationIntent)
            }

            PackageInstaller.STATUS_SUCCESS -> toastManager.showToast(getString(Res.strings.install_success))

            PackageInstaller.STATUS_FAILURE_ABORTED -> toastManager.showToast(getString(Res.strings.install_cancelled))

            else -> messages[statusCode]?.let { toastManager.showToast(getString(it)) }
        }

        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

}