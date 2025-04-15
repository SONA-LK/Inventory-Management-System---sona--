package com.sona.themes;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class mac
	extends FlatMacLightLaf
{
	public static final String NAME = "mac";

	public static boolean setup() {
		return setup( new mac() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, mac.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
