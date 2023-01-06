package com.sanmer.mrepo

import android.app.Application
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.sanmer.mrepo.app.Config
import com.sanmer.mrepo.data.Constant
import com.sanmer.mrepo.provider.FileServer
import com.sanmer.mrepo.utils.MagiskUtils
import com.sanmer.mrepo.utils.MediaStoreUtils
import com.sanmer.mrepo.utils.NotificationUtils
import com.sanmer.mrepo.utils.timber.DebugTree
import com.sanmer.mrepo.utils.timber.ReleaseTree
import com.sanmer.mrepo.works.Works
import com.tencent.mmkv.MMKV
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        MMKV.initialize(this)
        Constant.init(this)
        Works.init(this)
        MediaStoreUtils.init(this)
        NotificationUtils.init(this)

        Firebase.crashlytics.setCrashlyticsCollectionEnabled(Config.ANALYTICS_COLLECTION)
        Firebase.analytics.setAnalyticsCollectionEnabled(Config.ANALYTICS_COLLECTION)
        Timber.i("Firebase data collection: ${Config.ANALYTICS_COLLECTION}")

        if (Config.WORKING_MODE == Config.MODE_ROOT) {
            FileServer.init(this)
            MagiskUtils.init()
        }

        MagiskUtils.getManager(this)
        Timber.d("Magisk manager: ${MagiskUtils.packageName}")
    }
}