package com.mif50.extensions.helpers.ui.touch

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemTouchHelper(dragDirs: Int,
                              swipeDirs: Int,
                              private var listener: RecyclerItemTouchHelperListener?) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listener?.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }


    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        // check that is viewHolder
        // than find view of the foregroundCard

        // example
//        if (viewHolder is ItemConversationRow.ItemConversationRowViewHolder) {
//            getDefaultUIUtil().clearView(viewHolder.itemView.foregroundCard)
//        }
    }


    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//        if (viewHolder is ItemConversationRow.ItemConversationRowViewHolder) {
//            val foreground = viewHolder.itemView.foregroundCard
//            getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
//        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
//        if (viewHolder != null) {
//            if (viewHolder is ItemConversationRow.ItemConversationRowViewHolder) {
//                val foreground = viewHolder.itemView.foregroundCard
//                getDefaultUIUtil().onSelected(foreground)
//            }
//        }
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//        if (viewHolder is ItemConversationRow.ItemConversationRowViewHolder) {
//            val foreground = viewHolder.itemView.foregroundCard
//            getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
//        }
    }


}

interface RecyclerItemTouchHelperListener {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
}