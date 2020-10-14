package com.boss.cloud.conf;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: lpb
 * @create: 2020-09-18 16:48
 */
public class StreamTest {

    public static void main(String[] args) {
        Student test1 = new Student(1, "test1", 20);
        Student test2 = new Student(2, "test3", 23);
        Student test3 = new Student(3, "test4", 10);
        Student test4 = new Student(5, "test6", 230);
        Student test5 = new Student(8, "test8", 220);
        Student test7 = new Student(8, "test8", 220);
        Student test8 = new Student(8, "test8", 220);
        List<Student> collect = Stream.of(test1, test2, test3, test4, test5).collect(Collectors.toList());
        List<Integer> list = collect.stream().map(Student::getId).collect(Collectors.toList());
        System.out.println(collect.toString());
        System.out.println(list.toString());
    }


    static class Student {

        private int id;

        private String name;

        private int age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

}

