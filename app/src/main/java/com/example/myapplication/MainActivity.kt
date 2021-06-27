package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityMainBinding

private const val EXTRA_OBJECT = "object"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyFragmentAdapter
    private lateinit var notificationHelper: NotificationHelper

    private var currentNum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPager()
        binding.viewpagerController.btnInc.setOnClickListener { newFragment() }
        binding.viewpagerController.btnDec.setOnClickListener { deleteFragment() }
        notificationHelper = NotificationHelper(this)
        checkExtras(intent)
    }

    override fun onNewIntent(newIntent: Intent?) {
        super.onNewIntent(intent)
        checkExtras(newIntent)
    }

    private fun checkExtras(intent: Intent?) {
        val num = intent?.getIntExtra(EXTRA_OBJECT, 1)
        if (num != null) {
            moveToPageByNum(num)
        }
    }

    private fun moveToPageByNum(num: Int) {
        var index = adapter.getIndexByNum(num)
        if (index == -1) {
            adapter.createNewFragment(num)
            index = adapter.getIndexByNum(num)
        }
        binding.pager.currentItem = index
    }

    private fun initPager() {
        adapter = MyFragmentAdapter(this)
        binding.pager.adapter = adapter
        adapter.createNewFragment(1)
        binding.pager.registerOnPageChangeCallback(OnPageChangeListener())

    }

    private fun newFragment() {
        adapter.createNewFragment()
        binding.pager.currentItem = adapter.itemCount - 1
    }

    private fun deleteFragment() {
        notificationHelper.cancelNotification(currentNum)

        val index = adapter.getIndexByNum(currentNum)
        val lastIndex = adapter.itemCount - 1
        adapter.deleteFragment(index)

        if (index != lastIndex) {
            currentNum = adapter.getNumByPos(index)
            updateTextView(currentNum.toString())
        }
    }


    private fun updateTextView(text: String) {
        binding.viewpagerController.tvPosition.text = text
    }

    inner class OnPageChangeListener : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 0) {
                binding.viewpagerController.btnDec.visibility = Button.INVISIBLE
            } else {
                binding.viewpagerController.btnDec.visibility = Button.VISIBLE
            }
            currentNum = adapter.getNumByPos(position)
            updateTextView(currentNum.toString())
        }

    }
}