
# ANDROID-INFINITE-SCROLLING

Devuelve los directos de Twitch con más visualizaciones. Hace lo mismo que mi proyecto [react-infinite-scrolling](https://github.com/ericduart/react-infinite-scrolling), pero adaptado a Android.


## Puesta en producción

Clona el repositorio
```bash
  git clone https://github.com/ericduart/android-infinite-scrolling.git
```

El segundo paso será crear una aplicación en [la consola de Twitch](https://dev.twitch.tv/console) para conseguir acceso a la API (conseguiremos el Client-ID y el Client-Secret).

El último paso será, una vez tengamos el CLIENT_ID y el SECRET_ID, renombrar `ConstantsTemplate.kt` a `Constants.kt` y rellenar los campos. Constants.kt deberá tener los siguientes campos:

```bash
  val CLIENT_ID = "<CLIENT-ID de Twitch>"
  val CLIENT_SECRET = "<CLIENT-SECRET de Twitch>"
  val GRANT_TYPE = "client_credentials"

  val BASE_ID_URL = "https://id.twitch.tv/"
  val BASE_API_URL = "https://api.twitch.tv/"

  val CYPHER_KEY = "<Clave secreta para encriptar tokens>"

  val PREFERENCES_NAME = "ApiPreference"
  val PREFERENCES_TOKEN_KEY = "AuthToken"
  val PREFERENCES_IV_KEY = "AuthTokenIV"
```

