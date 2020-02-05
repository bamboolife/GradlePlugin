package com.bamboo.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project


class BambooPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("========================")
        System.out.println("hello gradle plugin!");
        System.out.println("========================")

        project.extensions.create("bamboo",BOExtension)//可以通过bamboo配置参数

        if(project.plugins.hasPlugin(AppPlugin)){
            def android=project.extensions.findByType(AppExtension)
            android.registerTransform(new BambooTransform())
            project.gradle.addListener(new TaskListener())
        }


    }


}