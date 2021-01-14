package com.youchat.common.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class StrategyMain {


    public static List<String> filterPowerByStrategy(List<String> allPowers, PowerQuery powerQuery) {
        // 这个例子中策略模式有明显的链式的规则
        // 但是使用List也可以很好地反应这种规则
        // 类似Spring的DispatchServlet中的各种Resolver等也是List组织的
        List<Pair<Predicate<PowerQuery>, BiFunction<List<String>, PowerQuery, List<String>>>> chains = new ArrayList<>();
        // ALL的逻辑
        chains.add(Pair.of(query -> "ALL".equalsIgnoreCase(query.getType()), (powers, query) -> powers));

        // 这里将外部用户的逻辑提到上部
        chains.add(Pair.of(query -> new Integer(1).equals(query.getUserType()), (powers, query) -> powers));

        // 内部用户且PowerName有值
        chains.add(Pair.of(query -> new Integer(0).equals(query.getUserType()), (powers, query) -> new ArrayList<>(powers)));

        //最后增加一个收尾的策略 其他情况统一返回原全量权限
        chains.add(Pair.of(query -> true, (powers, query) -> powers));

        // 使用策略List
        for (Pair<Predicate<PowerQuery>, BiFunction<List<String>, PowerQuery, List<String>>> chain : chains) {
            if (chain.getPredicate().test(powerQuery)) {
                return chain.getFunction().apply(allPowers, powerQuery);
            }
        }
        //这个逻辑是不会走的
        return allPowers;
    }

    public static void main(String[] args) {

    }
}
