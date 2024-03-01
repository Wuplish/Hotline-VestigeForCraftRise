package vestige.module.impl.movement;
import vestige.module.Module;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.ModeSetting;
import vestige.event.Event;
import vestige.event.impl.EventMotionUpdate;
import vestige.mapper.ThePlayer;
import vestige.module.Category;
import java.lang.reflect.Method;

import com.craftrise.dR;
import com.craftrise.lE;

import cr.launcher.main.a;
public class Speed extends Module{
	private final ModeSetting mode = new ModeSetting("Mode", "Watchdog","AccYPort", "Watchdog");

    private float speed;
    private int stage;
	public Speed() {
		super("Speed", Category.MOVEMENT);
        this.addSettings(mode);
	}

	public void onEvent(Event e) {
		if(e instanceof EventMotionUpdate) {
			
			switch (mode.getMode()) {
        	case "AccYPort":
                if (ThePlayer.isMoving()) {
                    if (ThePlayer.onGround()) {
                    	ThePlayer.jump();
                        ThePlayer.SetMotionY(0.3425);
                        ThePlayer.SetMotionX(1.5893);
                        ThePlayer.SetMotionZ(1.5893);
                    } else ThePlayer.SetMotionY(-0.19);
                }
			}
		}
	}
}
