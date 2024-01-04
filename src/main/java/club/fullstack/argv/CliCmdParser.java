package club.fullstack.argv;

import java.math.BigDecimal;
import java.util.*;

public class CliCmdParser {


    private final LinkedHashMap<String, CmdLine> cmdLines = new LinkedHashMap<String, CmdLine>();
    private final Map<String, IFunction>funcs = new HashMap<>();

    public void addCmdLine(String cmdLine, String info, IFunction func ) {
        CmdLine cl = addCmdLine( cmdLine, info );
        funcs.put( cl.getCmd(), func );
    }

    public CmdLine addCmdLine(String cmdLine, String info) {

        CmdLine cl = CmdLine.parse( cmdLine , info);

        if ( cmdLines.containsKey( cl.getCmd() ) ) {
            throw new IllegalArgumentException("重复注册命令 " + cl.getCmd() );
        }

        cmdLines.put( cl.getCmd(), cl );
        return cl;
    }

    public boolean canEval( CliCmdLine cmdLine ){
        return funcs.containsKey( cmdLine.getCmd() );
    }

    public void eval( CliCmdLine cmdLine ){
        IFunction func = funcs.get( cmdLine.getCmd() );
        if ( null == func ) {
            throw new UnsupportedOperationException();
        }

        func.eval( cmdLine );

    }

    public String help(){
        StringBuilder bs = new StringBuilder(Config.crlf);
        for (  String cmd : cmdLines.keySet() ) {
            bs.append("\t")
                    .append( cmdLines.get( cmd ).getLine() )
                    .append("\t")
                    .append( cmdLines.get( cmd ).getInfo() )
                    .append( Config.crlf );
        }
        return bs.toString();
    }

    public CliCmdLine parse(String[] argv) {
        if ( null == argv || argv.length < 1 ) {
            return null;
        }

        if ( argv[0].startsWith("-") ) {
            throw new IllegalArgumentException("入参命令格式错误 : 没有入参 cmd");
        }

        if ( ! cmdLines.containsKey( argv[0] ) ) {
            return null;
        }

        // 标记
        Set<String>flags = new HashSet<>();

        Map<String, String> params = new HashMap<>();

        // 参数
        List<String>position = new ArrayList<String>();

        for (int i = 1; i < argv.length; i++ ) {
            if ( argv[i].startsWith("--") ) {
                if ( i + 1 == argv.length || argv[ i + 1 ].startsWith("-") ) {
                    throw new IllegalArgumentException("flag 格式错误" + argv[i]);
                }
                params.put( argv[i].substring(2), argv[++i] );

            } else if ( argv[i].startsWith("-") ) {
                flags.add( argv[i].substring(1));
            } else {
                position.add( argv[i] );
            }
        }

        CmdLine line = cmdLines.get( argv[0] );

        return new CliCmdLine( line,  flags, params, position);

    }


}
