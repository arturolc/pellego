package com.example.pellego;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
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

import java.util.concurrent.TimeoutException;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
/** Eli Hebdon
 * Test the navigation for rsvp learning module. Navigate to the intro, go through the beginner submodule,
 * and complete the quiz, then navigate back to the learning module.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RsvpTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void rsvpTest() throws InterruptedException {
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

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.nav_learn), withContentDescription("Learn"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        DataInteraction relativeLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.nav_module_list),
                        childAtPosition(
                                withId(R.id.nav_module_overview),
                                1)))
                .atPosition(0);
        relativeLayout.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.title_module_overview), withText("Rapid Serial Visual Presentation"),
                        withParent(withParent(withId(R.id.frag_technique_overview))),
                        isDisplayed()));
        textView.check(matches(withText("Rapid Serial Visual Presentation")));

        DataInteraction relativeLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.nav_module_list),
                        childAtPosition(
                                withId(R.id.nav_module_overview),
                                1)))
                .atPosition(0);
        relativeLayout2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.header_text_view), withText("What is RSVP?"),
                        withParent(allOf(withId(R.id.item_page_container),
                                withParent(IsInstanceOf.<View>instanceOf(androidx.recyclerview.widget.RecyclerView.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("What is RSVP?")));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        DataInteraction relativeLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.nav_module_list),
                        childAtPosition(
                                withId(R.id.nav_module_overview),
                                1)))
                .atPosition(1);
        relativeLayout3.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withParent(allOf(withId(android.R.id.content),
                        withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        // start beginner rsvp module
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.ok_dialog_button), withText("GO!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        onView(isRoot()).perform(WaitForViewHelper.waitId(R.id.ok_dialog_button, 5000));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.ok_dialog_button), withText("GO!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.title_quiz), withText("Perfection!"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        DataInteraction relativeLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.nav_question_list),
                        childAtPosition(
                                withId(R.id.container_questions),
                                1)))
                .atPosition(0);
        relativeLayout4.perform(click());

        DataInteraction relativeLayout5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.nav_question_list),
                        childAtPosition(
                                withId(R.id.container_questions),
                                1)))
                .atPosition(1);
        relativeLayout5.perform(click());

        DataInteraction relativeLayout6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.nav_question_list),
                        childAtPosition(
                                withId(R.id.container_questions),
                                1)))
                .atPosition(2);
        relativeLayout6.perform(click());

        DataInteraction relativeLayout7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.nav_question_list),
                        childAtPosition(
                                withId(R.id.container_questions),
                                1)))
                .atPosition(3);
        relativeLayout7.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.title_results), withText("Results"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView4.check(matches(withText("Results")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_results_return), withText("Return to Learning Modules"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.text_learn), withText("Learning Modules"),
                        withParent(withParent(withId(R.id.frag_learn))),
                        isDisplayed()));
        textView5.check(matches(withText("Learning Modules")));
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