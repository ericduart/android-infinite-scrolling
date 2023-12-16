package es.ericd.twitchtop.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

abstract class InfiniteSnackbar {

    companion object {
        fun getInfiniteSnackbar(view: View, message: String, closeBtn: String? = null): Snackbar {

            val snack = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)

            if (closeBtn != null) {
                snack.setAction(closeBtn) {
                    snack.dismiss()
                }
            }

            return snack

        }

    }

}