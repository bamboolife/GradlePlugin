package com.bamboo.myplugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class FireantPlugin implements Plugin<Project> {
    public void apply(Project project) {
        // Register a task，Gradle init 时自动生成的任务，可以自行删除
        project.getTasks().register("greeting", task -> {
            task.doLast(s -> System.out.println("Hello from plugin 'com.bamboo.plugin'"));
        });

        // 通过 project 的 extensions 创建自己开发插件的配置参数
        MyAndroidInfo myAndroidInfo = project.getExtensions().create("myAndroidInfo", MyAndroidInfo.class);

        // 接下来创建我们自己的任务，这个任务可以读取 build.gradle 中配置的自定义参数，如下：
        project.task("myAndroidTask", task -> {
            // 为了方便找到我们插件的任务，给添加分组
            task.setGroup("fireantzhang");

            task.doLast(action -> {
                System.out.println("自定义插件中执行任务：myAndroidTask，获取到的参数为：" + myAndroidInfo.toString());
            });
        });
    }
}
