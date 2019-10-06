package com.mif50.extensions.ktx

import androidx.recyclerview.widget.LinearLayoutManager
import com.mif50.extensions.base.adapter.items.ItemLoadingError
import com.mif50.extensions.base.adapter.items.ItemLoadingProgress
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter

fun FastAdapter<IItem<*>> .addFooterAdapter(footerAdapter: ItemAdapter<IItem<*>>){
    addAdapter(1 ,footerAdapter )
}

fun FastAdapter<IItem<*>> .isEmpty():Boolean{
    return itemCount == 0
}

fun ItemAdapter<IItem<*>>.addLoadingItem(){
    clear()
    // .withEnabled(false)
    add(ItemLoadingProgress())
}

fun ItemAdapter<IItem<*>>.addErrorItem() {
    clear()
    // add item loading error
    add(ItemLoadingError())
}

fun LinearLayoutManager.isLastItem(): Boolean{
    val visibleItemCount = childCount
    val totalItemCount = itemCount
    val firstItemPositionInLayoutManager = findFirstVisibleItemPosition()
    return visibleItemCount + firstItemPositionInLayoutManager >= totalItemCount && firstItemPositionInLayoutManager > 0
}
