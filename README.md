# 安卓逆向

> 安卓高级研修班（网课）月薪三万计划，请务必不要外泄资源，仅限天问内部查看

## 01 frida高级逆向

- frida hook Java
- frida hook native
- frida辅助分析ollvm字符串加密
- frida辅助分析ollvm控制流平坦化
- frida辅助分析ollvm指令替换
- frida辅助分析ollvm虚假控制流
- frida辅助分析非标准算法
- IDA trace辅助分析非标准算法
- IDA trace分析被ollvm混淆的非标准算法

## 02 fart全自动脱壳机

- 脱壳意义和加壳原理
- 加壳技术发展和识别
- Dalvik下DEX加载流程和通用脱壳点
- ART下DEX加载流程和通用脱壳点
- ART下抽取壳实现
- FART中的脱壳点
- FART主动调用组件设计和源码分析
- FART frida
- FART修复组件和辅助VMP分析

## 03 ARM & C++算法还原原理

- ARM可执行程序的生成过程
- ARM汇编寻址、汇编指令、汇编开发
- ARM汇编指令集
- Thumb汇编指令集 AAAArch64汇编指令级
- C程序逆向：数据类型、运算符、分支与跳转、循环
- C程序逆向：函数、结构体、数组、位操作
- C++类、构造/析构函数、虚函数、虚表
- C++继承、重载、覆盖、RTTI、异常
- Android Studio汇编开发、内联汇编、内联汇编syscall、CMakeLists.txt加载S文件

## 04 C++11 & ART虚拟机开发

- C++11概念和基础数据类型
- 类型推导和访问权限相关
- 模板函数和模板类
- 模板函数和lambda
- ART中的C++对象内存布局
- ART中的C++对象内存布局实践篇
- ART中的函数inline
- ART定制方案比较和流程
- ART定制跟踪JNI函数绑定

## 05 彻底搞懂OLLVM

- LLVM简介、LLVM编译、CLion调试LLVM-最优化的质量和大小
- LLVM Pass：函数名称加密Pass
- OLLVM简介和移植-最优化的质量和大小
- fla 控制流平坦化-最优化的质量和大小
- bcf 虚假控制流-最优化的质量和大小
- Instructions Suubstitution SplitBasicBlock-最优化的质量和大小
- 编写字符串加密Pass-最优化的质量和大小
- 移植OLLVM到NDK中
- 逆向OLLVM算法的通用方法
- 逆向OLLVM算法的非通用方法（最优化的质量和大小）

## 06 高级调试和VMP

- 安卓APP夹克技术分类与初识VMP
- VMP保护的函数的快速逆向分析方法理论篇
- VMP保护的函数的快速逆向分析方法实践篇
- ADVMP源码分析与VMP壳简单上手
- 定制ART，绕过所有反调试
- Hyperpwn的安装与使用
- 算法还原案例 OLLVM RC4
- 使用Hyperpen调试VMP并构建映射表

## 07 Unicorn unidbg

- Capstion、Unicorn、Keystone三兄弟
- Unicorn初识与上手
- Unicorn调用so中函数
- Unicorn模拟调用JNI接口函数
- Unicorn模拟调用JNI_OnLoad
- AndroidNativeEmu调用JNI函数
- AndroidNativeEmu模拟与Java函数交互
- Unidbg加载so并调用so中函数
- Unidbg模拟与Java交互

## 08 非标准算法还原

- 常用加解密算法简介
- 算法还原案例-Base64
- 算法还原案例-CRC32
- 算法还原案例-MD5
- 算法还原案例-OLLVM MD5
- 算法还原案例-OLLVM SHA1
- 算法还原案例-HMAC
- 算法还原案例-OLLVM Base64
- 定制内核体验内存断点的威力
- 算法还原案例-Frida Stalker OLLVM AES
