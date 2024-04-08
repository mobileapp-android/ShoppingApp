package com.retail.shoppingapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.retail.shoppingapp.R
import com.retail.shoppingapp.ui.theme.RatingBarColor
import com.retail.shoppingapp.utils.Constants.MAX_RATING

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Int = MAX_RATING,
    starSize: Dp = dimensionResource(id = R.dimen.rating_bar_size_large),
) {
    Row(modifier = Modifier) {
        repeat(maxRating) { index ->
            val starId = if (index < rating) R.drawable.star_filled else R.drawable.star_outline
            Image(
                painter = painterResource(starId),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_tiny))
                    .size(starSize),
                alpha = if (index < rating) 1f else 0.3f,
                colorFilter = ColorFilter.tint(color = RatingBarColor)
            )
        }
    }
}