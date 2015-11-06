package softeng.coop.ui.viewdefinitions.viewmodels;

import java.util.*;

public class LanguageInfosViewModel
{
	private List<LanguageInfo> languageInfos;
	private LanguageInfo selectedLanguageInfo;
	
	public LanguageInfosViewModel()
	{
		this.languageInfos = new ArrayList<LanguageInfo>();
	}

	public LanguageInfo getSelectedLanguageInfo()
	{
		return selectedLanguageInfo;
	}

	public void setSelectedLanguageInfo(LanguageInfo selectedLanguageInfo)
	{
		this.selectedLanguageInfo = selectedLanguageInfo;
	}

	public List<LanguageInfo> getLanguageInfos()
	{
		return languageInfos;
	}

}
