function hook_java() {
    // 打开dex文件
    var dex = Java.openClassFile("/data/local/tmp/kgb.dex");

    // 启动时hook需要在frida启动参数中加--no-pause
    Java.perform(function () {
        // 加载DEX文件
        dex.load();
        var kgb = Java.use("KGB");
        console.log(kgb.decode());

        var System = Java.use("java.lang.System");
        System.getProperty.overload('java.lang.String').implementation = function (s) {
            console.log("getProperty:", s);
            return "Russia";
        };

        System.getenv.overload('java.lang.String').implementation = function (s) {
            var User = "RkxBR3s1N0VSTDFOR180UkNIM1J9Cg==";
            console.log(s);
            return User;
        };

        // var username = "codenameduchess";
        var LoginActivity = Java.use("com.tlamb96.kgbmessenger.LoginActivity");

        var username = "codenameduchess";
        LoginActivity.j.implementation = function () {
            return true
        };

        var a = Java.use("com.tlamb96.kgbmessenger.b.a");
        //hook 构造函数
        a.$init.implementation = function (i, str, str2, z) {
            this.$init(i, str, str2, z);
            console.log("a.$init:", i, str, str2, z);
            // 利用Java异常打印调用栈，frida不支持
            print_stack();
        };

        // 两个字符串解密逻辑位于python脚本中


    });
}

function print_stack() {
    Java.perform(function () {
        var Exception = Java.use("java.lang.Exception");
        // 实例化异常类
        var instance = Exception.$new("print_stack");
        var stack = instance.getStackTrace();
        console.log(stack);
        // 析构
        instance.$dispose();
    });
}

function main() {
    hook_java();
}

setImmediate(main);