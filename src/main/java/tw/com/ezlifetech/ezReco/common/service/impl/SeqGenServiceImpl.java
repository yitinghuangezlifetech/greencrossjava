package tw.com.ezlifetech.ezReco.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.respository.BasicSysparamDlRespository;
import tw.com.ezlifetech.ezReco.respository.BasicSysparamHtRespository;
import tw.com.ezlifetech.ezReco.respository.ItemOrderRespository;
import tw.com.ezlifetech.ezReco.respository.ProductPicRespository;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;
import tw.com.ezlifetech.ezReco.respository.ProductSaleRespository;
import tw.com.ezlifetech.ezReco.respository.RefProdGiftRespository;
import tw.com.ezlifetech.ezReco.respository.RoleRepository;
import tw.com.ezlifetech.ezReco.respository.ShoppingCarRespository;
import tw.com.ezlifetech.ezReco.respository.WebPagesDlRespository;
import tw.com.ezlifetech.ezReco.respository.WebPagesRespository;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
public class SeqGenServiceImpl implements SeqGenService{

	@Autowired
	private ProductRespository productRespository;
	
	@Autowired
	private ProductPicRespository productPicRespository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ShoppingCarRespository shoppingCarRespository;
	
	@Autowired
	private ItemOrderRespository itemOrderRespository;
	
	@Autowired
	private ProductSaleRespository productSaleRespository;
	
	@Autowired
	private RefProdGiftRespository refProdGiftRespository;
	
	@Autowired
	private BasicSysparamHtRespository basicSysparamHtRespository;
	
	@Autowired
	private BasicSysparamDlRespository basicSysparamDlRespository;
	
	@Autowired
	private WebPagesRespository webPagesRespository;
	
	@Autowired
	private WebPagesDlRespository webPagesDlRespository;
	
	@Override
	public String getProductNumber() {
		Long number = productRespository.getNextProductSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getProductPicNumber() {
		Long number = productPicRespository.getNextProductPicSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getCustRoleNumber() {
		Long number =roleRepository.getNextCustRoleSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getShoppingCarNumber() {
		Long number =shoppingCarRespository.getNextShoppingCarSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getItemOrderNumber() {
		Long number =itemOrderRespository.getNextItemOrderSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getProductSaleNumber() {
		Long number =productSaleRespository.getNextProductSaleSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getProductGiftNumber() {
		Long number =refProdGiftRespository.getNextProductGiftSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getSysparamHtNumber() {
		Long number =basicSysparamHtRespository.getNextSysparamHtSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getSysparamDlNumber() {
		Long number =basicSysparamDlRespository.getNextSysparamDlSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}

	@Override
	public String getSystemTimeRandomNumber() {
		String res = System.currentTimeMillis()+StringUtil.getRandomNum(3);
		return res;
	}
	
	@Override
	public String getWebPagesNumber() {
		Long number = webPagesRespository.getNextWebPagesSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}
	
	@Override
	public String getWebPagesDlNumber() {
		Long number = webPagesDlRespository.getNextWebPagesDlSeq();
		return DateUtil.getSystemTime("yyMMdd")+String.format("%04d",number);
	}
}
