package com.twobard.stackoverflowviewer.snapshot

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.twobard.stackoverflowviewer.ui.list.UserListPreviewProvider
import com.twobard.stackoverflowviewer.ui.list.UsersListScreen
import com.twobard.stackoverflowviewer.ui.theme.StackOverflowViewerTheme
import org.junit.Rule
import org.junit.Test

class UserListExtraLargeTextSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6_PRO.copy(fontScale = 2f)
    )

    @Test
    fun userListExtraLargeTextScreenPreview() {
        val provider = UserListPreviewProvider()
        provider.values.forEach {
            paparazzi.snapshot(name = "UsersList_ExtraLargeText_{${it.hashCode()}") {
                StackOverflowViewerTheme {
                    UsersListScreen(isLoading = false, users = it)
                }
            }
        }
    }

    @Test
    fun userListExtraLargeTextLoadingScreenPreview() {
        paparazzi.snapshot(name = "UsersList_ExtraLargeText_IsLoading") {
            StackOverflowViewerTheme {
                UsersListScreen(isLoading = true, users = listOf())
            }
        }

    }


}