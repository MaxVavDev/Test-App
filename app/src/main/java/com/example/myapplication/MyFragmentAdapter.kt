package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val ARG_OBJECT = "object"

class MyFragmentAdapter(
    fragmentActivity: FragmentActivity,
) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments: MutableList<MyFragment> = mutableListOf()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getNumByPos(position: Int): Int {
        return fragments[position].getNumber()
    }

    private fun getLastNumber(): Int {
        return fragments.last().getNumber()
    }

    fun createNewFragment() {
        makeNewFragment(getLastNumber() + 1)
        notifyItemInserted(fragments.size - 1)
    }

    fun createNewFragment(num: Int) {
        makeNewFragment(num)
        notifyItemInserted(fragments.size - 1)
    }

    fun getIndexByNum(num: Int): Int {
        return fragments.indexOfFirst { it.getNumber() == num }
    }

    fun deleteFragment(pos: Int) {
        fragments.removeAt(pos)
        notifyItemRemoved(pos)
    }


    private fun makeNewFragment(num: Int) {
        val fragment = MyFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, num)
        }
        fragments.add(fragment)
    }

    // make sure notifyData() works correctly
    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.map { it.hashCode().toLong() }.contains(itemId)
    }
}