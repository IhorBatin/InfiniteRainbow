package com.example.infiniterainbow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.infiniterainbow.R
import com.example.infiniterainbow.adapter.ColorsListAdapter.ColorsListViewHolder

class ColorsListAdapter(private var colorsList: MutableList<Int>) : RecyclerView.Adapter<ColorsListViewHolder>() {

    var onCardClicked: ((CardView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsListViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.color_item, parent, false)

        return ColorsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorsListViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.colorCardView.setCardBackgroundColor(colorsList[position])
    }

    override fun getItemCount() = colorsList.size

    fun addMoreItems(additionalList: MutableList<Int>) {
        val lastItemPosition = colorsList.size
        colorsList += additionalList
        notifyItemInserted(lastItemPosition)
    }

    inner class ColorsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorCardView: CardView = itemView.findViewById(R.id.cvColorItem)

        init {
            // Define click listener for the ViewHolder's View.
            colorCardView.setOnClickListener {
                onCardClicked?.invoke(it as CardView)
            }
        }
    }
}