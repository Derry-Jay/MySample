package com.my.sample.data

import com.my.sample.extensions.canExecuteCommand
import com.my.sample.extensions.file
import com.my.sample.extensions.hardwareContains
import com.my.sample.extensions.isStartOfBrand
import com.my.sample.extensions.isStartOfDevice
import com.my.sample.extensions.isStartOfFingerprint
import com.my.sample.extensions.manufacturerContains
import com.my.sample.extensions.modelContains
import com.my.sample.extensions.productContains
import com.my.sample.extensions.regexFromString
import java.time.LocalDate
import java.time.LocalDateTime

val today2: LocalDate = LocalDate.now()

val runTime: Runtime = Runtime.getRuntime()

val today1: LocalDateTime = LocalDateTime.now()

val pwdRegex: Regex =
    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$".regexFromString()

val mailRegex: Regex =
    "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$".regexFromString()

val isDeviceRooted: Boolean =
    "/system/app/Superuser.apk".file.exists() && ("/system/xbin/which su".canExecuteCommand
            || "/system/bin/which su".canExecuteCommand
            || "which su".canExecuteCommand)

/**
 * In Development - an idea of ours was to check the if selinux is enforcing - this could be disabled for some rooting apps
 * Checking for selinux mode
 *
 * @return true if selinux enabled
 */

val isSelinuxFlagInEnabled: Boolean
    get() {
        return try {
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java)
            val selinux = (get.invoke(c, "ro.build.selinux") as String).toIntOrNull() ?: 0
            selinux > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

val isEmulator: Boolean =
    ("generic".isStartOfBrand() && "generic".isStartOfDevice())
            || "Genymotion".manufacturerContains()
            || "generic".isStartOfFingerprint()
            || "unknown".isStartOfFingerprint()
            || "goldfish".hardwareContains()
            || "ranchu".hardwareContains()
            || "google_sdk".modelContains()
            || "Emulator".modelContains()
            || "Android SDK built for x86".modelContains()
            || "sdk_google".productContains()
            || "google_sdk".productContains()
            || "sdk".productContains()
            || "sdk_x86".productContains()
            || "sdk_gphone64_arm64".productContains()
            || "vbox86p".productContains()
            || "emulator".productContains()
            || "simulator".productContains()
