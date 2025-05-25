package com.example.digitalacademyapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalacademyapp.ui.theme.DigitalAcademyAppTheme
import java.text.SimpleDateFormat
import java.util.Date
// ---------- Models ----------
enum class AppLanguage { ENGLISH, GREEK }

val LocalAppLanguage = staticCompositionLocalOf { AppLanguage.ENGLISH }

object Strings {
    @Composable
    fun get(key: String): String {
        val language = LocalAppLanguage.current
        return when (language) {
            AppLanguage.ENGLISH -> englishStrings[key] ?: key
            AppLanguage.GREEK -> greekStrings[key] ?: englishStrings[key] ?: key
        }
    }

    private val englishStrings = mapOf(
        "welcome" to "Welcome to Digital Academy",
        "setup_profile" to "Let's set up your profile",
        "name" to "Your Name",
        "job_role" to "Your Job Role",
        "continue" to "Continue",
        "courses" to "Courses",
        "achievements" to "Achievements",
        "profile" to "Profile",
        "digital_academy" to "Digital Academy"
    )

    private val greekStrings = mapOf(
        "welcome" to "Καλώς ήρθατε στην Ψηφιακή Ακαδημία",
        "setup_profile" to "Ας ρυθμίσουμε το προφίλ σας",
        "name" to "Το Όνομα σας",
        "job_role" to "Η Επαγγελματική Σας Θέση",
        "continue" to "Συνέχεια",
        "courses" to "Μαθήματα",
        "achievements" to "Επιτεύγματα",
        "profile" to "Προφίλ",
        "digital_academy" to "Ψηφιακή Ακαδημία"
    )
}




@Composable
fun LanguageToggleButton(
    currentLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                onLanguageChange(
                    if (currentLanguage == AppLanguage.ENGLISH)
                        AppLanguage.GREEK
                    else
                        AppLanguage.ENGLISH
                )
            }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = when (currentLanguage) {
                AppLanguage.ENGLISH -> "EN"
                AppLanguage.GREEK -> "GR"
            },
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.Bold
        )
    }
}
data class QuizQuestion(
    val question: String, val options: List<String>, val correctAnswer: String
)
data class Badge(
    val courseName: String, val dateEarned: String, val level: String, val iconRes: Int? = null
) {
    fun getLevelColor(): Color {
        return when (level) {
            "Beginner" -> Color(0xFF4CAF50) // Green
            "Intermediate" -> Color(0xFF2196F3) // Blue
            "Advanced" -> Color(0xFF9C27B0) // Purple
            else -> Color(0xFF607D8B) // Gray
        }
    }
}
data class CourseCategory(
    val categoryName: String, val iconRes: Int? = null, val courses: List<Course>
)
data class Course(
    val title: String,
    val level: String,
    val duration: String,
    val description: String,
    val iconRes: Int? = null
)
// ---------- Data Providers ----------
fun getCourseCategories(): List<CourseCategory> {
    return listOf(
        CourseCategory(
            "Software Development", iconRes = R.drawable.ic_launcher_foreground, courses = listOf(
                Course(
                    "Python Programming for Beginners",
                    "Beginner",
                    "8 hours",
                    "Learn Python fundamentals with hands-on exercises",
                    R.drawable.ic_launcher_foreground
                ), Course(
                    "App Development with VS Code",
                    "Beginner",
                    "6 hours",
                    "Build web apps using Visual Studio Code",
                    R.drawable.ic_launcher_foreground
                )
            )
        ), CourseCategory(
            "Web Development", iconRes = R.drawable.ic_launcher_foreground, courses = listOf(
                Course(
                    "HTML & CSS Fundamentals",
                    "Beginner",
                    "5 hours",
                    "Master the building blocks of web development",
                    R.drawable.ic_launcher_foreground
                )
            )
        ), CourseCategory(
            "Cybersecurity", iconRes = R.drawable.ic_launcher_foreground, courses = emptyList()
        )
    )
}
fun getRecommendationsForRole(role: String): List<Course> {
    val allCats = getCourseCategories()
    val devCourses = allCats[0].courses                // Software Development
    val webCourses = allCats.getOrNull(1)?.courses ?: devCourses
    val r = role.lowercase()
    return when {
        listOf("developer", "programmer", "web", "android").any { r.contains(it) } -> devCourses
        listOf("data", "analyst").any { r.contains(it) } -> listOf(devCourses.first())
        listOf("cyber", "security", "ασφάλεια", "hacker").any { r.contains(it) } ->
            // If cybersecurity list is empty, fallback to first dev course
            allCats.find { it.categoryName == "Cybersecurity" }?.courses?.ifEmpty { devCourses }
                ?.take(1) ?: devCourses.take(1)
        else -> emptyList()
    }
}
fun getCourseSlides(course: String): List<String> {
    return when (course) {
        "App Development with VS Code" -> listOf(
            "Introduction\n\nLearn how to build web applications using Visual Studio Code...",
            "Getting Started\n\nSet up your development environment and create your first project...",
            "HTML Basics\n\nUnderstand the structure of HTML documents...",
            "CSS Styling\n\nLearn how to style your web pages with CSS..."
        )
        else -> listOf(
            "Introduction to $course", "Core Concepts", "Advanced Topics", "Final Project"
        )
    }
}
fun getQuizForCourse(course: String): List<QuizQuestion> {
    return when (course) {
        "App Development with VS Code" -> listOf(
            QuizQuestion(
                "What is VS Code primarily used for?",
                listOf("Writing code", "Graphic design", "Video editing", "Accounting"),
                "Writing code"
            ), QuizQuestion(
                "Which extension provides live preview of web pages?",
                listOf("Live Server", "Python", "Docker", "GitLens"),
                "Live Server"
            )
        )
        else -> listOf(
            QuizQuestion(
                "Sample question about $course",
                listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                "Option 2"
            )
        )
    }
}
// ---------- UI Components ----------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalAcademyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    DigitalAcademyApp()
                }
            }
        }
    }
}
@Composable
fun DigitalAcademyApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.ProfileSetup) }
    var userName by remember { mutableStateOf("") }
    var appLanguage by remember { mutableStateOf(AppLanguage.ENGLISH) }

    CompositionLocalProvider(
        LocalAppLanguage provides appLanguage
    ) {
        DigitalAcademyAppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                when (currentScreen) {
                    is Screen.ProfileSetup -> ProfileSetupScreen(
                        currentLanguage = appLanguage,
                        onLanguageChange = { newLanguage ->
                            appLanguage = newLanguage
                        },
                        onSetupComplete = { name ->
                            userName = name
                            currentScreen = Screen.MainDashboard
                        }
                    )
                    is Screen.MainDashboard -> MainDashboardScreen(
                        userName = userName,
                        currentLanguage = appLanguage,
                        onLanguageChange = { newLanguage ->
                            appLanguage = newLanguage
                        }
                    )
                }
            }
        }
    }
}
sealed class Screen {
    object ProfileSetup : Screen()
    object MainDashboard : Screen()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupScreen( currentLanguage: AppLanguage,
                        onLanguageChange: (AppLanguage) -> Unit, onSetupComplete: (userName: String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var jobRole by remember { mutableStateOf("") }
    val recommendedCourses = getRecommendationsForRole(jobRole)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            LanguageToggleButton(
                currentLanguage = currentLanguage,
                onLanguageChange = onLanguageChange
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            Strings.get("welcome"),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Let's set up your profile",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(value = name,
            onValueChange = { name = it },
            label = { Text(Strings.get("name"),)},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Name")
            })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = jobRole,
            onValueChange = { jobRole = it },
            label = { Text(Strings.get("job_role"),) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            leadingIcon = {
                Icon(Icons.Default.AccountCircle, contentDescription = "Job Role")
            })
        if (recommendedCourses.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Recommended for you:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recommendedCourses) { course ->
                    RecommendedCourseCard(course)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { if (name.isNotBlank()) onSetupComplete(name) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = name.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Continue", style = MaterialTheme.typography.labelLarge)
        }
    }
}
@Composable
fun RecommendedCourseCard(course: Course) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (course.iconRes != null) {
                Image(
                    painter = painterResource(course.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column {
                Text(
                    course.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "${course.level} • ${course.duration}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardScreen(userName: String,currentLanguage: AppLanguage,
                        onLanguageChange: (AppLanguage) -> Unit) {
    var selectedCourse by remember { mutableStateOf<Course?>(null) }
    var showQuiz by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("courses") }
    var earnedBadges by remember { mutableStateOf<List<Badge>>(emptyList()) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        Strings.get("digital_academy"),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    LanguageToggleButton(
                        currentLanguage = currentLanguage,
                        onLanguageChange = onLanguageChange
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                NavigationBarItem(selected = currentScreen == "courses",
                    onClick = { currentScreen = "courses" },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Courses") },
                    label = { Text("Courses") })
                NavigationBarItem(selected = currentScreen == "achievements",
                    onClick = { currentScreen = "achievements" },
                    icon = {
                        Icon(
                            Icons.Default.ShoppingCart, contentDescription = "Achievements"
                        )
                    },
                    label = { Text("Achievements") })
                NavigationBarItem(selected = currentScreen == "profile",
                    onClick = { currentScreen = "profile" },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") })
            }
        }) { innerPadding ->
        when (currentScreen) {
            "achievements" -> {
                AchievementsScreen(
                    earnedBadges = earnedBadges, modifier = Modifier.padding(innerPadding)
                )
            }
            "courses" -> {
                when {
                    selectedCourse != null && !showQuiz -> {
                        CourseDetailScreen(course = selectedCourse!!,
                            onBack = { selectedCourse = null },
                            onStartQuiz = { showQuiz = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    showQuiz && selectedCourse != null -> {
                        QuizScreen(
                            course = selectedCourse!!, onQuizCompleted = { score ->
                                if (score >= 80) {
                                    // Add badge when quiz is passed
                                    val newBadge = Badge(
                                        courseName = selectedCourse!!.title,
                                        dateEarned = SimpleDateFormat("yyyy-MM-dd").format(Date()),
                                        level = selectedCourse!!.level,
                                        iconRes = R.drawable.ic_launcher_foreground
                                    )
                                    earnedBadges = earnedBadges + newBadge
                                }
                                showQuiz = false
                                selectedCourse = null
                            }, modifier = Modifier.padding(innerPadding)
                        )
                    }
                    else -> {
                        HomeScreen(
                            onCourseSelected = { course -> selectedCourse = course },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
            "profile" -> {
                // Προσθέστε τον κώδικα για την οθόνη προφίλ εδώ
                ProfileScreen(
                    userName = userName.toString(),
                    earnedBadges = earnedBadges,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
@Composable
fun ProfileScreen(
    userName: String, earnedBadges: List<Badge>, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // User Profile Section
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.take(1).uppercase(),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    "Welcome back,",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Text(
                    userName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Badges Section
        Text(
            "Your Achievements",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (earnedBadges.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    "No badges yet. Complete courses to earn achievements!",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(earnedBadges) { badge ->
                    ProfileBadgeCard(badge = badge)
                }
            }
        }
    }
}
@Composable
fun ProfileBadgeCard(badge: Badge) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (badge.iconRes != null) {
                Image(
                    painter = painterResource(badge.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    badge.courseName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Earned on: ${badge.dateEarned}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = badge.getLevelColor().copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        badge.level,
                        color = badge.getLevelColor(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Badge",
                tint = badge.getLevelColor(),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
@Composable
fun AchievementsScreen(
    earnedBadges: List<Badge>, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Your Achievements",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (earnedBadges.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f), contentAlignment = Alignment.Center
            ) {
                Text(
                    "No achievements yet. Complete courses to earn badges!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(earnedBadges) { badge ->
                    AchievementCard(badge = badge)
                }
            }
        }
    }
}
@Composable
fun AchievementCard(badge: Badge) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (badge.iconRes != null) {
                Image(
                    painter = painterResource(badge.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                Text(
                    badge.courseName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Earned on: ${badge.dateEarned}", style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = badge.getLevelColor().copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        badge.level,
                        color = badge.getLevelColor(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
@Composable
fun HomeScreen(
    onCourseSelected: (Course) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Courses Section
        Text(
            "Available Courses",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        CourseCategoriesList(onCourseSelected = onCourseSelected)
    }
}
@Composable
fun FilterChipGroup(
    options: List<String>, selected: String, onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            FilterChip(selected = option == selected,
                onClick = { onOptionSelected(option) },
                label = { Text(option) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
@Composable
fun SlideViewer(slides: List<String>) {
    var index by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (slides.isNotEmpty()) {
            Text("Slide ${index + 1} of ${slides.size}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = slides[index], style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { if (index > 0) index-- }, enabled = index > 0) {
                    Text("Next")
                }
                Button(
                    onClick = { if (index < slides.size - 1) index++ },
                    enabled = index < slides.size - 1
                ) {
                    Text("Previous")
                }
            }
        }
    }
}
@Composable
fun BadgesGrid(filter: String) {
    val badges = remember {
        listOf(
            Badge(
                "Web Development", "2023-06-20", "Intermediate", R.drawable.ic_launcher_foreground
            )
        )
    }
    val filteredBadges = if (filter == "All") badges else badges.filter { it.level == filter }
    if (filteredBadges.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                "No badges yet", color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.height(150.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredBadges) { badge ->
                BadgeCard(badge)
            }
        }
    }
}
@Composable
fun BadgeCard(badge: Badge) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (badge.iconRes != null) {
                Image(
                    painter = painterResource(badge.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                Text(
                    badge.courseName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = badge.getLevelColor().copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            badge.level, color = badge.getLevelColor(), fontSize = 12.sp
                        )
                    }
                    Text(
                        badge.dateEarned,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
@Composable
fun CourseCategoriesList(onCourseSelected: (Course) -> Unit) {
    val categories = getCourseCategories()
    val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CourseCategoryItem(
                category = category,
                isExpanded = expandedCategories[category.categoryName] ?: false,
                onToggle = {
                    expandedCategories[category.categoryName] =
                        !(expandedCategories[category.categoryName] ?: false)
                },
                onCourseSelected = onCourseSelected
            )
        }
    }
}
@Composable
fun CourseCategoryItem(
    category: CourseCategory,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onCourseSelected: (Course) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggle() }
                .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                if (category.iconRes != null) {
                    Image(
                        painter = painterResource(category.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Text(
                    category.categoryName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.Home else Icons.Default.Add,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }
            if (isExpanded) {
                if (category.courses.isEmpty()) {
                    Text(
                        "No courses available yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier.padding(
                            start = 16.dp, end = 16.dp, bottom = 16.dp
                        ), verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        category.courses.forEach { course ->
                            CourseItem(course = course, onClick = { onCourseSelected(course) })
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun CourseItem(course: Course, onClick: () -> Unit) {
    val levelColor = when (course.level) {
        "Beginner" -> Color(0xFF4CAF50)
        "Intermediate" -> Color(0xFF2196F3)
        "Advanced" -> Color(0xFF9C27B0)
        else -> MaterialTheme.colorScheme.primary
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (course.iconRes != null) {
                Image(
                    painter = painterResource(course.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    course.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = levelColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            course.level, color = levelColor, fontSize = 12.sp
                        )
                    }
                    Text(
                        course.duration,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "View course",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
@Composable
fun CourseDetailScreen(
    course: Course, onBack: () -> Unit, onStartQuiz: () -> Unit, modifier: Modifier = Modifier
) {
    var showSlides by remember { mutableStateOf(false) }
    val slides = getCourseSlides(course.title)
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                course.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (course.iconRes != null) {
                            Image(
                                painter = painterResource(course.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        Column {
                            Text(
                                course.level,
                                style = MaterialTheme.typography.labelLarge,
                                color = when (course.level) {
                                    "Beginner" -> Color(0xFF4CAF50)
                                    "Intermediate" -> Color(0xFF2196F3)
                                    "Advanced" -> Color(0xFF9C27B0)
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                            Text(
                                course.duration, style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "About this course",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        course.description, style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showSlides = !showSlides },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (showSlides) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.primaryContainer,
                    contentColor = if (showSlides) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(if (showSlides) "Hide Course Material" else "View Course Material")
            }
            if (showSlides && slides.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Course Material",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                SlideViewer(slides = slides)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Take Quiz", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
@Composable
fun QuizScreen(
    course: Course, onQuizCompleted: (scorePercent: Int) -> Unit, modifier: Modifier = Modifier
) {
    val questions = getQuizForCourse(course.title)
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var quizCompleted by remember { mutableStateOf(false) }
    if (quizCompleted) {
        QuizResultScreen(score = score,
            totalQuestions = questions.size,
            onContinue = { onQuizCompleted((score * 100) / questions.size) })
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            LinearProgressIndicator(
                progress = { (currentQuestionIndex + 1).toFloat() / questions.size.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
            )
            QuizQuestionCard(question = questions[currentQuestionIndex],
                questionNumber = currentQuestionIndex + 1,
                totalQuestions = questions.size,
                onAnswerSelected = { answer ->
                    if (answer == questions[currentQuestionIndex].correctAnswer) {
                        score++
                    }
                    if (currentQuestionIndex == questions.size - 1) {
                        quizCompleted = true
                    } else {
                        currentQuestionIndex++
                    }
                })
        }
    }
}
@Composable
fun QuizQuestionCard(
    question: QuizQuestion,
    questionNumber: Int,
    totalQuestions: Int,
    onAnswerSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Question $questionNumber of $totalQuestions",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            question.question,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            question.options.forEach { option ->
                OutlinedButton(
                    onClick = { onAnswerSelected(option) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        option,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
@Composable
fun QuizResultScreen(
    score: Int, totalQuestions: Int, onContinue: () -> Unit
) {
    val percentage = (score * 100) / totalQuestions
    val passed = percentage >= 80
    val resultColor = if (passed) Color(0xFF4CAF50) else Color(0xFFF44336)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(120.dp)
                .background(
                    color = resultColor.copy(alpha = 0.2f), shape = CircleShape
                )
        ) {
            Text(
                "$percentage%",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = resultColor
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            if (passed) "Congratulations! 🎉" else "Try Again!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = resultColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "You scored $score out of $totalQuestions", style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (passed) {
            Text(
                "You've earned a badge for completing this course!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Badge earned",
                modifier = Modifier.size(80.dp)
            )
        } else {
            Text(
                "You need at least 80% to pass. Review the material and try again!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                if (passed) "Continue Learning" else "Back to Course",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

