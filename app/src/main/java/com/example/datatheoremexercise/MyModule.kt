package com.example.datatheoremexercise

import android.content.Context
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


class MyModule : IXposedHookLoadPackage {
    private lateinit var connectionHooks: ConnectionHooks
    private lateinit var context: Context

    @Throws(Throwable::class)
    override fun handleLoadPackage(p0: XC_LoadPackage.LoadPackageParam?) {
        p0?.let { lpparam ->
            XposedBridge.log("Loading package is: ${lpparam.packageName}")
            context = getContext(lpparam)
            XposedBridge.log("Module context is: $context")
            connectionHooks = ConnectionHooks(lpparam, context)
            connectionHooks.logAndSaveRequestUrl()
        } ?: kotlin.run {
            XposedBridge.log("Loading package was null")
        }
    }

    private fun getContext(lpparam: XC_LoadPackage.LoadPackageParam): Context {
        return XposedHelpers.callMethod(
            XposedHelpers.callStaticMethod(
                XposedHelpers.findClass(
                    "android.app.ActivityThread",
                    lpparam.classLoader
                ), "currentActivityThread"
            ), "getSystemContext"
        ) as Context
    }
}