package com.frontmatic.sendwrite.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.frontmatic.sendwrite.ui.screen.NavGraphs
import com.frontmatic.sendwrite.ui.screen.appCurrentDestinationAsState
import com.frontmatic.sendwrite.ui.screen.destinations.ArchiveScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.Destination
import com.frontmatic.sendwrite.ui.screen.destinations.HomeScreenDestination
import com.frontmatic.sendwrite.ui.screen.destinations.SettingsScreenDestination
import com.frontmatic.sendwrite.ui.screen.startAppDestination
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
        title = "Active Jobs",
        iconSelected = Icons.Filled.EventAvailable,
        icon = Icons.Outlined.EventAvailable,
        direction = HomeScreenDestination
    ),
    NavMenuItem(
        title = "Archived Jobs",
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
            val currentDestination: Destination = navController.appCurrentDestinationAsState().value
                ?: NavGraphs.root.startAppDestination

            val isSelected = currentDestination == item.direction

            val icon = if (isSelected) item.iconSelected else item.icon
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.direction) {
                            popUpTo(NavGraphs.root) {
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
