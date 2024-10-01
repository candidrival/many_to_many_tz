package com.example.many_to_many_tz.presentation.all_items.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.many_to_many_tz.domain.entities.Item
import com.example.many_to_many_tz.utils.baseUrl
import com.example.many_to_many_tz.databinding.ItemAllItemsBinding

class AllItemsAdapter(
) : RecyclerView.Adapter<AllItemsAdapter.AllItemsViewHolder>() {

    var clickListener: ((Item) -> Unit)? = null
    private var items: List<Item> = emptyList()

    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class AllItemsViewHolder(private val binding: ItemAllItemsBinding) :
        ViewHolder(binding.root) {
        fun bind(item: Item) = with(binding) {
            tvItem.text = item.name
            val imageUrl =
                if (item.image.isNotEmpty()) baseUrl + item.image else baseUrl + "/images/${item.id}.png"
            Glide.with(root.context).load(imageUrl).into(ivItem)
            layoutItem.apply {
                var drawableBg = background
                drawableBg = DrawableCompat.wrap(drawableBg)
                DrawableCompat.setTint(drawableBg, Color.parseColor("#${item.color}"))
                background = drawableBg
                setOnClickListener {
                    clickListener?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemsViewHolder {
        val binding =
            ItemAllItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AllItemsViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

}