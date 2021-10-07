package com.paiva.marvel.screens.loading

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paiva.marvel.R


class LoadingDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, null)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE));
    }

    override fun onDismiss(dialog: DialogInterface) {
        changeVisibilityActivity(View.VISIBLE)
        super.onDismiss(dialog)
    }

    override fun onDetach() {
        super.onDetach()
        changeVisibilityActivity(View.VISIBLE)
    }

    private fun changeVisibilityActivity(visibility: Int) {
        activity?.window?.decorView?.findViewById<View>(android.R.id.content)?.visibility =
            visibility
    }
}