package com.example.liftium.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.liftium.ui.screens.HomeScreen
import kotlinx.serialization.Serializable

// Navigation keys implementing NavKey interface
@Serializable
data object HomeRoute : NavKey

@Serializable
data object ProfileRoute : NavKey

@Serializable
data object WorkoutRoute : NavKey

@Serializable
data object ProgressRoute : NavKey

@Composable
fun NavigationHost(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(
                targetOffsetX = { -it }
            )
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
                targetOffsetX = { it }
            )
        },
        popTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
                targetOffsetX = { it }
            )
        },
        entryProvider = { key ->
            when (key) {
                is HomeRoute -> NavEntry(key) {
                    HomeScreen(
                        onStartWorkout = {
                            backStack.add(WorkoutRoute)
                        },
                        onTrackProgress = {
                            backStack.add(ProgressRoute)
                        },
                        onGetStronger = {
                            backStack.add(WorkoutRoute)
                        },
                        onStartFirstWorkout = {
                            backStack.add(WorkoutRoute)
                        },
                        onNavigateToProfile = {
                            backStack.add(ProfileRoute)
                        },
                        currentRoute = "home"
                    )
                }
                
                // Placeholder entries for unimplemented screens - they just return to home
                is ProfileRoute -> NavEntry(key) {
                    HomeScreen(
                        onStartWorkout = { backStack.add(WorkoutRoute) },
                        onTrackProgress = { backStack.add(ProgressRoute) },
                        onGetStronger = { backStack.add(WorkoutRoute) },
                        onStartFirstWorkout = { backStack.add(WorkoutRoute) },
                        onNavigateToProfile = { },
                        currentRoute = "profile"
                    )
                }
                
                is WorkoutRoute -> NavEntry(key) {
                    HomeScreen(
                        onStartWorkout = { },
                        onTrackProgress = { backStack.add(ProgressRoute) },
                        onGetStronger = { },
                        onStartFirstWorkout = { },
                        onNavigateToProfile = { backStack.add(ProfileRoute) },
                        currentRoute = "workout"
                    )
                }
                
                is ProgressRoute -> NavEntry(key) {
                    HomeScreen(
                        onStartWorkout = { backStack.add(WorkoutRoute) },
                        onTrackProgress = { },
                        onGetStronger = { backStack.add(WorkoutRoute) },
                        onStartFirstWorkout = { backStack.add(WorkoutRoute) },
                        onNavigateToProfile = { backStack.add(ProfileRoute) },
                        currentRoute = "progress"
                    )
                }

                else -> throw RuntimeException("Unknown route: $key")
            }
        }
    )
}
