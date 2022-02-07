package com.robotane.campusconnect.fragments

import android.content.Context
import com.robotane.campusconnect.fragments.MainFragment.OnButtonClickedListener
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.robotane.campusconnect.R
import java.lang.ClassCastException

class MainFragment : Fragment(), View.OnClickListener {
    //2 - Declare callback
    private var mCallback: OnButtonClickedListener? = null

    // 1 - Declare our interface that will be implemented by any container activity
    interface OnButtonClickedListener {
        fun onButtonClicked(view: View?)
    }

    // --------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Inflate the layout of MainFragment
        val result = inflater.inflate(R.layout.fragment_search_formation, container, false)

        //Set onClickListener to button "SHOW ME DETAILS"
        result.findViewById<View>(R.id.fragment_search_formation_search_btn)
            .setOnClickListener(this)
        return result
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 4 - Call the method that creating callback after being attached to parent activity
        createCallbackToParentActivity()
    }

    // --------------
    // ACTIONS
    // --------------
    override fun onClick(v: View) {
        // 5 - Spread the click to the parent activity
        mCallback!!.onButtonClicked(v)
    }

    // --------------
    // FRAGMENT SUPPORT
    // --------------
    // 3 - Create callback to parent activity
    private fun createCallbackToParentActivity() {
        mCallback = try {
            //Parent activity will automatically subscribe to callback
            activity as OnButtonClickedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException("$e must implement OnButtonClickedListener")
        }
    }
}