# 仿钉钉日历周历组件 支持左右滑动切换月份，picker快速选择月份，每个月有多少周，今天在本月的第几周，支持在APP、小程序、H5运行
***
##### 组件属性说明：
|属性名|类型|默认值|说明|
|:--:|:--:|:--:|:--:|
|duration|Number|300|动画时长|
|cellHeight|Number|75|日历每一列的高度，单位为rpx|
|dateActiveColor|String|#FE6601|日期选中颜色|
|defaultSelectedDate|String Null|null|选中日期，默认为当天|
***
> 项目源码注释清晰且不难理解，可自行根据实际需求进行二次开发；
> 因为单独计算了每个月有多少周，数据量很小，不进行缓存数据以便节省性能，滑出当前月份直接进行计算，使用轮播无限循环的效果，用户体验效果极佳。
> 该组件已经进行深度测试，请放心使用
***
##### 开发原理
主要通过获取`每周的周一`的日期，通过这个`每周的周一`的日期从而获取到`每周的周日`的日期，此时我们已经有了`每周的周一`的日期和`每周的周日`的日期；
根据【钉钉考勤】的周历可以看出【钉钉考勤】的每个月的第一周是从1日所在的周开始计算的，也就是说“掐开头不掐末尾”，所以可以通过每月的最后一周的周日来计算出`每个月的周数`；
根据当前日期可以计算出`当前所在周的周日`是几号，然后根据`当前所在周的周日`的日期来推算出`今天是在当前月的第几周`；
***
##### 关键语法介绍
Date.setFullYear(year,month,day)
|属性名|参数值|返回值类型|返回值描述|说明|
|:--:|:--:|:--:|:--:|:--:|
|Date.setFullYear(year,month,day)|year:必需。表示年份的四位整数。用本地时间表示。month:可选。表示月份的数值。用本地时间表示。介于 0 到 11 之间的整数值，表示从一月到十二月，-1 为去年的最后一个月，12 为明年的第一个月，13 为明年的第二个月，day:可选。表示月中某一天的数值。用本地时间表示。介于 1 ~ 31 之间：0 为上个月最后一天,，-1 为上个月最后一天之前的天数；如果当月有31天：32 为下个月的第一天，如果当月有30天：32 为下一个月的第二天|Number|1970年1月1日午夜至调整过日期的毫秒。|setFullYear() 方法用于设置年份。这个方法可用于设置月份及月份中的一天。|
|Date.getDate()|无|Number|dateObject 所指的月份中的某一天，使用本地时间。返回值是 1 ~ 31 之间的一个整数。|getDate() 方法可返回月份的某一天。|
|Date.getFullYear()|无|Number|表示年份的 4 位数字。|getFullYear() 方法可返回一个表示年份的 4 位数字。|
|Date.getMonth()|无|Number|返回值是 0（一月） 到 11（十二月） 之间的一个整数。|getMonth() 方法可返回表示月份的数字。返回值是 0（一月） 到 11（十二月） 之间的一个整数。|
|Date.getDay()|无|Number|返回值是 0（周日） 到 6（周六） 之间的一个整数。|getDay() 方法可返回一周（0~6）的某一天的数字。（注意： 星期天为 0, 星期一为 1, 以此类推。）|
|Date.getHours()|无|Number|返回值是 0 （午夜） 到 23 （晚上 11 点）之间的一个整数。|getHours() 方法可返回时间的小时字段。|
|Date.getMinutes()|无|Number|date Object 的分钟字段，以本地时间显示。返回值是 0 ~ 59 之间的一个整数。|getMinutes() 方法可返回时间的分钟字段。|

>>> Tips：如果使用中有问题或者发现问题可以随时联系我，如果需要定制功能也可以联系我哦~~~
>>> 如果此组件帮助到你，请给我一个五星好评吧~~~



















