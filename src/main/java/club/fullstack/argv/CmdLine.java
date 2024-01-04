package club.fullstack.argv;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 特色命令行交互格式
 * 有且只有一个命令码
 * 可以有多个参数
 * 可以有多个标记，标记分无参/单参/多参
 * 该类只是描述，不包含值，包含值的对应类型是 CliCmdLine
 */
public class CmdLine {
    // 命令
    private String cmd;

    // 说明
    private String info;

    // 参数
    private LinkedHashMap<String, CliParam> params = new LinkedHashMap<>();

    // 完整命令
    private String line;

    /**
     * 将命令描述解释成命令格式
     * @param cmdLine
     * @param info
     * @return
     */
    public static CmdLine parse(String cmdLine, String info){

        String[]pp = cmdLine.split("\\s+");
        CmdLine cl = new CmdLine(cmdLine, pp[0], info);

        boolean canFlagOptionalMode = true;
        boolean canParamOptionalMode = true;

        for ( int i = 1 ;i < pp.length; i++ ) {
            String p = pp[i];
            boolean isOptional = true;
            boolean isFlag = false;

            if ( p.startsWith("[") & p.endsWith("]")){
                isOptional = true;
            }
            else if ( p.startsWith("<") & p.endsWith(">")){
                isOptional = false;
            }
            else {
                throw new IllegalArgumentException("不合法的命令格式 - " + cmdLine);
            }

            // 去掉两端标记
            p = p.substring( 1, p.length() -1 );
            if( p.startsWith("--") ) {
                if ( ! isOptional && ! canFlagOptionalMode ) {
                    throw new IllegalArgumentException("参数解析错误 - 必选参数必须在可选参数前面 - " + cmdLine);
                }
                if ( isOptional ) {
                    canFlagOptionalMode = false;
                }

                p = p.substring(2);
                isFlag = true;
            }
            else if( p.startsWith("-") ) {
                if ( ! isOptional && ! canFlagOptionalMode ) {
                    throw new IllegalArgumentException("参数解析错误 - 必选参数必须在可选参数前面 - " + cmdLine);
                }
                if ( isOptional ) {
                    canFlagOptionalMode = false;
                }
                // 无值
                p = p.substring(1);
                cl.addParams( p, new CliParam( p, isOptional , true) );
                continue;
            } else {
                if ( ! isOptional && ! canParamOptionalMode ) {
                    throw new IllegalArgumentException("参数解析错误 - 必选参数必须在可选参数前面 - " + cmdLine);
                }
                if ( isOptional ) {
                    canParamOptionalMode = false;
                }
            }

            // 类型分析
            String type = "String";
            if ( p.contains(":") ) {
                String[] px = p.split(":");
                p = px[0].trim();
            }

            cl.addParams( p, new CliParam( p, isOptional , isFlag, true ) );

        }

        return cl;
    }

    CmdLine(String cmdLine, String cmd, String info) {
        this.line = cmdLine;
        this.cmd = cmd;
        this.info = info;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void addParams(String name, CliParam param){
        params.put( name, param );
    }

    public LinkedHashMap<String, CliParam> getParams() {
        return params;
    }

    public void setParams(LinkedHashMap<String, CliParam> params) {
        this.params = params;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
