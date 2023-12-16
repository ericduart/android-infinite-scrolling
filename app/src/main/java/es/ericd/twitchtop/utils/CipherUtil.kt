package es.ericd.twitchtop.utils

import es.ericd.twitchtop.classes.EncryptData
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CipherUtil {
    companion object {

        fun encrypt(strToEncrypt: String): EncryptData {
            val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
            val key = generateKey()
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val cipherText = cipher.doFinal(plainText)
            val iv = cipher.iv

            return EncryptData(cipherText, iv)
        }

        fun decrypt(dataToDecrypt: ByteArray, ivValue: ByteArray): String {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            val key = generateKey()
            cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(ivValue))
            val cipherText = cipher.doFinal(dataToDecrypt)

            return buildString(cipherText)
        }

        private fun generateKey(): SecretKeySpec {
            val cipherKey = Constants.CYPHER_KEY
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val bytes = cipherKey.toByteArray()
            digest.update(bytes, 0, bytes.size)
            val key = digest.digest()
            val secretKeySpec = SecretKeySpec(key, "AES")
            return secretKeySpec
        }

        private fun buildString(text: ByteArray): String{
            val sb = StringBuilder()
            for (char in text) {
                sb.append(char.toInt().toChar())
            }
            return sb.toString()
        }


    }
}