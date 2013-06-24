package com.inter.listener;

import java.util.EventListener;

import com.inter.event.SharedArtifactChangeEvent;



public interface SharedArtifactChangeEventListener extends EventListener {
	
	void invokeRuleEngine(SharedArtifactChangeEvent evt);

}
