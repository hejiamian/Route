package com.github.routeruntime

import android.content.Context

class Router {
    companion object {
        fun bind(target: Context): IntentBuilder {
            return IntentBuilder(target.applicationContext)
        }

    }
}