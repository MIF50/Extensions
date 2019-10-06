package com.mif50.extensions.helpers.linkview

import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mif50.extensions.R


import androidx.browser.customtabs.CustomTabsIntent


/**
 * Created by BeinMedia @2018.
 */

open class RichLinkView : RelativeLayout {

    internal var context: Context
     var metaData: MetaData? = null
        private set

    lateinit var linearLayout: LinearLayoutCompat
    lateinit var imageView: AppCompatImageView
    lateinit var textViewTitle: AppCompatTextView
    lateinit var textViewDesp: AppCompatTextView
    lateinit var textViewUrl: AppCompatTextView
    lateinit var textViewOriginalUrl: AppCompatTextView

    private var mainUrl: String? = null

    private var isDefaultClick = true

    private var richLinkListener: RichLinkListener? = null

    private var customTabsIntent: CustomTabsIntent? = null

    constructor(context: Context) : super(context) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.context = context
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        this.context = context
    }

    fun initView() {

        setCustomTab()

        val view: View?
        if (findLinearLayoutChild() != null) {
            view = findLinearLayoutChild()
        } else {
            view = this
            View.inflate(context, R.layout.layout_link, this)
        }

        linearLayout = findViewById(R.id.rich_link_card)
        imageView = findViewById(R.id.rich_link_image)
        textViewTitle = findViewById(R.id.rich_link_title)
        textViewDesp = findViewById(R.id.rich_link_desp)
        textViewUrl = findViewById(R.id.rich_link_url)

        textViewOriginalUrl = findViewById(R.id.rich_link_original_url)

        textViewOriginalUrl.text = mainUrl
        removeUnderlines(textViewOriginalUrl.text as Spannable)

        if (metaData!!.imageurl == "" || metaData!!.imageurl.isEmpty()) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            try {
                Glide.with(context).load(metaData!!.imageurl)
                    .apply(RequestOptions().placeholder(R.color.grayLight).circleCrop())
                    .into(imageView)
            } catch (ignored: IllegalArgumentException) {
            }

        }

        if (metaData!!.title.isEmpty() || metaData!!.title == "") {
            textViewTitle.visibility = View.GONE
        } else {
            textViewTitle.visibility = View.VISIBLE
            textViewTitle.text = metaData!!.title
        }
        if (metaData!!.url.isEmpty() || metaData!!.url == "") {
            textViewUrl.visibility = View.GONE
        } else {
            textViewUrl.visibility = View.VISIBLE
            textViewUrl.text = metaData!!.url
        }
        if (metaData!!.description.isEmpty() || metaData!!.description == "") {
            textViewDesp.visibility = View.GONE
        } else {
            textViewDesp.visibility = View.VISIBLE
            textViewDesp.text = metaData!!.description
        }

        linearLayout.setOnClickListener { view ->
            if (isDefaultClick) {
                richLinkClicked()
            } else {
                if (richLinkListener != null) {
                    richLinkListener!!.onClicked(view, metaData!!)
                } else {
                    richLinkClicked()
                }
            }
        }

        textViewOriginalUrl.setOnClickListener { linearLayout.performClick() }
    }

    private fun setCustomTab() {
        customTabsIntent = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(
                this.resources
                    .getColor(R.color.colorPrimary)
            )
            .setShowTitle(true)
            .build()
    }

    private fun richLinkClicked() {
        customTabsIntent!!.launchUrl(context, Uri.parse(mainUrl))
    }

     fun findLinearLayoutChild(): LinearLayoutCompat? {
        return if (childCount > 0 && getChildAt(0) is LinearLayoutCompat) {
            getChildAt(0) as LinearLayoutCompat
        } else null
    }

    fun setLinkFromMeta(metaData: MetaData) {
        this.metaData = metaData
        initView()
    }


    fun setDefaultClickListener(isDefault: Boolean) {
        isDefaultClick = isDefault
    }

    fun setClickListener(richLinkListener1: RichLinkListener) {
        richLinkListener = richLinkListener1
    }

    fun setLink(url: String, viewListener: ViewListener) {
        mainUrl = url
        val richPreview = RichPreview(object : ResponseListener {
            override fun onData(withMetaData: MetaData) {
                metaData = withMetaData

                if (metaData!!.title.isNotEmpty()) {
                    viewListener.onSuccess(true)
                }

                initView()
            }

            override fun onError(e: Exception) {
                viewListener.onError(e)
            }
        })
        richPreview.getPreview(url)
    }

    private fun removeUnderlines(p_Text: Spannable) {
        val spans = p_Text.getSpans(0, p_Text.length, URLSpan::class.java)

        spans.map {
            val start = p_Text.getSpanStart(it)
            val end = p_Text.getSpanEnd(it)
            p_Text.removeSpan(it)
            p_Text.setSpan(URLSpanNoUnderline(it.url), start, end, 0)
        }
    }

}
