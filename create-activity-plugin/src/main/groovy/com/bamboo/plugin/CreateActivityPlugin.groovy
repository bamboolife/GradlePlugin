 package com.bamboo.plugin

 import com.bamboo.plugin.extension.CreateActivityExtension
 import com.bamboo.plugin.task.CreateActivityTask
 import org.gradle.api.Plugin
 import org.gradle.api.Project

 class CreateActivityPlugin implements Plugin<Project>{

     @Override
     void apply(Project project) {
         def extension = project.extensions.create("activitySetting",new  CreateActivityExtension())
         println(extension.toString())
         project.task("createActivity", type: new CreateActivityTask()) {
             group "createActivity"
         }

     }
 }