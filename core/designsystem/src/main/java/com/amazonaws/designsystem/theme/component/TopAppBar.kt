package com.amazonaws.designsystem.theme.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.amazonaws.designsystem.theme.AppTheme
import com.amazonaws.designsystem.theme.icons.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    leftIcon: ImageVector,
    actionIcon: ImageVector,
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit,
    navigationIconContentDescription: String,
    actionIconContentDescription: String,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
) {
    CenterAlignedTopAppBar(title = {
        Text(text = stringResource(id = title))
    }, navigationIcon = {
        IconButton(onClick = onNavigationClick) {
            Icon(
                imageVector = leftIcon,
                contentDescription = navigationIconContentDescription,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("niaTopAppBar")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewTopAppBar() {
    BoxWithConstraints {
        AppTheme {
            TopAppBar(
                title = android.R.string.untitled,
                leftIcon = AppIcons.Soccer,
                actionIcon = AppIcons.Teams,
                onNavigationClick = {

                },
                onActionClick = {

                },
                navigationIconContentDescription = "left icon des",
                actionIconContentDescription = "action icon des"
            )
        }
    }
}