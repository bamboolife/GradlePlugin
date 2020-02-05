package com.bamboo.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApkChangeNamePlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        if (!project.android){
            throw new IllegalStateException('Must apply \'com.android.application\' or \'com.android.library\' first!')
        }

        project.android.applicationVariants.all{
            variant ->
                variant.outputs.all{
                    outputFileName = "bamboo-${variant.name}-${variant.versionName}.apk"
                }
        }

//        project.android.applicationVariants.all { variant ->
//            //if (variant.buildType.name == 'release') {
//                variant.outputs.all {
//                    def apkName = "bamboo_v${defaultConfig.versionName}_${variant.productFlavors[0].name}_${variant.name}"
//                    outputFileName = apkName + ".apk"
//                }
//           // }
       // }
    }
}