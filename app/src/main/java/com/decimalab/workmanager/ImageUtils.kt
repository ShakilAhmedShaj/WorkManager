package com.decimalab.workmanager

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.min

object ImageUtils {

    //grayscale multipliers
    private const val GRAYSCALE_RED = 0.3
    private const val GRAYSCALE_GREEN = 0.59
    private const val GRAYSCALE_BLUE = 0.11

    private const val MAX_COLOR = 255

    private const val SEPIA_TONE_RED = 110
    private const val SEPIA_TONE_GREEN = 65
    private const val SEPIA_TONE_BLUE = 20

    fun applySepiaFilter(bitmap: Bitmap): Bitmap {
        // image size
        val width = bitmap.width
        val height = bitmap.height

        // create output bitmap
        val outputBitmap = Bitmap.createBitmap(width, height, bitmap.config)

        // color information
        var alpha: Int
        var red: Int
        var green: Int
        var blue: Int
        var currentPixel: Int

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {

                // get pixel color
                currentPixel = bitmap.getPixel(x, y)

                // get color on each channel
                alpha = Color.alpha(currentPixel)
                red = Color.red(currentPixel)
                green = Color.green(currentPixel)
                blue = Color.blue(currentPixel)

                // apply grayscale sample
                red =
                    (GRAYSCALE_RED * red + GRAYSCALE_GREEN * green + GRAYSCALE_BLUE * blue).toInt()
                green = red
                blue = green

                // apply intensity level for sepid-toning on each channel
                red += SEPIA_TONE_RED
                green += SEPIA_TONE_GREEN
                blue += SEPIA_TONE_BLUE

                //if you overflow any color, set it to MAX (255)
                red = min(red, MAX_COLOR)
                green = min(green, MAX_COLOR)
                blue = min(blue, MAX_COLOR)

                outputBitmap.setPixel(x, y, Color.argb(alpha, red, green, blue))
            }
        }

        bitmap.recycle()

        return outputBitmap
    }
}