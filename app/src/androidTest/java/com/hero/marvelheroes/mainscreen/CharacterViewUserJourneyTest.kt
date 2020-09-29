
package com.hero.marvelheroes.mainscreen


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import com.azimolabs.conditionwatcher.ConditionWatcher.waitForCondition
import com.azimolabs.conditionwatcher.Instruction
import com.hero.marvelheroes.R
import com.hero.marvelheroes.mainscreen.SwipeRefreshLayoutMatchers.isRefreshing
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CharacterViewUserJourneyTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun characterViewUserJourneyTest() {

        val TreeDMan = "3-D Man"

        waitAndCheckRefresh()

        checkCharacter(TreeDMan)

        val recyclerView2 = clickOnCharacterInPosition(0)

        recyclerView2?.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        onView(allOf(withId(R.id.text_view_character_name))).apply { check(matches(withText(TreeDMan))) }

        onView(withId(R.id.image_view_character_image)).apply { check(matches(isDisplayed())) }

        AppConditionWatcher.waitUntil {
            onView(withId(R.id.toolbar)).checkAndReturn(matches(isDisplayed()))
        }

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).apply { pressBack() }

        waitAndCheckRefresh()

        checkCharacter(TreeDMan)
    }

    private fun clickOnCharacterInPosition(position: Int): ViewInteraction? {
        return onView(
            allOf(
                withId(R.id.recycler_view_characters),
                childAtPosition(withId(R.id.swipe_container), position)
            )
        )
    }

    private fun checkCharacter(name: String) {
        onView(
            allOf(
                withId(R.id.text_view_character_name), withText(name)
            )
        ).check(matches(isDisplayed()))
    }

    private fun waitAndCheckRefresh() {
        AppConditionWatcher.waitUntil {
            onView(withId(R.id.swipe_container)).checkAndReturn(matches(isRefreshing()))

            onView(withId(R.id.swipe_container)).checkAndReturn(matches(not(isRefreshing())))
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}

fun ViewInteraction.checkAndReturn(viewAssert: ViewAssertion) = try {
    check(viewAssert)
    true
} catch (e: Throwable) {
    false
}

object AppConditionWatcher {
    fun waitUntil(condition: () -> Boolean) = waitForCondition(ConditionInstruction(condition))

    private class ConditionInstruction(private val condition: () -> Boolean) : Instruction() {
        override fun getDescription(): String = ConditionInstruction::class.java.simpleName
        override fun checkCondition(): Boolean = condition()
    }
}

object SwipeRefreshLayoutMatchers {
    @JvmStatic
    fun isRefreshing(): Matcher<View> {
        return object : BoundedMatcher<View, SwipeRefreshLayout>(
            SwipeRefreshLayout::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("is refreshing")
            }

            override fun matchesSafely(view: SwipeRefreshLayout): Boolean {
                return view.isRefreshing
            }
        }
    }
}
