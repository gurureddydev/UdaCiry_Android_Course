package com.gurureddy.contactmanagerapp.offlineVideo.ui

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.DevByteItemBinding
import com.gurureddy.contactmanagerapp.databinding.FragmentDevByteBinding
import com.gurureddy.contactmanagerapp.offlineVideo.domain.Video


class DevByteFragment : Fragment() {
    private lateinit var binding: FragmentDevByteBinding
    private val viewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(
            this,
            DevByteViewModel.Factory(activity.application)
        ).get(DevByteViewModel::class.java)
    }

    private var viewModelAdapter: DevByteAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.playList.observe(viewLifecycleOwner, Observer<List<Video>> { videos ->
            videos.apply {
                viewModelAdapter?.videos = videos
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev_byte, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModelAdapter = DevByteAdapter(VideoClick {
            val packageManager = context?.packageManager?:return@VideoClick

            var intent = Intent(Intent(Intent.ACTION_VIEW,Uri.parse(it.url)))
            if (intent.resolveActivity(packageManager) != null) {
                intent = Intent(Intent.ACTION_VIEW,Uri.parse(it.url))
            }
            startActivity(intent)
        })
        return binding.root
    }

    private val Video.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube://" + httpUri.getQueryParameter("v"))
        }
}

class VideoClick(val block: (Video) -> Unit) {
    fun onClick(video: Video) = block(video)
}

class DevByteAdapter(val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>() {

    var videos: List<Video> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val binding: DevByteItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DevByteViewHolder.Layout,
            parent,
            false
        )
        return DevByteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.binding.also {
            it.video = videos[position]
            it.videoCallback = callback
        }
    }

    override fun getItemCount() = videos.size
}


class DevByteViewHolder(val binding: DevByteItemBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        val Layout = R.layout.dev_byte_item
    }
}
