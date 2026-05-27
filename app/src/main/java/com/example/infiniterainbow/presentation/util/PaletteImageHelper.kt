package com.example.infiniterainbow.presentation.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.FileProvider
import com.example.infiniterainbow.domain.usecase.GetColorPaletteUseCase
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.createBitmap

object PaletteImageHelper {

    fun sharePalette(context: Context, palette: GetColorPaletteUseCase.ColorPalette, mainColorName: String) {
        val bitmap = generatePaletteBitmap(palette, mainColorName)
        val uri = saveBitmapToCache(context, bitmap)
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share Palette"))
    }

    private fun generatePaletteBitmap(palette: GetColorPaletteUseCase.ColorPalette, mainColorName: String): Bitmap {
        val width = 1080
        val height = 1650
        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw background
        canvas.drawColor(android.graphics.Color.WHITE)

        var currentY = 100f

        // Title Area (Main color name)
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 60f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(mainColorName, width / 2f, currentY, paint)

        currentY += 50f

        // Main Color Card
        paint.color = palette.original
        canvas.drawRoundRect(50f, currentY, width - 50f, currentY + 400f, 40f, 40f, paint)
        
        currentY += 400f + 70f

        // Similar Colors Header
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 40f
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("Similar colors", 50f, currentY, paint)

        currentY += 30f

        // Similar Colors Grid (3 rows x 2 columns)
        val cardPadding = 15f
        val gridWidth = (width - 100f) / 2
        val gridHeight = 150f

        palette.similar.forEachIndexed { index, color ->
            val row = index / 2
            val col = index % 2
            val left = 50f + col * gridWidth + cardPadding
            val top = currentY + row * gridHeight + cardPadding
            val right = left + gridWidth - 2 * cardPadding
            val bottom = top + gridHeight - 2 * cardPadding

            paint.color = color
            canvas.drawRoundRect(left, top, right, bottom, 20f, 20f, paint)
            drawHexOverlay(canvas, color, left, top, right, bottom)
        }

        currentY += 3 * gridHeight + 70f

        // Analogous Section
        paint.color = android.graphics.Color.BLACK
        canvas.drawText("Analogous colors", 50f, currentY, paint)
        
        currentY += 30f
        
        palette.analogous.forEachIndexed { index, color ->
            val left = 50f + index * gridWidth + cardPadding
            val top = currentY + cardPadding
            val right = left + gridWidth - 2 * cardPadding
            val bottom = top + gridHeight - 2 * cardPadding
            
            paint.color = color
            canvas.drawRoundRect(left, top, right, bottom, 20f, 20f, paint)
            drawHexOverlay(canvas, color, left, top, right, bottom)
        }

        currentY += gridHeight + 70f

        // Complimentary Section
        paint.color = android.graphics.Color.BLACK
        canvas.drawText("Complimentary", 50f, currentY, paint)
        
        currentY += 30f
        
        val left = 50f + cardPadding
        val top = currentY + cardPadding
        val right = width - 50f - cardPadding
        val bottom = top + gridHeight - 2 * cardPadding
        
        paint.color = palette.complementary
        canvas.drawRoundRect(left, top, right, bottom, 20f, 20f, paint)
        drawHexOverlay(canvas, color = palette.complementary, left, top, right, bottom)

        return bitmap
    }

    private fun drawHexOverlay(canvas: Canvas, color: Int, left: Float, top: Float, right: Float, bottom: Float) {
        val hex = String.format("#%06X", 0xFFFFFF and color)
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 30f
            textAlign = Paint.Align.CENTER
            this.color = if (isLightColor(color)) android.graphics.Color.BLACK else android.graphics.Color.WHITE
        }
        val x = (left + right) / 2
        val y = (top + bottom) / 2 - ((textPaint.descent() + textPaint.ascent()) / 2)
        canvas.drawText(hex, x, y, textPaint)
    }

    private fun isLightColor(color: Int): Boolean {
        val luminance = (0.299 * android.graphics.Color.red(color) + 0.587 * android.graphics.Color.green(color) + 0.114 * android.graphics.Color.blue(color)) / 255
        return luminance > 0.5
    }

    private fun saveBitmapToCache(context: Context, bitmap: Bitmap): android.net.Uri {
        val imagesFolder = File(context.cacheDir, "shared_images")
        imagesFolder.mkdirs()
        // Static filename to avoid creating multiple files, causing it to use up storage.
        val file = File(imagesFolder, "shared_palette.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
}
