package com.frontmatic.sendwrite.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.frontmatic.sendwrite.ui.screen.NavGraphs
import com.frontmatic.sendwrite.ui.screen.destinations.ArchiveScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

data class NavMenuItem(
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val title: String,
    val direction: DirectionDestinationSpec
)

val bottomNavItems = listOf(
    NavMenuItem(
        title = "Active",
        iconSelected = Icons.Filled.DateRange,
        icon = Icons.Outlined.DateRange,
        direction = HomeScreenDestination
    ),
    NavMenuItem(
        title = "Archived",
        iconSelected = Icons.Filled.Archive,
        icon = Icons.Outlined.Archive,
        direction = ArchiveScreenDestination
    ),
    NavMenuItem(
        title = "Settings",
        iconSelected = Icons.Filled.Settings,
        icon = Icons.Outlined.Settings,
        direction = SettingsScreenDestination
    )
)

@Composable
fun BottomNavBar(
    navController: NavController,
) {

    NavigationBar() {
        bottomNavItems.forEach { item ->
            val isCurrentDestOnBackStack = navController.isRouteOnBackStack(item.direction)
            val icon = if (isCurrentDestOnBackStack) item.iconSelected else item.icon
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = isCurrentDestOnBackStack,
                onClick = {
                    navController.navigate(item.direction) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}
