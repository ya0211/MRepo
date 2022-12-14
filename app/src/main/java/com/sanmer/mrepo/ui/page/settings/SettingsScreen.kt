package com.sanmer.mrepo.ui.page.settings

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.sanmer.mrepo.R
import com.sanmer.mrepo.app.Config
import com.sanmer.mrepo.app.Const
import com.sanmer.mrepo.app.runtime.Configure
import com.sanmer.mrepo.ui.activity.log.LogActivity
import com.sanmer.mrepo.ui.component.EditItem
import com.sanmer.mrepo.ui.component.NormalItem
import com.sanmer.mrepo.ui.component.NormalTitle
import com.sanmer.mrepo.ui.component.PickerItem
import com.sanmer.mrepo.ui.expansion.navigatePopUpTo
import com.sanmer.mrepo.ui.navigation.SettingsGraph

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingsTopBar(
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            NormalTitle(text = stringResource(id = R.string.settings_title_normal))
            NormalItem(
                iconRes = R.drawable.brush_outline,
                text = stringResource(id = R.string.settings_app_theme),
                subText = stringResource(id = R.string.settings_app_theme_desc)
            ) {
                navController.navigatePopUpTo(SettingsGraph.AppTheme.route)
            }
            NormalItem(
                iconRes = R.drawable.health_outline,
                text = stringResource(id = R.string.settings_log_viewer),
                subText = stringResource(id = R.string.settings_log_viewer_desc)
            ) {
                val intent = Intent(context, LogActivity::class.java)
                context.startActivity(intent)
            }

            NormalTitle(text = stringResource(id = R.string.settings_title_app))
            EditItem(
                iconRes = R.drawable.cube_scan_outline,
                title = stringResource(id = R.string.settings_download_path),
                subtitle = Configure.downloadPath
            ) {
                Configure.downloadPath = it.ifEmpty { Const.DIR_PUBLIC_DOWNLOADS.absolutePath }
            }
            RepoItem()

        }
    }
}

@Composable
private fun RepoItem() {
    PickerItem(
        iconRes = R.drawable.cloud_connection_outline,
        itemList = Config.REPO_LIST,
        title = stringResource(id = R.string.settings_repo),
        selected = Configure.repoTag
    ) { tag, _ ->
        Configure.repoTag = tag
    }

    if (Configure.repoTag == Config.REPO_GITHUB_TAG) {
        EditItem(
            iconRes = R.drawable.code_outline,
            title = stringResource(id = R.string.settings_custom_repo, Config.displayRepoName),
            subtitle = Configure.repoGithub
        ) {
            Configure.repoGithub = it.ifEmpty { Const.REPO_GITHUB }
        }
        EditItem(
            iconRes = R.drawable.hierarchy_outline,
            title = stringResource(id = R.string.settings_custom_repo_branch),
            subtitle = Configure.repoBranch
        ) {
            Configure.repoBranch = it.ifEmpty { Const.REPO_BRANCH }
        }
    } else {
        EditItem(
            iconRes = R.drawable.code_outline,
            title = stringResource(id = R.string.settings_custom_url),
            subtitle = Configure.repoUrl,
            supportingText = {
                Text(text = stringResource(id = R.string.settings_custom_url_dialog))
            }
        ) {
            Configure.repoUrl = it.ifEmpty { Const.JSON_URL }
        }
    }
}

@Composable
private fun SettingsTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.page_settings),
            style = MaterialTheme.typography.titleLarge
        )
    },
    scrollBehavior = scrollBehavior
)