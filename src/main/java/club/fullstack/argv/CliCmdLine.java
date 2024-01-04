package club.fullstack.argv;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 特色命令行交互格式
 * 有且只有一个命令码
 * 可以有多个参数
 * 可以有多个标记，标记分无参/单参/多参
 */
public class CliCmdLine {

    // 命令
    private CmdLine cmdLine;

    // 标记
    private Set<String> flags;

    // 参数
    private Map<String, Object> params;

    CliCmdLine(CmdLine cmdLine, Set<String> flags, Map<String, String> params, List<String>position) {
        this.cmdLine = cmdLine;
        this.flags = new HashSet<>();
        this.params = new HashMap<>();

        int i = 0;
        for ( String pname : cmdLine.getParams().keySet() ) {
            CliParam param = cmdLine.getParams().get( pname );
            if ( param.isFlag() ) {
                if ( param.isNeedValue() ) {
                    if ( params.keySet().contains( pname ) ) {
                        this.params.put( pname , params.get( pname ));
                    } else  {
                        if ( ! param.isOptional() ) {
                            throw new IllegalArgumentException("命令解析错误，缺少必填标记参数。" + Config.crlf + cmdLine);
                        }
                    }
                } else {
                    if ( flags.contains( pname ) ) {
                        this.flags.add( pname );
                    } else  {
                        if ( ! param.isOptional() ) {
                            throw new IllegalArgumentException("命令解析错误，缺少必填标记。" + Config.crlf + cmdLine);
                        }
                    }
                }

            } else {

                if (  i < position.size() ) {
                    this.params.put( pname , position.get( i++ ) );
                } else  {
                    if ( ! param.isOptional() ) {
                        throw new IllegalArgumentException("命令解析错误，缺少必填参数。" + Config.crlf + cmdLine);
                    }
                }
            }
        }
    }

    public String getCmd() {
        return this.cmdLine.getCmd();
    }

    public boolean hasParam(String name){
        return params.containsKey( name );
    }

    public boolean hasFlag(String name){
        return flags.contains( name );
    }

    public String getParamValue(String name){
        return getParamValue(name, null);
    }

    public <T> T getParamValue(Class<T> tClass, String name){

        String v = getParamValue(name);
        return AutoType.autoStrChange( v, tClass );
    }

    public String getParamValue(String name, String re, String error){
        String str = getParamValue(name);
        if ( null == str ) {
            throw new IllegalArgumentException(error);
        }

        if ( Pattern.matches( re, str) ) {
            return str;
        } else {
            throw new IllegalArgumentException(error);
        }
    }

    public <T> T getParamValue(String name, T defValue){
        if ( name.startsWith("--") ) {
            name = name.substring(2);
        }

        String v =  (String) params.get( name );
        if( null == v ) {
            return defValue;
        } else {
            if ( null != defValue ) {
                return AutoType.autoStrChange( v ,defValue );
            }
            return (T) v;
        }
//        if ( name.startsWith("---") ) {
//            if ( null == v ) {
//                return defValue;
//            }else {
//                return (T) v;
//            }
//        } else if ( name.startsWith("--") ) {
//            if ( null == v ) {
//                return defValue;
//            }else {
//                return (T) v;
//            }
//        } else if ( name.startsWith("-") ) {
//            if ( null == v ) {
//                return (T) Boolean.valueOf(false);
//            }else {
//                return (T) Boolean.valueOf(true);
//            }
//        } else {
//            throw new IllegalArgumentException();
//        }
    }
}
