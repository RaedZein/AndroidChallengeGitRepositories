package com.raedzein.assignment.utils

import android.content.res.Resources
import android.util.TypedValue

fun Int.dpToPx() =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()
