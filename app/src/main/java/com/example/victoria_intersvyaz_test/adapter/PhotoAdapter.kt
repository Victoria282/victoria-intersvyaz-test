package com.example.victoria_intersvyaz_test.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.victoria_intersvyaz_test.databinding.ItemLayoutBinding
import com.example.victoria_intersvyaz_test.model.Photos
import kotlin.collections.ArrayList


class PhotoAdapter(private var photos: ArrayList<Photos>)
    : RecyclerView.Adapter<PhotoAdapter.DataViewHolder>() {

    var clickListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(id: String)
    }

    class DataViewHolder(
        private val binding: ItemLayoutBinding,
        private val listener: ItemClickListener?)
        : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(photo: Photos) {
            with(binding) {
                tittle.text = photo.title
                id.text = photo.id
                val glideUrl = GlideUrl(
                    photo.url, LazyHeaders
                        .Builder()
                        .addHeader(
                             "User-Agent",
                             "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) " +
                             "AppleWebKit / 537.36(KHTML, like Gecko) " +
                             "Chrome  47.0.2526.106 Safari / 537.36"
                        )
                        .build()
                )
                Glide.with(image.context)
                    .load(glideUrl)
                    .into(image)

                itemPhoto.setOnClickListener {
                    listener?.onItemClick(photo.id.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(
            inflater,
            parent,
            false)
        return DataViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int = photos.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(photos: ArrayList<Photos>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}