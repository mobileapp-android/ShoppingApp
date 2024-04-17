package com.retail.shoppingapp.widgets

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.retail.shoppingapp.R
import com.retail.shoppingapp.ui.theme.Black
import com.retail.shoppingapp.ui.theme.DarkBrownShade
import com.retail.shoppingapp.ui.theme.LightGray
import com.retail.shoppingapp.ui.theme.TopBarColour
import com.retail.shoppingapp.ui.theme.White
import com.retail.shoppingapp.utils.Constants.EMPTY
import com.retail.shoppingapp.utils.onClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String = EMPTY,
    navigationIcon: ImageVector? = null,
    actionIcon: ImageVector? = null,
    navOnClick: onClick = {},
    actionOnClick: onClick = {}
) {
    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(if (isSystemInDarkTheme()) Black else TopBarColour),
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    color = if (isSystemInDarkTheme()) White else LightGray
                )
            },
            navigationIcon = {
                if (navigationIcon != null) IconButton(onClick = { navOnClick.invoke() }) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = stringResource(id = R.string.nav_button),
                        tint = if (isSystemInDarkTheme()) White else LightGray
                    )
                }
            },
            actions = {
                if (actionIcon != null) IconButton(onClick = { actionOnClick.invoke() }) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = stringResource(id = R.string.action_button),
                        tint = if (isSystemInDarkTheme()) White else LightGray
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
        if (isSystemInDarkTheme()) {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = dimensionResource(id = R.dimen.thickness_tiny),
                color = DarkBrownShade
            )
        }
    }
}