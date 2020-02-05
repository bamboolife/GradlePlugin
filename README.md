# GradlePlugin
此项目用于gradle plugin学习

## Gradle 插件

- Gradle 本身只提供基本框架和核心概念，几乎所有的功能都是以插件的方式提供的。
- 例如构建 Java 应用的功能就是通过 Java 插件实现的。
- Gradle 内置了很多核心语言插件，基本上能满足大部分的构建工作，但有些插件没有内置或者有些功能没有提供，我们也可以自定义插件来使用，例如 Android Gradle 插件就是基于 Java 插件扩展的。

## 插件的作用
- 插件可以封装一系列任务，例如 编译，测试，打包等。

- 插进可以定义约定，例如 应用Java 插件后，约定的源代码位置就在 src/main/java 目录下。

- 插件可以封装配置，当项目应用插件时自动应用插进的配置。

- 插件可以扩展 Gradle 模型，添加可配置的 DSL 元素，例如 Android Gradle 的 android{}

- 这就是插件，我们只需要按照它的约定的方式，使用它提供的任务、方法或扩展就可以对我们的项目进行构建。

- 使用插件可以将相似的代码进行封装以提高重用性，使构建脚本高度模块化，增强组织性和可读性。

## 插件类型
### Gradle 有两种插件

- 脚本插件
- 二进制插件
### 脚本插件
- 脚本插件通常是一个脚本。

- 脚本插件其实并不能算是一个真正的插件，但我们不能忽视它的作用，它是脚本模块化的基础。

- 我们可以把复杂的脚本文件，进行分块，分段整理，拆分成一个个职责分明的脚本插件。

- 就像我们平常封装的 Utils 工具类一样，封装一个 utils.gradle 工具脚本

### 二进制插件
二进制插件是实现了[Plugin](https://docs.gradle.org/current/javadoc/org/gradle/api/Plugin.html) 接口的插件，以编程的方式操纵构建。

二进制插件一般是打包在一个 jar 里独立发布的。

## 发现插件
Gradle 专门有一个网站可以发布和寻找插件 https://plugins.gradle.org

当然也可以在 Github 上搜索，开源的力量是强大的。

## 使用插件
要使用一个插件必选先找到它，然后把它应用到我们的项目。

将插件应用到我们的项目就可以使用它了。

所以使用一个插件需要两步：

- 1.找到插件并添加到类路径
- 2.应用插件到项目

一旦插件被应用就可以使用它的 API 了。

应用插件就是在目标项目执行插件的 [Plugin.apply(T)](https://docs.gradle.org/current/javadoc/org/gradle/api/Plugin.html#apply-T-) 方法。

插件的应用是幂等的，应用多次和一次的效果是一样的。

### 应用脚本插件
前面说过脚本插件其实就是一个脚本，应用脚本插件其实就是将这个脚本加载进来。

使用 apply from 将脚本加载进来
```gradle
apply from: 'other.gradle'
```
脚本可以存在本地，也可以存在网络上。

存在本地就使用本项目的相对路径。

存在网络上就要使用 HTTP URL 。

### 应用二进制插件
二进制插件应用是通过他们的 ID 应用的。

插件 ID 是插件的全局唯一标识符或者名字

Gradle 核心插件的特殊之处就在于他们都有一个简短的 ID，例如 Java 插件的是 "java" 。

其他所有的二进制插件都必须使用完全限定形式的插件 ID (com.github.foo.bar) 。

插件 ID 在哪用就取决于你是使用 plugins{} 还是 buildscript{} 了。

核心插件是作为 Gardle 的一部分在应用时是由 Gradle 分发并自动解析的。

而其他的二进制插件则必须在应用前被找到并解析。

Gradle 通常是在下面这个几个地方去寻找

- 在上述提到的 Plugins 网站上或者自定义的仓库里
- 在指定的外部依赖 jar
- 在项目的 buildSrc 目录下寻找源文件
- 声明在脚本里的插件

目前有两种方式使用插件，一种是使用新推出的 plugins{} 应用插件，另一种是使用 buildscript{} 应用插件。

目前 Gradle 推荐使用 plugins{} 。

下面是两个方式的使用方法。

### 使用 buildscript{} 应用二进制插件
使用 buildscript{} 块应用的二进制插件都是通过 Project.apply() 方法完成的。

应用 Java 插件: build.gradle
```gradle
apply plugin: 'java'
```
这里的 "java" 就是 Java 插件的 ID，它对应的类型是 org.gradle.api.plugins.JavaPlugin

也可以通过类型应用插件：build.gradle
```gradle
apply org.gradle.api.plugins.JavaPlugin
```
因为 org.gradle.api.plugins 是默认导入的，所以也可以直接去掉包名称直接写为
```gradle
apply plugin:JavaPlugin
```
要使用第三方的插件就必须先在 buildscript{} 配置 类路径才可以使用。

这个不像 Gradle 的内置插件，是由 Gradle 负责分发的。

例如我们的 Android Gradle 插进，就属于 Android 发布的第三方插件，如果要使用就先要进行配置
```gradle
buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.2.0'
    }
}
```
因为 Android Gradle 插进是托管在 jcenter 仓库上，所以必须在 repositories{} 添加 jcenter 仓库。

buildscript{} 块是一个在构建项目之前，为项目进行前期准备和初始化相关配置依赖的地方，配置好所需的依赖，就可以应用插件了：
```gradle
apply plugin: 'com.android.application'
```
如果没有提前在 buildscript{} 里配置依赖的类路径，会提示找不到这个插件。

### 使用 plugins DSL 应用二进制插件

plugins DSL 配置可以很方便的访问 Gradle 插件网站上的插件。

这个配置块配置的是一个[PluginDependenciesSpec](https://docs.gradle.org/current/javadoc/org/gradle/plugin/use/PluginDependenciesSpec.html) 实例

应用核心插件
```gradle
plugins {
    id 'java'
}
```
使用插件 ID （必须是完全限定形式）应用第三方插件
```gradle
plugins {
    id 'com.jfrog.bintray' version '0.4.1'
}
```
使用 plugins{} 应用插件就不用先配置依赖然后再使用 apply 方法了，只需要在 plugins{} 里配置就可以了。

### plugins DSL 的限制

虽然 plugins DSL 将使用插件的步骤精简了，但还是有一些限制的，它的语法被固定了，不能改变
```gradle
plugins {
    id «plugin id»                                            // (1)
    id «plugin id» version «plugin version» [apply «false»]   // (2)
}
```
- 1.适用于有简写名字的核心插件或者已经加载过的第三方插件
- 2.适用于需要被解析的第三方插件

plugin id 和 plugin version 是必需的 且必须是常量,字面量,字符串.其他语句都是不允许的

apply 是可选的，默认是 true；它是控制在应用插件的时候是否立刻使用插件的默认行为。

这个 plugins{} 块 是不能嵌套在别的配置块里的，必须是脚本的顶级模块。

在目前的 5.6.2 版本里 plugins{} 只能在项目的构建脚本里使用，不能在脚本插件里，settings.gradle 以及 init.gradle 脚本里。

### 应用插件到子项目

可以利用 plugins {} 的 apply 选项将插件应用到某些项目而不是所有项目里。

默认 plugins {} 的插件是立即被解析并应用的。

可以使用 apply false 告诉 Gradle 不应用到当前项目,
然后在子项目的脚本中使用 apply plugin <> 或者 plugins{} 应用插件

settings.gradle
```gradle
include 'helloA'
include 'helloB'
include 'goodbyeC'
```
build.gradle
```gradle
plugins {
    id 'org.gradle.sample.hello' version '1.0.0' apply false
    id 'org.gradle.sample.goodbye' version '1.0.0' apply false
}



subprojects {
    if (name.startsWith('hello')) {
        apply plugin: 'org.gradle.sample.hello'
    }
}
```
goodbyeC/build.gradle
```gradle
plugins {
    id 'org.gradle.sample.goodbye'
}
```
上面的示例演示了如何给多个子项目分别应用不同的插件。

### 使用 buildSrc 目录下的插件
项目的 buildSrc 目录下的插件也是可以使用的，但必须定义插件 ID 。

下面是一个使用 buildSrc 目录下的插件的示例

这个插件 ID 是 my-plugin 实现的插件类是 my.MyPlugin

buildSrc/build.gradle
```gradle
plugins {
    id 'java'
    id 'java-gradle-plugin'
}

gradlePlugin {
    plugins {
        myPlugins {
            id = 'my-plugin'
            implementationClass = 'my.MyPlugin'
        }
    }
}

dependencies {
    compileOnly gradleApi()
}
```
使用插件
```gradle
plugins {
    id 'my-plugin'
}
```
### Plugin Management

这个 pluginManagement DSL 是用来管理插件的，它可以配置插件，自定义仓库，自定义解析规则等。

pluginManagement{} 只能定义在两个地方：

- settings.gradle 里，并且必须是第一个模块
- init.gradle 里

settings.gradle
```gradle
pluginManagement {
    plugins {
    }
    resolutionStrategy {
    }
    repositories {
    }
}
```
init.gradle
```gradle
settingsEvaluated { settings ->
    settings.pluginManagement {
        plugins {
        }
        resolutionStrategy {
        }
        repositories {
        }
    }
}
```
### 自定义仓库
plugins 默认是在 Gradle 的插件网站寻找插件的

我们可以在 pluginManagement{} 里的 repositories{} 里配置自己的仓库

Gradle 会按照配置的仓库顺序依次寻找插件

settings.gradle
```gradle
pluginManagement {
    repositories {
        maven {
            url '../maven-repo'
        }
        gradlePluginPortal()
        ivy {
            url '../ivy-repo'
        }
    }
}
```
首先在位于 ../maven-repo 的Maven 仓库找，找不到再去 Gradle 的插件网站 ，最后是位于 ../ivy-repo 的 ivy 仓库。

### 插件版本管理
在 pluginManagement{} 里的 plugins{} 里配置插件是没有固定语法限制的。

可以把所有的版本都放在一个单独的脚本里，或者是使用 gradle.properties 文件配置属性

settings.gradle
```gradle
pluginManagement {
  plugins {
        id 'org.gradle.sample.hello' version "${helloPluginVersion}"
    }
}
```gradle
build.gradle

plugins {
    id 'org.gradle.sample.hello'
}
```
gradle.properties
```gradle
helloPluginVersion=1.0.0
```
### 解析规则

解析规则就是解析插件的规则，通过自定义插件规则可以更改 plugins{} 块中的插件请求，例如更改请求的版本或显式指定实现工件坐标。

解析规则是在 pluginManagement{} 里的 resolutionStrategy{} 里配置的。

插件解析策略：
```gradle
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == 'org.gradle.sample') {
                useModule('org.gradle.sample:sample-plugins:1.0.0')
            }
        }
    }
    repositories {
        maven {
            url '../maven-repo'
        }
        gradlePluginPortal()
        ivy {
            url '../ivy-repo'
        }
    }
}
```
这告诉 Gradle 使用指定的插件实现工件，而不是使用从插件 ID 到 Maven / lvy 坐标的内置默认映射。

除了实际实现插件的工件之外，自定义 Maven 和 lvy 插件仓库必需包含 插件标记工件。

关于将插件发布到自定义的仓库的可以查看 Gradle 插件开发：
[传送门](https://docs.gradle.org/current/userguide/java_gradle_plugin.html#java_gradle_plugin)

### Plugin Marker Artifacts (插件标记工件)

这里插入一个 Maven 中的概念：Artifact (工件)

> Artifact 的概念比较抽象，它是 Maven 项目将要产生的文件。
一个Maven 项目可能会产生多个 Artifact ，可能是 jar 文件，源文件，二进制文件，war 文件，甚至是 pom 文件。
每一个 artifact 都有一个 group ID (通常是反转的域名，就像包名)，一个 artifact ID （一个名字）以及一个版本号，
这三个组合在一个表示一个 artifact 的坐标：groupId:artifiactId:version

由于 plugins DSL 块只允许通过全局唯一的插件 id 和 version 属性声明插件，因此 Gradle 需要一种方法来查找 插件实现工件 的坐标。
为此，Gradle 将使用坐标查找 插件标记工件 plugin.id:plugin.id.gradle.plugin:plugin.version 。
此标记需要依赖于实际的插件实现。java-gradle-plugin 会自动发布这些标记。

例如，下面的完整示例 sample-plugins 演示了如何使用 java-gradle-plugin，maven-publish 插件和 ivy-publish 插件的组合将 org.gradle.sample.hello 插件和 org.gradle.sample.goodbye 插件发布到 Ivy 和 Maven 仓库。

### 完整插件发布示例
```gradle
plugins {
    id 'java-gradle-plugin'
    id 'maven-publish'
    id 'ivy-publish'
}

group 'org.gradle.sample'
version '1.0.0'

gradlePlugin {
    plugins {
        hello {
            id = 'org.gradle.sample.hello'
            implementationClass = 'org.gradle.sample.hello.HelloPlugin'
        }
        goodbye {
            id = 'org.gradle.sample.goodbye'
            implementationClass = 'org.gradle.sample.goodbye.GoodbyePlugin'
        }
    }
}

publishing {
    repositories {
        maven {
            url '../../consuming/maven-repo'
        }
        ivy {
            url '../../consuming/ivy-repo'
        }
    }
}
```
运行 gradle publish 将会产生如下结构
