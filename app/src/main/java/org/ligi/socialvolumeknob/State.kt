package org.ligi.socialvolumeknob

import com.chibatching.kotpref.KotprefModel

object State : KotprefModel() {
    var identifier by nullableStringPref()
}