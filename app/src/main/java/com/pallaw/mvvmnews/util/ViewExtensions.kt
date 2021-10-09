package com.pallaw.mvvmnews.util

import android.view.View

fun View.toggleVisibility(enable: Boolean) {
    this.visibility = if (enable) View.VISIBLE else View.GONE
}