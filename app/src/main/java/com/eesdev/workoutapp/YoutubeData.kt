package com.eesdev.workoutapp

data class YoutubeData(
    val etag: String,
    val items: List<Items>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)