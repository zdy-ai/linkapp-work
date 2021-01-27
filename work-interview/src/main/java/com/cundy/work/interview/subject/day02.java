package com.cundy.work.interview.subject;

/**
 * 接口和抽象类的区别
 *
 * 1.基本语法区别
 * java中接口和抽象类的定义语法分别为interface与abstract关键字
 *
 * 抽象类：在java中被abstract关键字修饰的类称为抽象类，被abstract关键字修饰的方法称为抽象方法，抽象方法只有方法的声明，没有方法体。
 * 抽象类的特点：
 *  a.抽象类不能被实例化只能被继承
 *  b.包含抽象方法的一定是抽象类，但是抽象类不一定含有抽象方法；
 *  c.抽象类中的抽象方法的修饰符只能为public 或者protected,默认为public
 *  d.一个子类继承一个抽象类，则子类必须实现父类的抽象方法，否则子类也必须定义为抽象类；
 *  e.抽象类可以包含属性，方法，构造方法。但是构造方法不能用于实例化，主要用途是被子类调用。
 *
 * 接口：java中接口使用interface关键字修饰，特点为：
 *  a.接口可以包含变量，方法；变量被默认指定为public static final，方法被默认指定为public abstract(JDK1.8之前)
 *  b.接口支持多继承，即一个接口可以extends多个接口，间接解决了java中类的单继承问题；
 *  c.一个类可以实现多个接口；
 *  d.JDK1.8中对接口增加了新的特性：
 *      （1）默认方法（default method）:JDK1.8允许给接口添加非抽象的方法实现，但必须使用default关键字修饰；定义了default的方法可以不被实现子类所实现，但只能被实现子类的对象调用；如果子类实现了多个接口，并且这些接口包含一样的默认方法，则子类必须重写默认方法；
 *      （2）静态方法（static method）:JDK1.8中允许使用static关键字修饰一个方法，并提供实现，称为接口静态方法。接口静态方法只能通过接口调用（接口名.静态方法名）
 */
public interface day02 {

    public static final int a = 10;
    //默认方法
    default void sayHello()
    {
        System.out.println("sayHello in interface name is day02");
    }

    public abstract class Person{
        public abstract void say();
        public void eat(){};
    }
    //静态类实现
    public static void run()
    {
        System.out.println("run in interface name is day02");
    }

}
/**
 * 如上所述：
 * 接口只能是功能的定义，而抽象类既可以为功能的定义也可以为功能的实现
 *
 * 相同点：
 * 1.都不能被实例化
 * 2.接口的实现类或抽象类的子类都只有实现了接口或抽象类中的方法后才能实例化
 *
 * 不同点
 * 1.接口只有定义，不能有方法的实现，java1.8中可以定义default方法体，而抽象类可以有定义与实现，方法可以在抽象类中实现。
 * 2.实现接口的关键字为implements,继承抽象累的关键字为extends.一个类可以实现多个接口。但一个类只能继承一个抽象类
 * 所以，使用接口可以间接地实现多重继承。
 * 3.接口强调特定功能的实现，而抽象类强调所属关系
 * 4.接口成员变量默认为public static final,必须赋初值，不能修改；其所有的成员方法都是public abstract的。
 * 抽象类中的成员变量默认default,可以在子类中被重新定义，也可以被重新赋值；
 * 抽象方法abstract修饰。不能被private static synchronized和native等修饰，必须以分号结尾，不带花括号
 */
