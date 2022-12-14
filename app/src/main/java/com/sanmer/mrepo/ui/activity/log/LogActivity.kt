package com.sanmer.mrepo.ui.activity.log

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.sanmer.mrepo.app.runtime.Configure
import com.sanmer.mrepo.service.LogcatService
import com.sanmer.mrepo.ui.theme.AppTheme

class LogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        LogcatService.start(this)

        setContent {
            AppTheme(
                darkTheme = Configure.isDarkTheme(),
                themeColor = Configure.themeColor
            ) {

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LogScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogcatService.stop(this)
    }
}