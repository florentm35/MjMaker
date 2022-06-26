package fr.florent.mjmaker.injection;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import fr.florent.mjmaker.injection.annotation.Inject;
import fr.florent.mjmaker.injection.annotation.Injectable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Dependency injection manager, provide injection of class annotated with {@link fr.florent.mjmaker.injection.annotation.Injectable}
 * for field class annotated with {@link fr.florent.mjmaker.injection.annotation.Inject}.
 * <p>The injected class are rule as singleton, take care of synchronisation by your self</p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyInjectionManager {

    private static DependencyInjectionManager _instance;

    private Map<Class<?>, Object> cache = new HashMap<>();

    public static DependencyInjectionManager getInstance() {
        if (_instance == null) {
            _instance = new DependencyInjectionManager();
        }
        return _instance;
    }

    /**
     * Inject instance of field annotate {@link Inject}
     *
     * @param object The instance to inject
     */
    public void inject(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {

                Class<?> tClass = field.getType();

                Object injectableInstance = null;

                if (cache.containsKey(tClass)) {
                    injectableInstance = cache.get(tClass);
                } else if (tClass.isAnnotationPresent(Injectable.class)) {
                    injectableInstance = createInjectableInstance(tClass);
                } else {
                    throw new DependencyInjectionException("The injecte class must be annoted injectable");
                }
                field.setAccessible(true);
                try {
                    field.set(object, injectableInstance);
                } catch (IllegalAccessException e) {
                    throw new DependencyInjectionException("Can not be inject " +
                            injectableInstance.getClass().getName() + "to the field : " + field.getName() +
                            " of class " + object.getClass().getName(), e);
                }
            }
        }
    }

    /**
     * Create an instance of the injectable class and class injection for it<br>
     * The class must be need to be annoted with {@link Injectable} and provide an empty argument constructor
     *
     * @param tClass The class to instantiate
     * @return The instance
     */
    private Object createInjectableInstance(Class<?> tClass) {
        try {
            Constructor<?> constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object injectableInstance = constructor.newInstance();
            cache.put(tClass, injectableInstance);

            inject(injectableInstance);
            return injectableInstance;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new DependencyInjectionException("Can not be instanciate the class "
                    + tClass.getName() + "must be ensure they contained an empty constructor", e);
        }
    }
}

