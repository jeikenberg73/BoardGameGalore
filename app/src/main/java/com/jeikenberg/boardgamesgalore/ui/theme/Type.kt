package com.jeikenberg.boardgamesgalore.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.jeikenberg.boardgamesgalore.R

// Set of Material typography styles to start with
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val almendraFont = GoogleFont("Almendra")

val interFont = GoogleFont("Inter")

val AlmendraFontFamily = FontFamily(
    Font(
        googleFont = almendraFont,
        fontProvider = provider
    ),
    Font(
        resId = R.font.almendra_regular
    ),
    Font(
        googleFont = almendraFont,
        fontProvider = provider,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.almendra_bold,
        weight = FontWeight.Bold
    ),
    Font(
        googleFont = almendraFont,
        fontProvider = provider,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.almendra_italic,
        weight = FontWeight.Bold
    ),
    Font(
        googleFont = almendraFont,
        fontProvider = provider,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.almendra_bolditalic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    )
)

val InterFontFamily = FontFamily(
    Font(
        googleFont = interFont,
        fontProvider = provider
    ),
    Font(
        resId = R.font.inter_regular
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.inter_bold,
        weight = FontWeight.Bold
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.Black
    ),
    Font(
        resId = R.font.inter_black,
        weight = FontWeight.Black
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.ExtraBold
    ),
    Font(
        resId = R.font.inter_extrabold,
        weight = FontWeight.ExtraBold
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.ExtraLight
    ),
    Font(
        resId = R.font.inter_extralight,
        weight = FontWeight.ExtraLight
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.inter_light,
        weight = FontWeight.Light
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.inter_medium,
        weight = FontWeight.Medium
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.inter_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        googleFont = interFont,
        fontProvider = provider,
        weight = FontWeight.Thin
    ),
    Font(
        resId = R.font.inter_thin,
        weight = FontWeight.Thin
    )
)



val GameTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AlmendraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)