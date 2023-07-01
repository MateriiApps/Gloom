package com.materiiapps.gloom.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller.SessionParams
import android.content.pm.PackageManager
import android.os.Build
import com.materiiapps.gloom.service.InstallService
import java.io.File

fun Context.installApks(vararg apks: File) {
    val packageInstaller = packageManager.packageInstaller
    val params = SessionParams(SessionParams.MODE_FULL_INSTALL).apply {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) setInstallScenario(PackageManager.INSTALL_SCENARIO_FAST)
    }

    val sessionId = packageInstaller.createSession(params)
    val session = packageInstaller.openSession(sessionId)

    apks.forEach { apk ->
        session.openWrite(apk.name, 0, apk.length()).use {
            it.write(apk.readBytes())
            session.fsync(it)
        }
    }

    val callbackIntent = Intent(this, InstallService::class.java)

    @SuppressLint("UnspecifiedImmutableFlag")
    val contentIntent = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getService(this, 0, callbackIntent, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getService(this, 0, callbackIntent, 0)
    }

    session.commit(contentIntent.intentSender)
    session.close()
}