package fr.skah.playersize.capabilities;

import fr.skah.playersize.capabilities.sizecapa.ISizeCap;
import fr.skah.playersize.capabilities.sizecapa.SizeCapStorage;
import fr.skah.playersize.capabilities.sizecapa.SizeDefaultCap;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class Capabilities {

	public static void init()
	{
		CapabilityManager.INSTANCE.register(ISizeCap.class, new SizeCapStorage(), new CababilityFactory());
	}

	private static class CababilityFactory implements Callable<ISizeCap>
	{
		@Override
		public ISizeCap call() throws Exception
		{
			return new SizeDefaultCap();
		}
	}
}
