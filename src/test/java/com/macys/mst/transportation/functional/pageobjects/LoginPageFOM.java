package com.macys.mst.transportation.functional.pageobjects;

import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mtp.reportsutils.StepLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.artemis.config.GetPasswordCyberArk;
import com.macys.mst.transportation.functional.stepdefinitions.MIR201EndtoEndFlow;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;
import com.macys.mtp.reportsutils.StepLogger;
import io.qameta.allure.Step;

public class LoginPageFOM extends BasePage {

        private static Logger log = Logger.getLogger(com.macys.mst.transportation.functional.pageobjects.Home.class.getName());
        private String cyberarkSafe = FileConfig.getInstance().getStringConfigValue("cyberark.safe");
        private String cyberarkAppid = FileConfig.getInstance().getStringConfigValue("cyberark.appid");
        private String cyberarkPwdObjId = FileConfig.getInstance().getStringConfigValue("cyberark.AppPwdobjectid");
        private String formURL = FileConfig.getInstance().getStringConfigValue("AppUrls.FOMurl");
        private String username = FileConfig.getInstance().getStringConfigValue("AppUrls.userName");
        private String password = FileConfig.getInstance().getStringConfigValue("AppUrls.password");
        private String signin = FileConfig.getInstance().getStringConfigValue("AppUrls.signIn");

        StepsDataStore dataStorage = StepsDataStore.getInstance();
        StepLogger stepLogger = new StepLogger();

        @FindBy(xpath="//input[@name='USER_NAME']")
        WebElement userName;

        @FindBy(xpath = "//input[@name='PASSWORD']")
        WebElement passWord;

        @FindBy(xpath= "//span[@class='loginInner']")
        WebElement signIn;



}
