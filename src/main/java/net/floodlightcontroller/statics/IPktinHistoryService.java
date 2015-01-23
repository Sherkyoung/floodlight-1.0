package net.floodlightcontroller.statics;

import net.floodlightcontroller.core.module.IFloodlightService;


/**
 * The service registry
 * @author yangshuai
 */
public interface IPktinHistoryService extends IFloodlightService {
	/*
	 * 用于统计结果
	 */
	public long getPackINCount();
}
