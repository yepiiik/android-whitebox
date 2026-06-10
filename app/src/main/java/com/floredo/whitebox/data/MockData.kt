package com.floredo.whitebox.data

import com.floredo.whitebox.data.models.*

object MockData {
    val modules = listOf(
        // Course 1: Introduction to Kotlin
        Module(
            id = "m1",
            name = "Variables and Types",
            approximateTimeMillis = 15 * 60 * 1000L,
            content = """
                # Variables in Kotlin
                
                Kotlin uses two keywords to declare variables: `val` and `var`.
                
                - **val**: Used for read-only variables. Once assigned, its value cannot be changed (immutable).
                - **var**: Used for mutable variables. You can reassign values to it as many times as you want.
                
                ## Basic Types
                Kotlin is a statically typed language, meaning the type of a variable is known at compile time.
                
                ### Numbers
                - `Int`: Whole numbers like 1, 42, -5.
                - `Double`: Floating point numbers like 3.14, 2.0.
                - `Long`: Large whole numbers.
                
                ### Other Types
                - `Boolean`: Can be `true` or `false`.
                - `String`: A sequence of characters wrapped in double quotes.
                - `Char`: A single character in single quotes.
                
                ```kotlin
                val name: String = "Kotlin"
                var age: Int = 10
                age = 11 // This is fine
                // name = "Java" // This would be a compilation error
                ```
            """.trimIndent(),
            courseId = "1",
            description = "Learn the difference between val and var and explore basic data types.",
            referenceModulesId = emptyList()
        ),
        Module(
            id = "m2",
            name = "Functions",
            approximateTimeMillis = 20 * 60 * 1000L,
            content = """
                # Functions in Kotlin
                
                Functions are the building blocks of any program. In Kotlin, they are declared using the `fun` keyword.
                
                ## Standard Declaration
                ```kotlin
                fun sum(a: Int, b: Int): Int {
                    return a + b
                }
                ```
                
                ## Single-Expression Functions
                If a function returns a single expression, the curly braces can be omitted and the body is specified after an `=` symbol.
                
                ```kotlin
                fun multiply(a: Int, b: Int) = a * b
                ```
                
                ## Default Arguments
                You can provide default values for parameters, making them optional when calling the function.
                
                ```kotlin
                fun greet(name: String, message: String = "Hello") {
                    println("${'$'}message, ${'$'}name!")
                }
                ```
            """.trimIndent(),
            courseId = "1",
            description = "Defining and calling functions, including single-expression functions and default arguments.",
            referenceModulesId = listOf("m1")
        ),
        Module(
            id = "m3",
            name = "Control Flow",
            approximateTimeMillis = 15 * 60 * 1000L,
            content = """
                # Control Flow
                
                Control flow structures allow your program to make decisions and repeat actions.
                
                ## When Expression
                `when` is a powerful replacement for the traditional `switch` statement.
                
                ```kotlin
                when (status) {
                    200 -> println("Success")
                    404 -> println("Not Found")
                    else -> println("Unknown Status")
                }
                ```
                
                ## Loops
                Kotlin provides `for`, `while`, and `do...while` loops.
                
                ```kotlin
                for (i in 1..5) {
                    println(i) // Prints 1 to 5
                }
                ```
            """.trimIndent(),
            courseId = "1",
            description = "Learn how to use if, when, and loops to control your program's logic.",
            referenceModulesId = listOf("m1")
        ),
        Module(
            id = "m7",
            name = "Null Safety",
            approximateTimeMillis = 20 * 60 * 1000L,
            content = """
                # Null Safety
                
                One of Kotlin's primary goals is to eliminate the `NullPointerException`. 
                
                ## Nullable Types
                By default, Kotlin types are non-nullable. To allow a variable to hold `null`, you must explicitly declare it with a `?`.
                
                ```kotlin
                var name: String = "Kotlin"
                // name = null // Compilation Error
                
                var maybeName: String? = null // OK
                ```
                
                ## Safe Calls (`?.`)
                The safe call operator allows you to access properties of a nullable object without crashing. If the object is null, the result of the call is null.
                
                ## Elvis Operator (`?:`)
                Use the Elvis operator to provide a default value when an expression is null.
                
                ```kotlin
                val length = maybeName?.length ?: 0
                ```
            """.trimIndent(),
            courseId = "1",
            description = "Master Kotlin's null safety features to write crash-free code.",
            referenceModulesId = listOf("m1")
        ),

        // Course 2: Android UI with Compose
        Module(
            id = "co1",
            name = "Intro to Compose",
            approximateTimeMillis = 10 * 60 * 1000L,
            content = """
                # Introduction to Jetpack Compose
                
                Jetpack Compose is Android's modern toolkit for building native UI. It simplifies and accelerates UI development on Android with a declarative approach.
                
                ## Declarative UI
                In traditional XML-based UI, you manage the state of views manually. In Compose, you describe what the UI should look like for a given state, and Compose automatically updates the UI when that state changes.
                
                ## Composable Functions
                A UI component is just a Kotlin function annotated with `@Composable`.
                
                ```kotlin
                @Composable
                fun SimpleText() {
                    Text(text = "Hello, Compose!")
                }
                ```
            """.trimIndent(),
            courseId = "2",
            description = "Understand the declarative paradigm and the basics of @Composable functions.",
            referenceModulesId = emptyList()
        ),
        Module(
            id = "co2",
            name = "Layout Basics",
            approximateTimeMillis = 15 * 60 * 1000L,
            content = """
                # Layouts in Compose
                
                Since you don't use XML, you arrange elements using Composable layout functions.
                
                ## Row, Column, and Box
                - **Column**: Places its children in a vertical sequence.
                - **Row**: Places its children in a horizontal sequence.
                - **Box**: Stacks children on top of each other.
                
                ```kotlin
                Column {
                    Text("First line")
                    Text("Second line")
                }
                ```
                
                ## Modifiers
                Modifiers allow you to decorate or augment a composable. You can use them to change the size, layout, behavior, and appearance of the UI element.
            """.trimIndent(),
            courseId = "2",
            description = "Learn how to structure your UI using standard layout components.",
            referenceModulesId = listOf("co1")
        ),
        Module(
            id = "co4",
            name = "State in Compose",
            approximateTimeMillis = 25 * 60 * 1000L,
            content = """
                # State in Jetpack Compose
                
                State is any value that can change over time. When state changes, Compose "re-runs" your composable functions with the new data—this is called **recomposition**.
                
                ## remember
                Composables can use `remember` to store an object in memory during recomposition.
                
                ## mutableStateOf
                `mutableStateOf` creates an observable `MutableState`, which tells the Compose runtime to track changes and trigger recomposition.
                
                ```kotlin
                val count = remember { mutableStateOf(0) }
                Button(onClick = { count.value++ }) {
                    Text("I've been clicked ${'$'}{count.value} times")
                }
                ```
            """.trimIndent(),
            courseId = "2",
            description = "Learn how to handle data changes and UI updates using State and recomposition.",
            referenceModulesId = listOf("co1", "co2")
        ),

        // Course 3: Basic Chemistry
        Module(
            id = "ch1",
            name = "Atoms and Elements",
            approximateTimeMillis = 20 * 60 * 1000L,
            content = """
                # Atoms and Elements
                Atoms are the basic building blocks of all matter. Everything you see, touch, and breathe is made of atoms.
                
                ## Structure of an Atom
                An atom consists of three main subatomic particles:
                1. **Protons**: Positively charged particles found in the nucleus.
                2. **Neutrons**: Particles with no charge, also found in the nucleus.
                3. **Electrons**: Negatively charged particles that orbit the nucleus in shells.
                
                ## Elements
                An element is a pure substance that consists entirely of one type of atom. For example, Gold (Au) consists only of gold atoms.
            """.trimIndent(),
            courseId = "3",
            description = "Learn about the subatomic particles that make up atoms and the nature of chemical elements.",
            referenceModulesId = emptyList()
        ),
        Module(
            id = "ch2",
            name = "The Periodic Table",
            approximateTimeMillis = 15 * 60 * 1000L,
            content = """
                # The Periodic Table
                The periodic table is a tabular display of all known chemical elements.
                
                ## Organization
                Elements are arranged by their **atomic number**, which is the number of protons in the nucleus of an atom.
                
                ## Groups and Periods
                - **Groups**: Vertical columns. Elements in the same group often have similar chemical properties.
                - **Periods**: Horizontal rows.
            """.trimIndent(),
            courseId = "3",
            description = "Understand how elements are organized and how to read the periodic table.",
            referenceModulesId = listOf("ch1")
        ),

        // Course 4: Middle School Physics
        Module(
            id = "ph2",
            name = "Motion in One Dimension",
            approximateTimeMillis = 20 * 60 * 1000L,
            content = """
                # Motion in One Dimension
                
                Motion is defined as the change in position of an object over time.
                
                ## Distance vs. Displacement
                - **Distance**: The total length of the path traveled. It is a scalar quantity (only magnitude).
                - **Displacement**: The straight-line distance between the starting point and the ending point, including the direction. It is a vector quantity.
                
                ## Speed vs. Velocity
                - **Speed**: How fast an object is moving (`distance / time`).
                - **Velocity**: Speed in a specific direction (`displacement / time`).
                
                If you run 100 meters and return to where you started, your distance is 200m, but your displacement is 0!
            """.trimIndent(),
            courseId = "4",
            description = "Understand the fundamental concepts of distance, displacement, speed, and velocity.",
            referenceModulesId = emptyList()
        ),
        Module(
            id = "ph6",
            name = "Newton's Second Law",
            approximateTimeMillis = 20 * 60 * 1000L,
            content = """
                # Newton's Second Law of Motion
                
                Newton's Second Law describes how the motion of an object changes when a net force is applied to it.
                
                ## The Formula
                **F = ma**
                
                - **F** is the net force (measured in Newtons, N).
                - **m** is the mass of the object (measured in kilograms, kg).
                - **a** is the acceleration of the object (measured in m/s²).
                
                ## Key Implications
                1. If you apply more force to the same object, it will accelerate faster.
                2. If you apply the same force to a heavier object, it will accelerate slower.
            """.trimIndent(),
            courseId = "4",
            description = "Explore the relationship between force, mass, and acceleration.",
            referenceModulesId = listOf("ph2")
        ),

        // Course 5: Arithmetics Fundamentals
        Module(
            id = "ar1",
            name = "Fractions",
            approximateTimeMillis = 15 * 60 * 1000L,
            content = """
                # Fractions
                A fraction represents a part of a whole.
                
                ## Numerator and Denominator
                - **Numerator**: The top number, representing how many parts we have.
                - **Denominator**: The bottom number, representing how many parts make up the whole.
                
                Example: In 3/4, 3 is the numerator and 4 is the denominator.
            """.trimIndent(),
            courseId = "5",
            description = "Master the basics of fractions, including numerators and denominators.",
            referenceModulesId = emptyList()
        ),
        Module(
            id = "ar2",
            name = "Percentages",
            approximateTimeMillis = 15 * 60 * 1000L,
            content = """
                # Percentages
                Percentage means "per hundred". It is a way of expressing a number as a fraction of 100.
                
                ## Converting Fractions to Percentages
                To convert a fraction to a percentage, multiply by 100 and add the % symbol.
                Example: 1/2 = 0.5 = 50%
            """.trimIndent(),
            courseId = "5",
            description = "Learn how to calculate percentages and convert them from fractions.",
            referenceModulesId = listOf("ar1")
        )
    )

    val courses = listOf(
        Course(
            id = "1",
            name = "Introduction to Kotlin",
            followers = 1250,
            isFollowed = true,
            level = CourseLevel.EASY,
            description = "Learn Kotlin, the expressive and safe programming language used by millions of Android developers. Perfect for beginners starting their coding journey.",
            category = CourseTypes.TECHNICAL,
            thumbnailUrl = "https://kotlinlang.org/assets/images/twitter-card.png"
        ),
        Course(
            id = "2",
            name = "Android UI with Compose",
            followers = 920,
            isFollowed = false,
            level = CourseLevel.MEDIUM,
            description = "Master Jetpack Compose to build modern, beautiful Android applications. Learn how to create reactive UIs with less code and better performance.",
            category = CourseTypes.TECHNICAL,
            thumbnailUrl = "https://developer.android.com/images/social/android-master.png"
        ),
        Course(
            id = "3",
            name = "Basic Chemistry",
            followers = 1500,
            isFollowed = false,
            level = CourseLevel.EASY,
            description = "Explore the fascinating world of atoms, molecules, and chemical reactions. Build a strong foundation in the central science.",
            category = CourseTypes.SCIENCE,
            thumbnailUrl = "https://img.freepik.com/free-vector/chemistry-science-icons-vector-set_53876-115264.jpg"
        ),
        Course(
            id = "4",
            name = "Middle School Physics",
            followers = 3420,
            isFollowed = true,
            level = CourseLevel.EASY,
            description = "Discover the fundamental laws of nature. From the motion of planets to the energy in your battery, understand how the world around you works.",
            category = CourseTypes.SCIENCE,
            thumbnailUrl = "https://img.freepik.com/premium-vector/physics-concept-vector-illustration-science-experiment-education-background_675567-5507.jpg"
        ),
        Course(
            id = "5",
            name = "Arithmetics Fundamentals",
            followers = 5000,
            isFollowed = true,
            level = CourseLevel.EASY,
            description = "Master the basic operations of mathematics. A essential course for students and anyone looking to refresh their number skills.",
            category = CourseTypes.SCIENCE,
            thumbnailUrl = "https://img.freepik.com/free-vector/maths-concept-illustration_114360-3942.jpg"
        )
    ).map { course ->
        course.copy(modulesCount = modules.count { it.courseId == course.id })
    }

    val quizzes = listOf(
        Quiz(
            id = "q_m1",
            question = "Which keyword should you use for a variable that will NOT change its value?",
            answers = listOf(
                Answer("var", false),
                Answer("val", true),
                Answer("const", false),
                Answer("static", false)
            ),
            moduleId = "m1"
        ),
        Quiz(
            id = "q_m2",
            question = "How do you declare a single-expression function that returns 5?",
            answers = listOf(
                Answer("fun foo() { return 5 }", false),
                Answer("fun foo() = 5", true),
                Answer("fun foo() -> 5", false),
                Answer("val foo = 5", false)
            ),
            moduleId = "m2"
        ),
        Quiz(
            id = "q_m3",
            question = "Which expression is used in Kotlin as a replacement for the switch statement?",
            answers = listOf(
                Answer("switch", false),
                Answer("choose", false),
                Answer("when", true),
                Answer("case", false)
            ),
            moduleId = "m3"
        ),
        Quiz(
            id = "q_m7",
            question = "What does 'maybeName?.length' return if 'maybeName' is null?",
            answers = listOf(
                Answer("0", false),
                Answer("Throws NullPointerException", false),
                Answer("null", true),
                Answer("-1", false)
            ),
            moduleId = "m7"
        ),
        Quiz(
            id = "q_co1",
            question = "What annotation is required for a function that describes a UI component in Compose?",
            answers = listOf(
                Answer("@UI", false),
                Answer("@Layout", false),
                Answer("@Composable", true),
                Answer("@View", false)
            ),
            moduleId = "co1"
        ),
        Quiz(
            id = "q_co2",
            question = "Which layout composable stacks its children on top of each other?",
            answers = listOf(
                Answer("Column", false),
                Answer("Row", false),
                Answer("Box", true),
                Answer("Stack", false)
            ),
            moduleId = "co2"
        ),
        Quiz(
            id = "q_co4",
            question = "What happens when a state variable read by a composable function changes?",
            answers = listOf(
                Answer("The app restarts", false),
                Answer("Recomposition is triggered for that composable", true),
                Answer("The state is reset to its initial value", false),
                Answer("Nothing happens until the next manual refresh", false)
            ),
            moduleId = "co4"
        ),
        Quiz(
            id = "q_ch1",
            question = "Which subatomic particle has a positive charge?",
            answers = listOf(
                Answer("Electron", false),
                Answer("Neutron", false),
                Answer("Proton", true),
                Answer("Nucleus", false)
            ),
            moduleId = "ch1"
        ),
        Quiz(
            id = "q_ch2",
            question = "How are elements arranged in the modern periodic table?",
            answers = listOf(
                Answer("By alphabetical order", false),
                Answer("By atomic number", true),
                Answer("By date of discovery", false),
                Answer("By color", false)
            ),
            moduleId = "ch2"
        ),
        Quiz(
            id = "q_ph2",
            question = "If an object travels 5 meters North and then 5 meters South, what is its displacement?",
            answers = listOf(
                Answer("10 meters", false),
                Answer("0 meters", true),
                Answer("5 meters North", false),
                Answer("-10 meters", false)
            ),
            moduleId = "ph2"
        ),
        Quiz(
            id = "q_ph6",
            question = "According to Newton's Second Law, if the mass of an object is doubled and the force remains the same, the acceleration will be:",
            answers = listOf(
                Answer("Doubled", false),
                Answer("Halved", true),
                Answer("Squared", false),
                Answer("Four times as much", false)
            ),
            moduleId = "ph6"
        ),
        Quiz(
            id = "q_ar1",
            question = "In the fraction 5/8, what is the denominator?",
            answers = listOf(
                Answer("5", false),
                Answer("8", true),
                Answer("13", false),
                Answer("0.625", false)
            ),
            moduleId = "ar1"
        ),
        Quiz(
            id = "q_ar2",
            question = "What is 1/4 expressed as a percentage?",
            answers = listOf(
                Answer("14%", false),
                Answer("40%", false),
                Answer("25%", true),
                Answer("75%", false)
            ),
            moduleId = "ar2"
        )
    )
}
