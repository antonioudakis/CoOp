package softeng.coop.ui.viewdefinitions.viewmodels;

import softeng.coop.dataaccess.*;
import java.util.*;

import com.vaadin.data.util.*;

public class HeaderViewModel
{
	private List<LanguageInfo> languageInfos;
	private BeanItem<? extends AuthenticatedUser> authenticatedUser;
	
	public List<LanguageInfo> getLanguageInfos()
	{
		return languageInfos;
	}
	
	public void setLanguageInfos(List<LanguageInfo> languageInfos)
	{
		this.languageInfos = languageInfos;
	}
	
	public BeanItem<? extends AuthenticatedUser> getAuthenticatedUser()
	{
		return authenticatedUser;
	}
	
	public void setAuthenticatedUser(BeanItem<? extends AuthenticatedUser> authenticatedUser)
	{
		this.authenticatedUser = authenticatedUser;
	}
}
