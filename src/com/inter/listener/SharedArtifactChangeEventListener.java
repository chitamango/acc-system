package com.inter.listener;
/*
 *  @version 1.1
 */

import java.util.EventListener;

import com.inter.event.SharedArtifactChangeEvent;



public interface SharedArtifactChangeEventListener extends EventListener {
	
	void invokeRuleEngine(SharedArtifactChangeEvent evt);

}
