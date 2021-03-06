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