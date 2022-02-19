package com.robotane.campusconnect.utils

import android.util.Log
import com.robotane.campusconnect.data.FiliereOverviewModel

class FormationDetailsTextCreator {

    companion object {
        @JvmStatic
        fun ufrString(ufr: String?): String {
            return ufr?.split(" - ")?.last()?:""
        }

        fun univeristyString(formation: FiliereOverviewModel): String {
            return formation.nomUniversite?.split(" - ")?.first() ?: formation.nomUniversite ?: ""
        }

        @JvmStatic
        fun placesString(remaining: Int?, total: Int?, stringFormat: String, defaultString: String): String{
            return  if (remaining!=null) {
                stringFormat.format(remaining, total)
            }
            else
                defaultString
        }

        @JvmStatic
        fun debouchesString(debouches: String?): String? {
           return debouches?.split("•-")?.joinToString("\n", transform = String::trim)
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
