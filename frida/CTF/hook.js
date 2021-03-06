function hook_java(){
    Java.perform(function(){
        var MainActivity = Java.use("com.example.ctf.MainActivity");
        MainActivity.Check.implementation = function(str1, str2){
            var res = this.Check(str1, str2);
            console.log(str1, str2, res);
        }
    });
}

function hook_native(){
    var base = Module.findBaseAddress("libnative-lib.so");
    console.log("base addr of native: ", base);
    if(base){
        // var Check = Module.findExportByName("libnative-lib.so","Check");
        var Check = base + 0x918;
        if(Check){
            console.log("Address of Check() found.");
            Interceptor.attach(Check, {
                onEnter:function(args){
                    console.log("OnEnter Check() : ", ptr(args[1]).readCString(), args[2]);
                },
                onLeave:function(ret){
                    console.log("Call of Check() finished.");
                }
            });
        }
    }
}

function main(){
    // hook_native();
    hook_java();
}

setImmediate(main);