package com.twobard.stackoverflowviewer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twobard.stackoverflowviewer.R
import com.twobard.stackoverflowviewer.ui.theme.paddingMedium
import com.twobard.stackoverflowviewer.ui.theme.paddingSmall


@Preview
@Composable
fun ErrorListStatePreview(){
    Box {
        ErrorListState("No internet")
    }
}

@Composable
fun BoxScope.ErrorListState(errorText: String, onClickReload: () -> Unit = {}) {
    Card {
        Box {
            Column(modifier = Modifier
                .padding(paddingMedium)
                .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.error, errorText), style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(paddingSmall))

                Button(onClick = {
                    onClickReload()
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.refresh),
                        )

                        Spacer(modifier = Modifier.width(paddingSmall))

                        Text(stringResource(R.string.retry), style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}