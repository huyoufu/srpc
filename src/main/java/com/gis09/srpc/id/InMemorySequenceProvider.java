package com.gis09.srpc.id;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author xiaofuzi
 * 2016年2月25日
 * @description 基于内存的自增序列提供者
 */
public class InMemorySequenceProvider implements SequenceProvider {
	private Long sequence_start=1L; //默认是从0开始 但是我们返回的都是字符串类型的
	private final Map<String, Long> container=new HashMap<>();
	@Override
	public synchronized String  next(String key) {
		String sequence=String.valueOf(sequence_start);
		Long next = container.get(key);
		if (next!=null) {
			sequence=String.valueOf(next);
			next++;
			container.put(key, next);
		}else{
			container.put(key, new Long(sequence_start+1));
		}
		return sequence;
	}
	public long getSequence_start() {
		return sequence_start;
	}
	public void setSequence_start(long sequence_start) {
		this.sequence_start = sequence_start;
	}
	
}
