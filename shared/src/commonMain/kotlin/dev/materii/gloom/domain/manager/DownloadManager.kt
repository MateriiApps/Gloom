package dev.materii.gloom.domain.manager

expect class DownloadManager {

    fun download(url: String, block: (String) -> Unit = {})
}