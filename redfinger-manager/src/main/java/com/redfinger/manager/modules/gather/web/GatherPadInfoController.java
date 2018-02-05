package com.redfinger.manager.modules.gather.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.GatherConstants;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.gather.domain.RfGatherExpand;
import com.redfinger.manager.common.gather.domain.RfGatherFieldDefine;
import com.redfinger.manager.common.gather.domain.RfGatherPadInfo;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.gather.service.GatherFieldDefineService;
import com.redfinger.manager.modules.gather.service.GatherPadInfoService;
import com.redfinger.manager.modules.gather.service.GatherExpandService;
import com.redfinger.manager.modules.gather.web.GatherDeviceInfoController.SortByReorder;

@Controller
@RequestMapping(value = "/gather/padInfo")
public class GatherPadInfoController extends BaseController {
	
	@Autowired
	GatherPadInfoService service;
	@Autowired
	GatherExpandService expandService;
	@Autowired
	PadService padService;
	@Autowired
	GatherFieldDefineService fieldDefineService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGatherPadInfo> list(HttpServletRequest request, HttpServletResponse response, Model model,RfGatherPadInfo bean,String padCode,String ip) throws Exception {
		String ipFromPadCode;
		if(!StringUtils.isEmpty(padCode)){
			List<RfPad> pads = padService.initQuery().andLike("padCode", padCode).singleAll();
			if(pads!=null&&pads.size()>0){
				ipFromPadCode = pads.get(0).getPadIp();
				if((!StringUtils.isEmpty(ip))&&!ip.equals(ipFromPadCode)){
					ip=GatherConstants.TOFINDNULL;
				}else{
					ip=ipFromPadCode;
				}
			}else{
				ip=GatherConstants.TOFINDNULL;
			}
		}
		
		
		List<RfGatherPadInfo> list = service.initQuery()
				//.andEqualTo("padCode", padCode)
				.andLike("ip", ip)
				.pageAll(bean.getPage(), bean.getRows());
		for(RfGatherPadInfo li : list){
			RfGatherPadInfo temp = new RfGatherPadInfo();
			temp.setPadCode(li.getPadCode());
			temp.setIp(li.getIp());
			temp = service.selectOneByMap(temp);
			li.setPadInfoId(temp.getPadInfoId());
			li.setIp(temp.getIp());
			li.setEnableStatus(temp.getEnableStatus());
			li.setStatus(temp.getStatus());
			li.setCreater(temp.getCreater());
			li.setCreateTime(temp.getCreateTime());
			li.setModifier(temp.getModifier());
			li.setModifyTime(temp.getModifyTime());
			
			List<RfPad> pads = padService.initQuery().andEqualTo("padIp", li.getIp()).singleAll();
			if(pads!=null&&pads.size()>0){
				li.setPadCode(pads.get(0).getPadCode());
			}
			
			List<RfGatherPadInfo> gatherPadInfosForVmMac = service.initQuery()//想将vmMac放到上面
					.andEqualTo("ip", ip)
					.andEqualTo("fieldCode", "vmMac")
					.singleAll();
			if(null!=gatherPadInfosForVmMac&&gatherPadInfosForVmMac.size()>0){
				li.setVmMac(gatherPadInfosForVmMac.get(0).getValue());
			}
			
		}
		PageInfo<RfGatherPadInfo> pageInfo = new PageInfo<RfGatherPadInfo>(list);
		return pageInfo;
	}
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "getInfo")
	public Map<String, Object> getInfo(HttpServletRequest request, HttpServletResponse response, Model model,RfGatherPadInfo bean) throws Exception {
		//bean = service.get(bean.getPadInfoId());
		List<RfGatherPadInfo> list = service.selectAllByIp(bean);
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> tables = new ArrayList<>();
		List<RfGatherPadInfo> needDel = new ArrayList<>();
		
		for(RfGatherPadInfo li : list){
			if(StringUtils.isEmpty(li.getLable())){//如果lable为空，则显示key
				li.setLable(li.getFieldCode());
			}
			/*if("vmIp".equals(li.getFieldCode())||"vmMac".equals(li.getFieldCode())){
				needDel.add(li);
			}*/
			if(StringUtils.isEmpty(li.getValue())){//如果值为空，先去找拓展表，是否为拓展数据，若不是，则删除该值
				List<RfGatherExpand> expands = expandService.initQuery().andEqualTo("infoId", li.getPadInfoId()).findAll();
				needDel.add(li);
				if(expands.size()>0){
					if(getValueCount(expands)<=1){
						Map<String, Object> table = new HashMap<>();
						table.put("title", li.getLable());
						List<String> head = getHead(expands);
						List<List<String>> body = new ArrayList<>();
							List<String> row = new ArrayList<>();
							for(String he : head){
								String value = getValueNoGroupId(expands, he);
								row.add(value);
							}
							body.add(row);
							for(int j=0;j<head.size();j++){
								//解释表头
								List<RfGatherFieldDefine> fieldDefines = fieldDefineService.initQuery().andEqualTo("fieldCode", head.get(j)).singleAll();
								if(null!=fieldDefines&&fieldDefines.size()>0){
									head.set(j, fieldDefines.get(0).getLable());
								}
							}
						table.put("head", head);
						table.put("body", body);
						tables.add(table);
					}else{
						Map<String, Object> table = new HashMap<>();
						table.put("title", li.getLable());
						int rowCount = getRowCount(expands);
						List<String> head = getHead(expands);
						List<List<String>> body = new ArrayList<>();
						for(int i=0;i<rowCount;i++){
							List<String> row = new ArrayList<>();
							for(String he : head){
								String value = getValue(expands, String.valueOf(i), he);
								row.add(value);
							}
							body.add(row);
						}
						for(int j=0;j<head.size();j++){
							//解释表头
							List<RfGatherFieldDefine> fieldDefines = fieldDefineService.initQuery().andEqualTo("fieldCode", head.get(j)).singleAll();
							if(null!=fieldDefines&&fieldDefines.size()>0){
								head.set(j, fieldDefines.get(0).getLable());
							}
						}
						table.put("head", head);
						table.put("body", body);
						tables.add(table);
					}
				}
			}
		}
		list.removeAll(needDel);//删除值为空的，看业务需求添加与否
		result.put("tables", tables);
		result.put("list", list);
		
		
		return result;
	}
	
	private int getRowCount(List<RfGatherExpand> expands){
		Set<String> set = new HashSet<>();
		for(RfGatherExpand ex : expands){
			set.add(ex.getGroupId());
		}
		return set.size();
	}
	
	
	private List<String> getHead(List<RfGatherExpand> expands){
		Set<String> set = new HashSet<>();
		for(RfGatherExpand ex : expands){
			set.add(ex.getFieldCode());
		}
		List<String> head = new ArrayList<>();
		head.addAll(set);
		List<RfGatherFieldDefine> fieldDefines = new ArrayList<>();
		
		for(String he : head){
			RfGatherFieldDefine fieldDefine = fieldDefineService.get(he);
			fieldDefines.add(fieldDefine);
			if(null==fieldDefine){//如果这个字段没有在字段定义那里定义，则直接退出，不进行排序，只显示fieldCode
				log.info("有字段没有定义，无法排序，无法翻译");
				return head;
			}
		} 
		
		Collections.sort(fieldDefines, new SortByReorder());//对表头进行排序
		head.clear();
		for(RfGatherFieldDefine de : fieldDefines){
			head.add(de.getFieldCode());
		}
		return head;
	}
	
	private String getValue(List<RfGatherExpand> expands,String groupId,String head){
		for(RfGatherExpand ex : expands){
			if(ex.getGroupId().equals(groupId)&&ex.getFieldCode().equals(head)){
				return ex.getValue();
			}
		}
		return null;
	}
	
	private String getValueNoGroupId(List<RfGatherExpand> expands,String head){
		for(RfGatherExpand ex : expands){
			if(ex.getFieldCode().equals(head)){
				return ex.getValue();
			}
		}
		return null;
	}
	
	private int getValueCount(List<RfGatherExpand> expands){
		int valueCount = 0;
		for(RfGatherExpand ex : expands){
			if("0".equals(ex.getGroupId())){
				valueCount++;
			}
		}
		return valueCount;
	}
	
	public static void main(String[] args) {
		String json = "{\"opType\":\"monitorData\",\"padIp\":\"192.168.169.96\",\"scriptCode\":\"test_code2\",\"exStatus\":0,\"resultInfo\":\"{\\\"padInfo\\\":{\\\"padTemp\\\":54,\\\"CPUFrequence\\\":[{\\\"cpu0\\\":1800000},{\\\"cpu1\\\":1800000},{\\\"cpu2\\\":1800000},{\\\"cpu3\\\":1800000}],\\\"CPULoadAvg\\\":[{\\\"cpuLoadAvgOneMinute\\\":7.69},{\\\"cpuLoadAvgFiveMinute\\\":6.13},{\\\"cpuLoadAvgFifteenMinute\\\":5.27}],\\\"memUtili\\\":\\\"32.50%\\\",\\\"storageUtili\\\":\\\"41.17%\\\",\\\"kernelVersion\\\":\\\"3.10.0\\\",\\\"manageMain\\\":\\\"b7b597db9666359773b9ee6a4ccf5020\\\",\\\"manageAgent\\\":\\\"b146c7d115427915d02d3330f7013d01\\\",\\\"remotePlay\\\":\\\"20942a5a6e00e3fdd69e15722a4d2545\\\",\\\"padMac\\\":\\\"74:00:00:07:2c:77\\\",\\\"procInfo\\\":[{\\\"pid\\\":11912,\\\"vss\\\":\\\"503204K\\\",\\\"rss\\\":\\\"409384K\\\",\\\"pss\\\":\\\"395816K\\\",\\\"uss\\\":\\\"395472K\\\"},{\\\"pid\\\":3077,\\\"vss\\\":\\\"2032220K\\\",\\\"rss\\\":\\\"281016K\\\",\\\"pss\\\":\\\"207260K\\\",\\\"uss\\\":\\\"195044K\\\"},{\\\"pid\\\":9293,\\\"vss\\\":\\\"1152760K\\\",\\\"rss\\\":\\\"116248K\\\",\\\"pss\\\":\\\"72799K\\\",\\\"uss\\\":\\\"65948K\\\"},{\\\"pid\\\":3513,\\\"vss\\\":\\\"1670640K\\\",\\\"rss\\\":\\\"93476K\\\",\\\"pss\\\":\\\"44769K\\\",\\\"uss\\\":\\\"38884K\\\"}]},\\\"padInfo\\\":[{\\\"vmMac\\\":\\\"fe:fa:82:c0:19:f6\\\",\\\"vmIp\\\":\\\"192.168.168.105\\\"},{\\\"vmMac\\\":\\\"fe:c9:5b:b9:d2:93\\\",\\\"vmIp\\\":\\\"192.168.168.198\\\"},{\\\"vmMac\\\":\\\"fe:52:3d:59:25:fb\\\",\\\"vmIp\\\":\\\"192.168.168.197\\\"},{\\\"vmMac\\\":\\\"fe:ae:51:49:de:3a\\\",\\\"vmIp\\\":\\\"192.168.168.201\\\"}]}\"}";
		Map map = JsonUtils.stringToObject(json, Map.class);
		System.out.println(map.get("resultInfo"));
		Map map2 = JsonUtils.stringToObject(map.get("resultInfo").toString(), Map.class);
		System.out.println(map2);
		//System.out.println(map);
		
	}
	
	class SortByReorder implements Comparator {//用来对表头排序
        public int compare(Object o1, Object o2) {
        	RfGatherFieldDefine s1 = (RfGatherFieldDefine) o1;
        	RfGatherFieldDefine s2 = (RfGatherFieldDefine) o2;
        	if(null==s1||null==s1.getReorder()){
        		return 1;
        	}
        	if(null==s2||null==s2.getReorder()){
        		return -1;
        	}
        	return s1.getReorder().compareTo(s2.getReorder());
        }
       }
	
	
}