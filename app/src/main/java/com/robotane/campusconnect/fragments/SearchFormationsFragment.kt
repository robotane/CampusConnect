package com.robotane.campusconnect.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.robotane.campusconnect.R
import com.robotane.campusconnect.databinding.FragmentSearchFormationBinding
import com.robotane.campusconnect.ui.FiliereApplication
import com.robotane.campusconnect.ui.FormationsSearchViewModel
import com.robotane.campusconnect.ui.FormationsSearchViewModelFactory


class SearchFormationsFragment : Fragment() {

    // Declare callback
    private var mCallback: OnButtonClickedListener? = null

    // Declare our interface that will be implemented by any container activity
    interface OnButtonClickedListener {
        fun onButtonClicked(view: View?)
    }

    // Create callback to parent activity
    private fun createCallbackToParentActivity() {
        mCallback = try {
            // Parent activity will automatically subscribe to callback
            activity as OnButtonClickedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException("$e must implement OnButtonClickedListener")
        }
    }

    private lateinit var binding: FragmentSearchFormationBinding
    val viewModel: FormationsSearchViewModel by activityViewModels {
        FormationsSearchViewModelFactory(this, (activity?.application as FiliereApplication).repository, arguments)
    }

    companion object {
        fun newInstance() = SearchFormationsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_formation, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allBacType.observeForever{
            val adapter  =  ArrayAdapter(requireContext(), R.layout.item_auto_complete_input, it.sorted())
            val textView = binding.fragmentSearchFormationSerieBac.editText as? MultiAutoCompleteTextView
            textView?.setAdapter(adapter)
            textView?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        }
        createCallbackToParentActivity()

        viewModel.allTowns.observeForever{
            val adapter  =  ArrayAdapter(requireContext(), R.layout.item_auto_complete_input, it.sorted())
            val textView = binding.fragmentSearchFormationVilles.editText as? MultiAutoCompleteTextView
            textView?.setAdapter(adapter)
            textView?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        }
        viewModel.callback = mCallback
        binding.viewmodel = viewModel
    }
}