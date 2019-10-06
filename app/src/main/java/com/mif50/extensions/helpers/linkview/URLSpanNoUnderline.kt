package com.mif50.extensions.helpers.linkview

import android.text.TextPaint
import android.text.style.URLSpan

/**
 * Created by BeinMedia @2018.
 */

class URLSpanNoUnderline constructor(p_Url: String) : URLSpan(p_Url) {

    override fun updateDrawState(p_DrawState: TextPaint) {
        super.updateDrawState(p_DrawState)
        p_DrawState.isUnderlineText = false
    }
}
