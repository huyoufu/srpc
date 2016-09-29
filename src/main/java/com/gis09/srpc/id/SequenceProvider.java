package com.gis09.srpc.id;
/**
 * 
 * @author xiaofuzi
 * 2016年2月25日
 * @description 自增序列接口
 */
public interface SequenceProvider {
	 String next(String key);
}
