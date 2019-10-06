package com.mif50.extensions.base.adapter.items

import android.view.View
import com.mif50.extensions.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem

open class ItemLoadingError: AbstractItem<ItemLoadingError.ViewHolder>() {

    override val type: Int
        get() = R.id.item_loading_error

    override val layoutRes: Int
        get() = R.layout.row_loading_error

    override fun getViewHolder(v: View): ViewHolder = ViewHolder((v))

    class ViewHolder(v: View): FastAdapter.ViewHolder<ItemLoadingError>(v){
        override fun bindView(item: ItemLoadingError, payloads: MutableList<Any>) {

        }

        override fun unbindView(item: ItemLoadingError) {

        }

    }
}