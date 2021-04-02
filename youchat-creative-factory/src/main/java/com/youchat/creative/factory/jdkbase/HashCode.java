package com.youchat.creative.factory.jdkbase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

public class HashCode {
    public static void main(String[] args) {
        User lili = User.builder().name("lil222222i").age(10L).money(1000L).build();
        User enbo = User.builder().name("lil222222i").age(10L).money(1000L).build();
        System.out.println(lili.hashCode());
        System.out.println(enbo.hashCode());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class User{
        private String name;
        private Long age;
        private Long money;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(name, user.name) && Objects.equals(age, user.age) && Objects.equals(money, user.money);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, money);
        }
    }
}
