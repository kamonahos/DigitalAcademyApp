package com.example.digitalacademyapp
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.digitalacademyapp.UserSession
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.example.digitalacademyapp.BadgeProgressStore.clearAllDataStore
import com.example.digitalacademyapp.UserSession.userName
import com.example.digitalacademyapp.ui.theme.DigitalAcademyAppTheme
import kotlinx.coroutines.launch
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
        "av_cour" to "Available Courses",
        "setup_profile" to "Let's set up your profile",
        "name" to "Your Name",
        "job_role" to "Your Job Role",
        "continue" to "Continue",
        "courses" to "Courses",
        "achievements" to "Achievements",
        "profile" to "Profile",
        "digital_academy" to "Digital Academy",
        "about_course" to "About this course",
        "view_course_mat" to "View Course Material",
        "hide_course_mat" to "Hide Course Material",
        "quiz" to "Start Quiz",
        "prev" to "Previous",
        "next" to "Next",
        "congz" to "Congratulations! \uD83C\uDF89",
        "try" to "Try Again",
        "earned_badge" to "You've earned a badge for completing this course!",
        "atleast" to "You need at least 80% to pass. Review the material and try again!",
        "continue_learn" to "Continue Learning",
        "back_course" to "Back to Course",
        "score" to "You scored",
        "out" to "out of",
        "no_badge" to "No badges yet. Complete courses to earn achievements!",
        "ach" to "Your Achievements",
        "no_quiz_available" to "No quiz available in this language",
        "back_course" to "Back to Course",
        "earn" to "Earned on:",
        "wb" to "Welcome Back"
    )




    private val greekStrings = mapOf(
        "welcome" to "Καλώς ήρθατε στην e-Ακαδημία",
        "av_cour" to "Διαθέσιμα Μαθήματα",
        "setup_profile" to "Ας ρυθμίσουμε το προφίλ σας",
        "name" to "Το Όνομα σας",
        "job_role" to "Η Επαγγελματική Σας Θέση",
        "continue" to "Συνέχεια",
        "courses" to "Μαθήματα",
        "achievements" to "Επιτεύγματα",
        "profile" to "Προφίλ",
        "digital_academy" to "Ψηφιακή Ακαδημία",
        "about_course" to "Σχετικά με αυτό το μάθημα",
        "view_course_mat" to "Προβολή Υλικού Μαθήματος",
        "hide_course_mat" to "Απόκρυψη Υλικού Μαθήματος",
        "quiz" to "Έναρξη κουίζ",
        "prev" to "Προηγούμενο",
        "next" to "Επόμενο",
        "congz" to "Συγχαρητήρια! \uD83C\uDF89",
        "try" to "Προσπαθήστε Ξανά",
        "earned_badge" to "Κερδίσατε ένα παράσημο για την ολοκλήρωση αυτού του μαθήματος!",
        "atleast" to "Χρειάζεστε τουλάχιστον 80% για να περάσετε.Επαναλάβετε το μάθημα και προσπαθήστε ξανά!",
        "continue_learn" to "Συνέχισε την Εκμάθηση",
        "back_course" to "Επιστροφή στο Μάθημα",
        "score" to "Έκανες σωστά",
        "out" to "από",
        "no_badge" to "Δεν έχετε παράσημα.Ολοκληρώστε μαθήματα για να κερδίσετε επιτεύγματα!",
        "ach" to "Τα Επιτεύγματα σας",
        "no_quiz_available" to "Δεν υπάρχει διαθέσιμο κουίζ σε αυτή τη γλώσσα",
        "back_course" to "Επιστροφή στο μάθημα",
        "earn" to "Αποκτήθηκε στις:",
        "wb" to "Καλώς ήρθατε ξανά"
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
) {
    companion object {
        fun isEmpty(): Boolean {
            return true
        }
    }
}




data class Badge(
    val courseName: String, val dateEarned: String, val level: String, val iconRes: Int? = null
) {
    fun getLevelColor(): Color {
        return when (level) {
            "Beginner", "Αρχάριος" -> Color(0xFF4CAF50) // Green
            "Intermediate", "Μέσο επίπεδο" -> Color(0xFF2196F3) // Blue
            "Advanced", "Προχωρημένος" -> Color(0xFF9C27B0) // Purple
            else -> Color(0xFF607D8B) // Gray
        }
    }
}
data class CourseCategory(
    val categoryName: String, val iconRes: Int? = null, val courses: List<Course>
)
data class Course(
    val id: String,
    val title: String,
    val level: String,
    val duration: String,
    val description: String,
    val iconRes: Int? = null
)
// ---------- Data Providers ----------
@Composable
fun getCourseCategories(): List<CourseCategory> {
    return when (LocalAppLanguage.current) {
        AppLanguage.ENGLISH -> listOf(
            CourseCategory(
                "Software Development", iconRes = R.drawable.language_python, courses = listOf(
                    Course(
                        "A",
                        "Python Programming for Beginners",
                        "Beginner",
                        "8 hours",
                        "Learn Python fundamentals with hands-on exercises",
                        R.drawable.language_python
                    ), Course(
                        "B",
                        "Advanced Python Programming",
                        "Advanced",
                        "3 hours",
                        "The objective of the course is to deepen the audience's knowledge in programming. It covers topics such as object-oriented programming with Python, connecting Python to databases, and graphical interface programming using tkinter.",
                        R.drawable.language_python
                    )
                )
            ),
            CourseCategory(




                "Communication Networks", iconRes = R.drawable.network, courses = listOf(
                    Course(
                        "C",
                        "Multimedia Technology",
                        "Intermidiate",
                        " 3 hours",
                        "The course covers technologies used for the representation, processing, and playback of various media, methods for combining individual media to build multimedia applications, and the network infrastructure required to support multimedia applications.",
                        R.drawable.network
                    )
                )
            ), CourseCategory(
                "Cybersecurity", iconRes = R.drawable.shield_lock_outline, courses = listOf(
                    Course(
                        "D",
                        "Introduction to Cybersecurity",
                        "Beginner",
                        " 1-3 hours",
                        "Cybersecurity includes technologies, processes, and controls designed to protect systems, networks, and data from cyber attacks. Effective cybersecurity reduces the risk of attacks and protects organizations and individuals from unauthorized exploitation of systems, networks, and technologies.",
                        R.drawable.shield_lock_outline
                    )
                )
            ), CourseCategory(
                "Artificial Intelligence", iconRes = R.drawable.robot, courses = listOf(
                    Course(
                        "E",
                        "Introduction to Artificial Intelligence",
                        "Beginner",
                        " 1-3 hours",
                        "Learn the basic concepts of Artificial Intelligence, including machine learning, neural networks, and real-world applications in modern technology.",
                        R.drawable.robot
                    )
                )
            )
        )
        AppLanguage.GREEK -> listOf(
            CourseCategory(
                "Ανάπτυξη Λογισμικού", iconRes = R.drawable.language_python, courses = listOf(
                    Course(
                        "A",
                        "Προγραμματισμός Python για αρχάριους",
                        "Αρχάριος",
                        "8 ώρες",
                        "Μάθετε τα βασικά της Python με πρακτικές ασκήσεις",
                        R.drawable.language_python
                    ),
                    Course(
                        "B",
                        "Προχωρημένος Προγραμματισμός σε Python",
                        "Προχωρημένος",
                        "3 ώρες",
                        "Στόχος του μαθήματος είναι να εμβαθύνει τις γνώσεις του κοινού στον προγραμματισμό. Καλύπτει θέματα, όπως: αντικειμενοστραφής προγραμματισμός με την Python, σύνδεση της Python με βάσεις δεδομένων, προγραμματισμός γραφικών διεπαφών με την tkinter.",
                        R.drawable.language_python
                    )
                )
            ),
            CourseCategory(
                "Δίκτυα Επικοινωνιών", iconRes = R.drawable.network, courses = listOf(
                    Course(
                        "C",
                        "Τεχνολογία Πολυμέσων",
                        "Μέσο επίπεδο",
                        "3 ώρες",
                        "Στο μάθημα καλύπτονται οι τεχνολογίες που χρησιμοποιούνται για την αναπαράσταση, επεξεργασία και αναπαραγωγή των διάφορων μέσων, οι τρόποι συνδυασμού επί μέρους μέσων για την κατασκευή πολυμεσικών εφαρμογών και το δικτυακό υπόβαθρο που απαιτείται για την υποστήριξη πολυμεσικών εφαρμογών.",
                        R.drawable.network
                    )
                )
            ),
            CourseCategory(
                "Κυβερνοασφάλεια", iconRes = R.drawable.shield_lock_outline, courses = listOf(
                    Course(
                        "D",
                        "Εισαγωγή στην Κυβερνοασφάλεια",
                        "Αρχάριος",
                        "1-3 ώρες",
                        "Η ασφάλεια στον κυβερνοχώρο περιλαμβάνει τεχνολογίες, διαδικασίες και ελέγχους, που έχουν σχεδιαστεί για να προστατεύουν συστήματα, δίκτυα και δεδομένα από επιθέσεις στον κυβερνοχώρο. Η αποτελεσματική ασφάλεια στον κυβερνοχώρο περιορίζει τον κίνδυνο επιθέσεων και προστατεύει οργανισμούς και ιδιώτες από τη μη εξουσιοδοτημένη εκμετάλλευση συστημάτων, δικτύων και τεχνολογιών.",
                        R.drawable.shield_lock_outline
                    )
                )
            ),
            CourseCategory(
                "Τεχνητή Νοημοσύνη", iconRes = R.drawable.robot, courses = listOf(
                    Course(
                        "E",
                        "Εισαγωγή στην Τεχνητή Νοημοσύνη",
                        "Αρχάριος",
                        "1-3 ώρες",
                        "Μάθετε τις βασικές έννοιες της Τεχνητής Νοημοσύνης, όπως η μηχανική μάθηση, τα νευρωνικά δίκτυα και οι εφαρμογές τους στη σύγχρονη τεχνολογία.",
                        R.drawable.robot
                    )
                )
            )
        )




        else -> emptyList()
    }
}








@Composable
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




@Composable
fun getCourseSlides(courseid: String): List<String> {
    return when (courseid) {
        "B"-> when (LocalAppLanguage.current) {
            AppLanguage.ENGLISH -> listOf(
                "Introduction\n\nLearn how to build web applications using Visual Studio Code...",
                "Getting Started\n\nSet up your development environment and create your first project...",
                "HTML Basics\n\nUnderstand the structure of HTML documents...",
                "CSS Styling\n\nLearn how to style your web pages with CSS..."
            )




            AppLanguage.GREEK -> listOf(
                "Περιγραφή Μαθήματος\n\nΜάθετε τα βασικά της Python...",
                "Ξεκινώντας\n\nΡύθμιση περιβάλλοντος εργασίας...",
                "Μεταβλητές και Τύποι Δεδομένων\n\nΕισαγωγή σε strings, integers, κ.λπ."
            )




            else -> emptyList()
        }




        "A" -> when (LocalAppLanguage.current) {
            AppLanguage.ENGLISH -> listOf(
                "Course Description\n" +
                        "\n" +
                        "Would you like to be able to write a simple program in the Python language—the most popular among the \"new programming languages\"—that allows you (among many other things) to extract interesting information from the vast \"ocean\" of the internet? For example, from the public spending program \"Diavgeia\" or from the ever-present social networks? This course is designed to provide that skill to any citizen who is interested in acquiring it.\n" +
                        "\n" +
                        "A student who completes this course will be able to program in Python and solve interesting practical problems, such as extracting information from websites and creating simple games.",
                "Reasons Why Python Is Suitable for an Introduction to Programming\n" +
                        "\n" +
                        "It is a language with simple and readable syntax.\n" +
                        "\n" +
                        "Easy interaction: it is an interpreted language.\n" +
                        "\n" +
                        "It is an open-source language with a large community and extensive support resources.\n" +
                        "\n" +
                        "It has libraries for solving various problems (data analysis, web-related tasks).\n" +
                        "\n" +
                        "Supports multiple programming paradigms (procedural and object-oriented).\n" +
                        "\n" +
                        "Brief Historical Notes\n" +
                        "\n" +
                        "It was created in 1989 by Guido van Rossum.\n" +
                        "\n" +
                        "Its name comes from Monty Python, not the python snake—despite the logo.\n" +
                        "\n" +
                        "Python 1.0 was released in 1994.\n" +
                        "\n" +
                        "Python 2.0 came out in 2000 (latest version: 2.7), and Python 3.0 in 2008 (current version: 3.9).\n" +
                        "\n" +
                        "Python 3 is becoming the dominant version and has some minor differences from version 2, e.g., print(x) instead of print x.",
                "Installing Python \n\nFrom the python.org website, go to the Downloads tab and select the latest version of Python 3 for your computer’s operating system. It should be noted that Python 2 is already pre-installed on many computers, but in this course series, we will be using Python 3.\n" +
                        "\n" +
                        "Once the installation is complete, you can check that Python was installed on your computer by typing the following command in the command line (cmd on Windows operating systems):\n" +
                        "\n" +
                        "nginx\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "python\n" +
                        "Note: On some operating systems, the python command will launch Python 2.7, which is already installed. To access Python 3.x.x, you may need to use the command:\n" +
                        "\n" +
                        "nginx\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "python3\n" +
                        "When you run the python command, the interactive environment for the language will start in the command line. For example, you might see a response like this:\n" +
                        "\n" +
                        "csharp\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "Python 3.9.1 (default, Feb  3 2021, 07:04:15) \n" +
                        "[Clang 12.0.0 (clang-1200.0.32.29)] on darwin  \n" +
                        "Type \"help\", \"copyright\", \"credits\" or \"license\" for more information.  \n" +
                        ">>> \n" +
                        "In this environment—the Python interpreter—you can enter a command at the prompt (>>>).\n" +
                        "\n" +
                        "For example, you can enter the command:\n" +
                        "\n" +
                        "python-repl\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print(\"Have a nice day\")\n" +
                        "Have a nice day\n" +
                        ">>> \n" +
                        "You just wrote your first simple Python program.\n" +
                        "\n" +
                        "You can also use the interpreter as a calculator:\n" +
                        "\n" +
                        "python-repl\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> 5+5\n" +
                        "10\n" +
                        ">>> \n" +
                        "To exit the interpreter, simply type:\n" +
                        "\n" +
                        "python-repl\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> exit()\n" +
                        "We usually interact with the language’s interpreter to run our code using some kind of tool. Along with the interpreter that runs Python programs, the IDLE (Integrated Development and Learning Environment) graphical interface is installed, as well as tools for managing libraries, such as pip or, on some systems, pip3 (which stands for \"Pip Installs Packages 3\").\n" +
                        "\n" +
                        "This tool, as we will see later, is useful for installing Python libraries.\n" +
                        "\n" +
                        "In this course, we will use IDLE to write, debug, and run the Python programs we will develop.\n" +
                        "\n" +
                        "However, you are free to use other editors such as VS Code or PyCharm, which are used by many developers.",
                "Numbers, Variables, Expressions\n\nSymbolic names for memory locations\n" +
                        "A variable name can include Latin characters a–z, A–Z, digits 0–9, and the underscore _.\n" +
                        "However, it cannot begin with a digit.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "x = 3  \n" +
                        "name = \"Kostas\"  \n" +
                        "pi = 3.14\n" +
                        "Variable Types\n" +
                        "type(x) — The type of a variable\n" +
                        "\n" +
                        "Numeric variable types:\n" +
                        "\n" +
                        "<int> integers\n" +
                        "\n" +
                        "<float> floating-point numbers\n" +
                        "\n" +
                        "<str> — strings\n" +
                        "\n" +
                        "Exercise 1.1\n" +
                        "Which of the following are valid variable names?\n" +
                        "\n" +
                        "a12345\n" +
                        "\n" +
                        "_12345\n" +
                        "\n" +
                        "_abcd\n" +
                        "\n" +
                        "12345_\n" +
                        "\n" +
                        "Assigning a value to a variable:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "x = x**2 + 5  \n" +
                        "y = \"Kostas \" + \"Maria\"\n" +
                        "Arithmetic operators:\n" +
                        "\n" +
                        "+ Addition\n" +
                        "\n" +
                        "- Subtraction\n" +
                        "\n" +
                        "* Multiplication\n" +
                        "\n" +
                        "/ Division\n" +
                        "\n" +
                        "// Integer division (quotient)\n" +
                        "\n" +
                        "% Modulo (remainder of division)\n" +
                        "\n" +
                        "** Exponentiation (power)\n" +
                        "\n" +
                        "Abbreviated assignment operators:\n" +
                        "\n" +
                        "x += a → Add a to x (x = x + a)\n" +
                        "\n" +
                        "x -= a → Subtract a from x (x = x - a)\n" +
                        "\n" +
                        "x *= a → Multiply x by a (x = x * a)\n" +
                        "\n" +
                        "x /= a → Divide x by a (x = x / a)\n" +
                        "\n" +
                        "x //= a → Integer divide x by a (x = x // a)\n" +
                        "\n" +
                        "x %= a → Remainder of x divided by a (x = x % a)\n" +
                        "\n" +
                        "x **= a → Raise x to the power of a (x = x ** a)\n" +
                        "\n" +
                        "Exercise 1.2\n" +
                        "Convert a temperature from degrees Fahrenheit to degrees Celsius.\n" +
                        "\n" +
                        "Exercise 1.3\n" +
                        "Calculate the annual tax for someone earning a monthly salary of €800,\n" +
                        "given a tax rate of 50%.",
                "Variables - types - numbers\n\n" +
                        "Installing and Using IDLE\n" +
                        "\n" +
                        "In this course, we will use **IDLE** to interact with Python.\n" +
                        "\n" +
                        "You should search for **IDLE** on your computer, for example, using your operating system’s search field. When you type **IDLE** and select it, the **IDLE Shell** window will open.\n" +
                        "\n" +
                        "It’s a good idea to **pin the IDLE application to your taskbar** so you can find it more easily in the future.\n" +
                        "\n" +
                        "The contents of the IDLE window are similar to the Python interpreter environment we saw in the command line. You can type commands directly into it.\n" +
                        "\n" +
                        "The IDLE environment also includes several other useful options:\n" +
                        "\n" +
                        "File, Edit, Shell, Options, Window, Help.\n" +
                        "\n" +
                        "One option that allows you to customize the appearance of the window is:\n" +
                        "Options → Configure IDLE\n" +
                        "\n" +
                        "Here, under the **Highlights** tab, you can select the **IDLE Dark** theme, which is the one used in this course’s lectures.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "Data Types\n" +
                        "\n" +
                        "In IDLE, I can assign a value to a variable:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> a = 5\n" +
                        "```\n" +
                        "\n" +
                        "The variable `a` represents a memory location in the computer where the value `5` is stored.\n" +
                        "\n" +
                        "The variable `a` is of type **`int`** (integer). You can confirm this using the `type()` function:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> type(a)\n" +
                        "<class 'int'>\n" +
                        "```\n" +
                        "\n" +
                        "Other data types that can be stored in variables include:\n" +
                        "\n" +
                        "* `float` for decimal numbers\n" +
                        "* `str` for sequences of characters, known as **strings**\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> name = \"Nikos\"\n" +
                        ">>> type(name)\n" +
                        "<class 'str'>\n" +
                        "```\n" +
                        "\n" +
                        "Here we see that the variable `name` is of type `'str'` (string).\n" +
                        "\n" +
                        "Another way to express decimal numbers is through **scientific notation**. These are numbers written in the form `a × 10^b`. Scientific notation is useful for representing very small or very large numbers.\n" +
                        "\n" +
                        "For example, the decimal number `0.0000000123` can be written in scientific notation as `1.23 × 10^-8`. In Python, this can be written as:\n" +
                        "\n" +
                        "```python\n" +
                        "1.23E-8\n" +
                        "```\n" +
                        "\n" +
                        "The symbol `E` stands for exponent and can be written as either `e` or `E`.\n" +
                        "\n" +
                        "As we will see, Python includes many more simple and complex data types.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "### **Variable Types in Python**\n" +
                        "\n" +
                        "It’s important to note that **you do not need to declare a variable’s type** in Python, as is required in other programming languages. The type can also change later depending on the data it references.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "Variable Names\n" +
                        "\n" +
                        "A variable name can include a sequence of:\n" +
                        "\n" +
                        "* Letters\n" +
                        "* Numbers\n" +
                        "* Underscores (`_`)\n" +
                        "\n" +
                        "Symbols such as `$``%`, etc., which are allowed in other programming languages, **are not permitted** in Python variable names.\n" +
                        "\n" +
                        "Another restriction: **the first character of the variable name must be a letter or an underscore**, not a number.\n" +
                        "Therefore, the following commands are invalid due to illegal variable names:\n" +
                        "\n" +
                        "```python\n" +
                        "123a = 10\n" +
                        "\$123 = 100\n" +
                        "```\n" +
                        "\n" +
                        "Variable names should also be **descriptive** of the content they hold. For example, a typical Python variable could be:\n" +
                        "\n" +
                        "```python\n" +
                        "max_velocity = 150\n" +
                        "```\n" +
                        "\n" +
                        "It’s common practice to follow the **snake\\_case** naming convention in Python, where words in the variable name are separated by underscores.\n",
                "Assigning a Value to a Variable\n\nAs we’ve already seen, we can assign a value to a variable:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> a = 5\n" +
                        "```\n" +
                        "\n" +
                        "We can then use the variable `a` in an expression that includes other variables, values, and **operators** (symbols for operations).\n" +
                        "\n" +
                        "The most important operators used in expressions are:\n" +
                        "\n" +
                        "* `+` Addition\n" +
                        "* `-` Subtraction\n" +
                        "* `*` Multiplication\n" +
                        "* `/` Division\n" +
                        "* `//` Integer division (quotient)\n" +
                        "* `%` Modulo (remainder of division)\n" +
                        "* `**` Exponentiation (power)\n" +
                        "\n" +
                        "We typically assign the **result of an expression** to a variable.\n" +
                        "\n" +
                        "The **assignment statement** is written as:\n" +
                        "\n" +
                        "```python\n" +
                        "variable = expression\n" +
                        "```\n" +
                        "\n" +
                        "It is **not allowed** to use a variable in an expression **before it has been assigned a value**.\n" +
                        "\n" +
                        "For example, if the variable `c` has not yet been given a value, and we try to compute the expression:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> c + 5\n" +
                        "```\n" +
                        "\n" +
                        "We will get an **error message** from the interpreter (usually shown in red), such as:\n" +
                        "\n" +
                        "```\n" +
                        "Traceback (most recent call last):\n" +
                        "  File \"<pyshell#13>\", line 1, in <module>\n" +
                        "    c + 5\n" +
                        "NameError: name 'c' is not defined\n" +
                        "```\n" +
                        "\n" +
                        "On the other hand, if the variable already has a value, then it is **replaced by its value** in the expression, and the result is computed.\n" +
                        "\n" +
                        "**Example**: the following expression, for the variable `a` we used earlier, is:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> a ** 2\n" +
                        "25\n" +
                        "```\n",
                "Strings\n\nA string is a sequence of characters enclosed in single quotes ', double quotes \", or triple single quotes ''' and triple double quotes \"\"\".\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> name1 = \"Agamemnon\"\n" +
                        ">>> name2 = 'Clytemnestra'\n" +
                        ">>> story = '''Clytemnestra was the daughter of Tyndareus\n" +
                        "and Leda, sister of beautiful Helen and the Dioscuri,\n" +
                        "and the wife of Agamemnon.'''\n" +
                        "Single and double quotes are completely equivalent. Triple single quotes ''' or triple double quotes \"\"\" are used to enclose multiline text, which is not allowed with the other quote types.\n" +
                        "\n" +
                        "A string may contain Unicode characters as well as special escape sequences, introduced with a backslash \\, such as the backslash itself, quotes, or non-printable control characters.\n" +
                        "\n" +
                        "Examples of escape sequences:\n" +
                        "\n" +
                        "\\\\ backslash\n" +
                        "\n" +
                        "\\' single quote\n" +
                        "\n" +
                        "\\\" double quote\n" +
                        "\n" +
                        "\\n newline\n" +
                        "\n" +
                        "\\t horizontal tab\n" +
                        "\n" +
                        "\\v vertical tab\n" +
                        "\n" +
                        "String Indexing and Slicing\n" +
                        "You can index elements of a string s like this:\n" +
                        "s[indx], where indx is an integer from 0 to n-1 (where n is the length of the string).\n" +
                        "Python uses zero-based indexing, so the first character is at index 0.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> name = \"Clytemnestra\"\n" +
                        ">>> name[1]\n" +
                        "'l'\n" +
                        "A substring of a string is defined using slicing: s[a:b], where a is the start index and b is the end index (not inclusive of the end character).\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> name[3:8]\n" +
                        "'temne'\n" +
                        "You can also use negative indexing: -1 refers to the last character, -2 to the second last, and so on.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> name[-5:]\n" +
                        "'estra'\n" +
                        "Character Encoding\n" +
                        "The ord(character) function returns the Unicode code of a character.\n" +
                        "\n" +
                        "The chr(integer) function returns the character that corresponds to a given Unicode code.\n" +
                        "\n" +
                        "Examples:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> ord(\"\\n\")\n" +
                        "10\n" +
                        ">>> ord(\" \")\n" +
                        "32\n" +
                        "Also:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> chr(940)\n" +
                        "'ά'",
                "String Operations – String Methods\n\nString Concatenation**\n" +
                        "\n" +
                        "Strings can be **concatenated (added together)** using the `+` operator.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> hello = \"good \" + \"morning\"\n" +
                        ">>> hello\n" +
                        "'good morning'\n" +
                        "```\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "#### **String Repetition**\n" +
                        "\n" +
                        "You can **multiply a string by an integer** to repeat it multiple times.\n" +
                        "\n" +
                        "```python\n" +
                        ">>> 7 * \"Thebes\"\n" +
                        "'ThebesThebesThebesThebesThebesThebesThebes'\n" +
                        "```\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "#### **String Length**\n" +
                        "\n" +
                        "The `len(s)` function returns the **number of characters** in the string `s`.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> name = \"Clytemnestra\"\n" +
                        ">>> len(name)\n" +
                        "13\n" +
                        "```\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "#### **String Methods**\n" +
                        "\n" +
                        "Strings in Python are objects and come with **built-in methods**, which are accessed using dot notation, like `s.method()`:\n" +
                        "\n" +
                        "* `s.replace(old, new [, max])` → replaces occurrences of `old` with `new`\n" +
                        "* `s.count(str)` → counts how many times `str` appears in `s`\n" +
                        "* `s.isalpha()` → `True` if `s` contains only letters\n" +
                        "* `s.isdigit()` → `True` if `s` contains only digits\n" +
                        "* `s.islower()` → `True` if `s` contains only lowercase letters\n" +
                        "* `s.upper()` → converts all characters to uppercase\n" +
                        "* `s.lower()` → converts all characters to lowercase\n" +
                        "* `s.capitalize()` → capitalizes the first character\n" +
                        "* `s.find(str)` → returns the index of `str`, or `-1` if not found\n" +
                        "* `s.join(seq)` → joins the elements of `seq` using `s` as a separator\n" +
                        "* `s.split(delimiter)` → splits `s` into a list using the `delimiter`\n" +
                        "* `s.strip([chars])` → removes characters from both ends of `s` (defaults to whitespace)\n" +
                        "* `s.decode(encoding='UTF-8')` → converts a byte string to a normal string (used in older Python versions)\n" +
                        "* `s.encode(encoding='UTF-8')` → converts a string to a byte string\n" +
                        "* `s.format(param)` → inserts `param` into `s` using `{}` placeholders\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "### **Lesson 2: Strings**\n" +
                        "\n" +
                        "#### **Exercise 2.1**\n" +
                        "\n" +
                        "Capitalize the first letter of the name:\n" +
                        "\n" +
                        "```python\n" +
                        "name = \"katerina\"\n" +
                        "name = name[0].upper() + name[1:]\n" +
                        "print(name)\n" +
                        "```\n" +
                        "\n" +
                        "#### **Exercise 2.2**\n" +
                        "\n" +
                        "Extract and combine parts of a phrase:\n" +
                        "\n" +
                        "```python\n" +
                        "stixos = \"I stay alone in my present\"\n" +
                        "n = stixos[:5] + stixos[-9:-4]\n" +
                        "print(n)\n" +
                        "```\n" +
                        "\n" +
                        "#### **Exercise 2.3**\n" +
                        "\n" +
                        "Display a formatted message:\n" +
                        "\n" +
                        "```python\n" +
                        "st = 24 * \"=\" + \"\\n\" + 4 * \"  \" + \"Program Start\\n\" + 24 * \"=\"\n" +
                        "print(st)\n" +
                        "```\n",
                "The print() Function\n\n" +
                        "The print() function displays a string in the program's output window.\n" +
                        "\n" +
                        "Syntax:\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "print(arguments, sep='s', end='e')\n" +
                        "Where:\n" +
                        "\n" +
                        "arguments: A list of zero or more expressions to print.\n" +
                        "\n" +
                        "sep='s': The separator between arguments. Default is a single space \" \".\n" +
                        "\n" +
                        "end='e': The end character of the output. Default is the newline character \\n.\n" +
                        "\n" +
                        "All arguments to print() are converted to strings using the str() function.\n" +
                        "\n" +
                        "Note: A major difference between Python 2 and Python 3 is that print was a statement in Python 2 but is a function in Python 3.\n" +
                        "\n" +
                        "Example\n" +
                        "Suppose we want to display the words of a line from C.P. Cavafy’s poem “The God Abandons Antony” separated by dashes:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print(\"say goodbye\", \"to\", \"to\", \"Alexandria\", \"that's\", \"leaving\", sep=\"-\")\n" +
                        "say goodbye-to-to-Alexandria-that's-leaving\n" +
                        "Now imagine changing the separator to:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "sep=3*\"*\"\n" +
                        "This would insert *** between the words.\n" +
                        "\n" +
                        "Controlling Line Breaks with end\n" +
                        "By default, two print() calls print on separate lines:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print('one'); print('two')\n" +
                        "one\n" +
                        "two\n" +
                        "But with end, we can print on the same line:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print('one', end=\" \"); print('two')\n" +
                        "one two\n" +
                        "You can have multiple Python statements on one line if separated by a semicolon (;).\n" +
                        "\n" +
                        "String Formatting in print()\n" +
                        "There are two main ways to format strings:\n" +
                        "\n" +
                        "Using the % operator (common in other programming languages)\n" +
                        "\n" +
                        "Using the .format() method (preferred in Python)\n" +
                        "\n" +
                        "Examples:\n" +
                        "Using %:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> '%s %s' % ('one', 'two')\n" +
                        "'one two'\n" +
                        "Using .format():\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> '{} {}'.format('one', 'two')\n" +
                        "'one two'\n" +
                        "More Examples:\n" +
                        "Let’s print a variable x with an explanation:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> x = 123.456\n" +
                        ">>> print(\"The value of x is\", x)\n" +
                        "The value of x is 123.456\n" +
                        "To limit the number of decimal places:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print(\"The value of x is %1.1f\" % x)\n" +
                        "The value of x is 123.5\n" +
                        "Or with .format():\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print(\"The value of x is {:1.1f}\".format(x))\n" +
                        "The value of x is 123.5\n" +
                        "The .format() method is more flexible, allowing parameterized formatting like \"x={x:5s}\".\n" +
                        "\n" +
                        "f = float\n" +
                        "\n" +
                        "d = integer\n" +
                        "\n" +
                        "s = string\n" +
                        "\n" +
                        "1.1f means at least one digit before the decimal and one after.\n" +
                        "\n" +
                        "String Alignment with .format()\n" +
                        "You can control how strings are aligned within a space:\n" +
                        "\n" +
                        "Left-aligned in a 5-character space:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print(\"---{:5s}---\".format(\"abc\"))\n" +
                        "---abc  ---\n" +
                        "Right-aligned in a 5-character space:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> print(\"---{:>5s}---\".format(\"abc\"))\n" +
                        "---  abc---\n" +
                        "Lesson 3: Input/Output\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "x = 123.456\n" +
                        "print(\"The value of x is:\", x)\n" +
                        "print(\"The value of x is %f\" % x)\n" +
                        "print(\"The value of x is %1.2f\" % x)\n" +
                        "print(\"The value of x is {:1.2f}\".format(x))",
                "The input() Function\n\nThe input() function takes user input as a string, regardless of whether the user types characters or numbers.\n" +
                        "\n" +
                        "So, if we want the user to input a number, we must convert the input using the int() function for an integer or float() for a decimal number.\n" +
                        "\n" +
                        "Example:\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> y = input(\"Enter a value:\")\n" +
                        "Enter a value:10\n" +
                        ">>> type(y)\n" +
                        "<class 'str'>\n" +
                        "To convert y to an integer:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "y = int(y)\n" +
                        "⚠\uFE0F Important: If the user doesn't enter a valid number, this line will raise a ValueError.\n" +
                        "\n" +
                        "In a later section, we’ll learn how to protect our code from invalid input using defensive programming techniques.\n" +
                        "\n" +
                        "Lesson 3: Input/Output\n" +
                        "Using input\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "x = input(\"Enter 1st number:\")\n" +
                        "y = input(\"Enter 2nd number:\")\n" +
                        "x = int(x)\n" +
                        "y = int(y)\n" +
                        "s = x + y\n" +
                        "m = x * y\n" +
                        "print(\"\\nSum: {}\\nProduct: {}\".format(s, m))\n" +
                        "Temperature Conversion from Celsius to Fahrenheit\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "C = input(\"Enter temperature in Celsius:\")\n" +
                        "C = float(C)\n" +
                        "F = C * 1.8 + 32\n" +
                        "print(\"The temperature in Fahrenheit is {:1.2f}\".format(F))",
                "Lists in Python\n\nA list is a complex data structure in Python. It is an object that holds a sequence of data elements, which may be of different types.\n" +
                        "\n" +
                        "A list is defined as a sequence of elements, separated by commas, enclosed in square brackets:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list = [\"George\", 3.14, 100]\n" +
                        ">>> type(a_list)\n" +
                        "<class 'list'>\n" +
                        "In this example, the list a_list contains 3 elements:\n" +
                        "\n" +
                        "the first is a string,\n" +
                        "\n" +
                        "the second is a float,\n" +
                        "\n" +
                        "and the third is an integer.\n" +
                        "\n" +
                        "⚠\uFE0F A list is mutable, meaning you can change the value of its elements, or add/remove items. We’ll now see how we can modify a list’s elements.\n" +
                        "\n" +
                        "Accessing List Elements\n" +
                        "You can access list elements using indices, starting from 0, just like with strings:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list[0]\n" +
                        "'George'\n" +
                        ">>> a_list[2]\n" +
                        "100\n" +
                        "Operators and Functions for Lists\n" +
                        "The + operator concatenates two lists.\n" +
                        "\n" +
                        "The * operator repeats the list n times.\n" +
                        "\n" +
                        "You can also use slicing just like with strings, e.g., a[n:m] returns the sublist from index n to m - 1.\n" +
                        "\n" +
                        "The len() function returns the number of elements in a list.\n" +
                        "\n" +
                        "Nested Elements\n" +
                        "An element of a list can also be a sequence (e.g., another list or a string):\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list = [\"George\", 3.14, 100]\n" +
                        ">>> a_list[0][2]\n" +
                        "'o'\n" +
                        "2D Lists (Lists of Lists)\n" +
                        "A 2D table can be represented as a list of lists:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> matrix = [[2, 6, 4, 7], [1, 0, 3, 2]]\n" +
                        ">>> matrix[1][-2:]\n" +
                        "[3, 2]\n" +
                        "Here, matrix[1] gives us the second row, and [-2:] returns the last two elements of that row.\n" +
                        "\n" +
                        "List Methods\n" +
                        "Python lists have many useful built-in methods:\n" +
                        "\n" +
                        "append(x): Adds x to the end of the list.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list.append(123)\n" +
                        "['George', 3.14, 100, 123]\n" +
                        "extend(L): Extends the list by appending elements from list L.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list.extend([\"Kostas\", 55])\n" +
                        "['George', 3.14, 100, 123, 'Kostas', 55]\n" +
                        "insert(i, x): Inserts x at position i.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list.insert(1, 200)\n" +
                        "['George', 200, 3.14, 100, 123, 'Kostas', 55]\n" +
                        "remove(x): Removes the first occurrence of x. Raises an error if x is not found.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list.remove(\"Katerina\")\n" +
                        "ValueError: list.remove(x): x not in list\n" +
                        "pop([i]): Removes and returns the element at index i. If i is not given, removes the last element.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list.pop(0)\n" +
                        "'George'\n" +
                        ">>> a_list\n" +
                        "[200, 3.14, 100, 123, 'Kostas', 55]\n" +
                        "index(x): Returns the index of the first occurrence of x.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list.index(100)\n" +
                        "2\n" +
                        "count(x): Returns the number of times x appears.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> b_list = [1, 1, 2, 3, 4, 5, 6]\n" +
                        ">>> b_list.count(1)\n" +
                        "2\n" +
                        ">>> b_list.count(8)\n" +
                        "0\n" +
                        "sort(): Sorts the list in-place (ascending by default). Only works for comparable data types.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> b_list = [8, 5, 10]\n" +
                        ">>> b_list.sort()\n" +
                        ">>> b_list\n" +
                        "[5, 8, 10]\n" +
                        "\n" +
                        ">>> names = [\"Maria\", \"Katerina\", \"Andreas\"]\n" +
                        ">>> names.sort()\n" +
                        ">>> names\n" +
                        "['Andreas', 'Katerina', 'Maria']\n" +
                        "reverse(): Reverses the order of the elements.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> names.reverse()\n" +
                        ">>> names\n" +
                        "['Maria', 'Katerina', 'Andreas']",
                "Tuples in Python\n\nTuples are sequences of elements, just like lists.\n" +
                        "\n" +
                        "A tuple is defined as a sequence of elements separated by commas and enclosed in parentheses.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_tuple = (\"George\", 3.14, 100)\n" +
                        ">>> a_tuple[0]\n" +
                        "'George'\n" +
                        ">>> len(a_tuple)\n" +
                        "3\n" +
                        "Tuples have one important difference from lists:\n" +
                        "\n" +
                        "Tuples are immutable — they cannot be changed after creation.\n" +
                        "\n" +
                        "For example, the following statement:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "a_tuple[1] = 555\n" +
                        "will cause an error:\n" +
                        "\n" +
                        "plaintext\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "TypeError: 'tuple' object does not support item assignment\n" +
                        "Common Mistake\n" +
                        "A common mistake made by new programmers — especially in countries like Greece, where a comma is used as a decimal separator — is the following:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> pi = 3,14\n" +
                        ">>> type(pi)\n" +
                        "<class 'tuple'>\n" +
                        ">>> pi\n" +
                        "(3, 14)\n" +
                        "This code does not produce an error, but the programmer intended to assign the value 3.14 to the variable pi. Instead, a tuple with two elements was created: (3, 14).\n" +
                        "This happens because commas create tuples, and in this case, parentheses are optional when defining a tuple.",
                "Dictionaries in Python\n\nDictionaries are objects that store data as key–value pairs, separated by commas. Dictionaries are enclosed in curly braces {}.\n" +
                        "\n" +
                        "Example of a dictionary:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> my_contacts = {\"Maria\": 6977, \"Nikos\": 6988}\n" +
                        ">>> len(my_contacts)\n" +
                        "2\n" +
                        "Keys in a dictionary must be unique.\n" +
                        "\n" +
                        "Valid types for keys include immutable data types such as:\n" +
                        "\n" +
                        "Strings\n" +
                        "\n" +
                        "Numbers\n" +
                        "\n" +
                        "Booleans\n" +
                        "\n" +
                        "Tuples\n" +
                        "\n" +
                        " You cannot use lists or other dictionaries as keys.\n" +
                        "\n" +
                        "Accessing elements in a dictionary is similar to lists using square brackets [ ].\n" +
                        "However, dictionary keys are not integer indices, so their position is not fixed or ordered.\n" +
                        "\n" +
                        "Adding or Updating Elements\n" +
                        "You can add or update an element using:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "dictionary[key] = value\n" +
                        "If the key doesn't exist, a new entry is created.\n" +
                        "\n" +
                        "If the key already exists, its value is updated.\n" +
                        "\n" +
                        "Removing Elements\n" +
                        "To remove an entry:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "del dictionary[key]\n" +
                        "Dictionary Methods\n" +
                        "Useful methods for handling dictionaries:\n" +
                        "\n" +
                        "d.keys() → returns a sequence of the dictionary’s keys\n" +
                        "\n" +
                        "d.values() → returns a sequence of the dictionary’s values\n" +
                        "\n" +
                        "d.items() → returns a sequence of (key, value) tuples\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> my_contacts.items()\n" +
                        "dict_items([('Maria', 6977777777), ('Nikos', 6988888888)])\n" +
                        "These methods return special data types:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> type(my_contacts.items())\n" +
                        "<class 'dict_items'>\n" +
                        ">>> type(my_contacts.keys())\n" +
                        "<class 'dict_keys'>\n" +
                        ">>> type(my_contacts.values())\n" +
                        "<class 'dict_values'>\n" +
                        "You can convert them into lists using list():\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> list(my_contacts.keys())\n" +
                        "['Maria', 'Nikos']\n" +
                        "Lesson 5: Dictionaries – Examples\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "# Example with dictionary and dictionary update\n" +
                        "a = {\n" +
                        "   1: \"hydrogen\",\n" +
                        "   6: \"carbon\",\n" +
                        "   7: \"nitrogen\",\n" +
                        "   8: \"oxygen\",\n" +
                        "}\n" +
                        "\n" +
                        "a.update({8: \"phosphorous\", 9: \"sulfur\"})\n" +
                        "print(a)\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "# Nobel laureates – example with a tuple as the key\n" +
                        "nobel_prize_winners = {\n" +
                        "    (1979, \"physics\"): [\"Glashow\", \"Salam\", \"Weinberg\"],\n" +
                        "    (1962, \"chemistry\"): [\"Hodgkin\"],\n" +
                        "    (1984, \"biology\"): [\"McClintock\"],\n" +
                        "}\n" +
                        "\n" +
                        "print(nobel_prize_winners)\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "# Example: presenting a dictionary in sorted order\n" +
                        "contacts = {\"Nikos\": 777888, \"Maria\": 666544, \"Katerina\": 444333}\n" +
                        "print(contacts)\n" +
                        "\n" +
                        "contacts_sorted = list(contacts.items())\n" +
                        "contacts_sorted.sort()\n" +
                        "\n" +
                        "print(contacts_sorted)",
                "Sets in Python\n\nAnother data type similar to lists — and particularly useful when you need to store collections of unique elements — is the set.\n" +
                        "\n" +
                        "This data type follows the mathematical concept of a set, meaning a collection of distinct elements.\n" +
                        "\n" +
                        "The elements of a set are unique.\n" +
                        "\n" +
                        "They do not have a specific order or position.\n" +
                        "\n" +
                        "Defining and Modifying Sets\n" +
                        "A set is defined by enclosing its elements in curly braces {}.\n" +
                        "You can:\n" +
                        "\n" +
                        "Add elements using .add()\n" +
                        "\n" +
                        "Remove elements using .remove()\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_set = {1, 2, 3, 4}\n" +
                        ">>> a_set.add(5)\n" +
                        ">>> a_set\n" +
                        "{1, 2, 3, 4, 5}\n" +
                        ">>> a_set.remove(4)\n" +
                        ">>> a_set\n" +
                        "{1, 2, 3, 5}\n" +
                        "Creating a Set from a List\n" +
                        "The set() function can take a list as an argument and return a set object, automatically removing duplicates.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list = [1, 2, 3, 4, 4, 4, 5]\n" +
                        ">>> a_set = set(a_list)\n" +
                        ">>> a_set\n" +
                        "{1, 2, 3, 4, 5}\n" +
                        "Practical Use Case\n" +
                        "To remove duplicates from a list, you can use:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> a_list = [1, 2, 3, 4, 4, 4, 5]\n" +
                        ">>> list(set(a_list))\n" +
                        "[1, 2, 3, 4, 5]\n" +
                        "Note: Since sets are unordered, the resulting list may not preserve the original order of elements.",
                "Exercise: My Contacts\n\nCreate a dictionary of phone contacts and write commands to:\n" +
                        "\n" +
                        "Insert new entries provided by the user.\n" +
                        "\n" +
                        "Print the dictionary sorted in alphabetical order.\n" +
                        "\n" +
                        "You should sort the dictionary based on the contact's name (the dictionary key).\n" +
                        "\n" +
                        "To sort the list of contacts, use the sort() method on the list obtained by converting the (key, value) pairs from dictionary.items() into a list.\n" +
                        "\n" +
                        "Solution\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "epafes = {\"Nikos\": 111222, \"Maria\": 333444}\n" +
                        "\n" +
                        "# Insert new contact\n" +
                        "print(\"Current contacts: \\n\", epafes)\n" +
                        "print(\"Enter a new contact:\")\n" +
                        "name = input(\"Enter name: \")\n" +
                        "tel = input(\"Enter phone number: \")\n" +
                        "epafes[name] = int(tel)\n" +
                        "print(\"Updated contacts: \\n\", epafes)\n" +
                        "\n" +
                        "# Sort contacts alphabetically by name\n" +
                        "epafes_list = list(epafes.items())\n" +
                        "epafes_list.sort()\n" +
                        "print(\"Sorted contact list:\\n\", epafes_list)",
                "Exercise: Counting Words\n\nIf the variable poem contains a song (for example):\n" +
                        "\n" +
                        "pgsql\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "I count my change  \n" +
                        "to get through another month  \n" +
                        "I open and I see no sky  \n" +
                        "you have the whole of Athens  \n" +
                        "on your plate  \n" +
                        "you open up and gaze into the void  \n" +
                        "Write a program that:\n" +
                        "\n" +
                        "Prints the words in alphabetical order.\n" +
                        "\n" +
                        "Calculates the number of words.\n" +
                        "\n" +
                        "Calculates the number of letters in the lyrics of this song by Odysseas Ioannou.\n" +
                        "\n" +
                        "Solution\n" +
                        "Below are code snippets for each part of the exercise:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "# Exercise 6.3\n" +
                        "\n" +
                        "poem = '''\n" +
                        "I count my change  \n" +
                        "to get through another month  \n" +
                        "I open and I see no sky  \n" +
                        "you have the whole of Athens  \n" +
                        "on your plate  \n" +
                        "you open up and gaze into the void  \n" +
                        "'''\n" +
                        "\n" +
                        "print(poem)\n" +
                        "\n" +
                        "# Alphabetical list of words\n" +
                        "\n" +
                        "list_words = poem.split()\n" +
                        "list_words.sort()\n" +
                        "print(list_words)\n" +
                        "\n" +
                        "# Word count\n" +
                        "\n" +
                        "print(\"Number of words: {}\".format(len(list_words)))\n" +
                        "\n" +
                        "# Letter count (excluding spaces and newlines)\n" +
                        "\n" +
                        "poem_clean = poem.replace(\" \", \"\").replace(\"\\n\", \"\")\n" +
                        "print(\"Number of letters: {}\".format(len(poem_clean)))",
                "Decision Structure if-elif-else\n\nPython compound statement\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "if condition:\n" +
                        "    statement_block\n" +
                        "Decision Structure\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "if condition:\n" +
                        "    statement1\n" +
                        "else:\n" +
                        "    statement2\n" +
                        "statement3\n" +
                        "or\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "if condition:\n" +
                        "    statement1\n" +
                        "    statement2\n" +
                        "    ...\n" +
                        "or more complex:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "if condition1:\n" +
                        "    statement1\n" +
                        "elif condition2:\n" +
                        "    statement2\n" +
                        "elif condition3:\n" +
                        "    statement3\n" +
                        "else:\n" +
                        "    statement4\n" +
                        "    statement5\n" +
                        "    ...\n" +
                        "Decision Structure: condition\n" +
                        "The condition is an expression that evaluates to either True or False.\n" +
                        "\n" +
                        "Comparison Operators\n" +
                        "== (equals)\n" +
                        "\n" +
                        "> (greater than)\n" +
                        "\n" +
                        "< (less than)\n" +
                        "\n" +
                        ">= (greater than or equal)\n" +
                        "\n" +
                        "<= (less than or equal)\n" +
                        "\n" +
                        "!= (not equal)\n" +
                        "\n" +
                        "Logical Operators\n" +
                        "and\n" +
                        "\n" +
                        "or\n" +
                        "\n" +
                        "not\n" +
                        "\n" +
                        "Membership Operators (part of structure)\n" +
                        "in\n" +
                        "\n" +
                        "not in\n" +
                        "\n" +
                        "Exercises\n" +
                        "7.1 Ask the user for their name; if it starts with the letter 'N', welcome them.\n" +
                        "\n" +
                        "7.2 According to regulations, the degree grade scales as:\n" +
                        "\n" +
                        "excellent: from 8.5 to 10,\n" +
                        "\n" +
                        "very good: from 6.5 up to (but not including) 8.5,\n" +
                        "\n" +
                        "good: from 5 up to (but not including) 6.5.\n" +
                        "\n" +
                        "Write a program that classifies the degree grade based on this.\n" +
                        "\n" +
                        "7.3 Write a program to find the solutions of a quadratic equation.\n" +
                        "The user inputs three numbers a, b, c, which are the coefficients of the quadratic equation\n" +
                        "ax^2+bx+c=0.\n"+
                        "The program should print the solutions.\n" +
                        "\n" +
                        "7.4 The user inputs a word.\n" +
                        "Only if the word contains the letters \"ο\" or \"ω\", reply with \"Thank you\".\n" +
                        "\n" +
                        "7.5 Read 3 numbers and return the largest of the three.",
                "IF Statement\n\nThe Python if statement allows controlling the flow of a program, meaning the conditional execution of a part of the program. The if statement is found in all programming languages.\n" +
                        "Syntax of if\n" +
                        "This is the first compound statement in Python where we see the use of indentation for grouping commands.\n" +
                        "\n" +
                        "The simplest form of if is:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "if condition:\n" +
                        "    block_of_commands_1\n" +
                        "In this case, block_of_commands_1 executes if the condition is true; otherwise, it does not execute.\n" +
                        "\n" +
                        "A more complex form is when if is followed by an else branch:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "if condition:\n" +
                        "    block_of_commands_1\n" +
                        "else:\n" +
                        "    block_of_commands_2\n" +
                        "If the condition is true, block_of_commands_1 executes; if false, block_of_commands_2 executes.\n" +
                        "\n" +
                        "Also, the else block may itself contain another if statement. In this case, instead of else: if ..., the expression elif is used, as follows:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "if condition_1:\n" +
                        "    block_of_commands_1\n" +
                        "elif condition_2:\n" +
                        "    block_of_commands_2\n" +
                        "else:\n" +
                        "    block_of_commands_3\n" +
                        "In this example, first condition_1 is checked. If it is true, block_of_commands_1 executes. Otherwise, condition_2 is checked. If condition_2 is true (meaning condition_1 was false but condition_2 true), then block_of_commands_2 executes. Finally, if both condition_1 and condition_2 are false, then block_of_commands_3 executes.\n" +
                        "\n" +
                        "The elif branch can be repeated multiple times.\n" +
                        "\n" +
                        "The condition of an if\n" +
                        "The condition is a logical expression that evaluates to True or False.\n" +
                        "\n" +
                        "Note that the integer 0, the decimal number 0.0, the empty string \"\", the empty list [], the empty dictionary {}, etc., are considered equivalent to False. Whereas nonzero numbers, or lists/dictionaries with elements, evaluate to True.\n" +
                        "\n" +
                        "Explain the example below:\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> if \"0\":\n" +
                        "\tprint(\"ok\")\n" +
                        "ok\n" +
                        "\n" +
                        ">>> if 0:\n" +
                        "\tprint(\"ok\")\n" +
                        "Explanation: The first logical expression is a non-empty string, which evaluates to true, so print() executes. The second is the integer 0, which is false, so print() does not execute.\n" +
                        "\n" +
                        "Types of logical expressions\n" +
                        "A logical expression is a comparison (checking equality or inequality) between two values or two variables.\n" +
                        "\n" +
                        "Comparison operators\n" +
                        "== equal to\n" +
                        "\n" +
                        "> greater than\n" +
                        "\n" +
                        "< less than\n" +
                        "\n" +
                        ">= greater than or equal to\n" +
                        "\n" +
                        "<= less than or equal to\n" +
                        "\n" +
                        "!= not equal to\n" +
                        "\n" +
                        "Also, a logical expression can be a combination of expressions linked by logical operators and, or, and not.\n" +
                        "\n" +
                        "Logical operators:\n" +
                        "Expression a and b is:\n" +
                        "\n" +
                        "True if both a and b are true\n" +
                        "\n" +
                        "False if either a or b is false\n" +
                        "\n" +
                        "Expression a or b is:\n" +
                        "\n" +
                        "True if at least one of a or b is true\n" +
                        "\n" +
                        "False if both are false\n" +
                        "\n" +
                        "Expression not a is:\n" +
                        "\n" +
                        "True if a is false\n" +
                        "\n" +
                        "False if a is true\n" +
                        "\n" +
                        "Example:\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "v = input(\"grade: \")\n" +
                        "v = float(v)\n" +
                        "if v >= 5 and v <= 10:\n" +
                        "    print(\"you passed\")\n" +
                        "    print(\"congratulations!!\")\n" +
                        "elif v < 5 and v >= 0:\n" +
                        "    print(\"you failed\")\n" +
                        "    print(\"try again!!!\")\n" +
                        "else:\n" +
                        "    print(\"there is an error: grade should be between 0 and 10\")\n" +
                        "In this example, first it checks if the variable v is between 5 and 10, then if between 0 and 5, and for any other case prints an error message.",
                "Example: solution of a quadratic equation\n\nThe Problem\n" +
                        "Write a program that finds the solutions of a quadratic equation.\n" +
                        "\n" +
                        "The user must provide 3 numbers, a, b, and c, which are the parameters of the quadratic equation:\n" +
                        "\n" +
                        "\uD835\uDC4E\n" +
                        "\uD835\uDC65\n" +
                        "2\n" +
                        "+\n" +
                        "\uD835\uDC4F\n" +
                        "\uD835\uDC65\n" +
                        "+\n" +
                        "\uD835\uDC50\n" +
                        "=\n" +
                        "0\n" +
                        "ax \n" +
                        "2\n" +
                        " +bx+c=0\n" +
                        "The program should print the solutions, i.e., the values of \n" +
                        "\uD835\uDC65\n" +
                        "x that satisfy the equation, or print appropriate messages if there are no solutions.\n" +
                        "\n" +
                        "If the solution is a decimal number, it should be printed with two decimal places.\n" +
                        "\n" +
                        "Suggested Solution\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "a = input(\"a=\")\n" +
                        "b = input(\"b=\")\n" +
                        "c = input(\"c=\")\n" +
                        "print(\"We will solve the equation:\")\n" +
                        "print(\"{}x**2 + {}x + {} = 0\\n\".format(a, b, c))\n" +
                        "\n" +
                        "a = float(a)\n" +
                        "b = float(b)\n" +
                        "c = float(c)\n" +
                        "\n" +
                        "# Check different cases of a, b, c\n" +
                        "if a == 0:\n" +
                        "    if b == 0:\n" +
                        "        if c == 0:\n" +
                        "            print(\"There are infinite solutions\")\n" +
                        "        else:\n" +
                        "            print(\"There are no solutions\")\n" +
                        "    else:\n" +
                        "        # Linear equation bx + c = 0\n" +
                        "        print(\"The solution is x1 = x2 = {:1.2f}\".format(-c / b))\n" +
                        "else:\n" +
                        "    discriminant = b**2 - 4 * a * c\n" +
                        "    print(\"The discriminant is {:1.2f}\".format(discriminant))\n" +
                        "\n" +
                        "    if discriminant < 0:\n" +
                        "        print(\"The equation has no real solutions\")\n" +
                        "    else:\n" +
                        "        x1 = (-b + discriminant**0.5) / (2 * a)\n" +
                        "        x2 = (-b - discriminant**0.5) / (2 * a)\n" +
                        "        print(\"The solutions are: x1 = {:1.2f}, x2 = {:1.2f}\".format(x1, x2))",
                "For loop structure\n\nA loop structure allows executing a part of the program many times. This is a very common requirement in programming.\n" +
                        "\n" +
                        "There are various Python commands that allow repeated execution of a part of the program. The for loop, which we will see first, is based on enumerating the repetitions, while the while loop, which will be covered later, is based on checking a termination condition.\n" +
                        "\n" +
                        "The syntax of the for statement is:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "for element in collection:\n" +
                        "    block_of_statements\n" +
                        "The block_of_statements repeats as many times as there are elements in the collection, and in each iteration, the variable element takes the value of each member of the collection. The collection can be a list, a string, a dictionary, a tuple, or any iterable object.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> for fruit in [\"μπανάνες\", \"κεράσια\", \"μήλα\"]:\n" +
                        "        print(fruit, end=\", \")\n" +
                        "μπανάνες, κεράσια, μήλα,\n" +
                        "In this example, the for block repeated 3 times (the number of elements in the list), and in each iteration, the variable fruit took the corresponding value, which was printed.\n" +
                        "\n" +
                        "break and continue in a for loop\n" +
                        "Two Python commands that accompany loops are break and continue.\n" +
                        "\n" +
                        "break inside a loop forces the program execution to jump out of the loop.\n" +
                        "\n" +
                        "continue stops the current iteration, skips the remaining commands, and moves on to the next iteration.\n" +
                        "\n" +
                        "Often a for loop is followed by an else clause containing a block of commands executed if the loop finishes normally (without a break).\n" +
                        "\n" +
                        "Example program\n" +
                        "A program that knows 10 secret random numbers and asks the user to guess one. It replies if the user guessed correctly.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "secret = [2, 5, 8, 11, 12, 15, 18, 20]\n" +
                        "guess = int(input(\"δώσε αριθμό:\"))\n" +
                        "for number in secret:\n" +
                        "    if number == guess:\n" +
                        "        print(\"μάντεψες σωστά!\")\n" +
                        "        break\n" +
                        "else:\n" +
                        "    print(\"όχι δεν βρέθηκε ο αριθμός\")\n" +
                        "Program execution example\n" +
                        "python-repl\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "======== RESTART:  \n" +
                        "δώσε αριθμό:10\n" +
                        "όχι δεν βρέθηκε ο αριθμός\n" +
                        ">>> \n" +
                        "======== RESTART: \n" +
                        "δώσε αριθμό:15\n" +
                        "μάντεψες σωστά!\n" +
                        ">>> \n" +
                        "On the second line, the program asks the user for a number and assigns it to the variable guess after converting the input to an integer. Then it loops through the list secret with a for loop and checks if the current number equals the user's guess. If yes, it prints \"μάντεψες σωστά!\" (\"you guessed right!\") and breaks the loop since there is no point continuing. If the loop ends without finding the number, the else part executes and prints \"όχι δεν βρέθηκε ο αριθμός\" (\"no, the number was not found\").\n" +
                        "\n" +
                        "Iterating over dictionary elements\n" +
                        "If the collection in a for loop is a dictionary, it is interesting to see what values the loop variable takes.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> dd = {5:50, 6:66, 7:77}\n" +
                        ">>> for i in dd:\n" +
                        "        print(i, end=\" | \")\n" +
                        "5 | 6 | 7 | \n" +
                        ">>> \n" +
                        "From the example, the loop variable takes the keys of the dictionary. If you want to print both keys and values in each iteration, you have two ways:\n" +
                        "\n" +
                        "Use the key to get the value dd[i]\n" +
                        "\n" +
                        "Or loop through the dictionary with dd.items(), which returns tuples (key, value):\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> for t in dd.items():\n" +
                        "        print(t, end=\" | \")\n" +
                        "(5, 50) | (6, 66) | (7, 77) | \n" +
                        ">>> ",
                "Function range\n\nThe for structure is found in almost all programming languages, but the most common case is that the counting variable takes integer values from a starting value that increases until a termination condition is met.\n" +
                        "\n" +
                        "In Python, a similar behavior is achieved by using the iterable object returned by the function range(start, stop, step), which returns a sequence of integers starting from the value start and ending at stop - 1, increasing by step on each iteration.\n" +
                        "\n" +
                        "If only one argument is given, range(x), it is assumed that start=0, stop=x, and step=1.\n" +
                        "\n" +
                        "If two arguments are given, range(x, y), it is assumed that start=x, stop=y, and step=1.\n" +
                        "\n" +
                        "For example:\n" +
                        "\n" +
                        "The object returned by range(3) is the sequence 0, 1, 2.\n" +
                        "\n" +
                        "The object returned by range(1, 5) is the sequence 1, 2, 3, 4.\n" +
                        "\n" +
                        "The object returned by range(1, 5, 2) is the sequence 1, 3.\n" +
                        "\n" +
                        "An example of using range in a for loop is printing the squares of the first 5 positive integers:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> for i in range(1, 6):\n" +
                        "    print(i**2, end=\"\\t\")\n" +
                        "1\t4\t9\t16\t25\t\n" +
                        ">>> \n" +
                        "We can also use range to count the number of repetitions of a block. If we want to print a message 3 times:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> for _i in range(3):\n" +
                        "    print(\"goodmorning\")\n" +
                        "goodmorning\n" +
                        "goodmorning\n" +
                        "goodmorning\n" +
                        ">>>\n" +
                        "In this example, we see a programming convention many developers use: variable names that are auxiliary and not used elsewhere start with the symbol _. In this case, the variable _i is not used in any command other than the for loop itself.",
                "While loop structure\n\nThe while repetition structure is not related to iterating over a collection of elements like for, but rather to repeating a block of statements as long as a condition holds true. When the condition stops being true, the repetitions stop, and the program proceeds to the next instructions.\n" +
                        "\n" +
                        "The syntax of while is:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "while condition:\n" +
                        "    block_of_statements\n" +
                        "The while loop should be designed so that inside the block of statements there is a way to change the condition to achieve termination of the loop. If this does not happen, then we have an infinite loop.\n" +
                        "\n" +
                        "This structure, like for, can be combined with the break and continue statements and can be followed by an else clause.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "while condition:\n" +
                        "    block_of_statements_1\n" +
                        "    if condition:\n" +
                        "        continue  # go to the start of the loop\n" +
                        "    if condition:\n" +
                        "        break  # exit the loop\n" +
                        "else:\n" +
                        "    block_of_statements_2  # executed if the loop ends normally (without break)\n" +
                        "Also, a variation of while is the while True / break pattern:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "while True:\n" +
                        "    statements\n" +
                        "    if condition:\n" +
                        "        break\n" +
                        "This structure is used when there is no condition check initially, but it happens inside the block of statements, where we check some condition and when it is satisfied, we exit the loop using break.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "Suppose we ask the user for their name and print it in uppercase repeatedly until they enter the letter x.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "while True:\n" +
                        "    name = input('Name:')\n" +
                        "    if name.upper() in \"XΧ\":  # utf-8 codes 88 (Latin X) and 935 (Greek Χ)\n" +
                        "        break\n" +
                        "    print(\"ok\", name.upper())\n" +
                        "Sample run:\n" +
                        "\n" +
                        "makefile\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "Name:Νίκος\n" +
                        "ok ΝΊΚΟΣ\n" +
                        "Name:Κώστας\n" +
                        "ok ΚΏΣΤΑΣ\n" +
                        "Name:Μαρία\n" +
                        "ok ΜΑΡΊΑ\n" +
                        "Name:x\n" +
                        "Note: In this example, we check the input against both Latin and Greek capital \"X\", case-insensitive.\n" +
                        "\n" +
                        "Often this structure is used when it is not known in advance how many repetitions the loop will run, but it depends on external events, such as user input.\n" +
                        "\n" +
                        "Defensive programming example\n" +
                        "\n" +
                        "Suppose in a program we ask the user to provide a two-digit number. We can check the input value, and if it is not two digits, ask again. This is achieved by putting the input() command inside a while loop:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "# read two-digit number\n" +
                        "\n" +
                        "num = 0\n" +
                        "while num > 99 or num < 10:\n" +
                        "    num = int(input(\"Enter a positive two-digit number:\"))\n" +
                        "else:\n" +
                        "    print(\"Thank you, you entered {:2d}\".format(num))\n",
                "Brief Description of List Comprehension\n\nCreating a list from elements can be done using a for loop.\n" +
                        "\n" +
                        "Suppose we want to create a list containing numbers from 1 to 20 that are multiples of both 2 and 3.\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> li = []\n" +
                        ">>> for num in range(1, 21):\n" +
                        "\tif num % 2 == 0 and num % 3 == 0:\n" +
                        "\t\tli.append(num)\n" +
                        ">>> print(li)\n" +
                        "[6, 12, 18]\n" +
                        "In this example, we first create an empty list li and then iteratively check the numbers from 1 to 20 (returned by range(1, 21)), and those whose remainder when divided by both 2 and 3 is zero are added to the list. Finally, we print the list.\n" +
                        "\n" +
                        "An alternative way to do the same is with a list comprehension, which is a concise way to describe a list. This method, described next, is considered more Pythonic than the traditional for loop, and belongs to the declarative programming model, where we describe what we want, not how to produce it (procedural model).\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> li = [x for x in range(1, 21) if x % 2 == 0 and x % 3 == 0]\n" +
                        ">>> print(li)\n" +
                        "[6, 12, 18]\n" +
                        "We observe that indeed the for structure was replaced by a more concise description of the contents of the list li.\n" +
                        "\n" +
                        "In general, the concise list description is:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "[ expression(x) for x in Obj if condition ]\n" +
                        "where Obj is an iterable object such as a list, tuple, dictionary, etc., and the condition determines which elements x of Obj will be included in the new list.\n" +
                        "\n" +
                        "Here are two more examples.\n" +
                        "\n" +
                        "Example 2: Given the list L = [5,8,12,7], create a new list with elements of L that are odd numbers increased by 10.\n" +
                        "\n" +
                        "Using a for loop:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "L = [5,8,12,7]\n" +
                        "L1 = []\n" +
                        "for i in L:\n" +
                        "    if i % 2 == 1:\n" +
                        "        L1.append(i + 10)\n" +
                        "L = L1\n" +
                        "print(L)\n" +
                        "Using list comprehension:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "L = [5,8,12,7]\n" +
                        "L = [x + 10 for x in L if x % 2 == 1]\n" +
                        "print(L)\n" +
                        "Example 3: Given two strings s1 = \"abc\" and s2 = \"xyz\", create a list with all combinations of characters from s1 with those of s2.\n" +
                        "\n" +
                        "Using a for loop:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "s1 = \"abc\"\n" +
                        "s2 = \"xyz\"\n" +
                        "res = []\n" +
                        "for x in s1:\n" +
                        "    for y in s2:\n" +
                        "        res.append(x + y)\n" +
                        "print(res)\n" +
                        "Using list comprehension:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "res = [x + y for x in \"abc\" for y in \"xyz\"]\n" +
                        "print(res)\n" +
                        "Output:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "['ax', 'ay', 'az', 'bx', 'by', 'bz', 'cx', 'cy', 'cz']",
                "Random Date Generator\n\nExercise - Random Date Generator\n" +
                        "Task:\n" +
                        "Create a program that generates random valid dates. The user inputs the year (e.g., 2016) and the number of dates desired (e.g., 5). The program outputs a list of dates in the format DD-MM-YYYY (e.g., 05-03-2016).\n" +
                        "\n" +
                        "Preparation and approach\n" +
                        "a. Find the names of the months and the number of days in each month. A good source is the Wikipedia page about months.\n" +
                        "\n" +
                        "From the month list on that page, create a string months. Then, by processing this string with list comprehension, generate two lists: one with the month names and one with the number of days in each month.\n" +
                        "\n" +
                        "b. Find the algorithm to determine leap years, so you can decide if February has 28 or 29 days for the given year. Wikipedia's page on leap years provides the exact algorithm.\n" +
                        "\n" +
                        "c. To generate a random date, pick a random month number from 1 to 12, then pick a random day number within that month's valid days (e.g., January is from 1 to 31).\n" +
                        "\n" +
                        "If you want distinct dates, check before adding a date to the list if it already exists; if it does, try again.\n" +
                        "\n" +
                        "You will need to generate a random integer between two values, for which Python’s random module and its function randint(a,b) are suitable. This returns a pseudo-random integer x such that a <= x <= b.\n" +
                        "\n" +
                        "Also, remember how to import and use libraries in Python, e.g.,\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "import random\n" +
                        "random.randint(1, 12)\n" +
                        "Algorithm\n" +
                        "Store month names in the list month_names from the string months.\n" +
                        "\n" +
                        "Store the number of days per month in the list month_days.\n" +
                        "\n" +
                        "Ask the user for the year and validate that it is a positive integer.\n" +
                        "\n" +
                        "Check if the year is leap. If yes, set February days to 29 in month_days.\n" +
                        "\n" +
                        "Ask the user for how many random dates are needed; validate input.\n" +
                        "\n" +
                        "Loop: generate random month and day, format the date as DD-MM-YYYY and add it to a list if not already present. Stop when the list length reaches the desired count.\n" +
                        "\n" +
                        "Solution Code\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "import random\n" +
                        "\n" +
                        "months = '''Ιανουάριος (31 ημέρες)\n" +
                        "Φεβρουάριος (28 ή 29 ημέρες)\n" +
                        "Μάρτιος (31 ημέρες)\n" +
                        "Απρίλιος (30 ημέρες)\n" +
                        "Μάιος (31 ημέρες)\n" +
                        "Ιούνιος (30 ημέρες)\n" +
                        "Ιούλιος (31 ημέρες)\n" +
                        "Αύγουστος (31 ημέρες)\n" +
                        "Σεπτέμβριος (30 ημέρες)\n" +
                        "Οκτώβριος (31 ημέρες)\n" +
                        "Νοέμβριος (30 ημέρες)\n" +
                        "Δεκέμβριος (31 ημέρες)'''\n" +
                        "\n" +
                        "# Map accented Greek letters to unaccented versions\n" +
                        "tonoi = {\n" +
                        "    \"ά\":\"α\",\n" +
                        "    \"έ\": \"ε\",\n" +
                        "    \"ή\": \"η\",\n" +
                        "    \"ί\": \"ι\",\n" +
                        "    \"ό\": \"ο\",\n" +
                        "    \"ύ\": \"υ\",\n" +
                        "    \"ώ\": \"ω\"\n" +
                        "}\n" +
                        "\n" +
                        "# Extract month names from the string\n" +
                        "month_names = [x.split()[0] for x in months.split(\"\\n\")]\n" +
                        "print(month_names)\n" +
                        "\n" +
                        "# Extract number of days (first two digits inside parentheses)\n" +
                        "month_days = [int(x.split(\"(\")[1][:2]) for x in months.split(\"\\n\")]\n" +
                        "print(month_days)\n" +
                        "\n" +
                        "while True:\n" +
                        "    year = input(\"Έτος: \").strip()\n" +
                        "    if not year.isdigit():\n" +
                        "        break\n" +
                        "    else:\n" +
                        "        y = int(year)\n" +
                        "\n" +
                        "    # Leap year check\n" +
                        "    leap = False\n" +
                        "    if y % 4 == 0:\n" +
                        "        if y % 100 != 0:\n" +
                        "            leap = True\n" +
                        "    if not leap:\n" +
                        "        if y % 400 == 0:\n" +
                        "            leap = True\n" +
                        "\n" +
                        "    if leap:\n" +
                        "        month_days[1] = 29\n" +
                        "    else:\n" +
                        "        month_days[1] = 28\n" +
                        "\n" +
                        "    print(y, month_days)\n" +
                        "\n" +
                        "    random_days = input(\"Τυχαίες μέρες:\")\n" +
                        "    if not random_days.isdigit():\n" +
                        "        break\n" +
                        "    else:\n" +
                        "        random_days = int(random_days)\n" +
                        "        random_d_list = []\n" +
                        "\n" +
                        "        while len(random_d_list) < random_days:\n" +
                        "            m = random.randint(0, 11)          # random month index 0-11\n" +
                        "            d = random.randint(0, month_days[m] - 1)  # random day index\n" +
                        "            month_name = month_names[m]\n" +
                        "\n" +
                        "            # Remove accents from month name\n" +
                        "            month_temp = \"\"\n" +
                        "            for c in month_name:\n" +
                        "                if c in tonoi:\n" +
                        "                    month_temp += tonoi[c]\n" +
                        "                else:\n" +
                        "                    month_temp += c\n" +
                        "\n" +
                        "            date = \"{:02d}-{:02d}-{:04d}\".format(d + 1, m + 1, y)\n" +
                        "\n" +
                        "            # Uppercase month name for a variant output\n" +
                        "            month_name = month_temp.replace(\"ς\", \"Υ\").upper()\n" +
                        "            date_ = \"{:02d}-{:}-{:04d}\".format(d + 1, month_name, y)\n" +
                        "\n" +
                        "            if date not in random_d_list:\n" +
                        "                random_d_list.append(date)\n" +
                        "                print(date)\n" +
                        "                print(date_)\n" +
                        "\n" +
                        "        print(random_d_list)",
                "Definition of Functions with def\n\nDefinition\n" +
                        "A function is defined as a sequence of program instructions that performs a specific task. The most common type of function is one defined using the keyword def. In this case, the function has a name and can be called to execute multiple times.\n" +
                        "\n" +
                        "There is also the option of defining an anonymous function using the lambda keyword, which we will see in future lessons.\n" +
                        "\n" +
                        "Functions We've Seen So Far\n" +
                        "We’ve already used functions in previous lessons. Examples include:\n" +
                        "\n" +
                        "print(): prints its arguments to the output.\n" +
                        "\n" +
                        "len(): returns the length of a sequence (e.g., string, list).\n" +
                        "\n" +
                        "range(): returns a sequence of integers.\n" +
                        "\n" +
                        "type(): returns the type of its argument.\n" +
                        "\n" +
                        "int(): converts an argument to an integer.\n" +
                        "\n" +
                        "str(): converts an argument to a string.\n" +
                        "\n" +
                        "Some functions return a result, while others (like print()) simply perform an action and return nothing.\n" +
                        "\n" +
                        "We've also seen methods — functions that are called on an object using dot notation, like mylist.append(5), which adds 5 to the list mylist. These are typical of object-oriented programming.\n" +
                        "\n" +
                        "A common pattern in all cases is:\n" +
                        "\n" +
                        "The function name is followed by parentheses.\n" +
                        "\n" +
                        "Inside the parentheses are parameters, also called arguments, possibly in the form parameter=value.\n" +
                        "\n" +
                        "Functions help us organize code into smaller sections and are widely used in programming.\n" +
                        "\n" +
                        "In this chapter, we’ll learn how to create our own named functions and how to call them.\n" +
                        "\n" +
                        "Defining a Function\n" +
                        "To define a function in Python, use def:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def name(arguments):\n" +
                        "    function-body\n" +
                        "    ...\n" +
                        "    return value\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def adder(x, y):\n" +
                        "    result = x + y\n" +
                        "    return result\n" +
                        "In this example:\n" +
                        "\n" +
                        "The function is named adder.\n" +
                        "\n" +
                        "It has two arguments: x and y.\n" +
                        "\n" +
                        "It returns their sum using the return statement.\n" +
                        "\n" +
                        "Calling the function:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> adder(5, 10)\n" +
                        "15\n" +
                        "We can also store the result in a variable:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> s = adder(10, 20)\n" +
                        ">>> print(s)\n" +
                        "30\n" +
                        ">>> type(adder(5, 10))\n" +
                        "<class 'int'>\n" +
                        "Note:\n" +
                        "\n" +
                        "A function doesn't need to return a value — in such cases, return can be omitted.\n" +
                        "\n" +
                        "Functions can have multiple return points, based on conditions.\n" +
                        "\n" +
                        "Function Arguments\n" +
                        "In the previous example, adder() is defined with two parameters: x and y.\n" +
                        "\n" +
                        "If we try to call it with one or three arguments:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> adder(30, 40, 50)\n" +
                        "TypeError: adder() takes 2 positional arguments but 3 were given\n" +
                        "Python throws a TypeError, explaining that the function was defined to take 2 positional arguments, but 3 were given.\n" +
                        "\n" +
                        "Optional and Variable-Length Arguments\n" +
                        "Can we define functions with optional or variable-length arguments?\n" +
                        "\n" +
                        "Yes!\n" +
                        "\n" +
                        "Optional (Keyword) Arguments\n" +
                        "Defined with parameter=default_value. These follow the required (positional) parameters.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def adder(x, y=0):\n" +
                        "    result = x + y\n" +
                        "    return result\n" +
                        "Usage:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> adder(5)\n" +
                        "5\n" +
                        ">>> adder(5, 10)\n" +
                        "15\n" +
                        "Variable-Length Arguments\n" +
                        "Use *parameter to accept a variable number of arguments. Inside the function, this parameter behaves like a tuple.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def adder(*x):\n" +
                        "    result = sum(x)\n" +
                        "    return result\n" +
                        "Usage:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> adder()\n" +
                        "0\n" +
                        ">>> adder(30, 40, 50)\n" +
                        "120\n" +
                        "Since we defined x as a variable-length argument, we can call the function with any number of arguments, including none.\n" +
                        "\n",
                "Functions: Examples\n\nCreate a function isnum() that takes a string as input and returns whether it is a valid number or not.\n" +
                        "\n" +
                        "Note: Type Checking of Function Parameters\n" +
                        "Python, unlike other programming languages, does not require you to define the data type of function parameters. This makes it more flexible for the programmer, but it can also lead to runtime errors.\n" +
                        "Therefore, it’s important that we manually check the input data type.\n" +
                        "\n" +
                        "As we know, the type(x) function returns the data type of parameter x.\n" +
                        "If we want x to be an integer, we should check if it is of type int.\n" +
                        "However, we cannot write something like type(x) == \"int\" (as you might expect).\n" +
                        "Instead, the correct way is:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> type(5) is int\n" +
                        "True\n" +
                        ">>> type('nikos') is str\n" +
                        "True\n" +
                        "This observation is important for solving the exercise, where we must first check if the argument is a string.\n" +
                        "\n" +
                        "Suggested Solution\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def isnum(n = ''):\n" +
                        "    if not type(n) is str:\n" +
                        "        return False\n" +
                        "\n" +
                        "    print(n)\n" +
                        "    n = n.strip()\n" +
                        "\n" +
                        "    if n.isdigit():\n" +
                        "        return True\n" +
                        "    elif n[0] in '+-' and isnum(n[1:]):\n" +
                        "        return True\n" +
                        "    elif \".\" in n:\n" +
                        "        if n.count(\".\") <= 1 and isnum(n.replace(\".\", \"\")):\n" +
                        "            return True\n" +
                        "        else:\n" +
                        "            return False\n" +
                        "    else:\n" +
                        "        return False\n" +
                        "Usage Example\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "while True:\n" +
                        "    n = input(\"n=\")\n" +
                        "    if n == '':\n" +
                        "        break\n" +
                        "    print(isnum(n))\n" +
                        "This function checks if the input string represents a valid integer or float number (positive or negative), for example:\n" +
                        "\n" +
                        "\"123\" → True\n" +
                        "\n" +
                        "\"-45.6\" → True\n" +
                        "\n" +
                        "\"abc\" → False\n" +
                        "\n" +
                        "\"12.3.4\" → False",
                "Variable Scope\n\nThe concept of scope is broader than just variable scope in functions. In general, scope refers to the region of a program where a variable is known and can be used.\n" +
                        "\n" +
                        "For example, if a variable hasn't been initialized (i.e., assigned a value), it can't be used in an expression or passed as a function argument.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "print(val + 1)\n" +
                        "val = 10\n" +
                        "This will produce an error:\n" +
                        "\n" +
                        "pgsql\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "NameError: name 'val' is not defined\n" +
                        "This means that the variable val hasn’t been defined yet.\n" +
                        "If we reverse the order of the statements, the program will correctly print 11.\n" +
                        "\n" +
                        "Function Variable Scope\n" +
                        "When a variable is defined inside a function, it is called a local variable. Its scope is limited to within that function.\n" +
                        "This is true even if the variable name is the same as one defined outside the function.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def f():\n" +
                        "    name = \"Kostas\"\n" +
                        "\n" +
                        "f()\n" +
                        "print(name)\n" +
                        "This results in the following error:\n" +
                        "\n" +
                        "pgsql\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "NameError: name 'name' is not defined\n" +
                        "Why? Because the variable name is local to the function f() — it's only known within that function. Outside f(), in the main program, name is undefined.\n" +
                        "\n" +
                        "Scope Example with Multiple Functions\n" +
                        "Consider the following example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def main():\n" +
                        "    get_name()\n" +
                        "    print('Hello', name)\n" +
                        "\n" +
                        "def get_name():\n" +
                        "    name = input(\"What is your name?\")\n" +
                        "\n" +
                        "main()\n" +
                        "If the user enters: Nikos\n" +
                        "The output will be:\n" +
                        "\n" +
                        "perl\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "Traceback (most recent call last):\n" +
                        "  ...\n" +
                        "    print('Hello', name)\n" +
                        "NameError: name 'name' is not defined\n" +
                        "Why? Because the variable name in get_name() is local and not visible to main().\n" +
                        "\n" +
                        " Corrected Version:\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def main():\n" +
                        "    name = get_name()\n" +
                        "    print('Hello', name)\n" +
                        "\n" +
                        "def get_name():\n" +
                        "    name = input(\"What is your name?\")\n" +
                        "    return name\n" +
                        "\n" +
                        "main()\n" +
                        "Output:\n" +
                        "\n" +
                        "pgsql\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "What is your name? Nikos\n" +
                        "Hello Nikos\n" +
                        "Global Variables\n" +
                        "Variables defined outside any function are called global variables, and they are accessible throughout the program.\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def f2():\n" +
                        "    name = \"Maria\"\n" +
                        "    print(name)\n" +
                        "\n" +
                        "def f1():\n" +
                        "    name = \"Kostas\"\n" +
                        "    f2()\n" +
                        "\n" +
                        "name = \"George\"\n" +
                        "f1()\n" +
                        "Output:\n" +
                        "\n" +
                        "nginx\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "Maria\n" +
                        "Explanation:\n" +
                        "\n" +
                        "name = \"George\" is a global variable.\n" +
                        "\n" +
                        "f1() defines a local variable name = \"Kostas\" — known only in f1().\n" +
                        "\n" +
                        "f2() defines another local name = \"Maria\".\n" +
                        "\n" +
                        "The print(name) in f2() prints the local value \"Maria\".\n" +
                        "\n" +
                        "What happens if we remove name = \"Maria\" from f2()?\n" +
                        "Then the code becomes:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def f2():\n" +
                        "    print(name)\n" +
                        "\n" +
                        "def f1():\n" +
                        "    name = \"Kostas\"\n" +
                        "    f2()\n" +
                        "\n" +
                        "name = \"George\"\n" +
                        "f1()\n" +
                        "Now, f2() prints \"George\".\n" +
                        "This is because Python looks for a local variable name in f2(), doesn’t find one, and falls back to the global variable.\n" +
                        "\n" +
                        "Using global to Modify Global Variables\n" +
                        "What if we want to change a global variable inside a function?\n" +
                        "\n" +
                        "Example:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "a = 10\n" +
                        "\n" +
                        "def f():\n" +
                        "    a = 5\n" +
                        "\n" +
                        "f()\n" +
                        "print(a)\n" +
                        "This will print 10, because the a = 5 inside f() is a local assignment that doesn't affect the global a.\n" +
                        "\n" +
                        "To modify the global variable, we must use the global keyword:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "def f():\n" +
                        "    global a\n" +
                        "    a = 5\n" +
                        "\n" +
                        "a = 10\n" +
                        "f()\n" +
                        "print(a)\n" +
                        "Output:\n" +
                        "\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "5\n" +
                        "Now a inside f() is explicitly marked as global, so the assignment affects the global a.\n",
                "Modules\n\nUsing Modules in Python\n" +
                        "In previous chapters, we already used modules (also called libraries) from Python’s standard library.\n" +
                        "\n" +
                        "These modules are Python programs that perform specific tasks.\n" +
                        "\n" +
                        "As you progress in Python programming, you'll often find the need to load and use these libraries in your own code. We've already done this with the random and math libraries.\n" +
                        "\n" +
                        "Loading a Module\n" +
                        "To load a module into your program, you use the import statement, usually placed at the beginning of your code:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "import module_name\n" +
                        "Alternatively, you can use:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "from module_name import *\n" +
                        "Or, if you only want to import a specific part of the module:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "from module_name import element\n" +
                        "The module_name typically corresponds to a Python file named module_name.py, which is interpreted and loaded so that its contents (functions, variables, classes, etc.) can be used in your program.\n" +
                        "\n" +
                        "You can also create your own modules and call them from your main program. This helps in organizing your code into separate, manageable parts.\n" +
                        "\n" +
                        "Difference Between Import Methods\n" +
                        "There’s an important difference between the two import styles:\n" +
                        "\n" +
                        "With the first method (import module_name), you access elements using dot notation, like module_name.element.\n" +
                        "\n" +
                        "With the second method (from module_name import *), the module’s elements are added directly into your program’s namespace.\n" +
                        "\n" +
                        "Example:\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> import random\n" +
                        ">>> randint = random.randint(10, 20)\n" +
                        ">>> randint\n" +
                        "13\n" +
                        ">>> randint = random.randint(10, 20)\n" +
                        ">>> randint\n" +
                        "10\n" +
                        "Here, we use the randint() function from the random module twice to generate a pseudo-random integer between 10 and 20 and store it in a variable.\n" +
                        "\n" +
                        "Now let’s see how this behaves with the second import style:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        ">>> from random import *\n" +
                        ">>> randint = randint(10, 20)\n" +
                        ">>> randint\n" +
                        "12\n" +
                        ">>> randint(20, 30)\n" +
                        "This results in:\n" +
                        "\n" +
                        "vbnet\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "Traceback (most recent call last):\n" +
                        "  File \"<pyshell#6>\", line 1, in <module>\n" +
                        "    randint(20,30)\n" +
                        "TypeError: 'int' object is not callable\n" +
                        "What Happened?\n" +
                        "In this second example, we imported all contents of the random module directly into the program’s namespace.\n" +
                        "So after the first call, the name randint was reassigned to a local variable (an integer).\n" +
                        "The second call to randint() failed because randint is now an int, not a function.\n" +
                        "\n" +
                        "This happened because the second import method causes the module's contents to share the same namespace as your own program — increasing the chance of such name collisions.\n" +
                        "\n" +
                        "Conclusion:\n" +
                        "It is generally recommended to use the first import syntax:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "import module_name\n" +
                        "This avoids namespace conflicts between module elements and your own variables, making your code more predictable and easier to debug.",
                "Standard library\n\nThe Python Standard Library\n" +
                        "Python’s standard library contains several hundred modules, which cover:\n" +
                        "\n" +
                        "Data types, such as datetime, calendar\n" +
                        "\n" +
                        "Mathematical operations (e.g., math, random)\n" +
                        "\n" +
                        "Operating system tasks (e.g., os.path, os, time)\n" +
                        "\n" +
                        "Permanent data storage (e.g., sqlite3, pickle)\n" +
                        "\n" +
                        "File compression (e.g., zip)\n" +
                        "\n" +
                        "File formatting (e.g., csv, json)\n" +
                        "\n" +
                        "Cryptography (e.g., hashlib)\n" +
                        "\n" +
                        "Multithreading / concurrent execution (e.g., threading)\n" +
                        "\n" +
                        "Process communication (e.g., socket)\n" +
                        "\n" +
                        "Structured data tools (e.g., html, xml.etree)\n" +
                        "\n" +
                        "Internet access (e.g., urllib.request)\n" +
                        "\n" +
                        "Graphics (e.g., tkinter)\n" +
                        "\n" +
                        "...and much more.\n" +
                        "\n" +
                        "An overview of the Python Standard Library helps developers realize just how rich it is. All of it comes bundled with the Python distribution installed on your computer.\n" +
                        "\n" +
                        "This is why we often hear the phrase:\n" +
                        "\n" +
                        "\"Python comes with batteries included\" — everything you need is already packed in.\n" +
                        "\n" +
                        "Beyond the Standard Library\n" +
                        "Apart from the standard library, there are hundreds of thousands of external packages, many of which are complete frameworks or function/class libraries designed for specific purposes.\n" +
                        "\n" +
                        "The most comprehensive catalog of these packages is PyPI — the Python Package Index.\n" +
                        "\n" +
                        "There are currently over 300,000 packages listed there.\n" +
                        "\n" +
                        "Example: Using Flask\n" +
                        "Let’s say we want to build a web application, and after researching, we decide that the Flask library is the best fit.\n" +
                        "\n" +
                        "On PyPI, you can find all the information you need about Flask, including how to install and use it.\n" +
                        "\n" +
                        "If you try to load Flask in IDLE like this:\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "import flask\n" +
                        "You’ll get the following error:\n" +
                        "\n" +
                        "vbnet\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "ModuleNotFoundError: No module named 'flask'\n" +
                        "This happens because Flask is not part of the standard library — unlike previous examples like math or random.\n" +
                        "\n" +
                        "Installing External Packages\n" +
                        "In such cases, you need to open your system's command line (terminal) and run:\n" +
                        "\n" +
                        "bash\n" +
                        "Copy\n" +
                        "Edit\n" +
                        "pip install flask\n" +
                        "After the library is installed, you can import and use it in your program.\n" +
                        "\n" +
                        "Note:\n" +
                        "\n" +
                        "The pip command (or pip3 on some systems to avoid confusion with Python 2) is bundled with Python and used to install external packages and libraries.\n" +
                        "\n" +
                        "Other Package Resources\n" +
                        "Besides PyPI, there are other websites where you can discover and compare Python libraries. One popular option is:\n" +
                        "\n" +
                        "\uD83C\uDF10 https://python.libhunt.com — also known as Awesome Python.",
                "\n\n",
            )
            AppLanguage.GREEK -> listOf(




                "Περιγραφή μαθήματος\n" +
                        "\n" +
                        "Θα θέλατε να μάθετε να γράφετε απλά προγράμματα στη γλώσσα Python, την πιο δημοφιλή από τις νέες γλώσσες προγραμματισμού, που σας επιτρέπει (μεταξύ άλλων) να εξάγετε ενδιαφέρουσες πληροφορίες από τον απέραντο ωκεανό του διαδικτύου; Για παράδειγμα, από το πρόγραμμα δημόσιων δαπανών Διαφωτία ή από τα πανταχού παρόντα κοινωνικά δίκτυα; Αυτό το μάθημα έχει σχεδιαστεί για να παρέχει αυτή την ικανότητα σε κάθε πολίτη που ενδιαφέρεται να την αποκτήσει.\n" +
                        "\n" +
                        "Ένας μαθητής που ολοκληρώνει αυτό το μάθημα θα είναι σε θέση να προγραμματίζει σε Python και να επιλύει ενδιαφέροντα πρακτικά προβλήματα, όπως η εξαγωγή πληροφοριών από ιστότοπους και η δημιουργία απλών παιχνιδιών.",




                "Λόγοι για τους οποίους η Python είναι κατάλληλη για μια εισαγωγή στην προγραμματισμό\n" +
                        "\n" +
                        "Είναι μια γλώσσα με απλή και ευανάγνωστη σύνταξη.\n" +
                        "\n" +
                        "Εύκολη αλληλεπίδραση: είναι μια διερμηνευόμενη γλώσσα.\n" +
                        "\n" +




                        "Είναι μια γλώσσα ανοιχτού κώδικα με μεγάλη κοινότητα και εκτεταμένους πόρους υποστήριξης.\n" +
                        "\n" +
                        "Διαθέτει βιβλιοθήκες για την επίλυση διαφόρων προβλημάτων (ανάλυση δεδομένων, εργασίες σχετικές με τον ιστό).\n" +
                        "\n" +
                        "Υποστηρίζει πολλαπλά πρότυπα προγραμματισμού (διαδικαστικό και αντικειμενοστρεφές).\n" +
                        "\n" +




                        "Σύντομες ιστορικές σημειώσεις\n" +
                        "\n" +
                        "Δημιουργήθηκε το 1989 από τον Guido van Rossum.\n" +
                        "\n" +
                        "Το όνομά της προέρχεται από τους Monty Python, και όχι από το φίδι python, παρά το λογότυπό της.\n" +




                        "\n" +
                        "Το Python 1.0 κυκλοφόρησε το 1994.\n" +
                        "\n" +
                        "Το Python 2.0 κυκλοφόρησε το 2000 (τελευταία έκδοση: 2.7) και το Python 3.0 το 2008 (τρέχουσα έκδοση: 3.9).\n" +
                        "\n" +




                        "Η Python 3 γίνεται η κυρίαρχη έκδοση και έχει κάποιες μικρές διαφορές από την έκδοση 2, π.χ. print(x) αντί για print x.",
                "Εγκατάσταση της Python \n\nΑπό τον ιστότοπο python.org, μεταβείτε στην καρτέλα Downloads και επιλέξτε την τελευταία έκδοση της Python 3 για το λειτουργικό σύστημα του υπολογιστή σας. Πρέπει να σημειωθεί ότι το Python 2 είναι ήδη προεγκατεστημένο σε πολλούς υπολογιστές, αλλά σε αυτή τη σειρά μαθημάτων θα χρησιμοποιούμε το Python 3.\n" +
                        "\n" +
                        "Μόλις ολοκληρωθεί η εγκατάσταση, μπορείτε να ελέγξετε ότι το Python έχει εγκατασταθεί στον υπολογιστή σας πληκτρολογώντας την ακόλουθη εντολή στη γραμμή εντολών (cmd σε λειτουργικά συστήματα Windows):\n" +
                        "\n" +
                        "nginx\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "python\n" +
                        "Σημείωση: Σε ορισμένα λειτουργικά συστήματα, η εντολή python θα εκκινήσει το Python 2.7, το οποίο είναι ήδη εγκατεστημένο. Για να αποκτήσετε πρόσβαση στο Python 3.x.x, ίσως χρειαστεί να χρησιμοποιήσετε την εντολή:\n" +
                        "\n" +
                        "nginx\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "python3\n" +
                        "Όταν εκτελέσετε την εντολή python, το διαδραστικό περιβάλλον για τη γλώσσα θα ξεκινήσει στη γραμμή εντολών. Για παράδειγμα, ενδέχεται να δείτε μια απάντηση όπως αυτή:\n" +
                        "\n" +
                        "csharp\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "Python 3.9.1 (προεπιλογή, 3 Φεβρουαρίου 2021, 07:04:15) \n" +
                        "[Clang 12.0.0 (clang-1200.0.32.29)] σε darwin  \n" +




                        "Πληκτρολογήστε \"help\", \"copyright\", \"credits\" ή \"license\" για περισσότερες πληροφορίες.  \n" +
                        ">>> \n" +
                        "Σε αυτό το περιβάλλον — τον διερμηνέα Python — μπορείτε να εισαγάγετε μια εντολή στην προτροπή (>>>).\n" +
                        "\n" +
                        "Για παράδειγμα, μπορείτε να εισαγάγετε την εντολή:\n" +




                        "\n" +
                        "python-repl\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> print(\"Have a nice day\")\n" +
                        "Have a nice day\n" +
                        ">>> \n" +
                        "Μόλις γράψατε το πρώτο σας απλό πρόγραμμα Python.\n" +
                        "\n" +




                        "Μπορείτε επίσης να χρησιμοποιήσετε τον διερμηνέα ως αριθμομηχανή:\n" +
                        "\n" +
                        "python-repl\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> 5+5\n" +
                        "10\n" +
                        ">>> \n" +
                        "Για να βγείτε από τον διερμηνέα, απλά πληκτρολογήστε:\n" +




                        "\n" +
                        "python-repl\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> exit()\n" +




                        "Συνήθως αλληλεπιδρούμε με τον διερμηνέα της γλώσσας για να εκτελέσουμε τον κώδικά μας χρησιμοποιώντας κάποιο είδος εργαλείου. Μαζί με τον διερμηνέα που εκτελεί τα προγράμματα Python, εγκαθίσταται η γραφική διεπαφή IDLE (Integrated Development and Learning Environment), καθώς και εργαλεία για τη διαχείριση βιβλιοθηκών, όπως το pip ή, σε ορισμένα συστήματα, το pip3 (που σημαίνει Pip Installs Packages 3).\n" +
                        "\n" +




                        "Αυτό το εργαλείο, όπως θα δούμε αργότερα, είναι χρήσιμο για την εγκατάσταση βιβλιοθηκών Python.\n" +
                        "\n" +
                        "Σε αυτό το μάθημα, θα χρησιμοποιήσουμε το IDLE για να γράψουμε, να διορθώσουμε και να εκτελέσουμε τα προγράμματα Python που θα αναπτύξουμε.\n" +
                        "\n" +
                        "Ωστόσο, μπορείτε να χρησιμοποιήσετε και άλλους επεξεργαστές, όπως το VS Code ή το PyCharm, που χρησιμοποιούνται από πολλούς προγραμματιστές.",




                "Αριθμοί, μεταβλητές, εκφράσεις\n\nΣυμβολικά ονόματα για θέσεις μνήμης\n" +
                        "Ένα όνομα μεταβλητής μπορεί να περιλαμβάνει λατινικούς χαρακτήρες a–z, A–Z, ψηφία 0–9 και την υπογράμμιση _.\n" +
                        "Ωστόσο, δεν μπορεί να ξεκινά με ψηφίο.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        "x = 3  \n" +
                        "name = \"Kostas\"  \n" +
                        "pi = 3.14\n" +
                        "Τύποι μεταβλητών\n" +
                        "type(x) — Ο τύπος μιας μεταβλητής\n" +
                        "\n" +
                        "Αριθμητικοί τύποι μεταβλητών:\n" +




                        "\n" +
                        "<int> ακέραιοι\n" +
                        "\n" +
                        "<float> αριθμοί κινητής υποδιαστολής\n" +
                        "\n" +
                        "<str> — συμβολοσειρές\n" +
                        "\n" +
                        "Άσκηση 1.1\n" +
                        "Ποια από τα παρακάτω είναι έγκυρα ονόματα μεταβλητών;\n" +




                        "\n" +
                        "a12345\n" +
                        "\n" +
                        "_12345\n" +
                        "\n" +
                        "_abcd\n" +
                        "\n" +
                        "12345_\n" +
                        "\n" +
                        "Ανάθεση τιμής σε μεταβλητή:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "x = x**2 + 5  \n" +
                        "y = \"Kostas \" + \"Maria\"\n" +
                        "Αριθμητικοί τελεστές:\n" +
                        "\n" +
                        "+ Πρόσθεση\n" +




                        "\n" +
                        "- Αφαίρεση\n" +
                        "\n" +
                        "* Πολλαπλασιασμός\n" +
                        "\n" +
                        "/ Διαίρεση\n" +
                        "\n" +
                        "// Διαίρεση ακέραιων (διαιρέτης)\n" +
                        "\n" +
                        "% Μόλυβδος (υπόλοιπο διαίρεσης)\n" +
                        "\n" +




                        "** Εκθετική συνάρτηση (δύναμη)\n" +
                        "\n" +
                        "Συντομογραφικοί τελεστές ανάθεσης:\n" +
                        "\n" +
                        "x += a → Προσθέστε το a στο x (x = x + a)\n" +
                        "\n" +
                        "x -= a → Αφαιρέστε το a από το x (x = x - a)\n" +




                        "\n" +
                        "x *= a → Πολλαπλασιάζει το x με το a (x = x * a)\n" +
                        "\n" +
                        "x /= a → Διαιρεί το x με το a (x = x / a)\n" +
                        "\n" +
                        "x //= a → Διαιρεί το x με το a (x = x // a)\n" +
                        "\n" +




                        "x %= a → Υπόλοιπο του x διαιρούμενο με το a (x = x % a)\n" +
                        "\n" +
                        "x **= a → Ανύψωση του x στην δύναμη του a (x = x ** a)\n" +
                        "\n" +
                        "Άσκηση 1.2\n" +
                        "Μετατροπή θερμοκρασίας από βαθμούς Φαρενάιτ σε βαθμούς Κελσίου.\n" +




                        "\n" +
                        "Άσκηση 1.3\n" +
                        "Υπολογίστε τον ετήσιο φόρο για κάποιον που κερδίζει μηνιαίο μισθό 800 ευρώ,\n" +
                        "με φορολογικό συντελεστή 50%."+
                        "Μεταβλητές - τύποι - αριθμοί\n\n" +
                        "Εγκατάσταση και χρήση του IDLE\n" +
                        "\n" +




                        "Σε αυτό το μάθημα, θα χρησιμοποιήσουμε το **IDLE** για να αλληλεπιδράσουμε με την Python.\n" +
                        "\n" +
                        "Θα πρέπει να αναζητήσετε το **IDLE** στον υπολογιστή σας, για παράδειγμα, χρησιμοποιώντας το πεδίο αναζήτησης του λειτουργικού σας συστήματος. Όταν πληκτρολογήσετε **IDLE** και το επιλέξετε, θα ανοίξει το παράθυρο **IDLE Shell**.\n" +




                        "\n" +
                        "Καλό είναι να **καρφιτσώσετε την εφαρμογή IDLE στη γραμμή εργασιών**, ώστε να μπορείτε να τη βρείτε πιο εύκολα στο μέλλον.\n" +
                        "\n" +
                        "Το περιεχόμενο του παραθύρου IDLE είναι παρόμοιο με το περιβάλλον του διερμηνέα Python που είδαμε στη γραμμή εντολών. Μπορείτε να πληκτρολογήσετε εντολές απευθείας σε αυτό.\n" +




                        "\n" +
                        "Το περιβάλλον IDLE περιλαμβάνει επίσης πολλές άλλες χρήσιμες επιλογές:\n" +
                        "\n" +
                        "Αρχείο, Επεξεργασία, Shell, Επιλογές, Παράθυρο, Βοήθεια.\n" +
                        "\n" +
                        "Μια επιλογή που σας επιτρέπει να προσαρμόσετε την εμφάνιση του παραθύρου είναι:\n" +
                        "Επιλογές → Διαμόρφωση IDLE\n" +
                        "\n" +




                        "Εδώ, στην καρτέλα **Highlights**, μπορείτε να επιλέξετε το θέμα **IDLE Dark**, το οποίο χρησιμοποιείται στις διαλέξεις αυτού του μαθήματος.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "Τύποι δεδομένων\n" +
                        "\n" +
                        "Στο IDLE, μπορώ να αντιστοιχίσω μια τιμή σε μια μεταβλητή:\n" +




                        "\n" +
                        "```python\n" +
                        ">>> a = 5\n" +
                        "```\n" +
                        "\n" +
                        "Η μεταβλητή `a` αντιπροσωπεύει μια θέση μνήμης στον υπολογιστή όπου αποθηκεύεται η τιμή `5`.\n" +
                        "\n" +




                        "Η μεταβλητή `a` είναι τύπου **`int`** (ακέραιος). Μπορείτε να το επιβεβαιώσετε χρησιμοποιώντας τη συνάρτηση `type()`:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> type(a)\n" +
                        "<class “int”>\n" +
                        "```\n" +
                        "\n" +




                        "Άλλοι τύποι δεδομένων που μπορούν να αποθηκευτούν σε μεταβλητές περιλαμβάνουν:\n" +
                        "\n" +
                        "* `float` για δεκαδικούς αριθμούς\n" +
                        "* `str` για ακολουθίες χαρακτήρων, γνωστές ως **strings**\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "```python\n" +




                        ">>> name = \"Nikos\"\n" +
                        ">>> type(name)\n" +
                        "<class “str”>\n" +
                        "```\n" +
                        "\n" +
                        "Εδώ βλέπουμε ότι η μεταβλητή `name` είναι τύπου `“str”` (string).\n" +
                        "\n" +




                        "Ένας άλλος τρόπος για να εκφράσουμε δεκαδικούς αριθμούς είναι μέσω της **επιστημονικής σημειογραφίας**. Αυτοί είναι αριθμοί γραμμένοι με τη μορφή `a × 10^b`. Η επιστημονική σημειογραφία είναι χρήσιμη για την αναπαράσταση πολύ μικρών ή πολύ μεγάλων αριθμών.\n" +
                        "\n" +




                        "Για παράδειγμα, ο δεκαδικός αριθμός `0.0000000123` μπορεί να γραφτεί σε επιστημονική σημειογραφία ως `1.23 × 10^-8`. Στην Python, αυτό μπορεί να γραφτεί ως:\n" +
                        "\n" +
                        "```python\n" +
                        "1.23E-8\n" +
                        "```\n" +




                        "\n" +
                        "Το σύμβολο `E` σημαίνει εκθέτης και μπορεί να γραφτεί είτε ως `e` είτε ως `E`.\n" +
                        "\n" +
                        "Όπως θα δούμε, η Python περιλαμβάνει πολλούς περισσότερους απλούς και σύνθετους τύπους δεδομένων.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +




                        "### **Τύποι μεταβλητών στην Python**\n" +
                        "\n" +
                        "Είναι σημαντικό να σημειωθεί ότι **δεν χρειάζεται να δηλώσετε τον τύπο μιας μεταβλητής** στην Python, όπως απαιτείται σε άλλες γλώσσες προγραμματισμού. Ο τύπος μπορεί επίσης να αλλάξει αργότερα ανάλογα με τα δεδομένα στα οποία αναφέρεται.\n" +
                        "\n" +
                        "---\n" +
                        "\n" +




                        "Ονόματα μεταβλητών\n" +
                        "\n" +
                        "Ένα όνομα μεταβλητής μπορεί να περιλαμβάνει μια ακολουθία από:\n" +
                        "\n" +
                        "* Γράμματα\n" +
                        "* Αριθμούς\n" +
                        "* Υπογραμμιστικά (`_`)\n" +
                        "\n" +




                        "Σύμβολα όπως `$``%`, κ.λπ., τα οποία επιτρέπονται σε άλλες γλώσσες προγραμματισμού, **δεν επιτρέπονται** στα ονόματα μεταβλητών της Python.\n" +
                        "\n" +
                        "Ένας άλλος περιορισμός: **ο πρώτος χαρακτήρας του ονόματος της μεταβλητής πρέπει να είναι ένα γράμμα ή μια υπογράμμιση**, όχι ένας αριθμός.\n" +
                        "Επομένως, οι ακόλουθες εντολές είναι άκυρες λόγω παράνομων ονομάτων μεταβλητών:\n" +




                        "\n" +
                        "```python\n" +
                        "123a = 10\n" +
                        "\$123 = 100\n" +
                        "```\n" +
                        "\n" +
                        "Τα ονόματα των μεταβλητών πρέπει επίσης να **περιγράφουν** το περιεχόμενό τους. Για παράδειγμα, μια τυπική μεταβλητή Python θα μπορούσε να είναι:\n" +




                        "\n" +
                        "```python\n" +
                        "max_velocity = 150\n" +
                        "```\n" +
                        "\n" +
                        "Είναι συνήθης πρακτική να ακολουθείται η σύμβαση ονοματοδοσίας **snake\\_case** στην Python, όπου οι λέξεις στο όνομα της μεταβλητής διαχωρίζονται με κάτω παύλες.\n",




                "Ανάθεση τιμής σε μια μεταβλητή\n\nΌπως έχουμε ήδη δει, μπορούμε να αναθέσουμε μια τιμή σε μια μεταβλητή:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> a = 5\n" +
                        "```\n" +
                        "\n" +




                        "Μπορούμε στη συνέχεια να χρησιμοποιήσουμε τη μεταβλητή `a` σε μια έκφραση που περιλαμβάνει άλλες μεταβλητές, τιμές και **τελεστές** (σύμβολα για πράξεις).\n" +
                        "\n" +
                        "Οι πιο σημαντικοί τελεστές που χρησιμοποιούνται στις εκφράσεις είναι:\n" +
                        "\n" +
                        "* `+` Πρόσθεση\n" +
                        "* `-` Αφαίρεση\n" +




                        "* `*` Πολλαπλασιασμός\n" +
                        "* `/` Διαίρεση\n" +
                        "* `//` Ακέραιος διαίρεση (πλάτος)\n" +
                        "* `%` Μόδουλ (υπόλοιπο διαίρεσης)\n" +
                        "* `**` Εκθετική συνάρτηση (δύναμη)\n" +
                        "\n" +




                        "Συνήθως αποδίδουμε το **αποτέλεσμα μιας έκφρασης** σε μια μεταβλητή.\n" +
                        "\n" +
                        "Η **δήλωση ανάθεσης** γράφεται ως:\n" +
                        "\n" +
                        "```python\n" +
                        "μεταβλητή = έκφραση\n" +
                        "```\n" +
                        "\n" +




                        "**Δεν επιτρέπεται** η χρήση μιας μεταβλητής σε μια έκφραση **πριν της αποδοθεί μια τιμή**.\n" +
                        "\n" +
                        "Για παράδειγμα, αν η μεταβλητή `c` δεν έχει ακόμη λάβει μια τιμή και προσπαθούμε να υπολογίσουμε την έκφραση:\n" +
                        "\n" +




                        "python\n" +
                        ">>> c + 5\n" +
                        "\n" +
                        "\n" +
                        "Θα λάβουμε ένα **μήνυμα σφάλματος** από τον διερμηνέα (συνήθως εμφανίζεται με κόκκινο χρώμα), όπως:\n" +
                        "\n" +
                        "\n" +
                        "Traceback (most recent call last):\n" +




                        "  Αρχείο \"<pyshell#13>\", γραμμή 1, στο <module>\n" +
                        "    c + 5\n" +
                        "NameError: το όνομα “c” δεν έχει οριστεί\n" +
                        "```\n" +
                        "\n" +




                        "Από την άλλη πλευρά, εάν η μεταβλητή έχει ήδη μια τιμή, τότε **αντικαθίσταται από την τιμή της** στην έκφραση και υπολογίζεται το αποτέλεσμα.\n" +
                        "\n" +
                        "**Παράδειγμα**: η ακόλουθη έκφραση, για τη μεταβλητή `a` που χρησιμοποιήσαμε νωρίτερα, είναι:\n" +
                        "\n" +
                        "```python\n" +




                        ">>> a ** 2\n" +
                        "25\n" +
                        "```\n",
                "Strings\n\nA string is a sequence of characters enclosed in single quotes “, double quotes \", or triple single quotes ”'' and triple double quotes \"\"\".\n" +
                        "\n" +
                        "python\n" +
                        "Copy\n" +




                        "Επεξεργασία\n" +
                        ">>> name1 = \"Agamemnon\"\n" +
                        ">>> name2 = “Clytemnestra”\n" +
                        ">>> story = “”'Clytemnestra was the daughter of Tyndareus\n" +
                        "and Leda, sister of beautiful Helen and the Dioscuri,\n" +




                        "και σύζυγος του Αγαμέμνονα.“”'\n" +
                        "Τα απλά και τα διπλά εισαγωγικά είναι απολύτως ισοδύναμα. Τα τριπλά απλά εισαγωγικά “”' ή τα τριπλά διπλά εισαγωγικά \"\"\" χρησιμοποιούνται για να περικλείουν κείμενο πολλαπλών γραμμών, κάτι που δεν επιτρέπεται με τους άλλους τύπους εισαγωγικών.\n" +
                        "\n" +




                        "Μια συμβολοσειρά μπορεί να περιέχει χαρακτήρες Unicode καθώς και ειδικές ακολουθίες διαφυγής, που εισάγονται με μια κάθετο \\, όπως η ίδια η κάθετος, εισαγωγικά ή μη εκτυπώσιμους χαρακτήρες ελέγχου.\n" +
                        "\n" +
                        "Παραδείγματα ακολουθιών διαφυγής:\n" +
                        "\n" +
                        "\\\\ κάθετος\n" +
                        "\n" +




                        "\\' απλό εισαγωγικό\n" +
                        "\n" +
                        "\\\" διπλό εισαγωγικό\n" +
                        "\n" +
                        "\\n νέα γραμμή\n" +
                        "\n" +
                        "\\t οριζόντια ταμπ\n" +
                        "\n" +
                        "\\v κάθετη ταμπ\n" +
                        "\n" +




                        "Δείκτες και τεμαχισμός συμβολοσειρών\n" +
                        "Μπορείτε να δείξετε στοιχεία μιας συμβολοσειράς s ως εξής:\n" +
                        "s[indx], όπου indx είναι ένας ακέραιος αριθμός από 0 έως n-1 (όπου n είναι το μήκος της συμβολοσειράς).\n" +
                        "Η Python χρησιμοποιεί δείκτες με βάση το μηδέν, οπότε ο πρώτος χαρακτήρας βρίσκεται στον δείκτη 0.\n" +
                        "\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> name = \"Clytemnestra\"\n" +
                        ">>> name[1]\n" +
                        "“l”\n" +




                        "Ένα υποστρώμα μιας συμβολοσειράς ορίζεται χρησιμοποιώντας το slicing: s[a:b], όπου a είναι ο δείκτης έναρξης και b είναι ο δείκτης λήξης (δεν περιλαμβάνεται ο τελικός χαρακτήρας).\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> name[3:8]\n" +
                        "“temne”\n" +
                        "Μπορείτε επίσης να χρησιμοποιήσετε αρνητική ευρετηρίαση: -1 αναφέρεται στον τελευταίο χαρακτήρα, -2 στον προτελευταίο και ούτω καθεξής.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        ">>> name[-5:]\n" +
                        "“estra”\n" +
                        "Κωδικοποίηση χαρακτήρων\n" +
                        "Η συνάρτηση ord(character) επιστρέφει τον κωδικό Unicode ενός χαρακτήρα.\n" +
                        "\n" +
                        "Η συνάρτηση chr(integer) επιστρέφει τον χαρακτήρα που αντιστοιχεί σε έναν δεδομένο κωδικό Unicode.\n" +




                        "\n" +
                        "Παραδείγματα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> ord(\"\\n\")\n" +
                        "10\n" +
                        ">>> ord(\" \")\n" +
                        "32\n" +




                        "Επίσης:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> chr(940)\n" +
                        "“ά”",
                "Λειτουργίες συμβολοσειρών – Μέθοδοι συμβολοσειρών\n\nΣυνένωση συμβολοσειρών**\n" +
                        "\n" +




                        "Οι συμβολοσειρές μπορούν να **συγκρατηθούν (να προστεθούν μεταξύ τους)** χρησιμοποιώντας τον τελεστή `+`.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> hello = \"good \" + \"morning\"\n" +
                        ">>> hello\n" +




                        "“good morning”\n" +
                        "```\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "#### **Επανάληψη συμβολοσειράς**\n" +
                        "\n" +
                        "Μπορείτε να **πολλαπλασιάσετε μια συμβολοσειρά με έναν ακέραιο αριθμό** για να την επαναλάβετε πολλές φορές.\n" +
                        "\n" +




                        "python\n" +
                        ">>> 7 * \"Θήβα\"\n" +
                        "“ΘήβαΘήβαΘήβαΘήβαΘήβαΘήβα”\n" +
                        "\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "#### **Μήκος συμβολοσειράς**\n" +
                        "\n" +




                        "Η συνάρτηση `len(s)` επιστρέφει τον **αριθμό των χαρακτήρων** στη συμβολοσειρά `s`.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "```python\n" +
                        ">>> name = \"Clytemnestra\"\n" +
                        ">>> len(name)\n" +
                        "13\n" +




                        "```\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "#### **Μέθοδοι συμβολοσειράς**\n" +
                        "\n" +
                        "Οι συμβολοσειρές στην Python είναι αντικείμενα και διαθέτουν **ενσωματωμένες μεθόδους**, στις οποίες μπορείτε να έχετε πρόσβαση χρησιμοποιώντας τη σημείωση τελείας, όπως `s.method()`:\n" +
                        "\n" +




                        "* `s.replace(old, new [, max])` → αντικαθιστά τις εμφανίσεις του `old` με το `new`\n" +
                        "* `s.count(str)` → μετράει πόσες φορές εμφανίζεται το `str` στο `s`\n" +
                        "* `s.isalpha()` → `True` αν το `s` περιέχει μόνο γράμματα\n" +




                        "* `s.isdigit()` → `True` αν το `s` περιέχει μόνο ψηφία\n" +
                        "* `s.islower()` → `True` αν το `s` περιέχει μόνο πεζά γράμματα\n" +
                        "* `s.upper()` → μετατρέπει όλα τα χαρακτήρες σε κεφαλαία\n" +
                        "* `s.lower()` → μετατρέπει όλα τα χαρακτήρες σε πεζά\n" +




                        "* `s.capitalize()` → μετατρέπει το πρώτο χαρακτήρα σε κεφαλαίο\n" +
                        "* `s.find(str)` → επιστρέφει τον δείκτη του `str`, ή `-1` αν δεν βρεθεί\n" +
                        "* `s.join(seq)` → ενώνει τα στοιχεία του `seq` χρησιμοποιώντας το `s` ως διαχωριστικό\n" +




                        "* `s.split(delimiter)` → χωρίζει το `s` σε μια λίστα χρησιμοποιώντας το `delimiter`\n" +
                        "* `s.strip([chars])` → αφαιρεί χαρακτήρες και από τις δύο άκρες του `s` (προεπιλογή είναι τα κενά)\n" +




                        "* `s.decode(encoding=“UTF-8”)` → μετατρέπει μια συμβολοσειρά byte σε μια κανονική συμβολοσειρά (χρησιμοποιείται σε παλαιότερες εκδόσεις Python)\n" +
                        "* `s.encode(encoding=“UTF-8”)` → μετατρέπει μια συμβολοσειρά σε μια συμβολοσειρά byte\n" +




                        "* `s.format(param)` → εισάγει το `param` στο `s` χρησιμοποιώντας τους χαρακτήρες-placeholders `{}`\n" +
                        "\n" +
                        "---\n" +
                        "\n" +
                        "### **Μάθημα 2: Σειρές**\n" +
                        "\n" +
                        "#### **Άσκηση 2.1**\n" +




                        "\n" +
                        "Γράψτε με κεφαλαίο το πρώτο γράμμα του ονόματος:\n" +
                        "\n" +
                        "python\n" +
                        "name = \"katerina\"\n" +
                        "name = name[0].upper() + name[1:]\n" +
                        "print(name)\n" +




                        "\n" +
                        "#### **Άσκηση 2.2**\n" +
                        "\n" +
                        "Εξαγάγετε και συνδυάστε μέρη μιας φράσης:\n" +
                        "\n" +
                        "python\n" +
                        "stixos = \"I stay alone in my present\"\n" +




                        "n = stixos[:5] + stixos[-9:-4]\n" +
                        "print(n)\n" +
                        "```\n" +
                        "\n" +
                        "#### **Άσκηση 2.3**\n" +
                        "\n" +
                        "Εμφάνιση μορφοποιημένου μηνύματος:\n" +
                        "\n" +




                        "python\n" +
                        "st = 24 * \"=\" + \"\\n\" + 4 * \"  \" + \"Έναρξη προγράμματος\\n\" + 24 * \"=\"\n" +
                        "print(st)\n" +
                        "\n",
                "Η συνάρτηση print()\n\n" +




                        "Η συνάρτηση print() εμφανίζει μια συμβολοσειρά στο παράθυρο εξόδου του προγράμματος.\n" +
                        "\n" +
                        "Σύνταξη:\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "print(arguments, sep=“s”, end=“e”)\n" +
                        "Πού:\n" +
                        "\n" +




                        "arguments: Μια λίστα με μηδέν ή περισσότερες εκφράσεις προς εκτύπωση.\n" +
                        "\n" +
                        "sep=“s”: Ο διαχωριστής μεταξύ των ορίσματος. Η προεπιλογή είναι ένα κενό διάστημα \" \".\n" +
                        "\n" +
                        "end=“e”: Ο χαρακτήρας τερματισμού της εξόδου. Η προεπιλογή είναι ο χαρακτήρας νέας γραμμής \\n.\n" +
                        "\n" +




                        "Όλα τα ορίσματα της print() μετατρέπονται σε συμβολοσειρές χρησιμοποιώντας τη συνάρτηση str().\n" +
                        "\n" +
                        "Σημείωση: Μια σημαντική διαφορά μεταξύ της Python 2 και της Python 3 είναι ότι η print ήταν μια δήλωση στην Python 2, αλλά είναι μια συνάρτηση στην Python 3.\n" +
                        "\n" +
                        "Παράδειγμα\n" +




                        "Ας υποθέσουμε ότι θέλουμε να εμφανίσουμε τις λέξεις μιας γραμμής από το ποίημα του Κ.Π. Καβάφη Ο Θεός εγκαταλείπει τον Αντώνιο χωρισμένες με παύλες:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> print(\"say goodbye\", \"to\", \"to\", \"Alexandria\", \"that's\", \"leaving\", sep=\"-\")\n" +
                        "say goodbye-to-to-Alexandria-that's-leaving\n" +
                        "Τώρα φανταστείτε ότι αλλάζετε το διαχωριστικό σε:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "sep=3*\"*\"\n" +
                        "Αυτό θα εισάγει *** μεταξύ των λέξεων.\n" +
                        "\n" +
                        "Έλεγχος αλλαγής γραμμής με end\n" +




                        "Από προεπιλογή, δύο κλήσεις print() εκτυπώνονται σε ξεχωριστές γραμμές:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> print(“one”); print(“two”)\n" +
                        "one\n" +
                        "two\n" +




                        "Αλλά με το end, μπορούμε να εκτυπώσουμε στην ίδια γραμμή:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> print(“one”, end=\" \"); print(“two”)\n" +
                        "one two\n" +




                        "Μπορείτε να έχετε πολλαπλές δηλώσεις Python σε μία γραμμή αν διαχωρίζονται με ερωτηματικό (;).\n" +
                        "\n" +
                        "Μορφοποίηση συμβολοσειρών στο print()\n" +
                        "Υπάρχουν δύο βασικοί τρόποι μορφοποίησης συμβολοσειρών:\n" +
                        "\n" +
                        "Χρησιμοποιώντας τον τελεστή % (κοινός σε άλλες γλώσσες προγραμματισμού)\n" +
                        "\n" +




                        "Χρησιμοποιώντας τη μέθοδο .format() (προτιμώμενη στην Python)\n" +
                        "\n" +
                        "Παραδείγματα:\n" +
                        "Χρησιμοποιώντας %:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> “%s %s” % (“one”, “two”)\n" +




                        "“one two”\n" +
                        "Χρήση .format():\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> “{} {}”.format(“one”, “two”)\n" +
                        "“one two”\n" +
                        "Περισσότερα παραδείγματα:\n" +




                        "Ας εκτυπώσουμε μια μεταβλητή x με μια εξήγηση:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> x = 123.456\n" +
                        ">>> print(\"Η τιμή του x είναι\", x)\n" +




                        "Η τιμή του x είναι 123.456\n" +
                        "Για να περιορίσετε τον αριθμό των δεκαδικών ψηφίων:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> print(\"Η τιμή του x είναι %1.1f\" % x)\n" +




                        "Η τιμή του x είναι 123.5\n" +
                        "Ή με .format():\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> print(\"Η τιμή του x είναι {:1.1f}\".format(x))\n" +




                        "Η τιμή του x είναι 123.5\n" +
                        "Η μέθοδος .format() είναι πιο ευέλικτη, επιτρέποντας παραμετροποιημένη μορφοποίηση όπως \"x={x:5s}\".\n" +
                        "\n" +
                        "f = float\n" +
                        "\n" +
                        "d = integer\n" +
                        "\n" +




                        "s = string\n" +
                        "\n" +
                        "1.1f σημαίνει τουλάχιστον ένα ψηφίο πριν από το δεκαδικό και ένα μετά.\n" +
                        "\n" +
                        "Ευθυγράμμιση συμβολοσειράς με .format()\n" +
                        "Μπορείτε να ελέγξετε τον τρόπο ευθυγράμμισης των συμβολοσειρών μέσα σε ένα κενό:\n" +
                        "\n" +




                        "Ευθυγράμμιση αριστερά σε χώρο 5 χαρακτήρων:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> print(\"---{:5s}---\".format(\"abc\"))\n" +
                        "---abc  ---\n" +




                        "Ευθυγράμμιση δεξιά σε χώρο 5 χαρακτήρων:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> print(\"---{:>5s}---\".format(\"abc\"))\n" +
                        "---  abc---\n" +




                        "Μάθημα 3: Εισαγωγή/Εξαγωγή\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "x = 123.456\n" +
                        "print(\"Η τιμή του x είναι:\", x)\n" +
                        "print(\"Η τιμή του x είναι %f\" % x)\n" +




                        "print(\"Η τιμή του x είναι %1.2f\" % x)\n" +
                        "print(\"Η τιμή του x είναι {:1.2f}\".format(x))",
                "Η συνάρτηση input()\n\nΗ συνάρτηση input() λαμβάνει την εισαγωγή του χρήστη ως συμβολοσειρά, ανεξάρτητα από το αν ο χρήστης πληκτρολογεί χαρακτήρες ή αριθμούς.\n" +
                        "\n" +




                        "Έτσι, αν θέλουμε ο χρήστης να εισαγάγει έναν αριθμό, πρέπει να μετατρέψουμε την είσοδο χρησιμοποιώντας τη συνάρτηση int() για έναν ακέραιο αριθμό ή float() για έναν δεκαδικό αριθμό.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> y = input(\"Εισάγετε μια τιμή:\")\n" +




                        "Εισάγετε μια τιμή:10\n" +
                        ">>> type(y)\n" +
                        "<class “str”>\n" +
                        "Για να μετατρέψετε το y σε ακέραιο:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "y = int(y)\n" +




                        "⚠\uFE0F Σημαντικό: Εάν ο χρήστης δεν εισαγάγει έναν έγκυρο αριθμό, αυτή η γραμμή θα προκαλέσει ένα ValueError.\n" +
                        "\n" +
                        "Σε μια επόμενη ενότητα, θα μάθουμε πώς να προστατεύουμε τον κώδικά μας από μη έγκυρες εισόδους χρησιμοποιώντας τεχνικές αμυντικής προγραμματισμού.\n" +
                        "\n" +
                        "Μάθημα 3: Είσοδος/Έξοδος\n" +




                        "Χρήση εισόδου\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "x = input(\"Εισάγετε τον 1ο αριθμό:\")\n" +
                        "y = input(\"Εισάγετε τον 2ο αριθμό:\")\n" +
                        "x = int(x)\n" +
                        "y = int(y)\n" +




                        "s = x + y\n" +
                        "m = x * y\n" +
                        "print(\"\\nΆθροισμα: {}\\nΠρόϊο: {}\".format(s, m))\n" +
                        "Μετατροπή θερμοκρασίας από Κελσίου σε Φαρενάιτ\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "C = input(\"Εισάγετε τη θερμοκρασία σε Κελσίου:\")\n" +
                        "C = float(C)\n" +
                        "F = C * 1.8 + 32\n" +
                        "print(\"Η θερμοκρασία σε Φαρενάιτ είναι {:1.2f}\".format(F))",




                "Λίστες στην Python\n\nΜια λίστα είναι μια σύνθετη δομή δεδομένων στην Python. Είναι ένα αντικείμενο που περιέχει μια ακολουθία στοιχείων δεδομένων, τα οποία μπορεί να είναι διαφορετικών τύπων.\n" +
                        "\n" +
                        "Μια λίστα ορίζεται ως μια ακολουθία στοιχείων, διαχωρισμένων με κόμματα, που περικλείονται σε αγκύλες:\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_list = [\"George\", 3.14, 100]\n" +
                        ">>> type(a_list)\n" +
                        "<class “list”>\n" +
                        "Σε αυτό το παράδειγμα, η λίστα a_list περιέχει 3 στοιχεία:\n" +
                        "\n" +




                        "το πρώτο είναι μια συμβολοσειρά,\n" +
                        "\n" +
                        "το δεύτερο είναι ένας αριθμός κινητής υποδιαστολής,\n" +
                        "\n" +
                        "και το τρίτο είναι ένας ακέραιος αριθμός.\n" +
                        "\n" +
                        "⚠\uFE0F Μια λίστα είναι μεταβλητή, που σημαίνει ότι μπορείτε να αλλάξετε την τιμή των στοιχείων της ή να προσθέσετε/αφαιρέσετε στοιχεία. Τώρα θα δούμε πώς μπορούμε να τροποποιήσουμε τα στοιχεία μιας λίστας.\n" +
                        "\n" +
                        "Πρόσβαση στα στοιχεία της λίστας\n" +
                        "Μπορείτε να αποκτήσετε πρόσβαση στα στοιχεία της λίστας χρησιμοποιώντας δείκτες, ξεκινώντας από το 0, όπως και με τις συμβολοσειρές:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> a_list[0]\n" +
                        "“George”\n" +
                        ">>> a_list[2]\n" +
                        "100\n" +
                        "Τελεστές και συναρτήσεις για λίστες\n" +
                        "Ο τελεστής + συνενώνει δύο λίστες.\n" +
                        "\n" +
                        "Ο τελεστής * επαναλαμβάνει τη λίστα n φορές.\n" +




                        "\n" +
                        "Μπορείτε επίσης να χρησιμοποιήσετε την κοπή όπως με τις συμβολοσειρές, π.χ. a[n:m] επιστρέφει την υπολίστα από τον δείκτη n έως m - 1.\n" +
                        "\n" +
                        "Η συνάρτηση len() επιστρέφει τον αριθμό των στοιχείων σε μια λίστα.\n" +
                        "\n" +
                        "Ενσωματωμένα στοιχεία\n" +




                        "Ένα στοιχείο μιας λίστας μπορεί επίσης να είναι μια ακολουθία (π.χ. μια άλλη λίστα ή μια συμβολοσειρά):\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_list = [\"George\", 3.14, 100]\n" +




                        ">>> a_list[0][2]\n" +
                        "“o”\n" +
                        "Δισδιάστατες λίστες (λίστες λιστών)\n" +
                        "Ένας δισδιάστατος πίνακας μπορεί να αναπαρασταθεί ως μια λίστα λιστών:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> matrix = [[2, 6, 4, 7], [1, 0, 3, 2]]\n" +
                        ">>> matrix[1][-2:]\n" +
                        "[3, 2]\n" +
                        "Εδώ, το matrix[1] μας δίνει τη δεύτερη σειρά και το [-2:] επιστρέφει τα δύο τελευταία στοιχεία αυτής της σειράς.\n" +




                        "\n" +
                        "Μέθοδοι λίστας\n" +
                        "Οι λίστες Python έχουν πολλές χρήσιμες ενσωματωμένες μεθόδους:\n" +
                        "\n" +
                        "append(x): Προσθέτει το x στο τέλος της λίστας.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> a_list.append(123)\n" +
                        "[“George”, 3.14, 100, 123]\n" +
                        "extend(L): Επεκτείνει τη λίστα προσθέτοντας στοιχεία από τη λίστα L.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> a_list.extend([\"Kostas\", 55])\n" +
                        "[“George”, 3.14, 100, 123, “Kostas”, 55]\n" +
                        "insert(i, x): Εισάγει το x στη θέση i.\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_list.insert(1, 200)\n" +
                        "[“George”, 200, 3.14, 100, 123, “Kostas”, 55]\n" +




                        "remove(x): Αφαιρεί την πρώτη εμφάνιση του x. Εμφανίζει ένα σφάλμα αν το x δεν βρεθεί.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_list.remove(\"Katerina\")\n" +




                        "ValueError: list.remove(x): x δεν υπάρχει στη λίστα\n" +
                        "pop([i]): Αφαιρεί και επιστρέφει το στοιχείο στον δείκτη i. Εάν δεν δοθεί i, αφαιρεί το τελευταίο στοιχείο.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_list.pop(0)\n" +




                        "“George”\n" +
                        ">>> a_list\n" +
                        "[200, 3.14, 100, 123, “Kostas”, 55]\n" +
                        "index(x): Επιστρέφει τον δείκτη της πρώτης εμφάνισης του x.\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_list.index(100)\n" +
                        "2\n" +
                        "count(x): Επιστρέφει τον αριθμό των εμφανίσεων του x.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> b_list = [1, 1, 2, 3, 4, 5, 6]\n" +
                        ">>> b_list.count(1)\n" +
                        "2\n" +
                        ">>> b_list.count(8)\n" +
                        "0\n" +




                        "sort(): Ταξινομεί τη λίστα στη θέση της (από προεπιλογή σε αύξουσα σειρά). Λειτουργεί μόνο για συγκρίσιμους τύπους δεδομένων.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> b_list = [8, 5, 10]\n" +
                        ">>> b_list.sort()\n" +




                        ">>> b_list\n" +
                        "[5, 8, 10]\n" +
                        "\n" +
                        ">>> names = [\"Maria\", \"Katerina\", \"Andreas\"]\n" +
                        ">>> names.sort()\n" +
                        ">>> names\n" +




                        "[“Andreas”, “Katerina”, “Maria”]\n" +
                        "reverse(): Αντιστρέφει τη σειρά των στοιχείων.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> names.reverse()\n" +
                        ">>> names\n" +




                        "[“Maria”, “Katerina”, “Andreas”]",
                "Τύποι δεδομένων στην Python\n\nΟι τύποι δεδομένων είναι ακολουθίες στοιχείων, όπως οι λίστες.\n" +
                        "\n" +
                        "Ένας τύπος δεδομένων ορίζεται ως μια ακολουθία στοιχείων που χωρίζονται με κόμματα και περικλείονται σε παρενθέσεις.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_tuple = (\"George\", 3.14, 100)\n" +
                        ">>> a_tuple[0]\n" +
                        "“George”\n" +
                        ">>> len(a_tuple)\n" +




                        "3\n" +
                        "Οι τούπλες έχουν μια σημαντική διαφορά από τις λίστες:\n" +
                        "\n" +
                        "Οι τούπλες είναι αμετάβλητες — δεν μπορούν να αλλάξουν μετά τη δημιουργία τους.\n" +
                        "\n" +
                        "Για παράδειγμα, η ακόλουθη δήλωση:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        "a_tuple[1] = 555\n" +
                        "θα προκαλέσει σφάλμα:\n" +
                        "\n" +
                        "απλό κείμενο\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "TypeError: Το αντικείμενο “tuple” δεν υποστηρίζει την εκχώρηση στοιχείων\n" +
                        "Συχνό λάθος\n" +




                        "Ένα συνηθισμένο λάθος που κάνουν οι νέοι προγραμματιστές — ειδικά σε χώρες όπως η Ελλάδα, όπου η κόμμα χρησιμοποιείται ως δεκαδικός διαχωριστής — είναι το εξής:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> pi = 3,14\n" +
                        ">>> type(pi)\n" +




                        "<class “tuple”>\n" +
                        ">>> pi\n" +
                        "(3, 14)\n" +
                        "Αυτός ο κώδικας δεν παράγει σφάλμα, αλλά ο προγραμματιστής ήθελε να εκχωρήσει την τιμή 3,14 στη μεταβλητή pi. Αντ' αυτού, δημιουργήθηκε μια τούπλα με δύο στοιχεία: (3, 14).\n" +
                        "\n" +




                        "Αυτό συμβαίνει επειδή οι κόμμες δημιουργούν τούπλες και, σε αυτήν την περίπτωση, οι παρενθέσεις είναι προαιρετικές κατά τον ορισμό μιας τούπλης.",
                "Λεξικά στην Python\n\nΤα λεξικά είναι αντικείμενα που αποθηκεύουν δεδομένα ως ζεύγη κλειδιού-τιμής, διαχωρισμένα με κόμμες. Τα λεξικά περικλείονται σε αγκύλες {}.\n" +
                        "\n" +
                        "Παράδειγμα λεξικού:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> my_contacts = {\"Maria\": 6977, \"Nikos\": 6988}\n" +
                        ">>> len(my_contacts)\n" +
                        "2\n" +




                        "Τα κλειδιά σε ένα λεξικό πρέπει να είναι μοναδικά.\n" +
                        "\n" +
                        "Οι έγκυροι τύποι για κλειδιά περιλαμβάνουν αμετάβλητους τύπους δεδομένων όπως:\n" +
                        "\n" +
                        "Συμβολοσειρές\n" +
                        "\n" +
                        "Αριθμοί\n" +
                        "\n" +
                        "Booleans\n" +
                        "\n" +




                        "Τuple\n" +
                        "\n" +
                        "⚠\uFE0F Δεν μπορείτε να χρησιμοποιήσετε λίστες ή άλλα λεξικά ως κλειδιά.\n" +
                        "\n" +
                        "Η πρόσβαση σε στοιχεία σε ένα λεξικό είναι παρόμοια με τις λίστες που χρησιμοποιούν αγκύλες [ ].\n" +
                        "Ωστόσο, τα κλειδιά του λεξικού δεν είναι ακέραιοι δείκτες, επομένως η θέση τους δεν είναι σταθερή ή ταξινομημένη.\n" +




                        "\n" +
                        "Προσθήκη ή ενημέρωση στοιχείων\n" +
                        "Μπορείτε να προσθέσετε ή να ενημερώσετε ένα στοιχείο χρησιμοποιώντας:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "λεξικό[κλειδί] = τιμή\n" +




                        "Εάν το κλειδί δεν υπάρχει, δημιουργείται μια νέα καταχώριση.\n" +
                        "\n" +
                        "Εάν το κλειδί υπάρχει ήδη, η τιμή του ενημερώνεται.\n" +
                        "\n" +
                        "Κατάργηση στοιχείων\n" +
                        "Για να καταργήσετε μια καταχώριση:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        "del dictionary[κλειδί]\n" +
                        "Μέθοδοι λεξικού\n" +
                        "Χρήσιμες μέθοδοι για τη διαχείριση λεξικών:\n" +
                        "\n" +
                        "d.keys() → επιστρέφει μια ακολουθία των κλειδιών του λεξικού\n" +
                        "\n" +
                        "d.values() → επιστρέφει μια ακολουθία των τιμών του λεξικού\n" +




                        "\n" +
                        "d.items() → επιστρέφει μια ακολουθία από ζεύγη (κλειδί, τιμή)\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> my_contacts.items()\n" +




                        "dict_items([(“Maria”, 6977777777), (“Nikos”, 6988888888)])\n" +
                        "Αυτές οι μέθοδοι επιστρέφουν ειδικούς τύπους δεδομένων:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> type(my_contacts.items())\n" +
                        "<class “dict_items”>\n" +
                        ">>> type(my_contacts.keys())\n" +
                        "<class “dict_keys”>\n" +
                        ">>> type(my_contacts.values())\n" +
                        "<class “dict_values”>\n" +




                        "Μπορείτε να τα μετατρέψετε σε λίστες χρησιμοποιώντας τη συνάρτηση list():\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> list(my_contacts.keys())\n" +
                        "[“Maria”, “Nikos”]\n" +
                        "Μάθημα 5: Λεξικά – Παραδείγματα\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "# Παράδειγμα με λεξικό και ενημέρωση λεξικού\n" +
                        "a = {\n" +
                        "   1: \"hydrogen\",\n" +
                        "   6: \"carbon\",\n" +
                        "   7: \"nitrogen\",\n" +




                        "   8: \"οξυγόνο\",\n" +
                        "}\n" +
                        "\n" +
                        "a.update({8: \"φώσφορος\", 9: \"θείο\"})\n" +
                        "print(a)\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        "# Νομπελίστες – παράδειγμα με μια τούπλα ως κλειδί\n" +
                        "nobel_prize_winners = {\n" +
                        "    (1979, \"φυσική\"): [\"Glashow\", \"Salam\", \"Weinberg\"],\n" +




                        "    (1962, \"chemistry\"): [\"Hodgkin\"],\n" +
                        "    (1984, \"biology\"): [\"McClintock\"],\n" +
                        "}\n" +
                        "\n" +
                        "print(nobel_prize_winners)\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "# Παράδειγμα: παρουσίαση ενός λεξικού σε ταξινομημένη σειρά\n" +
                        "contacts = {\"Nikos\": 777888, \"Maria\": 666544, \"Katerina\": 444333}\n" +




                        "print(contacts)\n" +
                        "\n" +
                        "contacts_sorted = list(contacts.items())\n" +
                        "contacts_sorted.sort()\n" +
                        "\n" +
                        "print(contacts_sorted)",




                "Sets in Python\n\nAnother data type similar to lists — and particularly useful when you need to store collections of unique elements — is the set.\n" +
                        "\n" +
                        "This data type follows the mathematical concept of a set, meaning a collection of distinct elements.\n" +
                        "\n" +
                        "The elements of a set are unique.\n" +
                        "\n" +




                        "Δεν έχουν συγκεκριμένη σειρά ή θέση.\n" +
                        "\n" +
                        "Ορισμός και τροποποίηση συνόλων\n" +
                        "Ένα σύνολο ορίζεται περικλείοντας τα στοιχεία του με αγκύλες {}.\n" +
                        "Μπορείτε να:\n" +
                        "\n" +
                        "Προσθέσετε στοιχεία χρησιμοποιώντας .add()\n" +
                        "\n" +




                        "Να αφαιρέσετε στοιχεία χρησιμοποιώντας .remove()\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_set = {1, 2, 3, 4}\n" +




                        ">>> a_set.add(5)\n" +
                        ">>> a_set\n" +
                        "{1, 2, 3, 4, 5}\n" +
                        ">>> a_set.remove(4)\n" +




                        ">>> a_set\n" +
                        "{1, 2, 3, 5}\n" +
                        "Δημιουργία συνόλου από λίστα\n" +
                        "Η συνάρτηση set() μπορεί να λάβει μια λίστα ως όρισμα και να επιστρέψει ένα αντικείμενο συνόλου, αφαιρώντας αυτόματα τα διπλά στοιχεία.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        ">>> a_list = [1, 2, 3, 4, 4, 4, 5]\n" +
                        ">>> a_set = set(a_list)\n" +
                        ">>> a_set\n" +
                        "{1, 2, 3, 4, 5}\n" +
                        "Πρακτική περίπτωση χρήσης\n" +




                        "Για να αφαιρέσετε διπλότυπα από μια λίστα, μπορείτε να χρησιμοποιήσετε:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> a_list = [1, 2, 3, 4, 4, 4, 5]\n" +
                        ">>> list(set(a_list))\n" +




                        "[1, 2, 3, 4, 5]\n" +
                        "Σημείωση: Δεδομένου ότι τα σύνολα δεν είναι ταξινομημένα, η λίστα που προκύπτει ενδέχεται να μην διατηρεί την αρχική σειρά των στοιχείων.",
                "Άσκηση: Οι επαφές μου\n\nΔημιουργήστε ένα λεξικό τηλεφωνικών επαφών και γράψτε εντολές για:\n" +
                        "\n" +
                        "Εισαγωγή νέων καταχωρήσεων που παρέχονται από τον χρήστη.\n" +




                        "\n" +
                        "Εκτύπωση του λεξικού ταξινομημένου σε αλφαβητική σειρά.\n" +
                        "\n" +
                        "Πρέπει να ταξινομήσετε το λεξικό με βάση το όνομα της επαφής (το κλειδί του λεξικού).\n" +
                        "\n" +




                        "Για να ταξινομήσετε τη λίστα των επαφών, χρησιμοποιήστε τη μέθοδο sort() στη λίστα που προκύπτει από τη μετατροπή των ζευγών (κλειδί, τιμή) από το dictionary.items() σε μια λίστα.\n" +
                        "\n" +
                        "Λύση\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "epafes = {\"Nikos\": 111222, \"Maria\": 333444}\n" +
                        "\n" +
                        "# Εισαγωγή νέας επαφής\n" +
                        "print(\"Τρέχουσες επαφές: \\n\", epafes)\n" +
                        "print(\"Εισάγετε μια νέα επαφή:\")\n" +
                        "name = input(\"Εισάγετε όνομα: \")\n" +




                        "tel = input(\"Εισάγετε τον αριθμό τηλεφώνου: \")\n" +
                        "epafes[name] = int(tel)\n" +
                        "print(\"Ενημερωμένες επαφές: \\n\", epafes)\n" +
                        "\n" +
                        "# Ταξινόμηση επαφών αλφαβητικά κατά όνομα\n" +
                        "epafes_list = list(epafes.items())\n" +




                        "epafes_list.sort()\n" +
                        "print(\"Ταξινομημένη λίστα επαφών:\\n\", epafes_list)",
                "Άσκηση: Μετρώντας λέξεις\n\nΕάν η μεταβλητή poem περιέχει ένα τραγούδι (για παράδειγμα):\n" +
                        "\n" +
                        "pgsql\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "Μετράω τα ψιλά μου  \n" +
                        "για να βγάλω άλλο ένα μήνα  \n" +
                        "Ανοίγω και δεν βλέπω ουρανό  \n" +
                        "έχεις όλη την Αθήνα  \n" +
                        "στο πιάτο σου  \n" +
                        "ανοίγεις και κοιτάς το κενό  \n" +
                        "Γράψε ένα πρόγραμμα που:\n" +
                        "\n" +




                        "Εκτυπώνει τις λέξεις με αλφαβητική σειρά.\n" +
                        "\n" +
                        "Υπολογίζει τον αριθμό των λέξεων.\n" +
                        "\n" +
                        "Υπολογίζει τον αριθμό των γραμμάτων στους στίχους αυτού του τραγουδιού του Οδυσσέα Ιωάννου.\n" +
                        "\n" +
                        "Λύση\n" +




                        "Παρακάτω είναι αποσπάσματα κώδικα για κάθε μέρος της άσκησης:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "# Άσκηση 6.3\n" +
                        "\n" +
                        "poem = “”'\n" +
                        "Μετράω τα ρέστα μου  \n" +




                        "για να περάσω έναν άλλο μήνα  \n" +
                        "Ανοίγω και δεν βλέπω τον ουρανό  \n" +
                        "έχεις όλη την Αθήνα  \n" +
                        "στο πιάτο σου  \n" +
                        "ανοίγεις και κοιτάς το κενό  \n" +
                        "“”'\n" +
                        "\n" +
                        "print(poem)\n" +




                        "\n" +
                        "# Αλφαβητική λίστα λέξεων\n" +
                        "\n" +
                        "list_words = poem.split()\n" +
                        "list_words.sort()\n" +
                        "print(list_words)\n" +
                        "\n" +
                        "# Αριθμός λέξεων\n" +
                        "\n" +




                        "print(\"Αριθμός λέξεων: {}\".format(len(list_words)))\n" +
                        "\n" +
                        "# Αριθμός γραμμάτων (εξαιρουμένων των κενών και των νέων γραμμών)\n" +
                        "\n" +
                        "poem_clean = poem.replace(\" \", \"\").replace(\"\\n\", \"\")\n" +




                        "print(\"Αριθμός γραμμάτων: {}\".format(len(poem_clean)))",
                "Δομή απόφασης if-elif-else\n\nΣύνθετη δήλωση Python\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "if condition:\n" +




                        "    statement_block\n" +
                        "Δομή απόφασης\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "if condition:\n" +
                        "    statement1\n" +
                        "else:\n" +
                        "    statement2\n" +
                        "statement3\n" +




                        "ή\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "εάν συνθήκη:\n" +
                        "    δήλωση1\n" +
                        "    δήλωση2\n" +
                        "    ...\n" +
                        "ή πιο σύνθετο:\n" +
                        "\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "if condition1:\n" +
                        "    δήλωση1\n" +
                        "elif condition2:\n" +
                        "    δήλωση2\n" +
                        "elif condition3:\n" +
                        "    δήλωση3\n" +




                        "else:\n" +
                        "    δήλωση4\n" +
                        "    δήλωση5\n" +
                        "    ...\n" +
                        "Δομή απόφασης: συνθήκη\n" +
                        "Η συνθήκη είναι μια έκφραση που αποτιμάται ως True ή False.\n" +
                        "\n" +
                        "Τελεστές σύγκρισης\n" +




                        "== (ίσο)\n" +
                        "\n" +
                        "> (μεγαλύτερο από)\n" +
                        "\n" +
                        "< (μικρότερο από)\n" +
                        "\n" +
                        ">= (μεγαλύτερο ή ίσο)\n" +
                        "\n" +
                        "<= (μικρότερο ή ίσο)\n" +
                        "\n" +




                        "!= (δεν ισούται με)\n" +
                        "\n" +
                        "Λογικοί τελεστές\n" +
                        "και\n" +
                        "\n" +
                        "ή\n" +
                        "\n" +
                        "όχι\n" +
                        "\n" +
                        "Τελεστές συμμετοχής (μέρος της δομής)\n" +
                        "σε\n" +




                        "\n" +
                        "δεν είναι μέσα\n" +
                        "\n" +
                        "Ασκήσεις\n" +
                        "7.1 Ζητήστε από τον χρήστη το όνομά του. Αν ξεκινά με το γράμμα “N”, καλωσορίστε τον.\n" +
                        "\n" +
                        "7.2 Σύμφωνα με τους κανονισμούς, η κλίμακα βαθμολογίας είναι η εξής:\n" +
                        "\n" +




                        "άριστα: από 8,5 έως 10,\n" +
                        "\n" +
                        "πολύ καλά: από 6,5 έως (αλλά όχι συμπεριλαμβανομένου) 8,5,\n" +
                        "\n" +
                        "καλά: από 5 έως (αλλά όχι συμπεριλαμβανομένου) 6,5.\n" +
                        "\n" +




                        "Γράψτε ένα πρόγραμμα που ταξινομεί τους βαθμούς με βάση αυτό.\n" +
                        "\n" +
                        "7.3 Γράψτε ένα πρόγραμμα για να βρείτε τις λύσεις μιας τετραγωνικής εξίσωσης.\n" +
                        "Ο χρήστης εισάγει τρεις αριθμούς a, b, c, που είναι οι συντελεστές της τετραγωνικής εξίσωσης\n" +
                        "ax^2+bx+c=0.\n"+




                        "Το πρόγραμμα πρέπει να εκτυπώσει τις λύσεις.\n" +
                        "\n" +
                        "7.4 Ο χρήστης εισάγει μια λέξη.\n" +
                        "Μόνο αν η λέξη περιέχει τα γράμματα \"ο\" ή \"ω\", απαντήστε με \"Ευχαριστώ\".\n" +
                        "\n" +
                        "7.5 Διαβάστε 3 αριθμούς και επιστρέψτε τον μεγαλύτερο από τους τρεις.",




                "Δήλωση IF\n\nΗ δήλωση if της Python επιτρέπει τον έλεγχο της ροής ενός προγράμματος, δηλαδή την υπό όρους εκτέλεση ενός μέρους του προγράμματος. Η δήλωση if υπάρχει σε όλες τις γλώσσες προγραμματισμού.\n" +
                        "Σύνταξη της if\n" +
                        "Αυτή είναι η πρώτη σύνθετη δήλωση στην Python όπου βλέπουμε τη χρήση εσοχής για την ομαδοποίηση εντολών.\n" +
                        "\n" +




                        "Η απλούστερη μορφή του if είναι:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "if condition:\n" +
                        "    block_of_commands_1\n" +




                        "Σε αυτή την περίπτωση, το block_of_commands_1 εκτελείται αν η συνθήκη είναι αληθής, διαφορετικά δεν εκτελείται.\n" +
                        "\n" +
                        "Μια πιο σύνθετη μορφή είναι όταν το if ακολουθείται από έναν κλάδο else:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "if condition:\n" +
                        "    block_of_commands_1\n" +
                        "else:\n" +
                        "    block_of_commands_2\n" +
                        "Εάν η συνθήκη είναι αληθής, εκτελείται το block_of_commands_1. Εάν είναι ψευδής, εκτελείται το block_of_commands_2.\n" +
                        "\n" +




                        "Επίσης, το μπλοκ else μπορεί να περιέχει και άλλο if. Σε αυτή την περίπτωση, αντί για else: if ..., χρησιμοποιείται η έκφραση elif, ως εξής:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "if condition_1:\n" +
                        "    block_of_commands_1\n" +




                        "elif condition_2:\n" +
                        "    block_of_commands_2\n" +
                        "else:\n" +
                        "    block_of_commands_3\n" +
                        "Σε αυτό το παράδειγμα, πρώτα ελέγχεται η συνθήκη_1. Εάν είναι αληθής, εκτελείται το block_of_commands_1. Διαφορετικά, ελέγχεται η συνθήκη_2. Εάν η συνθήκη_2 είναι αληθής (δηλαδή η συνθήκη_1 ήταν ψευδής αλλά η συνθήκη_2 αληθής), τότε εκτελείται το block_of_commands_2. Τέλος, εάν και οι δύο συνθήκες_1 και συνθήκη_2 είναι ψευδείς, τότε εκτελείται το block_of_commands_3.\n" +




                        "\n" +
                        "Ο κλάδος elif μπορεί να επαναληφθεί πολλές φορές.\n" +
                        "\n" +
                        "Η συνθήκη ενός if\n" +
                        "Η συνθήκη είναι μια λογική έκφραση που αποτιμάται ως True ή False.\n" +
                        "\n" +




                        "Σημειώστε ότι ο ακέραιος αριθμός 0, ο δεκαδικός αριθμός 0.0, η κενή συμβολοσειρά \"\", η κενή λίστα [], το κενό λεξικό {}, κ.λπ., θεωρούνται ισοδύναμα με False. Αντίθετα, οι αριθμοί διαφορετικοί από το μηδέν ή οι λίστες/λεξικά με στοιχεία έχουν ως αποτέλεσμα True.\n" +
                        "\n" +
                        "Εξηγήστε το παρακάτω παράδειγμα:\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> if \"0\":\n" +
                        "\tprint(\"ok\")\n" +
                        "ok\n" +
                        "\n" +
                        ">>> if 0:\n" +
                        "\tprint(\"ok\")\n" +




                        "Επεξήγηση: Η πρώτη λογική έκφραση είναι μια μη κενή συμβολοσειρά, η οποία αποτιμάται ως αληθής, οπότε εκτελείται η εντολή print(). Η δεύτερη είναι ο ακέραιος αριθμός 0, ο οποίος είναι ψευδής, οπότε η εντολή print() δεν εκτελείται.\n" +
                        "\n" +
                        "Τύποι λογικών εκφράσεων\n" +
                        "Μια λογική έκφραση είναι μια σύγκριση (έλεγχος ισότητας ή ανισότητας) μεταξύ δύο τιμών ή δύο μεταβλητών.\n" +




                        "\n" +
                        "Τελεστές σύγκρισης\n" +
                        "== ίσο με\n" +
                        "\n" +
                        "> μεγαλύτερο από\n" +
                        "\n" +
                        "< μικρότερο από\n" +
                        "\n" +
                        ">= μεγαλύτερο ή ίσο με\n" +
                        "\n" +




                        "<= μικρότερο ή ίσο με\n" +
                        "\n" +
                        "!= διαφορετικό από\n" +
                        "\n" +
                        "Επίσης, μια λογική έκφραση μπορεί να είναι ένας συνδυασμός εκφράσεων που συνδέονται με λογικούς τελεστές και, ή, και όχι.\n" +
                        "\n" +
                        "Λογικοί τελεστές:\n" +
                        "Η έκφραση a και b είναι:\n" +




                        "\n" +
                        "Αληθές αν και a και b είναι αληθή\n" +
                        "\n" +
                        "Ψευδές αν είτε a είτε b είναι ψευδές\n" +
                        "\n" +
                        "Η έκφραση a ή b είναι:\n" +
                        "\n" +
                        "Αληθές αν τουλάχιστον ένα από τα a ή b είναι αληθές\n" +
                        "\n" +




                        "Ψευδής αν και οι δύο είναι ψευδείς\n" +
                        "\n" +
                        "Η έκφραση όχι a είναι:\n" +
                        "\n" +
                        "Αληθής αν a είναι ψευδής\n" +
                        "\n" +
                        "Ψευδής αν a είναι αληθής\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "v = input(\"grade: \")\n" +
                        "v = float(v)\n" +
                        "if v >= 5 and v <= 10:\n" +
                        "    print(\"you passed\")\n" +
                        "    print(\"congratulations!!\")\n" +




                        "elif v < 5 and v >= 0:\n" +
                        "    print(\"απέτυχες\")\n" +
                        "    print(\"δοκίμασε ξανά!!!\")\n" +
                        "else:\n" +
                        "    print(\"υπάρχει σφάλμα: ο βαθμός πρέπει να είναι μεταξύ 0 και 10\")\n" +




                        "Σε αυτό το παράδειγμα, πρώτα ελέγχει αν η μεταβλητή v είναι μεταξύ 5 και 10, μετά αν είναι μεταξύ 0 και 5, και για κάθε άλλη περίπτωση εκτυπώνει ένα μήνυμα σφάλματος.",
                "Παράδειγμα: λύση μιας τετραγωνικής εξίσωσης\n\nΤο πρόβλημα\n" +
                        "Γράψτε ένα πρόγραμμα που βρίσκει τις λύσεις μιας τετραγωνικής εξίσωσης.\n" +
                        "\n" +




                        "Ο χρήστης πρέπει να εισάγει 3 αριθμούς, a, b και c, που είναι οι παράμετροι της τετραγωνικής εξίσωσης:\n" +
                        "\n" +
                        "\uD835\uDC4E\n" +
                        "\uD835\uDC65\n" +
                        "2\n" +
                        "+\n" +




                        "\uD835\uDC4F\n" +
                        "\uD835\uDC65\n" +
                        "+\n" +
                        "\uD835\uDC50\n" +
                        "=\n" +
                        "0\n" +
                        "ax \n" +
                        "2\n" +




                        " +bx+c=0\n" +
                        "Το πρόγραμμα πρέπει να εκτυπώσει τις λύσεις, δηλαδή τις τιμές του \n" +
                        "\uD835\uDC65\n" +
                        "x που ικανοποιούν την εξίσωση, ή να εκτυπώσει τα κατάλληλα μηνύματα αν δεν υπάρχουν λύσεις.\n" +
                        "\n" +




                        "Εάν η λύση είναι δεκαδικός αριθμός, θα πρέπει να εκτυπωθεί με δύο δεκαδικά ψηφία.\n" +
                        "\n" +
                        "Προτεινόμενη λύση\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "a = input(\"a=\")\n" +
                        "b = input(\"b=\")\n" +




                        "c = input(\"c=\")\n" +
                        "print(\"Θα λύσουμε την εξίσωση:\")\n" +
                        "print(\"{}x**2 + {}x + {} = 0\\n\".format(a, b, c))\n" +
                        "\n" +
                        "a = float(a)\n" +
                        "b = float(b)\n" +




                        "c = float(c)\n" +
                        "\n" +
                        "# Ελέγξτε διαφορετικές περιπτώσεις των a, b, c\n" +
                        "if a == 0:\n" +
                        "    if b == 0:\n" +
                        "        if c == 0:\n" +
                        "            print(\"Υπάρχουν άπειρες λύσεις\")\n" +




                        "        else:\n" +
                        "            print(\"Δεν υπάρχουν λύσεις\")\n" +
                        "    else:\n" +
                        "        # Γραμμική εξίσωση bx + c = 0\n" +
                        "        print(\"Η λύση είναι x1 = x2 = {:1.2f}\".format(-c / b))\n" +
                        "else:\n" +




                        "    διακριτικός = b**2 - 4 * a * c\n" +
                        "    print(\"Ο διακριτικός είναι {:1.2f}\".format(διακριτικός))\n" +
                        "\n" +
                        "    if διακριτικός < 0:\n" +
                        "        print(\"Η εξίσωση δεν έχει πραγματικές λύσεις\")\n" +




                        "    else:\n" +
                        "        x1 = (-b + discriminant**0.5) / (2 * a)\n" +
                        "        x2 = (-b - discriminant**0.5) / (2 * a)\n" +




                        "        print(\"Οι λύσεις είναι: x1 = {:1.2f}, x2 = {:1.2f}\".format(x1, x2))",
                "Δομή βρόχου For\n\nΜια δομή βρόχου επιτρέπει την εκτέλεση ενός μέρους του προγράμματος πολλές φορές. Αυτή είναι μια πολύ συνηθισμένη απαίτηση στην προγραμματισμό.\n" +
                        "\n" +




                        "Υπάρχουν διάφορες εντολές Python που επιτρέπουν την επαναλαμβανόμενη εκτέλεση ενός μέρους του προγράμματος. Ο βρόχος for, τον οποίο θα δούμε πρώτα, βασίζεται στην απαρίθμηση των επαναλήψεων, ενώ ο βρόχος while, τον οποίο θα καλύψουμε αργότερα, βασίζεται στον έλεγχο μιας συνθήκης τερματισμού.\n" +
                        "\n" +
                        "Η σύνταξη της δήλωσης for είναι:\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "for element in collection:\n" +
                        "    block_of_statements\n" +




                        "Το block_of_statements επαναλαμβάνεται όσες φορές υπάρχουν στοιχεία στη συλλογή και σε κάθε επανάληψη, η μεταβλητή element παίρνει την τιμή κάθε μέλους της συλλογής. Η συλλογή μπορεί να είναι μια λίστα, μια συμβολοσειρά, ένα λεξικό, μια τούπλα ή οποιοδήποτε επαναλήψιμο αντικείμενο.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> for fruit in [\"μπανάνες\", \"κεράσια\", \"μήλα\"]:\n" +
                        "        print(fruit, end=\", \")\n" +
                        "μπανάνες, κεράσια, μήλα,\n" +




                        "Σε αυτό το παράδειγμα, το μπλοκ for επαναλήφθηκε 3 φορές (ο αριθμός των στοιχείων στη λίστα) και σε κάθε επανάληψη, η μεταβλητή fruit πήρε την αντίστοιχη τιμή, η οποία εκτυπώθηκε.\n" +
                        "\n" +
                        "break και continue σε έναν βρόχο for\n" +
                        "Δύο εντολές Python που συνοδεύουν τους βρόχους είναι οι break και continue.\n" +
                        "\n" +




                        "break μέσα σε έναν βρόχο αναγκάζει την εκτέλεση του προγράμματος να βγει από τον βρόχο.\n" +
                        "\n" +
                        "continue σταματά την τρέχουσα επανάληψη, παραλείπει τις υπόλοιπες εντολές και προχωρά στην επόμενη επανάληψη.\n" +
                        "\n" +
                        "Συχνά ένας βρόχος for ακολουθείται από μια ρήτρα else που περιέχει ένα μπλοκ εντολών που εκτελούνται αν ο βρόχος τελειώσει κανονικά (χωρίς break).\n" +




                        "\n" +
                        "Παράδειγμα προγράμματος\n" +
                        "Ένα πρόγραμμα που γνωρίζει 10 μυστικούς τυχαίους αριθμούς και ζητά από τον χρήστη να μαντέψει έναν. Αν ο χρήστης μαντέψει σωστά, το πρόγραμμα απαντά.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "secret = [2, 5, 8, 11, 12, 15, 18, 20]\n" +
                        "guess = int(input(\"δώσε αριθμό:\"))\n" +
                        "for number in secret:\n" +
                        "    if number == guess:\n" +




                        "        print(\"μάντεψες σωστά!\")\n" +
                        "        break\n" +
                        "else:\n" +
                        "    print(\"όχι δεν βρέθηκε ο αριθμός\")\n" +
                        "Παράδειγμα εκτέλεσης προγράμματος\n" +
                        "python-repl\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "======== ΕΠΑΝΕΚΚΙΝΗΣΗ:  \n" +
                        "δώσε αριθμό:10\n" +
                        "όχι δεν βρέθηκε ο αριθμός\n" +
                        ">>> \n" +
                        "======== ΕΠΑΝΕΚΚΙΝΗΣΗ: \n" +
                        "δώσε αριθμό:15\n" +
                        "μάντεψες σωστά!\n" +
                        ">>> \n" +
                        "Στη δεύτερη γραμμή, το πρόγραμμα ζητά από τον χρήστη έναν αριθμό και τον αποδίδει στη μεταβλητή guess μετά τη μετατροπή της εισόδου σε ακέραιο αριθμό. Στη συνέχεια, εκτελεί βρόχο στη λίστα secret με έναν βρόχο for και ελέγχει αν ο τρέχων αριθμός ισούται με την εικασία του χρήστη. Εάν ναι, εκτυπώνει \"μάντεψες σωστά!\" (\"you guessed right!\") και διακόπτει τον βρόχο, καθώς δεν έχει νόημα να συνεχίσει. Αν ο βρόχος τελειώσει χωρίς να βρει τον αριθμό, εκτελείται το else και εκτυπώνεται \"όχι δεν βρέθηκε ο αριθμός\".\n" +
                        "\n" +
                        "Επανάληψη των στοιχείων του λεξικού\n" +
                        "Αν η συλλογή σε έναν βρόχο for είναι ένα λεξικό, είναι ενδιαφέρον να δούμε τι τιμές παίρνει η μεταβλητή του βρόχου.\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> dd = {5:50, 6:66, 7:77}\n" +
                        ">>> for i in dd:\n" +
                        "        print(i, end=\" | \")\n" +




                        "5 | 6 | 7 | \n" +
                        ">>> \n" +
                        "Από το παράδειγμα, η μεταβλητή του βρόχου παίρνει τα κλειδιά του λεξικού. Αν θέλετε να εκτυπώσετε τόσο τα κλειδιά όσο και τις τιμές σε κάθε επανάληψη, έχετε δύο τρόπους:\n" +
                        "\n" +
                        "Χρησιμοποιήστε το κλειδί για να λάβετε την τιμή dd[i]\n" +
                        "\n" +




                        "Ή κάντε βρόχο στο λεξικό με dd.items(), που επιστρέφει τούπλες (κλειδί, τιμή):\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> for t in dd.items():\n" +
                        "        print(t, end=\" | \")\n" +




                        "(5, 50) | (6, 66) | (7, 77) | \n" +
                        ">>> ",
                "Εύρος λειτουργίας\n\nΗ δομή for υπάρχει σε όλες σχεδόν τις γλώσσες προγραμματισμού, αλλά η πιο συνηθισμένη περίπτωση είναι ότι η μεταβλητή μέτρησης παίρνει ακέραιες τιμές από μια αρχική τιμή που αυξάνεται μέχρι να ικανοποιηθεί μια συνθήκη τερματισμού.\n" +
                        "\n" +




                        "Στην Python, μια παρόμοια συμπεριφορά επιτυγχάνεται με τη χρήση του επαναλήψιμου αντικειμένου που επιστρέφεται από τη συνάρτηση range(start, stop, step), η οποία επιστρέφει μια ακολουθία ακέραιων αριθμών που ξεκινά από την τιμή start και τελειώνει στο stop - 1, αυξάνοντας κατά step σε κάθε επανάληψη.\n" +
                        "\n" +




                        "Εάν δοθεί μόνο ένα όρισμα, range(x), θεωρείται ότι start=0, stop=x και step=1.\n" +
                        "\n" +
                        "Εάν δοθούν δύο ορίσματα, range(x, y), θεωρείται ότι start=x, stop=y και step=1.\n" +




                        "\n" +
                        "Για παράδειγμα:\n" +
                        "\n" +
                        "Το αντικείμενο που επιστρέφεται από το range(3) είναι η ακολουθία 0, 1, 2.\n" +
                        "\n" +
                        "Το αντικείμενο που επιστρέφεται από το range(1, 5) είναι η ακολουθία 1, 2, 3, 4.\n" +
                        "\n" +




                        "Το αντικείμενο που επιστρέφεται από το range(1, 5, 2) είναι η ακολουθία 1, 3.\n" +
                        "\n" +
                        "Ένα παράδειγμα χρήσης του range σε έναν βρόχο for είναι η εκτύπωση των τετραγώνων των πρώτων 5 θετικών ακέραιων αριθμών:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> for i in range(1, 6):\n" +
                        "    print(i**2, end=\"\\t\")\n" +
                        "1\t4\t9\t16\t25\t\n" +
                        ">>> \n" +




                        "Μπορούμε επίσης να χρησιμοποιήσουμε το range για να μετρήσουμε τον αριθμό των επαναλήψεων ενός μπλοκ. Αν θέλουμε να εκτυπώσουμε ένα μήνυμα 3 φορές:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> for _i in range(3):\n" +
                        "    print(\"goodmorning\")\n" +




                        "goodmorning\n" +
                        "goodmorning\n" +
                        "goodmorning\n" +
                        ">>>\n" +
                        "Σε αυτό το παράδειγμα, βλέπουμε μια σύμβαση προγραμματισμού που χρησιμοποιούν πολλοί προγραμματιστές: τα ονόματα των μεταβλητών που είναι βοηθητικά και δεν χρησιμοποιούνται αλλού ξεκινούν με το σύμβολο _. Σε αυτή την περίπτωση, η μεταβλητή _i δεν χρησιμοποιείται σε καμία εντολή εκτός από τον ίδιο τον βρόχο for.",




                "Δομή βρόχου while\n\nΗ δομή επανάληψης while δεν σχετίζεται με την επανάληψη μιας συλλογής στοιχείων όπως το for, αλλά με την επανάληψη ενός μπλοκ δηλώσεων όσο ισχύει μια συνθήκη. Όταν η συνθήκη παύει να ισχύει, οι επαναλήψεις σταματούν και το πρόγραμμα προχωρά στις επόμενες οδηγίες.\n" +
                        "\n" +
                        "Η σύνταξη του while είναι:\n" +
                        "\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "while condition:\n" +
                        "    block_of_statements\n" +
                        "Ο βρόχος while πρέπει να σχεδιαστεί έτσι ώστε μέσα στο μπλοκ των δηλώσεων να υπάρχει ένας τρόπος αλλαγής της συνθήκης για να επιτευχθεί ο τερματισμός του βρόχου. Εάν αυτό δεν συμβεί, τότε έχουμε έναν ατέρμονο βρόχο.\n" +




                        "\n" +
                        "Αυτή η δομή, όπως και η for, μπορεί να συνδυαστεί με τις δηλώσεις break και continue και μπορεί να ακολουθείται από μια ρήτρα else.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "while condition:\n" +
                        "    block_of_statements_1\n" +




                        "    if condition:\n" +
                        "        continue  # go to the start of the loop\n" +
                        "    if condition:\n" +
                        "        break  # exit the loop\n" +
                        "else:\n" +
                        "    block_of_statements_2  # executed if the loop ends normally (without break)\n" +




                        "Επίσης, μια παραλλαγή του while είναι το μοτίβο while True / break:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "while True:\n" +
                        "    statements\n" +
                        "    if condition:\n" +
                        "        break\n" +




                        "Αυτή η δομή χρησιμοποιείται όταν δεν υπάρχει αρχικά έλεγχος συνθήκης, αλλά συμβαίνει μέσα στο μπλοκ των δηλώσεων, όπου ελέγχουμε κάποια συνθήκη και όταν ικανοποιείται, βγαίνουμε από τον βρόχο χρησιμοποιώντας break.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "Ας υποθέσουμε ότι ζητάμε από τον χρήστη το όνομά του και το εκτυπώνουμε με κεφαλαία γράμματα επανειλημμένα μέχρι να εισαγάγει το γράμμα x.\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "while True:\n" +
                        "    name = input(“Όνομα:”)\n" +
                        "    if name.upper() in \"XΧ\":  # κωδικοί utf-8 88 (λατινικό X) και 935 (ελληνικό Χ)\n" +




                        "        break\n" +
                        "    print(\"ok\", name.upper())\n" +
                        "Δείγμα εκτέλεσης:\n" +
                        "\n" +
                        "makefile\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "Όνομα:Νίκος\n" +
                        "ok ΝΊΚΟΣ\n" +




                        "Όνομα:Κώστας\n" +
                        "ok ΚΏΣΤΑΣ\n" +
                        "Όνομα:Μαρία\n" +
                        "ok ΜΑΡΊΑ\n" +
                        "Όνομα:x\n" +
                        "Σημείωση: Σε αυτό το παράδειγμα, ελέγχουμε την εισαγωγή τόσο για το λατινικό όσο και για το ελληνικό κεφαλαίο \"X\", χωρίς διάκριση πεζών-κεφαλαίων.\n" +




                        "\n" +
                        "Συχνά αυτή η δομή χρησιμοποιείται όταν δεν είναι γνωστό εκ των προτέρων πόσες επαναλήψεις θα εκτελέσει ο βρόχος, αλλά εξαρτάται από εξωτερικά γεγονότα, όπως η εισαγωγή δεδομένων από τον χρήστη.\n" +
                        "\n" +
                        "Παράδειγμα αμυντικής προγραμματισμού\n" +
                        "\n" +




                        "Ας υποθέσουμε ότι σε ένα πρόγραμμα ζητάμε από τον χρήστη να εισάγει έναν διψήφιο αριθμό. Μπορούμε να ελέγξουμε την τιμή εισόδου και, αν δεν είναι διψήφια, να ζητήσουμε ξανά. Αυτό επιτυγχάνεται τοποθετώντας την εντολή input() μέσα σε έναν βρόχο while:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "# ανάγνωση διψήφιου αριθμού\n" +




                        "\n" +
                        "num = 0\n" +
                        "while num > 99 or num < 10:\n" +
                        "    num = int(input(\"Εισάγετε έναν θετικό διψήφιο αριθμό:\"))\n" +
                        "else:\n" +
                        "    print(\"Ευχαριστούμε, εισάγατε {:2d}\".format(num))\n",




                "Σύντομη περιγραφή της κατανόησης λίστας\n\nΗ δημιουργία μιας λίστας από στοιχεία μπορεί να γίνει χρησιμοποιώντας έναν βρόχο for.\n" +
                        "\n" +
                        "Ας υποθέσουμε ότι θέλουμε να δημιουργήσουμε μια λίστα που περιέχει αριθμούς από το 1 έως το 20 που είναι πολλαπλάσια του 2 και του 3.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        ">>> li = []\n" +
                        ">>> for num in range(1, 21):\n" +
                        "\tif num % 2 == 0 and num % 3 == 0:\n" +
                        "\t\tli.append(num)\n" +
                        ">>> print(li)\n" +




                        "[6, 12, 18]\n" +
                        "Σε αυτό το παράδειγμα, πρώτα δημιουργούμε μια κενή λίστα li και στη συνέχεια ελέγχουμε επαναληπτικά τους αριθμούς από το 1 έως το 20 (που επιστρέφονται από το range(1, 21)), και εκείνοι των οποίων το υπόλοιπο όταν διαιρείται με το 2 και το 3 είναι μηδέν προστίθενται στη λίστα. Τέλος, εκτυπώνουμε τη λίστα.\n" +
                        "\n" +




                        "Ένας εναλλακτικός τρόπος για να κάνουμε το ίδιο είναι με μια κατανόηση λίστας, η οποία είναι ένας συνοπτικός τρόπος για να περιγράψουμε μια λίστα. Αυτή η μέθοδος, που περιγράφεται στη συνέχεια, θεωρείται πιο Pythonic από τον παραδοσιακό βρόχο for και ανήκει στο μοντέλο δηλωτικής προγραμματισμού, όπου περιγράφουμε τι θέλουμε, όχι πώς να το παράγουμε (διαδικαστικό μοντέλο).\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        ">>> li = [x for x in range(1, 21) if x % 2 == 0 and x % 3 == 0]\n" +
                        ">>> print(li)\n" +
                        "[6, 12, 18]\n" +




                        "Παρατηρούμε ότι πράγματι η δομή for αντικαταστάθηκε από μια πιο συνοπτική περιγραφή του περιεχομένου της λίστας li.\n" +
                        "\n" +
                        "Γενικά, η συνοπτική περιγραφή της λίστας είναι:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "[ έκφραση(x) για x σε Obj αν συνθήκη ]\n" +
                        "όπου Obj είναι ένα επαναλήψιμο αντικείμενο όπως μια λίστα, μια τούπλα, ένα λεξικό κ.λπ., και η συνθήκη καθορίζει ποια στοιχεία x του Obj θα συμπεριληφθούν στη νέα λίστα.\n" +
                        "\n" +
                        "Ακολουθούν δύο ακόμη παραδείγματα.\n" +
                        "\n" +




                        "Παράδειγμα 2: Δεδομένης της λίστας L = [5,8,12,7], δημιουργήστε μια νέα λίστα με στοιχεία της L που είναι περιττοί αριθμοί αυξημένοι κατά 10.\n" +
                        "\n" +
                        "Χρησιμοποιώντας έναν βρόχο for:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "L = [5,8,12,7]\n" +
                        "L1 = []\n" +
                        "for i in L:\n" +
                        "    if i % 2 == 1:\n" +
                        "        L1.append(i + 10)\n" +
                        "L = L1\n" +
                        "print(L)\n" +




                        "Χρησιμοποιώντας κατανόηση λίστας:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "L = [5,8,12,7]\n" +
                        "L = [x + 10 for x in L if x % 2 == 1]\n" +
                        "print(L)\n" +




                        "Παράδειγμα 3: Δεδομένων δύο συμβολοσειρών s1 = \"abc\" και s2 = \"xyz\", δημιουργήστε μια λίστα με όλους τους συνδυασμούς χαρακτήρων από το s1 με αυτούς του s2.\n" +
                        "\n" +
                        "Χρησιμοποιώντας έναν βρόχο for:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        "s1 = \"abc\"\n" +
                        "s2 = \"xyz\"\n" +
                        "res = []\n" +
                        "for x in s1:\n" +
                        "    for y in s2:\n" +
                        "        res.append(x + y)\n" +




                        "print(res)\n" +
                        "Χρήση κατανόησης λίστας:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "res = [x + y for x in \"abc\" for y in \"xyz\"]\n" +
                        "print(res)\n" +




                        "Έξοδος:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "[“ax”, “ay”, “az”, “bx”, “by”, “bz”, “cx”, “cy”, “cz”]",




                "Τυχαίος γεννήτρια ημερομηνιών\n\nΆσκηση - Τυχαίος γεννήτρια ημερομηνιών\n" +
                        "Εργασία:\n" +
                        "Δημιουργήστε ένα πρόγραμμα που δημιουργεί τυχαίες έγκυρες ημερομηνίες. Ο χρήστης εισάγει το έτος (π.χ. 2016) και τον αριθμό των ημερομηνιών που επιθυμεί (π.χ. 5). Το πρόγραμμα εμφανίζει μια λίστα ημερομηνιών με τη μορφή DD-MM-YYYY (π.χ., 05-03-2016).\n" +
                        "\n" +
                        "Προετοιμασία και προσέγγιση\n" +
                        "α. Βρείτε τα ονόματα των μηνών και τον αριθμό των ημερών σε κάθε μήνα. Μια καλή πηγή είναι η σελίδα της Wikipedia για τους μήνες.\n" +
                        "\n" +




                        "Από τη λίστα μηνών σε αυτή τη σελίδα, δημιουργήστε μια συμβολοσειρά months. Στη συνέχεια, επεξεργάζοντας αυτή τη συμβολοσειρά με κατανόηση λίστας, δημιουργήστε δύο λίστες: μία με τα ονόματα των μηνών και μία με τον αριθμό των ημερών σε κάθε μήνα.\n" +
                        "\n" +
                        "β. Βρείτε τον αλγόριθμο για τον προσδιορισμό των δίσεκτων ετών, ώστε να μπορείτε να αποφασίσετε αν ο Φεβρουάριος έχει 28 ή 29 ημέρες για το δεδομένο έτος. Η σελίδα της Wikipedia για τα δίσεκτα έτη παρέχει τον ακριβή αλγόριθμο.\n" +
                        "\n" +
                        "c. Για να δημιουργήσετε μια τυχαία ημερομηνία, επιλέξτε έναν τυχαίο αριθμό μήνα από το 1 έως το 12 και, στη συνέχεια, επιλέξτε έναν τυχαίο αριθμό ημέρας εντός των έγκυρων ημερών του μήνα (π.χ., ο Ιανουάριος είναι από το 1 έως το 31).\n" +
                        "\n" +




                        "Αν θέλετε διαφορετικές ημερομηνίες, πριν προσθέσετε μια ημερομηνία στη λίστα, ελέγξτε αν υπάρχει ήδη. Αν υπάρχει, δοκιμάστε ξανά.\n" +
                        "\n" +
                        "Θα χρειαστεί να δημιουργήσετε έναν τυχαίο ακέραιο αριθμό μεταξύ δύο τιμών, για τον οποίο είναι κατάλληλη η συνάρτηση randint(a,b) της μονάδας τυχαίων αριθμών της Python. Αυτή επιστρέφει έναν ψευδοτυχαίο ακέραιο αριθμό x, έτσι ώστε a <= x <= b.\n" +




                        "\n" +
                        "Επίσης, θυμηθείτε πώς να εισάγετε και να χρησιμοποιείτε βιβλιοθήκες στην Python, π.χ.\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "import random\n" +
                        "random.randint(1, 12)\n" +
                        "Αλγόριθμος\n" +




                        "Αποθηκεύστε τα ονόματα των μηνών στη λίστα month_names από τη συμβολοσειρά months.\n" +
                        "\n" +
                        "Αποθηκεύστε τον αριθμό των ημερών ανά μήνα στη λίστα month_days.\n" +




                        "\n" +
                        "Ζητήστε από τον χρήστη να εισάγει το έτος και βεβαιωθείτε ότι είναι θετικός ακέραιος αριθμός.\n" +
                        "\n" +
                        "Ελέγξτε αν το έτος είναι δίσεκτο. Εάν ναι, ορίστε τις ημέρες του Φεβρουαρίου σε 29 στη λίστα month_days.\n" +
                        "\n" +
                        "Ζητήστε από τον χρήστη να εισάγει τον αριθμό των τυχαίων ημερομηνιών που χρειάζονται και επαληθεύστε την εισαγωγή.\n" +
                        "\n" +




                        "Βρόχος: δημιουργήστε τυχαίο μήνα και ημέρα, μορφοποιήστε την ημερομηνία ως DD-MM-YYYY και προσθέστε την σε μια λίστα αν δεν υπάρχει ήδη. Σταματήστε όταν το μήκος της λίστας φτάσει στον επιθυμητό αριθμό.\n" +
                        "\n" +
                        "Κώδικας λύσης\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "import random\n" +
                        "\n" +
                        "months = “”'Ιανουάριος (31 ημέρες)\n" +
                        "Φεβρουάριος (28 ή 29 ημέρες)\n" +
                        "Μάρτιος (31 ημέρες)\n" +
                        "Απρίλιος (30 ημέρες)\n" +




                        "Μάιος (31 ημέρες)\n" +
                        "Ιούνιος (30 ημέρες)\n" +
                        "Ιούλιος (31 ημέρες)\n" +
                        "Αύγουστος (31 ημέρες)\n" +
                        "Σεπτέμβριος (30 ημέρες)\n" +




                        "Οκτώβριος (31 ημέρες)\n" +
                        "Νοέμβριος (30 ημέρες)\n" +
                        "Δεκέμβριος (31 ημέρες)“”'\n" +
                        "\n" +
                        "# Αντιστοίχιση ελληνικών γραμμάτων με διακριτικά σε εκδοχές χωρίς διακριτικά\n" +
                        "tonoi = {\n" +




                        "    \"ά\":\"α\",\n" +
                        "    \"έ\": \"ε\",\n" +
                        "    \"ή\": \"η\",\n" +
                        "    \"ί\": \"ι\",\n" +
                        "    \"ό\": \"ο\",\n" +




                        "    \"ύ\": \"υ\",\n" +
                        "    \"ώ\": \"ω\"\n" +
                        "}\n" +
                        "\n" +
                        "# Εξαγωγή ονομάτων μηνών από τη συμβολοσειρά\n" +
                        "month_names = [x.split()[0] for x in months.split(\"\\n\")]\n" +




                        "print(month_names)\n" +
                        "\n" +
                        "# Εξαγωγή αριθμού ημερών (τα δύο πρώτα ψηφία μέσα σε παρενθέσεις)\n" +
                        "month_days = [int(x.split(\"(\")[1][:2]) for x in months.split(\"\\n\")]\n" +
                        "print(month_days)\n" +
                        "\n" +




                        "while True:\n" +
                        "    year = input(\"Έτος: \").strip()\n" +
                        "    if not year.isdigit():\n" +
                        "        break\n" +
                        "    else:\n" +
                        "        y = int(year)\n" +
                        "\n" +
                        "    # Έλεγχος για δίσεκτο έτος\n" +




                        "    leap = False\n" +
                        "    if y % 4 == 0:\n" +
                        "        if y % 100 != 0:\n" +
                        "            leap = True\n" +
                        "    if not leap:\n" +
                        "        if y % 400 == 0:\n" +




                        "            leap = True\n" +
                        "\n" +
                        "    if leap:\n" +
                        "        month_days[1] = 29\n" +
                        "    else:\n" +
                        "        month_days[1] = 28\n" +
                        "\n" +
                        "    print(y, month_days)\n" +




                        "\n" +
                        "    random_days = input(\"Τυχαίες μέρες:\")\n" +
                        "    if not random_days.isdigit():\n" +
                        "        break\n" +
                        "    else:\n" +
                        "        random_days = int(random_days)\n" +
                        "        random_d_list = []\n" +




                        "\n" +
                        "        while len(random_d_list) < τυχαίες_μέρες:\n" +
                        "            m = τυχαία.randint(0, 11)          # τυχαίος δείκτης μήνα 0-11\n" +
                        "            d = τυχαία.randint(0, ημέρες_μήνα[m] - 1)  # τυχαίος δείκτης ημέρας\n" +




                        "            month_name = month_names[m]\n" +
                        "\n" +
                        "            # Αφαίρεση τόνων από το όνομα του μήνα\n" +
                        "            month_temp = \"\"\n" +
                        "            for c in month_name:\n" +
                        "                if c in tonoi:\n" +
                        "                    month_temp += tonoi[c]\n" +




                        "                else:\n" +
                        "                    month_temp += c\n" +
                        "\n" +
                        "            date = \"{:02d}-{:02d}-{:04d}\".format(d + 1, m + 1, y)\n" +
                        "\n" +




                        "            # Ονομασία μήνα με κεφαλαία για μια παραλλαγή εξόδου\n" +
                        "            month_name = month_temp.replace(\"ς\", \"Υ\").upper()\n" +
                        "            date_ = \"{:02d}-{:}-{:04d}\".format(d + 1, month_name, y)\n" +
                        "\n" +




                        "            if date not in random_d_list:\n" +
                        "                random_d_list.append(date)\n" +
                        "                print(date)\n" +
                        "                print(date_)\n" +
                        "\n" +
                        "        print(random_d_list)",
                "Ορισμός συναρτήσεων με def\n\nΟρισμός\n" +




                        "Μια συνάρτηση ορίζεται ως μια ακολουθία εντολών προγράμματος που εκτελεί μια συγκεκριμένη εργασία. Ο πιο συνηθισμένος τύπος συνάρτησης είναι αυτός που ορίζεται χρησιμοποιώντας τη λέξη-κλειδί def. Σε αυτή την περίπτωση, η συνάρτηση έχει ένα όνομα και μπορεί να κληθεί για να εκτελεστεί πολλές φορές.\n" +
                        "\n" +
                        "Υπάρχει επίσης η επιλογή να ορίσετε μια ανώνυμη συνάρτηση χρησιμοποιώντας τη λέξη-κλειδί lambda, την οποία θα δούμε σε μελλοντικά μαθήματα.\n" +
                        "\n" +




                        "Συναρτήσεις που έχουμε δει μέχρι τώρα\n" +
                        "Έχουμε ήδη χρησιμοποιήσει συναρτήσεις σε προηγούμενα μαθήματα. Παραδείγματα περιλαμβάνουν:\n" +
                        "\n" +
                        "print(): εκτυπώνει τα ορίσματά της στην έξοδο.\n" +
                        "\n" +
                        "len(): επιστρέφει το μήκος μιας ακολουθίας (π.χ. συμβολοσειρά, λίστα).\n" +
                        "\n" +




                        "range(): επιστρέφει μια ακολουθία ακέραιων αριθμών.\n" +
                        "\n" +
                        "type(): επιστρέφει τον τύπο του ορίσματός της.\n" +
                        "\n" +
                        "int(): μετατρέπει ένα όρισμα σε ακέραιο αριθμό.\n" +
                        "\n" +
                        "str(): μετατρέπει ένα όρισμα σε συμβολοσειρά.\n" +
                        "\n" +




                        "Ορισμένες συναρτήσεις επιστρέφουν ένα αποτέλεσμα, ενώ άλλες (όπως η print()) απλώς εκτελούν μια ενέργεια και δεν επιστρέφουν τίποτα.\n" +
                        "\n" +
                        "Έχουμε δει επίσης μεθόδους — συναρτήσεις που καλούνται σε ένα αντικείμενο χρησιμοποιώντας τη σημείωση τελείας, όπως mylist.append(5), η οποία προσθέτει 5 στη λίστα mylist. Αυτές είναι τυπικές της αντικειμενοστρεφούς προγραμματισμού.\n" +
                        "\n" +




                        "Ένα κοινό μοτίβο σε όλες τις περιπτώσεις είναι:\n" +
                        "\n" +
                        "Το όνομα της συνάρτησης ακολουθείται από παρενθέσεις.\n" +
                        "\n" +
                        "Μέσα στις παρενθέσεις βρίσκονται οι παράμετροι, που ονομάζονται επίσης ορίσματα, πιθανώς με τη μορφή παράμετρος=τιμή.\n" +
                        "\n" +




                        "Οι συναρτήσεις μας βοηθούν να οργανώσουμε τον κώδικα σε μικρότερα τμήματα και χρησιμοποιούνται ευρέως στον προγραμματισμό.\n" +
                        "\n" +
                        "Σε αυτό το κεφάλαιο, θα μάθουμε πώς να δημιουργούμε τις δικές μας ονομασμένες συναρτήσεις και πώς να τις καλούμε.\n" +
                        "\n" +
                        "Ορισμός μιας συνάρτησης\n" +
                        "Για να ορίσετε μια συνάρτηση στην Python, χρησιμοποιήστε def:\n" +
                        "\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def name(arguments):\n" +
                        "    function-body\n" +
                        "    ...\n" +
                        "    return value\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def adder(x, y):\n" +
                        "    result = x + y\n" +
                        "    return result\n" +
                        "Σε αυτό το παράδειγμα:\n" +
                        "\n" +
                        "Η συνάρτηση ονομάζεται adder.\n" +
                        "\n" +




                        "Έχει δύο ορίσματα: x και y.\n" +
                        "\n" +
                        "Επιστρέφει το άθροισμά τους χρησιμοποιώντας τη δήλωση return.\n" +
                        "\n" +
                        "Κλήση της συνάρτησης:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        ">>> adder(5, 10)\n" +
                        "15\n" +
                        "Μπορούμε επίσης να αποθηκεύσουμε το αποτέλεσμα σε μια μεταβλητή:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> s = adder(10, 20)\n" +




                        ">>> print(s)\n" +
                        "30\n" +
                        ">>> type(adder(5, 10))\n" +
                        "<class “int”>\n" +
                        "Σημείωση:\n" +
                        "\n" +
                        "Μια συνάρτηση δεν χρειάζεται να επιστρέφει μια τιμή — σε τέτοιες περιπτώσεις, η επιστροφή μπορεί να παραλειφθεί.\n" +




                        "\n" +
                        "Οι συναρτήσεις μπορούν να έχουν πολλαπλά σημεία επιστροφής, ανάλογα με τις συνθήκες.\n" +
                        "\n" +
                        "Ορίσματα συναρτήσεων\n" +
                        "Στο προηγούμενο παράδειγμα, η συνάρτηση adder() ορίζεται με δύο παραμέτρους: x και y.\n" +
                        "\n" +
                        "Αν προσπαθήσουμε να την καλέσουμε με ένα ή τρία ορίσματα:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> adder(30, 40, 50)\n" +
                        "TypeError: η adder() δέχεται 2 ονομαστικά ορίσματα, αλλά δόθηκαν 3\n" +




                        "Το Python εμφανίζει ένα TypeError, εξηγώντας ότι η συνάρτηση ορίστηκε να δέχεται 2 ορίσματα θέσης, αλλά δόθηκαν 3.\n" +
                        "\n" +
                        "Προαιρετικά και μεταβλητού μήκους ορίσματα\n" +
                        "Μπορούμε να ορίσουμε συναρτήσεις με προαιρετικά ή μεταβλητού μήκους ορίσματα;\n" +
                        "\n" +
                        "Ναι!\n" +
                        "\n" +




                        "Προαιρετικά (λέξεις-κλειδιά) ορίσματα\n" +
                        "Ορίζονται με την παράμετρο=προεπιλεγμένη_τιμή. Ακολουθούν τις απαιτούμενες (θεσιακές) παραμέτρους.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "def adder(x, y=0):\n" +
                        "    result = x + y\n" +
                        "    return result\n" +
                        "Χρήση:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> adder(5)\n" +




                        "5\n" +
                        ">>> adder(5, 10)\n" +
                        "15\n" +
                        "Ορίσματα μεταβλητού μήκους\n" +
                        "Χρησιμοποιήστε το *parameter για να δεχτείτε έναν μεταβλητό αριθμό ορίσματος. Μέσα στη συνάρτηση, αυτό το όρισμα συμπεριφέρεται σαν μια τούπλα.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def adder(*x):\n" +
                        "    result = sum(x)\n" +
                        "    return result\n" +
                        "Χρήση:\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> adder()\n" +
                        "0\n" +
                        ">>> adder(30, 40, 50)\n" +
                        "120\n" +
                        "Επειδή ορίσαμε το x ως όρισμα μεταβλητού μήκους, μπορούμε να καλέσουμε τη συνάρτηση με οποιονδήποτε αριθμό ορίσματος, συμπεριλαμβανομένου και του μηδενικού.\n" +




                        "\n",
                "Συναρτήσεις: Παραδείγματα\n\nΔημιουργήστε μια συνάρτηση isnum() που δέχεται μια συμβολοσειρά ως είσοδο και επιστρέφει αν είναι έγκυρος αριθμός ή όχι.\n" +
                        "\n" +
                        "Σημείωση: Έλεγχος τύπου παραμέτρων συνάρτησης\n" +




                        "Η Python, σε αντίθεση με άλλες γλώσσες προγραμματισμού, δεν απαιτεί να ορίσετε τον τύπο δεδομένων των παραμέτρων της συνάρτησης. Αυτό την καθιστά πιο ευέλικτη για τον προγραμματιστή, αλλά μπορεί επίσης να οδηγήσει σε σφάλματα εκτέλεσης.\n" +
                        "Επομένως, είναι σημαντικό να ελέγχουμε χειροκίνητα τον τύπο δεδομένων εισόδου.\n" +
                        "\n" +
                        "Όπως γνωρίζουμε, η συνάρτηση type(x) επιστρέφει τον τύπο δεδομένων της παραμέτρου x.\n" +




                        "Αν θέλουμε το x να είναι ακέραιος αριθμός, πρέπει να ελέγξουμε αν είναι τύπου int.\n" +
                        "Ωστόσο, δεν μπορούμε να γράψουμε κάτι όπως type(x) == \"int\" (όπως θα περίμενε κανείς).\n" +
                        "Αντ' αυτού, ο σωστός τρόπος είναι:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        ">>> type(5) is int\n" +
                        "True\n" +
                        ">>> type(“nikos”) is str\n" +
                        "True\n" +
                        "Αυτή η παρατήρηση είναι σημαντική για την επίλυση της άσκησης, όπου πρέπει πρώτα να ελέγξουμε αν το όρισμα είναι μια συμβολοσειρά.\n" +
                        "\n" +
                        "Προτεινόμενη λύση\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def isnum(n = “”):\n" +
                        "    if not type(n) is str:\n" +
                        "        return False\n" +
                        "\n" +
                        "    print(n)\n" +
                        "    n = n.strip()\n" +




                        "\n" +
                        "    if n.isdigit():\n" +
                        "        return True\n" +
                        "    elif n[0] in “+-” and isnum(n[1:]):\n" +
                        "        return True\n" +
                        "    elif \".\" in n:\n" +




                        "        if n.count(\".\") <= 1 and isnum(n.replace(\".\", \"\")):\n" +
                        "            return True\n" +
                        "        else:\n" +
                        "            return False\n" +
                        "    else:\n" +
                        "        return False\n" +
                        "Παράδειγμα χρήσης\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "while True:\n" +
                        "    n = input(\"n=\")\n" +
                        "    if n == “”:\n" +
                        "        break\n" +
                        "    print(isnum(n))\n" +




                        "Αυτή η συνάρτηση ελέγχει αν η συμβολοσειρά εισόδου αντιπροσωπεύει έναν έγκυρο ακέραιο ή κινητό αριθμό (θετικό ή αρνητικό), για παράδειγμα:\n" +
                        "\n" +
                        "\"123\" → True\n" +
                        "\n" +
                        "\"-45.6\" → True\n" +
                        "\n" +
                        "\"abc\" → False\n" +




                        "\n" +
                        "\"12.3.4\" → False",
                "Πεδίο μεταβλητών\n\nΗ έννοια του πεδίου είναι ευρύτερη από το πεδίο των μεταβλητών στις συναρτήσεις. Γενικά, το πεδίο αναφέρεται στην περιοχή ενός προγράμματος όπου μια μεταβλητή είναι γνωστή και μπορεί να χρησιμοποιηθεί.\n" +
                        "\n" +




                        "Για παράδειγμα, αν μια μεταβλητή δεν έχει αρχικοποιηθεί (δηλαδή, δεν έχει εκχωρηθεί μια τιμή), δεν μπορεί να χρησιμοποιηθεί σε μια έκφραση ή να περάσει ως όρισμα μιας συνάρτησης.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "print(val + 1)\n" +
                        "val = 10\n" +
                        "Αυτό θα προκαλέσει σφάλμα:\n" +
                        "\n" +
                        "pgsql\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "NameError: το όνομα “val” δεν έχει οριστεί\n" +




                        "Αυτό σημαίνει ότι η μεταβλητή val δεν έχει οριστεί ακόμα.\n" +
                        "Αν αντιστρέψουμε τη σειρά των δηλώσεων, το πρόγραμμα θα εκτυπώσει σωστά το 11.\n" +
                        "\n" +
                        "Πεδίο μεταβλητής συνάρτησης\n" +
                        "Όταν μια μεταβλητή ορίζεται μέσα σε μια συνάρτηση, ονομάζεται τοπική μεταβλητή. Το πεδίο της περιορίζεται στο εσωτερικό της συνάρτησης.\n" +




                        "Αυτό ισχύει ακόμη και αν το όνομα της μεταβλητής είναι το ίδιο με αυτό που έχει οριστεί εκτός της συνάρτησης.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def f():\n" +
                        "    name = \"Kostas\"\n" +




                        "\n" +
                        "f()\n" +
                        "print(name)\n" +
                        "Αυτό έχει ως αποτέλεσμα το ακόλουθο σφάλμα:\n" +
                        "\n" +
                        "pgsql\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "NameError: το όνομα “name” δεν έχει οριστεί\n" +




                        "Γιατί; Επειδή η μεταβλητή name είναι τοπική στη συνάρτηση f() — είναι γνωστή μόνο μέσα σε αυτή τη συνάρτηση. Έξω από τη συνάρτηση f(), στο κύριο πρόγραμμα, η name δεν είναι ορισμένη.\n" +
                        "\n" +
                        "Παράδειγμα πεδίου εφαρμογής με πολλαπλές συναρτήσεις\n" +
                        "Εξετάστε το ακόλουθο παράδειγμα:\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def main():\n" +
                        "    get_name()\n" +
                        "    print(“Hello”, name)\n" +
                        "\n" +
                        "def get_name():\n" +
                        "    name = input(\"What is your name?\")\n" +




                        "\n" +
                        "main()\n" +
                        "Αν ο χρήστης εισάγει: Nikos\n" +
                        "Το αποτέλεσμα θα είναι:\n" +
                        "\n" +
                        "perl\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "Traceback (τελευταία κλήση):\n" +
                        "  ...\n" +




                        "    print(“Hello”, name)\n" +
                        "NameError: name “name” is not defined\n" +
                        "Γιατί; Επειδή η μεταβλητή name στη συνάρτηση get_name() είναι τοπική και δεν είναι ορατή στη συνάρτηση main().\n" +
                        "\n" +
                        " Διορθωμένη έκδοση:\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "def main():\n" +
                        "    name = get_name()\n" +
                        "    print(“Hello”, name)\n" +
                        "\n" +
                        "def get_name():\n" +
                        "    name = input(\"What is your name?\")\n" +
                        "    return name\n" +
                        "\n" +




                        "main()\n" +
                        "Έξοδος:\n" +
                        "\n" +
                        "pgsql\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "Πώς σε λένε; Νίκος\n" +
                        "Γεια σου Νίκο\n" +
                        "Παγκόσμιες μεταβλητές\n" +




                        "Οι μεταβλητές που ορίζονται εκτός οποιασδήποτε συνάρτησης ονομάζονται παγκόσμιες μεταβλητές και είναι προσβάσιμες σε όλο το πρόγραμμα.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def f2():\n" +
                        "    name = \"Maria\"\n" +




                        "    print(name)\n" +
                        "\n" +
                        "def f1():\n" +
                        "    name = \"Kostas\"\n" +
                        "    f2()\n" +
                        "\n" +
                        "name = \"George\"\n" +
                        "f1()\n" +
                        "Έξοδος:\n" +




                        "\n" +
                        "nginx\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "Maria\n" +
                        "Επεξήγηση:\n" +
                        "\n" +
                        "name = \"George\" είναι μια παγκόσμια μεταβλητή.\n" +
                        "\n" +




                        "f1() ορίζει μια τοπική μεταβλητή name = \"Kostas\" — γνωστή μόνο στο f1().\n" +
                        "\n" +
                        "f2() ορίζει μια άλλη τοπική μεταβλητή name = \"Maria\".\n" +
                        "\n" +
                        "Η εντολή print(name) στο f2() εκτυπώνει την τοπική τιμή \"Maria\".\n" +
                        "\n" +




                        "Τι συμβαίνει αν αφαιρέσουμε το name = “Maria” από το f2()?\n" +
                        "Τότε ο κώδικας γίνεται:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "def f2():\n" +
                        "    print(name)\n" +
                        "\n" +




                        "def f1():\n" +
                        "    name = \"Kostas\"\n" +
                        "    f2()\n" +
                        "\n" +
                        "name = \"George\"\n" +
                        "f1()\n" +
                        "Τώρα, η f2() εκτυπώνει \"George\".\n" +




                        "Αυτό συμβαίνει επειδή η Python αναζητά ένα όνομα τοπικής μεταβλητής στο f2(), δεν βρίσκει κανένα και επιστρέφει στην παγκόσμια μεταβλητή.\n" +
                        "\n" +
                        "Χρήση της global για την τροποποίηση παγκόσμιων μεταβλητών\n" +
                        "Τι γίνεται αν θέλουμε να αλλάξουμε μια παγκόσμια μεταβλητή μέσα σε μια συνάρτηση;\n" +
                        "\n" +
                        "Παράδειγμα:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "a = 10\n" +
                        "\n" +
                        "def f():\n" +
                        "    a = 5\n" +
                        "\n" +
                        "f()\n" +
                        "print(a)\n" +




                        "Αυτό θα εκτυπώσει 10, επειδή το a = 5 μέσα στο f() είναι μια τοπική ανάθεση που δεν επηρεάζει το παγκόσμιο a.\n" +
                        "\n" +
                        "Για να τροποποιήσουμε την παγκόσμια μεταβλητή, πρέπει να χρησιμοποιήσουμε τη λέξη-κλειδί global:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "def f():\n" +
                        "    global a\n" +
                        "    a = 5\n" +
                        "\n" +
                        "a = 10\n" +
                        "f()\n" +
                        "print(a)\n" +
                        "Έξοδος:\n" +
                        "\n" +
                        "Αντιγραφή\n" +




                        "Επεξεργασία\n" +
                        "5\n" +
                        "Τώρα το a μέσα στο f() έχει επισημανθεί ρητά ως global, οπότε η ανάθεση επηρεάζει το global a.\n",
                "Μονάδες\n\nΧρήση μονάδων στην Python\n" +
                        "Στα προηγούμενα κεφάλαια, έχουμε ήδη χρησιμοποιήσει μονάδες (που ονομάζονται επίσης βιβλιοθήκες) από την τυπική βιβλιοθήκη της Python.\n" +
                        "\n" +




                        "Αυτές οι ενότητες είναι προγράμματα Python που εκτελούν συγκεκριμένες εργασίες.\n" +
                        "\n" +
                        "Καθώς προχωράτε στην προγραμματισμό Python, θα διαπιστώσετε συχνά την ανάγκη να φορτώσετε και να χρησιμοποιήσετε αυτές τις βιβλιοθήκες στον δικό σας κώδικα. Το έχουμε ήδη κάνει αυτό με τις βιβλιοθήκες random και math.\n" +
                        "\n" +
                        "Φόρτωση μιας ενότητας\n" +




                        "Για να φορτώσετε ένα module στο πρόγραμμά σας, χρησιμοποιείτε την εντολή import, που συνήθως τοποθετείται στην αρχή του κώδικα σας:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "import module_name\n" +
                        "Εναλλακτικά, μπορείτε να χρησιμοποιήσετε:\n" +
                        "\n" +




                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "from module_name import *\n" +
                        "Ή, αν θέλετε να εισαγάγετε μόνο ένα συγκεκριμένο μέρος του module:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "from module_name import element\n" +
                        "Το module_name αντιστοιχεί συνήθως σε ένα αρχείο Python με το όνομα module_name.py, το οποίο ερμηνεύεται και φορτώνεται έτσι ώστε το περιεχόμενό του (συναρτήσεις, μεταβλητές, κλάσεις κ.λπ.) να μπορεί να χρησιμοποιηθεί στο πρόγραμμά σας.\n" +
                        "\n" +




                        "Μπορείτε επίσης να δημιουργήσετε τις δικές σας ενότητες και να τις καλέσετε από το κύριο πρόγραμμά σας. Αυτό βοηθά στην οργάνωση του κώδικα σας σε ξεχωριστά, εύχρηστα μέρη.\n" +
                        "\n" +
                        "Διαφορά μεταξύ των μεθόδων εισαγωγής\n" +
                        "Υπάρχει μια σημαντική διαφορά μεταξύ των δύο στυλ εισαγωγής:\n" +
                        "\n" +




                        "Με την πρώτη μέθοδο (import module_name), έχετε πρόσβαση στα στοιχεία χρησιμοποιώντας τη σημειογραφία τελείας, όπως module_name.element.\n" +
                        "\n" +
                        "Με τη δεύτερη μέθοδο (from module_name import *), τα στοιχεία της ενότητας προστίθενται απευθείας στον χώρο ονομάτων του προγράμματος σας.\n" +
                        "\n" +
                        "Παράδειγμα:\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> import random\n" +
                        ">>> randint = random.randint(10, 20)\n" +
                        ">>> randint\n" +
                        "13\n" +
                        ">>> randint = random.randint(10, 20)\n" +
                        ">>> randint\n" +




                        "10\n" +
                        "Εδώ, χρησιμοποιούμε δύο φορές τη συνάρτηση randint() από το module random για να δημιουργήσουμε έναν ψευδοτυχαίο ακέραιο αριθμό μεταξύ 10 και 20 και να τον αποθηκεύσουμε σε μια μεταβλητή.\n" +
                        "\n" +
                        "Τώρα ας δούμε πώς συμπεριφέρεται αυτό με τον δεύτερο τύπο εισαγωγής:\n" +
                        "\n" +
                        "python\n" +




                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        ">>> from random import *\n" +
                        ">>> randint = randint(10, 20)\n" +
                        ">>> randint\n" +
                        "12\n" +
                        ">>> randint(20, 30)\n" +
                        "Αυτό έχει ως αποτέλεσμα:\n" +




                        "\n" +
                        "vbnet\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "Traceback (τελευταία κλήση):\n" +
                        "  Αρχείο \"<pyshell#6>\", γραμμή 1, σε <module>\n" +
                        "    randint(20,30)\n" +




                        "TypeError: “int” object is not callable\n" +
                        "Τι συνέβη;\n" +
                        "Σε αυτό το δεύτερο παράδειγμα, εισαγάγαμε όλο το περιεχόμενο του τυχαίου module απευθείας στον χώρο ονομάτων του προγράμματος.\n" +
                        "Έτσι, μετά την πρώτη κλήση, το όνομα randint ανατέθηκε εκ νέου σε μια τοπική μεταβλητή (έναν ακέραιο).\n" +




                        "Η δεύτερη κλήση της randint() απέτυχε επειδή η randint είναι τώρα ένας ακέραιος αριθμός και όχι μια συνάρτηση.\n" +
                        "\n" +
                        "Αυτό συνέβη επειδή η δεύτερη μέθοδος εισαγωγής προκαλεί το περιεχόμενο της ενότητας να μοιράζεται τον ίδιο χώρο ονομάτων με το δικό σας πρόγραμμα — αυξάνοντας την πιθανότητα τέτοιων συγκρούσεων ονομάτων.\n" +
                        "\n" +
                        "Συμπέρασμα:\n" +




                        "Γενικά συνιστάται να χρησιμοποιείτε την πρώτη σύνταξη εισαγωγής:\n" +
                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "import module_name\n" +
                        "Αυτό αποφεύγει τις συγκρούσεις ονοματοχώρου μεταξύ των στοιχείων του module και των δικών σας μεταβλητών, καθιστώντας τον κώδικα σας πιο προβλέψιμο και ευκολότερο να εντοπιστεί.",




                "Τυπική βιβλιοθήκη\n\nΗ τυπική βιβλιοθήκη Python\n" +
                        "Η τυπική βιβλιοθήκη Python περιέχει αρκετές εκατοντάδες ενότητες, οι οποίες καλύπτουν:\n" +
                        "\n" +
                        "Τύποι δεδομένων, όπως datetime, calendar\n" +
                        "\n" +
                        "Μαθηματικές πράξεις (π.χ. math, random)\n" +
                        "\n" +




                        "Εργασίες του λειτουργικού συστήματος (π.χ., os.path, os, time)\n" +
                        "\n" +
                        "Μόνιμη αποθήκευση δεδομένων (π.χ., sqlite3, pickle)\n" +
                        "\n" +
                        "Συμπίεση αρχείων (π.χ., zip)\n" +
                        "\n" +




                        "Μορφοποίηση αρχείων (π.χ., csv, json)\n" +
                        "\n" +
                        "Κρυπτογραφία (π.χ., hashlib)\n" +
                        "\n" +
                        "Πολλαπλές διεργασίες / ταυτόχρονη εκτέλεση (π.χ., threading)\n" +
                        "\n" +
                        "Επικοινωνία διεργασιών (π.χ., socket)\n" +




                        "\n" +
                        "Εργαλεία δομημένων δεδομένων (π.χ., html, xml.etree)\n" +
                        "\n" +
                        "Πρόσβαση στο Διαδίκτυο (π.χ., urllib.request)\n" +
                        "\n" +
                        "Γραφικά (π.χ., tkinter)\n" +
                        "\n" +




                        "...και πολλά άλλα.\n" +
                        "\n" +
                        "Μια επισκόπηση της τυπικής βιβλιοθήκης Python βοηθά τους προγραμματιστές να συνειδητοποιήσουν πόσο πλούσια είναι. Όλα αυτά περιλαμβάνονται στην διανομή Python που είναι εγκατεστημένη στον υπολογιστή σας.\n" +
                        "\n" +
                        "Γι' αυτό ακούμε συχνά τη φράση:\n" +
                        "\n" +




                        "Η Python έρχεται με μπαταρίες  όλα όσα χρειάζεστε είναι ήδη ενσωματωμένα.\n" +
                        "\n" +
                        "Εκτός από την τυπική βιβλιοθήκη, υπάρχουν εκατοντάδες χιλιάδες εξωτερικά πακέτα, πολλά από τα οποία είναι πλήρη frameworks ή βιβλιοθήκες συναρτήσεων/κλάσεων σχεδιασμένα για συγκεκριμένους σκοπούς.\n" +
                        "\n" +




                        "Ο πιο ολοκληρωμένος κατάλογος αυτών των πακέτων είναι το PyPI — το Python Package Index.\n" +
                        "\n" +
                        "Υπάρχουν σήμερα πάνω από 300.000 πακέτα καταχωρημένα εκεί.\n" +
                        "\n" +
                        "Παράδειγμα: Χρήση του Flask\n" +




                        "Ας υποθέσουμε ότι θέλουμε να δημιουργήσουμε μια εφαρμογή ιστού και, μετά από έρευνα, αποφασίζουμε ότι η βιβλιοθήκη Flask είναι η πιο κατάλληλη.\n" +
                        "\n" +
                        "Στο PyPI, μπορείτε να βρείτε όλες τις πληροφορίες που χρειάζεστε για το Flask, συμπεριλαμβανομένου του τρόπου εγκατάστασης και χρήσης του.\n" +
                        "\n" +
                        "Αν προσπαθήσετε να φορτώσετε το Flask στο IDLE με αυτόν τον τρόπο:\n" +




                        "\n" +
                        "python\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "import flask\n" +
                        "Θα εμφανιστεί το ακόλουθο σφάλμα:\n" +
                        "\n" +
                        "vbnet\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +




                        "ModuleNotFoundError: Δεν υπάρχει ενότητα με το όνομα “flask”\n" +
                        "Αυτό συμβαίνει επειδή το Flask δεν είναι μέρος της τυπικής βιβλιοθήκης — σε αντίθεση με τα προηγούμενα παραδείγματα όπως math ή random.\n" +
                        "\n" +
                        "Εγκατάσταση εξωτερικών πακέτων\n" +




                        "Σε τέτοιες περιπτώσεις, πρέπει να ανοίξετε τη γραμμή εντολών του συστήματός σας (τερματικό) και να εκτελέσετε:\n" +
                        "\n" +
                        "bash\n" +
                        "Αντιγραφή\n" +
                        "Επεξεργασία\n" +
                        "pip install flask\n" +
                        "Αφού εγκατασταθεί η βιβλιοθήκη, μπορείτε να την εισαγάγετε και να τη χρησιμοποιήσετε στο πρόγραμμά σας.\n" +




                        "\n" +
                        "Σημείωση:\n" +
                        "\n" +
                        "Η εντολή pip (ή pip3 σε ορισμένα συστήματα για να αποφευχθεί η σύγχυση με το Python 2) είναι ενσωματωμένη στο Python και χρησιμοποιείται για την εγκατάσταση εξωτερικών πακέτων και βιβλιοθηκών.\n" +
                        "\n" +
                        "Άλλοι πόροι πακέτων\n" +




                        "Εκτός από το PyPI, υπάρχουν και άλλοι ιστότοποι όπου μπορείτε να ανακαλύψετε και να συγκρίνετε βιβλιοθήκες Python. Μια δημοφιλής επιλογή είναι:\n" +
                        "\n" +
                        "\uD83C\uDF10 https://python.libhunt.com — επίσης γνωστό ως Awesome Python.",
                "\n\n",
            )




        }
        else -> {
            return emptyList()




        }        }
}
@Composable
fun getQuizForCourse(courseid: String): List<QuizQuestion> {








    val questions = when (courseid) {
        "B" -> when (LocalAppLanguage.current) {
            AppLanguage.ENGLISH -> listOf(
                QuizQuestion(
                    "What is VS Code primarily used for?",
                    listOf("Writing code", "Graphic design", "Video editing", "Accounting"),
                    "Writing code"
                )
            )




            AppLanguage.GREEK -> listOf(
                QuizQuestion(
                    "Για ποιον σκοπό χρησιμοποιείται κυρίως το VS Code;",
                    listOf("Προγραμματισμός", "Γραφιστική", "Επεξεργασία βίντεο", "Λογιστικά"),
                    "Προγραμματισμός"
                )
            )
            else -> emptyList()
        }
        "A" ->  when (LocalAppLanguage.current) {
            AppLanguage.ENGLISH -> listOf(
                QuizQuestion(
                    "What is a module in Python?",
                    listOf(
                        "A type of variable",
                        "A built-in function",
                        "A type of error",
                        "A Python file containing code that can be imported"
                    ),
                    "A Python file containing code that can be imported"
                ),




                QuizQuestion(
                    "What does the 'import' statement do in Python?",
                    listOf(
                        "Defines a new function",
                        "Loads a module into the program",
                        "Executes the program",
                        "Declares a variable"
                    ),
                    "Loads a module into the program"
                ),




                QuizQuestion(
                    "What is the scope of a local variable in a function?",
                    listOf(
                        "It is accessible throughout the program",
                        "It is accessible only within the function",
                        "It is accessible in all modules",
                        "It is accessible outside the function"
                    ),
                    "It is accessible only within the function"
                ),




                QuizQuestion(
                    "How can you access elements of a module after 'import module_name'?",
                    listOf(
                        "module_name.element",
                        "element.module_name",
                        "Just element",
                        "module_name->element"
                    ),
                    "module_name.element"
                ),




                QuizQuestion(
                    "What happens if you try to use a variable before initializing it in Python?",
                    listOf(
                        "It uses a default value",
                        "Python throws a NameError",
                        "Python ignores it",
                        "It assigns None automatically"
                    ),
                    "Python throws a NameError"
                ),




                QuizQuestion(
                    "What keyword is used to modify a global variable inside a function?",
                    listOf(
                        "local",
                        "global",
                        "static",
                        "extern"
                    ),
                    "global"
                ),




                QuizQuestion(
                    "Which of the following is NOT part of Python's standard library?",
                    listOf(
                        "math",
                        "flask",
                        "os",
                        "json"
                    ),
                    "flask"
                ),




                QuizQuestion(
                    "What command is used to install external Python packages?",
                    listOf(
                        "python install package",
                        "pip install package",
                        "install package",
                        "pip get package"
                    ),
                    "pip install package"
                ),




                QuizQuestion(
                    "What does the '*' mean in the import statement 'from module_name import *'?",
                    listOf(
                        "Import only one function",
                        "Import all public names from the module",
                        "Import the module as a whole",
                        "Import nothing"
                    ),
                    "Import all public names from the module"
                ),




                QuizQuestion(
                    "What error occurs if you try to call an integer as a function in Python?",
                    listOf(
                        "TypeError",
                        "NameError",
                        "SyntaxError",
                        "IndexError"
                    ),
                    "TypeError"
                ),




                QuizQuestion(
                    "What is the property used to get the number of elements in a list?",
                    listOf(
                        "length()",
                        "size",
                        "count()",
                        "size()"
                    ),
                    "size"
                ),




                QuizQuestion(
                    "Which of the following modules helps with date and time in Python?",
                    listOf(
                        "datetime",
                        "random",
                        "os",
                        "math"
                    ),
                    "datetime"
                ),




                QuizQuestion(
                    "What is the purpose of the 'return' statement in a function?",
                    listOf(
                        "To output a value from the function",
                        "To stop the program",
                        "To start a loop",
                        "To define a variable"
                    ),
                    "To output a value from the function"
                ),




                QuizQuestion(
                    "How can you call a function defined in an imported module?",
                    listOf(
                        "function()",
                        "module.function()",
                        "call function()",
                        "module:function()"
                    ),
                    "module.function()"
                ),




                QuizQuestion(
                    "What is the typical file extension for a Python module?",
                    listOf(
                        ".py",
                        ".txt",
                        ".mod",
                        ".exe"
                    ),
                    ".py"
                ),




                QuizQuestion(
                    "What happens if you use 'from module_name import *' and have a local variable with the same name as an imported function?",
                    listOf(
                        "Local variable overrides the imported function",
                        "Error is thrown",
                        "Function overrides local variable",
                        "Both coexist without problems"
                    ),
                    "Local variable overrides the imported function"
                ),




                QuizQuestion(
                    "What Python module can you use for creating graphical user interfaces?",
                    listOf(
                        "tkinter",
                        "socket",
                        "threading",
                        "urllib"
                    ),
                    "tkinter"
                ),




                QuizQuestion(
                    "Which module is commonly used for cryptography in Python?",
                    listOf(
                        "hashlib",
                        "pickle",
                        "csv",
                        "random"
                    ),
                    "hashlib"
                ),




                QuizQuestion(
                    "What is the effect of 'score++' in Kotlin or Python?",
                    listOf(
                        "Increases score by one",
                        "Syntax error in Python",
                        "Decreases score by one",
                        "Resets score"
                    ),
                    "Syntax error in Python"
                ),




                QuizQuestion(
                    "Which statement is true about Python's 'global' keyword?",
                    listOf(
                        "It allows modifying a global variable inside a function",
                        "It defines a new local variable",
                        "It deletes a global variable",
                        "It imports a module globally"
                    ),
                    "It allows modifying a global variable inside a function"
                ),








                )




            AppLanguage.GREEK -> listOf(








                QuizQuestion(
                    "Τι είναι ένα module στην Python;",
                    listOf(
                        "Μια ενσωματωμένη συνάρτηση",
                        "Ένα αρχείο Python που περιέχει κώδικα που μπορεί να εισαχθεί",
                        "Ένας τύπος μεταβλητής",
                        "Ένας τύπος σφάλματος"
                    ),
                    "Ένα αρχείο Python που περιέχει κώδικα που μπορεί να εισαχθεί"
                ),




                QuizQuestion(
                    "Τι κάνει η εντολή 'import' στην Python;",
                    listOf(
                        "Εκτελεί το πρόγραμμα",
                        "Φορτώνει ένα module στο πρόγραμμα",
                        "Ορίζει μια νέα συνάρτηση",
                        "Δηλώνει μια μεταβλητή"
                    ),
                    "Φορτώνει ένα module στο πρόγραμμα"
                ),




                QuizQuestion(
                    "Ποια είναι η εμβέλεια μιας τοπικής μεταβλητής μέσα σε μια συνάρτηση;",
                    listOf(
                        "Είναι προσβάσιμη σε όλα τα modules",
                        "Είναι προσβάσιμη μόνο μέσα στη συνάρτηση",
                        "Είναι προσβάσιμη σε όλο το πρόγραμμα",
                        "Είναι προσβάσιμη εκτός της συνάρτησης"
                    ),
                    "Είναι προσβάσιμη μόνο μέσα στη συνάρτηση"
                ),




                QuizQuestion(
                    "Πώς προσπελαύνουμε στοιχεία ενός module μετά το 'import module_name';",
                    listOf(
                        "module_name.element",
                        "element.module_name",
                        "module_name->element",
                        "Απλώς element"
                    ),
                    "module_name.element"
                ),




                QuizQuestion(
                    "Τι συμβαίνει αν προσπαθήσουμε να χρησιμοποιήσουμε μια μεταβλητή πριν την αρχικοποιήσουμε;",
                    listOf(
                        "Η Python την αγνοεί",
                        "Η Python εκχωρεί αυτόματα την τιμή None",
                        "Γίνεται χρήση μιας προεπιλεγμένης τιμής",
                        "Η Python εμφανίζει NameError"
                    ),
                    "Η Python εμφανίζει NameError"
                ),




                QuizQuestion(
                    "Ποια λέξη-κλειδί χρησιμοποιείται για να τροποποιήσουμε μια global μεταβλητή μέσα σε συνάρτηση;",
                    listOf(
                        "static",
                        "extern",
                        "local",
                        "global"
                    ),
                    "global"
                ),




                QuizQuestion(
                    "Ποιο από τα παρακάτω ΔΕΝ ανήκει στη τυπική βιβλιοθήκη της Python;",
                    listOf(
                        "os",
                        "json",
                        "math",
                        "flask"
                    ),
                    "flask"
                ),




                QuizQuestion(
                    "Ποια εντολή χρησιμοποιείται για την εγκατάσταση εξωτερικών πακέτων στην Python;",
                    listOf(
                        "pip install package",
                        "install package",
                        "pip get package",
                        "python install package"
                    ),
                    "pip install package"
                ),




                QuizQuestion(
                    "Τι σημαίνει το '*' στην εντολή 'from module_name import *';",
                    listOf(
                        "Εισάγει το module ως σύνολο",
                        "Δεν εισάγει τίποτα",
                        "Εισάγει μόνο μία συνάρτηση",
                        "Εισάγει όλα τα δημόσια ονόματα του module"
                    ),
                    "Εισάγει όλα τα δημόσια ονόματα του module"
                ),




                QuizQuestion(
                    "Τι σφάλμα προκύπτει αν καλέσουμε έναν ακέραιο σαν συνάρτηση στην Python;",
                    listOf(
                        "IndexError",
                        "NameError",
                        "SyntaxError",
                        "TypeError"
                    ),
                    "TypeError"
                ),




                QuizQuestion(
                    "Ποια ιδιότητα χρησιμοποιείται για να υπολογίσουμε τα στοιχεία μιας λίστας;",
                    listOf(
                        "size()",
                        "length()",
                        "count()",
                        "size"
                    ),
                    "size"
                ),




                QuizQuestion(
                    "Ποιο από τα παρακάτω modules βοηθάει με ημερομηνίες και ώρες στην Python;",
                    listOf(
                        "math",
                        "random",
                        "os",
                        "datetime"
                    ),
                    "datetime"
                ),




                QuizQuestion(
                    "Ποιος είναι ο σκοπός της εντολής 'return' σε μια συνάρτηση;",
                    listOf(
                        "Για να ξεκινήσει βρόχος",
                        "Για να σταματήσει το πρόγραμμα",
                        "Για να δηλώσει μεταβλητή",
                        "Για να επιστρέψει τιμή από τη συνάρτηση"
                    ),
                    "Για να επιστρέψει τιμή από τη συνάρτηση"
                ),




                QuizQuestion(
                    "Πώς καλούμε μια συνάρτηση που βρίσκεται σε εισαγόμενο module;",
                    listOf(
                        "module.function()",
                        "function()",
                        "module:function()",
                        "call function()"
                    ),
                    "module.function()"
                ),




                QuizQuestion(
                    "Ποια είναι η κλασική κατάληξη αρχείων module στην Python;",
                    listOf(
                        ".mod",
                        ".txt",
                        ".exe",
                        ".py"
                    ),
                    ".py"
                ),




                QuizQuestion(
                    "Τι συμβαίνει αν κάνουμε 'from module_name import *' και έχουμε τοπική μεταβλητή με το ίδιο όνομα με εισαγόμενη συνάρτηση;",
                    listOf(
                        "Η μεταβλητή αντικαθίσταται από τη συνάρτηση",
                        "Η τοπική μεταβλητή υπερισχύει της συνάρτησης",
                        "Δίνεται σφάλμα",
                        "Συνυπάρχουν χωρίς πρόβλημα"
                    ),
                    "Η τοπική μεταβλητή υπερισχύει της συνάρτησης"
                ),




                QuizQuestion(
                    "Ποιο module χρησιμοποιείται για γραφικά στην Python;",
                    listOf(
                        "threading",
                        "socket",
                        "tkinter",
                        "urllib"
                    ),
                    "tkinter"
                ),




                QuizQuestion(
                    "Ποιο module χρησιμοποιείται συνήθως για κρυπτογράφηση στην Python;",
                    listOf(
                        "random",
                        "pickle",
                        "csv",
                        "hashlib"
                    ),
                    "hashlib"
                ),




                QuizQuestion(
                    "Ποιο είναι το αποτέλεσμα της έκφρασης 'score++' στην Python;",
                    listOf(
                        "Αυξάνει το score κατά ένα",
                        "Μηδενίζει το score",
                        "Συντακτικό σφάλμα στην Python",
                        "Μειώνει το score κατά ένα"
                    ),
                    "Συντακτικό σφάλμα στην Python"
                ),




                QuizQuestion(
                    "Ποια δήλωση είναι σωστή για τη λέξη-κλειδί 'global';",
                    listOf(
                        "Ορίζει μια νέα τοπική μεταβλητή",
                        "Διαγράφει μια global μεταβλητή",
                        "Εισάγει ένα module παγκόσμια",
                        "Επιτρέπει τροποποίηση global μεταβλητής εντός συνάρτησης"
                    ),
                    "Επιτρέπει τροποποίηση global μεταβλητής εντός συνάρτησης"
                )
            )
























            else -> emptyList()
        }




        else -> emptyList()
    }




    return questions
}
// ---------- UI Components ----------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Reset DataStore at app start (testing only!)
//        lifecycleScope.launch {
//            clearAllDataStore(applicationContext)  // or use `this@MainActivity`
//        }
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
    val navController = null




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
            painter = painterResource(R.drawable.gatsby),
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
            Strings.get("setup_profile"),
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
                Icon(painterResource(R.drawable.treasure_chest), contentDescription = "Job Role")
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
            Text(Strings.get("continue"), style = MaterialTheme.typography.labelLarge)
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
    val showLanguageToggle = currentScreen == "courses" && selectedCourse == null && !showQuiz
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
//                actions = {
//                    if (showLanguageToggle) {
//                        LanguageToggleButton(
//                            currentLanguage = currentLanguage,
//                            onLanguageChange = onLanguageChange
//                        )
//                    }
//                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                NavigationBarItem(selected = currentScreen == "courses",
                    onClick = { currentScreen = "courses" },
                    icon = { Icon(painterResource(id = R.drawable.book_multiple), contentDescription = "Courses") },
                    label = { Text(Strings.get("courses")) })
                NavigationBarItem(selected = currentScreen == "achievements",
                    onClick = { currentScreen = "achievements" },
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.trophy), contentDescription = "Achievements"
                        )
                    },
                    label = { Text(Strings.get("achievements")) })
                NavigationBarItem(selected = currentScreen == "profile",
                    onClick = { currentScreen = "profile" },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                    label = { Text(Strings.get("profile")) })
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
                    Strings.get("wb"),
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
            Strings.get("ach"),
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
                    Strings.get("no_badge"),
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
                    Strings.get("earn") + " ${badge.dateEarned}",
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
                imageVector = Icons.Default.Done,
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
            Strings.get("ach"),
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
                    Strings.get("no_badge"),
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
                    Strings.get("earn") + " ${badge.dateEarned}", style = MaterialTheme.typography.bodyMedium
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
            Strings.get("av_cour"),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        CourseCategoriesList(onCourseSelected = onCourseSelected)
    }




}
//@Composable
//fun FilterChipGroup(
//    options: List<String>, selected: String, onOptionSelected: (String) -> Unit
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        options.forEach { option ->
//            FilterChip(selected = option == selected,
//                onClick = { onOptionSelected(option) },
//                label = { Text(option) },
//                colors = FilterChipDefaults.filterChipColors(
//                    selectedContainerColor = MaterialTheme.colorScheme.primary,
//                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
//        }
//    }
//}
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
                    Text(Strings.get("prev"))
                }
                Button(
                    onClick = { if (index < slides.size - 1) index++ },
                    enabled = index < slides.size - 1
                ) {
                    Text(Strings.get("next"))
                }
            }
        }
    }
}
//@Composable
//fun BadgesGrid(filter: String) {
//    val badges = remember {
//        listOf(
//            Badge(
//                "Web Development", "2023-06-20", "Intermediate", R.drawable.ic_launcher_foreground
//            )
//        )
//    }
//    val filteredBadges = if (filter == "All") badges else badges.filter { it.level == filter }
//    if (filteredBadges.isEmpty()) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(120.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.surfaceVariant,
//                    shape = RoundedCornerShape(12.dp)
//                ), contentAlignment = Alignment.Center
//        ) {
//            Text(
//                "No badges yet", color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//    } else {
//        LazyColumn(
//            modifier = Modifier.height(150.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(filteredBadges) { badge ->
//                BadgeCard(badge)
//            }
//        }
//    }
//}
//@Composable
//fun BadgeCard(badge: Badge) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant
//        )
//    ) {
//        Row(
//            modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
//        ) {
//            if (badge.iconRes != null) {
//                Image(
//                    painter = painterResource(badge.iconRes),
//                    contentDescription = null,
//                    modifier = Modifier.size(48.dp)
//                )
//                Spacer(modifier = Modifier.width(16.dp))
//            }
//            Column {
//                Text(
//                    badge.courseName,
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Medium
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .background(
//                                color = badge.getLevelColor().copy(alpha = 0.2f),
//                                shape = RoundedCornerShape(4.dp)
//                            )
//                            .padding(horizontal = 6.dp, vertical = 2.dp)
//                    ) {
//                        Text(
//                            badge.level, color = badge.getLevelColor(), fontSize = 12.sp
//                        )
//                    }
//                    Text(
//                        badge.dateEarned,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        fontSize = 12.sp
//                    )
//                }
//            }
//        }
//    }
//}
@Composable
fun CourseCategoriesList(onCourseSelected: (Course) -> Unit) {
    val categories = getCourseCategories()
    val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }
    val context = LocalContext.current
    val userName = remember { UserSession.userName } // ή αν είναι ήδη μεταβλητή που έχεις περάσει, πάρε τη σωστά
    val earnedBadges by BadgeProgressStore.earnedBadgesFlow(context, userName).collectAsState(initial = emptySet())


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
                onCourseSelected = onCourseSelected,
                earnedBadges = earnedBadges
            )
        }
    }
}
@Composable
fun CourseCategoryItem(
    category: CourseCategory,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onCourseSelected: (Course) -> Unit,
    earnedBadges: Set<String>
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
                        painter = painterResource(R.drawable.book_open_page_variant),
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
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
                            CourseItem(course = course, onClick = { onCourseSelected(course) },
                                showBadge = earnedBadges.contains(course.id)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun CourseItem(course: Course, onClick: () -> Unit,showBadge: Boolean) {
    val levelColor = when (course.level) {
        "Beginner", "Αρχάριος" -> Color(0xFF4CAF50) // Green
        "Intermediate", "Μέσο επίπεδο" -> Color(0xFF2196F3) // Blue
        "Advanced", "Προχωρημένος" -> Color(0xFF9C27B0) // Purple
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
                if (showBadge) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("✅", fontSize = 18.sp)
                }
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
                imageVector = Icons.Default.PlayArrow,
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
    val context = LocalContext.current
    val earnedBadges by BadgeProgressStore.earnedBadgesFlow(context, userName).collectAsState(initial = emptySet())
    var showSlides by remember { mutableStateOf(false) }
    val slides = getCourseSlides(course.id)
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
                                    "Beginner", "Αρχάριος" -> Color(0xFF4CAF50) // Green
                                    "Intermediate", "Μέσο επίπεδο" -> Color(0xFF2196F3) // Blue
                                    "Advanced", "Προχωρημένος" -> Color(0xFF9C27B0) // Purple
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
                        Strings.get("about_course"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        course.description, style = MaterialTheme.typography.bodyLarge
                    )
                    if (earnedBadges.contains(course.id)) {
                        Text("✅ 100% completed", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
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




                Text(if (showSlides) Strings.get("hide_course_mat") else Strings.get("view_course_mat"))
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
                Text(Strings.get("quiz"), style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
@Composable
fun QuizScreen(
    course: Course,
    onQuizCompleted: (scorePercent: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val questions = getQuizForCourse(course.id)




    if (questions.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Strings.get("no_quiz_available"), // Make sure this is in your strings
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onQuizCompleted(0) }) {
                Text(text = Strings.get("back_course"))
            }
        }
        return
    }




    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var quizCompleted by remember { mutableStateOf(false) }




    if (quizCompleted) {
        val scorePercent = (score * 100) / questions.size
        LaunchedEffect(Unit) {




            if (scorePercent >= 80) {
                BadgeProgressStore.markBadgeEarned(context,userName, course.id)
            }
        }
        QuizResultScreen(
            score = score,
            totalQuestions = questions.size,
            onContinue = { onQuizCompleted((score * 100) / questions.size) }
        )
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            LinearProgressIndicator(
                progress = { (currentQuestionIndex + 1).toFloat() / questions.size.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
            )
            QuizQuestionCard(
                question = questions[currentQuestionIndex],
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
                }
            )
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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = resultColor
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            if (passed) Strings.get("congz") else Strings.get("try"),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = resultColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            Strings.get("score") + " $score "+ Strings.get("out") + " $totalQuestions", style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (passed) {
            Text(
                Strings.get("earned_badge"),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.trophy),
                contentDescription = "Badge earned",
                modifier = Modifier.size(80.dp)
            )
        } else {
            Text(
                Strings.get("atleast"),
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
                if (passed) Strings.get("continue_learn") else Strings.get("back_course"),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}







