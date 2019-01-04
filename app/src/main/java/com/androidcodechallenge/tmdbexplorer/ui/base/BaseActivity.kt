package com.androidcodechallenge.tmdbexplorer.ui.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.androidcodechallenge.tmdbexplorer.AndroidApp
import com.androidcodechallenge.tmdbexplorer.R
import com.androidcodechallenge.tmdbexplorer.injection.ViewModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.CollapsingToolbarLayout

abstract class BaseActivity : AppCompatActivity() {

    val Activity.app: AndroidApp
        get() = application as AndroidApp

    val component by lazy {
        app.component.plus(ViewModule(this))
    }

    abstract fun inject()

    private val loadingFragment: LoadingFragment =
        LoadingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        if (!loadingFragment.isAdded) {
            loadingFragment.show(supportFragmentManager, "loading")
        }
    }

    fun hideLoading() {
        if (loadingFragment.isAdded) {
            loadingFragment.dismiss()
        }
    }


    fun Activity.checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    fun Activity.circularRevealedAtCenter(view: View) {
        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2
        val finalRadius = Math.max(view.width, view.height)

        if (checkIsMaterialVersion() && view.isAttachedToWindow) {
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())
            view.visibility = View.VISIBLE
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            anim.duration = 550
            anim.start()
        }
    }

      fun setToolbarHomeIndicator(toolbar: Toolbar?, titleToolbar: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            title = titleToolbar
        }
    }
      fun setAppBarSize(): Int {
        val idStatusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (idStatusBarHeight > 0) {
            resources.getDimensionPixelSize(idStatusBarHeight)
        } else {
            0
        }
    }

      fun setAppBarDimensions(toolbar: Toolbar?) {
        if (checkIsMaterialVersion()) {
            toolbar!!.layoutParams = (toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams).apply {
                topMargin = setAppBarSize()
            }
        }
    }

      fun requestGlideListener(view: View) : RequestListener<Drawable>{
        return object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                circularRevealedAtCenter(view)
                return false
            }
        }
    }

}