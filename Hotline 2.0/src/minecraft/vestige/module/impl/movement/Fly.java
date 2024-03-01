package vestige.module.impl.movement;
import vestige.module.Module;
import vestige.event.Event;
import vestige.event.impl.EventMotionUpdate;
import vestige.event.impl.EventReceivePacket;
import vestige.event.impl.EventRender;
import vestige.event.impl.EventSendPacket;
import vestige.event.impl.EventUpdate;
import vestige.event.impl.MovementEvent;
import vestige.mapper.ThePlayer;
import vestige.module.Category;
import vestige.setting.impl.ModeSetting;
import vestige.setting.impl.NumberSetting;
import vestige.util.misc.MathUtils;
import com.craftrise.dR;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lwjgl.input.Keyboard;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import cr.launcher.BlockPos;
import cr.launcher.Config;
import cr.launcher.Config;
import cr.launcher.eb;
import cr.launcher.main.a;
public class Fly extends Module {
	private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Hypixel", "Hypixel dmg", "Redesky", "Verus", "Minemora", "MMC", "Hycraft");
	public static final float PI2 = roundToFloat((Math.PI * 2D));
	private ModeSetting vanillaMode = new ModeSetting("Vanilla Mode", "Motion", "Motion", "Creative");
	private NumberSetting vanillaSpeed = new NumberSetting("Vanilla Speed", 1, 0.2, 9, 0.2, this);
	private NumberSetting vanillaVerticalSpeed = new NumberSetting("Vanilla Vertical Speed", 1, 0.2, 9, 0.2, this);
	
	private NumberSetting timer = new NumberSetting("Timer", 1, 0.1, 4, 0.1, this);
	
	private NumberSetting hypixelPhaseMotion = new NumberSetting("Hypixel Phase Motion", -0.2, -0.5, -0.02, 0.02, this);
	
	private ModeSetting verusMode = new ModeSetting("Verus mode", "Latest", "Latest", "Latest dmg");
	private NumberSetting verusSpeed = new NumberSetting("Verus speed", 2, 0.5, 9, 0.5, this);
	private com.craftrise.gg niga;
	private BlockPos originalPos;
	private double x, y, z;
	
	private ArrayList<BlockPos> blocks = new ArrayList<>();
	
	private double speed;
	private int counter, ticks;
	private boolean lagbacked;
	private boolean started;
	public Fly() {
		super("Fly", Category.MOVEMENT);
		this.addSettings(mode, vanillaMode, vanillaSpeed, vanillaVerticalSpeed, verusMode, verusSpeed, timer, hypixelPhaseMotion);
	}
    

	public void onEnable() {
		originalPos = new BlockPos(x = ThePlayer.GetPosX(), y = ThePlayer.GetPosY(), z = ThePlayer.GetPosZ());
		ticks = counter = 0;
		lagbacked = false;
		
		started = false;
		
	}
   
    public static float roundToFloat(double d)
    {
        return (float)((double)Math.round(d * 1.0E8D) / 1.0E8D);
    }
    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        a.q.bh = new dR(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        a.q.bf = new dR(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    public static double getBaseMoveSpeed() {
        double baseSpeed = a.q.S.b(5L) * 2.873;
        return baseSpeed;
    }

    public static void setSpeed(double moveSpeed) {
        setSpeed(moveSpeed, a.q.bL, a.q.l.c.a(5L), a.q.l.b.a(5L));
    }
	public void onDisable() {
		if(mode.is("Vanilla")) {
			ThePlayer.SetMotionX(0.1);
			ThePlayer.SetMotionY(0.1);
			ThePlayer.SetMotionZ(0.1);
		}
		if(mode.is("Hypixel")) {
			
		}
		
		
		if(!blocks.isEmpty()) {
			for(BlockPos pos : blocks) {
				//mc.theWorld.setBlockToAir(pos);	 bilmiyom hallet @negy
			}
			blocks.clear();
		}
	}
	public void onEvent(Event e) {
		if(e instanceof EventRender) {
			vanillaMode.setShowed(mode.is("Vanilla"));
			vanillaSpeed.setShowed(mode.is("Vanilla") && vanillaMode.is("Motion"));
			vanillaVerticalSpeed.setShowed(mode.is("Vanilla") && vanillaMode.is("Motion"));
			timer.setShowed(mode.is("Hypixel"));
			hypixelPhaseMotion.setShowed(mode.is("Hypixel"));
		
		}
		
		if(!isEnabled()) {
			return;
		}
		
		switch(mode.getMode()) {
		case "Vanilla":
			Vanilla(e);
			break;
		case "Hypixel":
			Hypixel(e);
			break;
		}
	}
    public static void SetisFlying(boolean value) {
        try {
            Class<?> thePlayerClass = com.craftrise.mg.class;
            Field sField = thePlayerClass.getDeclaredField("S");
            sField.setAccessible(true);
            Object sObject = sField.get(a.q);
            Field hField = sObject.getClass().getDeclaredField("h");
            hField.setAccessible(true);
            hField.set(sObject, new eb(value, cr.launcher.main.a.m));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	private void Vanilla(Event e) {
		if(e instanceof MovementEvent) {

			MovementEvent event = (MovementEvent) e;
			if(vanillaMode.is("Motion")) {
				ThePlayer.Strafe(vanillaSpeed.getValue());;
				event.setMotionY(vanillaVerticalSpeed.getValue());
				} else {
					event.setMotionY(0);
				}
				ThePlayer.SetMotionY(0);
			} else if(vanillaMode.is("Creative")) {
				SetisFlying(true);
			}
		}
	
	private void Hypixel(Event event) {
		
		if(event instanceof EventUpdate) {
			if(lagbacked) {
				//mc.thePlayer.onGround = true;
			}

		} else if(event instanceof MovementEvent) {
			MovementEvent e = (MovementEvent) event;
			if(lagbacked) {
				ThePlayer.SetMotionY(0);
			} else {
				setSpeed(0);
				if(ThePlayer.onGround() && ticks == 0) {
					ThePlayer.SetMotionY(0.0625);
					//mc.theWorld.setBlockToAir(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ));
				}
			}
		} else if(event instanceof EventMotionUpdate) {
			EventMotionUpdate e = (EventMotionUpdate) event;
			if(ticks > 1 && ticks < 6) {
				e.setY(ThePlayer.GetPosY() - 0.08);
			}
			ticks++;
		} else if(event instanceof EventReceivePacket) {
			EventReceivePacket e = (EventReceivePacket) event;
		}
	}
}
