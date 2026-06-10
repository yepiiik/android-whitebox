package com.floredo.whitebox.data.models

import java.sql.Time

data class Module(
    val id: String = "",
    val name: String = "",
    val approximateTimeMillis: Long = 0,
    val content: String = "",
    val courseId: String = "",
    val description: String? = null,
    val referenceModulesId: List<String> = emptyList()
)