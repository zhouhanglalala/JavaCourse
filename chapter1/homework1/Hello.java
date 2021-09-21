package com.zhouhang.chapter1.lesson1;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

/**
 * @author zhouhang
 */
public class Hello<T> {

    private final T word;

    public Hello(T word) {
        this.word = word;
    }

    public static void main(String[] args) {
        Hello<String> hello = new Hello<>(" Baron");
        // 打印word
        hello.say();
        // 测试加减乘除求和
        double var1 = 1.12;
        double var2 = 3.568;
        List<Double> doubleList = Arrays.asList(var1, var2);
        System.out.println(hello.add(var1, var2));
        System.out.println(hello.minus(var1, var2));
        System.out.println(hello.divide(var1, var2));
        System.out.println(hello.multiple(var1, var2));
        System.out.println(hello.sum(doubleList));

        // 测试switch
        hello.testSwitch((int) var1);
        // 测试枚举转换非空
        System.out.println(hello.testEnumTransformNonNpe((int) var2));

        // 测试局部内部类
        InnerInterface innerInterface = new InnerInterface() {
            @Override
            public void foo() {
                printName();
                printValue();
                printLength();
                System.out.println("foo");
            }
        };
        innerInterface.foo();
    }

    public void say() {
        System.out.println("hello" + word.toString());
    }

    public Number add(Number var1, Number var2) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(var1.doubleValue());
        BigDecimal bigDecimal2;
        bigDecimal2 = BigDecimal.valueOf(var2.doubleValue());
        return bigDecimal1.add(bigDecimal2).doubleValue();
    }

    public Number divide(Number var1, Number var2) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(var1.doubleValue());
        BigDecimal bigDecimal2 = BigDecimal.valueOf(var2.doubleValue());
        return bigDecimal1.divide(bigDecimal2, 2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public Number multiple(Number var1, Number var2) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(var1.doubleValue());
        BigDecimal bigDecimal2 = BigDecimal.valueOf(var2.doubleValue());
        return bigDecimal1.multiply(bigDecimal2).doubleValue();
    }

    public Number minus(Number var1, Number var2) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(var1.doubleValue());
        BigDecimal bigDecimal2 = BigDecimal.valueOf(var2.doubleValue());
        return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }

    public Number sum(List<Double> list) {
        if (list.isEmpty()) {
            return 0;
        }
        return list.stream().reduce(Double::sum).get();
    }

    public void testSwitch(int number) {
        switch (number) {
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("3");
                break;
            case 4:
                System.out.println("4");
                break;
            default:
                System.out.println("判断不了");
        }
    }

    public boolean testEnumTransformNonNpe(int number) {
        InnerEnum innerEnum = InnerEnum.valueOf(number);
        return Objects.isNull(innerEnum);
    }

    public interface InnerInterface {

        int VALUE = 0;
        String NAME = "foo";
        int LENGTH = 0;

        /**
         * 打印名字
         */
        default void printName() {
            System.out.println(NAME);
        }

        /**
         * 打印值
         *
         */
        default void printValue() {
            System.out.println(VALUE);
        }


        /**
         * 打印长度
         */
        default void printLength() {
            System.out.println(LENGTH);
        }

        /**
         * 不知道干啥
         */
        void foo();
    }

    public enum InnerEnum {

        /**
         * a
         */
        a(1),
        /**
         * b
         */
        b(2),
        /**
         * c
         */
        c(3),
        /**
         * d
         */
        d(4),
        /**
         * default
         */
        e(-1);


        @Getter
        private final Integer value;


        InnerEnum(Integer value) {
            this.value = value;
        }

        public static InnerEnum valueOf(Integer value) {
            return Arrays.stream(InnerEnum.values()).filter(item -> item.value.equals(value)).findAny().orElse(e);
        }

    }

}
