package name.bvv.bot.attack.http;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by User on 24.07.2016.
 */
public class Storage
{
    private Map<String, Object> storage;

    public Storage()
    {
        storage = new HashMap<>(10);
    }

    public Object get(String key)
    {
        return storage.getOrDefault(key, null);
    }

    public static String[] getTokens(String keyWithType)
    {
        return keyWithType.split(Pattern.quote("@"));
    }

    public static String getKeyWithoutType(String keyWithType)
    {
        return getTokens(keyWithType)[0];
    }

    public void save(String keyWithType, String saveAs, Object object) throws WrongTypeException
    {
        String[] tokens = getTokens(keyWithType);

        switch (tokens[1]){
            case Types.STRING: {
                storage.put(saveAs, String.valueOf(object));
            } break;
            case Types.INT: {
                storage.put(saveAs, (Integer)object);
            } break;
            default: throw new WrongTypeException();
        }
    }

    public int size()
    {
        return storage.size();
    }

    public static final class Types
    {
        public static final String STRING = "String";
        public static final String INT = "Int";
    }

    public final class WrongTypeException extends Exception{}
}
