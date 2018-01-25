# ClickTracker
Android 动态打点，无需在每个按钮的onClick事件中添加逻辑代码，有效的降低耦合，使用场景：大数据统计用户操作行为

## **添加依赖** ##

在工程的build.gradle中添加:

	allprojects {
			repositories {
				...
				maven { url 'https://jitpack.io' }
			}
		}

在module中添加:

	dependencies {
	        compile 'com.github.chuanshen:ClickTracker:v1.0'
	}

> ### **使用** ###

1. 新增一个类（如：MyClickTracker） 继承 ClickTracker并重写clickTracker(View view, String EventId), 以后所有的事件逻辑统一在clickTracker方法中完成
2. Activity继承TrackerAppCompatActivity 或 TrackerActivity 或 TrackerFragmentActivity并重写getClickTracker(), 在getClickTracker方法中返回刚刚新建的MyClickTracker类
3. 定义一个枚举（如：EventEnum）通过@DataId注解方式绑定viewID 和 EventId

整个动态打点过程完成


> ### **备注** ###

如果在ACTIVITY中有使用FRAGMENT，无须再FRAGMENT中添加任何代码

参考sample代码,并提供ClickTracker-sample.apk