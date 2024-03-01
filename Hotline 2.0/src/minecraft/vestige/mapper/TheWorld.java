package vestige.mapper;
import com.craftrise.dR;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.Math.*;
import java.util.List;

public class TheWorld {

    public static List<com.craftrise.m9> loadedEntityList() {
        return TheMinecraft.GetWorld().g;
    }
    public static List<com.craftrise.mg> playerEntities() {
        return TheMinecraft.GetWorld().H;
    }

}

