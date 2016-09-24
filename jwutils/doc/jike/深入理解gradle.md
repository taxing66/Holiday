http://www.groovy-lang.org/api.html （groovy api）
http://wiki.jikexueyuan.com/project/deep-android-gradle/three-three.html(深入理解 android gradle)
http://wiki.jikexueyuan.com/project/deep-android-gradle/four-one.html
1 Image
  ['ɪmɪdʒ]  英['ɪmɪdʒ]  美['ɪmɪdʒ]
  i ma ge
  n. 影像；想象；肖像；偶像
  vt. 想象；反映；象征；作…的像
  n. (Image)人名；(法)伊马热
  
  网络释义:
  Image - 图像; 像; 形象
  Digital image - 数字图像; 数字图像; 数字成像
  Core Image - Core Image; Core Image; 核心图像
  
深入理解 Android 之 Gradle

Gradle 是构建工具。
特点：快速、高效和多版本产出

闲言构建

构建，叫 build 也好，叫 make 也行。反正就是根据输入信息然后干一堆事情，最后得到几个产出物（Artifact）。

最最简单的构建工具就是 make 了。make 就是根据 Makefile 文件中写的规则，执行对应的命令，然后得到目标产物。

日常生活中，和构建最类似的一个场景就是做菜。输入各种食材，然后按固定的工序，最后得到一盘菜。当然，做同样一道菜，由于需求不同，做出来的东西也不尽相同。比如，宫保鸡丁这道菜，回民要求不能放大油、口淡的要求少放盐和各种油、辣不怕的男女汉子们可以要求多放辣子....总之，做菜包含固定的工序，但是对于不同条件或需求，需要做不同的处理。

这种“编程”不要搞得和程序员理解的编程那样复杂。寥寥几笔，轻轻松松把要做的事情描述出来就最好不过。所以，Gradle 选择了 Groovy。Groovy 基于 Java 并拓展了 Java。 Java 程序员可以无缝切换到使用 Groovy 开发程序。Groovy 说白了就是把写 Java 程序变得像写脚本一样简单。写完就可以执行，Groovy 内部会将其编译成 Java class 然后启动虚拟机来执行。当然，这些底层的渣活不需要你管。

gradle 特色：除了可以用很灵活的语言来写构建规则外，Gradle 另外一个特点就是它是一种 DSL，即 Domain Specific Language，领域相关语言。什么是 DSL，说白了它是某个行业中的行话。程序专有名词。



到此，大家应该明白要真正学会 Gradle 恐怕是离不开下面两个基础知识：

Groovy，由于它基于 Java，所以我们仅介绍 Java 之外的东西。了解 Groovy 语言是掌握 Gradle 的基础。

Gradle 作为一个工具，它的行话和它“为人处事”的原则。

         applicationVariants.all { variant ->
                variant.outputs.each { output ->
                    def date = new Date();
                    def formattedDate = date.format('yyyyMMddHHmmss')
                    def manifestParser = new DefaultManifestParser(android.sourceSets.main.manifest.srcFile)
                    def wmgVersionCode = manifestParser.getVersionName()+"-"+manifestParser.getVersionCode()
                    def outputDir = "".equals(RELEASE_OUTPUT_DIR) ? output.outputFile.parent : RELEASE_OUTPUT_DIR
                    println wmgVersionCode
                    println outputDir
                    output.outputFile = new File(outputDir,
                            output.outputFile.name.replace("app-release", "PTCatEnterprise-release-v" + wmgVersionCode + "-" + formattedDate)
                    )
                }
            }
            
            Groovy 注释标记和 Java 一样，支持//或者/**/
            Groovy 语句可以不用分号结尾。
            Groovy 中支持动态类型，即定义变量的时候可以不指定其类型。Groovy 中，变量定义可以使用关键字 def。注意，虽然 def 不是必须的，但是为了代码清晰，建议还是使用 def 关键字
          def variable1 = 1   //可以不使用分号结尾  
          def varable2 = "I am a person"
          def  int x = 1   //变量定义时，也可以直接指定类型
          
          函数定义时，参数的类型也可以不指定。比如
          String testFunction(arg1,arg2){//无需指定参数类型  
            ...
          }
          除了变量定义可以不指定类型外，Groovy 中函数的返回值也可以是无类型的。比如：
          //无类型的函数定义，必须使用 def 关键字  
          def  nonReturnTypeFunc(){
               last_line   //最后一行代码的执行结果就是本函数的返回值  
          
          }
          //如果指定了函数返回类型，则可不必加 def 关键字来定义函数  
          String  getString(){
             return "I am a string"
          }
          
          Groovy 对字符串支持相当强大，充分吸收了一些脚本语言的优点：
          1  单引号''中的内容严格对应 Java 中的 String，不对$符号进行转义  
             def singleQuote='I am $ dolloar'  //输出就是 I am $ dolloar
          2  双引号""的内容则和脚本语言的处理有点像，如果字符中有$号的话，则它会$表达式先求值。  
             def doubleQuoteWithoutDollar = "I am one dollar" //输出 I am one dollar
             def x = 1
             def doubleQuoteWithDollar = "I am $x dolloar" //输出 I am 1 dolloar
          3 三个引号'''xxx'''中的字符串支持随意换行 比如  
             def multieLines = ''' begin
               line  1 
               line  2
               end '''
          最后，除了每行代码不用加分号外，Groovy 中函数调用的时候还可以不加括号。比如：
          println("test") ---> println "test"
          --------------------------------
          Groovy 中的数据类型
          Groovy 中的数据类型我们就介绍两种和 Java 不太一样的：
          
          一个是 Java 中的基本数据类型。
          
          另外一个是 Groovy 中的容器类。
          
          最后一个非常重要的是闭包。
          放心，这里介绍的东西都很简单
          
          基本数据类型
          
          作为动态语言，Groovy 世界中的所有事物都是对象。所以，int，boolean 这些 Java 中的基本数据类型，在 Groovy 代码中其实对应的是它们的包装数据类型。比如 int 对应为 Integer，boolean 对应为 Boolean。比如下图中的代码执行结果：
          
          int 实际上是 Integer
          
          容器类
          
          Groovy 中的容器类很简单，就三种：
          
          List：链表，其底层对应 Java 中的 List 接口，一般用 ArrayList 作为真正的实现类。
          Map：键-值表，其底层对应 Java 中的 LinkedHashMap。
          Range：范围，它其实是 List 的一种拓展。
          对容器而言，我们最重要的是了解它们的用法。下面是一些简单的例子：
          
          1.List 类
          
          变量定义：List 变量由[]定义，比如
          
          def aList = [5,'string',true] //List 由[]定义，其元素可以是任何对象  
          变量存取：可以直接通过索引存取，而且不用担心索引越界。如果索引超过当前链表长度，List 会自动 往该索引添加元素
          
          assert aList[1] == 'string'
          assert aList[5] == null //第 6 个元素为空  
          aList[100] = 100  //设置第 101 个元素的值为 10
          assert aList[100] == 100
          那么，aList 到现在为止有多少个元素呢？
          
          println aList.size  ===>结果是 101
          2.Map 类
          
          容器变量定义
          
          变量定义：Map 变量由[:]定义，比如
          
          def aMap = ['key1':'value1','key2':true] 
          Map 由[:]定义，注意其中的冒号。冒号左边是 key，右边是 Value。key 必须是字符串，value 可以是任何对象。另外，key 可以用''或""包起来，也可以不用引号包起来。比如
          
          def aNewMap = [key1:"value",key2:true] //其中的 key1 和 key2 默认被  
          处理成字符串"key1"和"key2" 不过 Key 要是不使用引号包起来的话，也会带来一定混淆，比如
          
          def key1="wowo"
          def aConfusedMap=[key1:"who am i?"]
          aConfuseMap 中的 key1 到底是"key1"还是变量 key1 的值“wowo”？显然，答案是字符串"key1"。如果要是"wowo"的话，则 aConfusedMap 的定义必须设置成：  
          def aConfusedMap=[(key1):"who am i?"]
          Map 中元素的存取更加方便，它支持多种方法：
          
          println aMap.keyName    <==这种表达方法好像 key 就是 aMap 的一个成员变量一样  
          println aMap['keyName'] <==这种表达方法更传统一点  
          aMap.anotherkey = "i am map"  <==为 map 添加新元素  
          3.Range 类
          
          Range 是 Groovy 对 List 的一种拓展，变量定义和大体的使用方法如下：
          
          def aRange = 1..5  <==Range 类型的变量 由 begin 值+两个点+end 值表示  
                                左边这个 aRange 包含 1,2,3,4,5 这 5 个值  
          如果不想包含最后一个元素，则
          
          def aRangeWithoutEnd = 1..<5  <==包含 1,2,3,4 这 4 个元素  
          println aRange.from
          println aRange.to






















