# Frida笔记

## 前期准备

没有测试机的话，使用Android Studio下的AVD即可。我买了个pixel的测试机（美版），本来以为测试机会比虚拟机更方便一些，没想到搞环境费了不少时间。。。

### 解锁OEM

进入开发者模式什么的就不说了。手机翻墙，登录Google play后即可解锁OEM

### 解锁bootloader

```adb
USB调试下输入
adb reboot bootloader

进入bootloader后
fastboot flashing unlock
```

### 获取root

下载TWRP对应手机型号版本的一个zip和一个img两个文件，再下载一个magisk，将TWRP的zip和magisk的压缩包一并通过USB文件传输放到存储目录里。这里我遇到了一个大坑，pixel的驱动在电脑上有点问题，只能USB调试+充电，不能传文件（安装官方驱动并没有解决问题）。解决办法是关掉USB调试，电脑上卸载已经安装的对应驱动，重新连接到电脑，此时应该可以传输文件，连线状态下再打开USB调试貌似就没问题了。

之后`adb reboot bootloader`进入bootloader界面，再`fastboot boot PATH`，PATH为TWRP的img的路径，这步执行后进入TWRP界面

在TWRP的install里面，依次安装上面传入的两个压缩包，传入前注意两个勾选框都取消掉（一个好像是校验压缩包另一个忘了，就是选择了压缩包，准备滑动滑块确认操作的时候）。

两个安装好后选择重启系统，注意TWRP会询问安装TWRP应用，一共还是有两个复选框，也都不要选中。

系统重启后，可以发现magisk应用被安装，进入后，从电脑上进入adb shell执行su会在magisk上出现确认窗口，确认后可以获取root权限

### 后续

后面我在使用adb push的时候还是会出现权限或者只读文件系统的问题，简单搜了一圈下来没发现什么解决办法，不过考虑到刷root只是为了把frida-server传进去，所以也就没过多纠结，具体的方法是adb push到sdcard，进入adb shell将server端移动到/data/local处，添加可执行权限后再执行就好了。至此frida-server就可以在pixel上跑起来了，虽然root后的很多步骤还是很不方便也没解决，但还好很多操作是一次性的，起码比虚拟机要舒服得多了。。。

## frida-1

- 通过`frida -U 包名 -l 脚本名`来hook应用
- 注入代码要放在Java.perform()内执行
- 使用Java.use()指定类，如`var LoginActivity = Java.use("com.example.androiddemo.Activity.LoginActivity");`
- 使用overload()指定hook的同名函数

修改返回值，重载函数

```js
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
```

主动调用方法，在frida的界面内输入函数名即可调用

```js
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
            onComplete: function () {}
        });
    });
}
```

修改变量

```js
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
```

hook内部类

```js
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

        // 使用java中的遍历类的方法，hook多个函数
        var arr = InnerClasses.class.getDeclaredMethods();
        for (var i = 0; i < arr.length; i++) {
            console.log(arr[i] + typeof (arr[i]));
        }
    });
}
```

查找接口，hook动态DEX

```js
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
```

## frida-2

- 程序开始运行时立即hook：Frida启动参数用`-U --no-pause -f 包名 -l 脚本名`（启动时注入、使用frida启动应用）
- 使用dx.bat将jar包转换为dex文件，adb push后可以使用frida调用jar包方法：
  - hook脚本的开头打开dex文件（需在adb shell中给予权限）
  - 脚本中使用load()方法加载dex，接下来可直接调用dex内部的方法（非静态方法注意首先实例化）

```js
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
```

解密脚本（这部分其实就是两个很简单的加密，和frida没什么关系了）

```py
from z3 import *

p = [ord(x) for x in "V@]EAASB\u0012WZF\u0012e,a$7(&am2(3.\u0003"]
r = [ord(x) for x in "\u0000dslp}oQ\u0000 dks$|M\u0000h +AYQg\u0000P*!M$gQ\u0000"]

res_p = [0] * len(p)
for i in range(int(len(p) / 2)):
    c = p[i]
    res_p[i] = p[len(p) - i - 1] ^ ord('A')
    res_p[len(p) - i - 1] = c ^ ord('2')

print(''.join([chr(x) for x in res_p]))

res_r = [0] * len(r)

s = Solver()
x = [BitVec('x[%d]' % i, 32) for i in range(len(res_r))]
l = len(r)
for i in range(len(res_r)):
    s.add((x[i] >> (i % 8)) ^ x[i] == r[l - i - 1])

result = []

if s.check() == sat:
    model = s.model()
    for i in range(len(r)):
        c = model[x[i]]
        if c != None:
            result.append(c.as_long().real)

print(''.join([chr(x) for x in result]))
```

## frida-native

> 这部分用android8.1的pixel物理机测试会导致hook_java失败，网上能查到的只有刷android 7的系统，嫌麻烦就不刷机了，这部分主要靠视频记忆了

- 调用的JNI函数名在so内找不到：
  - IDA查看.init_array段
  - 或查看JNI_OnLoad函数（查看是否为动态注册）
    - 通过g_env的动态注册方法RegisterNatives找到对应的函数名
    - 参数g_env的类型为JNIEnv*，IDA默认只识别为int*
- JNI中，导出函数的部分参数在Java中被隐藏，第一个参数类型为JNIEnv*，修改后IDA可以识别偏移位置的函数
- 使用`ptr(retval).readCString()`将地址转化为指针对象并获取内容

```js
function hook_java() {
    Java.perform(function () {
        var MyApp = Java.use("com.gdufs.xman.MyApp");
        // hook java 层的jni函数 
        MyApp.saveSN.implementation = function (str) {
            console.log("MyApp.saveSN:", str);
            this.saveSN(str);
        };

        var Process = Java.use("android.os.Process");
        Process.killProcess.implementation = function (pid) {
            //this.killProcess(pid);
            console.log("Process.killProcess:", pid);
        };

        console.log("hook_java");
    });
}

function hook_native() {
    //获取模块的基址
    var base_myjni = Module.findBaseAddress("libmyjni.so");
    if (base_myjni) {
        //获取模块的导出函数
        var n2 = Module.findExportByName("libmyjni.so", "n2");
        //thumb的函数，0x000011F8, 实际地址0xdba461f9
        console.log("base_myjni:", base_myjni, "n2:", n2);
        //hook模块的导出函数
        Interceptor.attach(n2, {
            onEnter: function (args) {
                console.log("n2 onEnter:", args[0], args[1], args[2]);
            },
            onLeave: function (retval) {

            }
        });
    }
}

// libart由C++编写
function hook_libart() {
    var module_libart = Process.findModuleByName("libart.so");
    var symbols = module_libart.enumerateSymbols(); //枚举模块的符号

    var addr_GetStringUTFChars = null;
    var addr_FindClass = null;
    var addr_GetStaticFieldID = null;
    var addr_SetStaticIntField = null;

    // 遍历符号表
    for (var i = 0; i < symbols.length; i++) {
        var name = symbols[i].name;
        if (name.indexOf("art") >= 0) {
            if ((name.indexOf("CheckJNI") == -1) && (name.indexOf("JNI") >= 0)) {
                if (name.indexOf("GetStringUTFChars") >= 0) {
                    console.log(name);
                    addr_GetStringUTFChars = symbols[i].address;
                } else if (name.indexOf("FindClass") >= 0) {
                    console.log(name);
                    addr_FindClass = symbols[i].address;
                } else if (name.indexOf("GetStaticFieldID") >= 0) {
                    console.log(name);
                    addr_GetStaticFieldID = symbols[i].address;
                } else if (name.indexOf("SetStaticIntField") >= 0) {
                    console.log(name);
                    addr_SetStaticIntField = symbols[i].address;
                }
            }
        }
    }

    //hook jni的一些函数
    if (addr_GetStringUTFChars) {
        Interceptor.attach(addr_GetStringUTFChars, {
            onEnter: function (args) {
                //打印调用栈
                // console.log('addr_GetStringUTFChars onEnter called from:\n' +
                //     Thread.backtrace(this.context, Backtracer.FUZZY)
                //         .map(DebugSymbol.fromAddress).join('\n') + '\n');
            },
            onLeave: function (retval) {
                // retval const char*
                console.log("addr_GetStringUTFChars onLeave:", ptr(retval).readCString(), "\r\n");
            }
        });
    }

    if (addr_FindClass) {
        Interceptor.attach(addr_FindClass, {
            onEnter: function (args) {
                console.log("addr_FindClass:", ptr(args[1]).readCString());
            },
            onLeave: function (retval) {

            }
        });
    }
    if (addr_GetStaticFieldID) {
        Interceptor.attach(addr_GetStaticFieldID, {
            onEnter: function (args) {
                console.log("addr_GetStaticFieldID:", ptr(args[2]).readCString(), ptr(args[3]).readCString());
            },
            onLeave: function (retval) {

            }
        });
    }
    if (addr_SetStaticIntField) {
        Interceptor.attach(addr_SetStaticIntField, {
            onEnter: function (args) {
                console.log("addr_SetStaticIntField:", args[3]);
            },
            onLeave: function (retval) {

            }
        });
    }
}

function hook_libc() {
    //hook libc的函数
    var strcmp = Module.findExportByName("libc.so", "strcmp");
    console.log("strcmp:", strcmp);
    Interceptor.attach(strcmp, {
        onEnter: function (args) {
            var str_2 = ptr(args[1]).readCString();
            if (str_2 == "EoPAoY62@ElRD") {
                console.log("strcmp:", ptr(args[0]).readCString(),
                    ptr(args[1]).readCString());
            }
        },
        onLeave: function (retval) {}
    });

}

function write_reg_dat() {

    //frida 的api来写文件
    var file = new File("/sdcard/reg.dat", "w");
    file.write("EoPAoY62@ElRD");
    file.flush();
    file.close();
}


function write_reg_dat2() {

    //把C函数定义为NativeFunction来写文件
    var addr_fopen = Module.findExportByName("libc.so", "fopen");
    var addr_fputs = Module.findExportByName("libc.so", "fputs");
    var addr_fclose = Module.findExportByName("libc.so", "fclose");

    console.log("addr_fopen:", addr_fopen, "addr_fputs:", addr_fputs, "addr_fclose:", addr_fclose);
    var fopen = new NativeFunction(addr_fopen, "pointer", ["pointer", "pointer"]);
    var fputs = new NativeFunction(addr_fputs, "int", ["pointer", "pointer"]);
    var fclose = new NativeFunction(addr_fclose, "int", ["pointer"]);

    var filename = Memory.allocUtf8String("/sdcard/reg.dat");
    var open_mode = Memory.allocUtf8String("w+");
    var file = fopen(filename, open_mode);
    console.log("fopen file:", file);

    var buffer = Memory.allocUtf8String("EoPAoY62@ElRD");
    var ret = fputs(buffer, file);
    console.log("fputs ret:", ret);

    fclose(file);
}

function main() {
    // 去掉hook_java()后不会出问题
    // hook_java();
    // hook_native();
    hook_libart();
    // hook_libc();
}

setImmediate(main);
```

## frida-ollvm字符串加密

- `.datadiv_decodexxxxx`格式为默认的ollvm混淆字符串存放处
- JNI_OnLoad参数类型为JavaVM*（需要IDA导入C的头文件，jni.h）
- ollvm默认混淆：静态下为加密字符串，运行时调用`.init_array`中函数进行解密

### hellojni_2.0.0

- ollvm默认混淆后的字符串在内存中为解密后的状态，所以hook native并打印即可

```js
// com.example.hellojni_sign2
function hook_native(){
    var base_hellojni = Module.findBaseAddress("libhello-jni.so");
    if (base_hellojni){
        // ollvm默认混淆：静态下为加密字符串
        // 运行时调用.init_array中函数进行解密
        var addr_37070 = base_hellojni.add(0x37070); // 使用add()方法对指针做加法
        console.log("addr_37070: ", ptr(addr_37070).readCString());

        var addr_37080 = base_hellojni.add(0x37080); // 使用add()方法对指针做加法
        console.log("addr_37080: ", ptr(addr_37080).readCString());
    }
}

function print_string(addr){
    var base_hellojni = Module.findBaseAddress("libhello-jni.so");
    var str_addr = base_hellojni.add(addr);
    if(base_hellojni){
        console.log("address:0x"+addr.toString(16), ", String:", ptr(str_addr).readCString());
    }
}

function main(){
    hook_native();
}

setImmediate(main);
```

### hellojni_2.0.1

- 在上个应用基础上去掉了`.datadiv_decode`段名（修改ollvm源码）：前往`.init_array`查看函数指针

### hellojni_2.0.2

- 在上个应用基础上修改了`.init_array`内容，解密的字符串不再存放在此处：查看JNIOnLoad

```js
function hook_native(){
    // var base_addr = Module.findBaseAddress("libhello-jni.so");
    var module = Process.findModuleByName("libart.so");
    var symbol = module.enumerateSymbols();
    var addr_registernatives = null;
    if(symbol){
        for(var i = 0; i<symbol.length;i++){
            var name = symbol[i].name;
            if(name.indexOf("RegisterNatives") >= 0){
                addr_registernatives = symbol[i].address;
            }
        }
        if (addr_registernatives){
            Interceptor.attach(addr_registernatives, {
                onEnter: function(args){
                    console.log("v9:", hexdump(args[2]));
                    console.log(args[2]);
                    console.log(args[2].readPointer().readCString());
                    console.log(args[2].readPointer().add(Process.pointerSize).readCString()); // 偏移一个ARM64指针的大小
                    console.log("");
                },
                onLeave: function(ret){
                    console.log("leave", ret);
                }
            });
        }
    }
}

// 从函数中非开始指令开始hook
// libhello-jni.so在程序启动时无法hook（未加载），因此需要hook dlopen
function inline_hook(){
    var hellojni = Module.findBaseAddress("libhello-jni.so");
    if (hellojni){
        var hook_addr = hellojni.add(0x7320);
        Interceptor.attach(hook_addr, {
            onEnter: function(args){
                // 打印寄存器w13 w14（ARM64下为x13 x14）
                // x14 即为解密后的字符串数据
                // 有时不稳定，ARM64下指令均为四字节因此没有失败
                console.log("x13", this.context.x13, "x14",this.context.x14);
            },
            onLeave: function(ret){
            }
        });
    }
}

function hook_dlopen(){
    // android 6.0以前此方法可用
    var dlopen_addr = Module.findExportByName(null, "dlopen");
    Interceptor.attach(dlopen_addr, {
        onEnter: function(args){
            console.log("dlopen args[0]:", ptr(args[0]).readCString());
        },onLeave: function(ret){

        }
    });

    // 后续版本用此方法代替
    var dlopen_ext = Module.findExportByName(null, "android_dlopen_ext");
    Interceptor.attach(dlopen_ext, {
        onEnter: function(args){
            // 判断找到hellojni则hook
            this.call_hook = false;
            if (ptr(args[0]).readCString().indexOf("hello") >= 0){
                console.log("dlopen args[0]:", ptr(args[0]).readCString());
                this.call_hook = true;
            }
        },
        onLeave: function(ret){
            if(this.call_hook){
                inline_hook();
            }
        }
    });
}

function main(){
    // hook_native();
    // inline_hook();
    hook_dlopen();
}

setImmediate(main);
```
