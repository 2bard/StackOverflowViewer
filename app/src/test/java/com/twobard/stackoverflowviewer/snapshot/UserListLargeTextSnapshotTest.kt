package com.twobard.stackoverflowviewer.snapshot

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.twobard.stackoverflowviewer.ui.list.UserListPreviewProvider
import com.twobard.stackoverflowviewer.ui.list.UsersListScreen
import com.twobard.stackoverflowviewer.ui.theme.StackOverflowViewerTheme
import org.junit.Rule
import org.junit.Test

class UserListLargeTextSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6_PRO.copy(fontScale = 1.3f)
    )

    @Test
    fun userListLargeTextScreenPreview() {
        val provider = UserListPreviewProvider()
        provider.values.forEach {
            paparazzi.snapshot(name = "UsersList_LargeText_{${it.hashCode()}") {
                StackOverflowViewerTheme {
                    UsersListScreen(isLoading = false, users = it)
                }
            }
        }
    }

    @Test
    fun userListLargeTextLoadingScreenPreview() {

        paparazzi.snapshot(name = "UsersList_LargeText_IsLoading") {
            StackOverflowViewerTheme {
                UsersListScreen(isLoading = true, users = listOf())
            }
        }

    }


}