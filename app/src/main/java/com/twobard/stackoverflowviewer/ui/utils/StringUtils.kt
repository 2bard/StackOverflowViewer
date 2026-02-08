package com.twobard.stackoverflowviewer.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun followMap(resIds: List<Int>): Map<Int, String> =
    resIds.associateWith { stringResource(it) }