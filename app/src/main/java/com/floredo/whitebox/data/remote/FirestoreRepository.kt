package com.floredo.whitebox.data.remote

import android.util.Log
import com.floredo.whitebox.data.models.Course
import com.floredo.whitebox.data.models.Module
import com.floredo.whitebox.data.models.Quiz
import com.floredo.whitebox.data.models.Answer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

import com.floredo.whitebox.data.MockData
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = "FirestoreRepository"

    suspend fun seedDatabase(): Boolean {
        return try {
            Log.d(TAG, "Starting database seed...")
            val batch = firestore.batch()
            
            // Seed Courses
            MockData.courses.forEach { course ->
                val docRef = firestore.collection("courses").document(course.id)
                val firestoreCourse = mapOf(
                    "id" to course.id,
                    "name" to course.name,
                    "followers" to course.followers,
                    "isFollowed" to course.isFollowed,
                    "modulesCount" to course.modulesCount,
                    "level" to course.level.name,
                    "description" to course.description,
                    "category" to course.category?.name,
                    "thumbnailUrl" to course.thumbnailUrl
                )
                batch.set(docRef, firestoreCourse)
            }

            // Seed Modules
            MockData.modules.forEach { module ->
                val docRef = firestore.collection("modules").document(module.id)
                batch.set(docRef, module)
            }

            // Seed Quizzes
            MockData.quizzes.forEach { quiz ->
                val docRef = firestore.collection("quizzes").document(quiz.id)
                val firestoreQuiz = mapOf(
                    "id" to quiz.id,
                    "question" to quiz.question,
                    "moduleId" to quiz.moduleId,
                    "answers" to quiz.answers.map { answer ->
                        mapOf(
                            "answerText" to answer.answerText,
                            "isCorrect" to answer.isCorrect
                        )
                    }
                )
                batch.set(docRef, firestoreQuiz)
            }
            
            batch.commit().await()
            Log.d(TAG, "Seed successful! ${MockData.courses.size} courses, ${MockData.modules.size} modules, and ${MockData.quizzes.size} quizzes uploaded.")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Seed failed: ${e.message}", e)
            false
        }
    }

    suspend fun getCourses(): List<Course> {
        return try {
            Log.d(TAG, "Fetching courses from collection 'courses'...")
            val result = firestore.collection("courses").get().await()
            
            if (result.isEmpty) {
                Log.w(TAG, "The 'courses' collection is EMPTY. Run the 'Seed' function to add data.")
                return emptyList()
            }

            // Manually map documents to handle cases where 'level' or 'category' might be stored as Long (ordinal)
            val courses = result.documents.mapNotNull { doc ->
                try {
                    val data = doc.data ?: return@mapNotNull null
                    
                    // Robustly handle the 'level' enum conversion
                    val levelRaw = data["level"]
                    val level = when (levelRaw) {
                        is String -> try { 
                            com.floredo.whitebox.data.models.CourseLevel.valueOf(levelRaw) 
                        } catch (e: Exception) { 
                            com.floredo.whitebox.data.models.CourseLevel.EASY 
                        }
                        is Long -> com.floredo.whitebox.data.models.CourseLevel.values().getOrElse(levelRaw.toInt()) { 
                            com.floredo.whitebox.data.models.CourseLevel.EASY 
                        }
                        else -> com.floredo.whitebox.data.models.CourseLevel.EASY
                    }

                    // Robustly handle the 'category' enum conversion
                    val categoryRaw = data["category"]
                    val category = when (categoryRaw) {
                        is String -> try { 
                            com.floredo.whitebox.data.models.CourseTypes.valueOf(categoryRaw) 
                        } catch (e: Exception) { 
                            null 
                        }
                        is Long -> com.floredo.whitebox.data.models.CourseTypes.values().getOrElse(categoryRaw.toInt()) { 
                            null 
                        }
                        else -> null
                    }

                    Course(
                        id = doc.getString("id") ?: "",
                        name = doc.getString("name") ?: "",
                        followers = (data["followers"] as? Long)?.toInt() ?: 0,
                        isFollowed = data["isFollowed"] as? Boolean ?: false,
                        modulesCount = (data["modulesCount"] as? Long)?.toInt() ?: 0,
                        level = level,
                        description = data["description"] as? String,
                        category = category,
                        thumbnailUrl = doc.getString("thumbnailUrl") ?: ""
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error mapping document ${doc.id}: ${e.message}")
                    null
                }
            }
            Log.d(TAG, "Found ${result.size()} docs. Mapped ${courses.size} courses.")
            courses
        } catch (e: Exception) {
            Log.e(TAG, "CRITICAL: Firestore fetch failed. Check Rules or Internet.", e)
            emptyList()
        }
    }

    suspend fun getModules(courseId: String): List<Module> {
        return try {
            firestore.collection("modules")
                .whereEqualTo("courseId", courseId)
                .get()
                .await()
                .toObjects<Module>()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getModuleById(moduleId: String): Module? {
        return try {
            firestore.collection("modules")
                .document(moduleId)
                .get()
                .await()
                .toObject(Module::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getQuiz(moduleId: String): Quiz? {
        return try {
            val result = firestore.collection("quizzes")
                .whereEqualTo("moduleId", moduleId)
                .get()
                .await()
            
            val doc = result.documents.firstOrNull() ?: return null
            mapDocumentToQuiz(doc)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get quiz for module $moduleId", e)
            null
        }
    }

    suspend fun getQuizById(quizId: String): Quiz? {
        return try {
            val doc = firestore.collection("quizzes")
                .document(quizId)
                .get()
                .await()
            if (!doc.exists()) return null
            mapDocumentToQuiz(doc)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get quiz $quizId", e)
            null
        }
    }

    private fun mapDocumentToQuiz(doc: com.google.firebase.firestore.DocumentSnapshot): Quiz? {
        return try {
            val data = doc.data ?: return null
            val answersRaw = data["answers"] as? List<*> ?: emptyList<Any?>()
            val answers = answersRaw.mapNotNull { item ->
                val answerMap = item as? Map<String, Any> ?: return@mapNotNull null
                val answerText = answerMap["answerText"] as? String ?: ""
                
                // Extremely robust boolean extraction
                val rawIsCorrect = answerMap["isCorrect"] ?: answerMap["correct"]
                val isCorrect = when (rawIsCorrect) {
                    is Boolean -> rawIsCorrect
                    is Number -> rawIsCorrect.toInt() == 1
                    is String -> rawIsCorrect.equals("true", ignoreCase = true)
                    else -> false
                }

                Answer(answerText, isCorrect)
            }
            
            Quiz(
                id = doc.getString("id") ?: "",
                question = doc.getString("question") ?: "",
                answers = answers,
                moduleId = doc.getString("moduleId") ?: ""
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error mapping quiz document ${doc.id}", e)
            null
        }
    }

    suspend fun getAllModules(): List<Module> {
        return try {
            firestore.collection("modules")
                .get()
                .await()
                .toObjects<Module>()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get all modules", e)
            emptyList()
        }
    }

    suspend fun saveUserCourse(userId: String, userCourse: com.floredo.whitebox.data.local.entities.UserCourse) {
        try {
            firestore.collection("users").document(userId)
                .collection("enrolled_courses").document(userCourse.courseId)
                .set(userCourse).await()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save user course", e)
        }
    }

    suspend fun deleteUserCourse(userId: String, courseId: String) {
        try {
            firestore.collection("users").document(userId)
                .collection("enrolled_courses").document(courseId)
                .delete().await()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete user course", e)
        }
    }

    suspend fun saveModuleProgress(userId: String, progress: com.floredo.whitebox.data.local.entities.ModuleProgress) {
        try {
            val docId = "${progress.courseId}_${progress.moduleId}"
            firestore.collection("users").document(userId)
                .collection("module_progress").document(docId)
                .set(progress).await()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save module progress", e)
        }
    }

    suspend fun getUserCourses(userId: String): List<com.floredo.whitebox.data.local.entities.UserCourse> {
        return try {
            firestore.collection("users").document(userId)
                .collection("enrolled_courses").get().await()
                .toObjects<com.floredo.whitebox.data.local.entities.UserCourse>()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get user courses", e)
            emptyList()
        }
    }

    suspend fun getUserProgress(userId: String): List<com.floredo.whitebox.data.local.entities.ModuleProgress> {
        return try {
            firestore.collection("users").document(userId)
                .collection("module_progress").get().await()
                .toObjects<com.floredo.whitebox.data.local.entities.ModuleProgress>()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get user progress", e)
            emptyList()
        }
    }
}
