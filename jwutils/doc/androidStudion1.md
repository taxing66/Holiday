调试和配置文件工具
Android Studio 可帮助您调试和改进代码的性能，包括内联调试和性能分析工具。
内联调试信息包括：

内联变量值
引用某选定对象的引用对象
方法返回值
Lambda 和运算符表达式
工具提示值
http://blog.csdn.net/dd864140130/article/details/51560664
性能监视器
Android Studio 提供性能监视器，让您可以更加轻松地跟踪应用的内存和 CPU 使用情况、查找已解除内存分配的对象、查找内存泄漏以及优化图形性能和分析网络请求。在设备或模拟器上运行您的应用时，打开 Android Monitor 工具窗口，然后点击 Monitors 选项卡。
https://developer.android.com/studio/profile/android-monitor.html
堆转储
在 Android Studio 中监控内存使用情况时，您可以同时启动垃圾回收并将 Java 堆转储为 Android 专有 HPROF 二进制格式的堆快照文件。HPROF 查看器显示类、每个类的实例以及引用树，可以帮助您跟踪内存使用情况，查找内存泄漏。
https://developer.android.com/studio/profile/am-memory.html#dumping
分配跟踪器
Android Studio 允许在监视内存使用情况的同时跟踪内存分配情况。 利用跟踪内存分配功能，您可以在执行某些操作时监视对象被分配到哪些位置。 了解这些分配后，您就可以相应地调整与这些操作相关的方法调用，从而优化应用的性能和内存使用。
https://developer.android.com/studio/profile/am-allocation.html
代码检查
在您每次编译程序时，Android Studio 都将自动运行已配置的 Lint 及其他 IDE 检查，以帮助您轻松识别和纠正代码结构质量问题。

Lint 工具可检查您的 Android 项目源文件是否有潜在的错误，以及在正确性、安全性、性能、可用性、无障碍性和国际化方面是否需要优化改进。
Android Studio 中的注解
Android Studio 支持为变量、参数和返回值添加注解，以帮助您捕捉错误，例如 null 指针异常和资源类型冲突。 Android SDK 管理器将支持注解库纳入 Android 支持存储库中，供与 Android Studio 结合使用。 Android Studio 在代码检查期间验证已配置的注解。
https://developer.android.com/studio/write/annotations.html
日志消息
在使用 Android Studio 构建和运行应用时，您可以点击窗口底部的 Android Monitor 查看 adb 输出和设备日志消息 (logcat)。

如果您想使用 Android 设备监视器调试您的应用，您可以点击 Tools > Android > Android Device Monitor 启动设备监视器。设备监视器中提供全套的 DDMS 工具，您可以使用这些工具进行应用分析和设备行为控制等操作。此外，该监视器还包括层次结构查看器工具，可帮助您优化布局。
https://developer.android.com/studio/profile/monitor.html