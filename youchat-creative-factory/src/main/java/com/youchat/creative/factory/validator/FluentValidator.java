package com.youchat.creative.factory.validator;

import com.youchat.creative.factory.common.User;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FluentValidator<T> {

    private final T target;

    private final LinkedList<ValidatorElement<T>> validatorList = new LinkedList<>();

    private final List<String> errorList = new ArrayList<>();

    private boolean isPass = true;

    public static void main(String[] args) {
        User user = new User();

        User result = FluentValidator.check(user).failFast()
                .on(Objects::isNull)
                .will(u -> "user is null")
                .on(u -> u.getAge() == 0)
                .will(u -> "user age error current is " + u.getAge())
                .on(u -> "-".equals(u.getAddr()))
                .will(u -> "user addr error current is " + u.getAddr())
                .doValidate()
                .result(new Result<User, User>() {
                    @Override
                    public User onSuccess(User user) {
                        return user;
                    }

                    @Override
                    public User onFailure(List<String> list, User user) {
                        return null;
                    }
                });

    }

    private FluentValidator(T target) {
        this.target = target;
    }

    /**
     * 是否一旦发生验证错误就退出
     */
    private boolean isFailFast = false;

    public FluentValidator<T> failFast() {
        isFailFast = true;
        return this;
    }

    /**
     * 创建<tt>FluentValidator</tt>
     *
     * @return FluentValidator
     */
    public static <T> FluentValidator<T> check(T target) {
        return new FluentValidator<>(target);
    }

    /**
     * 在待验证对象<tt>t</tt>上，使用<tt>v</tt>验证器进行验证
     */
    public FluentValidator<T> will(Function<T, String> error) {
        ValidatorElement<T> last = validatorList.getLast();
        last.setErrorMsg(error);
        return this;
    }

    public <X extends RuntimeException> FluentValidator<T> will(Supplier<? extends X> exceptionSupplier) {
        ValidatorElement<T> last = validatorList.getLast();
        last.setExceptionSupplier(exceptionSupplier);
        return this;
    }

    /**
     * 在待验证对象<tt>t</tt>上，使用<tt>v</tt>验证器进行验证
     *
     * @param v 验证器
     * @return FluentValidator
     */
    public FluentValidator<T> on(Predicate<T> v) {
        Objects.requireNonNull(v, "Validator should not be NULL");
        ValidatorElement<T> e = new ValidatorElement<>();
        e.setValidator(v);
        validatorList.add(e);
        return this;
    }

    /**
     * 按照默认验证回调条件，开始使用验证
     *
     * @return FluentValidator
     */
    public FluentValidator<T> doValidate() {
        for (ValidatorElement<T> v : validatorList) {
            boolean accept = v.getValidator().test(target);
            if (accept) {
                if (v.getExceptionSupplier() != null) {
                    throw v.getExceptionSupplier().get();
                }
                if (v.getErrorMsg() == null) {
                    throw new IllegalArgumentException("Function config error! ");
                }
                addErrorMsg(v);
                if (isFailFast) {
                    return this;
                }
            }
        }
        return this;
    }

    private void addErrorMsg(ValidatorElement<T> v) {
        errorList.add(v.getErrorMsg().apply(target));
        isPass = false;
    }


    /**
     * 转换为对外的验证结果，在<code>FluentValidator.on(..).on(..).doValidate()</code>这一连串“<a href="https://en.wikipedia
     * .org/wiki/Lazy_evaluation">惰性求值</a>”计算后的“及时求值”收殓出口。
     * <p/>
     * &lt;T&gt;是验证结果的泛型
     *
     * @return 对外验证结果
     */
    public <R> R result(Result<R, T> function) {
        if (isPass) {
            return function.onSuccess(target);
        }
        return function.onFailure(errorList, target);
    }

    interface Result<R, T> {
        R onSuccess(T t);

        R onFailure(List<String> list, T t);
    }


    private static class ValidatorElement<T> {
        Predicate<T> validator;

        Function<T, String> errorMsg;

        Supplier<? extends RuntimeException> exceptionSupplier;

        public Predicate<T> getValidator() {
            return validator;
        }

        public void setValidator(Predicate<T> validator) {
            this.validator = validator;
        }

        public Function<T, String> getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(Function<T, String> errorMsg) {
            this.errorMsg = errorMsg;
        }

        public Supplier<? extends RuntimeException> getExceptionSupplier() {
            return exceptionSupplier;
        }

        public void setExceptionSupplier(Supplier<? extends RuntimeException> exceptionSupplier) {
            this.exceptionSupplier = exceptionSupplier;
        }
    }
}
