package lancet_.floating_horses;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import net.minecraft.util.Identifier;

public class FloatingHorsesConfig extends Config {
    public static FloatingHorsesConfig config = ConfigApiJava.registerAndLoadConfig(FloatingHorsesConfig::new, RegisterType.BOTH);
    public FloatingHorsesConfig() {
        super(Identifier.of(FloatingHorses.MOD_ID, "config"));
    }

    @ValidatedFloat.Restrict(min = 0f, max = 1f, type = ValidatedNumber.WidgetType.SLIDER)
    public float normalHorseSpeed = 0.8f;

    @ValidatedFloat.Restrict(min = 0f, max = 1f, type = ValidatedNumber.WidgetType.SLIDER)
    public float skeletonHorseSpeed = 0.96f;

    public float scaleSpeed(float startingSpeed){
        double minSpeed = 1;
        double maxSpeed = 2;
        double b = Math.log((maxSpeed/minSpeed)/(maxSpeed-minSpeed));
        double a = maxSpeed / Math.exp(b*maxSpeed);
        return (float) (a * Math.exp(b*startingSpeed));
    }
}
