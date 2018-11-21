package com.uottawa.jasonsmith.homeservicesapp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class activity_sp_informationTest {
    @Rule
    public ActivityTestRule<activity_SP_information> spInformationTestRule = new ActivityTestRule<activity_SP_information>(activity_SP_information.class);
    private activity_SP_information mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = spInformationTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkCompanyName() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.companyName);
        text.setText("Silver Rivals");
        String name = text.getText().toString();
        assertNotEquals(" ", name);
    }

    @Test
    @UiThreadTest
    public void checkPhone() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.phoneNumber);
        text.setText("999-999-9999");
        String number = text.getText().toString();
        assertNotEquals("9999999999", number);
    }

    @Test
    @UiThreadTest
    public void checkLicense() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.license);
        text.setText("yes");
        String answer = text.getText().toString();
        assertNotEquals(" ", answer);
    }

    @Test
    @UiThreadTest
    public void checkDescription() throws Exception{
        assertNotNull(mActivity.findViewById(R.id.CreatePrompt));
        text = mActivity.findViewById(R.id.description);
        text.setText("This is where you would describe what it is that your company will do");
        String describe = text.getText().toString();
        assertNotEquals(" ", describe);
    }
}
