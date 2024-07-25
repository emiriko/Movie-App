package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alvaro.movieapp.features.ui.navigation.NavigationItem
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme

@Composable
fun BottomNavigationBar(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column (
        modifier = modifier
    ){
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            NavigationItem.items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.labelResId)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.labelResId),
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BottomNavigationBarPreview() {
    MovieAppTheme {
        BottomNavigationBar()
    }
}