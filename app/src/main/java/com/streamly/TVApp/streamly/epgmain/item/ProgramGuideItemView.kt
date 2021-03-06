package com.streamly.TVApp.streamly.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.streamly.TVApp.streamly.R
import com.streamly.TVApp.streamly.entity.ProgramGuideSchedule
import com.streamly.TVApp.streamly.util.ProgramGuideUtil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min


@Suppress("DEPRECATION")
class ProgramGuideItemView<T> : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var schedule: ProgramGuideSchedule<T>? = null

    private val staticItemPadding: Int = resources.getDimensionPixelOffset(R.dimen.programguide_item_padding)

    private var itemTextWidth: Int = 0
    private var maxWidthForRipple: Int = 0
    private var preventParentRelayout = false

    //#neovifyissueresolved28sep
    private val titleView: TextView
    private val titlee1: TextView
    private val progressView: ProgressBar

    init {
        View.inflate(context, R.layout.programguide_item_program, this)

        titleView = findViewById(R.id.title)
        titlee1 = findViewById(R.id.titleview)
        progressView = findViewById(R.id.progress)

    }

    fun setValues(scheduleItem: ProgramGuideSchedule<T>, fromUtcMillis: Long, toUtcMillis: Long,
                  gapTitle: String, displayProgress: Boolean) {
        schedule = scheduleItem
        val layoutParams = layoutParams
        if (layoutParams != null) {
            val spacing = resources.getDimensionPixelSize(R.dimen.programguide_item_spacing)
            layoutParams.width = scheduleItem.width - 2 * spacing // Here we subtract the spacing, otherwise the calculations will be wrong at other places
            setLayoutParams(layoutParams)
        }

        val minutes = (schedule?.startsAtMillis!! / (1000 * 60) % 60)
        val hours = (schedule?.startsAtMillis!! / (1000 * 60 * 60) % 24)
        val time = (hours).toString() + ":" + (minutes).toString()

//        Log.e("PGIV", "::>>>id: " + schedule?.id + "<<>>orgtimes: " + schedule?.originalTimes)
//        Log.e("PGIV", "::>>>starttime: " + schedule?.startsAtMillis + ":::" + schedule?.endsAtMillis)

        val df: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        val time_start: String = df.format(schedule?.startsAtMillis)
//        Log.e("PGIV", "::>>>time_start: " + time_start + "<<>>conversion: " )

        val dfend: DateFormat = SimpleDateFormat("HH:mm", Locale.US)
        dfend.setTimeZone(TimeZone.getTimeZone("GMT"));
        val time_end: String = dfend.format(schedule?.endsAtMillis)

        val schedule_time = time_start + "-" + time_end

        var title = schedule?.displayTitle
        val id = schedule_time

        if (scheduleItem.isGap) {
            title = gapTitle
//            setBackgroundResource(R.drawable.programguide_gap_item_background)
            setBackgroundResource(R.drawable.programguide_item_program_background)
//            titleView.setBackgroundColor(resources.getColor(R.color.appDarkColor))
            isClickable = false
        } else {
            setBackgroundResource(R.drawable.programguide_item_program_background)
            isClickable = scheduleItem.isClickable
        }
        title = if (title?.isEmpty() == true) resources.getString(R.string.programguide_title_no_program) else title

        updateText(title, id)
        initProgress(ProgramGuideUtil.convertMillisToPixel(startMillis = scheduleItem.startsAtMillis, endMillis = scheduleItem.endsAtMillis))
        if (displayProgress) {
            updateProgress(System.currentTimeMillis())
        } else {
            progressView.visibility = View.GONE
        }

        titleView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        itemTextWidth = titleView.measuredWidth - titleView.paddingLeft - titleView.paddingRight
        // Maximum width for us to use a ripple
        maxWidthForRipple = ProgramGuideUtil.convertMillisToPixel(fromUtcMillis, toUtcMillis)
    }

    private fun updateText(title: String?, id: String?) {
        titlee1.text = id
        titleView.text =  title
    }

    private fun initProgress(width: Int) {
        progressView.max = width
    }

    //#neovifyissueresolved28sep
    fun updateProgress(now: Long) {
        with(progressView) {
            schedule?.let {
                if (it.isCurrentProgram.not()) {
                    visibility = View.GONE

                    if (System.currentTimeMillis() <= it.startsAtMillis) {
                        titlee1.setTextColor(resources.getColor(R.color.md_white_1000))
                        titleView.setTextColor(resources.getColor(R.color.md_white_1000))

                    } else {
                        titlee1.setTextColor(resources.getColor(R.color.md_white_60))
                        titleView.setTextColor(resources.getColor(R.color.md_white_60))
                    }

                } else {
                    visibility = View.VISIBLE
                    progressView.progress = ProgramGuideUtil.convertMillisToPixel(it.startsAtMillis, now)
                }
            }
        }
    }

    /** Update programItemView to handle alignments of text. */
    fun updateVisibleArea() {
        val parentView = parent as View
        layoutVisibleArea(parentView.left + parentView.paddingLeft - left, right - parentView.right)
    }

    /**
     * Layout title and episode according to visible area.
     *
     *
     * Here's the spec.
     * 1. Don't show text if it's shorter than 48dp.
     * 2. Try showing whole text in visible area by placing and wrapping text, but do not wrap text less than 30min.
     * 3. Episode title is visible only if title isn't multi-line.
     *
     * @param startOffset Offset of the start position from the enclosing view's start position.
     * @param endOffset Offset of the end position from the enclosing view's end position.
     */
    private fun layoutVisibleArea(startOffset: Int, endOffset: Int) {
        val width = schedule?.width ?: 0
        var startPadding = max(0, startOffset)
        var endPadding = max(0, endOffset)
        val minWidth = min(width, itemTextWidth + 2 * staticItemPadding)
        if (startPadding > 0 && width - startPadding < minWidth) {
            startPadding = max(0, width - minWidth)
        }
        if (endPadding > 0 && width - endPadding < minWidth) {
            endPadding = max(0, width - minWidth)
        }

        if (startPadding + staticItemPadding != paddingStart || endPadding + staticItemPadding != paddingEnd) {
            preventParentRelayout = true // The size of this view is kept, no need to tell parent.
            titleView.setPaddingRelative(startPadding + staticItemPadding, 0, endPadding + staticItemPadding, 0)
            preventParentRelayout = false
        }
    }

    override fun requestLayout() {
        if (preventParentRelayout) {
            // Trivial layout, no need to tell parent.
            forceLayout()
        } else {
            super.requestLayout()
        }
    }

    fun clearValues() {
        tag = null
        schedule = null
    }
}