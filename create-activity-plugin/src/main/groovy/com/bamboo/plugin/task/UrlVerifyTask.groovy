package com.bamboo.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

class UrlVerifyTask extends DefaultTask {
    private String url;

    @Option(option = "url", description = "Configures the URL to be verified.")
    void setUrl(String url) {
        this.url = url;
    }

    @TaskAction
    void verify() {
        getLogger().quiet("Verifying URL '{}'", url);

        // verify URL by making a HTTP call
    }
}