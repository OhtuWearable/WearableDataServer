package com.ohtu.wearable.wearabledataservice.fragments;

import android.app.Activity;
import android.widget.Button;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;
import com.ohtu.wearable.wearabledataservice.R;
import com.ohtu.wearable.wearabledataservice.fragments.FragmentThree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.SupportFragmentTestUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertNotNull;

//import static org.junit.Assert.assertThat;

@RunWith(CustomRobolectricRunner.class)
public class FragmentThreeTest {
    private FragmentThree fragment;


    @Before
    public void setUp() {
        fragment = new FragmentThree();
        SupportFragmentTestUtil.startFragment(fragment);
    }


    /*@Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull( fragment );
    }*/


    @Test
    public void shouldNotBeNull() {
        Activity activity = fragment.getActivity();
        //assertThat(activity).isNotNull();
        assertThat(activity, notNullValue());

        Button bn1 = (Button) activity.findViewById(R.id.btnaddnewtext1);
        //assertThat(bn1).isNotNull();
        //assertThat(bn1, notNullValue());

        Button bn2 = (Button) activity.findViewById(R.id.btnaddnewtext2);
        //assertThat(bn2).isNotNull();
        //assertThat(bn2, notNullValue());

    }

    /*@Test
    public void shouldStopServerWhenButtonPressed() {

        Button button = (Button) activity.findViewById(R.id.button);
        button.performClick();

        //assertThat(textView).containsText("Hello, Peter!");
    }*/

    /*@Test
    public void shouldStartServerWhenButtonPressed() {

        Button button = (Button) activity.findViewById(R.id.button);
        button.performClick();

        //assertThat(textView).containsText("Hello, Peter!");
    }*/


    /*@Test
    public void shouldNotBeNull() {
        assertThat(activity).isNotNull();

        Button bn2 = (Button) activity.findViewById(R.id.btnaddnewtext2);
        assertThat(bn2).isNotNull();

    }*/

    /*@Test
    public void shouldShowBatteryLevelWhenButtonPressed() {

        Button button = (Button) activity.findViewById(R.id.button);
        button.performClick();

        //assertThat(textView).containsText("Hello, Peter!");
    }*/

    @Test
    public void activityShouldNotBeNull() throws Exception {
        Activity activity = fragment.getActivity();
        assertNotNull(activity);
    }

}
