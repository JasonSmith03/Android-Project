package com.uottawa.jasonsmith.homeservicesapp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class adminInterfaceTest {
    @Rule
    public ActivityTestRule<admin_interface> adminInterfaceTestRule = new ActivityTestRule<admin_interface>(admin_interface.class);
    private admin_interface mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = adminInterfaceTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkServiceName() throws Exception {
        assertNotNull(mActivity);
        text = mActivity.findViewById(R.id.service);
        text.setText("Refrigerator repair");
        String name = text.getText().toString();
        assertNotEquals(" ", name);
    }

    @Test
    @UiThreadTest
    public void checkRate() throws Exception{
        assertNotNull(mActivity);
        text = mActivity.findViewById(R.id.rate);
        text.setText("120");
        String testRate = text.getText().toString();
        assertNotEquals(" ", testRate);
    }
}
