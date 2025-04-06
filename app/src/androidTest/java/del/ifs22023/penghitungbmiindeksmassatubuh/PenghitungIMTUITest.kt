package del.ifs22023.penghitungbmiindeksmassatubuh

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PenghitungIMTUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCalculateBMI() {
        composeTestRule.setContent {
            PenghitungBMIIndeksMassaTubuhLayout()
        }

        // Masukkan berat badan
        composeTestRule.onNodeWithText("Berat Badan").performTextInput("70")
        // Masukkan tinggi badan dalam cm
        composeTestRule.onNodeWithText("Tinggi Badan").performTextInput("170")

        // Pastikan hasil IMT yang benar muncul
        val expectedBMI = String.format("%.2f", 70 / (1.7 * 1.7))
        composeTestRule.onNodeWithText("Indeks Massa Tubuh (IMT):").assertExists()
        composeTestRule.onNodeWithText(expectedBMI).assertExists()
    }

    @Test
    fun testShowBMICategory() {
        composeTestRule.setContent {
            PenghitungBMIIndeksMassaTubuhLayout()
        }

        // Masukkan data
        composeTestRule.onNodeWithText("Berat Badan").performTextInput("50")
        composeTestRule.onNodeWithText("Tinggi Badan").performTextInput("170")

        // Aktifkan switch kategori BMI
        composeTestRule.onNodeWithText("Tampilkan Kategori BMI").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("17.30", substring = true).assertExists()
    }
}
