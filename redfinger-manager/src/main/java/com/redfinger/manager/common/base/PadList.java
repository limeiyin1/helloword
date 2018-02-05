package com.redfinger.manager.common.base;

import java.util.ArrayList;
import java.util.List;

import com.redfinger.manager.common.domain.RfPad;

public class PadList {
private List<RfPad> pads=new ArrayList<RfPad>();

public List<RfPad> getPads() {
	return pads;
}

public void setPads(List<RfPad> pads) {
	this.pads = pads;
}
}
