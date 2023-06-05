package com.gurureddy.contactmanagerapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gurureddy.contactmanagerapp.databinding.ActivityColorMyViewBinding

class ColorMyView : AppCompatActivity() {
    private lateinit var binding: ActivityColorMyViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_color_my_view)

        initListeners()
    }

    private fun initListeners() {
        val clickableViews: List<View> =
            listOf(

                binding.boxOneTv,
                binding.boxTwoTv,
                binding.box3Tv,
                binding.box4Tv,
                binding.box5Tv,
                binding.redBtn,
                binding.yellowBtn,
                binding.greenBtn

            )

        for (item in clickableViews) {
            item.setOnClickListener { markedColor(it) }
        }
    }

    private fun markedColor(view: View) {
        when (view.id) {
            R.id.box_one_tv -> binding.boxOneTv.setImageResource(R.drawable.img5)
            R.id.box_two_tv -> binding.boxTwoTv.setImageResource(R.drawable.img4)
            R.id.box_3_tv -> binding.box3Tv.setImageResource(R.drawable.img3)
            R.id.box_4_tv -> binding.box4Tv.setImageResource(R.drawable.img2)
            R.id.box_5_Tv -> binding.box5Tv.setImageResource(R.drawable.img1)
            R.id.red_btn -> binding.box3Tv.setImageResource(R.drawable.dice_1)
            R.id.yellow_btn -> binding.box4Tv.setImageResource(R.drawable.dice_2)
            R.id.green_btn -> binding.box5Tv.setImageResource(R.drawable.dice_6)

            else -> R.drawable.img5
        }
    }
}