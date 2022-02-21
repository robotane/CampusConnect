package com.robotane.campusconnect.utils

import android.content.Context
import com.robotane.campusconnect.R
import com.robotane.campusconnect.data.FiliereOverviewModel

class FormationDetailsTextCreator {

    companion object {
        @JvmStatic
        fun ufrString(ufr: String?): String {
            return ufr?.split(Regex(" - "))?.last() ?: ""
        }

        fun univeristyString(formation: FiliereOverviewModel): String {
            return formation.nomUniversite?.split(Regex(" - "))?.first() ?: formation.nomUniversite ?: ""
        }

        @JvmStatic
        fun placesString(
            remaining: Int?,
            total: Int?,
            stringFormat: String,
            defaultString: String
        ): String {
            return if (remaining != null) {
                stringFormat.format(remaining, total)
            } else
                defaultString
        }

        fun formatComplicatedListString(string: String?): String {
            val reformattedList = string?.removePrefix("- ")?.removePrefix("• ")
                ?.replace(" - ", " $ ")?.replace(" • ", " § ")
                ?.replace(Regex(": [0-9]-"), "^§ ")?.replace(Regex("[0-9]-"), " § ")
            var toFormat = reformattedList
            var formattedString = ""
            if (reformattedList?.contains("^§") == true) {
                formattedString = "${reformattedList.substringBefore("^§ ")}:\n"
                toFormat = reformattedList.substringAfter("^§ ")
            }
            formattedString += toFormat?.split(Regex(" § "))
                ?.joinToString("\n") { "• ${it.trim()}" }
            return formattedString
        }

        @JvmStatic
        fun formatListString(string: String?): String? {
            return string?.replaceFirstChar { c->if(c=='-') "• " else c.toString() }
                ?.replace(Regex("[0-9]-"),  "\n• ")
                ?.replace(" - ", "\n• ")
                ?.replace(" • ", "\n• ")
        }

        @JvmStatic
        fun constraintsString(string: String?, context: Context): String? {

            fun getFullSubject(truncSubject: String): String {
                val keys = arrayListOf<String>()
                val values = arrayListOf<String>()
                context.resources.getStringArray(R.array.subjectFullName).forEach {
                    val l = it.split("§")
                    keys.add(l[0])
                    values.add(l[1])
                }
                return if (keys.contains(truncSubject))
                    values[keys.indexOf(truncSubject)]
                else
                    truncSubject
            }
            return string?.split("\n")?.joinToString("\n") {
                if (it.startsWith("NB:"))
                    return@joinToString "• $it"

                val beginning = "• BAC ${it.substringBefore(":")}: "
                val toForm = it.substringAfter(":").trim()
                beginning + if (Regex("([a-z]+_[a-z]+)").containsMatchIn(it)) {
                    val nTofForm = toForm.replace("(", "").replace(")", "")
                    var sep = ""
                    var l = listOf(nTofForm)
                    if (nTofForm.contains("AND")) {
                        l = nTofForm.split(Regex("AND"))
//                        sep = context.getString(R.string.and)
                        sep = ";"
                    }
                    else if (nTofForm.contains("OR")) {
                        l = nTofForm.split(Regex("OR"))
                        sep = context.getString(R.string.or)
                    }
                    l.map { s ->
                        val ls = s.split(Regex(">="))// TODO manage when >= has not been found
                        return@map context.getString(R.string.markGreaterThan)
                            .format(getFullSubject(ls[0].trim()), ls[1])
                    }.joinToString("$sep ")
                } else {
                    toForm
                }
            }
        }

        @JvmStatic
        fun conditionsString(conditions: String?): String {
            return if (conditions.isNullOrBlank()) {
                "Aucunes conditions particulières"
            } else
                conditions.replaceFirstChar { c -> c.uppercaseChar() }
        }
    }

}
