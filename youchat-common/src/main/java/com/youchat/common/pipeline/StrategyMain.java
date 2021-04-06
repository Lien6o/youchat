package com.youchat.common.pipeline;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class StrategyMain {

    /**
     * 这个例子中策略模式有明显的链式的规则
     * 但是使用List也可以很好地反应这种规则
     * 类似Spring的DispatchServlet中的各种Resolver等也是List组织的
     */
    public static List<Integer> filterPowerByStrategy(List<String> allPowers, PowerQuery powerQuery) {
        List<Pair<Predicate<PowerQuery>, BiFunction<List<String>, PowerQuery, List<Integer>>>> chains = new ArrayList<>();

        // ALL的逻辑
        chains.add(
                Pair.of("逻辑1", query -> "ALL".equalsIgnoreCase(query.getType()), (powers, query) -> Lists.newArrayList(0))
        );

        // 这里将外部用户的逻辑提到上部
        chains.add(
                Pair.of("逻辑2", query -> Integer.valueOf(1).equals(query.getUserType()), (powers, query) ->
                        {
                            powers.add("333");
                            return Lists.newArrayList(1,2,3);
                        }
                )
        );

        // 内部用户且PowerName有值
        chains.add(
                Pair.of("逻辑3", query -> Integer.valueOf(0).equals(query.getUserType()), (powers, query) ->
                        {
                            powers.add(query.getType());
                            int a = 1/0;
                            return Lists.newArrayList(3,4,3);
                        }
                )
        );

        //最后增加一个收尾的策略 其他情况统一返回原全量权限
        chains.add(
                Pair.of("默认逻辑", query -> true, (powers, query) -> new ArrayList<>())
        );

        // 使用策略List
        for (Pair<Predicate<PowerQuery>, BiFunction<List<String>, PowerQuery, List<Integer>>> chain : chains) {
            if (chain.getPredicate().test(powerQuery)) {
                System.out.println("strategy = " + chain.getName());
                return chain.getFunction().apply(allPowers, powerQuery);
            }
        }

        // 这个逻辑是不会走的

        return new ArrayList<>();
    }

    public static void main(String[] args) {
        PowerQuery powerQuery = new PowerQuery();
        // powerQuery.setType("ALL7");
        powerQuery.setUserType(0);

        List<Integer> strings = filterPowerByStrategy(Lists.newArrayList("1", "2", "3"), powerQuery);
        System.out.println("strings = " + strings);
    }
}
