package com.gis09.srpc.id;

import java.util.UUID;

/**
 * Created by Administrator on 2016/9/29.
 */
public class IdGenerator {
    private static SequenceProvider provider;
    static{
        provider=new InMemorySequenceProvider();
    }
    /**
     *
     * @description 默认是用uuid的生成策略
     * @return
     */
    public static String generate(){
        return generate(Policy.UUID,null);
    }
    /**
     *
     * @description 自增序列
     * @param key
     * @return
     */
    public static String increment(String key){
        return increment(key, 10);
    }
    /**
     *
     * @description 格式化 根据填充的个数
     * @param key
     * @param fillCount
     * @return
     */
    public static String increment(String key,int fillCount){
        return format(generate(Policy.INCREMENT,key),fillCount);
    }
    public static String format(String sequence,int fillCount){
        String format="%0"+fillCount+"d";
        return String.format(format, Long.parseLong(sequence));
    }
    public static String generate(Policy policy,String key){
        if (policy==Policy.UUID) {
            return UUID.randomUUID().toString().replaceAll("-","");
        }else{
            //自增的机制 有三种方案可以采用 最不靠谱的是内存机制 但也是最省事的
            return provider.next(key);
        }
    }
    public static enum Policy{
        UUID,INCREMENT
    }

}
