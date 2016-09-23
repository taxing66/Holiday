http://kvh.io/cn/embrace-android-studio-migration.html
1 Invalidateg
  [ɪn'vælɪdeɪt]  英[ɪn'vælɪdeɪt]  美[ɪn'vælɪdet]
  in va li date 使瓦力在有效期无效
  vt. 使无效；使无价值
  
  网络释义:
  invalidate - 使无效; 无效; 使无效力
  to invalidate - 废弃
  write invalidate - 写无效; 写作废
  

Android 开发因为涉及到代码编辑、UI 布局、打包等工序，最好使用一款 IDE。Google 最早提供了基于 Eclipse 的 ADT 作为开发工具，
后于2013年 Google I/O 大会发布基于 IntelliJ IDEA 开发 Android Studio。后者正式版发布之后，Google 宣布不再持续支持 ADT。
Android Studio 有更快的速度，更好的代码完成等特性。
让更多的人拥抱变化，拥抱新鲜事物。
Android Studio 的优胜的地方。

更快的运行速度；
更智能的自动补全；


更好的重构，IDEA 会帮你找到每一个需要重构的地方；
更好的索引与搜索，双击 shift 键搜索，文件搜索，代码搜索，都很方便；
更好的版本管理功能（git/SVN）；
更灵活强大集成脚本 gradle；
原生支持的从项目文件到操作系统的文件浏览器功能；

常用网站

部分需要科学上网

你无法科学上网？赶紧搜一下，自己搭或者买一个吧。世界如此多娇，人生苦短。将你遇到过问题的出错信息用 Google 搜索，第一条来自 stackoverflow 的答案就是你想要的。

Android Studio 下载页
Android Studio：Android Developer 上 Android Studio 的介绍
Android Tools：Android 工具首页，有大量的深度的技术文档 还有技术视频
Android Build System：基于 gradle 的 Android 构建系统，最为关键的一篇文章
从 ADT 迁移指南：官方提供的迁移手册
Android Studio 国内下载渠道：良心网站，你懂的，但是笔者不对 Xcode Ghost之类的事情负责

android studio 下的项目结构：
Android Studio 的一个 project (项目)下可以包含多个独立的 module（模块）。

project/build.gradle：项目的 build.gradle 文件，主要是全局的远程仓库配置jcenter、gradle 插件版本等信息及任务清理
{
构建脚本
buildscript {
      规程仓库
     repositories {
     java center
         jcenter()
     }
     //依赖
     dependencies {
         //gradle 对于android插件版本信息
         类路径：com.android.工具。构建：gradle:版本号
         classpath 'com.android.tools.build:gradle:2.2.0'
 
         // NOTE: Do not place your application dependencies here; they belong
         // in the individual module build.gradle files
     }
 }
 所有工程
 allprojects {
 
 //    运程仓库
     repositories {
         jcenter()
     }
 }
 任务清除
 task clean(type: Delete) {
     删除 要项目。构建路径
     delete rootProject.buildDir
 }}

project/app：名字为 app 的模块

project/app/build.gradle：模块的 build.gradle 文件，是最关键的配置文件，后续会有文章继续专门介绍
申请插件为：com.android.资源库，应用
apply plugin: 'com.android.library'
//com.android.library标示是一个库模块；
//com.android.application标示这是一个应用模块

android {
    compileSdkVersion 19//编译 sdk 版本
    buildToolsVersion "23.0.2"//构建工具版本
     默认配置
    defaultConfig {
        minSdkVersion 9
        targetSdkVersion 19
    }
    构建打包类型
    buildTypes {//打包类型
        release {
            minifyEnabled false//是否混淆
            混淆文件 获取默认混淆文件（混淆-android.txt'）,"自定义混淆文件"
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.txt'
        }
    }
}

dependencies {//依赖管理
    compile 'com.bugtags.library:bugtags-lib:1.0.8'//远程
    编译文件树（目录：‘libs’,包括'x*.jar'）
    compile fileTree(dir: 'libs', include: ['*.jar'])//本地 jar 依赖
}

project/app/src：源码文件夹

project/app/src/java：放置代码文件

project/app/src/jniLibs：默认的.so 库放置的位置，可以在模块的 build.gradle 中通过jniLibs.srcDirs 值来修改所在位置

project/app/libs：放置 .jar 库

重点：
build variant

Android Studio 内置了多渠打包功能，就是所谓的 build variant，可以新建若干个 productflavor 来达到目的。

这个功能十分的灵活和强大，举个例子，你想要有两个版本，一个收费版，一个免费版，在若干代码文件和资源文件上，都不大一样。

以前你可能选择在代码中判断当前版本，加载不同的代码和资源。或者使用 git 的 branch。

现在你可以使用 Android Studio 直接使用多个 productflavor， 在对应的 flavor 放置那些有区别的代码和文件。一次打包，即可完成。
在下一篇系列文章中，笔者将会详细介绍这个功能。

--------------------------------
进阶篇：
方法：
在组织本系列文章的时候是先讲入门实例，进而学习 Gradle 和 Groovy 基础原理，最后学习进阶实例。

gradle 相关结构：

├── gradle
│   └── wrapper				//所使用的 Gradle 包装器配置
├── .gradle					//所使用 Gradle 版本
│   └── 2.8
├── AsInDepth.iml
├── app						//app module
│   ├── app.iml
│   ├── build
│   ├── build.gradle		//app module 的 build.gradle
│   ├── libs
│   ├── proguard-rules.pro
│   └── src
├── build.gradle			//项目 build.gradle，通常配置项目全局配置，如 repositories 和 dependencies
├── gradle.properties		//项目属性文件，通常可以放置一些常量
├── gradlew					//Gradle 包装器可执行文件
├── gradlew.bat				//Gradle 包装器可执行文件(Windows)
├── lib						//lib module
│   ├── build
│   ├── build.gradle		//lib module 的 build.gradle
│   ├── lib.iml
│   ├── libs
│   ├── proguard-rules.pro
│   └── src
├── local.properties		//项目的本地属性，通常是 sdk 所在位置
└── settings.gradle			//项目总体设置，通常是配置项目中所有的 module
https://docs.gradle.org/current/dsl/
Invalidate Cache
F:\devin_workplace\android_workplace\workspace\Holiday\.gradle
Android Studio 会出现索引的问题，那可以从删除 cache 重建索引，File->Invalidate Caches/Restart
