package com.bamboo.plugin.template;



class ActivityNodeTemplate {
    def template = '''
        <activity android:name=".${packageName}.${activityName}Activity"/>
'''
}
