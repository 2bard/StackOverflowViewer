package com.twobard.stackoverflowviewer.snapshot

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.twobard.stackoverflowviewer.ui.list.UserListPreviewProvider
import com.twobard.stackoverflowviewer.ui.list.UsersListScreen
import com.twobard.stackoverflowviewer.ui.theme.StackOverflowViewerTheme
import org.junit.Rule
import org.junit.Test

class UserListSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6_PRO
    )

    @Test
    fun userListLargeTextScreenPreview() {
        val provider = UserListPreviewProvider()
        provider.values.forEach {
            paparazzi.snapshot (name = "UsersList_{${it.hashCode()}") {
                StackOverflowViewerTheme {
                    UsersListScreen(isLoading = false, users = it, )
                }
            }
        }
    }

    @Test
    fun userListLargeTextLoadingScreenPreview() {
        paparazzi.snapshot (name = "UsersList_IsLoading") {
            StackOverflowViewerTheme {
                UsersListScreen(isLoading = true, users = listOf(), )
            }
        }
    }
}