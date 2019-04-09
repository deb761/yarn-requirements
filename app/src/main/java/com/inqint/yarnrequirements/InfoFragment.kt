package com.inqint.yarnrequirements

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class InfoFragment : Fragment(), OnClickListener {
    companion object {

        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mview = inflater.inflate(R.layout.fragment_info, container, false)
        val button: Button = mview.findViewById(R.id.icon8Link)
        button.setOnClickListener(this)
        return mview
    }

    // Implement the OnClickListener callback
    override fun onClick(v: View) {
        // open the Icon8 web site
        val webpage: Uri = Uri.parse("http://www.icons8.com")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(activity?.packageManager!!) != null) {
            startActivity(intent)
        }
    }
}