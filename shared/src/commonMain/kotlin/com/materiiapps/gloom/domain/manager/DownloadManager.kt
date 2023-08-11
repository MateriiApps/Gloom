package com.materiiapps.gloom.domain.manager

expect class DownloadManager {
    fun download(url: String, block: (String) -> Unit = {})
}