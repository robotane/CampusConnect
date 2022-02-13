package com.robotane.campusconnect.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.robotane.campusconnect.R
import com.robotane.campusconnect.data.FiliereOverviewModel
import com.robotane.campusconnect.databinding.ItemFiliereBinding

class FiliereListAdapter :
    ListAdapter<FiliereOverviewModel, FiliereListAdapter.FiliereViewHolder>(FilieresComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiliereViewHolder {
        return FiliereViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FiliereViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class FiliereViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemFiliereBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_filiere,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(filiere: FiliereOverviewModel?) {
            binding.formation = filiere
        }
    }

    class FilieresComparator : DiffUtil.ItemCallback<FiliereOverviewModel>() {
        override fun areItemsTheSame(oldItem: FiliereOverviewModel, newItem: FiliereOverviewModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FiliereOverviewModel, newItem: FiliereOverviewModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}