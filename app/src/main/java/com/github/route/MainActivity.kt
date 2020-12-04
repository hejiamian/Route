package com.github.route

import android.os.Bundle
import com.github.annotation.Route
import com.github.routeruntime.Router

@Route(value = "activity://MainActivity")
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Router.bind(this).module("demo")
            .route("activity:DemoActivity")
            .start()
    }
}