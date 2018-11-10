package com.uottawa.jasonsmith.homeservicesapp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class createNewAccountTest {
    @Rule
    public ActivityTestRule<CreateNewAccount> createNewAccountActivityTestRule = new ActivityTestRule<CreateNewAccount>(CreateNewAccount.class);
    private CreateNewAccount mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = createNewAccountActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkUserName() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.createUsername);
        text.setText("brose035");
        String name = text.getText().toString();
        assertNotEquals(" ", name);
    }

    @Test
    @UiThreadTest
    public void checkEmail() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.email);
        text.setText("brose035@uotttawa.ca");
        String name = text.getText().toString();
        assertNotEquals(" ", name);
    }

    @Test
    @UiThreadTest
    public void checkAddress() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.address);
        text.setText("91 University");
        String name = text.getText().toString();
        assertNotEquals(" ", name);
    }

    @Test
    @UiThreadTest
    public void checkPassword() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.createPassword);
        text.setText("Password");
        String name = text.getText().toString();
        assertNotEquals(" ", name);
    }
}
