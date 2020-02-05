package com.bamboo.plugin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class SecondPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        System.out.println("====================");
        System.out.println("Hello, SecondPlugin!");
        System.out.println("====================");
    }
}
