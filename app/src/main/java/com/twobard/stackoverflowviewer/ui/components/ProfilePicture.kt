package com.twobard.stackoverflowviewer.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.theme.profilePicElevation
import com.twobard.stackoverflowviewer.ui.theme.roundedCornerSize

@Composable
fun ProfilePicture(user: User, size: Dp){
    Card(elevation = CardDefaults.cardElevation(profilePicElevation)) {
        AsyncImage(
            modifier = Modifier.size(size).clip(RoundedCornerShape(roundedCornerSize)),
            model = user.profileImage,
            contentDescription = null,
        )
    }
}