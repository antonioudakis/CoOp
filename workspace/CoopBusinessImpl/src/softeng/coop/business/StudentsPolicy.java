package softeng.coop.business;

/**
 * Policy for students management.
 */
public class StudentsPolicy
{
	private boolean registrationSuggestionEnabledOnlyWhenInNoActiveCoop;

	/**
	 * If true, student is suggested to register to new coops only if 
	 * the student is not registered to an active coop.
	 * Default is false.
	 */
	public boolean isRegistrationSuggestionEnabledOnlyWhenInNoActiveCoop()
	{
		return registrationSuggestionEnabledOnlyWhenInNoActiveCoop;
	}

	/**
	 * If true, student is suggested to register to new coops only if 
	 * the student is not registered to an active coop.
	 */
	public void setRegistrationSuggestionEnabledOnlyWhenInNoActiveCoop(boolean registrationSuggestionEnabledOnlyWhenInNoActiveCoop)
	{
		this.registrationSuggestionEnabledOnlyWhenInNoActiveCoop = registrationSuggestionEnabledOnlyWhenInNoActiveCoop;
	}
}
