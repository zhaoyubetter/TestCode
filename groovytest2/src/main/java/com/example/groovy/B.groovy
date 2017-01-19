public class B {
    public static void main(String[] args) {
        def msg = "Everything For Better Life!";
        println msg.metaClass;      // 元类

        String.metaClass.up = { delegate.toUpperCase() };
        String.metaClass.lower = { delegate.toLowerCase() };

        println(msg.up());
        println(msg.lower());

        // 通过元类，可以检索对象所拥有的方法和属性（类似反射）
        msg.metaClass.methods.each({ println it.name });
        println("------------------");
        msg.metaClass.properties.each( {println it.name });

        // 判断是否有某个方法
        if(msg.metaClass.respondsTo(msg, 'up')) {
            println msg.toUpperCase()
        }
        // 判断是否有某个属性
        if(msg.metaClass.hasProperty(msg, "bytes")) {
            println msg.bytes.encodeBase64();
        }
    }
}