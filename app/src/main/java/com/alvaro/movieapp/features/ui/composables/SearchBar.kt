package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaro.movieapp.R
import com.alvaro.movieapp.features.ui.theme.Gray
import com.alvaro.movieapp.features.ui.theme.LightGray
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import io.eyram.iconsax.IconSax

@Composable
fun SearchBar(
    searchText: TextFieldValue,
    onSearchTextChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                color = Gray,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.titleMedium
            )
        },
        singleLine = true,
        trailingIcon = {
            Icon(
                painter = painterResource(id = IconSax.Linear.SearchNormal1),
                contentDescription = "Search Icon",
                tint = Gray,
                modifier = Modifier
                    .scale(scaleX = -1f, scaleY = 1f)
                    .size(22.dp),
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = LightGray,
            cursorColor = Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = LightGray,
            focusedTextColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    MovieAppTheme {
        SearchBar(
            searchText = TextFieldValue(),
            onSearchTextChanged = {}
        )
    }
}