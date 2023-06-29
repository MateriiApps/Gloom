package com.materiiapps.gloom.domain.manager

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.app.DownloadManager as AndroidDownloadManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.materiiapps.gloom.utils.showToast
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DownloadManager(
    private val context: Context,
    private val authManager: AuthManager
) {
    private val downloadManager = context.getSystemService<AndroidDownloadManager>()!!
    private val downloadScope = CoroutineScope(Dispatchers.IO)
    private val gloomDownloadFolder = File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS).resolve("Gloom")

    fun download(url: String) {
        val name = url.toUri().lastPathSegment!!
        downloadScope.launch {
            download(url, File(gloomDownloadFolder, name)).also {
                Handler(Looper.getMainLooper()).post {
                    if(it.exists()) context.showToast("Download complete: $name")
                }
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    suspend fun download(url: String, out: File): File {
        out.parentFile?.mkdirs()

        return suspendCoroutine { continuation ->
            val receiver = object : BroadcastReceiver() {
                var dlId = 0L

                @SuppressLint("Range")
                override fun onReceive(context: Context, intent: Intent) {
                    val id = intent.getLongExtra(AndroidDownloadManager.EXTRA_DOWNLOAD_ID, -1)

                    if(dlId != id) return

                    val (status, reason) = AndroidDownloadManager.Query().run {
                        setFilterById(dlId)

                        val cursor = downloadManager.query(this)
                            .apply { moveToFirst() }

                        if (cursor.count == 0) {
                            -1 to -1
                        } else {
                            val status = cursor.run { getInt(getColumnIndex(AndroidDownloadManager.COLUMN_STATUS)) }
                            val reason = cursor.run { getInt(getColumnIndex(AndroidDownloadManager.COLUMN_REASON)) }

                            status to reason
                        }
                    }

                    when(status) {
                        -1 -> {
                            context.unregisterReceiver(this)
                            continuation.resumeWithException(Error("Download canceled"))
                        }

                        AndroidDownloadManager.STATUS_SUCCESSFUL -> {
                            context.unregisterReceiver(this)
                            continuation.resume(out)
                        }

                        AndroidDownloadManager.STATUS_FAILED -> {
                            context.unregisterReceiver(this)
                            continuation.resumeWithException(
                                Error("Failed to download $url")
                            )
                        }

                        else -> {}
                    }
                }
            }

            context.registerReceiver(receiver, IntentFilter(AndroidDownloadManager.ACTION_DOWNLOAD_COMPLETE))

            receiver.dlId = AndroidDownloadManager.Request(url.toUri()).run {
                setTitle("Gloom: Downloading ${out.name}")
                setNotificationVisibility(AndroidDownloadManager.Request.VISIBILITY_VISIBLE)
                setDestinationUri(out.toUri())
                addRequestHeader(HttpHeaders.Authorization, authManager.authToken)
                downloadManager.enqueue(this)
            }
        }
    }
}