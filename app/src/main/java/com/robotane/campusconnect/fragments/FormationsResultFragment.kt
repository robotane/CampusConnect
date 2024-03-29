package com.robotane.campusconnect.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.robotane.campusconnect.FormationDetailsActivity
import com.robotane.campusconnect.R
import com.robotane.campusconnect.databinding.FragmentFormationsResultBinding
import com.robotane.campusconnect.ui.*
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.EmptyDataObserver
import com.robotane.campusconnect.utils.UniversityType
import me.zhanghai.android.fastscroll.FastScrollerBuilder


class FormationsResultFragment : Fragment() {

    private lateinit var binding: FragmentFormationsResultBinding
    private val viewModel: FormationsResultViewModel by activityViewModels {
        FormationsResultViewModelFactory((activity?.application as FiliereApplication).repository)
    }

    companion object {
        fun newInstance(extras: Bundle?): FormationsResultFragment {
            val instance = FormationsResultFragment()
            instance.arguments = extras
            return instance
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_formations_result, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val bacType = args?.getString(Constants.BAC_TYPE)
        val universityType =
            args?.getSerializable(Constants.UNIVERSITY_TYPE) as UniversityType?
        val formations = args?.getString(Constants.FORMATIONS)
        val towns = args?.getString(Constants.TOWNS)
        val isTwoPane = args?.getBoolean(Constants.IS_TWO_PANE, false)

        val layoutManager = LinearLayoutManager(context)

        val filiereListAdapter = FiliereListAdapter(context)

        val emptyDataObserver = EmptyDataObserver(
            binding.fragmentFormationsResultFormationsRecyclerview,
            binding.fragmentFormationsResultEmptyDataParent.root
        )


        viewModel.isTwoPane = isTwoPane == true
        viewModel.fetchFormationsByQuery(bacType, formations, towns, universityType)
        viewModel.searchFormationsLiveData.observe(viewLifecycleOwner) { filieres ->
            // Update the cached copy of the formations in the adapter.
            filieres?.let(filiereListAdapter::submitList)
            println("Added ${filieres.size}!!")
            filiereListAdapter.notifyDataSetChanged()
        }

        //TODO Add a header with the research statistics: # of formations found, research queries, ...
        filiereListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.fragmentFormationsResultFormationsRecyclerview.layoutManager = layoutManager
        binding.fragmentFormationsResultFormationsRecyclerview.adapter = filiereListAdapter

        filiereListAdapter.registerAdapterDataObserver(emptyDataObserver)

        binding.fragmentFormationsResultFormationsRecyclerview.onItemClick { _, position, _ ->
            val selected = viewModel.searchFormationsLiveData.value?.get(position)
            selected?.let {
                val newIntent = Intent(context, FormationDetailsActivity::class.java)
                newIntent.putExtra(Constants.FORMATION_ID, selected.id)
                context?.startActivity(newIntent)
            }
        }

        FastScrollerBuilder(binding.fragmentFormationsResultFormationsRecyclerview).build()
        binding.viewmodel = viewModel
    }
}