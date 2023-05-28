package com.example.gsbsuser.account

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.gsbsuser.R

class EditIntroduceFragment:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        val inflater=requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_introduce, null)
        builder.setView(dialogView)

        builder.setPositiveButton("저장"){
                dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") {
                dialog, _ ->
            dialog.dismiss()
        }

        return builder.create()
    }
}