package com.redfinger.manager.modules.stat.base;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redfinger.manager.common.exception.BusinessException;
public class StatDomain {

	// 显示结果
	private Integer id;
	private String label;
	private double amount;
	private int number;
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	// 查询条件
	@JsonIgnore
	private String type;
	@JsonIgnore
	private String begin;
	@JsonIgnore
	private String end;
	@JsonIgnore
	private String where;
	@JsonIgnore
	private String formart;

	public String getFormart() {
		return formart;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		if (this.getType().equals("month")) {
			this.formart = "yyyy-MM";
		} else if (this.getType().equals("week")) {
			this.formart = "yyyy-MM-DD";
		} else if (this.getType().equals("day")) {
			this.formart = "yyyy-MM-DD";
		} else {
			throw new BusinessException("日期类型有错误！");
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public void setFormart(String formart) {
		this.formart = formart;
	}

	public double getPrice() {
		return amount;
	}

	public void setPrice(double price) {
		this.amount = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonIgnore
	public String getSubTitle() {
		if (this.getType().equals("month")) {
			return this.getBegin().substring(0, 7) + " - " + this.getEnd().substring(0, 7);
		} else if (this.getType().equals("day") || this.getType().equals("week")) {
			return this.getBegin() + " 至 " + this.getEnd();
		} else {
			return "";
		}
	}
	
	public static void main(String[] args) {
		String url="http://app.market.xiaomi.com/apm/search?clientId=e61ecf1d9805afc9cdc302a5e3b37d4a&co=CN&densityScaleFactor=3.0&deviceType=0&imei=07f417ef2a5da22a8b8509ff8c1c1f5e&keyword=%E5%A4%A9%E9%BE%99%E5%85%AB%E9%83%A83d&la=zh&marketVersion=150&miuiBigVersionName=V6&model=MI+4W&os=V6.1.2.0.KXDCNBJ&page=0&ref=suggestion&resolution=1080*1920&ro=unknown&sdk=19&session=fgBuXak96alheJ_m&";
		RestTemplate temp = new RestTemplate();
		
		for(int i=0;i<1000;i++){
			try{
				ResponseEntity<String> result=temp.getForEntity(url, String.class);
				String json=java.net.URLDecoder.decode(result.getBody(),"utf-8");
				System.out.println(json);
				if(json.indexOf("天龙八部")!=-1){
					System.out.println("yes");
				}else{
					System.out.println("no");
				}
			}catch(Exception e){
				System.err.println("fault");
			}
		}
	}
	

}
