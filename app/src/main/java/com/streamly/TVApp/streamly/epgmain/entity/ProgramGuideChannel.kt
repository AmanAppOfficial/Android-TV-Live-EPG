package com.streamly.TVApp.streamly.entity

import android.text.Spanned

/**
 * A channel which may be associated with multiple programmes.
 * Channels are displayed on the left side of the screen, and display the image you have defined in the URL,
 * and the name to the right of the image. ID is only used for identification purposes, and should be unique.
 */
interface ProgramGuideChannel {
    val id: String
    val name: Spanned?
    val imageUrl: String?
}