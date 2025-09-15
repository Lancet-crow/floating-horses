package lancet_.swimming_horses;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwimmingHorses implements ModInitializer {
	public static final String MOD_ID = "swimming-horses";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info(String.valueOf(SwimmingHorsesConfig.config.scaleSpeed(SwimmingHorsesConfig.config.normalHorseSpeed)));
		LOGGER.info(String.valueOf(SwimmingHorsesConfig.config.scaleSpeed(SwimmingHorsesConfig.config.skeletonHorseSpeed)));
	}
}