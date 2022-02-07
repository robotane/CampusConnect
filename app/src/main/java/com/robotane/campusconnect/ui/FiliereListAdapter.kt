package com.robotane.campusconnect.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.robotane.campusconnect.R
import com.robotane.campusconnect.data.Filiere

class FiliereListAdapter : ListAdapter<Filiere, FiliereListAdapter.FiliereViewHolder>(FilieresComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiliereViewHolder {
        return FiliereViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FiliereViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class FiliereViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filiereNomView: TextView = itemView.findViewById(R.id.item_filiere_name_lbl)
        private val ufrView: TextView = itemView.findViewById(R.id.item_filiere_ufr_lbl)
        private val universiteView: TextView = itemView.findViewById(R.id.item_filiere_universite_lbl)
        private val filiereSeries: TextView = itemView.findViewById(R.id.item_filiere_series_lbl)
        private val placesRestantes: TextView = itemView.findViewById(R.id.item_filiere_places_restantes_lbl)

        fun bind(filiere: Filiere?) {
            filiereNomView.text = filiere?.nom

            ufrView.text = filiere?.ufr?.split(" - ")?.last() ?:filiere?.ufr

            universiteView.text = filiere?.nomUniversite?.split(" - ")?.first() ?:filiere?.nomUniversite
//            ufrView.text = ufrView.context.getString(R.string.filiereRcVUfrUniversite, filiere?.ufr, filiere?.nomUniversite)
            filiereSeries.text = "Séries autorisées: ${filiere?.series} ${if (filiere?.debouches != null) "\n\nDébouchés: ${filiere?.debouches}" else ""}"
//            numberview.text = filiere?.id.toString()
            placesRestantes.text = "${if(filiere?.placesRestantes != null) filiere?.placesRestantes else 0} places restantes"
        }

        companion object {
            fun create(parent: ViewGroup): FiliereViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_filiere, parent, false)
                return FiliereViewHolder(view)
            }
        }
    }

    class FilieresComparator : DiffUtil.ItemCallback<Filiere>() {
        override fun areItemsTheSame(oldItem: Filiere, newItem: Filiere): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Filiere, newItem: Filiere): Boolean {
            return oldItem.nom == newItem.nom && oldItem.nomUniversite == newItem.nomUniversite
        }
    }
}