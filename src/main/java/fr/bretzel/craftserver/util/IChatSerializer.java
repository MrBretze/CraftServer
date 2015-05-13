package fr.bretzel.craftserver.util;

/**
 * Created by Loic on 08/05/2015.
 */
public class IChatSerializer {

    private Object jselement = null;

    public IChatSerializer(Object o) {
        this.jselement = o;
    }

    public Object getJSonElement() {
        return jselement;
    }
}
