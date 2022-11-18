package com.example.seoulpublicswimmingpool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seoulpublicswimmingpool.databinding.ItemMainBinding

class CustomAdapter(private val dataList: MutableList<DataVO>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataVO) {
            binding.tvCenterName.text = data.centerName
            binding.tvWeek.text = data.week
            binding.tvClassTime.text = data.time
            binding.tvFee.text = data.fee
            val position = bindingAdapterPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size


}