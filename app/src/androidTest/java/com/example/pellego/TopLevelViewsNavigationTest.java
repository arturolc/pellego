package com.example.pellego;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.amplifyframework.core.Amplify;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/** Eli Hebdon
 * Test the navigation for the top level views of the app i.e. the bottom navigation and side nav views.
 * This test navigates to each view and verifies the title displayed on the screen matches the fragment.
 *
 * NOTE: Please re-record this test when major changes occur to the main UI of the APP
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TopLevelViewsNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void topLevelViewsNavigationTest() throws InterruptedException {
        Thread.sleep(5000);

        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i("AmplifyQuickstart", result.toString());
                    if (!result.isSignedIn()) { // sign in if not already
                        ViewInteraction appCompatEditText = onView(
                                allOf(withId(R.id.editTextTextEmailAddress),
                                        childAtPosition(
                                                childAtPosition(
                                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                        0),
                                                3),
                                        isDisplayed()));
                        appCompatEditText.perform(replaceText("elihebdon@gmail.com"), closeSoftKeyboard());

                        ViewInteraction appCompatEditText2 = onView(
                                allOf(withId(R.id.editTextTextPassword),
                                        childAtPosition(
                                                childAtPosition(
                                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                        0),
                                                5),
                                        isDisplayed()));
                        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());

                        ViewInteraction appCompatEditText3 = onView(
                                allOf(withId(R.id.editTextTextPassword),
                                        childAtPosition(
                                                childAtPosition(
                                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                        0),
                                                5),
                                        isDisplayed()));
                        appCompatEditText3.perform(click());

                        ViewInteraction appCompatEditText4 = onView(
                                allOf(withId(R.id.editTextTextPassword),
                                        childAtPosition(
                                                childAtPosition(
                                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                        0),
                                                5),
                                        isDisplayed()));
                        appCompatEditText4.perform(replaceText("speedread45!"), closeSoftKeyboard());

                        ViewInteraction materialButton = onView(
                                allOf(withId(R.id.button2), withText("Sign In"),
                                        childAtPosition(
                                                childAtPosition(
                                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                        0),
                                                6),
                                        isDisplayed()));
                        materialButton.perform(click());
                    }
                },
                error -> {
                    Log.e("AmplifyQuickstart", error.toString());
                }
        );
        Thread.sleep(3000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.text_library), withText("Library"),
                        withParent(allOf(withId(R.id.lib_linear_layout),
                                withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Library")));

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.nav_learn), withContentDescription("Learn"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.text_learn), withText("Learning Modules"),
                        withParent(withParent(withId(R.id.frag_learn))),
                        isDisplayed()));
        textView2.check(matches(withText("Learning Modules")));

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.nav_progress), withContentDescription("Progress"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.text_progress), withText("Progress Reports"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView3.check(matches(withText("Progress Reports")));

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.nav_settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav_view),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.text_settings), withText("Settings"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView4.check(matches(withText("Settings")));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.design_navigation_view),
                        withParent(allOf(withId(R.id.side_nav_view),
                                withParent(withId(R.id.home_layout)))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_profile),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.side_nav_view),
                                                0)),
                                2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.text_profile), withText("Profile"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView5.check(matches(withText("Profile")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.nav_settings),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.side_nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.text_settings), withText("Settings"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView6.check(matches(withText("Settings")));

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(withId(R.id.nav_terms_and_conditions),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.side_nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.text_terms_and_conditions), withText("Terms and Conditions"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView7.check(matches(withText("Terms and Conditions")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
