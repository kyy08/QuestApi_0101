package com.example.pam_questapi.ui.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_questapi.ui.ViewModel.InsertUiEvent
import com.example.pam_questapi.ui.ViewModel.InsertUiState
import com.example.pam_questapi.ui.ViewModel.InsertViewModel
import com.example.pam_questapi.ui.Widget.CostumeTopAppBar
import com.example.pam_questapi.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMhsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onSiswaValueChange = { updatedEvent ->
                viewModel.updateInsertMhsState { currentEvent ->
                    currentEvent.copy(
                        nim = updatedEvent.nim,
                        nama = updatedEvent.nama,
                        alamat = updatedEvent.alamat,
                        jenisKelamin = updatedEvent.jenisKelamin,
                        kelas = updatedEvent.kelas,
                        angkatan = updatedEvent.angkatan
                    )
                }
            },
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.isValidInput()) { // Tambahkan validasi
                        viewModel.insertMhs()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onSiswaValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}