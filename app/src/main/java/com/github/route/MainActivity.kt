package com.github.route

import android.os.Bundle
import com.github.annotation.Route

@Route(value = "activity://MainActivity")
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}