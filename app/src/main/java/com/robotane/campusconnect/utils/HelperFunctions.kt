package com.robotane.campusconnect.utils

import java.text.Normalizer

class HelperFunctions {
    companion object {
        fun stripAccents(s: String): String {
            var stripped = Normalizer.normalize(s, Normalizer.Form.NFKD)
            stripped = stripped.replace(Regex("[\\p{InCOMBINING_DIACRITICAL_MARKS}]"), "")
            return stripped
        }
    }
}