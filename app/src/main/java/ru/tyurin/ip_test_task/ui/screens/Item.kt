package ru.tyurin.ip_test_task.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ru.tyurin.ip_test_task.R
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity
import ru.tyurin.ip_test_task.ui.viewmodel.GadgetViewModel
import java.util.Locale

@Composable
fun ItemListScreen(viewModel: GadgetViewModel = hiltViewModel()) {
    val gadgets by viewModel.gadgets.collectAsState()
    var searchText by remember { mutableStateOf(viewModel.getSearchQuery()) }
    val selectedGadgetForEdit by viewModel.selectedGadgetForEdit.collectAsState()
    val selectedGadgetForDelete by viewModel.selectedGadgetForDelete.collectAsState()

    LaunchedEffect(searchText) {
        viewModel.setSearchQuery(searchText)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                viewModel.setSearchQuery(newText)
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search_icon))
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(R.string.cancel))
                    }
                }
            },
            placeholder = { Text(text = stringResource(R.string.search_item)) },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(gadgets) { gadget ->
                GadgetItem(
                    gadget = gadget,
                    onEditClick = { viewModel.setSelectedGadgetForEdit(gadget) },
                    onDeleteClick = { viewModel.setSelectedGadgetForDelete(gadget) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    selectedGadgetForEdit?.let { gadget ->
        EditGadgetDialog(
            gadget = gadget,
            onDismiss = { viewModel.setSelectedGadgetForEdit(null) },
            onSave = { editedGadget ->
                viewModel.editGadget(editedGadget)
                viewModel.setSelectedGadgetForEdit(null)
            }
        )
    }

    selectedGadgetForDelete?.let { gadget ->
        DeleteConfirmationDialog(
            onDismiss = { viewModel.setSelectedGadgetForDelete(null) },
            onConfirmDelete = {
                viewModel.removeGadget(gadget)
                viewModel.setSelectedGadgetForDelete(null)
            }
        )
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GadgetItem(
    gadget: GadgetEntity,
    onEditClick: () -> Unit,
    onDeleteClick: (GadgetEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.width(200.dp)){
                    Text(
                        text = gadget.name,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row {
                    IconButton(onClick = { onEditClick() }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFF6200EA)
                        )
                    }
                    IconButton(onClick = { onDeleteClick(gadget) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFFF5722)
                        )
                    }
                }
            }

            if (gadget.tags.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    gadget.tags.forEach { tag ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text(text = tag) },
                            border = BorderStroke(1.dp, Color.Gray),
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = Color.Transparent
                            )
                        )
                    }
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.in_stock),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${gadget.amount}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column {
                    Text(
                        text = stringResource(R.string.add_date),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = gadget.time.toLong().toFormattedDate(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

fun Long.toFormattedDate(): String {
    val sdf = java.text.SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return sdf.format(java.util.Date(this * 1000))
}
@Composable
fun DeleteConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.delete_item_title),
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.confirm_delete),
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(onClick = {
                        onConfirmDelete()
                    }) {
                        Text(stringResource(R.string.delete_item))
                    }
                }
            }
        }
    }
}

@Composable
fun EditGadgetDialog(
    gadget: GadgetEntity,
    onDismiss: () -> Unit,
    onSave: (GadgetEntity) -> Unit
) {
    var amount by remember { mutableIntStateOf(gadget.amount) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.item_quantity),
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        if (amount > 0) {
                            amount -= 1
                        }
                    }) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }

                    Text(text = amount.toString(), fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))

                    IconButton(onClick = {
                        amount += 1
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(onClick = {
                        onSave(gadget.copy(amount = amount))
                    }) {
                        Text(stringResource(R.string.accept))
                    }
                }
            }
        }
    }
}

