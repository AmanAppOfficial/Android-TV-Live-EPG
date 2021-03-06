package com.streamly.TVApp.streamly

import com.streamly.TVApp.streamly.entity.ProgramGuideSchedule

interface ProgramGuideHolder<T> {
    val programGuideGrid : ProgramGuideGridView<T>
    val programGuideManager : ProgramGuideManager<T>

    fun getTimelineRowScrollOffset(): Int
    fun onScheduleClickedInternal(schedule: ProgramGuideSchedule<T>)

    val DISPLAY_SHOW_PROGRESS : Boolean
}