package com.ohtu.wearable.wearabledataservice.server;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sjsaarin on 27.3.2015.
 */
@RunWith(CustomRobolectricRunner.class)
public class SensorHTTPServerTest {
    @Test
    public void alwaysPassingTest(){
        assertThat(true, is(true));
    }

}
