package com.macys.mst.transportation.functional.execdrivers;

import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.context.StepsContext;

import com.macys.mst.artemis.serenityJbehaveJira.SerenityJiraTestRunner;
import com.macys.mst.transportation.functional.pageobjects.Home;
import com.macys.mst.transportation.functional.stepdefinitions.CleanUpSteps;
import com.macys.mst.transportation.functional.stepdefinitions.EMSConnectionAndValidationSteps;
import com.macys.mst.transportation.functional.stepdefinitions.FLODBIntegrationStesps;
import com.macys.mst.transportation.functional.stepdefinitions.LoadAcceptSteps;
import com.macys.mst.transportation.functional.stepdefinitions.MIR201EndtoEndFlow;
import com.macys.mst.transportation.functional.stepdefinitions.MIR202EndtoEndFlow;
import com.macys.mst.transportation.functional.stepdefinitions.PurchaseOrderIntegrationSteps;
import com.macys.mst.transportation.functional.stepdefinitions.PurchaseOrderManagerSteps;
import com.macys.mst.transportation.functional.stepdefinitions.ShipmentIntegration;

public class SerenityJiraRunConfig extends SerenityJiraTestRunner {

	@Override
	public InjectableStepsFactory stepsFactory() {
		
		StepsContext stepsContext = new StepsContext();
		
		return new InstanceStepsFactory(configuration(),
				new CleanUpSteps(),
				new Home(),
				new PurchaseOrderManagerSteps(),
				new PurchaseOrderIntegrationSteps(stepsContext),
				new LoadAcceptSteps(stepsContext),
				new FLODBIntegrationStesps(stepsContext),
				new ShipmentIntegration(stepsContext),
				new EMSConnectionAndValidationSteps(stepsContext),
				new MIR201EndtoEndFlow(stepsContext),
				new MIR202EndtoEndFlow(stepsContext));
	}
}
