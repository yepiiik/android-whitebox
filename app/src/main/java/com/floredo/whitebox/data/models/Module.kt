package com.floredo.whitebox.data.models

import java.sql.Time

data class Module(
    val id: String,
    val name: String,
    val approximateTime: Time,
    val content: List<Any>,
    val courseId: String,
    val description: String?,
    val referenceModulesId: List<String>
)