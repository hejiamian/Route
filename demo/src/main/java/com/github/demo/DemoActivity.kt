package com.github.demo

import android.os.Bundle
import com.github.annotation.Route
import com.github.demo.databinding.ActivityDemoBinding

@Route(value = "activity:DemoActivity")
class DemoActivity : BaseActivity() {
    private lateinit var mViewBinding: ActivityDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
    }

}