package io.funraise.swagger.util;

import java.util.Comparator;
import org.apache.commons.lang3.ClassUtils;

public class ParameterTypeComparator implements Comparator<Class<?>> {

    @Override
    public int compare(Class<?> o1, Class<?> o2) {
        if (o1.equals(o2)) {
            return 0;
        }

        if (ClassUtils.primitiveToWrapper(o1).equals(ClassUtils.primitiveToWrapper(o2))) {
            return 0;
        }

        return 1;
    }
}
