package com.example.datatheoremexercise

import android.content.Context
import androidx.room.Room
import com.example.datatheoremexercise.persistence.LoggedUrl
import com.example.datatheoremexercise.persistence.LoggedUrlDatabase
import de.robv.android.xposed.XposedBridge
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class DatabaseUtils(
    private val context: Context,
    private val onError: (Throwable) -> Unit
    ) {
    // db cannot be instantiated and used due to external cache dir issues
//    private val database = Room.databaseBuilder(
//        context,
//        LoggedUrlDatabase::class.java,
//        name = "loggedurls.db"
//    ).build()
//    private val dao = database.dao
    // buffered reader/writer cannot be be used due to emulator file permission errors
//    private val urlDao = UrlDao()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private val coroutineContext = Dispatchers.IO + SupervisorJob() + exceptionHandler
    private val scope = CoroutineScope(coroutineContext)

    fun getAllUrls(): List<String> {
        var urls = listOf<String>()
        scope.launch {
//            urls = urlDao.getAllUrls()
//            urls = dao.getAllUrls()
        }
        return urls
    }

    fun saveLoggedUrl(loggedUrl: String) {
        scope.launch {
            XposedBridge.log("adding to database url: ${loggedUrl}")
            // external cache dir and file permissions on emulator causing errors
//            urlDao.addUrl(loggedUrl)
//            dao.insertUrl(loggedUrl)
        }

    }

    fun removeLoggedUrl(loggedUrl: LoggedUrl) {
        scope.launch {
            XposedBridge.log("removing from database url: ${loggedUrl.url}")
                // external cache dir not recognised causing errors with Room
//            dao.deleteUrl(loggedUrl)
        }
    }

    fun cancelChildren() {
        // comment whichever method not needed but kept both for visibility
//        urlDao.flushStream()
        scope.coroutineContext.cancelChildren()
    }

    fun cancelScope() {
        // comment whichever method not needed but kept both for visibility
//        urlDao.closeStream()
        scope.cancel()
    }

}