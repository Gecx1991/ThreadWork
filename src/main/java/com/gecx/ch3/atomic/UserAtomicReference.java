package com.gecx.ch3.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/24 23:54
 */
public class UserAtomicReference {
    static AtomicReference<User> atomicReference;

    public static void main(String[] args) {
        //要修改的实体的实例
        User user = new User("Mark", 15);
        atomicReference = new AtomicReference(user);
        User updateUser = new User("Bill", 17);
        atomicReference.compareAndSet(user, updateUser);

        System.out.println(atomicReference.get());
        System.out.println(user);
    }

    static class User {
        private volatile String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
