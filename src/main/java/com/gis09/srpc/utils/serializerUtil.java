package com.gis09.srpc.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Created by huyoufu on 2016/9/27.
 */
public class SerializerUtil {
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new RuntimeException("序列化对象(" + obj + ")!");
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj
                    + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    public static <T> T deserialize(byte[] paramArrayOfByte,
                                    Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        T instance = null;
        try {
            instance = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e);
        }
        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
        return instance;
    }
    public static void main(String[] args){
        Student Student=new Student("xiaoming","18");
        byte[] serialize = serialize(Student);
        SerializerUtil.Student deserialize = deserialize(serialize, Student.getClass());
        System.out.println(deserialize);
    }
    private static class Student{
        private String name;
        private String age;

        public Student() {
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }

        public Student(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Student setName(String name) {
            this.name = name;
            return this;
        }

        public String getAge() {
            return age;
        }

        public Student setAge(String age) {
            this.age = age;
            return this;
        }
    }

}
