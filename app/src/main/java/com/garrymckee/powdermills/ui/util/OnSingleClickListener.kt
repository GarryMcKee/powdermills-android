package com.garrymckee.powdermills.ui.util

import android.view.View

/*
Solution from:
https://stackoverflow.com/questions/51060762/illegalargumentexception-navigation-destination-xxx-is-unknown-to-this-navcontr

This class prevents rapid double clicking of buttons and can help prevent crashes caused by the navigation component when navigating back and forth between destinations quickly
Seems to bve a shortcoming in the Navigation Library at this point.
 */
class OnSingleClickListener : View.OnClickListener {

    private val onClickListener: View.OnClickListener

    constructor(listener: View.OnClickListener) {
        onClickListener = listener
    }

    constructor(listener: (View) -> Unit) {
        onClickListener = View.OnClickListener { listener.invoke(it) }
    }

    override fun onClick(v: View) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
            previousClickTimeMillis = currentTimeMillis
            onClickListener.onClick(v)
        }
    }

    companion object {
        // Tweak this value as you see fit. In my personal testing this
        // seems to be good, but you may want to try on some different
        // devices and make sure you can't produce any crashes.
        private const val DELAY_MILLIS = 200L

        private var previousClickTimeMillis = 0L
    }

}