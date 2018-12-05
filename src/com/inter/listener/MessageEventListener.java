package com.inter.listener;
/*
 *  @version 1.1
 */

import java.util.EventListener;


import com.inter.event.CombineEvent;
import com.inter.event.MessageEvent;
import com.inter.manager.SharedArtifactManager;
import com.inter.rule.AccRuleImpl;



public interface MessageEventListener extends EventListener {
	
	void invokeRuleEngine(MessageEvent evt);
	
	void invokeRuleEngine(CombineEvent evt);
	
	void setRuleEngine(AccRuleImpl RuleEngine);
	
	void setSharedArtifactManager(SharedArtifactManager sharedArtifactManager);

}
