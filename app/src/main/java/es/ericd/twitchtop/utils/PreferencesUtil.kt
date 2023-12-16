package es.ericd.twitchtop.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil {
    companion object {
        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)

        }

        fun getToken(context: Context): String? {
            val prefs = getPreferences(context)

            return prefs.getString(Constants.PREFERENCES_TOKEN_KEY,null)
        }

        fun setToken(context: Context, token: String) {
            val prefs = getPreferences(context)

            with(prefs.edit()) {
                putString(Constants.PREFERENCES_TOKEN_KEY, token)
                apply()
            }

        }

        fun getIV(context: Context): String? {
            val prefs = getPreferences(context)

            return prefs.getString(Constants.PREFERENCES_IV_KEY, null)
        }

        fun setIV(context: Context, iv: String) {
            val prefs = getPreferences(context)

            with(prefs.edit()) {
                putString(Constants.PREFERENCES_IV_KEY, iv)
                apply()
            }

        }



    }
}