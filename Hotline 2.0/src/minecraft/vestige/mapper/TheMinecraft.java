package vestige.mapper;

import com.craftrise.client.cf;
import cr.launcher.BlockPos;
import cr.launcher.IChatComponent;
import cr.launcher.main.a;
import vestige.Vestige;
import cr.launcher.Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TheMinecraft {
   
    public static com.craftrise.client.dG getCurrentScreen() {
        return Config.getMinecraft().bw;
    }

    public static int getScreenWidth() {
        int health = 31;

        try {
            Class<?> entityLivingBaseClazz = Class.forName("com.craftrise.client.y");

            for (Method m : entityLivingBaseClazz.getDeclaredMethods()) {
                if (m.getName().equals("a") && m.getParameterCount() == 0 && m.getReturnType().equals(int.class)) {
                    Object result = m.invoke(new com.craftrise.client.y(Config.getMinecraft()) );
                    health = (int)result;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            Config.warn(e.getMessage());
        }

        return health;
    }
    public static com.craftrise.client.g8 getRenderManager() {
        Object result = null;

        try {
            for (Method m : TheMinecraft.GetMinecraft().getClass().getDeclaredMethods()) {
                if(m.getName().equals("a") && m.getReturnType().toString().contains("com.craftrise.client.g8")) {

                    m.setAccessible(true);

                    result = m.invoke(GetMinecraft());
                }
            }
        } catch (Exception e) {
            TheMinecraft.addChatMessage(e.toString());
        }

        return (com.craftrise.client.g8) result;
    }
    public static com.craftrise.client.fa GetPlayer(){
        return a.q;
    }
    public static com.craftrise.client.S GetMinecraft(){
        return Config.getMinecraft();
    }
    public static com.craftrise.client.cf GetWorld(){
        return GetMinecraft().bu;
    }
    public static com.craftrise.client.ez GetPlayerControllerMp(){
        return GetMinecraft().b;
    }
    public static com.craftrise.client.d0 GetFontRendererObj(){
        return GetMinecraft().j;
    }
    public static com.craftrise.client.dt InGameGui(){
        return GetMinecraft().K;
    }
    public static void SetIngameGui(com.craftrise.client.dt gui) {
        TheMinecraft.GetMinecraft().K = gui;
    }
    public static int getDebugFPS() {
        int fps = 31;

        try {
            Class<?> MinecraftClazz = Class.forName("com.craftrise.client.S");
            for (Method m : MinecraftClazz.getDeclaredMethods()) {
                if (m.getName().equals("b") && m.getParameterCount() == 0 && m.getReturnType().equals(int.class)) {
                    Object result = m.invoke(new com.craftrise.client.y(Config.getMinecraft()) );
                    fps = (int)result;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
        }

        return fps;
    }
    public static void addChatMessage(String message) {
        try {
            message = String.format("�7[�gHotline�7]�7 %s" , message);
            Class EntityPlayerSpClazz = Class.forName("com.craftrise.client.fa");

            Class<?>[] paramTypes = {IChatComponent.class, long.class};

            Method adddMessage = EntityPlayerSpClazz.getDeclaredMethod("a", paramTypes);
            Method adddMessage2 = EntityPlayerSpClazz.getDeclaredMethod("a", paramTypes);

            Class<?>[] paramTypesChatCompoent = {IChatComponent.class, long.class};

            Class IChatComponentClazz = Class.forName("cr.launcher.ChatComponentText");
            Constructor<?> stringChatText = IChatComponentClazz.getConstructor(String.class);
            Object chatComponent = stringChatText.newInstance(message);

            adddMessage.invoke((Object) GetPlayer(), chatComponent, Vestige.idk);
        } catch (Exception e) {
            Config.warn("Exception: " + e.getMessage());
        }
    }
    public static void  drawStrHook(com.craftrise.client.d0 hook) {
        TheMinecraft.GetMinecraft().j = hook;
    }
    public static String getEntityDisplayName(com.craftrise.m9 entity) {
        String res = "null";
        try {
            Class EntityClazz = Class.forName("com.craftrise.m9");

            for (Method m : EntityClazz.getDeclaredMethods()) {
                if(m.getReturnType().toString().contains("cr.launcher.IChatComponent")) {
                    m.setAccessible(true);
                    IChatComponent amcik = (IChatComponent) m.invoke(entity);

                    res = amcik.getFormattedText();
                }
            }
        } catch (Exception e) {
            TheMinecraft.addChatMessage(e.toString() + " yaa");
        }

        return res;
    }
    public static int getScaledWidth(com.craftrise.client.y ScaledResulation) {
        try {
            Class<?> resulationClass = ScaledResulation.getClass();
            Field getscaledwidthfield = resulationClass.getDeclaredField("d");
            getscaledwidthfield.setAccessible(true); // Eri�im izinlerini ayarla
            return (int) getscaledwidthfield.get(ScaledResulation);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Hata durumunda hatay� yazd�r
            return -1; // veya ba�ka bir hata durumu de�eri d�nd�r
        }
    }
    public static int getScaledHeight(com.craftrise.client.y ScaledResulation) {
        try {
            Class<?> resulationClass = ScaledResulation.getClass();
            Field getscaledwidthfield = resulationClass.getDeclaredField("b");
            getscaledwidthfield.setAccessible(true); // Eri�im izinlerini ayarla
            return (int) getscaledwidthfield.get(ScaledResulation);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // Hata durumunda hatay� yazd�r
            return -1; // veya ba�ka bir hata durumu de�eri d�nd�r
        }
    }
}
