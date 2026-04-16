package com.floredo.whitebox.data

import com.floredo.whitebox.data.models.*

object MockData {
    val courses = listOf(
        Course(
            id = "1",
            name = "Introduction to Kotlin",
            followers = 1200,
            isFollowed = true,
            modulesCount = 3,
            level = CourseLevel.EASY,
            description = "Learn the basics of Kotlin programming language from scratch.",
            category = CourseTypes.TECHNICAL,
            thumbnailUrl = androidx.compose.ui.text.LinkAnnotation.Url("https://kotlinlang.org/assets/images/twitter-card.png")
        ),
        Course(
            id = "2",
            name = "Android UI with Compose",
            followers = 850,
            isFollowed = false,
            modulesCount = 5,
            level = CourseLevel.MEDIUM,
            description = "Master Jetpack Compose to build beautiful modern Android UIs.",
            category = CourseTypes.TECHNICAL,
            thumbnailUrl = androidx.compose.ui.text.LinkAnnotation.Url("https://developer.android.com/images/social/android-master.png")
        )
    )

    val modules = listOf(
        Module(
            id = "m1",
            name = "Variables and Types",
            approximateTime = java.sql.Time(0, 15, 0),
            content = listOf("In Kotlin, variables are declared using 'val' or 'var'...", "Types can be inferred by the compiler."),
            courseId = "1",
            description = "Basic building blocks of Kotlin."
        ),
        Module(
            id = "m2",
            name = "Functions",
            approximateTime = java.sql.Time(0, 20, 0),
            content = listOf("Functions are declared using the 'fun' keyword.", "They can have parameters and return types."),
            courseId = "1",
            description = "Learn how to structure your code with functions."
        )
    )

    val quizzes = listOf(
        Quiz(
            id = "q1",
            question = "Which keyword is used to declare a read-only variable in Kotlin?",
            answers = listOf(
                Answer("var", false),
                Answer("val", true),
                Answer("let", false),
                Answer("const", false)
            ),
            moduleId = "m1"
        )
    )
}
