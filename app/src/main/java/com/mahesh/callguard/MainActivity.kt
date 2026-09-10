package com.mahesh.callguard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.mahesh.callguard.data.ContactsRepository
import com.mahesh.callguard.model.Contact
import com.mahesh.callguard.ui.theme.CallGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CallGuardTheme {
                CallGuardApp()
            }
        }
    }
}

// Root composable — decides which screen is currently visible
@Composable
fun CallGuardApp() {
    var showContactSelection by remember { mutableStateOf(false) }
    var importantContacts by remember { mutableStateOf<List<Contact>>(emptyList()) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (showContactSelection) {
            ContactSelectionScreen(
                currentSelections = importantContacts,
                onConfirm = { selected ->
                    importantContacts = selected
                    showContactSelection = false
                },
                onDismiss = { showContactSelection = false },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            CallGuardHomeScreen(
                importantContacts = importantContacts,
                onAddContactsClick = { showContactSelection = true },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun CallGuardHomeScreen(
    importantContacts: List<Contact>,
    onAddContactsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isProtectionEnabled by remember { mutableStateOf(false) }

    val contactPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onAddContactsClick()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CallGuard",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (isProtectionEnabled) "Protection: ON" else "Protection: OFF",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(10.dp))
            Switch(
                checked = isProtectionEnabled,
                onCheckedChange = { isProtectionEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Important Contacts",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (importantContacts.isEmpty()) {
            Text(text = "No contacts selected")
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(importantContacts) { contact ->
                    ImportantContactItem(contact = contact)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_CONTACTS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    onAddContactsClick()
                } else {
                    contactPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }
            }
        ) {
            Text("Add Important Contacts")
        }
    }
}

@Composable
fun ImportantContactItem(contact: Contact) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = contact.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = contact.phoneNumber, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ContactSelectionScreen(
    currentSelections: List<Contact>,
    onConfirm: (List<Contact>) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var allContacts by remember { mutableStateOf<List<Contact>>(emptyList()) }
    var selectedIds by remember { mutableStateOf(currentSelections.map { it.id }.toSet()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        allContacts = ContactsRepository.loadContacts(context)
        isLoading = false
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Select Contacts",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = {
                    val selected = allContacts.filter { it.id in selectedIds }
                    onConfirm(selected)
                }
            ) {
                Text("Done")
            }
        }

        HorizontalDivider()

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(allContacts) { contact ->
                    ContactSelectionItem(
                        contact = contact,
                        isSelected = contact.id in selectedIds,
                        onToggle = {
                            selectedIds = if (contact.id in selectedIds) {
                                selectedIds - contact.id
                            } else {
                                selectedIds + contact.id
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun ContactSelectionItem(
    contact: Contact,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onToggle() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = contact.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = contact.phoneNumber, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallGuardPreview() {
    CallGuardTheme {
        CallGuardHomeScreen(
            importantContacts = emptyList(),
            onAddContactsClick = {}
        )
    }
}