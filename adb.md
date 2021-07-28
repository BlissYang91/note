

adb connect 127.0.0.1:7555
adb   devices
adb kill-server 杀死adb进程
adb start-server 开启adb进程

夜神模拟器 端口号 ：62001
海马玩模拟器 端口号：26944
网易mumu模拟器端口号：7555
天天模拟器 端口号：6555

1、adb kill-server来杀死adb进程，然后再使用adb start-server命令来开启； 
2、检测5037端口（adb.exe默认端口）是否被占用，关闭除adb.exe以外占用了5037端口的进程，重新开启adb服务： 
2.1、adb nodaemon server：检测5037端口是否被占用； 
2.2、netstat -ano | findstr “5037”：查看是什么进程占用了5037端口； 
2.3、tasklist | findstr “21152”：查看这个进程是由哪个程序创建的（21152就是占用5037端口的一个进程的PID）； 
2.4、taskkill /f /pid 21152：将进程关掉； 
2.5、adb devices：显示当前连接设备。 
ps -A | grep adb 列出adb名字的进程

知道一个App的包名，想获取这个App的进程ID
adb shell "ps | grep com.test.demo" 命令ps是列出所有的进程，而后面的竖线是管道的意思，即把前面ps命令的输出作为后面命令的输入，而后面的grep命令是过滤操作.

推送文件结束之后，看一下是否推送成功:
adb shell "cd/mnt/sdcard; ls -l | grep wav" 先进入SD卡的根目录，然后列出所有的wav文件，并且有修改时间的信息显示

查看一个App的内存占用情况，可以执行如下命令：
adb shell dumpsys meminfo com.test.demo


查看占用端口1992、8716的进程（10836是多开控制台）
Netstat -ano | findstr 1992
Netstat -ano | findstr 8716
用tasklist查看进程pid（当然任务管理器是更好用的）
tasklist > c:\log.txt
以天天模拟器为例 ，已经有2台模拟器，只成功链接1台
netstat -aon | findstr "5037"
1）获取模拟器所有包名
adb shell pm list packages
2）获取模拟器所有包名并且包括APK路径
adb shell pm list packages -f
3）获取包名对应的APK路径
adb shell pm path packageName
4）清理应用数据
adb shell pm clear packageName
6、启动应用
adb shell am start -n 包名/Activity类名
例子：启动应用宝：
adb shell am start -n com.tencent.android.qqdownloader/com.tencent.assistant.activity.SplashActivity

指定模拟器启动动应用宝：
adb -s 127.0.0.1:5555 shell am start -n com.tencent.android.qqdownloader/com.tencent.assistant.activity.SplashActivity


7、关闭应用
adb shell am force-stop 包名

8、模拟输入
adb shell input text 字符串(不支持中文)

9、模拟按键
adb shell input keyevent 键值

10、模拟鼠标点击
adb shell input tap X Y

11、模拟鼠标滑动
adb shell input swipe X1 Y1 X2 Y2

12、截屏
adb shell screencap -p /sdcard/screencap.png

13、设置手机IMEI/IMSI/手机号/SIM卡序列号
adb shell setprop persist.nox.modem.imei 352462010682470
adb shell setprop persist.nox.modem.imsi 460000000000000
adb shell setprop persist.nox.modem.phonumber 15605569000
adb shell setprop persist.nox.modem.serial 89860000000000000000

14、nox_adb shell进去然后执行下面的命令修改经纬度
setprop persist.nox.gps.latitude xxx
setprop persist.nox.gps.longitude xxx

15、修改mac地址
setprop persist.nox.wifimac xxx                  修改mac地址
setprop persist.nox.modem.phonumber 138111111111         手机号，生成一个随机11位数字
setprop persist.nox.model ABC001                  手机型号，英文加数字随机
setprop persist.nox.manufacturer XiaoMi               手机制造商英文随机
setprop persist.nox.brand Mi                    手机品牌英文随机



16、adb logcat
打印log信息
指令 说明 备注
adb logcat 打印log /
adb logcat -c 清除手机的log buffer 有些手机权限控制, 不支持.
adb logcat -b <buffer> 打印指定buffer的log信息 buffer有: main(主log区,默认), events(事件相关的log), radio(射频, telephony相关的log)
adb logcat -v <format> 格式化输出log 常用的用adb logcat -v time显示时间
adb logcat -f <filename> 输出log到指定文件 
adb logcat -s "AudioEncoder | AudioDecoder"  过滤标签日志

- 打印TAG下全部日志
```
adb shell logcat -s TAG
```
- 打印过滤指定字段日志

```
adb shell logcat  | grep recvTurnInfo
```
> 如果grep报错，切换到shell环境下分开执行
> 先执行adb shell，在执行logcat | grep

```
adb shell
root@sabresd_6dq:/ # logcat | grep recvTurnInfo
```

- 下载全部日志

```
adb logcat > D:/log_navi.txt
```
- 下载指定字段日志

```
adb shell logcat -s PresentationsService > D:/locat_navi.txt
```
- adb 拉取文件
拉取指定路径下所有文件
```
  adb pull storage/emulated/0/. D:/file
```
拉取单个文件
```
  adb pull storage/emulated/0/filename.txt D:/file
```


17、adb start/kill-server
启动/杀死adb简介中提到的Server端进程。
由于adb并不稳定, 有时候莫名的问题掉线时, 可以先kill-server, 然后start-server来确保Server进程启动。往往可以解决问题。

18、adb shell am
am即activity manager.
该命令用来执行一些系统动作, 例如启动指定activity, 结束进程, 发送广播, 更改屏幕属性等. 调试利器.
指令 说明 备注
start <Intent> 根据intent指向启动Activity Intent可以是显示的指向activity, 也可以是ACTION方式, 并且可以添加flag, data等参数信息.
startservice <Intent> 启动Service 可以添加flag, data等参数信息.
broadcast <Intent> 发送广播 可以添加flag, data等参数信息.
monitor 启动一个Crash和ANR的监听器 如有Crash或ANR会在控制台输出相关信息.
force-stop <Package> 强制停止该包相关的一切 传入package name.
kill <Package> 杀死该包相关的所有进程 传入package name.
kill-all 杀死所有后台进程 
display-size WxH 改变显示的分辨率 例如adb shell am display-size 1280x720, 手机可能不支持.
display-density <dpi> 改变显示的density 例如adb shell am display-density 320, 手机可能不支持.

19、adb shell dumpsys
强大的dump工具, 可以输出很多系统信息. 例如window, activity, task/back stack信息, wifi信息等.
常用dumpsys:
指令 说明 备注 细分参数
activity 输出app组件相关信息 还可以用细分参数获得单项内容, 下同. 例如adb shell dumpsys activity activities来获取activity task/back stack信息. activites, service, providers, intents, broadcasts, processes
alarm 输出当前系统的alarm信息 / /
cpuinfo 输出当前的CPU使用情况 / /
diskstats 输出当前的磁盘使用状态 / /
batterystats 电池使用信息 / /
package package相关信息, 相当于pm功能的集合 输出诸如libs, features, packages等信息 /
meminfo 输出每个App的内存使用和系统内存状态 可以指定包名, 例如adb shell dumpsys meminfo com.anly.githubapp /
window 输出当前窗口相关信息 / policy, animator, tokens, windows
20.复制  -copy:<nox_id> _from:<nox_id>例：Nox.exe -copy:Nox_1 -from:nox
Nox_1是新增的模拟器，复制自nox

21.删除  -remove:<nox_id>
例：Nox.exe -remove:Nox_2
删除模拟器Nox_2

22.备份  -backup:<nox_id> -file:filepath
例：Nox.exe -backup:Nox_1 -file:C:\Users\xxxx\Desktop
备份Nox_1到桌面

23.还原  -restore:<nox_id> -file:filepath
例：Nox.exe -restore:Nox_1 -file:C:\xxxx\lihc\Desktop\backup20171030174008.vmdk
[C:\Users\xxxx\Desktop\backup20171030174008.vmdk]文件还原到Nox_1