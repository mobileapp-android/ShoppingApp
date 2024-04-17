package com.retail.shoppingapp.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.retail.shoppingapp.R
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.ui.theme.GrayShade1
import com.retail.shoppingapp.ui.theme.Red
import com.retail.shoppingapp.ui.theme.White
import com.retail.shoppingapp.utils.onItemClick
import com.retail.shoppingapp.widgets.CoilImageLoader
import com.retail.shoppingapp.widgets.RatingBar

@Composable
fun ItemCardView(product: Product, itemClick: (Product) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_small)),
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .background(if (isSystemInDarkTheme()) GrayShade1 else White
                    )
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImageLoader(
                imageUrl = product.thumbnail,
                contentDesc = product.title,
                modifier = Modifier.size(dimensionResource(id = R.dimen.box_size_small)),
                placeholder = R.drawable.baseline_broken_image_24,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = product.brand,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = "$${product.price}", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        text = "(${product.discountPercentage}% Off)", modifier = Modifier.padding(
                            dimensionResource(id = R.dimen.padding_small)
                        ), style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            IconButton(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                onClick = { itemClick.invoke(product) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_button),
                    tint = Color.Black
                )
            }
        }
    }
}