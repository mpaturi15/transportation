package com.macys.mst.transportation.functional.execdrivers;

import java.util.HashMap;
import java.util.Map;

import com.macys.mst.artemis.config.FileConfig;
import com.macys.mst.artemis.config.GetPasswordCyberArk;
import com.macys.mst.artemis.serenityJbehaveJira.Executiondriver;
import com.macys.mst.artemis.testNg.LocalDriverManager;

public class ExecutionConfig extends Executiondriver {
	public String execenv = LocalDriverManager.getInstance().getexecenvflag();
	public static ThreadLocal<String> passwordobj = new ThreadLocal<String>();
	public static ThreadLocal<String> cyberarksafe = new ThreadLocal<String>();
	public static ThreadLocal<String> cyberarkappid = new ThreadLocal<String>();

	public static void setHeaders(Map<String, String> headers) {
		ExecutionConfig.headers = headers;
	}

	public static Map<String, String> headers = new HashMap<String, String>();

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ExecutionConfig.password = password;
	}

	private static String password;

	@Override
	public void calserenityjiralocaltestrunner() {
		SerenityJiraRunConfig jirR = new SerenityJiraRunConfig();
		getDbPwd();
		try {
			jirR.run();
			// new SerenityLocalRunConfig();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	@Override
	public void calserenitylocaltestrunner() {
		SerenityLocalRunConfig SerRun = new SerenityLocalRunConfig();
		getDbPwd();
		try {
			SerRun.run();
		} catch (Throwable e) {

			e.printStackTrace();

		}

	}

	private void getDbPwd() {
		cyberarksafe.set(FileConfig.getInstance().getStringConfigValue("cyberark.safe"));
		cyberarkappid.set(FileConfig.getInstance().getStringConfigValue("cyberark.appid"));
		passwordobj.set(FileConfig.getInstance().getStringConfigValue("cyberark.pwdobjectid"));
		password = (GetPasswordCyberArk.getpassword(cyberarksafe.get(), cyberarkappid.get(), passwordobj.get()));
		setPassword(password);
	}

	public static Map<String, String> getHeaders() {
		Map<String, String> headersArg = new HashMap<String, String>();
		//headersArg.put("X-MY-CLIENT","testAutomation");
		setHeaders(headersArg);
		return headersArg;
	}

}