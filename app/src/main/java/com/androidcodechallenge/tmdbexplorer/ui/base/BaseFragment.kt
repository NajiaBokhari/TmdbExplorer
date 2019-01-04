package com.androidcodechallenge.tmdbexplorer.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidcodechallenge.tmdbexplorer.AndroidApp
import com.androidcodechallenge.tmdbexplorer.injection.ViewModule

abstract class BaseFragment : Fragment() {

    val Fragment.app: AndroidApp
        get() = activity?.application as AndroidApp


    val component by lazy {
        app.component.plus(ViewModule(activity))
    }

    private val loadingFragment: LoadingFragment =
        LoadingFragment()

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    fun Fragment.toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        if (!loadingFragment.isAdded) {
            loadingFragment.show(fragmentManager, "loading")
        }
    }

    fun hideLoading() {
        if (loadingFragment.isAdded) {
            loadingFragment.dismiss()
        }
    }
}
