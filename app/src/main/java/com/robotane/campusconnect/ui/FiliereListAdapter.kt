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
        private val filiereNomView: TextView = itemView.findViewById(R.id.filiereTextView)
        private val ufrView: TextView = itemView.findViewById(R.id.ufrTextView)

        fun bind(filiere: Filiere?) {
            filiereNomView.text = filiere?.nom
            ufrView.text = ufrView.context.getString(R.string.filiereRcVUfrUniversite, filiere?.ufr, filiere?.nomUniversite)
        }

        companion object {
            fun create(parent: ViewGroup): FiliereViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
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