package club.fullstack.argv;

/**
 * @Author：Cheney
 * @Date：2023/4/6 11:32
 */
public class AutoType {

    public static <T> T autoStrChange(String v, Class<T> clazz){

        if ( clazz.isAssignableFrom(java.lang.String.class) ) {
            return (T) v;
        }

        if ( clazz.isAssignableFrom(java.lang.Integer.class) ) {
            return (T) Integer.valueOf( v );
        }
        if (clazz.isAssignableFrom(java.lang.Float.class)) {
            return (T) Float.valueOf( v );
        }
        if ( clazz.isAssignableFrom(java.lang.Double.class)) {
            return (T) Double.valueOf( v );
        }
        throw new IllegalArgumentException("类型自动转换时出错");
    }

    public static <T> T autoStrChange(String v, Object def){
        if ( def instanceof String ){
            return (T) v;
        }
        if ( def instanceof java.lang.Integer) {
            return (T) Integer.valueOf( v );
        }
        if ( def instanceof java.lang.Float) {
            return (T) Float.valueOf( v );
        }
        if ( def instanceof java.lang.Double) {
            return (T) Double.valueOf( v );
        }
        throw new IllegalArgumentException("类型自动转换时出错");
    }
}
