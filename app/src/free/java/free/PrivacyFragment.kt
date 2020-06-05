package com.inqint.yarnrequirements.free

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inqint.yarnrequirements.R





/**
 * A simple [Fragment] subclass.
 */
class PrivacyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_privacy, container, false)
        //val t2 = view.findViewById(R.id.privacy_statement) as TextView
        //t2.movementMethod = LinkMovementMethod.getInstance()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment free.PrivacyFragment.
         */
        @JvmStatic
        fun newInstance() =
            PrivacyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
