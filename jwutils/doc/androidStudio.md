Android Studio 是基于 IntelliJ IDEA 的官方 Android 应用开发集成开发环境 (IDE)。
特点：
强大的代码编辑器和开发者工具，
提供了更多可提高 Android 应用构建效率的功能，build application
例如：

基于 Gradle 的灵活构建系统
快速且功能丰富的模拟器 + 可针对所有 Android 设备进行开发的统一的环境
Instant Run，可将变更推送到运行中的应用，无需构建新的 APK
可帮助您构建常用应用功能和导入示例代码的代码模板和 GitHub 集成
丰富的测试工具和框架
可捕捉性能、可用性、版本兼容性以及其他问题的 Lint 工具
C++ 和 NDK 支持
内置对 Google 云端平台的支持，可轻松集成 Google Cloud Messaging 和 App 引擎
本页介绍了 Android Studio 的基本功能。 有关最新变更摘要，请参阅 Android Studio 发行说明
android build app 的项目结构：
项目结构
每个项目project包含一个或多个含有(manifests 源代码文件和资源文件java and res,assets , jniLibs)的模块module。
所有构建文件在项目层次结构顶层 Gradle Scripts (脚本)下显示
选择项目的 Problems 视图会显示指向包含任何已识别编码和语法错误（例如布局文件中缺失一个 XML 元素结束标记）的源文件的链接
按两下 Shift 键或点击 Android Studio 窗口右上角的放大镜搜索源代码、数据库、操作和用户界面的元素等。此功能非常实用，例如在您忘记如何触发特定 IDE 操作时，可以利用此功能进行查找。
要显示或隐藏整个工具窗口栏，请点击 Android Studio 窗口左下角的窗口图标
使用最近文件操作在最近访问的文件之间切换。 按 Control+E
使用文件结构操作查看当前文件的结构。 按 Control+F12
使用导航至类操作搜索并导航至项目中的特定类。 按 Control+N
使用导航至文件操作导航至文件或文件夹。 按 Control+Shift+N
使用导航至符号操作按名称导航至方法或字段按 Control+Shift+Alt+N
按 Alt+F7 查找引用当前光标位置处的类、方法、字段、参数或语句的所有代码片段

------------
版本控制基础知识
--------------
Gradle 构建系统
Android Studio 基于 Gradle 构建系统，并通过 Android Gradle 插件'com.android.tools.build:gradle:2.1.2'提供更多面向 Android 的功能。
自定义、配置和扩展构建流程。
使用相同的项目和模块为您的应用创建多个具有不同功能的 APK。
在不同源代码集中重复使用代码和资源。
利用 Gradle 的灵活性，您可以在不修改应用核心源文件的情况下实现以上所有目的。 Android Studio 构建文件以 build.gradle 命名。 这些文件是纯文本文件，使用 Android Gradle 插件提供的元素以 Groovy 语法配置构建。每个项目有一个用于整个项目的顶级构建文件，以及用于各模块的单独的模块层级构建文件。在导入现有项目时，Android Studio 会自动生成必要的构建文件。
https://developer.android.com/studio/build/index.html#build-config 自由构建配置
构建变体：
构建系统可帮助您从一个项目创建同一应用的不同版本。 如果您同时拥有免费版本和付费版本的应用，或想要在 Google Play 上为不同设备配置分发多个 APK，则可以使用此功能
https://developer.android.com/studio/build/build-variants.html#workBuildVariants 构建变体APK
APK 拆分
通过 APK 拆分，您可以高效地基于屏幕密度或 ABI 创建多个 APK。 例如，您可以利用 APK 拆分创建单独的 hdpi 和 mdpi 版本应用，同时仍将它们视为一个变体，并允许其共享测试应用、javac、dx 和 ProGuard 设置。
https://developer.android.com/studio/build/configure-apk-splits.html 构建拆分APK
资源压缩
Android Studio 中的资源压缩功能可自动从您打包的应用和库依赖关系中删除不使用的资源。 例如，如果您的应用正在使用 Google Play 服务，以访问 Google 云端硬盘功能，且您当前未使用 Google Sign-In，则资源压缩功能可删除 SignInButton 按钮的各种可绘制资产 注： 资源压缩与代码压缩工具（例如 ProGuard）协同工作。
https://developer.android.com/studio/build/shrink-code.html 
管理依赖关系
项目的依赖关系在 build.gradle 文件中按名称指定。 Gradle 可自动查找您的依赖关系，并在构建中提供。 您可以在 build.gradle 文件中声明模块依赖关系、远程二进制依赖关系以及本地二进制依赖关系。 Android Studio 配置项目时默认使用 Maven 中央存储库。
https://developer.android.com/studio/build/build-variants.html#dependencies 