package com.floredo.whitebox.data.models

data class Profile(
    val username: String,
    val courseListIds: List<String>,
    val displayName: String?,
)
