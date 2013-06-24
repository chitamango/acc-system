package test;

import com.inter.factory.AccFactory;
import com.inter.instance.CoordinationInstance;
import com.inter.instance.RoleInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedRuleInstance;

public class TestFactory {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		AccFactory myFac = new AccFactory();
		CoordinationInstance MyCoordinationInstance = myFac.getCoordinationInstance("purchase");
		SharedArtifactInstance MyArtifactInstance = myFac.getSharedArtifactInstance("purchase", "purchase_order"); 
		RoleInstance MyRoleInstance = myFac.getRoleInstance("purchase", "buyer", "createOrder", "createOrder");
		SharedRuleInstance MyRuleInstance = myFac.getSharedRuleInstance("purchase", "rule1");
		
		System.out.println(MyCoordinationInstance.getCoordinationName());
		System.out.println(MyArtifactInstance.getSharedArtifactName());
		System.out.println(MyArtifactInstance.getCurrentState());
		System.out.println(MyArtifactInstance.getStateInstance("pre_created").getStateName());
		System.out.println(MyArtifactInstance.getSharedAttributeInstance("customer").getAttributeStructure());
		System.out.println(MyRoleInstance.getRole());
		System.out.println(MyRoleInstance.getPort());
		System.out.println(MyRoleInstance.getInputMessageName());
		System.out.println(MyRoleInstance.getOutoutMessageName());
		System.out.println(MyRoleInstance.getService());
		System.out.println(MyRuleInstance.getRuleName());
		System.out.println(MyRuleInstance.getInvokingServiceName());
		System.out.println(MyRuleInstance.getRoleName());
		System.out.println(MyRuleInstance.getCoordinationRules().get(0).getFromType());
		System.out.println(MyRuleInstance.getNotificationRules().get(0).getFromType());
		System.out.println(MyRuleInstance.getTransitionRules().get(0).getFromState());
	}

}
