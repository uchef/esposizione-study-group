package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ui.StudyTheme

private val CosmicDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = DeepBlack,
    secondary = GoldDark,
    onSecondary = DeepBlack,
    tertiary = GoldLight,
    background = DeepBlack,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = CardDark,
    onSurfaceVariant = TextSecondary,
    outline = BorderDark
)

private val ParchmentWarmColorScheme = lightColorScheme(
    primary = Color(0xFF8B4513), // Bronze brown
    onPrimary = Color(0xFFFAF4EB),
    secondary = Color(0xFFA0522D),
    onSecondary = Color(0xFFFAF4EB),
    tertiary = Color(0xFFCD853F),
    background = Color(0xFFFAF4EB), // Warm parchment
    onBackground = Color(0xFF2C1E14), // Dark espresso brown text
    surface = Color(0xFFF3EAD8),
    onSurface = Color(0xFF2C1E14),
    surfaceVariant = Color(0xFFE9DCBF),
    onSurfaceVariant = Color(0xFF5D4037),
    outline = Color(0xFFD7CCC8)
)

private val OliveForestColorScheme = darkColorScheme(
    primary = Color(0xFF8DA38B), // Sage green
    onPrimary = Color(0xFF0F1511),
    secondary = Color(0xFF6E8B6E),
    onSecondary = Color(0xFF0F1511),
    tertiary = Color(0xFFB4C8B4),
    background = Color(0xFF0F1511), // Midnight Forest
    onBackground = Color(0xFFE2EBE2),
    surface = Color(0xFF1B241F),
    onSurface = Color(0xFFE2EBE2),
    surfaceVariant = Color(0xFF27342D),
    onSurfaceVariant = Color(0xFFB4C8B4),
    outline = Color(0x22FFFFFF)
)

private val IvoryLightColorScheme = lightColorScheme(
    primary = Color(0xFF5C6BC0), // Elegant indigo
    onPrimary = Color.White,
    secondary = Color(0xFF3F51B5),
    onSecondary = Color.White,
    tertiary = Color(0xFF9FA8DA),
    background = Color(0xFFFCFAF7), // Soft ivory
    onBackground = Color(0xFF1A1A1A),
    surface = Color(0xFFF4F1EC),
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFEBE6DF),
    onSurfaceVariant = Color(0xFF4A4A4A),
    outline = Color(0xFFD0C8BF)
)

@Composable
fun LuminaStudyTheme(
    studyTheme: StudyTheme = StudyTheme.COSMIC_DARK,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (studyTheme) {
        StudyTheme.COSMIC_DARK -> CosmicDarkColorScheme
        StudyTheme.PARCHMENT_WARM -> ParchmentWarmColorScheme
        StudyTheme.OLIVE_FOREST -> OliveForestColorScheme
        StudyTheme.IVORY_LIGHT -> IvoryLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
