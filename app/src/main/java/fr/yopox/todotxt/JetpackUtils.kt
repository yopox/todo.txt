package fr.yopox.todotxt

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

object JetpackUtils {
    val jbMono = FontFamily(
        Font(R.font.jbm_light, FontWeight.Light),
        Font(R.font.jbm_regular, FontWeight.Normal),
        Font(R.font.jbm_bold, FontWeight.Bold),
        Font(R.font.jbm_extrabold, FontWeight.ExtraBold)
    )

    @Composable
    fun getScheme(): ColorScheme {
        val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        val darkTheme = isSystemInDarkTheme()
        return when {
            dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
            dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
            darkTheme -> darkColorScheme()
            else -> lightColorScheme()
        }
    }
}