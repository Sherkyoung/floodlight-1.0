package net.floodlightcontroller.statics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.restserver.IRestApiService;

/*
 * 
 *@author yangshuai
 */ 

public class PktinHistory implements 
	IOFMessageListener,IFloodlightModule,IPktinHistoryService{

	protected static Logger log = LoggerFactory.getLogger(PktinHistory.class);
	protected IFloodlightProviderService FloodlightProvider ;
	protected IRestApiService restApi ;
	private AtomicLong PACKET_IN_COUNT = new AtomicLong() ;
	
	@Override
	public String getName() {
		return "PktinHistory" ;
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getPackINCount() {
		return PACKET_IN_COUNT.get();
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		Collection<Class<? extends IFloodlightService>> l = 
				new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IPktinHistoryService.class);
		return l;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		Map<Class<? extends IFloodlightService>,IFloodlightService> m = 
				new  HashMap<Class<? extends IFloodlightService>,IFloodlightService>();
		m.put(IPktinHistoryService.class,this);
		return m;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = 
				new ArrayList<Class<? extends IFloodlightService>>() ;
		l.add(IFloodlightProviderService.class);
		l.add(IRestApiService.class);
		return l ;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		FloodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		restApi = context.getServiceImpl(IRestApiService.class) ;
	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		FloodlightProvider.addOFMessageListener(OFType.PACKET_IN,this) ;
		restApi.addRestletRoutable(new PktInHistoryWebRoutable());
	}

	@Override
	public net.floodlightcontroller.core.IListener.Command receive(
			IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		long count = PACKET_IN_COUNT.incrementAndGet() ;
		log.info("The total count of packet-in Messages are" + count);
		return Command.CONTINUE ;
	}

}
