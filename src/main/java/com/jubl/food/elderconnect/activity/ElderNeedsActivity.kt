package com.jubl.food.elderconnect.activity

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jubl.food.elderconnect.ChecklistItem
import com.jubl.food.elderconnect.loadChecklist
import com.jubl.food.elderconnect.saveChecklist
import com.jubl.food.elderconnect.ui.theme.ElderConnectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ElderNeedsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ElderConnectTheme {
                ChecklistScreen()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    // Load checklist items from DataStore
    val checklistItemsFlow = loadChecklist(context)
    var checklistItems by remember { mutableStateOf(emptyList<ChecklistItem>()) }

    // Observe the checklist items
    LaunchedEffect(Unit) {
        checklistItemsFlow.collect { items ->
            checklistItems = items
        }
    }

    // State for the new item text field
    var newItemText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEEDF1))
            .padding(top = 100.dp, start = 10.dp)
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Text(
            "Needs For Elders",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(checklistItems.size) { index ->
                ChecklistItemRow(
                    item = checklistItems[index],
                    onCheckedChange = { isChecked ->
                        val updatedChecklist = checklistItems.toMutableList().apply {
                            this[index] = this[index].copy(isChecked = isChecked)
                        }
                        checklistItems = updatedChecklist
                        scope.launch { saveChecklist(context, updatedChecklist) }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newItemText,
                onValueChange = { newItemText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Add new item") },
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        addItemToChecklist(newItemText, checklistItems, context, scope, focusManager) { updatedChecklist ->
                            checklistItems = updatedChecklist
                            newItemText = ""
                        }
                    }
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                addItemToChecklist(newItemText, checklistItems, context, scope, focusManager) { updatedChecklist ->
                    checklistItems = updatedChecklist
                    newItemText = ""
                }
            }) {
                Text("Add")
            }
        }
    }
}

// Helper function to add a new item and update DataStore
private fun addItemToChecklist(
    newItemText: String,
    checklistItems: List<ChecklistItem>,
    context: Context,
    scope: CoroutineScope,
    focusManager: FocusManager,
    onListUpdated: (List<ChecklistItem>) -> Unit
) {
    if (newItemText.isNotBlank()) {
        val updatedChecklist = checklistItems + ChecklistItem(text = newItemText.trim())
        scope.launch { saveChecklist(context, updatedChecklist) }
        onListUpdated(updatedChecklist)
        focusManager.clearFocus()
    }
}

@Composable
fun ChecklistItemRow(item: ChecklistItem, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckedChange,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.text.trim(),
            fontSize = 18.sp,
        )
    }
}