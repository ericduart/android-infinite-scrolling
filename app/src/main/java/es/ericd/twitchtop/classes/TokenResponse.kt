package es.ericd.twitchtop.classes

data class TokenResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)