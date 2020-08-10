package com.macys.mst.transportation.functional.pageobjects;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.artemis.testNg.LocalDriverManager;
import com.macys.mst.transportation.functional.util.CommonUtils;
import com.macys.mst.transportation.functional.util.StepsDataStore;
import com.macys.mst.transportation.functional.util.UserType;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

public class FLODBIntegrationPage extends BasePage {
	StepsDataStore dataStorage = StepsDataStore.getInstance();

	private static Logger log = Logger.getLogger(FLODBIntegrationPage.class.getName());
	static LocalDriverManager localDriverManager = LocalDriverManager.getInstance();
	public static WebDriver driver = localDriverManager.getDriver();
	public int loadid;
    
	public void FLODBLogin() throws InterruptedException {
        
	}
		          		
}
