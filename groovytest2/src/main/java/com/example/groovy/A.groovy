/**
 * Created by zhaoyu1 on 2016/10/31.
 */
public class A {
    public static void main(String[] args) {
        // 没有类型
        def var = "Hello World";
        println var;
        println var.class

        // tab 都打印了
        def var2 = """
            Hello Better,
            Good Luck!
        """
        println(var2);

        // 不关心对象类型
        var = 1024;
        println var;
        println var.class;     // Integer.class

        // 简单循环，范围
        for (i in 0..5) {
            print(var + "\t");
        }
        println();

        // Map
        def map = ['name': 'better', 'age': 29, 'sex': 'man'];
        println(map);
        // 添加项
        map = map + ['weight': 55];
        map.put('height', 165); // 注意，这里是小括号
        map.father = 'Better.Father'    // 这个也行
        // 检索
        println(map['father']);     // 通过key作为下标
        println(map.height);        // 通过key作为成员名变量

        // 闭包
        map.each { key, value -> println "$key:$value" };
        map.each({ println it });   // it 为关键字，代表map集合中的每一项
        map.each({ println it.key + "--->" + it.value });

        // 闭包单独定义
        def say = {
            word -> println "Hi,$word welcome to grooy world!";
        };
        // 调用
        say("Better");
        say.call('Chelsea & Better');
    }
}