http://kvh.io/cn/embrace-android-studio-gradle-plugin.html

需求

默认的 Android 打包插件会把 apk 命名成 
module-productFlavor-buildType.apk，
例如 app-official-debug.apk，并且会把包文件发布到固定的位置：
module/build/outputs/apk 有的时候，
这个命名风格并不是你所要的，
你也想讲 apk 输出到别的目录。
咱们通过 gradle 插件来实现自定义。这个插件的需求是：

输入一个名为 nameMap 的 Closure，用来修改 apk 名字
输入一个名为 destDir 的 String，用于输出位置

原理简述

插件之于 Gradle

插件打包了可重用的构建逻辑，可以适用于不同的项目和构建过程。

Gradle 提供了很多官方插件，用于支持 Java、Groovy 等工程的构建和打包。
同时也提供了自定义插件的机制，让每个人都可以通过插件来实现特定的构建逻辑，
并可以把这些逻辑打包起来，分享给其他人。

插件的源码可以使用 Groovy、Scala、Java 三种语言。
groovy 前者用于实现与 Gradle 构建生命周期（如 task 的依赖）有关的逻辑，java用于核心逻辑，
表现为 Groovy 调用 Java 的代码。

用 Java 实现核心业务代码，将有利于实现快速迁移。

插件打包方式

Gradle 的插件有三种打包方式，主要是按照复杂程度和可见性来划分：

Build script

把插件写在 build.gradle 文件中，一般用于简单的逻辑，只在该 build.gradle 文件中可见，笔者常用来做原型调试。

buildSrc 项目

将插件源代码放在 rootProjectDir/buildSrc/src/main/groovy 中，只对该项目中可见，适用于逻辑较为复杂，但又不需要外部可见的插件。

独立项目

一个独立的 Groovy 和 Java 项目，可以把这个项目打包成 Jar 文件包，一个 Jar 文件包还可以包含多个插件入口，将文件包发布到托管平台上，供其他人使用。本文将着重介绍此类

















