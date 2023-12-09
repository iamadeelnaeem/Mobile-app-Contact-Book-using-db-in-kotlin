package com.example.madassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyCustomAdapter(private var mList: List<User>) :
    RecyclerView.Adapter<MyCustomAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Click listener variable
    private var onItemClickListener: OnItemClickListener? = null

    // Method to set the click listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
    // Holds the views for adding it to image and text
    inner class ViewHolder(currentView: View) :
        RecyclerView.ViewHolder(currentView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.onItemClick(position)
                }
            }
        }
        fun bind(product: User) {
            val imageView: TextView = itemView.findViewById(R.id.circular_text_view)
            val nameTv: TextView = itemView.findViewById(R.id.name)
            val priceTv: TextView = itemView.findViewById(R.id.price)

            nameTv.text = product.name
            priceTv.text = product.phone.toString()
            val firstChar = if (!product.name.isNullOrEmpty()) product.name[0] else ' '
            imageView.text = firstChar.toString()
         // imageView.setImageResource(product.image)
        }
    }

    // Creates new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item,
            parent, false
        )
        return ViewHolder(view)
    }

    // Binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = mList[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    // Returns the number of items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Function to update the data based on the filtered list
    fun updateData(newList: List<User>) {
        mList = newList
        notifyDataSetChanged()
    }
}
