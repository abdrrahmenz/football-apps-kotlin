package com.example.abdurrahman.footballapps.ui.home

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.abdurrahman.footballapps.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.widget.EditText
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAppBehaviour(){
        Thread.sleep(2000)
        // Check id RecyclerView listNextMatch trus scroll posisi 12 kemudian click row tersebut
        Espresso.onView(ViewMatchers.withId(listNextMatch))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(listNextMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))
        Espresso.onView(ViewMatchers.withId(listNextMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(12, ViewActions.click()))

        Thread.sleep(2000)

        // Setelah masuk ke Activity detail Match diberi action click favorite
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())

        // kembali
        Espresso.pressBack()

        // Melakukan swipe ke kiri (pindah tabs)
        Espresso.onView(ViewMatchers.withId(viewpager)).perform(swipeLeft())

        Thread.sleep(2000)

        // Check id RecyclerView listPrevMatch trus scroll posisi 12 kemudian click row tersebut
        Espresso.onView(ViewMatchers.withId(listPrevMatch))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(listPrevMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))
        Espresso.onView(ViewMatchers.withId(listPrevMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(12, ViewActions.click()))

        Thread.sleep(2000)

        // Setelah masuk ke Activity detail Match diberi action click favorite
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())

        // kembali
        Espresso.pressBack()

        // Melakukan click pada search menu di actionBar untuk mencoba mencari match yg lain
        Espresso.onView(ViewMatchers.withId(search_match))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(search_match)).perform(ViewActions.click())

        Thread.sleep(1000)

        // Melakukan action pada editText searchView untuk menuliskan Barcelona
        Espresso.onView(isAssignableFrom(EditText::class.java)).perform(typeText("Barcelona"),pressImeActionButton())

        // Menutup keyboard dan kembali ke menu awal
        Espresso.closeSoftKeyboard()
        Espresso.pressBack()

        Thread.sleep(1000)

        // Melakukan action click pada bottom navigation untuk tabs teams
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(teams)).perform(ViewActions.click())

        Thread.sleep(3000)

        // Check id RecyclerView listItemTeams trus scroll posisi 13 kemudian click row tersebut
        Espresso.onView(ViewMatchers.withId(listItemTeams))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(listItemTeams)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(13))
        Espresso.onView(ViewMatchers.withId(listItemTeams)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, ViewActions.click()))

        Thread.sleep(2000)

        // Setelah masuk ke Activity detail Teams diberi action click favorite
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())

        // kembali
        Espresso.pressBack()

        Thread.sleep(3000)

        // Melakukan action click pada bottom navigation untuk tabs favorite
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(favorites)).perform(ViewActions.click())

        // Melakukan swipe ke kiri (pindah tabs)
        Espresso.onView(ViewMatchers.withId(viewpagerFavorite)).perform(swipeLeft())

        Thread.sleep(3000)
    }
}