Meta:
@issue Login scenario
@tags Project: Trasportation, Product: FOM, Program: Logistics
@automatedBy B004797_MADHUSHA PATURI

Narrative:
In order to automate FOM E2E scenarios
As a Quality assurance person
I want to login to FOM Application

Scenario:1 User should be able to Login into FOM application
Meta:
@id Shipment Scenario
@tag Type: integration , Module: LoginPage
@automatedBy B004797_Madhusha Paturi

Given the user is navigated to |URL|
When


Examples:
| URL                                               | Username | Password|
|http://ma001xsjda1006.federated.fds:8001/fom/shell/| B004797  |B004797@a123|

