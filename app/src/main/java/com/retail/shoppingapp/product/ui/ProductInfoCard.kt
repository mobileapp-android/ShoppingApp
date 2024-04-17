package com.retail.shoppingapp.product.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.retail.shoppingapp.R
import com.retail.shoppingapp.storage.tables.Product
import com.retail.shoppingapp.ui.theme.Gray
import com.retail.shoppingapp.ui.theme.GrayShade1
import com.retail.shoppingapp.ui.theme.White
import com.retail.shoppingapp.widgets.RatingBar


@Composable
fun DrawProductInfoCard(product: Product) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))
//        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_small)),
//        shadowElevation = dimensionResource(id = R.dimen.elevation_small)
    ) {
        Column(
            modifier = Modifier
                .background(if (isSystemInDarkTheme()) GrayShade1 else White)
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = product.brand,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start =
                        dimensionResource(id = R.dimen.padding_small)
                    ),
                )
                Spacer(modifier = Modifier.weight(2f))
            }
            Text(text = product.category, style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start =
                    dimensionResource(id = R.dimen.padding_small), top = dimensionResource(id = R.dimen.padding_small)
                ),)

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Text(text = "$${product.price}",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start =
                    dimensionResource(id = R.dimen.padding_small)
                    ),)
                Text(
                    text = "(${product.discountPercentage}% Off)",
                    modifier = Modifier.padding(start =
                        dimensionResource(id = R.dimen.padding_small)
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "Stock: ${product.stock}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start =
                    dimensionResource(id = R.dimen.padding_small), top = dimensionResource(id = R.dimen.padding_small)
                ),
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start =
                    dimensionResource(id = R.dimen.padding_small), top = dimensionResource(id = R.dimen.padding_small)
                ),
            )

        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DrawProductDetailsCardViewPreview() {
    DrawProductInfoCard(
        Product(
            brand = "Samsung",
            category = "Smartphone",
            description = "Galaxy S24 Ultra",
            discountPercentage = 25.0f,
            price = 8400,
            rating = 5.0f,
            stock = 120,
            title = "Samsung Galaxy S24 Ultra"
        )
    )
}