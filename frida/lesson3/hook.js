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

function hook_libart() {
    var module_libart = Process.findModuleByName("libart.so");
    var symbols = module_libart.enumerateSymbols(); //枚举模块的符号

    var addr_GetStringUTFChars = null;
    var addr_FindClass = null;
    var addr_GetStaticFieldID = null;
    var addr_SetStaticIntField = null;

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
    // hook_libart();
    hook_libc();
}

setImmediate(main);