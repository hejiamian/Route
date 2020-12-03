package com.github.routeruntime

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

class IntentBuilder(private val target: Application) {
    private var mClassName: String? = null
    private val mBundle = Bundle()

    private val mRouteBinding by lazy {
        try {
            val clazz = Class.forName("${target.packageName}.RouteBinding")
            val constructor = clazz.getDeclaredConstructor()
            constructor.newInstance()
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    private fun build(): Intent {
        return Intent().apply {
            mClassName?.let {
                setClassName(target.packageName, it)
                putExtras(mBundle)
            }
        }
    }

    fun start() = target.startActivity(build())

    fun route(route: String) = apply {
        try {
            mRouteBinding?.let {
                val method = it::class.java.getDeclaredMethod("get", String::class.java)
                mClassName = method.invoke(mRouteBinding, route) as String
            }
//            val functions = mRouteBinding!!::class.declaredMemberFunctions
//            if (functions.isNotEmpty()) {
//                mClassName = functions.elementAt(0).call(route) as String
//            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun putExtra(name: String, value: Boolean) = apply {
        mBundle.putBoolean(name, value)
    }

    fun putExtra(name: String?, value: Byte) = apply {
        mBundle.putByte(name, value)
    }

    fun putExtra(name: String?, value: Char) = apply {
        mBundle.putChar(name, value)
    }

    fun putExtra(name: String?, value: Short) = apply {
        mBundle.putShort(name, value)
    }

    fun putExtra(name: String?, value: Int) = apply {
        mBundle.putInt(name, value)
    }


    fun putExtra(name: String?, value: Long) = apply {
        mBundle.putLong(name, value)
    }

    fun putExtra(name: String?, value: Float) = apply {
        mBundle.putFloat(name, value)
    }

    fun putExtra(name: String?, value: Double) = apply {
        mBundle.putDouble(name, value)
    }

    fun putExtra(name: String?, value: String?) = apply {
        mBundle.putString(name, value)
    }


    fun putExtra(name: String?, value: CharSequence?) = apply {
        mBundle.putCharSequence(name, value)
    }

    fun putExtra(name: String?, value: Parcelable?) = apply {
        mBundle.putParcelable(name, value)
    }

    fun putExtra(name: String?, value: Array<Parcelable?>?) = apply {
        mBundle.putParcelableArray(name, value)
    }


    fun putParcelableArrayListExtra(
        name: String?,
        value: ArrayList<out Parcelable?>?
    ) = apply {
        mBundle.putParcelableArrayList(name, value)
    }

    fun putIntegerArrayListExtra(name: String?, value: ArrayList<Int?>?) = apply {
        mBundle.putIntegerArrayList(name, value)
    }

    fun putStringArrayListExtra(
        name: String?,
        value: ArrayList<String?>?
    ) = apply {
        mBundle.putStringArrayList(name, value)
    }


    fun putCharSequenceArrayListExtra(
        name: String?,
        value: ArrayList<CharSequence?>?
    ) = apply {
        mBundle.putCharSequenceArrayList(name, value)
    }


    fun putExtra(name: String?, value: Serializable?) = apply {
        mBundle.putSerializable(name, value)
    }


    fun putExtra(name: String?, value: BooleanArray?) = apply {
        mBundle.putBooleanArray(name, value)
    }

    fun putExtra(name: String?, value: ByteArray?) = apply {
        mBundle.putByteArray(name, value)
    }

    fun putExtra(name: String?, value: ShortArray?) = apply {
        mBundle.putShortArray(name, value)
    }

    fun putExtra(name: String?, value: CharArray?) = apply {
        mBundle.putCharArray(name, value)
    }

    fun putExtra(name: String?, value: IntArray?) = apply {
        mBundle.putIntArray(name, value)
    }

    fun putExtra(name: String?, value: LongArray?) = apply {
        mBundle.putLongArray(name, value)
    }


    fun putExtra(name: String?, value: FloatArray?) = apply {
        mBundle.putFloatArray(name, value)
    }

    fun putExtra(name: String?, value: DoubleArray?) = apply {
        mBundle.putDoubleArray(name, value)
    }

    fun putExtra(name: String?, value: Array<String?>?) = apply {
        mBundle.putStringArray(name, value)
    }

    fun putExtra(
        name: String?,
        value: Array<CharSequence?>?
    ) = apply {
        mBundle.putCharSequenceArray(name, value)
    }

    fun putExtra(name: String?, value: Bundle?) = apply {
        mBundle.putBundle(name, value)
    }

    fun putExtras(extras: Bundle) = apply {
        mBundle.putAll(extras)
    }
}