package com.example.gsbsuser.account

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.gsbsuser.R
import com.example.gsbsuser.databinding.EditInfoBinding

class EditInfoFragment:DialogFragment() {
    private var _binding: EditInfoBinding?= null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = EditInfoBinding.inflate(layoutInflater)
        val view = binding.root

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        builder.setPositiveButton("저장"){
            dialog, _ ->
            btnClickListener.onButtonClicked()
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") {
                dialog, _ ->
            dialog.dismiss()
        }

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonClickListener{
        fun onButtonClicked()
    }

    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.btnClickListener = buttonClickListener
    }
    // 클릭 이벤트 실행
    private lateinit var btnClickListener: OnButtonClickListener
}