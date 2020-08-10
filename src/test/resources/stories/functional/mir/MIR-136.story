@issue MIR-136

Narrative:

In order to ensure PO Integration is working successfully

As a User 

I want to be able to view my Purchase Orders created for me in FOM application

 
Scenario: As an Vendor user I should be able to view the purchase orders that i have created
@id SC001

Given a Vendor, Department and Purchase Order Attributes are identified
And a Purchase Order is created in BUY
And FOM application is available for the vendor user

And a vendor user is able to log in successful

When he is on Purchase Order Manager Screen

Then he should be able to see purchase orders created for him

 

Scenario: As an Internal User I should be able to view all the purchase orders
@id SC001

Given a Vendor, Department and Purchase Order Attributes are identified
 And a Purchase Order is created in BUY

And FOM application is available for the vendor user

And a MTO user is able to log in successful

And a Vendor DUNS is selected

When he is on Purchase Order Manager Screen

Then he should be able to see purchase orders created for the selected Vendor
