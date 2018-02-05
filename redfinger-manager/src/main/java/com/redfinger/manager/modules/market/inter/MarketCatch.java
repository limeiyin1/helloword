package com.redfinger.manager.modules.market.inter;

import com.redfinger.manager.common.domain.MarketGame;
import com.redfinger.manager.common.domain.MarketResource;
import com.redfinger.manager.common.domain.RfVersion;

public interface MarketCatch {

	public void runCatch(MarketResource reource,MarketGame game,RfVersion version);
		
}
