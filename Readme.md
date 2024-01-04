# argv
Cli 命令解析工具

## 使用示例
1. 最简使用
```shell
public static void main(String[] args) {
    
    // 注册命令
    CliCmdParser parser = new CliCmdParser();
    parser.addCmdLine("version", "查看版本");
    parser.addCmdLine("help", "查看帮助");
    
    // 解析用户输入命令
    CliCmdLine cmd = parser.parse(args);
    
    // 执行命令
    if ("help".equals(cmd.getCmd())) {
        // 辅助打印命令提示符
        System.out.println(parser.help());
    } else {
        // 其他命令执行需自行实现
        System.out.println(cmd);
    }
}
```

2. 添加一个待参数的命令
```shell
parser.addCmdLine("config <key> [value]", "查询/设置配置");
```

```
[] 表示可选参数
<> 表示必选参数
```


在程序中获取参数
```shell
String key = line.getParamValue("key");
String value = line.getParamValue("value");
```

获取程序时附带默认值
```shell
String value = line.getParamValue("value", "xxx");
```


3. 增加一个参数标记
```shell
parser.addCmdLine("tcp <--ip> <--port> <--headlen> [--timeout] <filepath>", "tcp 报文发送");
```

```
[] 表示可选参数
<> 表示必选参数
- 表示标记，即无值属性
-- 表示属性

```

- 必选参数必须在可选参数前面



# TODO
- [x] getParamValue 基本类型自动转换

# 暂时无法实现
- [x] 参数说明，因为参数中目前不能有空格。
- [x] 参数默认值，因为参数中目前不能有空格。临时方法，在代码中实现。


## 使用
### Maven
```
<dependency>
  <groupId>club.fullstack</groupId>
  <version>1.0-SNAPSHOT</version>
  <artifactId>argv</artifactId>
</dependency>
```

