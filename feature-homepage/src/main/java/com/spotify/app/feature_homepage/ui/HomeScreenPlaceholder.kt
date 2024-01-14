package com.spotify.app.feature_homepage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spotify.app.core_base.shared.util.BaseConstants
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeScreenPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .shimmer()
    ) {
        repeat(3) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 10.dp)
            ) {
                repeat(4) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .width(200.dp)
                                .height(200.dp)
                                .background(Color.White.copy(alpha = BaseConstants.SHIMMER_ALPHA))
                                .padding(horizontal = 6.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .width(120.dp)
                                .height(8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = BaseConstants.SHIMMER_ALPHA))
                        )
                    }
                }
            }
        }
    }
}
