package com.my.sample.extensions

import android.net.Uri
import android.os.Build.BRAND
import android.os.Build.DEVICE
import android.os.Build.FINGERPRINT
import android.os.Build.HARDWARE
import android.os.Build.MANUFACTURER
import android.os.Build.MODEL
import android.os.Build.PRODUCT
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.my.sample.data.mailRegex
import com.my.sample.data.runTime
import com.my.sample.data.today1
import com.my.sample.data.today2
import com.my.sample.utils.Api
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt
import android.net.Uri.Builder as Bu

val String?.file: File
    get() {
        return File(trimmed)
    }

val String.uri: Uri
    get() {
        return toUri()
    }

val String.uriBuilder: Bu?
    get() {
        return uri.buildUpon()
    }

val Int.isPerfectSquare: Boolean
    get() {
        val sq = sqrt(this.toDouble())

        val f = floor(sq)

        return sq == f
    }

val String?.trimmed: String
    get() {
        return this?.trim() ?: ""
    }

val String?.isEmpty: Boolean
    get() {
        return isNullOrBlank()
    }

val String?.time: Time
    get() {
        return Time.valueOf(trimmed)
    }

val String?.lowerCased: String
    get() {
        return this?.lowercase() ?: ""
    }

val String?.upperCased: String
    get() {
        return this?.uppercase() ?: ""
    }

val String?.simpleDateFormat: SimpleDateFormat
    get() {
        return SimpleDateFormat(trimmed)
    }

fun String?.isStartOfDevice(ic: Boolean? = null): Boolean {
    return DEVICE.startsWith(trimmed, ic ?: false)
}

fun String?.isStartOfBrand(ic: Boolean? = null): Boolean {
    return BRAND.startsWith(trimmed, ic ?: false)
}

fun String?.modelContains(ic: Boolean? = null): Boolean {
    return MODEL.contains(trimmed, ic ?: false)
}

fun String?.productContains(ic: Boolean? = null): Boolean {
    return PRODUCT.contains(trimmed, ic ?: false)
}

fun String?.hardwareContains(ic: Boolean? = null): Boolean {
    return HARDWARE.contains(trimmed, ic ?: false)
}

fun String?.manufacturerContains(ic: Boolean? = null): Boolean {
    return MANUFACTURER.contains(trimmed, ic ?: false)
}

fun String?.isStartOfFingerprint(ic: Boolean? = null): Boolean {
    return FINGERPRINT.startsWith(trimmed, ic ?: false)
}

val String?.isNotEmpty: Boolean
    get() {
        return !isEmpty && (this?.isNotBlank() ?: false)
    }

val Int.isFibonacciTerm: Boolean
    get() {
        return (5 * this * this + 4).isPerfectSquare ||
                (5 * this * this - 4).isPerfectSquare
    }

fun String?.regexFromString(ros: Set<RegexOption>? = null): Regex {
    val ls: Int = ros?.size ?: 0
    return if (ls == 0) Regex(trimmed) else (if (ls == 1) Regex(trimmed, ros?.first()!!) else Regex(
        trimmed,
        ros!!
    ))
}

val String?.isValidEmail: Boolean
    get() {
        return mailRegex.matches(trimmed) && mailRegex.findAll(trimmed).toList().size == 1
    }

val String?.fileExt: String
    get() {
        return this?.split('.')?.lastOrNull()?.trimmed?.lowerCased ?: ""
    }

val Int.eitherZeroOrOne: Boolean
    get() {
        return arrayOf(0, 1).contains(this)
    }

val Int.factorial: Int
    get() {
        return if (this < 0) 0 else (if (eitherZeroOrOne) 1 else this * (this - 1).factorial)
    }

val Int.reverse: Int
    get() {
        if (this > 9) {
            var res = 0
            var cur: Int = this
            while (cur != 0) {
                res = (res * 10) + (cur % 10)
                cur /= 10
            }
            return res
        } else return this
    }

val Int.isPalindrome: Boolean
    get() {
        return this == reverse
    }

val Int.nthFibonacciTerm: Int
    get() {
        return if (eitherZeroOrOne) this else ((this - 1).nthFibonacciTerm + (this - 2).nthFibonacciTerm)
    }

val String?.flag: Boolean
    get() {
        val arr = arrayOf("true", "ok", "yes", "good", "fine")
        return trimmed.isNotEmpty && (arr.contains(trimmed) || arr.map { it.upperCased }
            .contains(trimmed.upperCased) || (trimmed.toIntOrNull() ?: 0) > 0)
    }

val String?.date: LocalDate
    get() {
        return try {
            LocalDate.parse(trimmed)
        } catch (e: Exception) {
            e.printStackTrace()
            today2
        }
    }

fun String?.dateWithFormat(formatter: DateTimeFormatter?): LocalDate {
    return try {
        LocalDate.parse(trimmed, formatter)
    } catch (e: Exception) {
        e.printStackTrace()
        today2
    }
}

val String?.dateTime: LocalDateTime
    get() {
        return try {
            LocalDateTime.parse(trimmed)
        } catch (e: Exception) {
            e.printStackTrace()
            today1
        }
    }

fun String?.dateTimeWithFormat(formatter: DateTimeFormatter?): LocalDateTime {
    return try {
        LocalDateTime.parse(trimmed, formatter)
    } catch (e: Exception) {
        e.printStackTrace()
        today1
    }
}

fun String.limitString(limit: Int = 24, hiddenText: String = "..."): String {
    return if (trimmed.isEmpty) "" else trimmed.substring(
        0,
        min(limit, length)
    ) + (if (length > limit) hiddenText else "")
}

val String?.firstLetterCapitalized: String
    get() {
        return if (trimmed.isEmpty) "" else trimmed.substring(0, 1)
            .upperCased + trimmed.substring(1).lowerCased
    }

val String?.isImage: Boolean
    get() {
        val arr = arrayOf("jpg", "jpeg", "png", "svg", "gif")
        return arr.contains(fileExt)
    }

val String?.isVideo: Boolean
    get() {
        val arr = arrayOf("avi", "mp4", "mov", "hevc", "flv", "mkv")
        return arr.contains(fileExt)
    }

val String?.process: Process
    get() {
        return runTime.exec(trimmed)
    }

val String?.dateFormat: DateTimeFormatter
    get() {
        return try {
            DateTimeFormatter.ofPattern(trimmed)
        } catch (e: Exception) {
            e.printStackTrace()
            DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        }
    }

fun String?.dateFormatterWithLocale(locale: Locale?): DateTimeFormatter {
    return try {
        DateTimeFormatter.ofPattern(trimmed, locale)
    } catch (e: Exception) {
        e.printStackTrace()
        DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
    }
}

val String?.canExecuteCommand: Boolean
    get() {
        return try {
            val process = if (trimmed.isNotEmpty) process else null
            println(process?.isAlive)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

val String?.isAudio: Boolean
    get() {
        val arr = arrayOf(
            "mp3",
            "wav",
            "aac",
            "m4a",
            "flac",
            "wma",
            "midi",
            "mod"
        )
        return arr.contains(fileExt)
    }

val View.navCon: NavController
    get() {
        return findNavController()
    }

fun View.stringFromResource(resId: Int): String {
    return context.getString(resId)
}

fun View.getDeviceService(name: String): Any {
    return context.getSystemService(name)
}

val String?.retrofitInstance: Retrofit
    get() {
        return Builder().baseUrl(trimmed)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }

val String?.api: Api
    get() {
        return retrofitInstance.apiLink
    }

val Retrofit.apiLink: Api
    get() {
        return create(Api::class.java)
    }

fun View.assetFiles(path: String): Array<out String>? {
    return context.assets.list(path)
}

fun View.getSnackBar(message: String, length: Int): Snackbar {
    return Snackbar.make(this, message, length)
}

fun View.getSnackBarWithAction1(
    message: String,
    length: Int,
    action: String?,
    listener: View.OnClickListener?
): Snackbar {
    return getSnackBar(message, length).setAction(action, listener)
}

fun View.getSnackBarWithAction2(
    message: String,
    length: Int,
    resId: Int,
    listener: View.OnClickListener?
): Snackbar {
    return getSnackBar(message, length).setAction(resId, listener)
}

fun View.showSnackBar(
    message: String,
    length: Int,
    action: String?,
    resId: Int?,
    listener: View.OnClickListener?
) {
    assert(action != null || resId != null)
    val snb = if (action != null) getSnackBarWithAction1(
        message,
        length,
        action,
        listener
    ) else if (resId != null) getSnackBarWithAction2(message, length, resId, listener) else null
    snb?.show()
}

val Any?.str: String get() {
    if (this == null) return "null".firstLetterCapitalized
    // After the null check, 'this' is autocast to a non-nullable type, so the toString() below
    // resolves to the member function of the Any class
    return toString()
}

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?): ImageView {
    return try {
        Glide.with(context)
            .load(imgUrl.trimmed.uriBuilder?.scheme("https")?.build())
            .into(this).view
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}