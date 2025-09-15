package com.example.liftium.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

// 1. Users Table
data class User(
    val id: String, // UUID from Supabase Auth
    val email: String,
    val createdAt: LocalDateTime,
    val userName: String,
    val rememberMeDevice: String? = null // Optional device id for remember me feature
)

// 2. Splits Table
data class Split(
    val id: String, // UUID
    val userId: String, // FK → users.id
    val name: String, // e.g. "Push/Pull/Legs", "Bro Split"
    val createdAt: LocalDateTime
)

// 3. Split Days Table
data class SplitDay(
    val id: String, // UUID
    val splitId: String, // FK → splits.id
    val dayOfWeek: Int, // 0 (Sunday) to 6 (Saturday)
    val name: String, // e.g. "Push", "Legs", "Rest"
    val isRestDay: Boolean // defines if its a rest day or not
)

// 4. Exercises Table
data class Exercise(
    val id: String, // UUID
    val splitDayId: String, // FK → split_days.id
    val name: String, // e.g. "Bench Press"
    val defaultSets: Int, // e.g. 3 sets
    val restTimeSec: Int, // rest time between sets
    val note: String? = null, // Optional notes e.g "adjust seat to 45 degrees"
    val exerciseOrder: Int, // Order of the Exercise within the training day
    val muscleGroups: String // Muscle group targeted in the exercise
)

// 5. Sessions Table
data class Session(
    val id: String, // UUID
    val userId: String, // FK → users.id
    val splitDayId: String, // FK → split_days.id
    val date: LocalDate, // e.g. 2025-04-12
    val createdAt: LocalDateTime // Start of the workout session
)

// 6. Sets (Workout Logs) Table
data class WorkoutSet(
    val id: String, // UUID
    val sessionId: String, // FK → sessions.id
    val exerciseId: String, // FK → exercises.id
    val setNumber: Int, // Set index (1, 2, 3…)
    val reps: Int, // Actual reps performed
    val weight: Double // Actual weight used
)

// Helper data classes for UI and business logic
data class SessionWithDetails(
    val session: Session,
    val splitDay: SplitDay,
    val split: Split,
    val exercises: List<ExerciseWithSets>
)

data class ExerciseWithSets(
    val exercise: Exercise,
    val sets: List<WorkoutSet>
)

data class SplitWithDays(
    val split: Split,
    val splitDays: List<SplitDayWithExercises>
)

data class SplitDayWithExercises(
    val splitDay: SplitDay,
    val exercises: List<Exercise>
)

// Progress and statistics data classes
data class ProgressStats(
    val totalWorkouts: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val totalVolume: Double, // total weight lifted
    val favoriteExercise: String,
    val averageWorkoutDuration: Int // in minutes
)

data class ExerciseProgress(
    val exerciseId: String,
    val exerciseName: String,
    val maxWeight: Double,
    val maxReps: Int,
    val totalVolume: Double,
    val lastPerformed: LocalDate?
)

// Enums for muscle groups (can be extended)
enum class MuscleGroup(val displayName: String) {
    CHEST("Chest"),
    BACK("Back"),
    SHOULDERS("Shoulders"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    LEGS("Legs"),
    QUADS("Quadriceps"),
    HAMSTRINGS("Hamstrings"),
    GLUTES("Glutes"),
    CALVES("Calves"),
    CORE("Core"),
    FULL_BODY("Full Body")
}

// Day of week enum for better type safety
enum class DayOfWeek(val value: Int, val displayName: String) {
    SUNDAY(0, "Sunday"),
    MONDAY(1, "Monday"),
    TUESDAY(2, "Tuesday"),
    WEDNESDAY(3, "Wednesday"),
    THURSDAY(4, "Thursday"),
    FRIDAY(5, "Friday"),
    SATURDAY(6, "Saturday");
    
    companion object {
        fun fromInt(value: Int): DayOfWeek {
            return entries.find { it.value == value } ?: SUNDAY
        }
    }
}
