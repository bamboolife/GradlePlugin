 package com.bamboo.plugin.extension

 class CreateActivityExtension{
     // 例如: com.example.tsnt
     String applicationId
     // Activity的名字
     String activityName
     // Activity所属的包名, 例如:.gradle
     String packageName

     @Override
     String toString() {
         return "applicationId="+applicationId+"activityName="+activityName+"packageName="+packageName
     }
 }