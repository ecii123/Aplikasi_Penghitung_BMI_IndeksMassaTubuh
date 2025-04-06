package del.ifs22023.penghitungbmiindeksmassatubuh

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import del.ifs22023.penghitungbmiindeksmassatubuh.ui.theme.PenghitungBMIIndeksMassaTubuhTheme
import org.jetbrains.annotations.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PenghitungBMIIndeksMassaTubuhTheme {
                PenghitungBMIIndeksMassaTubuhLayout()
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PenghitungBMIIndeksMassaTubuhLayout() {
    var weightInput by remember { mutableStateOf("") } // Input berat badan
    var heightInput by remember { mutableStateOf("") } // Input tinggi badan
    val weight = weightInput.toDoubleOrNull() ?: 0.0
    val height = heightInput.toDoubleOrNull()?.div(100) ?: 0.0 // Konversi tinggi cm ke meter

    var showCategory by remember { mutableStateOf(false) } // Status Switch
    val bmi = calculateBMI(weight, height) // Menghitung BMI

    // Menentukan kategori BMI
    val bmiCategory = when (bmi) {
        in 0.0..18.4 -> "Kurus"
        in 18.5..24.9 -> "Normal"
        else -> "Gemuk"
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.frame_2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Konten utama
        Column(
            modifier = Modifier
                .padding(40.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(250.dp))

            // Input berat badan
            EditNumberField(
                label = R.string.berat_badan,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = weightInput,
                onValueChanged = { weightInput = it },
                leadingIcon = R.drawable.berat,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
            )

            // Input tinggi badan
            EditNumberField(
                label = R.string.tinggi_badan,
                leadingIcon = R.drawable.tinggi,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = heightInput,
                onValueChanged = { heightInput = it },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
            )

            // Switch untuk menampilkan informasi tambahan
            RoundTheTipRow(
                roundUp = showCategory,
                onRoundUpChanged = { showCategory = it },
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Menampilkan nilai BMI
            Text(
                text = "Indeks Massa Tubuh (IMT):",
                color = Color(0xFF90A1C1), // Warna biru tua
                fontWeight = FontWeight.Bold, // Membuat teks tebal
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 25.sp
                )
            )
            Text(
                text = String.format("%.2f", bmi),
                fontWeight = FontWeight.Bold, // Membuat teks tebal
                fontSize = 30.sp, // Ukuran teks
                color = Color(0xFF90A1C1), // Warna oranye
                modifier = Modifier.padding(top = 8.dp) // Menambahkan jarak dari teks di atasnya
            )

            // Menampilkan gambar sesuai kategori BMI jika Switch aktif
            if (showCategory) {
                Spacer(modifier = Modifier.height(16.dp))
                val imageRes = when (bmiCategory) {
                    "Kurus" -> R.drawable.kurus
                    "Normal" -> R.drawable.normal
                    else -> R.drawable.gemuk
                }

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = bmiCategory,
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

// Fungsi perhitungan BMI yang benar
@VisibleForTesting
internal fun calculateBMI(weight: Double, height: Double): Double {
    return if (height > 0) weight / (height * height) else 0.0
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        label = { Text(stringResource(label)) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFD4E8F4), // Biru muda lebih terang saat tidak fokus
            focusedContainerColor = Color(0xFFD4E8F4), // Warna utama saat fokus
            unfocusedIndicatorColor = Color(0xFFD4E8F4), // Border biru keabu-abuan saat tidak fokus
            focusedIndicatorColor = Color(0xFFD4E8F4) // Border biru lebih tegas saat fokus
        )
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Tampilkan Kategori BMI")
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun  PenghitungBMIIndeksMassaTubuhLayoutPreview() {
    PenghitungBMIIndeksMassaTubuhTheme {
        PenghitungBMIIndeksMassaTubuhLayout()
    }
}