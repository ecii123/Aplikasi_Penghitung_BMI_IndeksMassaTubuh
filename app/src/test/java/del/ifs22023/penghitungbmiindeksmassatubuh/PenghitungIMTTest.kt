package del.ifs22023.penghitungbmiindeksmassatubuh

import org.junit.Assert.assertEquals
import org.junit.Test

class PenghitungIMTTest {

    @Test
    fun calculateBMI_validInputs_returnsCorrectBMI() {
        val weight = 70.0 // kg
        val height = 1.75 // meters
        val expectedBMI = 70.0 / (1.75 * 1.75)
        val actualBMI = calculateBMI(weight, height)
        assertEquals(expectedBMI, actualBMI, 0.01) // Delta 0.01 untuk toleransi perbedaan
    }

    @Test
    fun calculateBMI_zeroHeight_returnsZero() {
        val weight = 70.0
        val height = 0.0
        val actualBMI = calculateBMI(weight, height)
        assertEquals(0.0, actualBMI, 0.0)
    }

    @Test
    fun calculateBMI_negativeHeight_returnsZero() {
        val weight = 70.0
        val height = -1.75
        val actualBMI = calculateBMI(weight, height)
        assertEquals(0.0, actualBMI, 0.0)
    }

    @Test
    fun calculateBMI_zeroWeight_returnsZero() {
        val weight = 0.0
        val height = 1.75
        val actualBMI = calculateBMI(weight, height)
        assertEquals(0.0, actualBMI, 0.0)
    }
}