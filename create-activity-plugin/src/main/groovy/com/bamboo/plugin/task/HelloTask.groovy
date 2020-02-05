package com.bamboo.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class HelloTask extends DefaultTask {
    String nameOfPerson = "David"

    @TaskAction
    void hello() {
        println "Hello, $nameOfPerson !"
    }
}

