package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationManagerCompat

private const val ARG_OBJECT = "object"

class MyFragment : Fragment(R.layout.fragment_my) {

    private lateinit var notificationHelper: NotificationHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationHelper = NotificationHelper(requireContext())

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val btnNotify: Button = view.findViewById(R.id.btnNotify)
            btnNotify.setOnClickListener { showNotification() }
        }
    }

    private fun showNotification(){
        val num = getNumber()
        val builder = notificationHelper.createNotification(num)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(num, builder.build())
        }
    }

    fun getNumber(): Int {
        var num = -1
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            num = getInt(ARG_OBJECT)
        }
        return num
    }
}