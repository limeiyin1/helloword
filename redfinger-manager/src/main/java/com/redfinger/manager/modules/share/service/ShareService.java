package com.redfinger.manager.modules.share.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfShare;
import com.redfinger.manager.common.domain.RfShareExample;
import com.redfinger.manager.common.domain.RfShareExample.Criteria;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfShareMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "shareId")
public class ShareService extends BaseService<RfShare, RfShareExample, RfShareMapper> {
	/*
	 * @Autowired LogAdminMapper logAdminMapper;
	 */
	@Autowired
	RfShareMapper shareMapper;

	/**
	 * 保存
	 */
	public void saveShare(HttpServletRequest request, RfShare bean) throws Exception {
		String path = request.getServletPath();
		this.save(request, bean);

	}

	public void updateShare(HttpServletRequest request, RfShare bean) throws Exception {
		String path = request.getServletPath();
		this.update(request, bean);

	}

	public void startShare(HttpServletRequest request, RfShare bean) throws Exception {
		String path = request.getServletPath();
		String[] ids = StringUtils.split(bean.getIds(), ",");
		// 获取shareId
		for (String shareId : ids) {
			// 获取分享对象
			RfShare rfShareObj = shareMapper.selectByPrimaryKey(Integer.parseInt(shareId));
			// 根据对象去查询类型和客户端同样的数据
			RfShareExample example = new RfShareExample();
			Criteria criteria = example.createCriteria();
			criteria.andClientEqualTo(rfShareObj.getClient()).andShareTypeEqualTo(rfShareObj.getShareType());
			//查询到的所有类型和客户端相同的数据
			List<RfShare> list = this.selectByExample(example);
			//没有查询到同样的数据启用
			if (null == list && list.size() == 0) {
				this.start(request, bean);
			} else {
				//查询到相同的数据
				for (RfShare rfShare : list) {
					if (StringUtils.equals(rfShare.getEnableStatus(), ConstantsDb.globalEnableStatusNomarl())
							&& StringUtils.equals(rfShare.getStatus(), ConstantsDb.globalStatusNomarl())) {
						throw new BusinessException("启用失败,已启用相同客户端,相同类型分享!");
					} else {
						this.start(request, bean);
					}
				}
			}
		}
	}

	public void stopShare(HttpServletRequest request, RfShare bean) throws Exception {
		String path = request.getServletPath();
		this.stop(request, bean);

	}

	public void removeShare(HttpServletRequest request, RfShare bean) throws Exception {
		String path = request.getServletPath();
		this.remove(request, bean);

	}

	public List<RfShare> selectByExample(RfShareExample example) throws Exception {
		List<RfShare> selectByExample = shareMapper.selectByExample(example);
		return selectByExample;
	}

}
