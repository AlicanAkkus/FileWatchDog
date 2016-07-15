# FileWatchDog
FileWatchDog continuously monitors your configuration files. WatchDog can be create warn for you when configuration files is updated. 

Also you can pass check delay to watch dog. The default delay between every file modification check, set to 60 seconds.

You can use very easy as following;

```java
String fileName = "src/WatchDog.properties";
ConfigurationWatchDog watchDog = new ConfigurationWatchDog();
watchDog.initialize(fileName, 60000);// 60 second
```		
		
