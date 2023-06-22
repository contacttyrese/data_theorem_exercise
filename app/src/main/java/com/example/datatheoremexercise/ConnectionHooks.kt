package com.example.datatheoremexercise

import android.content.Context
import com.example.datatheoremexercise.persistence.LoggedUrl
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class ConnectionHooks(
    private val lpparam: XC_LoadPackage.LoadPackageParam,
    private val context: Context
    ) {
//    private val dbUtils = DatabaseUtils(context) { throwable ->
//        XposedBridge.log(throwable)
//    }
    fun logAndSaveRequestUrl() {
        XposedBridge.log("running method log and save request url")

        val okHttpClassName = "okhttp3.OkHttpClient"
        val okHttpMethodName = "newCall"
        val okHttpClassString = "okhttp3.Request"
        val volleyClassName = "com.android.volley.toolbox.StringRequest"
        val volleyResponseListenerClassString = "com.android.volley.Response.Listener"
        val volleyErrorListenerClassString = "com.android.volley.Response.ErrorListener"
        val httpClassName = "java.net.URL"

        if (lpparam.packageName.equals("com.datatheorem.xposedtest")) {
            XposedBridge.log("running inside sample app package")

            XposedHelpers.findAndHookMethod(okHttpClassName, lpparam.classLoader, okHttpMethodName,
                okHttpClassString, object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        super.afterHookedMethod(param)
                        XposedBridge.log("running after method - $okHttpClassName:$okHttpMethodName")
                        logRequestUrl(param)
                    }
                }
            )

            XposedHelpers.findAndHookConstructor(volleyClassName, lpparam.classLoader,
                "int", "java.lang.String",
                volleyResponseListenerClassString, volleyErrorListenerClassString,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        super.afterHookedMethod(param)
                        XposedBridge.log("running after constructor - $volleyClassName")
                        logRequestUrl(param, 1)
                    }
                }
            )

            XposedHelpers.findAndHookConstructor(httpClassName, lpparam.classLoader, "" +
                    "java.lang.String",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        super.afterHookedMethod(param)
                        XposedBridge.log("running after constructor - $httpClassName")
                        logRequestUrl(param)
                    }
                }
            )

            // method to stop db operations or buffered reader/writer operations
//            XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader,
//                "onStop", object: XC_MethodHook() {
//                    override fun beforeHookedMethod(param: MethodHookParam?) {
//                        super.beforeHookedMethod(param)
//                        XposedBridge.log("stopping db operations")
//                        dbUtils.cancelChildren()
//                    }
//
//                }
//            )
//
            // method to finish db operations or buffered reader/writer operations
//            XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader,
//                "onDestroy", object: XC_MethodHook() {
//                    override fun beforeHookedMethod(param: MethodHookParam?) {
//                        super.beforeHookedMethod(param)
//                        XposedBridge.log("closing db")
//                        dbUtils.cancelScope()
//                    }
//
//                }
//            )

        } else {
            XposedBridge.log("Not in correct loading package")
        }
    }

    private fun logRequestUrl(param: XC_MethodHook.MethodHookParam?, element: Int = 0) {
        var requestUrl = ""

        param?.let { parameter ->
            parameter.args?.let { arguments ->
                requestUrl = arguments[element].toString()
                if (requestUrl.contains("url=", true)) {
                    val newUrl = requestUrl.split("url=", "}", ignoreCase = true)
                    requestUrl = newUrl[1]
                }
                XposedBridge.log("Request url is: $requestUrl")
                // method to save url as String or as LoggedUrl
//                dbUtils.saveLoggedUrl(requestUrl)

            } ?: kotlin.run {
                XposedBridge.log("argument was null")
            }
        } ?: kotlin.run {
            XposedBridge.log("param was null")
        }
    }

}