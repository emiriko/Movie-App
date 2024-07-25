package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alvaro.movieapp.core.presentation.state.MovieState
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.features.home.CategoryMovies
import com.alvaro.movieapp.features.home.PopularMovie
import com.alvaro.movieapp.features.ui.theme.LightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TabMenu(
    tabs: List<String>,
    tabState: LazyGridState,
    tabIndex: Int,
    onTabClick: (Int) -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        containerColor = Color.Transparent,
        edgePadding = 0.dp,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                color = LightGray,
            )
        },
        divider = { },
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                    )
                },
                selected = tabIndex == index,
                onClick = {
                    onTabClick(index)
                    coroutineScope.launch {
                        tabState.animateScrollToItem(0)
                    }
                },
            )
        }
    }
}