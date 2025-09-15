package com.example.liftium.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liftium.R
import com.example.liftium.mock.MockData
import com.example.liftium.ui.components.BottomNavItem
import com.example.liftium.ui.components.LiftiumBottomNavigation
import com.example.liftium.ui.components.LiftiumTopAppBar
import com.example.liftium.ui.components.WorkoutCard
import com.example.liftium.ui.theme.Dimens
import com.example.liftium.ui.theme.LiftiumTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onStartWorkout: () -> Unit = {},
    onTrackProgress: () -> Unit = {},
    onGetStronger: () -> Unit = {},
    onStartFirstWorkout: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    currentRoute: String = "home",
    modifier: Modifier = Modifier
) {
    val user = MockData.currentUser
    val motivationalMessage = MockData.getRandomMotivationalMessage()
    val hasWorkoutToday = MockData.hasWorkoutToday()
    val workoutStatus = MockData.getTodaysWorkoutStatus()
    val todaysWorkoutName = MockData.getTodaysWorkoutName()
    val hasRecentWorkouts = MockData.hasRecentWorkouts()
    
    val bottomNavItems = listOf(
        BottomNavItem("home", "Home", iconVector = Icons.Default.Home),
        BottomNavItem("profile", "Profile & Settings", iconVector = Icons.Default.Person)
    )
    
    Scaffold(
        topBar = {
            LiftiumTopAppBar(
                title = "LIFTIUM"
            )
        },
        bottomBar = {
            LiftiumBottomNavigation(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { route ->
                    when (route) {
                        "profile" -> onNavigateToProfile()
                        "home" -> { /* Already on home */ }
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.ScreenPadding)
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            
            // Welcome Section
            WelcomeSection(
                userName = user.userName,
                motivationalMessage = motivationalMessage
            )
            
            Spacer(modifier = Modifier.height(Dimens.SectionSpacing))
            
            // Ready to Train Card
            ReadyToTrainSection(
                hasWorkout = hasWorkoutToday,
                workoutStatus = workoutStatus,
                workoutName = todaysWorkoutName,
                onStartWorkout = onStartWorkout,
                onGetStronger = onGetStronger,
                onTrackProgress = onTrackProgress
            )
            
            Spacer(modifier = Modifier.height(Dimens.SectionSpacing))
            
            // Recent Workouts Section
            RecentWorkoutsSection(
                hasRecentWorkouts = hasRecentWorkouts,
                onStartFirstWorkout = onStartFirstWorkout
            )
            
            Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
        }
    }
}

@Composable
private fun WelcomeSection(
    userName: String,
    motivationalMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome, $userName",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        
        Text(
            text = "Track your fitness journey",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
        
        Text(
            text = motivationalMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ReadyToTrainSection(
    hasWorkout: Boolean,
    workoutStatus: String,
    workoutName: String,
    onStartWorkout: () -> Unit,
    onGetStronger: () -> Unit,
    onTrackProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CardPadding),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardElevation
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.CardPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimens.IconSizeLarge)
                )
                Text(
                    text = "Ready to Train?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            
            // Today's Training Status
            WorkoutCard(
                title = workoutName,
                subtitle = "Today's Training",
                statusText = workoutStatus,
                statusColor = if (hasWorkout) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                iconPainter = painterResource(id = R.drawable.fitness_center_24px),
                onClick = onStartWorkout
            )
            
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            
            // Start Workout Button
            Button(
                onClick = onStartWorkout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ButtonHeight),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.IconSize)
                )
                Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                Text(
                    text = "Start Workout",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            
            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    text = "Get Stronger",
                    iconPainter = painterResource(id = R.drawable.fitness_center_24px),
                    onClick = onGetStronger,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
                
                ActionButton(
                    text = "Track Progress",
                    iconPainter = painterResource(id = R.drawable.trending_up_24px),
                    onClick = onTrackProgress,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    iconPainter: androidx.compose.ui.graphics.painter.Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimens.IconSize)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RecentWorkoutsSection(
    hasRecentWorkouts: Boolean,
    onStartFirstWorkout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CardPadding),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardElevation
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.CardPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recent Workouts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            
            if (!hasRecentWorkouts) {
                // Empty State
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(64.dp)
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                Text(
                    text = "No workout history yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                
                Button(
                    onClick = onStartFirstWorkout,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.IconSizeSmall)
                    )
                    Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                    Text(
                        text = "Start Your First Workout",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    LiftiumTheme {
        HomeScreen()
    }
}
