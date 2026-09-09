package com.mahesh.callguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mahesh.callguard.ui.theme.CallGuardTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Switch

//Contacts permission
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

//Permission Dialog
import androidx.compose.ui.platform.LocalContext
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CallGuardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CallGuardHomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CallGuardHomeScreen(modifier: Modifier = Modifier) {

    var isProtectionEnabled by remember {
        mutableStateOf(false)
    }

    var showContactSelection by remember {
        mutableStateOf(false)
    }

    val contactPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showContactSelection = true
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text="CallGuard",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isProtectionEnabled) {
                    "Protection: ON"
                } else {
                    "Protection: OFF"
                },
                style=MaterialTheme.typography.titleMedium
            )
            Spacer(modifier=Modifier.width(10.dp))

            Switch(
                checked = isProtectionEnabled,
                onCheckedChange={ enabled ->
                    isProtectionEnabled = enabled
                }
            )
        }

        Spacer(modifier=Modifier.height(40.dp))

        Text(
            text = "Selected contacts",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No contacts selected"
        )

        Spacer(modifier = Modifier.height(40.dp))

        val context = LocalContext.current
        Button(
            onClick={
                if (
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_CONTACTS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    showContactSelection = true
                } else {
                    contactPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }
            }
        ){
            Text("Add important contacts")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallGuardPreview() {
    CallGuardTheme {
        CallGuardHomeScreen()
    }
}