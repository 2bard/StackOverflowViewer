package com.twobard.stackoverflowviewer.ui.components

import com.twobard.stackoverflowviewer.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twobard.stackoverflowviewer.ui.theme.loadingSpinnerSize

@Composable
@Preview
fun BoxScope.LoadingState(){
    Box(modifier = Modifier
        .wrapContentSize()
        .align(Alignment.Center), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(modifier = Modifier.size(loadingSpinnerSize))
            Text(stringResource(R.string.loading), style = MaterialTheme.typography.titleSmall)
        }

    }
}