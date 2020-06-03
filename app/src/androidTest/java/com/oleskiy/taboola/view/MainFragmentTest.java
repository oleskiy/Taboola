package com.oleskiy.taboola.view;

import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.oleskiy.taboola.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class MainFragmentTest  {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mainFragmentActivityTestRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void testOnViewCreated() {

        //Espresso.onView(withId(R.id.rv_taboola)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
        try {
            mainFragmentActivityTestRule.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Espresso.onView(withId(R.id.rv_taboola)).perform(RecyclerViewActions.scrollToPosition(9));
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }
}