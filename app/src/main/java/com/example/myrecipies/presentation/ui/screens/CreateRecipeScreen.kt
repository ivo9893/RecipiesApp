package com.example.myrecipies.presentation.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import coil.compose.AsyncImage
import com.example.myrecipies.R
import com.example.myrecipies.domain.model.Ingredient
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CreateRecipeLayout(viewModel: CreateRecipeViewModel) {

    val state = viewModel.uiState.collectAsState().value

    CreateRecipeScreen(
        state = state,
        availableUnits = state.availableUnits,
        selectedImages = state.selectedImages,
        coverImage = state.coverImage,
        onCoverImageSet = viewModel::setCoverImage,
        onImageSelected = viewModel::setSelectedImages,
        onExitClicked = {},
        onNameTextChanged = viewModel::onNameChanged,
        onDescriptionStateTextChanged = viewModel::onDescriptionChanged,
        onCookTimeMinutesChanged = viewModel::onCookTimeMinutesChanged,
        onPrepTimeMinutesChanged = viewModel::onPrepTimeMinutesChanged,
        onAddIngredient = viewModel::addIngredient,
        onRemoveIngredient = viewModel::removeIngredient,
        onToggleDialog = viewModel::setDialogVisible
    )

}

@Composable
fun CreateRecipeScreen(
    state: CreateRecipeState,
    availableUnits: List<com.example.myrecipies.domain.model.Unit>,
    selectedImages: List<Uri>,
    coverImage: Uri?,
    onCoverImageSet: (Uri) -> Unit,
    onImageSelected: (List<Uri>) -> Unit,
    onExitClicked: () -> Unit,
    onNameTextChanged: (String) -> Unit,
    onDescriptionStateTextChanged: (String) -> Unit,
    onCookTimeMinutesChanged: (Int) -> Unit,
    onPrepTimeMinutesChanged: (Int) -> Unit,
    onAddIngredient: (String, String, Int) -> Unit,
    onRemoveIngredient: (Ingredient) -> Unit,
    onToggleDialog: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Top Row: Close + Title + Confirm
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { onExitClicked() },
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = "Add Recipe",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { onExitClicked() },
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Confirm",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp) // make icon bigger
                    )
                }
            }
        }

        // Name Field
        item {
            Text(
                text = "Name",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 8.dp)
            )

            val nameState = rememberTextFieldState(state.name)
            LaunchedEffect(nameState) {
                snapshotFlow { nameState.text.toString() }.collectLatest {
                    onNameTextChanged(it)
                }
            }
            TextField(
                nameState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Description Field
        item {
            Text(
                text = "Description",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )

            val descriptionState = rememberTextFieldState(state.description)
            LaunchedEffect(descriptionState) {
                snapshotFlow { descriptionState.text.toString() }.collectLatest {
                    onDescriptionStateTextChanged(it)
                }
            }
            TextField(
                descriptionState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(elevation = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 6)
            )
        }

        // Minutes Fields
        item {
            MinutesField(
                value = state.prepTimeMinutes,
                onValueChange = onPrepTimeMinutesChanged,
                text = "Prep time (minutes)"
            )
            MinutesField(
                value = state.cookTimeMinutes,
                onValueChange = onCookTimeMinutesChanged,
                text = "Cook time (minutes)"
            )
        }

        // Multiple Images Picker
        item {
            MultipleImagePicker(
                selectedImages = selectedImages,
                coverImage = coverImage,
                onCoverImageSet = onCoverImageSet,
                onImagesSelected = onImageSelected
            )
        }

        // Ingredients List
        item {
            IngredientList(
                ingredients = state.ingredients,
                availableUnits = availableUnits,
                showDialog = state.showDialog,
                onAddIngredient = onAddIngredient,
                onRemoveIngredient = onRemoveIngredient,
                onToggleDialog = onToggleDialog
            )
        }
    }
}

//@Composable
//fun CreateRecipeScreen(state: CreateRecipeState,
//                       availableUnits : List<com.example.myrecipies.domain.model.Unit>,
//                       selectedImages: List<Uri>,
//                       coverImage: Uri?,
//                       onCoverImageSet: (Uri) -> Unit,
//                       onImageSelected: (List<Uri>) -> Unit,
//                       onExitClicked : () -> Unit,
//                       onNameTextChanged : (name : String) -> Unit,
//                       onDescriptionStateTextChanged : (description : String) -> Unit,
//                       onCookTimeMinutesChanged : (cookTimeMinutes : Int) -> Unit,
//                       onPrepTimeMinutesChanged : (prepTimeMinutes : Int) -> Unit,
//                       onAddIngredient : (name: String, qty: String, unit: Int) -> Unit,
//                       onRemoveIngredient: (Ingredient) -> Unit,
//                       onToggleDialog: (Boolean) -> Unit){
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            IconButton(
//                onClick = { onExitClicked() },
//                modifier = Modifier
//                    .size(24.dp)
//                    .background(
//                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
//                        shape = CircleShape
//                    )
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Close,
//                    contentDescription = "Close",
//                    tint = MaterialTheme.colorScheme.onSurface
//                )
//            }
//
//            Text(
//                text = "Add Recipe",
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.weight(1f)
//            )
//
//            IconButton(
//                onClick = { onExitClicked() },
//                modifier = Modifier.size(24.dp)
//                    .background(
//                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
//                        shape = CircleShape
//                    )
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.check),
//                    contentDescription = "Confirm",
//                    tint = MaterialTheme.colorScheme.onSurface,
//                    modifier = Modifier.size(24.dp) // make icon bigger
//                )
//            }
//        }
//
//        Text(
//            text = "Name",
//            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 8.dp)
//            )
//
//        val nameState = rememberTextFieldState(state.name)
//        LaunchedEffect (nameState) {
//            snapshotFlow { nameState.text.toString() }.collectLatest {
//                onNameTextChanged(it)
//            }
//        }
//        TextField(
//            nameState,
//            modifier = Modifier.fillMaxWidth()
//                .padding(horizontal = 16.dp)
//                .shadow(elevation = 6.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = MaterialTheme.colorScheme.surface,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            )
//        )
//
//        Text(
//            text = "Description",
//            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
//        )
//
//        val descriptionState = rememberTextFieldState(state.description)
//        LaunchedEffect (descriptionState) {
//            snapshotFlow { descriptionState.text.toString() }.collectLatest {
//                onDescriptionStateTextChanged(it)
//            }
//        }
//        TextField(
//            descriptionState,
//            modifier = Modifier.fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//                .shadow(elevation = 6.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = MaterialTheme.colorScheme.surface,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            ),
//            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 6)
//        )
//
//
//        MinutesField(value = state.prepTimeMinutes, onValueChange = {onPrepTimeMinutesChanged(it)}, text = "Prep time (minutes)")
//        MinutesField(value = state.cookTimeMinutes, onValueChange = {onCookTimeMinutesChanged(it)}, text = "Cook time (minutes)")
//
//        MultipleImagePicker(selectedImages = selectedImages, onImagesSelected = onImageSelected, coverImage = coverImage, onCoverImageSet = onCoverImageSet)
//
//        IngredientList(ingredients = state.ingredients, showDialog = state.showDialog, onAddIngredient = onAddIngredient, onRemoveIngredient = onRemoveIngredient, onToggleDialog = onToggleDialog, availableUnits = availableUnits)
//    }
//}

@Composable
fun IngredientList(
    ingredients: List<Ingredient>,
    availableUnits: List<com.example.myrecipies.domain.model.Unit>,
    showDialog: Boolean,
    onAddIngredient: (name: String, qty: String, unit: Int) -> Unit,
    onRemoveIngredient: (Ingredient) -> Unit,
    onToggleDialog: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.bodyMedium
            )

            IconButton(
                onClick = { onToggleDialog(true) },
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))

        ingredients.forEach { ingredient ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text("Ingredient", style = MaterialTheme.typography.labelSmall)
                        Text(ingredient.name, style = MaterialTheme.typography.bodyLarge)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Qty", style = MaterialTheme.typography.labelSmall)
                        Text(ingredient.quantity, style = MaterialTheme.typography.bodyLarge)
                    }
                    Column(modifier = Modifier.weight(1f)) {

                        Text("Unit", style = MaterialTheme.typography.labelSmall)

                        val unitName = availableUnits.find { it.id == ingredient.unit }?.name ?: "?"

                        Text(text = unitName, style = MaterialTheme.typography.bodyLarge)
                    }
                    IconButton(
                        onClick = { onRemoveIngredient(ingredient) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

            }
        }
    }

    if (showDialog) {
        AddIngredientDialog(onDismiss = { onToggleDialog(false) }, onAdd = { name, qty, unit ->
            //ingredients = ingredients + Ingredient(name, qty, unit)
            onAddIngredient(name, qty, unit)
            onToggleDialog(false)
        }, availableUnits = availableUnits)
    }
}


@Composable
fun MultipleImagePicker(
    selectedImages: List<Uri>,
    onImagesSelected: (List<Uri>) -> Unit,
    coverImage: Uri?,
    onCoverImageSet: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            val updated = (selectedImages + uris).distinct()
            onImagesSelected(updated)
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        // 🔹 Header with "Remove All"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Images", style = MaterialTheme.typography.titleMedium)

            if (selectedImages.isNotEmpty()) {
                TextButton(onClick = { onImagesSelected(emptyList()) }) {
                    Text(
                        "Remove All",
                        color = MaterialTheme.colorScheme.error // 🔴 red text for destructive action
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedImages.isEmpty()) {
            // Empty state -> big clickable box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tap to select images",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(selectedImages) { uri ->
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { onCoverImageSet(uri) }
                        )

                        // Cover overlay
                        if (coverImage == uri) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color.Black.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.TopStart
                            ) {
                                Text(
                                    text = "Cover",
                                    color = Color.White,
                                    modifier = Modifier.padding(6.dp),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        // 🔴 Smaller red delete button
                        IconButton(
                            onClick = {
                                val updated = selectedImages - uri
                                onImagesSelected(updated)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-6).dp, y = 6.dp) // ✅ Moves it inward so it's not clipped
                                .size(12.dp) // Slightly bigger so it's easier to tap
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove image",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp) // adjusted icon size
                            )
                        }
                    }
                }

                // "Add" card
                item {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Images",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Add",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddIngredientDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, qty: String, unit: Int) -> Unit,
    availableUnits: List<com.example.myrecipies.domain.model.Unit>
) {
    var name by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }


    var expanded by remember { mutableStateOf(false) }
    var selectedUnit by remember { mutableStateOf(availableUnits.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Ingredient") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Ingredient Name") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = qty,
                    onValueChange = { qty = it },
                    label = { Text("Quantity") },
                    singleLine = true
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedUnit.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Unit of Measurement") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        availableUnits.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit.name) },
                                onClick = {
                                    selectedUnit = unit
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && qty.isNotBlank() && selectedUnit.name.isNotBlank()) {
                        onAdd(name, qty, selectedUnit.id)
                    }
                }
            ) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}


@Composable
fun MinutesField(
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int = 0,
    maxValue: Int = 3000,
    text: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp)
            ) {

                val valueState = rememberTextFieldState(value.toString())
                LaunchedEffect(valueState) {
                    snapshotFlow { valueState.text.toString() }.collectLatest {
                        it.toIntOrNull()?.let { newValue -> onValueChange(newValue) }
                    }
                }

                IconButton(
                    onClick = {
                        if (value > minValue) {
                            val newValue = value - 1
                            // ✅ Update both states:
                            valueState.edit {
                                replace(0, valueState.text.length, newValue.toString())
                            }
                            onValueChange(newValue)
                        }
                    },
                    enabled = value < maxValue
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = "Decrease",
                        modifier = Modifier.size(12.dp)
                    )
                }



                BasicTextField(
                    state = valueState,
                    modifier = Modifier.width(20.dp),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    ),
                    lineLimits = TextFieldLineLimits.SingleLine
                )

                IconButton(
                    onClick = {
                        if (value < maxValue) {
                            val newValue = value + 1
                            // ✅ Update both states:
                            valueState.edit {
                                replace(0, valueState.text.length, newValue.toString())
                            }
                            onValueChange(newValue)
                        }
                    },
                    enabled = value < maxValue
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.up),
                        contentDescription = "Increase",
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreateRecipeLayoutPreview() {

    val state = CreateRecipeState()

    CreateRecipeScreen(
        state,
        availableUnits = emptyList(),
        selectedImages = emptyList(),
        coverImage = null,
        onCoverImageSet = {},
        onImageSelected = {},
        onExitClicked = {},
        onNameTextChanged = {},
        onDescriptionStateTextChanged = {},
        onCookTimeMinutesChanged = {},
        onPrepTimeMinutesChanged = {},
        onAddIngredient = { _, _, _ -> },
        onRemoveIngredient = {},
        onToggleDialog = {}
    )
}
