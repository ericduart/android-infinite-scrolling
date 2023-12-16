package es.ericd.twitchtop.classes

data class EncryptData (
    val cipherData: ByteArray,
    val cipherIv: ByteArray
)