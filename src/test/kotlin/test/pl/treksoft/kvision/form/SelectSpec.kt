package test.pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Root
import pl.treksoft.kvision.form.SELECTWIDTHTYPE
import pl.treksoft.kvision.form.Select
import test.pl.treksoft.kvision.DomSpec
import kotlin.browser.document
import kotlin.test.Test
import kotlin.test.assertTrue

class SelectSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test")
            val select = Select(listOf("test1" to "Test 1", "test2" to "Test 2"), "test1", true, "Label").apply {
                liveSearch = true
                placeholder = "Choose ..."
                selectWidthType = SELECTWIDTHTYPE.FIT
                emptyOption = true
            }
            root.add(select)
            val element = document.getElementById("test")
            val id = select.input.id
            assertTrue(true == element?.innerHTML?.startsWith("<div class=\"form-group\"><label for=\"$id\">Label</label>"), "Should render correct select form field")
            assertTrue(true == element?.innerHTML?.endsWith("<select class=\"form-control selectpicker\" id=\"$id\" multiple=\"\" data-live-search=\"true\" title=\"Choose ...\" data-style=\"btn-default\" data-width=\"fit\" tabindex=\"-98\"><option value=\"#kvnull\"></option><option value=\"test1\">Test 1</option><option value=\"test2\">Test 2</option></select></div></div>"), "Should render correct select form field")
        }
    }

}