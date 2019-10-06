package com.mif50.extensions.base.adapter.items

import android.view.View
import com.mif50.extensions.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem

open class ItemLoadingProgress() : AbstractItem<ItemLoadingProgress.ViewHolder>(){

    override val type: Int
        get() = R.id.item_loading_progress

    override val layoutRes: Int
        get()  = R.layout.row_loading_progress

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)



    class ViewHolder(v: View): FastAdapter.ViewHolder<ItemLoadingProgress>(v) {

        override fun bindView(item: ItemLoadingProgress, payloads: MutableList<Any>) {

        }

        override fun unbindView(item: ItemLoadingProgress) {

        }

    }
}