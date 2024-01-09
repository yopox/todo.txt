package fr.yopox.todotxt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkStoragePermission()
        TasksData.reload()
        setContent {
            val theme = JetpackUtils.getScheme()

            SideEffect {
                window.statusBarColor = theme.background.toArgb()
            }

            BottomNavigationItem().BottomNavigationBar()
        }
    }

    private fun checkStoragePermission() {
        if (!Environment.isExternalStorageManager()) {
            val intent = Intent(
                ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.parse("package:$packageName")
            )
            val storagePermissionResultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (!Environment.isExternalStorageManager()) {
                        finishAffinity()
                    }
                }
            storagePermissionResultLauncher.launch(intent)
        }
    }
}