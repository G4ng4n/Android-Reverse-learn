function hook_java() {
    Java.perform(function () { // 对java代码的操作必须放在perform里
        var LoginActivity = Java.use("com.example.androiddemo.Activity.LoginActivity");

        // 登录关学习overload、调用活动中函数
        LoginActivity.a.overload('java.lang.String', 'java.lang.String').implementation = function (str, str2) {
            var result = this.a(str, str2); //调用原来的函数
            console.log("LoginActivity.a:", str, str2, result);
            return result;
        };

        var FridaActivity1 = Java.use("com.example.androiddemo.Activity.FridaActivity1");

        // 第一关练习hook函数控制返回值
        FridaActivity1.a.implementation = function (bArr) {
            // hook a函数，直接返回用于比较的值
            console.log("FridaActivity1");
            return "R4jSLLLLLLLLLLOrLE7/5B+Z6fsl65yj6BgC6YWz66gO6g2t65Pk6a+P65NK44NNROl0wNOLLLL=";
        };
    });
}

// 第二关练习主动调用方法（静态和非静态）
function call_frida2() {
    Java.perform(function () {
        var FridaActivity2 = Java.use("com.example.androiddemo.Activity.FridaActivity2");
        FridaActivity2.setStatic_bool_var(); //静态函数在use()之后可以直接调用

        //非静态函数需要找到类的实例化对象再调用
        // choose传入类名和回调函数，遍历类的实例化对象，并对每个遍历的实例进行函数调用
        Java.choose("com.example.androiddemo.Activity.FridaActivity2", {
            // 对于找到的标定类的对象，主动调用其setBool_var
            onMatch: function (instance) {
                instance.setBool_var();
            },
            onComplete: function () {
                console.log("遍历结束");
            }
        });
    });
}


// 第三关练习修改变量
function call_frida3() {
    Java.perform(function () {
        var FridaActivity3 = Java.use("com.example.androiddemo.Activity.FridaActivity3");
        // 访问、修改变量的值，注意用value来访问变量值
        FridaActivity3.static_bool_var.value = true;
        Java.choose("com.example.androiddemo.Activity.FridaActivity3", {
            onMatch: function (ins) {
                ins.bool_var.value = true;
                // 出现变量和方法同名时，变量前加下划线
                ins._same_name_bool_var.value = true;
            },
            onComplete: function () {}
        });
    })
}

// 第四关练习hook内部类函数
function call_frida4() {
    Java.perform(function () {
        // 使用$连接类文件和内部类
        var InnerClasses = Java.use("com.example.androiddemo.Activity.FridaActivity4$InnerClasses");

        InnerClasses.check1.implementation = function () {
            return true
        };
        InnerClasses.check2.implementation = function () {
            return true
        };
        InnerClasses.check3.implementation = function () {
            return true
        };
        InnerClasses.check4.implementation = function () {
            return true
        };
        InnerClasses.check5.implementation = function () {
            return true
        };
        InnerClasses.check6.implementation = function () {
            return true
        };

        //遍历类中方法，解析出方法名调用
        var arr = InnerClasses.class.getDeclaredMethods();
        for (var i = 0; i < arr.length; i++) {
            console.log(arr[i] + typeof (arr[i]));
        }
    });
}

// 第五关练习hook动态DEX
function call_frida5() {
    Java.perform(function () {

        // 1
        // 枚举类装载器练习，找到dalvik的dex装载器
        // Java.enumerateClassLoaders({
        //     onMatch : function(loader){
        //         console.log(loader);
        //     }, onComplete : function(){}
        // });

        // 2
        // Java.use()不能对接口使用，需要找到接口的实现
        // public CheckInterface getDynamicDexCheck()返回了一个接口，因此寻找其返回的接口对应的类名

        var FridaActivity5 = Java.use("com.example.androiddemo.Activity.FridaActivity5");
        // 查找接口类名 com.example.androiddemo.Dynamic.DynamicCheck
        // Java.choose("com.example.androiddemo.Activity.FridaActivity5",{
        //     onMatch : function(ins){
        //         // $className 返回类名
        //         console.log(ins.getDynamicDexCheck().$className);
        //     },onComplete : function(){}
        // });

        // 3
        // 下列代码会引起ClassNotFoundException，因为目标类不存在于base.apk中，frida没有找到
        // var DynamicCheck = Java.use("com.example.androiddemo.Dynamic.DynamicCheck");
        // Java.choose("com.example.androiddemo.Dynamic.DynamicCheck",{
        //     onMatch : function(ins){
        //         console.log(ins);
        //     },onComplete : function(){}
        // });

        // 4
        // 需要从dex装载器中寻找对应的类（类在动态载入的dex中被实现）
        Java.enumerateClassLoaders({
            onMatch: function (loader) {
                try {
                    if (loader.findClass("com.example.androiddemo.Dynamic.DynamicCheck")) {
                        console.log("[+] \"com.example.androiddemo.Dynamic.DynamicCheck\" found" + loader);
                        Java.classFactory.loader = loader; // 改变loader，这样frida就可以在正确位置寻找目标类
                    }
                } catch (e) {}
            },
            onComplete: function () {}
        });

        // 5
        // 如同前面几例hook即可
        var DynamicCheck = Java.use("com.example.androiddemo.Dynamic.DynamicCheck");
        Java.choose("com.example.androiddemo.Dynamic.DynamicCheck", {
            onMatch: function (ins) {
                ins.check.implementation = function () {
                    return true
                };
            },
            onComplete: function () {}
        });
    });
}

// 第六关练习多个类的遍历
function call_frida6() {
    // 这关可以通过多次切换使用的类，hook所有的check()，这里主要是练习遍历的方式
    Java.perform(function () {
        Java.enumerateLoadedClasses({
            onMatch: function (name, handle) {

                // 过滤位于Frida6包中的类
                if (name.indexOf("com.example.androiddemo.Activity.Frida6") >= 0) {
                    console.log(name);
                    // 筛选出类后hook
                    var f6 = Java.use(name);
                    f6.check.implementation = function () {
                        return true
                    };
                }
            },
            onComplete: function () {}
        });
    });
}

function hook_mul_class() {
    Java.perform(function () {
        Java.enumerateLoadedClasses({
            onMatch: function (name, handle) {
                if (name.indexOf("com.example.androiddemo.Activity.Frida6") >= 0) {
                    console.log(name);
                    var fridaclass6 = Java.use(name);
                    fridaclass6.check.implementation = function () {
                        console.log("frida 6 check:", this);
                        return true;
                    };
                }

            },
            onComplete: function () {

            }
        })
    });
}

function main() {
    hook_java();
}

setImmediate(main);