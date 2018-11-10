package com.uottawa.jasonsmith.homeservicesapp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class LogInPageTest {
    @Rule
    public ActivityTestRule<LogInPage> adminSignInTestRule = new ActivityTestRule<LogInPage>(LogInPage.class);
    private LogInPage mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = adminSignInTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkUserName() throws Exception {
        assertNotNull(mActivity);
        text = mActivity.findViewById(R.id.usernameText);
        text.setText("Silver Rivals");
        String name = text.getText().toString();
        assertNotEquals("SilverRivals", name);
    }

    @Test
    @UiThreadTest
    public void checkPassword() throws Exception{
        assertNotNull(mActivity);
        text = mActivity.findViewById(R.id.PasswordText);
        text.setText("admin1");
        String name = text.getText().toString();
        assertNotEquals("Admin1", name);
    }
}
