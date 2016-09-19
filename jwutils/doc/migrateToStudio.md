1 Migrate
  [maɪ'greɪt; 'maɪgreɪt]  英[maɪ'greɪt; 'maɪgreɪt]  美['maɪɡret]
  mi grate
  vi. 移动；随季节而移居；移往
  vt. 使移居；使移植
  
  网络释义:
  migrate - 迁移; 移居; 迁徙
  Motorola Migrate - 摩托罗拉迁移
  migrate workers - 流动工人; 农民工
  eclipse migrate the android studio
  一、两者的不同：
  项目和模块组织
  Android Studio 基于 IntelliJ IDEA IDE。 如需了解 IDE 基础知识，例如导航、代码自动完成和键盘快捷键，请参阅探索 Android Studio。
  
  Android Studio 不使用工作区，因此各个项目在单独的 Android Studio 窗口中打开。 Android Studio 将代码组织到项目中，其中包含从应用源代码到构建配置和测试代码等定义 Android 应用的所有信息。每个项目包含一个或多个模块，您可以将项目分成独立的功能单元。模块可独立构建、测试和调试。
  
  如需了解有关 Android Studio 项目和模块的详细信息，请参阅项目概览。
  
  基于 Gradle 的构建系统
  Android Studio 的构建系统基于 Gradle，并使用以 Groovy 语法编写的构建配置文件，以便于扩展和自定义。
  
  如需了解有关使用和配置 Gradle 的详细信息，请参阅配置构建。
  
  依赖关系
  在 Android Studio 中，库依赖关系使用依赖关系声明，对于具有 Maven 坐标的已知本地源代码和二进制库，则使用 Maven 依赖关系。如需了解详细信息，请参阅配置构建变体。
  
  测试代码
  通过使用 Eclipse ADT，可在不同的项目中编写仪器测试，并通过清单文件中的 <instrumentation> 元素进行集成。Android Studio 在项目的主源代码集中提供 androidTest/ 目录，因此您可以在同一项目视图中轻松添加和维护仪器测试代码。 Android Studio 在项目的主源代码集中还提供了 test/ 目录，用于本地 JVM 测试。
  
 二、eclipse 迁移先决条件
  1. 确保 Eclipse ADT 根目录包含 AndroidManifest.xml 文件。 此外，根目录必须包含 Eclipse 的 .project 和 .classpath 文件或 res/ 和 src/ 目录。
  2. 在需要导入的 project.properties 或.classpath 文件中注释掉对 Eclipse ADT 工作区库文件的任何引用。 您可以在导入后在 build.gradle 文件中添加这些引用。 如需了解详细信息，请参阅配置构建。
  