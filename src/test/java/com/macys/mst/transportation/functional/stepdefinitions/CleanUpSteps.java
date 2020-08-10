package com.macys.mst.transportation.functional.stepdefinitions;

import java.util.concurrent.ConcurrentHashMap;

import org.jbehave.core.annotations.BeforeStory;

import com.macys.mst.artemis.testNg.TestNGListener;

public class CleanUpSteps {

	public long TestNGThreadID = Thread.currentThread().getId();

	@BeforeStory
	public void beforeStory() {
		ConcurrentHashMap<String, String> obj = TestNGListener.EnvMap.get(TestNGThreadID);
		TestNGListener.EnvMap.put((Thread.currentThread().getId()), obj);
	}

}
