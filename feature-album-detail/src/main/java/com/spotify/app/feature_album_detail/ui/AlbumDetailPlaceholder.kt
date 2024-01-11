package com.spotify.app.feature_album_detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spotify.app.core_base.shared.util.BaseConstants
import com.valentinilk.shimmer.shimmer

@Composable
fun AlbumDetailPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .shimmer()
    ) {
        repeat(12) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 6.dp)
                    .background(Color.White.copy(alpha = BaseConstants.SHIMMER_ALPHA))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .width(50.dp)
                            .height(50.dp)
                            .background(Color.White.copy(alpha = BaseConstants.SHIMMER_ALPHA))
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    Column {

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .width(150.dp)
                                .height(10.dp)
                                .background(Color.White.copy(alpha = BaseConstants.SHIMMER_ALPHA))
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .width(100.dp)
                                .height(10.dp)
                                .background(Color.White.copy(alpha = BaseConstants.SHIMMER_ALPHA))
                        )

                        Spacer(modifier = Modifier.fillMaxHeight())
                    }

                    Spacer(modifier = Modifier.fillMaxWidth())
                }
            }
        }

    }
}