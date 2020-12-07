package com.youchat.account.pool;

import java.util.List;

@FunctionalInterface
public interface AccountCreator {

    List<Long> create(Long accountId);

}
