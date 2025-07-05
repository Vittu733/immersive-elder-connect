package com.jubl.food.elderconnect.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jubl.food.elderconnect.ChecklistItem
import com.jubl.food.elderconnect.ContactRow
import com.jubl.food.elderconnect.YouTubeVideo
import com.jubl.food.elderconnect.loadContacts
import com.jubl.food.elderconnect.saveContacts
import com.jubl.food.elderconnect.ui.theme.ElderConnectTheme
import kotlinx.coroutines.launch

class NGODetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ElderConnectTheme {
                ContactTable()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactTable() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    // Load contact list from DataStore
    val contactListFlow = loadContacts(context)
    var contactList by remember { mutableStateOf(emptyList<ContactRow>()) }

    // Observe the contact list
    LaunchedEffect(Unit) {
        contactListFlow.collect { contacts ->
            contactList = contacts
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEEDF1))
            .padding(top = 100.dp, start = 10.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Text(
            "NGO's Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text("Organization Name", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight(700))
            Text("Contact", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight(700))
        }

        HorizontalDivider()

        // Table Rows
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(contactList.size) { index ->
                ContactTableRow(
                    row = contactList[index],
                    onContactChange = { updatedRow ->
                        contactList = contactList.toMutableList().apply {
                            this[index] = updatedRow
                        }
                        scope.launch { saveContacts(context, contactList) }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Row Button
        Button(
            onClick = {
                contactList = contactList + ContactRow()
                scope.launch { saveContacts(context, contactList) }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp)
        ) {
            Text("Add New NGO")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactTableRow(
    row: ContactRow,
    onContactChange: (ContactRow) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var name by remember { mutableStateOf(row.name) }
    var contact by remember { mutableStateOf(row.contact) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Name Column
        TextField(
            value = name,
            onValueChange = {
                name = it
                onContactChange(row.copy(name = it))
            },
            placeholder = { Text("Enter NGO Name") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = if (name.isEmpty()) Color(0xFFCCFFCC) else Color.White
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Contact Column
        TextField(
            value = contact,
            onValueChange = {
                contact = it
                onContactChange(row.copy(contact = it))
            },
            placeholder = { Text("Enter NGO Contact") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = if (contact.isEmpty()) Color(0xFFCCFFCC) else Color.White
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier.weight(1f)
        )
    }
}