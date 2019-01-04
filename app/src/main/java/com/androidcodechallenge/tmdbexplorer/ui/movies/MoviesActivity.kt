package com.androidcodechallenge.tmdbexplorer.ui.movies

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.androidcodechallenge.tmdbexplorer.R
import com.androidcodechallenge.tmdbexplorer.ui.base.BaseActivity
import com.androidcodechallenge.tmdbexplorer.ui.movies.listing.MoviesListingFragment


class MoviesActivity : BaseActivity() {

    override fun inject() {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()
    }

    private fun initializeUI() {
        supportFragmentManager.beginTransaction().replace(
            R.id.activity_main_layout_container,
            MoviesListingFragment()
        ).commit()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this, R.style.MyDialogTheme)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(getString(R.string.exit_title_txt))
            .setMessage(getString(R.string.exit_message_txt))
            .setPositiveButton(
                getString(R.string.exit_positive_btn_txt),
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton(getString(R.string.exit_negative_btn_txt), null)
            .show()
    }
}
