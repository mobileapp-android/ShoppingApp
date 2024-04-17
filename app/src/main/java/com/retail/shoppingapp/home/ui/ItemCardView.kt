package com.retail.shoppingapp.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.retail.shoppingapp.R
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.utils.onClick
import com.retail.shoppingapp.utils.onItemClick
import com.retail.shoppingapp.widgets.CoilImageLoader

@Composable
fun ItemCardView(
    product: Product,
    onClick: onItemClick
) {
    Surface(shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_small))) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(5.dp)
            .fillMaxWidth()
            .clickable { product.productId?.let { onClick.invoke(it) } }) {
            CoilImageLoader(
                imageUrl = product.thumbnail,
                contentDesc = product.title,
                modifier = Modifier.size(dimensionResource(id = R.dimen.box_size_medium)),
                placeholder = R.drawable.baseline_broken_image_24,
                contentScale = ContentScale.FillBounds
            )
            Text(text = product.title)
            Text(text = product.brand)
            Text(text = "Price : "+product.price.toString())
        }
    }
}