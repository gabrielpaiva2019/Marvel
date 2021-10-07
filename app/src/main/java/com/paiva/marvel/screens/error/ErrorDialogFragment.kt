package com.paiva.marvel.screens.error

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paiva.marvel.R
import kotlinx.android.synthetic.main.fragment_error.*

class ErrorDialogFragment(builder: Builder): DialogFragment() {

    var onTryAgainClick: () -> Unit = builder.onRetryClick as () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_error, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        buttonTryAgain.setOnClickListener {
            this.dismiss()
            onTryAgainClick()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
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

    class Builder {
        internal var onRetryClick: () -> Unit? = { }

        fun addButtonClick( onRetryClick: () -> Unit) = apply { this.onRetryClick = onRetryClick }
    }
}