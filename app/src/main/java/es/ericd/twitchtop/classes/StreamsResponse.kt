package es.ericd.twitchtop.classes

data class StreamsResponse(
    val `data`: List<Data>,
    val pagination: Pagination
)