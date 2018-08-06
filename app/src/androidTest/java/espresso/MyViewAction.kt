package espresso

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher

fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click"
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById<View>(id)
            v.performClick()
        }
    }
}
