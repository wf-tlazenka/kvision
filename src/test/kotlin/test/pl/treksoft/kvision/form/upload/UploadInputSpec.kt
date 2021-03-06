/*
 * Copyright (c) 2018. Robert Jaros
 */
package test.pl.treksoft.kvision.form.upload

import pl.treksoft.jquery.jQuery
import pl.treksoft.kvision.form.upload.UploadInput
import pl.treksoft.kvision.panel.Root
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test

class UploadInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", true)
            val upi = UploadInput(multiple = true).apply {
                id = "idti"
            }
            root.add(upi)
            val content = document.getElementById("test")?.let { jQuery(it).find("input.form-control")[0]?.outerHTML }
            assertEqualsHtml(
                "<input class=\"form-control\" id=\"idti\" type=\"file\" multiple=\"true\">",
                content,
                "Should render correct file input control for multiple files"
            )
            upi.multiple = false
            val content2 = document.getElementById("test")?.let { jQuery(it).find("input.form-control")[0]?.outerHTML }
            assertEqualsHtml(
                "<input class=\"form-control\" id=\"idti\" type=\"file\">",
                content2,
                "Should render correct file input control for single file"
            )
        }
    }

}
