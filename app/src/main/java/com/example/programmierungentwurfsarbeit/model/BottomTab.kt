package com.example.programmierungentwurfsarbeit.model

import androidx.annotation.StringRes
import com.example.programmierungentwurfsarbeit.R

enum class BottomTab(@StringRes val labelRes: Int) {
    Inspiration(R.string.inspiration),
    Activities(R.string.aktivitaeten),
    Impressum(R.string.impressum)
}
