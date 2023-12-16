package es.ericd.twitchtop.utils

import android.util.Base64

abstract class EncodeUtil {

    companion object {
        fun encodeDataToBase64(data: ByteArray): String {
            return Base64.encodeToString(data, Base64.DEFAULT)
        }

        fun decodeBase64toData(encodedString: String): ByteArray {
            return Base64.decode(encodedString, Base64.DEFAULT)
        }

    }

}