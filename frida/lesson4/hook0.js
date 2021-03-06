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