package com.gurureddy.contactmanagerapp.trivia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.ActivityAboutMeBinding
import com.gurureddy.contactmanagerapp.trivia.model.MyName

class AboutMe : AppCompatActivity() {
    private lateinit var binding: ActivityAboutMeBinding
    private val myName: MyName = MyName("Guru Reddy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_me)

        binding.doneBtn.setOnClickListener {
            addNickName(it)
        }

        binding.myName = myName
    }

    private fun addNickName(view: View) {

        binding.apply {
            myName?.nickName = nickNameEdt.text.toString()
            invalidateAll()
            nickNameEdt.visibility = View.GONE
            view.visibility = View.GONE
            nickNameTv.visibility = View.VISIBLE
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}