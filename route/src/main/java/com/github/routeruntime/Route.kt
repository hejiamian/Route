package com.github.routeruntime

import android.app.Application

class Route {
    companion object {
        fun bind(target: Application): IntentBuilder {
            return IntentBuilder(target)
        }

    }
}