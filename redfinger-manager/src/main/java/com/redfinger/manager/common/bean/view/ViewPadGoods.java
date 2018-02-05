package com.redfinger.manager.common.bean.view;

import java.util.ArrayList;
import java.util.List;

import com.redfinger.manager.modules.goods.dto.PadGoodsDto;

public class ViewPadGoods {

	private List<PadGoodsDto> padGoods = new ArrayList<PadGoodsDto>();

	public List<PadGoodsDto> getPadGoods() {
		return padGoods;
	}

	public void setPadGoods(List<PadGoodsDto> padGoods) {
		this.padGoods = padGoods;
	}
}
