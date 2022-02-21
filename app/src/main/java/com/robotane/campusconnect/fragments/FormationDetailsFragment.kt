package com.robotane.campusconnect.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.robotane.campusconnect.R
import com.robotane.campusconnect.databinding.FragmentFormationDetailsBinding
import com.robotane.campusconnect.ui.FiliereApplication
import com.robotane.campusconnect.ui.FormationDetailsViewModel
import com.robotane.campusconnect.ui.FormationsDetailsViewModelFactory
import com.robotane.campusconnect.utils.Constants
import me.zhanghai.android.fastscroll.FastScrollerBuilder

class FormationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFormationDetailsBinding
    private val TAG: String = javaClass.simpleName
    val viewModel: FormationDetailsViewModel by activityViewModels {
        FormationsDetailsViewModelFactory((activity?.application as FiliereApplication).repository)
    }

    companion object {
        fun newInstance() = FormationDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_formation_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = activity?.intent
        val formationId = intent?.getIntExtra(Constants.FORMATION_ID, 0)
        Log.d(TAG, "formation ID: ${formationId.toString()}")
        formationId?.let { viewModel.getFormationDetails(it) }
        viewModel.formation.observeForever {
            addChip(it.series)
        }
        FastScrollerBuilder(binding.fragmentFormationDetailsMainSclv).build()
        binding.viewmodel = viewModel
    }

    //TODO Chip are duplicated after screen rotation, FIXIT
    private fun addChip(series: String?) {
        if(isAdded){
            series?.split(",")?.map(String::trim)?.forEach { serie ->
                val chip = LayoutInflater.from(context).inflate(R.layout.chip_item, binding.fragmentFormationDetailsSeriesChipgroup, false) as Chip
                chip.text = serie
                binding.fragmentFormationDetailsSeriesChipgroup.addView(chip as View)
            }
        }
    }
}