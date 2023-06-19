package com.base.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


class ImageUtils {
    companion object {
        const val MAX_IMAGE_DIMENSION = 1080
        fun createDirectoryAndSaveFile(context: Context, bitmap: Bitmap, fileName: String): String {
                val cw  = ContextWrapper(context.getApplicationContext());
                // path to /data/data/yourapp/app_data/imageDir
                val directory: File  = cw.getDir("imageDir", Context.MODE_PRIVATE);
                // Create imageDir
                val fname = "$fileName.jpg"
                val mypath = File(directory,fname)


                try {
                    val fos =  FileOutputStream(mypath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close();
                } catch (e: Exception ) {
                    e.printStackTrace();
                } finally {
                    try {

                    } catch (e: IOException) {
                        e.printStackTrace();
                    }
                }
                return mypath.absolutePath
        }
        fun getBitmap(src: String?): Bitmap? {
            return convertToBitmapFromURL(src)
        }
        private fun convertToBitmapFromURL(src: String?): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val url = URL(src)
                val bitMapTemp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                bitmap = scaleImage(bitMapTemp)
            } catch (e: IOException) {
                Log.d("Error:", e.message.toString())
            }
            return bitmap
        }
        private fun scaleImage(bitmap: Bitmap?): Bitmap? {

            if (bitmap == null) return null

            var width = bitmap.width

            if (width > MAX_IMAGE_DIMENSION) {
                val ratio = MAX_IMAGE_DIMENSION.toFloat() / width.toFloat()

                width = MAX_IMAGE_DIMENSION
                val height = (bitmap.height.toFloat() * ratio).toInt()
                return Bitmap.createScaledBitmap(bitmap, width, height, true)
            }

            return bitmap
        }
    }

}