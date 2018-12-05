package com.inter.rule;
/*
 *  @version 1.1
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import org.apache.axiom.om.OMElement;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

import com.inter.event.CombineEvent;
import com.inter.event.MessageEvent;
import com.inter.event.SharedArtifactChangeEvent;
import com.inter.instance.CoordinationInstance;
import com.inter.manager.SharedArtifactManager;

//import com.acp.controllers.AcpProcessController;
//import com.acp.instance.ProcessInstance;

public class AccRuleImpl {
	
	protected KnowledgeBase kbase;
	protected DebugWorkingMemoryEventListener debugListener;
	protected ArrayList<String> RuleFiles;

	
	/**
	 * 
	 * Constructor method to initialise rule engine //need to fix to be able to load more rule files
	 * 
	 * @param RuleFile
	 */
	public AccRuleImpl()
	{
		
		/*
		 *  to turn a DRL source file into Package objects
		 */
		RuleFiles = new ArrayList<String>();
		//get a list of Rule 
		this.readRuleFiles();
		
	try {
		
		
		
		debugListener = new DebugWorkingMemoryEventListener();
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		//kbuilder.add(ResourceFactory.newInputStreamResource(RuleFis), ResourceType.DRL);
		
		//add each rule file into knowleadge builder
		ListIterator<String> itr = RuleFiles.listIterator();
		
		while(itr.hasNext())
		{
			String RuleFile = itr.next();
		
			FileInputStream RuleFis = new FileInputStream(RuleFile);
		
		
			kbuilder.add(ResourceFactory.newInputStreamResource(RuleFis), ResourceType.DRL);
			
		
		
		if (kbuilder.hasErrors()) {
			System.out.println(kbuilder.getErrors().toString());
			throw new RuntimeException("Unable to compile: "+ RuleFile + ".");
		}
		
		}
		/*
		* get the compiled packages (which are serializable)
		* 
		*/
		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		
		/*
		 * add the packages to a knowledgebase (deploy the knowledge packages).
		 *
		 */
		kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(pkgs);
	//	ksession = kbase.newStatefulKnowledgeSession();
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
/*
	public void executeRules(ProcessInstance process)
	{
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ksession.addEventListener(debugListener);
		
		ksession.insert(process);
		
		
		ksession.fireAllRules();
		
			
	}
*/
	
	public void executeRules(SharedArtifactChangeEvent sharedArtifactChangeEvent, SharedArtifactManager sharedArtifactmanager)
	{
		StatelessKnowledgeSession ksession;
		//StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		//ksession = kbase.newStatefulKnowledgeSession();
		ksession = kbase.newStatelessKnowledgeSession();
		ksession.addEventListener(debugListener);
		ksession.setGlobal("ArtifactManager", sharedArtifactmanager );
		//ksession.insert(Process);
	
			ksession.execute(sharedArtifactChangeEvent);
		
		//ksession.fireAllRules();
		
		//ksession.dispose();
		
			
	}
	
	public void executeRules(MessageEvent MessageEvent, SharedArtifactManager sharedArtifactmanager)
	{
		StatelessKnowledgeSession ksession;
		//StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		//ksession = kbase.newStatefulKnowledgeSession();
		ksession = kbase.newStatelessKnowledgeSession();
		ksession.addEventListener(debugListener);
		ksession.setGlobal("ArtifactManager", sharedArtifactmanager );
		//ksession.insert(Process);
	
			ksession.execute(MessageEvent);
		
		//ksession.fireAllRules();
		
		//ksession.dispose();
	
		
	}
	
	public void executeRules(CombineEvent combineEvent, SharedArtifactManager sharedArtifactmanager)
	{
		StatelessKnowledgeSession ksession;
		//StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		//ksession = kbase.newStatefulKnowledgeSession();
		ksession = kbase.newStatelessKnowledgeSession();
		ksession.addEventListener(debugListener);
		ksession.setGlobal("ArtifactManager", sharedArtifactmanager );
		//ksession.insert(Process);
	
			ksession.execute(combineEvent);
		
		//ksession.fireAllRules();
		
		//ksession.dispose();
	
		
	}
	
	
/*	
	public void executeRules(Object arg0, SharedArtifactManager sharedArtifactmanager)
	{
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ksession.addEventListener(debugListener);
		ksession.setGlobal("pc", sharedArtifactmanager );
		ksession.insert(arg0);
		
		
		ksession.fireAllRules();
	
		
	}
*/	
/*	
	public void excuteRule(Object arg0)
	{
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ksession.addEventListener(debugListener);
		ksession.insert(arg0);
		
		
		ksession.fireAllRules();
	
		
	}
	
*/	
	//need to add more method in the future !!
	
	
	private void readRuleFiles()
	{
		//have to change in the future
		File RuleFolder = new File("rules/") ;
		File[] ListOfFiles = RuleFolder.listFiles();
		
		for (int i = 0;i<ListOfFiles.length; i++ )
		{
			if(ListOfFiles[i].isFile())
			{
				this.RuleFiles.add(ListOfFiles[i].getPath());	
			
				
			}
						
		}
		
				
	}
	
	
	
	

}
