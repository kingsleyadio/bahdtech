package com.example.bahdtech.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bahdtech.R
import com.example.bahdtech.api.GithubUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.i_users.*
import ng.kingsley.android.extensions.loadImage

/**
 * @author ADIO Kingsley O.
 * @since 14 Jul, 2019
 */
class UserAdapter(
    private val dataProvider: DataProvider<GithubUser>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    interface DataProvider<T> {
        fun itemAt(position: Int): T
        fun size(): Int
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.i_users, parent, false)
        return ViewHolder(view).apply {
            // do stuff
        }
    }

    override fun getItemCount(): Int {
        return dataProvider.size()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataProvider.itemAt(position))
    }

    override fun getItemId(position: Int): Long {
        return dataProvider.itemAt(position).id.toLong()
    }

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: GithubUser) {
            imageView.loadImage(item.avatarUrl)
            textView.text = item.username
        }
    }
}
