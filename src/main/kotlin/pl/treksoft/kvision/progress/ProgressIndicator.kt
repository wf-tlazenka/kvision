/*
 * Copyright (c) 2018. Robert Jaros
 */
package pl.treksoft.kvision.progress

import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.core.StringPair
import pl.treksoft.kvision.html.Align
import pl.treksoft.kvision.html.Div
import pl.treksoft.kvision.utils.perc

/**
 * Progress bar styles.
 */
enum class ProgressBarStyle(internal val className: String) {
    SUCCESS("progress-bar-success"),
    INFO("progress-bar-info"),
    WARNING("progress-bar-warning"),
    DANGER("progress-bar-danger")
}

internal const val DEFAULT_MIN = 0
internal const val DEFAULT_MAX = 100


/**
 * The Bootstrap progress bar indicator.
 *
 * @constructor
 * @param progress the current progress
 * @param min the minimal progress
 * @param max the maximal progress
 * @param style the style of the progress bar indicator
 * @param striped determines if the progress bar indicator is striped
 * @param animated determines if the progress bar indicator is animated
 * @param content element text
 * @param rich determines if [content] can contain HTML code
 * @param align content align
 * @param classes a set of CSS class names
 */
internal class ProgressIndicator(
    progress: Int, min: Int = DEFAULT_MIN, max: Int = DEFAULT_MAX, style: ProgressBarStyle? = null,
    striped: Boolean = false, animated: Boolean = false,
    content: String? = null, rich: Boolean = false, align: Align? = null,
    classes: Set<String> = setOf()
) :
    Div(content, rich, align, classes) {

    /**
     * The current progress.
     */
    var progress by refreshOnUpdate(progress, { refreshWidth() })
    /**
     * The minimal progress.
     */
    var min by refreshOnUpdate(min, { refreshWidth() })
    /**
     * The maximal progress.
     */
    var max by refreshOnUpdate(max, { refreshWidth() })
    /**
     * The style of the progress indicator.
     */
    var style by refreshOnUpdate(style)
    /**
     * Determines if the progress indicator is striped.
     */
    var striped by refreshOnUpdate(striped)
    /**
     * Determines if the progress indicator is animated.
     */
    var animated by refreshOnUpdate(animated)

    init {
        role = "progressbar"
        refreshWidth()
    }

    private fun refreshWidth() {
        val value = (if (max - min > 0) (progress - min) * DEFAULT_MAX.toFloat() / (max - min) else 0f).toInt()
        val percent = if (value < 0) 0 else if (value > DEFAULT_MAX) DEFAULT_MAX else value
        width = percent.perc
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("progress-bar" to true)
        style?.let {
            cl.add(it.className to true)
        }
        if (striped || animated) {
            cl.add("progress-bar-striped" to true)
        }
        if (animated) {
            cl.add("active" to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("aria-valuenow" to "$progress")
        sn.add("aria-valuemin" to "$min")
        sn.add("aria-valuemax" to "$max")
        return sn
    }
}
