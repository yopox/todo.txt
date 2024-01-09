package fr.yopox.todotxt

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Grading
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screens(val route: String) {
    data object All : Screens("all_tasks")
    data object Work : Screens("work_tasks")
    data object Project : Screens("project_tasks")
    data object Context : Screens("context_tasks")
}

//initializing the data class with default parameters
data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    //function to get the list of bottomNavigationItems
    private fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "All",
                icon = Icons.Filled.Grading,
                route = Screens.All.route
            ),
            BottomNavigationItem(
                label = "Work",
                icon = Icons.Filled.Work,
                route = Screens.Work.route
            ),
            BottomNavigationItem(
                label = "Project",
                icon = Icons.Filled.ViewTimeline,
                route = Screens.Project.route
            ),
            BottomNavigationItem(
                label = "Context",
                icon = Icons.Filled.TravelExplore,
                route = Screens.Context.route
            ),
        )
    }

    @Composable
    fun BottomNavigationBar() {
        var navigationSelectedItem by remember { mutableIntStateOf(0) }
        val navController = rememberNavController()
        val scheme = JetpackUtils.getScheme()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    containerColor = scheme.surface
                ) {
                    BottomNavigationItem().bottomNavigationItems()
                        .forEachIndexed { index, navigationItem ->
                            NavigationBarItem(
                                selected = index == navigationSelectedItem,
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = scheme.secondaryContainer,
                                    selectedIconColor = scheme.onSecondaryContainer,
                                    selectedTextColor = scheme.onSurface,
                                    disabledIconColor = scheme.onSurfaceVariant,
                                    disabledTextColor = scheme.onSurfaceVariant,
                                ),
                                label = {
                                    Text(navigationItem.label)
                                },
                                icon = {
                                    Icon(
                                        navigationItem.icon,
                                        contentDescription = navigationItem.label
                                    )
                                },
                                onClick = {
                                    navigationSelectedItem = index
                                    navController.navigate(navigationItem.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screens.All.route,
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
                composable(Screens.All.route) {
                    TasksList()
                }
                composable(Screens.Work.route) {
                    val regex = Regex("\\B@work\\b")
                    TasksList {
                        regex.containsMatchIn(it.text)
                    }
                }
                composable(Screens.Project.route) {
                    TasksList()
                }
                composable(Screens.Context.route) {
                    TasksList()
                }
            }
        }
    }
}