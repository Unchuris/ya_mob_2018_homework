package espresso

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.kissedcode.finance.R
import com.kissedcode.finance.main_screen.MainActivity
import com.kissedcode.finance.repository.Rate
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.allOf
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class AddTransactionTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            InstrumentationRegistry.getTargetContext().deleteDatabase("tracker")
        }
    }

    @Rule
    @JvmField
    val mainActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun addTransaction() {
        onView(withId(R.id.accountsRv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,
                        clickChildViewWithId(R.id.accountNewOperationIb)))
        onView(allOf(withId(R.id.etTransactionSum), isDisplayed()))
                .perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.btnCreateTransaction)).perform(scrollTo(), click())
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(RecyclerViewMatcher(R.id.post_list)
                .atPositionOnView(0, R.id.tv_amount))
                .check(matches(withText("100,00")))
        onView(RecyclerViewMatcher(R.id.post_list)
                .atPositionOnView(0, R.id.tv_group_name))
                .check(matches(withText("Food")))
    }

    @Test
    fun changeBalance() {
        onView(withId(R.id.accountsRv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,
                        clickChildViewWithId(R.id.accountNewOperationIb)))
        onView(allOf(withId(R.id.etTransactionSum), isDisplayed()))
                .perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.btnCreateTransaction)).perform(scrollTo(), click())

        onView(RecyclerViewMatcher(R.id.accountsRv)
                .atPositionOnView(1, R.id.accountBalanceTv))
                .check(matches(withText("-100,00" + " " + "₽")))
    }

    @Test
    fun changeBalanceConvert() {
        onView(withId(R.id.accountsRv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        clickChildViewWithId(R.id.accountNewOperationIb)))
        onView(allOf(withId(R.id.etTransactionSum), isDisplayed()))
                .perform(typeText("6300"), closeSoftKeyboard())
        onView(withId(R.id.btnCreateTransaction)).perform(scrollTo(), click())

        val amount = -6300 / Rate.rate

        onView(RecyclerViewMatcher(R.id.accountsRv)
                .atPositionOnView(0, R.id.accountBalanceTv))
                .check(matches(withText(String.format("%.2f", amount) + " " + "$")))
    }

}
